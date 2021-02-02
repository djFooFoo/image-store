FROM gradle:jdk15

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
WORKDIR /home/spring

RUN gradle clean build -x test

COPY /build/libs/*.jar application.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "application.jar"]
