FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/*.jar *.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/*.jar"]