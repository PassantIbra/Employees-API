FROM openjdk:11
ADD challenge.jar challenge.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "challenge.jar"]