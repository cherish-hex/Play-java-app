# Play-Java Web services App using mongo DB

This project demonstrate how to create a simple CRUD Web Services App with Play Java and Mongo DB.

Steps to execute:  
1. cd Play-java-app    
2. sbt  
3. compile - to compile the project  
4. run - to run the application on http://localhost:9000/  
5. test - to run the test suit  

Sample requests:  
1. PUT      http://localhost:9000/add?name=ron&age=5&sex=male  
2. GET      http://localhost:9000/list_pets  
3. POST     http://localhost:9000/update (with x-www-form-urlencoded parameters: name=ron, age=3, sex=male)  
4. GET      http://localhost:9000/get/ron  
5. DELETE   http://localhost:9000/delete/ron  

Some validations:  
1. name must be unique  
2. age must be between 0-30  
3. sex must be either male or female  
