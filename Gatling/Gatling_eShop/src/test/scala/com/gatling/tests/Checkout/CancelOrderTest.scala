package com.gatling.tests.checkout

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class CancelOrderTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://host.docker.internal:5100")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*""", """.*\.PNG""", """.*/pic/""", """.*\.svg""", """.*/js/site.js.*""", """.*/hub/notificationhub/.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.9")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")

	val addItem = exec(http("Home Page")
		.get("/"))
		.pause(3)
		.exec(http("Add Item to Cart")
			.post("/Cart/AddToCart")
			.formParam("id", "2"))
		.pause(3)
		.exec(http("View Cart")
			.get("/Cart"))
		.pause(2)

	val completeCheckout = exec(http("Click Checkout")
		.post("/Cart")
		.formParam("quantities[0].Key", "43286de8-16ed-420c-9af0-fdc644b283c7")
		.formParam("quantities[0].Value", "1")
		.formParam("action", "[ Checkout ]"))
		.pause(10)
		.exec(http("Submit Checkout")
			.post("/Order/Checkout")
			.formParam("Street", "1001 Speight Ave")
			.formParam("City", "Waco")
			.formParam("State", "TX")
			.formParam("Country", "United States of America")
			.formParam("CardNumber", "4118102062335380")
			.formParam("CardHolderName", "Sheldon Smith")
			.formParam("CardExpirationShort", "02/32")
			.formParam("CardSecurityNumber", "843")
			.formParam("orderitems[0].PictureUrl", "http://host.docker.internal:5202/c/api/v1/catalog/items/2/pic/")
			.formParam("orderitems[0].ProductName", ".NET Black & White Mug")
			.formParam("orderitems[0].UnitPrice", "8.5")
			.formParam("orderitems[0].Units", "1")
			.formParam("Total", "8.5")
			.formParam("action", "[ Place Order ]")
			.formParam("ZipCode", "76706")
			.formParam("RequestId", "fdb6265e-34e1-43be-885d-ac5f1571be78"))
		.pause(1)
		.exec(http("Past Order Page")
			.get("/Order"))
		.pause(3)

	val cancelOrder = exec(http("Cancel Order")
		.get("/Order/cancel?orderId=2"))
		.pause(4)
		.exec(http("View Order Details")
			.get("/Order/Detail?orderId=2"))
		.pause(3)
		.exec(http("Past Order Page")
			.get("/Order"))

	val users1 = scenario("Users1").exec(addItem, completeCheckout, cancelOrder)

	val users2 = scenario("Users2").exec(addItem, completeCheckout, cancelOrder)

	setUp(
		users1.inject(rampUsers(50).during(15)),
		users2.inject(rampUsers(30).during(10))
	).protocols(httpProtocol)
}