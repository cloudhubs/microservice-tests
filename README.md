# Capstone-Project5
## DevOps: Stress, Load, and Functional tests for OSS Microservices

The aim of our project was to develop comprehensive tests for the TrainTicket and eShopOnContainers microservices, specifically covering load and functional regression testing using Gatling and Selenium, respectively.

For this project, we are using the microservices [TrainTicket](https://github.com/FudanSELab/train-ticket) and [eShopOnContainers](https://github.com/dotnet-architecture/eShopOnContainers).

#### To run the eShopOnContainers microservice using Docker, run the makefile.
 - `make build` - builds the main application and eShopOnContainers
   - To build just eshop: `make build-eshop`
   - To build just react app: `make build-react`
 - `make` - runs main application and eShopOnContainers
   - To run just eshop: `make eshop`
   - To run just react app: `make react`
 - `make stop` - stops the containers on Docker

### To run TrainTicket 1.0.1, use the following instructions.
 - Run the following commands:
 - git clone https://github.com/cloudhubs/train-ticket train-ticket-repo
 - cd train-ticket-repo
 - git checkout tags/v1.0.1
 - make reset-deploy
 - make deploy

#### Access the React App (frontend):
 - [localhost:3000](http://localhost:3000)

#### eShopOnContainers Health Check:
 - [localhost:5107](http://host.docker.internal:5107)

#### eShopOnContainers Main View:
 - [host.docker.internal:5100](http://host.docker.internal:5100)
 - [host.docker.internal:5104](http://host.docker.internal:5104)

### Installation Information for Tools
 - [Minikube](https://minikube.sigs.k8s.io/docs/start/)
 - [kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl-windows/)
   - Use binary using curl
   - `curl.exe -LO "https://dl.k8s.io/release/v1.26.0/bin/windows/amd64/kubectl.exe"`
 - [Helm](https://helm.sh/docs/intro/install/)
   - Best: use scoop to install
   - [Scoop](https://scoop.sh/)
 - NGINX [Ingress](https://kubernetes.io/docs/concepts/services-networking/ingress/) Controller
   - Used for eShopOnContainers
   - Install using kubectl
   - `kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.1.1/deploy/static/provider/cloud/deploy.yaml`

### Main Contributions
1.  Provided an open-source example of automated functional regression tests  and load tests for microservice systems, as previous published examples are not sufficient
2. Produced an initial set of comprehensive tests for a proposed benchmark of well-established microservice systems
3. Demonstrated how a business process reconstruction can be used as a way to validate test scenarios

Developed by Ethan Robinson, Timmy Frederiksen, Trae Stevens and Sheldon Smith.

### Project Walkthrough
YouTube Link: https://youtu.be/flkiH4gcPZ4
