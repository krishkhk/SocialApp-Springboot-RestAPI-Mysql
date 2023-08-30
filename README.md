# SocialApp-Springboot-RestAPI-Mysql

#Created Springboot Application And Added SpringData JPA and Spring Web Dependencies.
#Entity class : User,Post,Comment.
#Repository   : UserRepo,PostRepo,CommentRepo.
#Controllers  : UserController, PostController, CommentController.

#Database in properties file.
spring.datasource.url = jdbc:mysql://localhost:3306/social?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false
spring.datasource.username = root
spring.datasource.password = java
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto = update

---------------------#RestAPI's End Points here,---------------------------------------
=========================================================================================
User Class :
=========================================================================================
Delete Id :  http://localhost:8080/users/6
Get Specific Id: http://localhost:8080/users/user/6
put Mapp : http://localhost:8080/users/update/5
Patch method:http://localhost:8080/users/updateRequired/55
Post Method : http://localhost:8080/users/create
Get Method : http://localhost:8080/users/list
Pay load :

{
    "userName": "john_doe",
    "email": "john@example.com",
    "contactNumber": "123-456-7890",
    "dateOfBirth": "1990-01-15",
    "address": "123 Main St, City"
}
==========================================================================================
POST class:
===========================================================================================
update details putmapping : http://localhost:8080/post/updatedPost/10
payload : 

{
    "title": "The Titel Updated Successfullly ",
    "content": "Putmapping performing",
    "postedAt": "2023-08-17T13:41:00.000Z",
    "author": {
        "id": 5
    },
    "comments": []
}

Get specific id : http://localhost:8080/post/post/4
Post Method : http://localhost:8080/post/createpost
Get Method : http://localhost:8080/post/list
Pay load :

{
    "title": "First Inst Post",
    "content": "Content Realted to Social Media.",
    "postedAt": "2023-08-17T15:30:00.000Z",
    "author": {
        "id": 1
    }
}
=======================================================================================
Comment Class:
========================================================================================

Comments find by Post Id : http://localhost:8080/comments/commentsByPost/12
Post Method : http://localhost:8080/comments/postComment
Get Method : http://localhost:8080/comments/list
Pay load :

{
    "text": "Its nice to see buddy.",
    "commentedAt": "2023-08-17T15:59:00.000Z",
    "author": {
        "id": 1
    },
    "post": {
        "id": 4
    }
}

------------------------------- Added Loggers---------------------------------------------

<!-- https://mvnrepository.com/artifact/ch.qos.logback/logback-core -->

---------------------------------- Bycrpted Password--------------------------------------

<!-- https://mvnrepository.com/artifact/org.springframework.security/spring-security-crypto -->

--------------------------------  Connected Project to SonarQube ----------------------------------------
1. Download  SonarQube Windows and Extract.
2. GoTo bin -> startSonar --> localhost:9000 login:admin password:admin its default after that can be change.
3. add Sonar and Jacaco Maven Plugins
4. Run as Maven Build Set Goal : clean org.jacoco:jacoco-maven-plugin:prepare-agent install    (For jacaco test reports)
5. Got Sonar Dashbaord -> Myprofile -> Security -> Generate Key ->
6. Run as Maven Build set Goal : sonar:sonar -Dsonar.login=####### Generated Key Here ########################
7. After Refresh adn Goto Projects In SonarQube Home page.Springboot Project Will appears !!!!!.

