FROM adoptopenjdk/openjdk11:ubi
ARG JAR_FILE=target/*.jar
# default values that will be erases
ENV BOT_NAME=javarush_telegram_kiryl_bot
ENV BOT_TOKEN=6811604095:AAFupp0CpIm7XxdDNV5yiNWLfnce8p0zKPY
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dbot.username=${BOT_NAME}","-Dbot.token=${BOT_TOKEN}", "-jar", "/app.jar"]