FROM maven:3.9.6-eclipse-temurin-21
WORKDIR /workspace
COPY . .
RUN mvn -q versions:use-latest-releases -DgenerateBackupPoms=false \
    && mvn -B clean test
CMD ["bash"] 