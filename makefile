make: eshop react-app

eshop: ./eShopOnContainers/src/docker-compose.yml
	docker-compose -f ./eShopOnContainers/src/docker-compose.yml up -d

react-app:
	docker-compose -f ./app/docker-compose.yml up -d

build: build-react build-eshop

build-react:
	docker-compose -f ./app/docker-compose.yml build

build-eshop:
	docker-compose -f ./eShopOnContainers/src/docker-compose.yml build
