# Distribution linux et version du JRE
FROM eclipse-temurin:17-jre-alpine
# Dossier racine du conteneur
WORKDIR locnes
# Copie du .jar
COPY target/locnes.jar locnes.jar
# Copie du .env
COPY .env .env

# GESTION DES PORTS
# Port ouvrable :
EXPOSE 8080

# [commande à exécuter, paramètres, fichier à exécuter]
ENTRYPOINT ["java", "-jar", "locnes.jar"]