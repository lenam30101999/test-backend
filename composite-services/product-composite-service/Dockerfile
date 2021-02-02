FROM maven:3.6-openjdk-11

COPY ./product-composite-service/target/*.jar /home/app/

WORKDIR /home/app


EXPOSE 8091

ENTRYPOINT java -jar *.jar
