FROM azul/zulu-openjdk-alpine:21

COPY target/*.jar config.jar

ENTRYPOINT ["java","-jar", "config.jar"]

EXPOSE 9296