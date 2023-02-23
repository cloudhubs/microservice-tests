package com.gatling.tests

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class InvalidLogin extends Simulation {

	val httpProtocol = http
		.baseUrl("http://host.docker.internal:5105")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*""", """.*\.PNG""", """.*/pic/""", """.*\.svg""", """.*/js/site.js.*""", """.*/hub/notificationhub/.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.9")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")

	val headers_0 = Map("Cache-Control" -> "max-age=0")

	val headers_2 = Map(
		"Cache-Control" -> "max-age=0",
		"Origin" -> "http://host.docker.internal:5105")

	val headers_5 = Map(
		"Cache-Control" -> "max-age=0",
		"Origin" -> "null")

	val uri1 = "host.docker.internal"

	val scn = scenario("InvalidLogin")
		.exec(http("request_0")
			.get("http://" + uri1 + ":5100/")
			.headers(headers_0))
		.pause(1)
		.exec(http("request_1")
			.get("http://" + uri1 + ":5100/Account/SignIn"))
		.pause(25)
		.exec(http("request_2")
			.post("/Account/Login?returnurl=%2Fconnect%2Fauthorize%2Fcallback%3Fclient_id%3Dmvc%26redirect_uri%3Dhttp%253A%252F%252Fhost.docker.internal%253A5100%252Fsignin-oidc%26response_type%3Dcode%2520id_token%26scope%3Dopenid%2520profile%2520orders%2520basket%2520webshoppingagg%2520orders.signalrhub%26response_mode%3Dform_post%26nonce%3D638127774742297520.NzAzMjRmZjAtOTk3NS00MzkzLWI4ZWQtN2VmZTU2ODU2MjRjZjA0NzEzY2YtYWZhYy00YTM3LWFhOTktZGUxYTBkMDZjZTM1%26state%3DCfDJ8MuPnMIYXd5ErJ4gKURjpuwG2iGNgDv7Nmm68rfr9h0R3QVB7lsTjZ034TujTGeXkpMv65sOcqiMMhx1O2GHssF7Ri_rgtS_PYumlP2H7NftkTJrJ61A0TJj8MfQPt6ZGctASb-8XPxDD8q_KDpiHQUgYvCngrsuIE-20Iu7LVuGgRsu9E6eVNH5wBn4WIEzvoDN_X0b2CQvfUmRLXrbuhy42wZM74cIMDIZ7QeLdX4xcFNHWIC4ke7m8FpluDAI55ETIe_L9kH0rH6qdOTLvvu2oyX-6rvQn49mMkyqu3ZEnzuwwN69Mn4MxtwhbNIiTvJdz9YfGT2Yv3mR_r7zjwM%26x-client-SKU%3DID_NETSTANDARD2_0%26x-client-ver%3D6.10.0.0")
			.headers(headers_2)
			.formParam("ReturnUrl", "/connect/authorize/callback?client_id=mvc&redirect_uri=http%3A%2F%2Fhost.docker.internal%3A5100%2Fsignin-oidc&response_type=code%20id_token&scope=openid%20profile%20orders%20basket%20webshoppingagg%20orders.signalrhub&response_mode=form_post&nonce=638127774742297520.NzAzMjRmZjAtOTk3NS00MzkzLWI4ZWQtN2VmZTU2ODU2MjRjZjA0NzEzY2YtYWZhYy00YTM3LWFhOTktZGUxYTBkMDZjZTM1&state=CfDJ8MuPnMIYXd5ErJ4gKURjpuwG2iGNgDv7Nmm68rfr9h0R3QVB7lsTjZ034TujTGeXkpMv65sOcqiMMhx1O2GHssF7Ri_rgtS_PYumlP2H7NftkTJrJ61A0TJj8MfQPt6ZGctASb-8XPxDD8q_KDpiHQUgYvCngrsuIE-20Iu7LVuGgRsu9E6eVNH5wBn4WIEzvoDN_X0b2CQvfUmRLXrbuhy42wZM74cIMDIZ7QeLdX4xcFNHWIC4ke7m8FpluDAI55ETIe_L9kH0rH6qdOTLvvu2oyX-6rvQn49mMkyqu3ZEnzuwwN69Mn4MxtwhbNIiTvJdz9YfGT2Yv3mR_r7zjwM&x-client-SKU=ID_NETSTANDARD2_0&x-client-ver=6.10.0.0")
			.formParam("Email", "sheldon_smith2@baylor.ed")
			.formParam("Password", "Pass@word1")
			.formParam("__RequestVerificationToken", "CfDJ8DD67GJ9SN5FrrZSWHQBhqpH6DAS-GMXfkw73XLcbmmpF1qdE7fSXULEMyn5WR4hqus_QVOdi4hGsjH0TtAOLyOBqb22nbMDSt1rbP70Cv41f1A_EliwVnT6PGiggPg7Q-1FoxzY7hKzFllfJ3chFrU")
			.formParam("RememberMe", "false"))
		.pause(22)
		.exec(http("request_3")
			.post("/Account/Login?returnurl=%2Fconnect%2Fauthorize%2Fcallback%3Fclient_id%3Dmvc%26redirect_uri%3Dhttp%253A%252F%252Fhost.docker.internal%253A5100%252Fsignin-oidc%26response_type%3Dcode%2520id_token%26scope%3Dopenid%2520profile%2520orders%2520basket%2520webshoppingagg%2520orders.signalrhub%26response_mode%3Dform_post%26nonce%3D638127774742297520.NzAzMjRmZjAtOTk3NS00MzkzLWI4ZWQtN2VmZTU2ODU2MjRjZjA0NzEzY2YtYWZhYy00YTM3LWFhOTktZGUxYTBkMDZjZTM1%26state%3DCfDJ8MuPnMIYXd5ErJ4gKURjpuwG2iGNgDv7Nmm68rfr9h0R3QVB7lsTjZ034TujTGeXkpMv65sOcqiMMhx1O2GHssF7Ri_rgtS_PYumlP2H7NftkTJrJ61A0TJj8MfQPt6ZGctASb-8XPxDD8q_KDpiHQUgYvCngrsuIE-20Iu7LVuGgRsu9E6eVNH5wBn4WIEzvoDN_X0b2CQvfUmRLXrbuhy42wZM74cIMDIZ7QeLdX4xcFNHWIC4ke7m8FpluDAI55ETIe_L9kH0rH6qdOTLvvu2oyX-6rvQn49mMkyqu3ZEnzuwwN69Mn4MxtwhbNIiTvJdz9YfGT2Yv3mR_r7zjwM%26x-client-SKU%3DID_NETSTANDARD2_0%26x-client-ver%3D6.10.0.0")
			.headers(headers_2)
			.formParam("ReturnUrl", "/connect/authorize/callback?client_id=mvc&redirect_uri=http%3A%2F%2Fhost.docker.internal%3A5100%2Fsignin-oidc&response_type=code%20id_token&scope=openid%20profile%20orders%20basket%20webshoppingagg%20orders.signalrhub&response_mode=form_post&nonce=638127774742297520.NzAzMjRmZjAtOTk3NS00MzkzLWI4ZWQtN2VmZTU2ODU2MjRjZjA0NzEzY2YtYWZhYy00YTM3LWFhOTktZGUxYTBkMDZjZTM1&state=CfDJ8MuPnMIYXd5ErJ4gKURjpuwG2iGNgDv7Nmm68rfr9h0R3QVB7lsTjZ034TujTGeXkpMv65sOcqiMMhx1O2GHssF7Ri_rgtS_PYumlP2H7NftkTJrJ61A0TJj8MfQPt6ZGctASb-8XPxDD8q_KDpiHQUgYvCngrsuIE-20Iu7LVuGgRsu9E6eVNH5wBn4WIEzvoDN_X0b2CQvfUmRLXrbuhy42wZM74cIMDIZ7QeLdX4xcFNHWIC4ke7m8FpluDAI55ETIe_L9kH0rH6qdOTLvvu2oyX-6rvQn49mMkyqu3ZEnzuwwN69Mn4MxtwhbNIiTvJdz9YfGT2Yv3mR_r7zjwM&x-client-SKU=ID_NETSTANDARD2_0&x-client-ver=6.10.0.0")
			.formParam("Email", "demouser@microsoft.com")
			.formParam("Password", "pass")
			.formParam("__RequestVerificationToken", "CfDJ8DD67GJ9SN5FrrZSWHQBhqqwBMxUeqDVQnu8S6rYmzdAaknz9KLLaqin2xrQU-qLDxWbPUSio0ETlWS6Q6jyqXhqn___Ld_A2TCw7dtnHKHuK1cvgleBCL3wLeqVX1QQ76uIdtJLfZZdV2Gp4k3S_KY")
			.formParam("RememberMe", "false"))
		.pause(7)
		.exec(http("request_4")
			.post("/Account/Login?returnurl=%2Fconnect%2Fauthorize%2Fcallback%3Fclient_id%3Dmvc%26redirect_uri%3Dhttp%253A%252F%252Fhost.docker.internal%253A5100%252Fsignin-oidc%26response_type%3Dcode%2520id_token%26scope%3Dopenid%2520profile%2520orders%2520basket%2520webshoppingagg%2520orders.signalrhub%26response_mode%3Dform_post%26nonce%3D638127774742297520.NzAzMjRmZjAtOTk3NS00MzkzLWI4ZWQtN2VmZTU2ODU2MjRjZjA0NzEzY2YtYWZhYy00YTM3LWFhOTktZGUxYTBkMDZjZTM1%26state%3DCfDJ8MuPnMIYXd5ErJ4gKURjpuwG2iGNgDv7Nmm68rfr9h0R3QVB7lsTjZ034TujTGeXkpMv65sOcqiMMhx1O2GHssF7Ri_rgtS_PYumlP2H7NftkTJrJ61A0TJj8MfQPt6ZGctASb-8XPxDD8q_KDpiHQUgYvCngrsuIE-20Iu7LVuGgRsu9E6eVNH5wBn4WIEzvoDN_X0b2CQvfUmRLXrbuhy42wZM74cIMDIZ7QeLdX4xcFNHWIC4ke7m8FpluDAI55ETIe_L9kH0rH6qdOTLvvu2oyX-6rvQn49mMkyqu3ZEnzuwwN69Mn4MxtwhbNIiTvJdz9YfGT2Yv3mR_r7zjwM%26x-client-SKU%3DID_NETSTANDARD2_0%26x-client-ver%3D6.10.0.0")
			.headers(headers_2)
			.formParam("ReturnUrl", "/connect/authorize/callback?client_id=mvc&redirect_uri=http%3A%2F%2Fhost.docker.internal%3A5100%2Fsignin-oidc&response_type=code%20id_token&scope=openid%20profile%20orders%20basket%20webshoppingagg%20orders.signalrhub&response_mode=form_post&nonce=638127774742297520.NzAzMjRmZjAtOTk3NS00MzkzLWI4ZWQtN2VmZTU2ODU2MjRjZjA0NzEzY2YtYWZhYy00YTM3LWFhOTktZGUxYTBkMDZjZTM1&state=CfDJ8MuPnMIYXd5ErJ4gKURjpuwG2iGNgDv7Nmm68rfr9h0R3QVB7lsTjZ034TujTGeXkpMv65sOcqiMMhx1O2GHssF7Ri_rgtS_PYumlP2H7NftkTJrJ61A0TJj8MfQPt6ZGctASb-8XPxDD8q_KDpiHQUgYvCngrsuIE-20Iu7LVuGgRsu9E6eVNH5wBn4WIEzvoDN_X0b2CQvfUmRLXrbuhy42wZM74cIMDIZ7QeLdX4xcFNHWIC4ke7m8FpluDAI55ETIe_L9kH0rH6qdOTLvvu2oyX-6rvQn49mMkyqu3ZEnzuwwN69Mn4MxtwhbNIiTvJdz9YfGT2Yv3mR_r7zjwM&x-client-SKU=ID_NETSTANDARD2_0&x-client-ver=6.10.0.0")
			.formParam("Email", "demouser@microsoft.com")
			.formParam("Password", "Pass@word1")
			.formParam("__RequestVerificationToken", "CfDJ8DD67GJ9SN5FrrZSWHQBhqoaWwVDhq-18b85dVCA8i-10Iua9y1EhCdnzhHv5FW5mEaK8KRiqPOuBmFfP4CNGGPSYIDZJXlB8CQMTwD7W1ERy3vNZpIxBQHUWDPSjDkw6o4-j9ld2cBwNcFv0gcexu4")
			.formParam("RememberMe", "false")
			.resources(http("request_5")
			.post("http://" + uri1 + ":5100/signin-oidc")
			.headers(headers_5)
			.formParam("code", "EKlsFWslUtaqYDqU2BWctAGwLdq_mDXr0BxN-jNzO9E")
			.formParam("id_token", "eyJhbGciOiJSUzI1NiIsImtpZCI6IjZCN0FDQzUyMDMwNUJGREI0RjcyNTJEQUVCMjE3N0NDMDkxRkFBRTEiLCJ0eXAiOiJKV1QiLCJ4NXQiOiJhM3JNVWdNRnY5dFBjbExhNnlGM3pBa2ZxdUUifQ.eyJuYmYiOjE2NzcxODA3MzIsImV4cCI6MTY3NzE4NzkzMiwiaXNzIjoibnVsbCIsImF1ZCI6Im12YyIsIm5vbmNlIjoiNjM4MTI3Nzc0NzQyMjk3NTIwLk56QXpNalJtWmpBdE9UazNOUzAwTXprekxXSTRaV1F0TjJWbVpUVTJPRFUyTWpSalpqQTBOekV6WTJZdFlXWmhZeTAwWVRNM0xXRmhPVGt0WkdVeFlUQmtNRFpqWlRNMSIsImlhdCI6MTY3NzE4MDczMiwiY19oYXNoIjoiNWprRU9TbTNvVkVPUTZTZV9DNUROdyIsInNfaGFzaCI6InF3VENqOTFYR2c0YXUwVlJZNm00SVEiLCJzaWQiOiJHRGMtMVgxZERneVZmXzFoUUhSeGZBIiwic3ViIjoiYjBjMGRhMjgtYmRjMi00YThhLTljMzAtOTc0YzNhM2YyNTAyIiwiYXV0aF90aW1lIjoxNjc3MTgwNzMxLCJpZHAiOiJsb2NhbCIsInByZWZlcnJlZF91c2VybmFtZSI6ImRlbW91c2VyQG1pY3Jvc29mdC5jb20iLCJ1bmlxdWVfbmFtZSI6ImRlbW91c2VyQG1pY3Jvc29mdC5jb20iLCJuYW1lIjoiRGVtb1VzZXIiLCJsYXN0X25hbWUiOiJEZW1vTGFzdE5hbWUiLCJjYXJkX251bWJlciI6IjQwMTI4ODg4ODg4ODE4ODEiLCJjYXJkX2hvbGRlciI6IkRlbW9Vc2VyIiwiY2FyZF9zZWN1cml0eV9udW1iZXIiOiI1MzUiLCJjYXJkX2V4cGlyYXRpb24iOiIxMi8yNSIsImFkZHJlc3NfY2l0eSI6IlJlZG1vbmQiLCJhZGRyZXNzX2NvdW50cnkiOiJVLlMuIiwiYWRkcmVzc19zdGF0ZSI6IldBIiwiYWRkcmVzc19zdHJlZXQiOiIxNTcwMyBORSA2MXN0IEN0IiwiYWRkcmVzc196aXBfY29kZSI6Ijk4MDUyIiwiZW1haWwiOiJkZW1vdXNlckBtaWNyb3NvZnQuY29tIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwaG9uZV9udW1iZXIiOiIxMjM0NTY3ODkwIiwicGhvbmVfbnVtYmVyX3ZlcmlmaWVkIjpmYWxzZSwiYW1yIjpbInB3ZCJdfQ.EOQzt0oktAojYGwIY7evymWwMMOUtWEjMYe3vEAJsJjZDxAHedAObGWm_OV_G_5gL8xuhwuMg9tyVrPy9GUAYJEQSE_PkdMbP-P_yEqrgbXvjXV0BRmjCnN3k2_kcQIKebm_F6FKYQrktZSA89gVV5YNUmor1eLxtUDcsd5yWt6jB_vhvrg4t33rscwsZZGrhc6ZUeU_0f5nN8tP8PElIwupwPntmPuTWJcHqQEQu0cMuOqA-6IVT_QxuVYsjp9upGy-zCCpUYTP6ipbc7xGwl6-00AN4-NY-gZ7mQvU3ezLns7r2Aas9QbaUmTQGNTJv8SjXplrggnehD47_khF2w")
			.formParam("scope", "openid profile orders basket webshoppingagg orders.signalrhub")
			.formParam("state", "CfDJ8MuPnMIYXd5ErJ4gKURjpuwG2iGNgDv7Nmm68rfr9h0R3QVB7lsTjZ034TujTGeXkpMv65sOcqiMMhx1O2GHssF7Ri_rgtS_PYumlP2H7NftkTJrJ61A0TJj8MfQPt6ZGctASb-8XPxDD8q_KDpiHQUgYvCngrsuIE-20Iu7LVuGgRsu9E6eVNH5wBn4WIEzvoDN_X0b2CQvfUmRLXrbuhy42wZM74cIMDIZ7QeLdX4xcFNHWIC4ke7m8FpluDAI55ETIe_L9kH0rH6qdOTLvvu2oyX-6rvQn49mMkyqu3ZEnzuwwN69Mn4MxtwhbNIiTvJdz9YfGT2Yv3mR_r7zjwM")
			.formParam("session_state", "hIr7N1OXwdkee-066uqaiFPKo_FdIJTtefmxoKip-FY.8VU_lcU6vL3T_woUviHSHg")))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}