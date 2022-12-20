# Take-home Assignment (Full Stack) #

### Goal: ###
* Create an Employee Table like the one in the image below.
* Use any front-end framework (Angular, React, Vue, ect...) or CSS library to accomplish this.
* Implement the back-end API with a Node.js framework, and a SQL database

![](example.png)

### Functional Requirements: ###
* Initially, list all employees that are in data.json
  * First name, last name, and salary in currency format (ie. $42,000)
* The ability to edit an employee
* The ability to delete an employee
* The ability to create a new employee

### Technical Guidelines: ###
* Structure your application and components in a modular/reusable way
* Commit code with useful and informative comments
* Your application doesn't have to use the data.json file directly, but have a SQL script to initialize your database with data found in that file
* Implement API code to read and write to a SQL database
* Styling: CSS or SCSS or SASS can be used, whichever you prefer (can use popular UI frameworks like Bootstrap as well)

### Questions? ###
Please reach out to me with any questions

### Testing ###
Manually create a MySQL databased named "db_ecapital"
Use "root" as the user
Change the password to "assignment"
If other username/password combinations are used, please change application.properties accordingly
spring.jpa.hibernate.ddl-auto is set to "create-drop" which will always create a fresh database upon lanuching. Change it to "update" to avoid re-create and drop of the database upon lanuching and closing.

The backend is written with Spring
Make sure the testing environment supports Maven and Java 17
Use "./mvnw spring-boot:run" to run the API on port 8080

The frontend is written with React
Make sure the environment supports npm
Run "npm install" then run "npm start" to server the frontend on port 3000

