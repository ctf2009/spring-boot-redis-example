# spring-redis-example
An example of using Spring Boot with a Redis Store

If you have run up the Docker Compose file in the testing directory, you can connect to the Redis server using the following

docker run -it --net=host redis redis-cli -h localhost

Some simple commands

##### Gets all Keys
`keys *`

##### Get All Author Keys
`keys message:author*`

##### Get All message ids by author
`smembers message:author:<author>`

##### Get Message from id
`hgetAll message:<id>`