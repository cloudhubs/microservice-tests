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
			.formParam("TypesFilterApplied", "All"))
		.pause(4)
		.exec(http("Other Filter")
			.post("/")
			.formParam("BrandFilterApplied", "2")
			.formParam("TypesFilterApplied", "All"))
		.pause(3)
		.exec(http("All Filter")
			.post("/")
			.formParam("BrandFilterApplied", "All")
			.formParam("TypesFilterApplied", "All"))
		.pause(3)

	val filter2 = exec(http("Other Filter")
		.post("/")
		.formParam("BrandFilterApplied", "2")
		.formParam("TypesFilterApplied", "All"))
		.pause(4)

	val users1 = scenario("Users1").exec(filter1)

	val users2 = scenario("Users2").exec(filter2)

	setUp(
		users1.inject(rampUsers(10).during(10)),
		users2.inject(rampUsers(6).during(10))
	).protocols(httpProtocol)
}