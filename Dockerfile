FROM openjdk:17
WORKDIR /app
COPY build/target/ms-turbo-worker.jar app.jar
CMD ["java", "-jar","app.jar"]