package com.gatling.tests.OrderListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class AddOrder extends Simulation {

	val httpProtocol = http
		.baseUrl("http://192.168.3.205:32677")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-US,en;q=0.9",
		"Cache-Control" -> "max-age=0",
		"If-Modified-Since" -> "Mon, 29 Aug 2022 07:25:34 GMT",
		"If-None-Match" -> "630c69ee-b88b",
		"Upgrade-Insecure-Requests" -> "1",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")

	val headers_2 = Map(
		"Accept" -> "application/json, text/javascript, */*; q=0.01",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-US,en;q=0.9",
		"Content-Type" -> "application/json",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
		"X-Requested-With" -> "XMLHttpRequest",
		"authorization" -> "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWQiOiI2ODU1ZTA3OS1lNTRkLTQ5YmEtYjg4Zi1hMTNhNGQ2MmFjMzQiLCJpYXQiOjE2NzY5NzI1MTUsImV4cCI6MTY3Njk3NjExNX0.Ev5wj-Ab1UbRDAL_lMnyEdzzk7jXkFSthoGIXdHiAOQ")

	val headers_3 = Map(
		"Accept" -> "application/json, text/plain, */*",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-US,en;q=0.9",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
		"authorization" -> "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWQiOiI2ODU1ZTA3OS1lNTRkLTQ5YmEtYjg4Zi1hMTNhNGQ2MmFjMzQiLCJpYXQiOjE2NzY5NzI1MTUsImV4cCI6MTY3Njk3NjExNX0.Ev5wj-Ab1UbRDAL_lMnyEdzzk7jXkFSthoGIXdHiAOQ")

	val headers_8 = Map(
		"Accept" -> "application/json, text/plain, */*",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-US,en;q=0.9",
		"Content-Type" -> "application/json;charset=UTF-8",
		"Origin" -> "http://192.168.3.205:32677",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
		"authorization" -> "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWQiOiI2ODU1ZTA3OS1lNTRkLTQ5YmEtYjg4Zi1hMTNhNGQ2MmFjMzQiLCJpYXQiOjE2NzY5NzI1MTUsImV4cCI6MTY3Njk3NjExNX0.Ev5wj-Ab1UbRDAL_lMnyEdzzk7jXkFSthoGIXdHiAOQ")



	val scn = scenario("AddOrder")
		.exec(http("request_0")
			.get("/admin.html")
			.headers(headers_0)
			.resources(http("request_1")
			.get("/assets/fonts/fontawesome-webfont.woff2?v=4.6.3"),
            http("request_2")
			.get("/api/v1/userservice/users")
			.headers(headers_2),
            http("request_3")
			.get("/api/v1/adminorderservice/adminorder")
			.headers(headers_3),
            http("request_4")
			.get("/api/v1/admintravelservice/admintravel")
			.headers(headers_2)))
		.pause(11)
		.exec(http("request_5")
			.get("/api/v1/contactservice/contacts/account/8b80e423-4270-40fe-8087-a0b0ce9e2b1b")
			.headers(headers_2)
			.resources(http("request_6")
			.get("/api/v1/contactservice/contacts/account/4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f")
			.headers(headers_2),
            http("request_7")
			.get("/api/v1/contactservice/contacts/account/8b80e423-4270-40fe-8087-a0b0ce9e2b1b")
			.headers(headers_2)))
		.pause(55)
		.exec(http("request_8")
			.post("/api/v1/adminorderservice/adminorder")
			.headers(headers_8)
			.body(RawFileBody("com/gatling/tests/OrderListAdmin/addorder/0008_request.json")))
		.pause(1)
		.exec(http("request_9")
			.get("/admin.html")
			.headers(headers_0)
			.resources(http("request_10")
			.get("/assets/fonts/fontawesome-webfont.woff2?v=4.6.3"),
            http("request_11")
			.get("/api/v1/userservice/users")
			.headers(headers_2),
            http("request_12")
			.get("/api/v1/adminorderservice/adminorder")
			.headers(headers_3),
            http("request_13")
			.get("/api/v1/admintravelservice/admintravel")
			.headers(headers_2)))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}