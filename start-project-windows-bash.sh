echo "Iniciando ZooKeeper"
cd "C:\Kafka\kafka_2.13-3.8.0"
mintty -h always -e bin/zookeeper-server-start.sh config/zookeeper.properties "$@" &

echo "Iniciando Kafka"
cd "C:\Kafka\kafka_2.13-3.8.0"
mintty -h always -e bin/kafka-server-start.sh config/server.properties "$@" &

echo "Iniciando OrderService"
cd ".\order-service"
mintty -h always -e mvn spring-boot:run "$@" &

echo "Iniciando ProductService"
cd ".\product-service"
mintty -h always -e mvn spring-boot:run "$@" &

echo "Iniciando CustomerService"
cd ".\customer-service"
mintty -h always -e mvn spring-boot:run "$@" &

echo "Iniciando el Frontend"
cd "C:\Users\Edu\Desktop\Projects\OnlineShop\store-frontend"
mintty -h always -e ng serve "$@" &