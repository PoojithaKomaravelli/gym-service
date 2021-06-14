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
1.Navigate to the project root folder
2. RUn the command 'gradlew bootRun'
3. You should see application starting on 8081 port

## Test the service locally
Postman is an application which can be used to test our applications from our machine. For more information on Postman and how to use it,please visit
<a href="https://learning.postman.com/" target="_blank">Postman</a> .
Once the SpringBoot Application starts to run successfully,use Postman's GET request to hit the below endpoint to see sample result

http://localhost:8081/api/gym-service/suggestion?date=1623768587000



