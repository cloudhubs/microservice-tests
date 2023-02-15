package com.gatling.tests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class OrderListCancelOrder extends Simulation {

	val httpProtocol = http
		.baseUrl("http://192.168.3.205:32677")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-US,en;q=0.9",
		"Cache-Control" -> "max-age=0",
		"If-Modified-Since" -> "Mon, 29 Aug 2022 07:25:34 GMT",
		"If-None-Match" -> "630c69ee-46c7",
		"Upgrade-Insecure-Requests" -> "1",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")

	val headers_6 = Map(
		"Accept" -> "application/json, text/javascript, */*; q=0.01",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-US,en;q=0.9",
		"Content-Type" -> "application/json",
		"Origin" -> "http://192.168.3.205:32677",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
		"X-Requested-With" -> "XMLHttpRequest",
		"authorization" -> "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmZHNlX21pY3Jvc2VydmljZSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpZCI6IjRkMmE0NmM3LTcxY2ItNGNmMS1iNWJiLWI2ODQwNmQ5ZGE2ZiIsImlhdCI6MTY3NjUyNjkwNiwiZXhwIjoxNjc2NTMwNTA2fQ.1I3PyACu4-ndFXu1iYvLNYVVtnx_THxEhi_Utz8wLMU")

	val viewList = exec(http("Home Page")
		.get("/index.html")
		.headers(headers_0))
		.pause(3)
		.exec(http("View Order list")
			.get("/client_order_list.html")
			.resources(http("Refresh Page")
				.post("/api/v1/orderservice/order/refresh")
				.headers(headers_6)
				.body(RawFileBody("com/gatling/tests/orderlistcancelorder/0006_request.json")),
				http("Refresh Page")
					.post("/api/v1/orderOtherService/orderOther/refresh")
					.headers(headers_6)
					.body(RawFileBody("com/gatling/tests/orderlistcancelorder/0007_request.json"))))
		.pause(5)

	val cancelOrder = exec(http("Select Cancel Order")
		.get("/api/v1/cancelservice/cancel/refound/cfd74f18-9135-422f-8c16-73aa8e019059")
		.headers(headers_6))
		.pause(4)
		.exec(http("View Cancellation Error")
			.get("/api/v1/cancelservice/cancel/cfd74f18-9135-422f-8c16-73aa8e019059/4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f")
			.headers(headers_6))
		.pause(1)
		.exec(http("View Order List")
			.get("/client_order_list.html")
			.headers(headers_0)
			.resources(http("Refresh Page")
				.post("/api/v1/orderOtherService/orderOther/refresh")
				.headers(headers_6)
				.body(RawFileBody("com/gatling/tests/orderlistcancelorder/0012_request.json")),
				http("Refresh Page")
					.post("/api/v1/orderservice/order/refresh")
					.headers(headers_6)
					.body(RawFileBody("com/gatling/tests/orderlistcancelorder/0013_request.json"))))
		.pause(2)
		.exec(http("Home Page")
			.get("/index.html"))

	val viewUsers = scenario("Users Viewing Page").exec(viewList)

	val cancelUsers = scenario("Users Cancelling Order").exec(viewList, cancelOrder)

	setUp(
		viewUsers.inject(rampUsers(60).during(25)),
		cancelUsers.inject(rampUsers(30).during(20))
	).protocols(httpProtocol)
}