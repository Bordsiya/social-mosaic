FROM openjdk:17

WORKDIR /app

COPY ./build/libs/core-service-0.0.1-SNAPSHOT.jar /app/

# Команда для запуска приложения при старте контейнера
CMD ["java", "-jar", "core-service-0.0.1-SNAPSHOT.jar"]