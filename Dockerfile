FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Instalar wait-for-it para esperar que postgres esté listo
RUN apk add --no-cache bash curl
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

COPY target/tallerproapp-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]