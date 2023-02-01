# Capstone-Project5
## DevOps: Stress, Load, and Functional tests for OSS Microservices

For this project, we are using the microservices [TrainTicket](https://github.com/FudanSELab/train-ticket) and [eShopOnContainers](https://github.com/dotnet-architecture/eShopOnContainers).

To run the microservices using Docker, run the makefile.
 - `make build` - builds the main application and eShopOnContainers
 - `make` - runs main application and eShopOnContainers
 - `make stop` - stops the containers on Docker

To access the react app:
 - localhost:3000

eShopOnContainers health check:
 - localhost:5107

eShopOnContainers main view:
 - host.docker.internal:5100
 - host.docker.internal:5104

Developed by Ethan Robinson, Timmy Frederiksen, Trae Stevens and Sheldon Smith.
