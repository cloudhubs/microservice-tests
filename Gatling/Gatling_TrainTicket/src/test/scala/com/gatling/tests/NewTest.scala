package com.gatling.tests

import scala.concurrent.duration._

import io.gatling.core.Predef.*
import io.gatling.core.structure.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

class NewTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://192.168.3.205:32677")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())

	var token = "test"

	val headers_2 = Map(
		"Accept" -> "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-US,en;q=0.9",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")

	val headers_6 = Map(
		"Accept" -> "application/json, text/plain, */*",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-US,en;q=0.9",
		"Content-Type" -> "application/json;charset=UTF-8",
		"Origin" -> "http://192.168.3.205:32677",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")

	val headers_9 = Map(
		"Accept" -> "application/json, text/javascript, */*; q=0.01",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-US,en;q=0.9",
		"Content-Type" -> "application/json",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
		"X-Requested-With" -> "XMLHttpRequest",
		"authorization" -> "Bearer ${token}")

	val headers_10 = Map(
		"Accept" -> "application/json, text/plain, */*",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-US,en;q=0.9",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
		"authorization" -> "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWQiOiIzZDdhNjkwOC0xOGFjLTRjODgtOTgzZi00M2IzOGU5N2NiZWIiLCJpYXQiOjE2Nzg5NTI0NjgsImV4cCI6MTY3ODk1NjA2OH0.j2prQZGiV9EmbRl9rcCkX0mBR6Z6ww2Botumm9H7D44")

	val scn = scenario("NewTest")
		.exec(http("Home Page")
			.get("/")
			.resources(http("Generate Code")
			.get("/api/v1/verifycode/generate")
			.headers(headers_2),
				http("Generate Code")
			.get("/api/v1/verifycode/generate")
			.headers(headers_2)))
		.pause(4)
		.exec(http("Admin Login")
			.get("/adminlogin.html"))
		.pause(6)
		.exec(http("Login Request")
			.post("/api/v1/users/login")
			.headers(headers_6)
			.body(RawFileBody("com/gatling/tests/newtest/0006_request.json"))
			.check(bodyString.saveAs("BodyString"))
			.check(jsonPath("$.data.token").saveAs("token")))
		.exec { session =>
			token = session("token").as[String]
			println(s"Token: $token")
			session
		}
		.exec(http("Admin Page")
			.get("/admin.html"))
		.exec(http("Get Users")
			.get("/api/v1/userservice/users")
			.headers(headers_9))
		.exec(http("Get Orders")
			.get("/api/v1/adminorderservice/adminorder")
			.headers(headers_9))
		.exec(http("Get Travel")
			.get("/api/v1/admintravelservice/admintravel")
			.headers(headers_9))
		.pause(2)
		/*.exec(http("request_12")
			.get("/admin_travel.html")
			.resources(http("request_13")
			.get("/assets/fonts/fontawesome-webfont.woff2?v=4.6.3"),
				http("request_14")
			.get("/api/v1/trainservice/trains")
			.headers(headers_9),
				http("request_15")
			.get("/api/v1/stationservice/stations")
			.headers(headers_9),
				http("request_16")
			.get("/api/v1/routeservice/routes")
			.headers(headers_9),
				http("request_17")
			.get("/api/v1/admintravelservice/admintravel")
			.headers(headers_10)))*/

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}