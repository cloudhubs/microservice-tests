package com.gatling.tests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class CollectTicket extends Simulation {

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

	val headers_1 = Map(
		"Accept" -> "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-US,en;q=0.9",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")

	val headers_6 = Map(
		"Accept" -> "application/json, text/javascript, */*; q=0.01",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-US,en;q=0.9",
		"Content-Type" -> "application/json",
		"Origin" -> "http://192.168.3.205:32677",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
		"X-Requested-With" -> "XMLHttpRequest",
		"authorization" -> "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmZHNlX21pY3Jvc2VydmljZSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpZCI6IjRkMmE0NmM3LTcxY2ItNGNmMS1iNWJiLWI2ODQwNmQ5ZGE2ZiIsImlhdCI6MTY3Njk2OTQ2NSwiZXhwIjoxNjc2OTczMDY1fQ.t48nQSmaUGgoduE_rzINPCZqGaP-7igw9aZpcH7nzxs")

	val headers_8 = Map(
		"Accept" -> "application/json, text/javascript, */*; q=0.01",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-US,en;q=0.9",
		"Content-Type" -> "application/json",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
		"X-Requested-With" -> "XMLHttpRequest",
		"authorization" -> "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmZHNlX21pY3Jvc2VydmljZSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpZCI6IjRkMmE0NmM3LTcxY2ItNGNmMS1iNWJiLWI2ODQwNmQ5ZGE2ZiIsImlhdCI6MTY3Njk2OTQ2NSwiZXhwIjoxNjc2OTczMDY1fQ.t48nQSmaUGgoduE_rzINPCZqGaP-7igw9aZpcH7nzxs")

	val headers_9 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-US,en;q=0.9",
		"Cache-Control" -> "max-age=0",
		"If-Modified-Since" -> "Mon, 29 Aug 2022 07:25:34 GMT",
		"If-None-Match" -> "630c69ee-2d58",
		"Upgrade-Insecure-Requests" -> "1",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")



	val scn = scenario("CollectTicket")
		.exec(http("request_0")
			.get("/index.html")
			.headers(headers_0)
			.resources(http("request_1")
			.get("/api/v1/verifycode/generate")
			.headers(headers_1),
            http("request_2")
			.get("/assets/fonts/fontawesome-webfont.woff2?v=4.6.3"),
            http("request_3")
			.get("/api/v1/verifycode/generate")
			.headers(headers_1)))
		.pause(2)
		.exec(http("request_4")
			.get("/client_ticket_collect.html")
			.resources(http("request_5")
			.get("/assets/fonts/fontawesome-webfont.woff2?v=4.6.3"),
            http("request_6")
			.post("/api/v1/orderOtherService/orderOther/refresh")
			.headers(headers_6)
			.body(RawFileBody("com/gatling/tests/collectticket/0006_request.json")),
            http("request_7")
			.post("/api/v1/orderservice/order/refresh")
			.headers(headers_6)
			.body(RawFileBody("com/gatling/tests/collectticket/0007_request.json"))))
		.pause(3)
		.exec(http("request_8")
			.get("/api/v1/executeservice/execute/collected/4220e6bf-7c4b-4b74-9a02-f448b28b79be")
			.headers(headers_8))
		.pause(1)
		.exec(http("request_9")
			.get("/client_ticket_collect.html")
			.headers(headers_9)
			.resources(http("request_10")
			.get("/assets/fonts/fontawesome-webfont.woff2?v=4.6.3"),
            http("request_11")
			.post("/api/v1/orderOtherService/orderOther/refresh")
			.headers(headers_6)
			.body(RawFileBody("com/gatling/tests/collectticket/0011_request.json")),
            http("request_12")
			.post("/api/v1/orderservice/order/refresh")
			.headers(headers_6)
			.body(RawFileBody("com/gatling/tests/collectticket/0012_request.json"))))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}