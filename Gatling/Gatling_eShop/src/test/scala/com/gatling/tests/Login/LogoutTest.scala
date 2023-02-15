package com.gatling.tests.Login

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class LogoutTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://host.docker.internal:5100")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*""", """.*\.PNG""", """.*/pic/""", """.*\.svg""", """.*/js/site.js.*""", """.*/hub/notificationhub/.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.9")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")

	val navigatePages = exec(http("Home Page")
		.get("/"))
		.pause(3)
		.exec(http("GoTo Page 2")
			.get("/?page=1"))
		.pause(5)
		.exec(http("GoTo Page 1")
			.get("/?page=0"))
		.pause(4)

	val checkout = exec(http("Logout")
		.post("/Account/SignOut"))
		.pause(2)

	val users1 = scenario("Users1").exec(navigatePages, checkout)

	val users2 = scenario("Users2").exec(checkout)

	setUp(
		users1.inject(rampUsers(50).during(15)),
		users2.inject(rampUsers(30).during(10))
	).protocols(httpProtocol)
}