package com.gatling.tests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class CompleteOrderTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://host.docker.internal:5100")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*""", """.*\.PNG""", """.*/pic/""", """.*\.svg""", """.*/js/site.js.*""", """.*/hub/notificationhub/.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.9")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")

	val addItem = exec(http("Home Page")
		.get("/?page=1"))
		.pause(4)
		.exec(http("Add Item to Cart")
			.post("/Cart/AddToCart")
			.formParam("id", "3"))
		.pause(3)
		.exec(http("View Cart")
			.get("/Cart"))
		.pause(4)

	val completeCheckout = exec(http("Click Checkout")
		.post("/Cart")
		.formParam("quantities[0].Key", "c36b5afa-1ead-431e-9e2d-7af1ba934263")
		.formParam("quantities[0].Value", "1")
		.formParam("action", "[ Checkout ]"))
		.pause(12)
		.exec(http("Submit Checkout")
			.post("/Order/Checkout")
			.formParam("Street", "1001 Speight Ave Unit 443B")
			.formParam("City", "Waco")
			.formParam("State", "TX")
			.formParam("Country", "United States of America")
			.formParam("CardNumber", "4118102062335380")
			.formParam("CardHolderName", "Sheldon Smith")
			.formParam("CardExpirationShort", "02/32")
			.formParam("CardSecurityNumber", "843")
			.formParam("orderitems[0].PictureUrl", "http://host.docker.internal:5202/c/api/v1/catalog/items/3/pic/")
			.formParam("orderitems[0].ProductName", "Prism White T-Shirt")
			.formParam("orderitems[0].UnitPrice", "12")
			.formParam("orderitems[0].Units", "1")
			.formParam("Total", "12")
			.formParam("action", "[ Place Order ]")
			.formParam("ZipCode", "76706")
			.formParam("RequestId", "df2761c1-6cff-40f3-b63c-41f6090c7327"))
		.pause(3)
		.exec(http("Order Page")
			.get("/Order"))
		.pause(3)

	val viewOrder = exec(http("View Order Details")
		.get("/Order/Detail?orderId=1"))
		.pause(5)
		.exec(http("Home Page")
			.get("/"))
		.pause(2)

	val users1 = scenario("Users1").exec(addItem, completeCheckout)

	val users2 = scenario("Users2").exec(addItem, completeCheckout, viewOrder)

	setUp(
		users1.inject(rampUsers(50).during(15)),
		users2.inject(rampUsers(30).during(10))
	).protocols(httpProtocol)
}