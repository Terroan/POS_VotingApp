# POS SEMESTER PROJECT 2024

*Daniel Jessner 4AHINF*

The second semester of the 4AHINF of the HTL Saalfelden for computer science was dedicated to the development of a project, whereby the students were allowed to choose a suitable topic and work independently on everything from development to problem solving to detailed documentation.

My personal project is a **voting app**. What it is and what it involves is explained below:


## Voting App
The voting app is a software that allows users to create a new or log into an existing account and store **voting sessions** on that account. They can create sessions where they add their questions and answer options. After a session is safed, they can start it and give the random generated string to other people which can join this session, look at the questions and give their answers to it. In the end, the session owner ends the session and the given answers are visually displayed. Created sessions can be updated or deleted anytime.

If you were to, you could see it as fan project of **Kahoot.it**


## Softwaredesign
The software itself comprises three areas:
- **Client (C# and XAML)**
- **Client (HTML, CSS and Javascript)**
- **Server (Java, Spring Boot)**

Each of the clients sends data to the server (API), which processes it (updating in database if needed) and sends back a corresponding response. The whole thing can be represented in the architecture as following graphs show:

### Component Diagram
The component diagram below illustrates the high-level architecture of the voting app. It shows how the different clients (desktop and web) communicate with the API server, which uses JDBC to interact with the MongoDB database.

```mermaid
graph TD;
    Client1["Client (C# and XAML)"] -->|HTTP| API["API (Spring Boot)"];
    Client2["Client (HTML, CSS and JavaScript)"] -->|HTTP| API["API (Spring Boot)"];
    API -->|API| Backend;
    Backend -->|MQL/JDBC| Database;
```
### Sequences
#### Sequence 1 - LogIn
The sequence diagram below shows the process of a user logging into the voting app, detailing the interactions between the user, client, API, and database.

The sequence diagram below shows the process of a user logging into the voting app, detailing the interactions between the user, client, API, and database. It includes alternative paths for both successful and unsuccessful logins.

```mermaid
sequenceDiagram
    participant User
    participant Client
    participant API
    participant Database
    
    User->>Client: Enter login credentials
    Client->>API: POST /user/login
    API->>Database: collection.find(user and password)
    alt Login successful
        Database-->>API: User data (valid)
        API-->>Client: Login success
        Client-->>User: Display login success message
    else Login failure
        Database-->>API: No user data (invalid)
        API-->>Client: Login failure
        Client-->>User: Display login failure message
    end
```

#### Sequence 2 - SignIn
The sequence diagram below depicts the process of a user signing up for the voting app, detailing the interactions between the user, client, API, and database. It includes alternative paths for both successful and unsuccessful signups.

```mermaid
sequenceDiagram
    participant User
    participant Client
    participant API
    participant Database
    
    User->>Client: Enter signup details
    Client->>API: POST /user/create
    API->>Database: collection.put(user, password)
    alt Signup successful
        Database-->>API: Success
        API-->>Client: Signup success
        Client-->>User: Display signup success message
    else Signup failure
        Database-->>API: Failure (e.g., username already exists -> HTTP 401)
        API-->>Client: Signup failure
        Client-->>User: Display signup failure message
    end
```
#### Sequence 3 - Create Session

#### Sequence 4 - Update Session

#### Sequence 5 - Start Session

#### Sequence 6 - Join Session

#### Sequence 7 - End Session

### Class Diagram

### Activities
#### Activity 1 - Join Session
```mermaid
    flowchart TD
        A[User logs/signs in] --> C[Joins session]
        C --> D[Views questions]
        D --> E[Submits answers]
        E --> F[Session owner ends session]
        F --> G[Results are displayed]
```
#### Activity 2 - Create and start Session
```mermaid
    flowchart TD
        A[User logs/signs in] --> C[Creates session]
        C --> D[Selects session]
        D --> E[Starts session]
        E --> F[Waits for answers]
        F --> G[Ends session]
        G --> H[Answers are displayed]
```
#### Activity 3 - Update Session
```mermaid
    flowchart TD
        A[User logs/signs in] --> C[Selects session]
        C --> D[Updates session]
```

### Class diagram
Tofill

### Deployment diagram
The deployment diagram below illustrates the physical deployment of components in the voting app, outlining the distribution of clients, server, and database components across different nodes and their connectivity via the internet.

```mermaid
    graph TD;
    Client1["Client (User PC)"] -->|Internet| Server["Backend Server"];
    Client2["Client (User Mobile)"] -->|Internet| Server;
    Server -->|JDBC| Database["Database Server"];

```

## API
The API documentation provides detailed information on the **endpoints, request/response formats, and functionality** of the voting app's backend API. Due to project reasons this API is not online, still, in future, this section could serve as a guide for developers and integrators to interact with the API and utilize its features effectively.

The API covers two main parts: 
- **User Endpoints**
- **Session Endpoints**

Both serve as an interface between user and the application.

### Base URL
The base URL for accessing the API endpoints is:
- users: **/api/user**
- session: **/api/session** (exact one)
- sessions: **/api/sessions** (one or more)

### Authentication
The API does not currently utilize token-based authentication. Developers can perform actions by sending the user's credentials along with the session ID. The server verifies whether the session is associated with the user before processing the request.

### Error Handling
The API returns appropriate HTTP status codes and error messages to indicate the success or failure of a request. Detailed error handling ensures clarity in identifying and resolving issues during API interactions.

### Endpoints
The API exposes various endpoints to perform actions such as **creating sessions, creating users, and posting voting data**. Each endpoint is documented with its purpose, request method, parameters, and response format, additionally showing the java implementation of the endpoint in the API-Controller.

Refer to the following sections for detailed documentation on specific endpoints:

#### Health Endpoint:
Endpoint for debugging reasons that returns "UP" if the server is running and good to go.
<details>
<summary><code>GET</code> <code><b>/api/status</b></code></summary>

##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`        | `server status `                                |

##### Example cURL

> ```javascript
>  curl -X GET -H "Content-Type: application/json" --data http://localhost:5010/api/status
> ```

##### Java
```java
@GetMapping("status")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("UP");
    }
```
</details>

#### User Endpoints
Endpoints that expose routes for handling the voting app users, including the **login** with an existing user and **creating** a new user.
##### Sign up with new user
<details>
<summary><code>POST</code> <code><b>/api/user/create</b></code></summary>

##### Parameters

> | name      |  type     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | None      |  required | object (JSON)   | user data  |


##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`        | `User object `                                |
> | `400`         | `application/json`                | `{"code":"400","message":"Bad Request"}`                            |
> | `409`         | `application/json`         | `{"code":"409","message":"Conflict"}`                                                                |

##### Example cURL

> ```javascript
>  curl -X POST -H "Content-Type: application/json" --data @post.json http://localhost:5010/api/user/create
> ```

##### Java
```java
@PostMapping("user/create")
    public ResponseEntity<VoterIngressDTO> signUp(@RequestBody VoterIngressDTO voterIngressDTO) {
        try {
            boolean tmp = userService.addUser(voterIngressDTO.toVoterEntity());
            if(!tmp)
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            return ResponseEntity.ok(voterIngressDTO);
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
```
</details>

##### Log in with existing user
<details>
<summary><code>POST</code> <code><b>/api/user/login</b></code></summary>

##### Parameters

> | name      |  type     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | None      |  required | object (JSON)   | user data  |


##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`        | `User object `                                |
> | `400`         | `application/json`                | `{"code":"400","message":"Bad Request"}`                            |
> | `401`         | `application/json`         | `{"code":"401","message":"Unathorized"}`                                                                |

##### Example cURL

> ```javascript
>  curl -X POST -H "Content-Type: application/json" --data @post.json http://localhost:5010/api/user/login
> ```

##### Java
```java
@PostMapping("user/login")
    public ResponseEntity<VoterIngressDTO> login(@RequestBody VoterIngressDTO voterIngressDTO) {
        try {
            VoterEntity tmp = userService.checkUser(voterIngressDTO.toVoterEntity());
            if(tmp == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            return ResponseEntity.ok(voterIngressDTO);
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
```
</details>

#### Session endpoints
##### Create a new session
<details>
<summary><code>POST</code> <code><b>/api/session</b></code></summary>

##### Parameters

> | name      |  type     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | None      |  required | object (JSON)   | user data + session data  |


##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `text/plain;charset=UTF-8`         | `Object ID of posted session `                                |
> | `400`         | `application/json`                | `{"code":"400","message":"Bad Request"}`                            |
> | `401`         | `application/json`         | `{"code":"401","message":"Unathorized"}`                                                                |

##### Example cURL

> ```javascript
>  curl -X POST -H "Content-Type: application/json" --data @post.json http://localhost:5010/api/session
> ```

##### Java
```java
@PostMapping("session")
    public ResponseEntity<String> createVotingSession(@RequestBody HttpPostRequest httpRequest) {
        try {
            ObjectId tmp = votingSessionService.create(httpRequest.getVotingSessionDTO(), httpRequest.getVoterDTO());
            if(tmp == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            return ResponseEntity.ok(tmp.toString());
        }
        catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
```
</details>

##### Start an existing session
<details>
<summary><code>POST</code> <code><b>/api/session/start/{id}</b></code></summary>

##### Path variables

> | name      |  type     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | None      |  required | text   | object id of specific session  |

##### Parameters

> | name      |  type     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | None      |  required | object (JSON)   | user data  |


##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `text/plain;charset=UTF-8`         | `Object ID of posted session `                                |
> | `400`         | `application/json`                | `{"code":"400","message":"Bad Request"}`                            |
> | `401`         | `application/json`         | `{"code":"401","message":"Unathorized"}`                                                                |

##### Example cURL

> ```javascript
>  curl -X POST -H "Content-Type: application/json" --data @post.json http://localhost:5010/api/session/start/892134812409324
> ```

##### Java
```java
@PostMapping("session/start/{id}")
    public ResponseEntity<String> startVotingSession(@PathVariable String id, @RequestBody VoterIngressDTO voterIngressDTO) {
        try {
            String tmp = votingSessionService.startSession(new ObjectId(id), voterIngressDTO);
            System.out.println(tmp);
            if(tmp == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            return ResponseEntity.ok(tmp);
        }
        catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
```
</details>

##### End an existing session
<details>
<summary><code>POST</code> <code><b>/api/session/end/{id}</b></code></summary>

##### Path variables

> | name      |  type     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | None      |  required | text   | object id of specific session  |

##### Parameters

> | name      |  type     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | None      |  required | object (JSON)   | user data  |


##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`         | ` Session object with results `                                |
> | `400`         | `application/json`                | `{"code":"400","message":"Bad Request"}`                            |
> | `401`         | `application/json`         | `{"code":"401","message":"Unathorized"}`                                                                |

##### Example cURL

> ```javascript
>  curl -X POST -H "Content-Type: application/json" --data @post.json http://localhost:5010/api/session/end/892134812409324
> ```

##### Java
```java
    @PostMapping("session/end/{id}")
    public ResponseEntity<VotingSessionExgressDTO> endSession(@PathVariable String id, @RequestBody VoterIngressDTO voterIngressDTO) {
        try {
            VotingSessionExgressDTO tmp = votingSessionService.endSession(new ObjectId(id), voterIngressDTO);
            if(tmp == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            System.out.println(tmp);
            return ResponseEntity.ok(tmp);
        }
        catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
```
</details>

##### Post answers to a session
<details>
<summary><code>POST</code> <code><b>/api/session/results/{sessionId}</b></code></summary>

##### Path variables

> | name      |  type     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | None      |  required | text   | session id of specific session  |

##### Parameters

> | name      |  type     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | None      |  required | object (JSON)   | results  |


##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`         | ` posted answers `                                |
> | `404`         | `application/json`                | `{"code":"404","message":"Not Found"}`                            |
> | `401`         | `application/json`         | `{"code":"401","message":"Unathorized"}`                                                                |

##### Example cURL

> ```javascript
>  curl -X POST -H "Content-Type: application/json" --data @post.json http://localhost:5010/api/session/results/ABCDEF
> ```

##### Java
```java
    @PostMapping("session/results/{sessionID}")
    public ResponseEntity<VotingPostDTO> postResults(@PathVariable String sessionID, @RequestBody VotingPostDTO votingPostDTO) {
        try {
            VotingSessionExgressDTO votingSessionExgressDTO = votingSessionService.read(sessionID);
            if (votingSessionExgressDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.ok(votingSessionService.postResults(sessionID, votingPostDTO));
        }
        catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
```
</details>

##### Fetch all sessions for a user
<details>
<summary><code>POST</code> <code><b>/sessions/user</b></code></summary>

##### Parameters

> | name      |  type     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | None      |  required | object (JSON)   | user data  |


##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`         | ` list of sessions for the user `                                |                            |
> | `401`         | `application/json`         | `{"code":"401","message":"Unathorized"}`                                                                |

##### Example cURL

> ```javascript
>  curl -X POST -H "Content-Type: application/json" --data @post.json http://localhost:5010/api/sessions/user
> ```

##### Java

```java
    @PostMapping("sessions/user")
    public ResponseEntity<List<VotingSessionExgressDTO>> readUserSessions(@RequestBody VoterIngressDTO voterIngressDTO) {
        List<VotingSessionExgressDTO> votingSessionExgressDTOs = votingSessionService.readAllByUser(voterIngressDTO);
        if (votingSessionExgressDTOs == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(votingSessionExgressDTOs);
    }
```
</details>

##### Fetch a specific session (join a session)
<details>
<summary><code>GET</code> <code><b>/api/session/{sessionId}</b></code></summary>

##### Path variables

> | name      |  type     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | None      |  required | text   | session id of specific session  |

##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`         | ` session object `                                |                            |
> | `404`         | `application/json`         | `{"code":"404","message":"Not Found"}`                                                                |

##### Example cURL

> ```javascript
>  curl -X POST -H "Content-Type: application/json" --data @post.json http://localhost:5010/api/session
> ```

##### Java

```java
    @GetMapping("session/{sessionId}")
    public ResponseEntity<VotingSessionExgressDTO> readVotingSession(@PathVariable String id) {
        VotingSessionExgressDTO votingSessionExgressDTO = votingSessionService.read(id);
        if (votingSessionExgressDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(votingSessionExgressDTO);
    }
```
</details>

##### Delete a specific session
<details>
<summary><code>DELETE</code> <code><b>/api/session/{id}</b></code></summary>

##### Path variables

> | name      |  type     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | None      |  required | text   | object id of a specific session  |

##### Parameters

> | name      |  type     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | None      |  required | object (JSON)   | user data  |

##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`         | ` true/false `                                |                            |
> | `401`         | `application/json`         | `{"code":"401","message":"Aunathorized"}`                                                                |

##### Example cURL

> ```javascript
>  curl -X DELETE -H "Content-Type: application/json" http://localhost:5010/api/session/892134812409324
> ```

##### Java

```java
    @DeleteMapping("session/{id}")
    public ResponseEntity<Boolean> deleteVotingSession(@PathVariable String id, @RequestBody VoterIngressDTO voterIngressDTO) {
        boolean tmp = votingSessionService.delete(new ObjectId(id), voterIngressDTO.toVoterEntity());
        if (!tmp) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // session id is not in users list
        return ResponseEntity.ok(tmp);
    }
```
</details>

##### Delete all sessions in the repository
<details>
<summary><code>DELETE</code> <code><b>/api/sessions</b></code></summary>


##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`         | ` amount of deleted sessions `                                |                            |                                                             |

##### Example cURL

> ```javascript
>  curl -X DELETE -H "Content-Type: application/json" http://localhost:5010/api/sessions
> ```

##### Java

```java
    @DeleteMapping("sessions")
    public ResponseEntity<Long> deleteVotingSessions() {
        return ResponseEntity.ok(votingSessionService.deleteAll());
    }
```
</details>

##### Update an existing session
<details>
<summary><code>PUT</code> <code><b>/api/session/{id}</b></code></summary>

##### Path variables

> | name      |  type     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | None      |  required | text   | object id of a specific session  |

##### Parameters

> | name      |  type     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | None      |  required | object (JSON)   | updated session data  |

##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`         | ` updated session data + user data `                                |                            |                                                             |

##### Example cURL

> ```javascript
>  curl -X PUT -H "Content-Type: application/json" --data @post.json http://localhost:5010/api/session/892134812409324
> ```

##### Java

```java
    @PutMapping("session/{id}")
    public  ResponseEntity<HttpPostRequest> updateVotingSession(@PathVariable String id, @RequestBody HttpPostRequest httpPostRequest) {
        try {
            boolean tmp = votingSessionService.update(new ObjectId(id), httpPostRequest.getVotingSessionDTO().toVotingSessionEntity(),
                    httpPostRequest.getVoterDTO().toVoterEntity());
            if (!tmp) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // session id is not in users list
            return ResponseEntity.ok(httpPostRequest);
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
```
</details>

### How to use 
To use the API just take a hit at the endpoints and communicate with the application backend. You might want to change some internal configuration following:

#### MongoDB Configuration
This section provides documentation for configuring MongoDB in the Spring Boot application. The configuration class MongoDBConfiguration sets up the MongoDB client using properties defined in the application's configuration file.

Configuration Class
The MongoDBConfiguration class is annotated with **@Configuration**, indicating that it defines beans for the Spring application context.

```java
@Configuration
public class MongoDBConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String connectionString;

    @Bean
    public MongoClient mongoClient() {
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        return MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .codecRegistry(codecRegistry)
                .build());
    }

}
```
##### Explanation
###### Annotations
- **@Configuration**: Indicates that this class contains one or more bean methods that will be managed by the Spring container.
- **@Value("${spring.data.mongodb.uri}")**: Injects the MongoDB URI from the application's properties file into the connectionString variable.

##### Fields
- **java private String connectionString;**: Stores the MongoDB connection string retrieved from the application properties.

Methods
- **@Bean public MongoClient mongoClient()**: Defines a MongoClient bean that will be managed by the Spring container.

Bean Creation
1. CodecRegistry Setup:
- **CodecRegistry pojoCodecRegistry**: Configures a codec registry that automatically handles POJOs.
- **CodecRegistry codecRegistry**: Combines the default codec registry with the POJO codec registry.

2. MongoClient Creation:
- **MongoClients.create(MongoClientSettings.builder()...)**: Creates a MongoClient using the specified settings.
- **applyConnectionString(new ConnectionString(connectionString))**: Applies the MongoDB connection string.
- **codecRegistry(codecRegistry)**: Sets the custom codec registry.

Configuration Properties
Ensure that the MongoDB connection string is specified in your application.properties or application.yml file:

**application.properties:**
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/yourdatabase
```

**application.yml:**
```yaml
spring:
data:
    mongodb:
    uri: mongodb://localhost:27017/yourdatabase
```

##### Usage
By defining this configuration, the Spring Boot application will automatically use the configured MongoClient bean to interact with MongoDB. This setup enables the application to handle MongoDB operations with a properly configured connection and codec registry for POJOs.

##### Summary
The MongoDBConfiguration class provides a way to configure and customize the MongoDB client for the Spring Boot application, ensuring that the application can connect to MongoDB and handle POJOs correctly. This configuration is essential for applications that need to interact with MongoDB in a type-safe and efficient manner.

#### Web Configuration
This section provides documentation for configuring web-related settings in the Spring Boot application. The WebConfiguration class enables Spring's Web MVC framework and configures **Cross-Origin Resource Sharing** (CORS) settings.

##### Configuration Class
The WebConfiguration class is annotated with **@Configuration** and **@EnableWebMvc**, indicating that it defines beans for the Spring application context and enables Web MVC.

```java
@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }
}
```

##### Explanation

Annotations
- **@Configuration:** Indicates that this class contains one or more bean methods to be managed by the Spring container.
- **@EnableWebMvc:** Enables Spring's Web MVC support and configuration.

Implementation
- **public class WebConfiguration implements WebMvcConfigurer:** Implements the WebMvcConfigurer interface to customize the configuration for Spring Web MVC.

Methods
- **@Override public void addCorsMappings(CorsRegistry registry):** Configures CORS mappings.

CORS Configuration
1. addCorsMappings(CorsRegistry registry):
- **registry.addMapping("/"):** Applies CORS configuration to all endpoints.
- **allowedOrigins("*"):** Allows requests from any origin.
- **allowedMethods("GET", "POST", "PUT", "DELETE"):** Allows specified HTTP methods.
- **allowedHeaders("*"):** Allows any headers in CORS requests.

##### CORS Configuration Properties
This configuration allows the application to accept requests from any origin, which can be useful during development. However, for production environments, you may want to restrict allowed origins to trusted domains.

##### Usage
By defining this configuration, the Spring Boot application will automatically apply the specified CORS settings to all endpoints. This setup is essential for enabling cross-origin requests.

##### Summary
The WebConfiguration class provides a way to configure web-related settings, particularly CORS, for the Spring Boot application. By enabling CORS with permissive settings, it allows the application to accept cross-origin requests, facilitating frontend-backend communication in a decoupled architecture. This configuration is crucial for enabling seamless interaction between different parts of the web application.

## Discussion
### Summary
The project for the second term of the HTL Saalfelden for Informatics is finished and provides a functional voting application including:
- WPF Client
- Web Client
- Spring Boot Server

which together form a distributed system that can be seen by users as a simply way to initiate voting sessions. For instance, it can be established as some sort of fast gathering mechanism for opinions in schools, companies, etc...

### Background
The reason for me tho choose a voting app as my project is simple as it sounds: interest. I thought it would be interesting to create an application like kahoot for web and desktop. Additionally it is not something that is finished and use once. No, the project is free to go for innovations and updates and is surely something that brings a use for many people.

### Future
As I have already stated above, it is very likely there will be updates in the future. The application is build in an architecture that allows fast and easy extensions. 

Possible extensions might be:
- User log out mechanismn
- Update user mechanismn
- Option to activate time limit for sessions
- Possibility for users to provide own answers
- Possibility for sessions to include images and/or audio files

## Links
This section contains various tools that were used to create the application or at least helped at it:

### IDEs
- Microsoft Visual Studio 2022:
- Microsoft Visual Studio Code: 

### Technology
- Spring Boot:
- C#:
- HTML5, CSS, Java Script

### Debugging
- Postman:
