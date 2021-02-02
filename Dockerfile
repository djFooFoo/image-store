FROM gradle:jdk15 AS build

COPY --chown=gradle:gradle . /home/gradle/app
WORKDIR /home/gradle/app
RUN gradle build --no-daemon

FROM openjdk:15-jdk
EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/app/build/libs/*.jar /app/spring-boot-application.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/spring-boot-application.jar"]
