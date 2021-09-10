# -Запуск микросервиса через Docker

1. В терминале пропивать команду `./gradlew clean build`.
Данная команда генирирует `jar` файл по пути `build\libs`

2. Для запуска jar файла через докер контейнер требуется Dockerfile

```` 
FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"] 
````

3. В `build.gradle` добавить плагин `com.palantir.docker`

4. Задать параметры конфигурации блока Docker:

````
docker {
    name "${project.name}:${project.version}"
    files "$buildDir/libs/${project.name}-${project.version}.jar"
    buildArgs([JAR_FILE: "${project.name}-${project.version}.jar"])
}
````

5. Создать образ докера, прописав команду `./gradlew docker`

6. В `build.gradle` добавить плагин `com.palantir.docker-run`

7. Задать параметры блока dockerRun:

````
dockerRun {
    name "${project.name}"
    image "${project.name}:${project.version}"
    ports '8080:8080'
    clean true
    daemonize false
}
````

8. Запустить указанный образ с указанным именем, прописав команду `./gradlew dockerRun`

[ПОДРОБНЕЕ О ПЛАГИНАХ](https://github.com/palantir/gradle-docker.git)
