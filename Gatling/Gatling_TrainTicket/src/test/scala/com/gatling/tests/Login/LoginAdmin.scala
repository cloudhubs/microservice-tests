package com.gatling.tests.Login

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class LoginAdmin extends Simulation {

  val httpProtocol = http
    .baseUrl("http://192.168.3.205:32677")
    .inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())

  val headers_0 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
    "Accept-Encoding" -> "gzip, deflate",
    "Accept-Language" -> "en-US,en;q=0.9",
    "Cache-Control" -> "max-age=0",
    "If-Modified-Since" -> "Mon, 29 Aug 2022 07:25:34 GMT",
    "If-None-Match" -> "630c69ee-8ed",
    "Upgrade-Insecure-Requests" -> "1",
    "User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")

  val headers_2 = Map(
    "Accept" -> "application/json, text/plain, */*",
    "Accept-Encoding" -> "gzip, deflate",
    "Accept-Language" -> "en-US,en;q=0.9",
    "Content-Type" -> "application/json;charset=UTF-8",
    "Origin" -> "http://192.168.3.205:32677",
    "User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")

  val headers_5 = Map(
    "Accept" -> "application/json, text/javascript, */*; q=0.01",
    "Accept-Encoding" -> "gzip, deflate",
    "Accept-Language" -> "en-US,en;q=0.9",
    "Content-Type" -> "application/json",
    "User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
    "X-Requested-With" -> "XMLHttpRequest",
    "authorization" -> "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWQiOiI2ODU1ZTA3OS1lNTRkLTQ5YmEtYjg4Zi1hMTNhNGQ2MmFjMzQiLCJpYXQiOjE2NzY1MzI3NDAsImV4cCI6MTY3NjUzNjM0MH0.1QpC0KtcW69ekFKadCDXxlCQ4s7G2ie7imUv2GqgZsQ")

  val headers_6 = Map(
    "Accept" -> "application/json, text/plain, */*",
    "Accept-Encoding" -> "gzip, deflate",
    "Accept-Language" -> "en-US,en;q=0.9",
    "User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
    "authorization" -> "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWQiOiI2ODU1ZTA3OS1lNTRkLTQ5YmEtYjg4Zi1hMTNhNGQ2MmFjMzQiLCJpYXQiOjE2NzY1MzI3NDAsImV4cCI6MTY3NjUzNjM0MH0.1QpC0KtcW69ekFKadCDXxlCQ4s7G2ie7imUv2GqgZsQ")


  val scn = scenario("Login Admin - Valid")
    .exec(http("Go to Login Page")
      .get("/adminlogin.html")
      .headers(headers_0))
    .pause(3)
    .exec(http("Send Login Request")
      .post("/api/v1/users/login")
      .headers(headers_2)
      .body(RawFileBody("com/gatling/tests/Login/admin_login.json"))
      .resources(http("Go to Admin Page")
        .get("/admin.html"),
        http("Get User List")
          .get("/api/v1/userservice/users")
          .headers(headers_5),
        http("Get Order List")
          .get("/api/v1/adminorderservice/adminorder")
          .headers(headers_6),
        http("Get Travel List")
          .get("/api/v1/admintravelservice/admintravel")
          .headers(headers_5)))

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}