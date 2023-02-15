package com.gatling.tests.PastOrders

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class CheckPastOrders extends Simulation {

	val httpProtocol = http
		.baseUrl("http://host.docker.internal:5100")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*""", """.*\.PNG""", """.*/pic/""", """.*\.svg""", """.*/js/site.js.*""", """.*/hub/notificationhub/.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.9")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")


	val scn = scenario("CheckPastOrders")
		.exec(http("Home Page")
			.get("/"))
		.pause(2)
		.exec(http("View Past Orders")
			.get("/Order"))
		.pause(3)
		.exec(http("View First Order")
			.get("/Order/Detail?orderId=1"))
		.pause(4)
		.exec(http("View Past Orders")
			.get("/Order"))
		.pause(2)
		.exec(http("View Second Order")
			.get("/Order/Detail?orderId=2"))
		.pause(5)
		.exec(http("View Past Orders")
			.get("/Order"))
		.pause(2)

	val users1 = scenario("Users1").exec(scn)

	val users2 = scenario("Users2").exec(scn)

	setUp(
		users1.inject(rampUsers(400).during(10)),
		users2.inject(rampUsers(400).during(15))
	).protocols(httpProtocol)
}