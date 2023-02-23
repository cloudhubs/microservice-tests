package com.gatling.tests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ValidRegister extends Simulation {

	val httpProtocol = http
		.baseUrl("http://host.docker.internal:5105")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*""", """.*\.PNG""", """.*/pic/""", """.*\.svg""", """.*/js/site.js.*""", """.*/hub/notificationhub/.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.9")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")

	val headers_0 = Map("Cache-Control" -> "max-age=0")

	val headers_3 = Map(
		"Cache-Control" -> "max-age=0",
		"Origin" -> "http://host.docker.internal:5105")

	val uri1 = "host.docker.internal"

	val scn = scenario("ValidRegister")
		.exec(http("request_0")
			.get("http://" + uri1 + ":5100/")
			.headers(headers_0))
		.pause(2)
		.exec(http("request_1")
			.get("http://" + uri1 + ":5100/Account/SignIn"))
		.pause(3)
		.exec(http("request_2")
			.get("/Account/Register?returnurl=%2Fconnect%2Fauthorize%2Fcallback%3Fclient_id%3Dmvc%26redirect_uri%3Dhttp%253A%252F%252Fhost.docker.internal%253A5100%252Fsignin-oidc%26response_type%3Dcode%2520id_token%26scope%3Dopenid%2520profile%2520orders%2520basket%2520webshoppingagg%2520orders.signalrhub%26response_mode%3Dform_post%26nonce%3D638127777014677575.OTVlNjljYzUtZTkxZi00ZDdlLTk1YjQtYTA2OGI4ZGYxMDFhYmFmOTE5YzQtODk2Yi00ZTg2LTlmYTgtYTRhZTMyMTlhMWRk%26state%3DCfDJ8MuPnMIYXd5ErJ4gKURjpuz4kZfeLtHsGoqrpiH48bNs6W8rlVw2vPh_4rGl-JosfTvd1lZhLzQnkNXC_ZQ0mNn7SEeoVEI6zkjwHTfW-VxaqvFkgb8pH5NeSCvol_mya5oRREfyvqlNNj0bMd-y0wV4jTq3UdCYJve-2HnFlKYTkUzGVVZrNZcuS5O5RmwjkJcqQsUJBm-Ugi6AX1rI6eGm_GLijxM6Jb9t1FRj8RV6H7MMWLK6Ioxxn1fwau9pmK3UX3KzGP3M6sbwErHtFUPIbR9UJ0xzBzB00UHQd54ObKrRdDgk8-s0SNSauXmCbkDKEB19wmt8hplduTy7A4c%26x-client-SKU%3DID_NETSTANDARD2_0%26x-client-ver%3D6.10.0.0"))
		.pause(92)
		.exec(http("request_3")
			.post("/Account/Register?returnurl=%2Fconnect%2Fauthorize%2Fcallback%3Fclient_id%3Dmvc%26redirect_uri%3Dhttp%253A%252F%252Fhost.docker.internal%253A5100%252Fsignin-oidc%26response_type%3Dcode%2520id_token%26scope%3Dopenid%2520profile%2520orders%2520basket%2520webshoppingagg%2520orders.signalrhub%26response_mode%3Dform_post%26nonce%3D638127777014677575.OTVlNjljYzUtZTkxZi00ZDdlLTk1YjQtYTA2OGI4ZGYxMDFhYmFmOTE5YzQtODk2Yi00ZTg2LTlmYTgtYTRhZTMyMTlhMWRk%26state%3DCfDJ8MuPnMIYXd5ErJ4gKURjpuz4kZfeLtHsGoqrpiH48bNs6W8rlVw2vPh_4rGl-JosfTvd1lZhLzQnkNXC_ZQ0mNn7SEeoVEI6zkjwHTfW-VxaqvFkgb8pH5NeSCvol_mya5oRREfyvqlNNj0bMd-y0wV4jTq3UdCYJve-2HnFlKYTkUzGVVZrNZcuS5O5RmwjkJcqQsUJBm-Ugi6AX1rI6eGm_GLijxM6Jb9t1FRj8RV6H7MMWLK6Ioxxn1fwau9pmK3UX3KzGP3M6sbwErHtFUPIbR9UJ0xzBzB00UHQd54ObKrRdDgk8-s0SNSauXmCbkDKEB19wmt8hplduTy7A4c%26x-client-SKU%3DID_NETSTANDARD2_0%26x-client-ver%3D6.10.0.0")
			.headers(headers_3)
			.formParam("User.Name", "TestUser")
			.formParam("User.LastName", "TestLast")
			.formParam("User.Street", "123 Main St")
			.formParam("User.City", "Waco")
			.formParam("User.State", "TX")
			.formParam("User.Country", "United States")
			.formParam("User.ZipCode", "76706")
			.formParam("User.PhoneNumber", "5555555555")
			.formParam("User.CardNumber", "555555555")
			.formParam("User.CardHolderName", "TestUser")
			.formParam("User.Expiration", "02/24")
			.formParam("User.SecurityNumber", "123")
			.formParam("Email", "test_user@test.com")
			.formParam("Password", "testPassword1!")
			.formParam("ConfirmPassword", "testPassword1!")
			.formParam("__RequestVerificationToken", "CfDJ8DD67GJ9SN5FrrZSWHQBhqo0S2ytWzVsL80w33u_bahUAC4FFi2RYrTsZH15CHZQRAiF24z71hsvbGZz-GEMwwWGiAD5OV3ECNcdFPukGKKuwZgZlPhOQ5LtkNzitkb6lE86Mm31uaZwLvsheAXkwaw"))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}