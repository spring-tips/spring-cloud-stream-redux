cd `dirname $0`/..

mvn clean spring-boot:build-image

AN=$( docker images | grep rmq-app | cut -f1 -d\  )

docker run \
  -e SPRING_RABBITMQ_HOST=host.docker.internal  \
  -e SPRING_RABBITMQ_USERNAME=guest  \
  -e SPRING_RABBITMQ_PASSWORD=guest  \
  $( docker images -aq $AN )
