FROM openjdk:21-jdk-bullseye

# Instala Maven manualmente
ARG MAVEN_VERSION=3.8.6
ARG USER_HOME_DIR="/root"
ARG MAVEN_SHA=fcb2cda9163991b12028d2480b9e60105190eda9
ARG BASE_URL=https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries

RUN apt-get update && apt-get install -y curl tar \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  && echo "${MAVEN_SHA}  /tmp/apache-maven.tar.gz" | sha1sum -c - \
  && tar -xzf /tmp/apache-maven.tar.gz -C /opt \
  && ln -s /opt/apache-maven-${MAVEN_VERSION} /opt/maven \
  && rm /tmp/apache-maven.tar.gz \
  && apt-get clean

ENV MAVEN_HOME /opt/maven
ENV PATH $MAVEN_HOME/bin:$PATH

COPY . /usr/src/app/order-service
WORKDIR /usr/src/app/order-service

RUN mvn clean package

CMD ["java", "-jar", "target/order-service.jar"]