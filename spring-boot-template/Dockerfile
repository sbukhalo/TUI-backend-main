FROM eclipse-temurin:17-jdk-alpine
#FROM eclipse-temurin:17-jdk
VOLUME /tmp
COPY build/libs/quotes-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]