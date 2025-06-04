# Utiliser une image Maven pour construire l'application
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copier les fichiers nécessaires pour le build
COPY pom.xml .
COPY src ./src

# Construire l'application
RUN mvn clean package -DskipTests

# Utiliser une image Java pour exécuter l'application
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copier le fichier JAR généré depuis l'étape de build
COPY --from=build /app/target/msprAPI-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port utilisé par l'application
EXPOSE 8080

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "app.jar"]