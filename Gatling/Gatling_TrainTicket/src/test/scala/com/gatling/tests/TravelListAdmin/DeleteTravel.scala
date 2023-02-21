package com.gatling.tests.TravelListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class DeleteTravel extends Simulation {

	val httpProtocol = http
		.baseUrl("http://192.168.3.205:32677")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-US,en;q=0.9",
		"Cache-Control" -> "max-age=0",
		"If-Modified-Since" -> "Mon, 29 Aug 2022 07:25:34 GMT",
		"If-None-Match" -> "630c69ee-7a9c",
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

	val headers_5 = Map(
		"Accept" -> "application/json, text/plain, */*",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-US,en;q=0.9",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
		"authorization" -> "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWQiOiI2ODU1ZTA3OS1lNTRkLTQ5YmEtYjg4Zi1hMTNhNGQ2MmFjMzQiLCJpYXQiOjE2NzY5NzI1MTUsImV4cCI6MTY3Njk3NjExNX0.Ev5wj-Ab1UbRDAL_lMnyEdzzk7jXkFSthoGIXdHiAOQ")

	val headers_6 = Map(
		"Accept" -> "application/json, text/plain, */*",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-US,en;q=0.9",
		"Origin" -> "http://192.168.3.205:32677",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
		"authorization" -> "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWQiOiI2ODU1ZTA3OS1lNTRkLTQ5YmEtYjg4Zi1hMTNhNGQ2MmFjMzQiLCJpYXQiOjE2NzY5NzI1MTUsImV4cCI6MTY3Njk3NjExNX0.Ev5wj-Ab1UbRDAL_lMnyEdzzk7jXkFSthoGIXdHiAOQ")



	val scn = scenario("DeleteTravel")
		.exec(http("request_0")
			.get("/admin_travel.html")
			.headers(headers_0)
			.resources(http("request_1")
			.get("/assets/fonts/fontawesome-webfont.woff2?v=4.6.3"),
				http("request_2")
			.get("/api/v1/trainservice/trains")
			.headers(headers_2),
				http("request_3")
			.get("/api/v1/stationservice/stations")
			.headers(headers_2),
				http("request_4")
			.get("/api/v1/routeservice/routes")
			.headers(headers_2),
				http("request_5")
			.get("/api/v1/admintravelservice/admintravel")
			.headers(headers_5)))
		.pause(11)
		.exec(http("request_6")
			.delete("/api/v1/admintravelservice/admintravel/Z1235")
			.headers(headers_6))
		.pause(3)
		.exec(http("request_7")
			.get("/admin_travel.html")
			.headers(headers_0)
			.resources(http("request_8")
			.get("/assets/fonts/fontawesome-webfont.woff2?v=4.6.3"),
				http("request_9")
			.get("/api/v1/stationservice/stations")
			.headers(headers_2),
				http("request_10")
			.get("/api/v1/routeservice/routes")
			.headers(headers_2),
				http("request_11")
			.get("/api/v1/trainservice/trains")
			.headers(headers_2),
				http("request_12")
			.get("/api/v1/admintravelservice/admintravel")
			.headers(headers_5)))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}