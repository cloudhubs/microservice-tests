package com.gatling.tests.OrderList

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class OrderListPayTicket extends Simulation {

	val httpProtocol = http
		.baseUrl("http://192.168.3.205:32677")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.9")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
		"Cache-Control" -> "max-age=0",
		"If-Modified-Since" -> "Mon, 29 Aug 2022 07:25:34 GMT",
		"If-None-Match" -> "630c69ee-46c7")

	val headers_6 = Map(
		"Accept" -> "application/json, text/javascript, */*; q=0.01",
		"Content-Type" -> "application/json",
		"Origin" -> "http://192.168.3.205:32677",
		"X-Requested-With" -> "XMLHttpRequest",
		"authorization" -> "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmZHNlX21pY3Jvc2VydmljZSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpZCI6IjRkMmE0NmM3LTcxY2ItNGNmMS1iNWJiLWI2ODQwNmQ5ZGE2ZiIsImlhdCI6MTY3NjQ1NDc0NywiZXhwIjoxNjc2NDU4MzQ3fQ.21_qzgFtYVOc5SNqC_2XDLkXtouFJiP3jti_IuvkYYY")

	val headers_9 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
		"Cache-Control" -> "max-age=0",
		"If-Modified-Since" -> "Mon, 29 Aug 2022 07:25:34 GMT",
		"If-None-Match" -> "630c69ee-82d3")

	val viewList = exec(http("Home Page")
		.get("/index.html")
		.headers(headers_0))
		.pause(1)
		.exec(http("View Order List")
			.get("/client_order_list.html")
			.resources(http("Refresh Page Test")
				.post("/api/v1/orderservice/order/refresh")
				.headers(headers_6)
				.body(RawFileBody("com/gatling/tests/OrderList/orderlistpayticket/account_request.json")),
				http("Refresh Page")
					.post("/api/v1/orderOtherService/orderOther/refresh")
					.headers(headers_6)
					.body(RawFileBody("com/gatling/tests/OrderList/orderlistpayticket/account_request.json"))))
		.pause(10)

	val pay = exec(http("Confirm Payment")
		.post("/api/v1/inside_pay_service/inside_payment")
		.headers(headers_6)
		.formParam("orderId", "8c019509-7b40-44c2-803f-a15c17f83b1f")
		.formParam("tripId", "D1345"))
		.pause(2)
		.exec(http("View Order List")
			.get("/client_order_list.html")
			.headers(headers_9)
			.resources(http("Refresh Page")
				.post("/api/v1/orderservice/order/refresh")
				.headers(headers_6)
				.body(RawFileBody("com/gatling/tests/OrderList/orderlistpayticket/account_request.json")),
				http("Refresh Page")
					.post("/api/v1/orderOtherService/orderOther/refresh")
					.headers(headers_6)
					.body(RawFileBody("com/gatling/tests/OrderList/orderlistpayticket/account_request.json"))))
		.pause(4)

	val returnHome = exec(http("Home Page")
		.get("/index.html"))
		.pause(2)

	val viewUsers = scenario("Users Viewing List").exec(viewList, returnHome)

	val payUsers = scenario("Users Paying").exec(viewList, pay, returnHome)

	setUp(
		payUsers.inject(atOnceUsers(1))
	).protocols(httpProtocol)
}