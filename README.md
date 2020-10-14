# Laboratory No. 1 - Network Programming
###### Student: Cocieri Cristina
###### Group: FAF-181

### Main Tasks
```
​1. Pull a docker container (alexburlacu/pr-server) from the registry and run it.
2. Access the root route of the server and find your way to /register.
3. The access token that we get after accessing the /register route must be put in a HTTP header of the subsequent requests under the key X-Access-Token key.
4. Extract data from data key and get next links from link key.
5. Convert the fetched data to a common representation.
6. Create concurrent TCP server, serving the fetched content, that will respond to a column selector message, like `SelectColumn column_name`.
```
#### Description
This program is able to collect all the data from Client HTTP Server and transfer it to a TCP Server, in which this data can be easily accessed.
#### How to run?
1) Pull the docker container using this command: 
```-docker pull alexburlacu/pr-server```
2) Run the local registry using this command: 
```docker run -p <PORT>:<PORT>```
3) Run the program.
4) Configure HTTP & TCP ports in *config.properties file*.
5) Connect to TCP Server : 
```telnet <IP ADDRESS OF SERVER PC> <PORT>```


   






