# Test Client Framework

This is a framework designed to work with TCP, HTTP, and WebSocket clients. It is meant to be extendable to other servers. 
It currently is being tested against the SCIM microservice.

## SCIM Overview

    HTTP server on port 8080
    – One Endpoint at /number that takes a query Parameter with the key
    “number” and a double value as the param. It will return the square
    root of this number
    • TCP server on port 9000
    – Takes in a number and will return the power of 2 of the same number.
    Each response starts with the character “%” and ends with the
    character “&”
    • WebSocket Server on port 8090
    – takes a number and will return the cube root of the same number
    
## Flags

Some inputs will result in random flags being thrown. For example, an input of 64 to the HTTP server on port 8080 can 
result in the flag `8.0::ctf_ONE_TWO_PUNCH` being thrown. Some unit tests include these flags which cause the tests to 
fail intermittently.    


## Running SCIM microservice

`docker run --name scim -d -p 8080:8080 -p 9000:9000 -p 8090:8090 scims:0.0.1-SNAPSHOT`