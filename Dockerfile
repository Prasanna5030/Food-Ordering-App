FROM openjdk:21
LABEL authors="Prasanna"
WORKDIR /usr/app
ENV PORT 8080
EXPOSE 8080
COPY target/food-ordering-app.jar /usr/app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]