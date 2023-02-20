package com.gatling.tests.Login

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class LoginUserInvalidPassword extends Simulation {

  val httpProtocol = http
    .baseUrl("http://192.168.3.205:32677")
    .inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())

  val headers_1 = Map(
    "Accept" -> "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8",
    "Accept-Encoding" -> "gzip, deflate",
    "Accept-Language" -> "en-US,en;q=0.9",
    "User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")

  val headers_3 = Map(
    "Accept" -> "application/json, text/javascript, */*; q=0.01",
    "Accept-Encoding" -> "gzip, deflate",
    "Accept-Language" -> "en-US,en;q=0.9",
    "Content-Type" -> "application/json",
    "Origin" -> "http://192.168.3.205:32677",
    "User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
    "X-Requested-With" -> "XMLHttpRequest")



  val scn = scenario("Login - Wrong Password")
    .exec(http("Go to Login Page")
      .get("/client_login.html")
      .resources(http("Generate CAPTCHA")
        .get("/api/v1/verifycode/generate")
        .headers(headers_1)))
    .pause(2)
    .exec(http("Request Login")
      .post("/api/v1/users/login")
      .headers(headers_3)
      .body(RawFileBody("com/gatling/tests/loginbadpassword/0003_request.json")))

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}