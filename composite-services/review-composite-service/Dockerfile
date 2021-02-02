FROM maven:3.6-openjdk-11

COPY ./review-composite-service/target/*.jar /home/app/

WORKDIR /home/app


EXPOSE 8092

ENTRYPOINT java -jar *.jar
