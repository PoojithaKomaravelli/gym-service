### Description
gym-service is a SpringBoot Microservice which can be used to retrieve suggestions on a "workout class".The Api expects an input 
'target time of day' as input for a chain of 3 gyms around Los Angeles with an existing schedule of classes every 2 hours from 9am-5pm, throughout the day.

The caller's location can optionally be supplied via querystring parameters 'latitude' and 'longitude' to help improve relative scores.

## pre-requisites
1. Java- JDK,JRE
2. Gradle
3. any IDE
4. Postman

## How to run the project
1. Navigate to the project root folder
2. Run the command 'gradlew bootRun'
3. You should see application started message





