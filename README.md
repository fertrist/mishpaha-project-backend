# mishpaha-project-backend

To run this you need java 8 installed. https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html
To run this you need maven installed. http://maven.apache.org/install.html

Launch API:
1. clone project
2. cd to {project root directory}
3. execute
#mvn clean install
#mvn package
war file will be genereted as {project root directory}\target\my-app.war
4. cd {project root directory}\target
5. execute
#java -jar my-app.war

application will run on port 8080. To login use admin/password credentials.

For now the following endpoints are ready:
GET 
/security/user - check if you are authenticated
/security/resource - simple greeting endpoint
/people/group/{id}/info - detailed info on group members
/people/group/{id}/summary - detailed info on group members
/events/group/{id} - retrieve all events for a given group (by default time range is now() minus 1 month till now() + 2 weeks)
/reports/group/{id} - get detailed report for a given group (by default time range is now() minus 3 month till now(),
start and end adjusted so they point to week start and end i.e. monday and sunday respectively)

POST (might won't be allowed for now because of security config)
/events/event - save event. Input json data should look something like
{"personId":1,"groupId":1,"typeId":1,"happened":[2016,5,17],"comment":null}
for happened it should be possible to pass smth like '2016-05-17'
/people/person - create new person

DELETE  (might won't be allowed for now because of security config)
/events/event/{id}
/people/person/{id}

PUT
/events/event/{id} - useful only to update a comment
/people/person/{id} 
