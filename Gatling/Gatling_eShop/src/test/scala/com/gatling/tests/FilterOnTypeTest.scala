package com.gatling.tests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class FilterOnTypeTest extends Simulation {

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
		.exec(http("Mug Filter")
			.post("/")
			.formParam("BrandFilterApplied", "All")
			.formParam("TypesFilterApplied", "1")
			.formParam("x", "25")
			.formParam("y", "38")
			.formParam("__RequestVerificationToken", "CfDJ8MuPnMIYXd5ErJ4gKURjpuxy6JUKPKVYvZY68H2P1nIGwfO3Ip_tbXS8UpfzHmzFqs8__YynKPoVNtafH4MP94icJy_v_zHAfc2vnXPaqdeKpuAr-yX1m8c4C7B98hyZQXfikZ9G30wJF-2ejvHI8LU"))
		.pause(4)
		.exec(http("T-Shirt Filter")
			.post("/")
			.formParam("BrandFilterApplied", "All")
			.formParam("TypesFilterApplied", "2")
			.formParam("x", "13")
			.formParam("y", "40")
			.formParam("__RequestVerificationToken", "CfDJ8MuPnMIYXd5ErJ4gKURjpuwRqQN9jwIYAye8S3zeyT5VszfJRPmkrg14HQM7dlsoQuGTC4KInOIjMw5hPe_h8Ki8YBoDMv1b-rEYZ44EU9BSJfb7vWd-PsIDGpeZEE_r66G06hSZW8kWvFUXPdh-WtE"))
		.pause(3)
		.exec(http("Pin Filter")
			.post("/")
			.formParam("BrandFilterApplied", "All")
			.formParam("TypesFilterApplied", "3")
			.formParam("x", "12")
			.formParam("y", "37")
			.formParam("__RequestVerificationToken", "CfDJ8MuPnMIYXd5ErJ4gKURjpuwYDJf5Li68pHBQW8IJOxdDbCh38DA1OODiY-6mw8ucMlnUHJPTKwu185b33lz2LHZKT3FicJG85cs6JDuZEBaliluX3ScreptswxujtO3eq7IkF4U2RYwe4u_KO5RMmUU"))
		.pause(4)
	
	val filter2 = exec(http("All Filter")
		.post("/")
		.formParam("BrandFilterApplied", "All")
		.formParam("TypesFilterApplied", "All")
		.formParam("x", "21")
		.formParam("y", "43")
		.formParam("__RequestVerificationToken", "CfDJ8MuPnMIYXd5ErJ4gKURjpuwOutTVA4RAAyknPwOtkUgeSWuBw9Wkru1pl3tByyHOj9t3K-8Pp6gHphtonlgA8mqdCTJcpBhk562JEg4X_cTHquWDVo0fh6tFx5F9VjlOkr7EgSL2PwJ1YHE7cIxrv7c"))
		.pause(3)
		.exec(http("T-Shirt Filter")
			.post("/")
			.formParam("BrandFilterApplied", "All")
			.formParam("TypesFilterApplied", "2")
			.formParam("x", "20")
			.formParam("y", "41")
			.formParam("__RequestVerificationToken", "CfDJ8MuPnMIYXd5ErJ4gKURjpuwO6SwcCt8GQU3Wgc4LT5PTPOYaJVzV3gkbIGFkG2jiNjr-L6hA-yBzY6Sg2zALxfr1NfuIinbyDtgxQkQ3YYA9QOnkic33cke1eF5t9VCtc5abX3yqCN_mp-63udrQD-0"))
		.pause(3)

	val users1 = scenario("Users1").exec(filter1)

	val users2 = scenario("Users2").exec(filter2)

	setUp(
		users1.inject(rampUsers(10).during(10)),
		users2.inject(rampUsers(6).during(10))
	).protocols(httpProtocol)
}