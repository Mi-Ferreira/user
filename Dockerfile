# Use uma imagem base com Java 17
FROM eclipse-temurin:17-jdk-alpine

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo JAR do seu projeto para o contêiner
COPY target/user-0.0.1-SNAPSHOT.jar /app/user.jar

# Define a variável de ambiente para o Java
ENV JAVA_OPTS=""

# Expõe a porta 8080, que o Spring Boot usará
EXPOSE 8080

# Comando para rodar a aplicação Java (Spring Boot)
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/user.jar"]