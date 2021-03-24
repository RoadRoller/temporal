# temporal sandbox

Check out https://github.com/temporalio/docker-compose and boot up temporal with

```
docker-compose -f docker-compose-postgres.yml up
```

Run SandboxApplication and to invoke workflow call a REST endpoint 
```
http://localhost:8080/api/simple/25
```
If last parameter is integer it will be simulated task duration in seconds.

Run WorkerApplication to add a new temporal worker.
