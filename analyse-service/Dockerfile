FROM openjdk:17

WORKDIR /app

COPY build/libs/analyse-service-0.0.1-SNAPSHOT.jar /app/

# Команда для запуска приложения при старте контейнера
CMD ["java", "-jar", "analyse-service-0.0.1-SNAPSHOT.jar"]