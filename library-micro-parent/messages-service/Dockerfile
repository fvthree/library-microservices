FROM azul/zulu-openjdk-alpine:21

COPY target/*.jar messages.jar

ENTRYPOINT ["java","-jar", "messages.jar"]

EXPOSE 8082