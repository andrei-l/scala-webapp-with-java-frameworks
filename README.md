# Scala WebApplication With Java Frameworks

This is a sample application of Scala/Java tools and frameworks combination:
 * SBT v0.13.9
 * Scala v2.11.7
 * Java v8
 * Hibernate v5.1.0.Final
 * Spring Boot v1.3.1.Release
 * H2
 * Spring Test


This webapp represents a set of REST endpoints which are used to store, fetch and update information [CRU]

## Supported requests
### POST /info [store]
##### Request body example:
````
    {
        "label": "sample",
        "main_details": "pin code: ****",
        "comments": "this is super important info"
    }
````

### GET /info [fetch]
##### Response body example:
````
    {
        "updatedDate":"2016-02-14T18:07:04",
        "createdDate":"2016-01-14T18:07:04",
        "label":"sample",
        "comments":"this is super important info",
        "main_details":"pin code: ****"
    }
````

### PUT /info [update]
##### Request body example:
````
    {
        "label": "sample",
        "main_details": "pin code: ****",
        "comments": "pin code has changed!"
    }
````

## Application Details
 * To run this web application call the main method of ````com.github.swwjf.WebServicesApplication````.
This will run embedded Tomcat with embedded H2 instance. Hibernate will automatically create schema if it is a first run
 * A number of component tests are located in ````com.github.swwjf.ws.InfoEndpointTest````.
 They CRU information. Mock data for fetch and update operations is populated with DBUnit.
