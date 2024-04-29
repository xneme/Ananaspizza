FROM adoptopenjdk/openjdk8 

# Install maven
RUN apt-get update
RUN apt-get install -y maven postgresql-client

WORKDIR /code

# Prepare by downloading dependencies
COPY pom.xml /code/pom.xml
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]

# Adding source, compile and package into a fat jar
COPY src /code/src
RUN ["mvn", "package"]

EXPOSE 4567
CMD ["java", "-cp","target/classes/:target/dependency/*", "ykskakskolme.runko.Main"]
#CMD ["ls", "./target"]
