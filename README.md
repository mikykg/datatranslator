# Data Translator

## About

<p>Data Translator is a multithreading high performance scheduler which can process and transform large data files based on defined configurations.</p>
Ref below  samples of supported Data and config files

- [Column Config](https://github.com/mikykg/datatranslator/blob/main/resources/testfiles/column.config)
- [Row Config](https://github.com/mikykg/datatranslator/blob/main/resources/testfiles/vendor.config)
- [Sample Data](https://github.com/mikykg/datatranslator/blob/main/resources/testfiles/data.dat)

## Requirements

- [Java 11](https://www.oracle.com/in/java/technologies/javase-jdk11-downloads.html)
- [gradle 6.6](https://docs.gradle.org/6.6/release-notes.html)
- [Docker](https://www.docker.com/?utm_source=google&utm_medium=cpc&utm_campaign=dockerhomepage&utm_content=nemea&utm_term=dockerhomepage&utm_budget=growth&gclid=Cj0KCQjwrsGCBhD1ARIsALILBYpuILmY6l15dUg-XaNWUjSA4yCkkESn7Z0YdeRz5QOWxwaORUtgeFkaAtLpEALw_wcB)

## Dependencies

- [Spring Boot - 2.3.5](https://start.spring.io/) 

## Build & Run (Gradle)

- Gradle Build
    ```. /gradlew clean build```
- Gradle Run
    ```. /gradlew bootRun```
### Runtime args
- `-Dapp.config.columnConfig.path=<columnConfig.path>`
- `-Dapp.config.vendorConfig.path=<vendorConfig.path>`
- `-Dapp.config.vendorData.path=<vendorData.path>`
- `-Dapp.config.processedData.path=<processedData.path>`
- `-Dapp.config.schedule=<schedule>`

## Exception Handling
//TODO
## Tests
//TODO
#### Unit & Acceptance Testing
//TODO
#### Perfomance Tests
//TODO

## Docker
### Build

- Gradle Build
    ```. /gradlew clean build```
- Docker Build
    `docker build -t datatranslator:1.0.0 .`

### Deploy / Run
`docker run --mount type=bind,source="$(pwd)"/resources/testfiles,dst=/home/data -it datatranslator:1.0.0`

> NB : Replace `$(pwd)"/resources/testfiles` to point to any path as required.
 
### Dockerhub
Data Translator is also available in 
[dockerhub](https://hub.docker.com/repository/docker/michaelkgeorge/datatranslator)

Pull the latest image 

`docker pull michaelkgeorge/datatranslator:latest`

## Contribute

[PR](https://github.com/mikykg/datatranslator/pulls)


## Find me 

- [Linkedin](https://www.linkedin.com/in/michael-george-7881b9126/)
- [Stackoverflow](https://stackoverflow.com/users/12447757/michael-george)

