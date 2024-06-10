Просмотр информации про топик:
kafka-topics.sh --describe --topic <название_топика> --bootstrap-server localhost:9092

Чтение сообщений из топика:
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic <название_топика> --from-beginning
