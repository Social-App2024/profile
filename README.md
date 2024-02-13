# profile
 
Profile microservice, responsible for handling Thaqafa profiles, including following APIs:
- Creating new Profile
  (POST - http://localhost:9094/profile/profile)
- Find details about the Following List (Username, userid)
  (POST - http://localhost:9094/profile/following/{offset}), offset default: -1
- Find details about the Followers List (Username, userid)
  (GET - http://localhost:9094/profile/followers/-1?userId=1)
- Get Profile Details
  (POST - http://localhost:9094/graphql)
- Follow a user
  (GET - http://localhost:9094/profile/follow/{userid-to-follow}?profileId={follower-id})

Complete APIs calls in the file: Profiles.postman_collection.json
The microservice is developed in Spring Boot, GraphQL and mongo DB.

To run the microservice:
- Install JDK 17
- Create a blank DB in mongo, name it "social"
- Run the service discovery Eureka server in this repository: https://github.com/Microservices-Restaurant-App/eureka.git
- Open the project from IDE (Intellij or Eclipse STS)
- run ProfileApplication.java, and access the APIs from above urls
