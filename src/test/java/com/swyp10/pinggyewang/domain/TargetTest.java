package com.swyp10.pinggyewang.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class TargetTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  @DisplayName("Target ENUM의 getName() 메서드가 올바른 값을 반환한다")
  void getName_ShouldReturnCorrectName() {
    // given & when & then
    assertThat(Target.BOSS_SENIOR.getName()).isEqualTo("상사/선배");
    assertThat(Target.TEACHER.getName()).isEqualTo("교수/선생님");
    assertThat(Target.COLLEAGUE_FRIEND.getName()).isEqualTo("동료/친구");
    assertThat(Target.LOVER_FAMILY.getName()).isEqualTo("연인/가족");
    assertThat(Target.OTHER.getName()).isEqualTo("기타");
  }

  @ParameterizedTest
  @DisplayName("정확한 이름으로 Target을 찾을 수 있다")
  @CsvSource({
      "상사/선배, BOSS_SENIOR",
      "교수/선생님, TEACHER",
      "동료/친구, COLLEAGUE_FRIEND",
      "연인/가족, LOVER_FAMILY",
      "기타, OTHER"
  })
  void of_ShouldReturnCorrectTarget_WhenExactNameMatches(String inputName, Target expected) {
    // when
    Target result = Target.of(inputName);

    // then
    assertThat(result).isEqualTo(expected);
  }

  @ParameterizedTest
  @DisplayName("부분 매칭으로 Target을 찾을 수 있다")
  @CsvSource({
      "상사, BOSS_SENIOR",
      "선배, BOSS_SENIOR",
      "교수, TEACHER",
      "선생님, TEACHER",
      "동료, COLLEAGUE_FRIEND",
      "친구, COLLEAGUE_FRIEND",
      "연인, LOVER_FAMILY",
      "가족, LOVER_FAMILY"
  })
  void of_ShouldReturnCorrectTarget_WhenPartialNameMatches(String inputName, Target expected) {
    // when
    Target result = Target.of(inputName);

    // then
    assertThat(result).isEqualTo(expected);
  }

  @ParameterizedTest
  @DisplayName("매칭되지 않는 이름은 OTHER를 반환한다")
  @ValueSource(strings = {"팀장님", "담임 선생님", "CEO", "부장님", "랜덤값", "   "})
  void of_ShouldReturnOther_WhenNameDoesNotMatch(String inputName) {
    // when
    Target result = Target.of(inputName);

    // then
    assertThat(result).isEqualTo(Target.OTHER);
  }

  @Test
  @DisplayName("JSON 직렬화가 올바르게 작동한다 (@JsonValue)")
  void jsonSerialization_ShouldWork() throws Exception {
    // given
    Target target = Target.COLLEAGUE_FRIEND;

    // when
    String json = objectMapper.writeValueAsString(target);

    // then
    assertThat(json).isEqualTo("\"동료/친구\"");
  }

  @Test
  @DisplayName("JSON 역직렬화가 올바르게 작동한다 (@JsonCreator)")
  void jsonDeserialization_ShouldWork() throws Exception {
    // given
    String json = "\"동료/친구\"";

    // when
    Target target = objectMapper.readValue(json, Target.class);

    // then
    assertThat(target).isEqualTo(Target.COLLEAGUE_FRIEND);
  }

  @Test
  @DisplayName("알 수 없는 JSON 값은 OTHER로 역직렬화된다")
  void jsonDeserialization_ShouldReturnOther_WhenUnknownValue() throws Exception {
    // given
    String json = "\"팀장님\"";

    // when
    Target target = objectMapper.readValue(json, Target.class);

    // then
    assertThat(target).isEqualTo(Target.OTHER);
  }

  @Test
  @DisplayName("JSON 객체 내에서 Target 필드가 올바르게 처리된다")
  void jsonDeserialization_ShouldWorkInObject() throws Exception {
    // given
    String json = "{\"target\":\"동료/친구\",\"situation\":\"테스트\"}";

    // when
    TestDto result = objectMapper.readValue(json, TestDto.class);

    // then
    assertThat(result.target).isEqualTo(Target.COLLEAGUE_FRIEND);
    assertThat(result.situation).isEqualTo("테스트");
  }

  // 테스트용 DTO
  static class TestDto {
    public Target target;
    public String situation;
  }

  @Test
  @DisplayName("모든 Target ENUM 값이 정의되어 있다")
  void allTargetValues_ShouldBeDefined() {
    // given
    Target[] targets = Target.values();

    // then
    assertThat(targets).hasSize(5);
    assertThat(targets).containsExactly(
        Target.BOSS_SENIOR,
        Target.TEACHER,
        Target.COLLEAGUE_FRIEND,
        Target.LOVER_FAMILY,
        Target.OTHER
    );
  }

  @Test
  @DisplayName("첫 번째 매칭되는 Target을 반환한다 (우선순위 테스트)")
  void of_ShouldReturnFirstMatch_WhenMultipleMatches() {
    // given
    String inputName = "선생"; // "교수/선생님"에 매칭됨

    // when
    Target result = Target.of(inputName);

    // then
    assertThat(result).isEqualTo(Target.TEACHER);
  }
}