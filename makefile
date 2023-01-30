make: eshop

eshop: ./eShopOnContainers
	cd ./eShopOnContainers/src
	docker-compose up