FROM adoptopenjdk/openjdk17:ubi
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=javarush_telegram_kiryl_bot
ENV BOT_TOKEN=6811604095:AAFupp0CpIm7XxdDNV5yiNWLfnce8p0zKPY
ENV BOT_DB_USERNAME=tb_mysql_db_user
ENV BOT_DB_PASSWORD=tb_mysql_db_password
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.datasource.password=${BOT_DB_PASSWORD}", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-Dspring.datasource.username=${BOT_DB_USERNAME}", "-jar", "app.jar"]