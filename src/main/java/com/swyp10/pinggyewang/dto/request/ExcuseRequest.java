package com.swyp10.pinggyewang.dto.request;

import java.util.List;

public record ExcuseRequest(
    String situation, String target, String tone, boolean isRegenerated, String regeneratedBtnVal, List<QuestionRequest> questions
) {

}
