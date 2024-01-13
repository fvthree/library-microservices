FROM azul/zulu-openjdk-alpine:21

COPY target/*.jar gateway.jar

ENTRYPOINT ["java","-jar", "gateway.jar"]

EXPOSE 9090