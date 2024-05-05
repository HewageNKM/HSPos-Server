FROM maven:3.6.5 AS build
WORKDIR /app
ENV GMAIL=${GMAIL}
ENV GMAIL_PASSWORD=${GMAIL_PASSWORD}
ENV DB_URL=${DB_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}
COPY pom.xml /app
RUN mvn dependency:resolve
COPY . /app
RUN mvn clean
RUN mvn package -DskipTests -X
EXPOSE 8080

FROM openjdk
COPY --from=build /app/target/*.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]