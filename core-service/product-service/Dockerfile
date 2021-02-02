FROM maven:3.6-openjdk-11

COPY ./product-service/target/*.jar /home/app/

WORKDIR /home/app


EXPOSE 8086

ENTRYPOINT java -jar *.jar

