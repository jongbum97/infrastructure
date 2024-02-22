# 첫 번째 단계: Maven을 사용하여 프로젝트 빌드
FROM maven:3.6.3-openjdk-8 as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY WebContent ./WebContent
RUN mvn package

# 두 번째 단계: Tomcat을 사용하여 애플리케이션 배포
FROM tomcat:8.5.61-jdk8-openjdk
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
