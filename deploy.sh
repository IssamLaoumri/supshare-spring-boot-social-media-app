#!/bin/bash

# === CONFIG ===
JAR_NAME="supshare-spring-boot-social-media-app-0.0.1.jar"
LOCAL_JAR_PATH="target/$JAR_NAME"
REMOTE_SERVER="admin@5.75.244.235"
REMOTE_JAR_DIR="/opt/test-supshare"

echo "🚀 Deploying $JAR_NAME to $REMOTE_SERVER:$REMOTE_JAR_DIR"

# Transfer JAR
rsync -avz -e "ssh" $LOCAL_JAR_PATH $REMOTE_SERVER:$REMOTE_JAR_DIR/supshare-test.jar

# Restart service
ssh $REMOTE_SERVER << EOF
  echo "♻ Restarting supshare-test.service..."
  sudo systemctl restart supshare-test.service
EOF

echo "✅ Deployment completed!"
