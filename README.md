# CapiasManagementService
Capias Management Service

# Front-End
- Java Script
- JQuery (제외 예정)
- Vue.js (추가 예정)
- Mustache
- HTML5 & CSS5

# Back-End
- Java 17
- JPA
- MySQL 8

# Framework
- Spring Boot
- Gradle
- BootStrap 5.1
- Hibernate

# Security
- Spring Security
- SSL Cipher Suit
- JWT

# 개발환경설정
+ IDE를 설치합니다. (이후, 가이드는 Eclipse를 기준)
  + Eclipse: https://www.eclipse.org/downloads/
  + IntelliJ: https://www.jetbrains.com/ko-kr/idea/download/
+ IDE의 내장된 SDK가 없다면 Java 17 버전을 설치합니다.
  + Java 17: (추후 링크 추가)
+ Lombok을 설치합니다.
  + Lombok: https://projectlombok.org/downloads/lombok.jar
  + 위에서 다운받은 lombok.jar 파일을 IDE가 설치된 폴더에 넣습니다.
    + ex> c:\dev\workspace\eclipse\lombok.jar
  + lombok.jar 파일을 실행합니다.
    + #] java -jar c:\dev\workspace\eclipse\lombok.jar
+ IDE를 실행합니다.
+ import Project로 Git Repository의 주소에서 프로젝트를 다운받습니다.
+ 다시 import를 실행하고 Gradle - Existing Gradle Project를 체크하고 Next.
+ 프로젝트가 있는 root directory를 설정하고 Next.
+ Gradle 프로젝트가 정상적으로 build가 되면 Finish.
+ 프로젝트를 우클릭하고 Gradle - Refresh Gradle Project.
+ CMS는 기본적으로 local, dev, staging, prod Environment가 있습니다. 아래와 같이 Run Configuration을 설정합니다.
  + (Spring Boot App - Arguments - Spring Boot)
    + local: Profile을 local로 합니다.
    + dev: Profile을 dev로 합니다.
    + staging: Profile을 staging로 합니다.
    + prod: Profile을 prod로 합니다.
  + (Spring Boot App - Arguments - Program Arguments)
    + local: -Dspring.profiles.active=local
    + dev: -Dspring.profiles.active=dev
    + staging: -Dspring.profiles.active=staging
    + prod: -Dspring.profiles.active=prod
+ 원하는 Environment를 선택하고, Run으로 스프링부트 프로젝트를 실행합니다.
