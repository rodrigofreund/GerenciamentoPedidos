# GerenciamentoPedidos
Gerar a imagem
mvn clean package docker:build

Executar subir o docker
docker run --name gerped -p 8080:8080 gerenciamentopedidos:latest
