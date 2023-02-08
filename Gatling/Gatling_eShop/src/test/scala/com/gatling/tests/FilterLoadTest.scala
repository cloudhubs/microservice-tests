package com.gatling.tests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class FilterLoadTest extends Simulation {

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
		.exec(http("Filter w/ Two Items")
			.post("/")
			.formParam("BrandFilterApplied", "1")
			.formParam("TypesFilterApplied", "2")
			.formParam("x", "15")
			.formParam("y", "22")
			.formParam("__RequestVerificationToken", "CfDJ8HI2B0m15WhGp3yVc3O5P6mLSI9sG_gZ_PBdn4QgWPod3VRl8h5_oDJZfGTtOdhY2xQPo7WX7uoqGJxOEP1Vhr2Wfnm1btgVhm_otcnjEtVtE5a8Zqj3LI_mG1JU9AfTK_Evrhv-Qh_k8mKDsD8PdsQ"))
		.pause(4)

	val filter2 = exec(http("Filter w/ One Items")
		.post("/")
		.formParam("BrandFilterApplied", "1")
		.formParam("TypesFilterApplied", "All")
		.formParam("x", "30")
		.formParam("y", "34")
		.formParam("__RequestVerificationToken", "CfDJ8HI2B0m15WhGp3yVc3O5P6k0Em54BmtF0a0fkWCivhS20gu1Jt8TcznpE4OjvcZs_VXeM_9f5dgbo0jr7e1H1kIwF5U24Wbq63BRCNiNJUCsAgDRFvZRteHFR3d7oltmPOb4h2CIGHIj8116D8Wa6SY"))
		.pause(3)

	val users1 = scenario("Users1").exec(filter1)

	val users2 = scenario("Users2").exec(filter1, filter2)

	setUp(
		users1.inject(rampUsers(10).during(10)),
		users2.inject(rampUsers(4).during(10))
	).protocols(httpProtocol)
}