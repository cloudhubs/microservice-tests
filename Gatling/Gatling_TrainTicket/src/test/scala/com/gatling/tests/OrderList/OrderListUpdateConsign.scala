package com.gatling.tests.OrderList

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class OrderListUpdateConsign extends Simulation {

	val httpProtocol = http
		.baseUrl("http://192.168.3.205:32677")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())

	val headers_6 = Map(
		"Accept" -> "application/json, text/javascript, */*; q=0.01",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-US,en;q=0.9",
		"Content-Type" -> "application/json",
		"Origin" -> "http://192.168.3.205:32677",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
		"X-Requested-With" -> "XMLHttpRequest",
		"authorization" -> "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmZHNlX21pY3Jvc2VydmljZSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpZCI6IjRkMmE0NmM3LTcxY2ItNGNmMS1iNWJiLWI2ODQwNmQ5ZGE2ZiIsImlhdCI6MTY3NjQ1NDc0NywiZXhwIjoxNjc2NDU4MzQ3fQ.21_qzgFtYVOc5SNqC_2XDLkXtouFJiP3jti_IuvkYYY")

	val headers_8 = Map(
		"Accept" -> "application/json, text/javascript, */*; q=0.01",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-US,en;q=0.9",
		"Content-Type" -> "application/json",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
		"X-Requested-With" -> "XMLHttpRequest",
		"authorization" -> "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmZHNlX21pY3Jvc2VydmljZSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpZCI6IjRkMmE0NmM3LTcxY2ItNGNmMS1iNWJiLWI2ODQwNmQ5ZGE2ZiIsImlhdCI6MTY3NjQ1NDc0NywiZXhwIjoxNjc2NDU4MzQ3fQ.21_qzgFtYVOc5SNqC_2XDLkXtouFJiP3jti_IuvkYYY")

	val viewList = exec(http("Home Page")
		.get("/index.html"))
		.pause(1)
		.exec(http("Order List Page")
			.get("/client_order_list.html"))
		.pause(6)

	val update = exec(http("View Consign")
		.get("/api/v1/consignservice/consigns/order/8c019509-7b40-44c2-803f-a15c17f83b1e")
		.headers(headers_8))
		.pause(6)
		.exec(http("Update Consign")
			.put("/api/v1/consignservice/consigns")
			.headers(headers_6)
			.body(RawFileBody("com/gatling/tests/OrderList/orderlistupdateconsign/0009_request.json")))
		.pause(3)
		.exec(http("Home Page")
			.get("/index.html"))
		.pause(2)

	val viewUsers = scenario("Users Viewing List").exec(viewList)

	val updateUsers = scenario("Users Updating").exec(viewList, update)

	setUp(
		viewUsers.inject(rampUsers(20).during(10)),
		updateUsers.inject(rampUsers(15).during(10))
	).protocols(httpProtocol)
}