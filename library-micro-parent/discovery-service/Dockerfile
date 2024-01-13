FROM azul/zulu-openjdk-alpine:21

COPY target/*.jar discovery.jar

ENTRYPOINT ["java","-jar", "discovery.jar"]

EXPOSE 8761