package com.gatling.tests

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class LoginTest2 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://host.docker.internal:5100")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*""", """.*\.PNG""", """.*/pic/""", """.*\.svg""", """.*/js/site.js.*""", """.*/hub/notificationhub/.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.9")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36")

	val uri1 = "host.docker.internal"

	val login = exec(http("Home Page")
		.post("/"))
		.pause(2)
		.exec(http("Sign In Page")
			.get("/Account/SignIn"))
		.pause(4)
		.exec(http("Submit Sign In")
			.post("http://" + uri1 + ":5105/Account/Login?returnurl=%2Fconnect%2Fauthorize%2Fcallback%3Fclient_id%3Dmvc%26redirect_uri%3Dhttp%253A%252F%252Fhost.docker.internal%253A5100%252Fsignin-oidc%26response_type%3Dcode%2520id_token%26scope%3Dopenid%2520profile%2520orders%2520basket%2520webshoppingagg%2520orders.signalrhub%26response_mode%3Dform_post%26nonce%3D638114104999505444.MDczNGIxNjMtNDhmNS00ODRjLTk1MGItNTkzZTk5NWEwYzRiYjRkOWQyZmYtNGVjMS00ODFkLWFmOTItMTEyYmQwNDNkYzU1%26state%3DCfDJ8HI2B0m15WhGp3yVc3O5P6meCJTffNO4HVoh0b2BXP-AcH5ZEz09PhggPpidoAxtCw4kMeDLioDr0MC1SL35WaLMYy7BQDyYCKbqNwNnMYaLaOsl9RhPRDT6nSt1-7TS1X0a9uecrRqwYnyR2XmKXI-CvMYdLPJoiK5k_moGWmqQ7GotZEWbnoi__O0wTMH5LN2Jwor6t8dur6tCCJ1OH2jIqbSsati-xWN2Qen0EWwbSItTci2JyJo_vNqKjFXlTqs5VEJwxzXVivAH3dc9abgFdJdBldZr82q_-8ICZ59kU6ZoLO1IEObDC-lyMQBS7AY7z5glKjqqWGEBYUKf2iw%26x-client-SKU%3DID_NETSTANDARD2_0%26x-client-ver%3D6.10.0.0")
			.formParam("ReturnUrl", "/connect/authorize/callback?client_id=mvc&redirect_uri=http%3A%2F%2Fhost.docker.internal%3A5100%2Fsignin-oidc&response_type=code%20id_token&scope=openid%20profile%20orders%20basket%20webshoppingagg%20orders.signalrhub&response_mode=form_post&nonce=638114104999505444.MDczNGIxNjMtNDhmNS00ODRjLTk1MGItNTkzZTk5NWEwYzRiYjRkOWQyZmYtNGVjMS00ODFkLWFmOTItMTEyYmQwNDNkYzU1&state=CfDJ8HI2B0m15WhGp3yVc3O5P6meCJTffNO4HVoh0b2BXP-AcH5ZEz09PhggPpidoAxtCw4kMeDLioDr0MC1SL35WaLMYy7BQDyYCKbqNwNnMYaLaOsl9RhPRDT6nSt1-7TS1X0a9uecrRqwYnyR2XmKXI-CvMYdLPJoiK5k_moGWmqQ7GotZEWbnoi__O0wTMH5LN2Jwor6t8dur6tCCJ1OH2jIqbSsati-xWN2Qen0EWwbSItTci2JyJo_vNqKjFXlTqs5VEJwxzXVivAH3dc9abgFdJdBldZr82q_-8ICZ59kU6ZoLO1IEObDC-lyMQBS7AY7z5glKjqqWGEBYUKf2iw&x-client-SKU=ID_NETSTANDARD2_0&x-client-ver=6.10.0.0")
			.formParam("Email", "demouser@microsoft.com")
			.formParam("Password", "Pass@word1")
			.formParam("RememberMe", "false"))
		.pause(2)

	val addItem = exec(http("Add Item to Cart")
		.post("/Cart/AddToCart")
		.formParam("id", "1")
		.formParam("__RequestVerificationToken", "CfDJ8HI2B0m15WhGp3yVc3O5P6nfcGDfQczPnwFldV7uR1PM7Ap6m1WY8AP1OMfGLzVvD6O9bWUjWJ1Qs_dF04x0XvrFx_Umy5gccQrfXmemMgKUr0rMTnXbd90cqc1y_yDY_V5CMAI3zaWk-GJxYhx1VujMii4Ugp9MDbOz3XMhIkKKomBrfOdDY-6PfcsD71dpYQ"))
		.pause(3)

	val users1 = scenario("Users1").exec(login)

	val users2 = scenario("Users2").exec(login, addItem)

	setUp(
		//users1.inject(rampUsers(6).during(10)),
		users2.inject(atOnceUsers(1))
	).protocols(httpProtocol)
}