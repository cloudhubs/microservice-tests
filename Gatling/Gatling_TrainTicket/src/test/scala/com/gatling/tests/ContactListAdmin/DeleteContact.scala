package com.gatling.tests.ContactListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class DeleteContact extends Simulation {

	val httpProtocol = http
		.baseUrl("http://192.168.3.205:32677")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-US,en;q=0.9",
		"Cache-Control" -> "max-age=0",
		"If-Modified-Since" -> "Mon, 29 Aug 2022 07:25:34 GMT",
		"If-None-Match" -> "630c69ee-3d01",
		"Upgrade-Insecure-Requests" -> "1",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")

	val headers_2 = Map(
		"Accept" -> "application/json, text/plain, */*",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-US,en;q=0.9",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
		"authorization" -> "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWQiOiI2ODU1ZTA3OS1lNTRkLTQ5YmEtYjg4Zi1hMTNhNGQ2MmFjMzQiLCJpYXQiOjE2NzY5ODU1NjYsImV4cCI6MTY3Njk4OTE2Nn0.LGLnZcALgYS7tlQ4a7II7stlM3v7bYcxeqxKpLiAvKc")

	val headers_3 = Map(
		"Accept" -> "application/json, text/plain, */*",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "en-US,en;q=0.9",
		"Origin" -> "http://192.168.3.205:32677",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
		"authorization" -> "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWQiOiI2ODU1ZTA3OS1lNTRkLTQ5YmEtYjg4Zi1hMTNhNGQ2MmFjMzQiLCJpYXQiOjE2NzY5ODU1NjYsImV4cCI6MTY3Njk4OTE2Nn0.LGLnZcALgYS7tlQ4a7II7stlM3v7bYcxeqxKpLiAvKc")



	val scn = scenario("DeleteContact")
		.exec(http("request_0")
			.get("/admin_contacts.html")
			.headers(headers_0)
			.resources(http("request_1")
			.get("/assets/fonts/fontawesome-webfont.woff2?v=4.6.3"),
				http("request_2")
			.get("/api/v1/adminbasicservice/adminbasic/contacts")
			.headers(headers_2)))
		.pause(4)
		.exec(http("request_3")
			.delete("/api/v1/adminbasicservice/adminbasic/contacts/5729b7bf-f1c8-4d82-8773-9bbb682489f5")
			.headers(headers_3))
		.pause(1)
		.exec(http("request_4")
			.get("/admin_contacts.html")
			.headers(headers_0)
			.resources(http("request_5")
			.get("/assets/fonts/fontawesome-webfont.woff2?v=4.6.3"),
				http("request_6")
			.get("/api/v1/adminbasicservice/adminbasic/contacts")
			.headers(headers_2)))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}