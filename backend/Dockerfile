FROM openjdk:17-jdk-slim as builder
#FROM azul/zulu-openjdk-alpine:17-jre as builder
#FROM gradle:7.2-jdk1.8 as builder
#FROM openjdk:8 as builder
WORKDIR /app
COPY . .
CMD ["java" "-version"]
RUN chmod +x ./gradlew
RUN ./gradlew clean build

#FROM azul/zulu-openjdk-alpine:17-jre as runner
FROM openjdk:17-jdk-slim as runner
#FROM openjdk:jre-alpine as runner
EXPOSE 5000
COPY --from=builder /app/build/libs/Emo-Bank-1.0-SNAPSHOT.jar ./app.jar
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "./app.jar"]
