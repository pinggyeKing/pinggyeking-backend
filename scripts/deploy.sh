set -euo pipefail

DIR="${1:-/root/app}"
cd "$DIR"

echo "▶ $(date): Docker Compose 배포 시작 ($DIR)"

if [[ "$DIR" == "/root/app" ]]; then
  COMPOSE_FILES="-f docker-compose.yml -f docker-compose.dev.yml"
else
  COMPOSE_FILES="-f docker-compose.yml -f docker-compose.prod.yml"
fi

docker-compose down || true

docker-compose pull
docker-compose up -d --build

docker-compose ps

echo "▶ $(date '+%Y-%m-%d %H:%M:%S'): 최근 로그"
docker-compose logs --tail=50

echo "▶ $(date '+%Y-%m-%d %H:%M:%S'): 배포 완료"