package com.gatling.tests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class LoginTest extends Simulation {

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
		.post("/")
		.formParam("BrandFilterApplied", "1")
		.formParam("TypesFilterApplied", "All")
		.formParam("x", "30")
		.formParam("y", "34")
		.formParam("__RequestVerificationToken", "CfDJ8HI2B0m15WhGp3yVc3O5P6k0Em54BmtF0a0fkWCivhS20gu1Jt8TcznpE4OjvcZs_VXeM_9f5dgbo0jr7e1H1kIwF5U24Wbq63BRCNiNJUCsAgDRFvZRteHFR3d7oltmPOb4h2CIGHIj8116D8Wa6SY"))
		.pause(2)
		.exec(http("Sign In Page")
			.get("/Account/SignIn"))
		.pause(4)
		.exec(http("Submit Sign In")
			.post("http://" + uri1 + ":5105/Account/Login?returnurl=%2Fconnect%2Fauthorize%2Fcallback%3Fclient_id%3Dmvc%26redirect_uri%3Dhttp%253A%252F%252Fhost.docker.internal%253A5100%252Fsignin-oidc%26response_type%3Dcode%2520id_token%26scope%3Dopenid%2520profile%2520orders%2520basket%2520webshoppingagg%2520orders.signalrhub%26response_mode%3Dform_post%26nonce%3D638114104999505444.MDczNGIxNjMtNDhmNS00ODRjLTk1MGItNTkzZTk5NWEwYzRiYjRkOWQyZmYtNGVjMS00ODFkLWFmOTItMTEyYmQwNDNkYzU1%26state%3DCfDJ8HI2B0m15WhGp3yVc3O5P6meCJTffNO4HVoh0b2BXP-AcH5ZEz09PhggPpidoAxtCw4kMeDLioDr0MC1SL35WaLMYy7BQDyYCKbqNwNnMYaLaOsl9RhPRDT6nSt1-7TS1X0a9uecrRqwYnyR2XmKXI-CvMYdLPJoiK5k_moGWmqQ7GotZEWbnoi__O0wTMH5LN2Jwor6t8dur6tCCJ1OH2jIqbSsati-xWN2Qen0EWwbSItTci2JyJo_vNqKjFXlTqs5VEJwxzXVivAH3dc9abgFdJdBldZr82q_-8ICZ59kU6ZoLO1IEObDC-lyMQBS7AY7z5glKjqqWGEBYUKf2iw%26x-client-SKU%3DID_NETSTANDARD2_0%26x-client-ver%3D6.10.0.0")
			.formParam("ReturnUrl", "/connect/authorize/callback?client_id=mvc&redirect_uri=http%3A%2F%2Fhost.docker.internal%3A5100%2Fsignin-oidc&response_type=code%20id_token&scope=openid%20profile%20orders%20basket%20webshoppingagg%20orders.signalrhub&response_mode=form_post&nonce=638114104999505444.MDczNGIxNjMtNDhmNS00ODRjLTk1MGItNTkzZTk5NWEwYzRiYjRkOWQyZmYtNGVjMS00ODFkLWFmOTItMTEyYmQwNDNkYzU1&state=CfDJ8HI2B0m15WhGp3yVc3O5P6meCJTffNO4HVoh0b2BXP-AcH5ZEz09PhggPpidoAxtCw4kMeDLioDr0MC1SL35WaLMYy7BQDyYCKbqNwNnMYaLaOsl9RhPRDT6nSt1-7TS1X0a9uecrRqwYnyR2XmKXI-CvMYdLPJoiK5k_moGWmqQ7GotZEWbnoi__O0wTMH5LN2Jwor6t8dur6tCCJ1OH2jIqbSsati-xWN2Qen0EWwbSItTci2JyJo_vNqKjFXlTqs5VEJwxzXVivAH3dc9abgFdJdBldZr82q_-8ICZ59kU6ZoLO1IEObDC-lyMQBS7AY7z5glKjqqWGEBYUKf2iw&x-client-SKU=ID_NETSTANDARD2_0&x-client-ver=6.10.0.0")
			.formParam("Email", "demouser@microsoft.com")
			.formParam("Password", "Pass@word1")
			.formParam("__RequestVerificationToken", "CfDJ8G_Nzkb2tfhIh0rfCChTaUUM9r58LN3ZSl6iUl1BSTrAg1Vo2IykNk2JYIvdVwpdFOwGBLnZ63L0_8_MCbvExEnkcqefU9KAy20FWU8x9bleM6A0xSraWL0uo-faFGm4iqYonS_moeyGJYA3cSL5JTA")
			.formParam("RememberMe", "false")
			.resources(http("Authorizing Sign In")
				.post("/signin-oidc")
				.formParam("code", "C7oOYpKN3ZkG3-lsJz6qrKWHSF2bq7bs1Dpevr7ZRKE")
				.formParam("id_token", "eyJhbGciOiJSUzI1NiIsImtpZCI6IjZCN0FDQzUyMDMwNUJGREI0RjcyNTJEQUVCMjE3N0NDMDkxRkFBRTEiLCJ0eXAiOiJKV1QiLCJ4NXQiOiJhM3JNVWdNRnY5dFBjbExhNnlGM3pBa2ZxdUUifQ.eyJuYmYiOjE2NzU4MTM3MDgsImV4cCI6MTY3NTgyMDkwOCwiaXNzIjoibnVsbCIsImF1ZCI6Im12YyIsIm5vbmNlIjoiNjM4MTE0MTA0OTk5NTA1NDQ0Lk1EY3pOR0l4TmpNdE5EaG1OUzAwT0RSakxUazFNR0l0TlRrelpUazVOV0V3WXpSaVlqUmtPV1F5Wm1ZdE5HVmpNUzAwT0RGa0xXRm1PVEl0TVRFeVltUXdORE5rWXpVMSIsImlhdCI6MTY3NTgxMzcwOCwiY19oYXNoIjoiN2l4RklXYmJoZmF2TUxTaUM4MDBQUSIsInNfaGFzaCI6Ik03RE1abkNLcGZsZm5LcFZwSjBWS2ciLCJzaWQiOiJfWFpwVlhOZW5COWZaVUI5YWVNM0FnIiwic3ViIjoiMTY5YzIxYjQtY2QzZS00NGUzLWI2MzctOGE3ODlmMzc0MzYyIiwiYXV0aF90aW1lIjoxNjc1ODEzNzA4LCJpZHAiOiJsb2NhbCIsInByZWZlcnJlZF91c2VybmFtZSI6ImRlbW91c2VyQG1pY3Jvc29mdC5jb20iLCJ1bmlxdWVfbmFtZSI6ImRlbW91c2VyQG1pY3Jvc29mdC5jb20iLCJuYW1lIjoiRGVtb1VzZXIiLCJsYXN0X25hbWUiOiJEZW1vTGFzdE5hbWUiLCJjYXJkX251bWJlciI6IjQwMTI4ODg4ODg4ODE4ODEiLCJjYXJkX2hvbGRlciI6IkRlbW9Vc2VyIiwiY2FyZF9zZWN1cml0eV9udW1iZXIiOiI1MzUiLCJjYXJkX2V4cGlyYXRpb24iOiIxMi8yNSIsImFkZHJlc3NfY2l0eSI6IlJlZG1vbmQiLCJhZGRyZXNzX2NvdW50cnkiOiJVLlMuIiwiYWRkcmVzc19zdGF0ZSI6IldBIiwiYWRkcmVzc19zdHJlZXQiOiIxNTcwMyBORSA2MXN0IEN0IiwiYWRkcmVzc196aXBfY29kZSI6Ijk4MDUyIiwiZW1haWwiOiJkZW1vdXNlckBtaWNyb3NvZnQuY29tIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwaG9uZV9udW1iZXIiOiIxMjM0NTY3ODkwIiwicGhvbmVfbnVtYmVyX3ZlcmlmaWVkIjpmYWxzZSwiYW1yIjpbInB3ZCJdfQ.ZhnkKbDMVyte0vCcfZUMuIt29Lf4JZkB7QHMEMao7Zex7x7numlxNXupehOIld2Yjd-WZ9v7o8nIc2B_Fk54zvNq7sclC-VAGwaX1UztK54LOhtUh7cC9YYDxgtEb5gXfcia6OfTCH7r3s7WLe8FYG9Q_PPdaHSaPcJPkfNfW85fGq6_KIYGd2x_U6rV9tB7WC7Dv2Fvta8EyEPF8R4ypNRgO28_5icxA8iWQ4yFwKj9GAAjQJ2sxfuRh-uERCrHLTAP1gm-1_LxwDdXmjnWgPVV94Xuq94LWf07dOCb8Dhzd1P2_HdNlOF3kL87yLBE-uZmvjHSG8iRc2JiSu7_0Q")
				.formParam("scope", "openid profile orders basket webshoppingagg orders.signalrhub")
				.formParam("state", "CfDJ8HI2B0m15WhGp3yVc3O5P6meCJTffNO4HVoh0b2BXP-AcH5ZEz09PhggPpidoAxtCw4kMeDLioDr0MC1SL35WaLMYy7BQDyYCKbqNwNnMYaLaOsl9RhPRDT6nSt1-7TS1X0a9uecrRqwYnyR2XmKXI-CvMYdLPJoiK5k_moGWmqQ7GotZEWbnoi__O0wTMH5LN2Jwor6t8dur6tCCJ1OH2jIqbSsati-xWN2Qen0EWwbSItTci2JyJo_vNqKjFXlTqs5VEJwxzXVivAH3dc9abgFdJdBldZr82q_-8ICZ59kU6ZoLO1IEObDC-lyMQBS7AY7z5glKjqqWGEBYUKf2iw")
				.formParam("session_state", "5vSr9Ckw1DiHlLxLyzOth-wb2P7tN7gyEoaJWcpp1iE.nlZcEtbKe-4LIJNe6oL8Rg")))
		.pause(2)

	val addItem = exec(http("Add Item to Cart")
		.post("/Cart/AddToCart")
		.formParam("id", "1")
		.formParam("__RequestVerificationToken", "CfDJ8HI2B0m15WhGp3yVc3O5P6nfcGDfQczPnwFldV7uR1PM7Ap6m1WY8AP1OMfGLzVvD6O9bWUjWJ1Qs_dF04x0XvrFx_Umy5gccQrfXmemMgKUr0rMTnXbd90cqc1y_yDY_V5CMAI3zaWk-GJxYhx1VujMii4Ugp9MDbOz3XMhIkKKomBrfOdDY-6PfcsD71dpYQ"))
		.pause(3)

	val users1 = scenario("Users1").exec(login)

	val users2 = scenario("Users2").exec(login, addItem)

	setUp(
		users1.inject(rampUsers(6).during(10)),
		users2.inject(rampUsers(4).during(10))
	).protocols(httpProtocol)
}