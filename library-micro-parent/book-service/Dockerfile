FROM azul/zulu-openjdk-alpine:21

COPY target/*.jar book-service.jar

ENTRYPOINT ["java","-jar", "book-service.jar"]

EXPOSE 8081