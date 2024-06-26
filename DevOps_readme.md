# Group 4 Smarthome Project

This project is a SmartHome management system developed by Group 4 using Java, Spring Boot, Maven, JavaScript and React.
The project is divided into two parts: the backend and the frontend. The backend is a RESTful API developed using Spring
Boot and Maven. The frontend is a single-page application developed using React.

## Table of Contents

- [Group 4 Smarthome Project](#group-4-smarthome-project)
  - [Table of Contents](#table-of-contents)
  - [Project Setup](#project-setup)
    - [Backend](#backend)
    - [Frontend](#frontend)
  - [Project Deployment](#project-deployment)
    - [Database Setup](#database-setup)
    - [Server Setup](#server-setup)
    - [Backend Deployment](#backend-deployment)
    - [Frontend Deployment](#frontend-deployment)
  - [Issues Encountered](#issues-encountered)

# Project Setup

## Backend

Because the project was developed and run locally, the first step was to edit the application.properties and pom.xml
file to adapt to the new database and server. The application.properties file was edited to connect to the MariaDB
database and the pom.xml file was edited to include the correct dependencies.
To do so, we added the following dependencies to the pom.xml file:

```xml

<dependency>
    <groupId>org.mariadb.jdbc</groupId>
    <artifactId>mariadb-java-client</artifactId>
    <version>LATEST</version>
</dependency>

<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-tomcat</artifactId>
<version>3.2.4</version>
</dependency>
```

We also added the following properties to the application.properties file:

```properties
spring.datasource.url=jdbc:mariadb://vs1265.dei.isep.ipp.pt:3306/SmartHome
spring.datasource.username=smarthome
spring.datasource.password=smarthome
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect
spring.jpa.hibernate.ddl-auto=update
```

The **spring.jpa.hibernate.ddl-auto** was set to *create-drop* initially but was later changed to *update* to avoid
losing data when the application is restarted.
The **spring.datasource.url** was set to the MariaDB database URL, the **spring.datasource.username** was set to the
username of the database and the **spring.datasource.password** was set to the password of the database. All of these
properties were after we initialized the MariaDB database and created the user with all privileges. These steps will be
explained in the next section.

To host the application in Tomcat, a war file was needed. To generate the war file, we added a final change in the
pom.xml file:

```xml

<packaging>war</packaging>
```

Finally, in a terminal, we ran the following command to generate the war file:

```bash
mvn clean package
```

This command generates a war file in the target folder of the project.

## Frontend

The frontend was developed using React and because it was developed locally, all the *fetch* links were pointing to localhost. To deploy the frontend, we had to change all the fetch links to point to the backend. To do so, we created a environment variable to set the fetch links in the frontend code to point to the backend. The environment variable was set in the .env.demo file in the root of the frontend project:

```bash
REACT_APP_BACKEND_API_URL=http://10.9.24.206:81/api
```

After setting the environment variable, we changed all the fetch links in the frontend code to point to the environment variable.
Then we installed the -env-cmd package to read the environment variable and set the fetch links in the frontend code. To do so, we ran the following command in the terminal:

```bash
npm install env-cmd
```

Lastly, we changed the package.json file to include the following line:

```json
"build": "env-cmd -f .env.demo react-scripts build",
```

This line was added to the scripts section of the package.json file. This line tells the frontend to read the environment variable and set the fetch links in the frontend code to point to the backend.
To build the frontend, we navigated to the root of the frontend project and ran the following command in the terminal:

```bash
npm run build
```

Thus generating the static files of the frontend in the build folder of the frontend project.

# Project Deployment

## Database Setup

A template in the DEI Server was used to create the container running the MariaDB database. In this we used template
number 29, which is a MariaDB image.

After the container was started, another template was used, template number 25, with an Adminer 4.8.0 image, which
allowed us to access the MariaDB database and create the database and user.
The database was named SmartHome and the user was named smarthome. The user was given all privileges on the SmartHome
database.
After these steps, we could access the database container and copy the database URL, username and password to the
application.properties file.

## Server Setup

To set up the server, another template was used, template number 63, with an Ubuntu 20.04 image. This template was used
to host the backend and frontend applications.
After the container was started, Tomcat and Nginx were manually installed to host the backend and frontend applications,
respectively.

## Backend Deployment

the war file was previously generated locally and copied to the Ubuntu container via SSH. The war file was copied to the
webapps folder of the Tomcat server. After the war file was copied, the Tomcat server was started and the backend
application was running.
To start the Tomcat server, is was necessary to navigate to the opt/tomcat/webapps directory and the following command
was used:

```bash
./startup.sh
```

With Tomcat running, the backend application was accessible at the following URL:

```bash
10.9.24.206:8080/smarthome
```

However, since no frontend was running, the application was not fully functional and was only accessible via Postman.

## Frontend Deployment

The frontend static files were previously created in the Project Setup section. After installing Nginx, a directory was
created in /var/www/ called smarthome.
To deploy the frontend, the static files were copied to the /var/www/smarthome directory of the Ubuntu container via
ssh. After the frontend application was copied, the Nginx server was started and the frontend application was running.
To start the Nginx server, the following command was used:

The last step was to connect the frontend to the backend, so that any request that reached the frontend was redirect to
the backend. To do so, the default configuration file of Nginx was edited. The file was located in
/etc/nginx/sites-available/default. The following lines were added to the file:

```nginx
server {
listen 81;
listen [::]:81;

       server_name localhost;

       root /var/www/smarthome;
       index index.html;

       location /api {
        add_header 'Acess-Control-Allow-Origin' 'http://10.9.24.206:81';
        add_header 'Access-Control-Allow-Methods' 'GET, POST, PATCH, PUT, DELETE, OPTIONS';
        proxy_pass http://10.9.24.206:8080/api;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        try_files $uri $uri/ =404;
       }
}
```

This file contains a lot of information, let's break it down step by step:

- The server block is used to define the server configuration. The listen directive is used to define the port where the
  server will listen for requests. In this case, the server will listen on port 81.
- The server_name directive is used to define the server name. In this case, the server name is localhost.
- The root directive is used to define the root directory where the static files are located. In this case, the root
  directory is /var/www/smarthome.
- The index directive is used to define the default file that will be served when a directory is requested. In this
  case, the default file is index.html.
- The location block is used to define the location of the API. In this case, the location is /api (which was already
  defined in the application.properties of the backend).
- The add_header directive is used to add headers to the response. In this case, the headers Access-Control-Allow-Origin
  and Access-Control-Allow-Methods are added.
- The proxy_pass directive is used to pass the request to the backend. In this case, the request is passed
  to http://10.9.24.206:8080/api.
- The proxy_set_header directive is used to set the headers of the request that will be passed to the backend.
- The try_files directive is used to define the files that will be served when a request is made.

After editing the file, the following command was used to restart Nginx:

```bash
sudo systemctl restart nginx
```

With Nginx running, the frontend application was accessible at the following URL:

```bash
10.9.24.206:81
```

The application was now fully functional and could be accessed by anyone with the URL, the only requirement is a VPN
connection to the DEI network.

# Issues Encountered

During the deployment of the app a lot of issues were encountered. The first issue was the deployment of the backend. The first strategy was to use Docker containers to host the backend, frontend and the database. However, we discovered that the templates available in the DEI server were Docker containers themselves, and running a container inside a container did not allow the containers running our app to access the web, making it impossible for them to clone the repository, package both the backend and frontend and deploy the app.
To address this situation, we attempted to create a single container for the backend and frontend and clone the repository directly there to build and package it. Unfortunately we found that the container did not have enough storage and processing capability to build both the backend and frontend, which required a lot of memory to build.

Another issue encountered was the communication between the backend and frontend. Since our frontend was using Routes to navigate between the different pages and Tomcat 10 does not support routing, we had to use Nginx to redirect the requests to the backend. This was a challenge because we had never used Nginx before and had to learn how to configure it to redirect the requests to the backend.

