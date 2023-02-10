package com.gatling.tests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class FilterOnBrandTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://host.docker.internal:5100")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*""", """.*\.PNG""", """.*/pic/""", """.*\.svg""", """.*/js/site.js.*""", """.*/hub/notificationhub/.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.9")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36")

	val filter1 = exec(http("Home Page")
		.get("/"))
		.pause(2)
		.exec(http(".NET Filter")
			.post("/")
			.formParam("BrandFilterApplied", "1")
			.formParam("TypesFilterApplied", "All")
			.formParam("x", "42")
			.formParam("y", "28")
			.formParam("__RequestVerificationToken", "CfDJ8MuPnMIYXd5ErJ4gKURjpuyGAVVggSG5ksvbaT_ZfXluuY_wereA-_hfuv3DWdSAGdTMx0Gk3otjI4XQkh6pF47XdwSdWrKY9izH4TsE0ijW5k7F1Idrl8SoHiwSDDgyRbrsbdSgIhFWt5nw4AsOitg"))
		.pause(4)
		.exec(http("Other Filter")
			.post("/")
			.formParam("BrandFilterApplied", "2")
			.formParam("TypesFilterApplied", "All")
			.formParam("x", "28")
			.formParam("y", "37")
			.formParam("__RequestVerificationToken", "CfDJ8MuPnMIYXd5ErJ4gKURjpuwu5owdQts6i8Sx3INaw-OJ5MkdhQdzlN7P_k4mQSjquPWfFYAvQth7hmiq4piJVT_IH8NKZjyij5q8IaTiRdMzxOTlTBDGblb1P0m-J4DorxoTIOkcjAReYyk-tuB7Wec"))
		.pause(3)
		.exec(http("All Filter")
			.post("/")
			.formParam("BrandFilterApplied", "All")
			.formParam("TypesFilterApplied", "All")
			.formParam("x", "18")
			.formParam("y", "33")
			.formParam("__RequestVerificationToken", "CfDJ8MuPnMIYXd5ErJ4gKURjpux6g41lTbMhWGjlMIPRK02HmaH25nPstV_A6cuyD0Zs_Ou-SKbEm7nUDmq5loCS7X6Xe6b0RBf8Y-sKu4s5ofzDrUq7m1j4YaYn8K7WZFiyC76PBqqZK7ySNffCSnuYywc"))
		.pause(3)

	val filter2 = exec(http("Other Filter")
		.post("/")
		.formParam("BrandFilterApplied", "2")
		.formParam("TypesFilterApplied", "All")
		.formParam("x", "30")
		.formParam("y", "28")
		.formParam("__RequestVerificationToken", "CfDJ8MuPnMIYXd5ErJ4gKURjpuzHG1LUqmh3NoB2041jwT-LYgpUJVttD84kuCaueJOCk8y2GFvExzA-ZYCckIv9iEJIru5-38aZhA1uwNu-iNme6EkqsuIQ-Bg0zn5KazKC0snC3htNGcpE1-f4gigLj0I"))
		.pause(4)

	val users1 = scenario("Users1").exec(filter1)

	val users2 = scenario("Users2").exec(filter2)

	setUp(
		users1.inject(rampUsers(10).during(10)),
		users2.inject(rampUsers(6).during(10))
	).protocols(httpProtocol)
}