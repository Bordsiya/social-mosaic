FROM openjdk:17

WORKDIR /app

COPY build/libs/graph-service-0.0.1-SNAPSHOT.jar /app/

# Команда для запуска приложения при старте контейнера
CMD ["java", "-jar", "graph-service-0.0.1-SNAPSHOT.jar"]