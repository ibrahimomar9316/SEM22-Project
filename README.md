# ROWING APP API

## Description of project

The rowing associations in and surrounding Delft have decided it should be easier to find a team to train with. As it will often happen that someone cancels or that 1 person is missing for a training to continue. To this extend they want us to develop an application that will match available people to trainings and competitions that still require extra people. However, not everybody can join every training although they might be available, the restrictions can be found in Domain specifications

The person responsible for overseeing this project has summarized what they would like to have developed. The system, as they described it, should be a player team matching system.

The bounded context of our implementation is based on the relationship between users, competitions, boats, and certifications. In order to achieve the client's demands, we should divide our implementation of the application into four parts: Rower/Administrator (User), Competition/Training (Event), Certificate, and Authentication.

We would like to show a quick summary of each microservice's functions to grasp the concept behind our idea:
- The User microservice is responsible for storing and managing user information such as gender, preferences, and certificates. It allows users to modify their own data and enables the creation of new user accounts. The user microservice handles all operations related to user management, such as creating and deleting user accounts, as well as modifying user information.
- The Authentication microservice is responsible for handling user authentication and authorization, ensuring that only authorized users have access to the application. This includes allowing users to only modify their own events and not those of other users.
- The Event microservice manages the creation and management of rowing events (training and competition), including registering participants, disjoining from events, and creating your events.
- The Certificate microservice manages digital certificates for joining rowing events allowing for faster and easier filtering of correct events a user can join.


## Group members

| Name        | Email                        |
|-------------|------------------------------|
| Radu-Stefan Ezaru | R.Ezaru@student.tudelft.nl |
| Cristian Soare | C.Soare@student.tudelft.nl |
| Jan Bryczkowski | J.M.Bryczkowski@student.tudelft.nl |
| Wiktor Grzybko | W.J.Grzybko@student.tudelft.nl |
| Ibrahim Omar Abdalla Mohamed | IbrahimOmarAbdallaMohamed@student.tudelft.nl |
| Glenn Weeland |G.S.M.Weeland@student.tudelft.nl |

## How to run it
You first start all Microservices individually.

We used postman to send requests to ports: `8081` to `8084`, and to `\authenticate` `\user` and `\event`. You can see what links to send requests to in our Controllers.

## How to contribute to it

To contribute, please first open an issue, complete with the definition of done
and the appropriate labels. Then, open a merge request to `dev` and push
your changes.
