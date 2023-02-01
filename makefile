make: eshop react-app

eshop:
	docker-compose --project-directory ./eShopOnContainers/src/ up -d

react-app:
	docker-compose --project-directory ./app/ up -d

build: build-react build-eshop

build-react:
	docker-compose --project-directory ./app/ build

build-eshop:
	docker-compose --project-directory ./eShopOnContainers/src/ build

stop:
	docker-compose --project-directory ./eShopOnContainers/src/ stop
	docker-compose --project-directory ./app/ stop
