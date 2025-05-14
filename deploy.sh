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


################################################################################
#!/bin/bash

APP_NAME=supshare
IMAGE_NAME=$APP_NAME:latest
CONTAINER_NAME=$APP_NAME-container

# Optional: Copy only necessary files if building remotely
scp Dockerfile target/*.jar admin@5.75.244.235:/home/admin/$APP_NAME/

ssh admin@5.75.244.235 << EOF
  cd $APP_NAME
  docker stop $CONTAINER_NAME || true
  docker rm $CONTAINER_NAME || true
  docker rmi $IMAGE_NAME || true

  # Build Docker image
  docker build -t $IMAGE_NAME .

  # Run container with environment variables
  docker run -d \
    --name $CONTAINER_NAME \
    -e JWT_SECRET=${JWT_SECRET} \
    -e SPRING_PROFILES_ACTIVE=${ACTIVE_PROFILE} \
    -e PROD_DB_PASSWORD=${PROD_DB_PASSWORD} \
    -e PROD_DB_URI=${PROD_DB_URI} \
    -e PROD_DB_USERNAME=${PROD_DB_USERNAME} \
    -p 8080:8080 \
    $IMAGE_NAME
EOF
