FROM openjdk:17-jdk-slim

COPY target/account-deletion-requests-0.0.1-SNAPSHOT.jar /deployments/account-deletion-requests-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "/deployments/account-deletion-requests-0.0.1-SNAPSHOT.jar"]
