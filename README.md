# Code Challenge – Back-End Software Developer @ TUI 

Hi!

We’re looking forward to meeting you! 

To give you a chance to get a feeling of the work you will be doing in our team
and for us to get a good impression of the coding skills you will add to our team, we have prepared for you a nice
code challenge. 


## General instructions

The challenge is a simplified version of tasks this role would work on a day-to-day basis.
*Your project may be implemented using one of our templates (Spring Boot or Micronaut) or you may create it from scratch,
however in this case your project must provide a Dockerfile and instructions on how to build the image.*

We would like to give some hints on what we value the most in your submission. We would appreciate:
- Well tested code
- A working solution (done is better than perfect)
- No over-engineering
- Clean code and architecture
- Clean and small git commits (you might also share a public git repo with us, but you must also provide a zip file)
- We provide build instructions to run the application, if there is any extra step to run your code/test, please let us know

Note: You are encouraged to submit what you have managed to prepare. It doesn’t have to be perfect!

## Prerequisites

*Mandatory*
- Docker
- Docker-compose
- Java JDK 17+ or equivalent
- Gradle 8+ or Maven 3
- IDE

*Optional, our templates are compatible with:*
- Java JDK 17 or equivalent
- Gradle 8

## Technical instructions

We would highly appreciate if you could do the challenge using Java or Kotlin and Spring Boot or Micronaut as these
are the languages and frameworks relevant to the position and would give us a better picture of the necessary skills.

You will have a mongodb already loaded with data.

### Functional requirements:
The goal of this code challenge is to create an API that retrieves quotes from a MongoDB using a framework that is either provided or of your own choice.

- The app should be able to return a specific item based on an ID.
- The app should be able to return a list of items by author. 
- The app should be able to return all items in that collection.

### Non functional requirements:
- The app must be able to support 50 requests per second in up to 200 milliseconds for the search functionality by ID
- The app must be able to support 50 requests per second in up to 200 milliseconds for the search functionality by author
- The app must be able to answer in less than 30 seconds for the functionality to search all items in this collection.

The collection will have the following format:
``` json
{
 "_id": "5eb17aaeb69dc744b4e72a4a",
 "quoteText": "The first rule of any technology used in a business is that automation applied to an efficient
operation will magnify the efficiency. The second automation applied to an inefficient operation will magnify the
inefficiency.",
 "quoteAuthor": "Bill Gates",
 "quoteGenre": "business",
 "__v": 0
}
```

### Step 0:
Pick **one** of the templates available for the challenge (**Spring Boot (Java)** or **Micronaut (Kotlin)**) or you may 
create a containerized application from scratch using a language and framework of your taste.

We are providing the template ready to use Kotlin or Java, but feel free to use another language if you prefer so.

#### Instructions to test the template: 

1. From the selected template root directory run:

    | Template    | Command                      |
    |-------------|------------------------------|
    | Spring-Boot | `gradlew clean build`<br/>`gradlew docker`     |
    | Micronaut   | `gradlew dockerBuild`        |

    Will generate a docker image in your local docker repository.


2. From the deployment directory:

    Run `docker-compose up -d`

*You should have now a populated mongodb and a rest api running!*

There is a postman collection in the template that can help you to test the api.

Please check that it works as expected:
- MongoDB container up and running, also containing a challenge DB with almost 73k quotes.
- An endpoint of test available at http://localhost:8080/hello

Optional: Create an initial commit to the repo where you will upload the solution.

### Step 1:
Accomplish the task 🙂

We will pay attention to the commit messages (if you provide a public repo), the architecture, the tests, etc. 
So feel free to organize the project according to your preferences. 

### Step 2:
You should reply us with an email containing a zip file with your final response to the challenge. *Be sure to do that
before the deadline*.
If possible, please provide us the requests you have used for tests (either collection (Postman), or cURL command, etc).

We look forward to reviewing your solution.
Have fun and good luck! 
