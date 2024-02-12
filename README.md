# profile
 
Profile microservice, responsible for handling Thaqafa profiles, including following APIs:
- Creating new Profile
  (http://localhost:9094/profile/profile)
- Find details about the Following List (Username, userid)
  (http://localhost:9094/profile/following/{offset}), offset default: -1
- Find details about the Followers List (Username, userid)
  (http://localhost:9094/profile/followers/-1?userId=1)
- Get Profile Details
  (http://localhost:9094/graphql)
- Follow a user
  (http://localhost:9094/profile/follow/{userid-to-follow}?profileId={follower-id})

The microservice is developed in Spring Boot, GraphQL and mongo DB.

To run the microservice:
- Install JDK 17
- Open the project from IDE (Intellij or Eclipse STS)
- run the project, and access the APIs from above urls
