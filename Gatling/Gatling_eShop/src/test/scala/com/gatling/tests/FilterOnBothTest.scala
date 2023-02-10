package com.gatling.tests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class FilterOnBothTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://host.docker.internal:5100")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*""", """.*\.PNG""", """.*/pic/""", """.*\.svg""", """.*/js/site.js.*""", """.*/hub/notificationhub/.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.9")
		.contentTypeHeader("application/x-www-form-urlencoded")
		.originHeader("http://host.docker.internal:5100")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36")

	val filter1 = exec(http("Home Page")
		.get("/"))
		.pause(2)
		.exec(http(".Net Mug Filter")
			.post("/")
			.formParam("BrandFilterApplied", "1")
			.formParam("TypesFilterApplied", "1")
			.formParam("x", "25")
			.formParam("y", "34")
			.formParam("__RequestVerificationToken", "CfDJ8MuPnMIYXd5ErJ4gKURjpuwmNnPcDeg75-vWhnoUMQs7KMHD_lAnMl5iEI3P5PkJoQCRN-BFOAoQfmfAcImUgS6sHgYobx6tIWQGRNpR6UXI142uK1gG_njyc_xcSrocE1KzfSJkSD7Xlozt5ejs-RE"))
		.pause(3)
		.exec(http("Other T-Shirt Filter")
			.post("/")
			.formParam("BrandFilterApplied", "2")
			.formParam("TypesFilterApplied", "2")
			.formParam("x", "19")
			.formParam("y", "37")
			.formParam("__RequestVerificationToken", "CfDJ8MuPnMIYXd5ErJ4gKURjpux9aYCNR5tM2KLRqbaGOAoK-D9-0IJMMRDyNadPtnxU0pwg2unIN5qWChevUC58zk4Mov47U_x-fBwV_tsBlSPPPkkQB9KrcU51HixGFE8HCHV9PTKS4WWf5iwLPyZgsn0"))
		.pause(4)

	val filter2 = exec(http(".Net Pin Filter")
		.post("/")
		.formParam("BrandFilterApplied", "1")
		.formParam("TypesFilterApplied", "3")
		.formParam("x", "27")
		.formParam("y", "28")
		.formParam("__RequestVerificationToken", "CfDJ8MuPnMIYXd5ErJ4gKURjpuzEIaa9RBEUW4uAlObPD8aSbSPApBH-A-kJV-4Uc-VyHP5jke5IvGzuazRQ0oYrE__4_p4u_7JJgpWVJB293MiJsV6X_w8hxSDLBxcuE348RSyn10nBlKnXaBtI9hhkG-4"))
		.pause(4)
		.exec(http("All Mug Filter")
			.post("/")
			.formParam("BrandFilterApplied", "All")
			.formParam("TypesFilterApplied", "1")
			.formParam("x", "23")
			.formParam("y", "37")
			.formParam("__RequestVerificationToken", "CfDJ8MuPnMIYXd5ErJ4gKURjpuyhM3vC7eMMxBhqdtLdINmNGybW3hMTlcFnnJXBnvpnuRhcHbt0yCvECHc2yN2M-EI1OcxjjpWS4z0LZCYDY1O2D0Bt5p_v0kcgcHimJ3xwEXv5Xkduji9tXBiNlK1uDLE"))
		.pause(3)

	val users1 = scenario("Users1").exec(filter1)

	val users2 = scenario("Users2").exec(filter2)

	setUp(
		users1.inject(rampUsers(10).during(10)),
		users2.inject(rampUsers(6).during(10))
	).protocols(httpProtocol)
}