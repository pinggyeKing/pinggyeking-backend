set -euo pipefail

DIR="/root/app"
cd "$DIR"

echo "▶ $(date): Docker Compose 배포 시작"

docker-compose down || true

docker-compose pull
docker-compose up -d --build

docker-compose ps

echo "▶ $(date '+%Y-%m-%d %H:%M:%S'): 최근 로그"
docker-compose logs --tail=50

echo "▶ $(date '+%Y-%m-%d %H:%M:%S'): 배포 완료"