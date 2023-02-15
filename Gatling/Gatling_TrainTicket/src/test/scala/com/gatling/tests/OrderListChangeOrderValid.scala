package com.gatling.tests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class OrderListChangeOrderValid extends Simulation {

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



	val users = scenario("Users Changing Order")
		.exec(http("Home Page")
			.get("/index.html")
			.headers(headers_0))
		.pause(2)
		.exec(http("View Order List")
			.get("/client_order_list.html")
			.resources(http("Refresh Page")
			.post("/api/v1/orderservice/order/refresh")
			.headers(headers_6)
			.body(RawFileBody("com/gatling/tests/orderlistchangeordervalid/0006_request.json")),
				http("Refresh Page")
			.post("/api/v1/orderOtherService/orderOther/refresh")
			.headers(headers_6)
			.body(RawFileBody("com/gatling/tests/orderlistchangeordervalid/0007_request.json"))))
		.pause(4)
		.exec(http("Select Change Order")
			.post("/api/v1/travelservice/trips/left")
			.headers(headers_6)
			.body(RawFileBody("com/gatling/tests/orderlistchangeordervalid/0008_request.json")))
		.pause(4)
		.exec(http("Confirm Rebook")
			.post("/api/v1/rebookservice/rebook")
			.headers(headers_6)
			.body(RawFileBody("com/gatling/tests/orderlistchangeordervalid/0009_request.json")))
		.pause(7)
		.exec(http("Home Page")
			.get("/index.html"))
		.pause(2)

	setUp(
		users.inject(rampUsers(1000).during(30))
	).protocols(httpProtocol)
}