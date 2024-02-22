
# 빌드 스테이지
FROM maven:3.6.0-jdk-8-slim AS build
COPY src/ /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

# 패키지 스테이지
FROM openjdk:8-jdk-alpine
COPY --from=build /usr/src/app/target/*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]


