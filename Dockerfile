# Dockerfile
FROM openjdk:22
COPY code/target/ImdbLike-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
