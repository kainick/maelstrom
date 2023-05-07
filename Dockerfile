FROM docker.io/library/gradle:7.4.2-jdk17 as build

COPY . /work
WORKDIR /work

RUN /usr/bin/gradle --console=plain --info --stacktrace --no-daemon build

FROM docker.io/library/eclipse-temurin:17-jre-alpine
EXPOSE 25565
WORKDIR /server

COPY --from=build /work/build/libs/maelstrom-*-all.jar /server/server.jar

CMD ["java", "-jar", "/server/server.jar"]
