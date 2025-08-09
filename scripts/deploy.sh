set -euo pipefail

DIR="${1:-/root/app}"
cd "$DIR"

echo "▶ $(date): Docker Compose 배포 시작 ($DIR)"

if [[ "$DIR" == "/root/app" ]]; then
  COMPOSE_FILES="-f docker-compose.yml -f docker-compose.dev.yml"
else
  COMPOSE_FILES="-f docker-compose.yml -f docker-compose.prod.yml"
fi

if command -v docker &>/dev/null && docker compose version &>/dev/null; then
  DC="docker compose"
else
  DC="docker-compose"
fi

$DC $COMPOSE_FILES down --remove-orphans || true

$DC $COMPOSE_FILES build --pull

$DC $COMPOSE_FILES up -d

$DC $COMPOSE_FILES ps

echo "▶ $(date '+%Y-%m-%d %H:%M:%S'): 최근 로그"
$DC $COMPOSE_FILES logs --tail=50

echo "▶ $(date '+%Y-%m-%d %H:%M:%S'): 배포 완료"
