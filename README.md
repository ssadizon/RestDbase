    <!--
    Licensed to the Apache Software Foundation (ASF) under one
    or more contributor license agreements.  See the NOTICE file
    distributed with this work for additional information
    regarding copyright ownership.  The ASF licenses this file
    to you under the Apache License, Version 2.0 (the
    "License"); you may not use this file except in compliance
    with the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.
-->
# Apache Karaf REST Example 

## Abstract

This example shows how to use JAX-RS to implement a REST service.

It implements a `BookingService` with a REST implementation. 

The "client" bundle uses the `BookingService` with a REST client stub.

This demonstrates how two different bundles communicate through `Service Registration`

## Significant Artifacts

* **karaf-rest-example-api** is a common bundle containing the `Booking` POJO and the `BookingService` interface.   
* **karaf-rest-example-websvc** is a bundle providing the `BookingServiceRest` implementation of the `BookingService` interface. It uses JAX-RS.
* **karaf-rest-example-dbase-connector** is a bundle using the `BookingService`. It uses pax-jdbc-derby as driver.
* **karaf-rest-example-features** provides a Karaf features repository used for the deployment.

## Build

The build uses Apache Maven. Simply use:

```
mvn clean install
```

## Launch Karaf

Before anything else, you must first launch Apache Karaf. Navigate to Apache Karaf directory and invoke `bin/karaf`

Note: There may be some instances that there might be some bundle installation issues. 
These are bundle cache issue. To fix that, add clean to the launch command i.e `bin/karaf clean`

## Feature and Deployment

On a running Karaf instance, register the features repository using:

```
karaf@root()> feature:repo-add mvn:org.apache.karaf.examples/karaf-rest-example-features/4.2.11/xml
```

NOTE: There is an existing issue regarding missing dependencies even though they are already defined in the `features.xml` file. Work around for this is to unpack `DependencyJars.zip` in `{KARAF_HOME}/deploy`

Then, you can install the application:

```
karaf@root()> feature:install karaf-rest-example-app
```

If using JPA, run this instead
```shell
karaf@root()> feature:install datasource
karaf@root()> feature:install karaf-rest-example-app-jpa
```

## Basic Authentication

This example uses Basic Authentication to secure all its API calls.

```
Credentials
	- username: karaf
	- password: karaf
```

## Usage

Once you have installed the application, launch your preferred API client tool(during the development, we used Postman) to test the following APIs.

```
List all bookings
Method: GET
URL: http://localhost:8181/cxf/booking
Response: List of all bookings(initally empty)
Response Status Codes:
	- 200: Successful
	- 401: Unauthorized(Credentials are not valid)
```

```
Add a booking
Method: POST
URL: http://localhost:8181/cxf/booking
Request Body: {"customer": "<customer_name_here>","flight": "<flight_no_here>"}
Response: N/A
Response Status Codes:
	- 200: Successful
	- 401: Unauthorized(Credentials are not valid)
```

```
Select a booking
Method: GET
URL: http://localhost:8181/cxf/booking/{id}
Response: N/A
Response Status Codes:
	- 200: Successful
	- 401: Unauthorized(Credentials are not valid)
```

```
Select a booking
Method: DELETE
URL: http://localhost:8181/cxf/booking/{id}
Response: N/A
Response Status Codes:
	- 200: Successful
	- 401: Unauthorized(Credentials are not valid)
```

To check if the actions and records do reflect on the database, you can do a simple database query.
```
jdbc:query jdbc/karaf-example "select * from karaf_example.booking"
```

## Micrometer

Micrometer has been integrated to monitor this application. Metrics are being sent to an InfluxDB.

```
URL: http://172.16.13.13:8086/
username: admin
password: keybridge123
bucket: micrometer
organization: Keybridge Wireless
```