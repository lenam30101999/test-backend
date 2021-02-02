FROM maven:3.6-openjdk-11

COPY ./category-composite-service/target/*.jar /home/app/

WORKDIR /home/app


EXPOSE 8090

ENTRYPOINT java -jar *.jar
