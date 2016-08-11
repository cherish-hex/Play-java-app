# Getting started with Play (Java)

This project demonstrate how to create a simple CRUD application with Play.

Steps to execute:
1. cd play-java-intro
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
2. age can be between 0-30
3. sex can be either male or female
