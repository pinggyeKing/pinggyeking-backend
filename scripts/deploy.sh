set -euo pipefail

APP="pinggye-wang-0.0.1-SNAPSHOT.jar"
DIR="/root/app"

LOG="$DIR/app.log"
PORT=8080

echo "▶ $(date): Deploy 시작"

pkill -f "java.*$APP" || true

if [ -f "$LOG" ]; then
  mv "$LOG" "$LOG.$(date +%Y%m%d%H%M%S)"
  find "$DIR" -name 'app.log.*' -mtime +7 -delete
fi

nohup java -jar "$DIR/$APP" --server.address=0.0.0.0 --server.port="$PORT" > "$LOG" 2>&1 &

echo "▶ $(date): 배포 완료 (PID $!)"
touch /root/app/pinggye-wang-0.0.1-SNAPSHOT.jar
echo "Deployed at $(date '+%Y-%m-%d %H:%M:%S')" >> /root/app/deploy.log
