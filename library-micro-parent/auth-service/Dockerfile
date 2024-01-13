FROM azul/zulu-openjdk-alpine:21

COPY target/*.jar auth-service.jar

ENTRYPOINT ["java","-jar", "auth-service.jar"]

EXPOSE 8084