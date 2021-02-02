FROM maven:3.6-openjdk-11

COPY ./auth-service/target/*.jar /home/app/

WORKDIR /home/app


EXPOSE 8088

ENTRYPOINT java -jar *.jar

