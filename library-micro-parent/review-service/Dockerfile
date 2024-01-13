FROM azul/zulu-openjdk-alpine:21

COPY target/*.jar review-service.jar

ENTRYPOINT ["java","-jar", "review-service.jar"]

EXPOSE 8083