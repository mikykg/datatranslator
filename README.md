# Data Translator



## Requirements

- [Java 11](https://www.oracle.com/in/java/technologies/javase-jdk11-downloads.html)
- [gradle 6.6](https://docs.gradle.org/6.6/release-notes.html)
- [Docker](https://www.docker.com/?utm_source=google&utm_medium=cpc&utm_campaign=dockerhomepage&utm_content=nemea&utm_term=dockerhomepage&utm_budget=growth&gclid=Cj0KCQjwrsGCBhD1ARIsALILBYpuILmY6l15dUg-XaNWUjSA4yCkkESn7Z0YdeRz5QOWxwaORUtgeFkaAtLpEALw_wcB)

## Dependencies

- [Spring Boot - 2.3.5](https://start.spring.io/) 

## Build And Run (springboot)

- Gradle Build
    ```. /Gradlew clean build```
- Gradle Run
    ```. /Gradlew bootRun```
    
## Exception Handling
//TODO
## Tests
//TODO
#### Unit & Acceptance Testing
//TODO
#### Perfomance Tests
//TODO

## Contribute

[PR](https://github.com/mikykg/datatranslator/pulls) & [CI](TODO)

### Docker
#### Build

- Gradle Build
    ```. /Gradlew clean build```
- docker build -t datatranslator:1.0.0 .

#### Deploy / Run
docker run datatranslator:1.0.0 

#### Runtime args
- `-Dapp.config.columnConfig.path=<columnConfig.path>`
- `-Dapp.config.vendorConfig.path=<vendorConfig.path>`
- `-Dapp.config.vendorData.path=<vendorData.path>`
- `-Dapp.config.processedData.path=<processedData.path>`
- `-Dapp.config.schedule=<schedule>`


## See Also

- [Linkedin](https://www.linkedin.com/in/michael-george-7881b9126/)
- [Stackoverflow](https://stackoverflow.com/users/12447757/michael-george)

