# Search
1.) Make sure you have Java 1.8 installed in your system and you have a Java IDE like Eclipse to run this application.
2.) Build the project using Maven (make sure Maven is Installed.) - Using command: mvn clean install -DskipTests
3.) Now you can run the application as "run as spring boot application" using IDE support.
4.) Once application is ready, go to h2 console to check the DB, url of which is "jdbc:h2:mem:searchDB", username: sa and no password... Make sure to change the datatype of column cast and description to varchar (2000). Since default 255 wont suffice for this dataset.
5.) Make sure to call POST call to http://localhost:8080/uploadCsv from postman with body as file with the file of the csv dataset. In our case it is netflix_titles.csv.
6.) Once we have data in the system, we can call our http://localhost:8080/search API with the query param fields cast or director, also pagination fileds like page and size.
