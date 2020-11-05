FROM openjdk:12-alpine
COPY ./* /app/
WORKDIR /app/
RUN javac -d ./output ./OpgAnalyse.java
WORKDIR /app/output