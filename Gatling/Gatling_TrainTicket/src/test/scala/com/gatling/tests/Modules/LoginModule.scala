package com.gatling.tests.Modules

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*
import com.gatling.tests.Modules.HeaderModules.*
import io.gatling.core.structure.ChainBuilder

object LoginModule {

  var token = "test"

  //Header that has information needed for api requests
  val apiV1Header = Map(
    "Accept" -> "application/json, text/plain, text/javascript, */*; q=0.01",
    "Content-Type" -> "application/json",
    "Response-Type" -> "application/json",
    "Origin" -> "http://192.168.3.205:32677",
    "X-Requested-With" -> "XMLHttpRequest",
    "authorization" -> "Bearer ${token}")

  //Header for login scenario
  val loginHeader = Map(
    "Accept" -> "application/json, text/plain, */*",
    "Accept-Encoding" -> "gzip, deflate",
    "Accept-Language" -> "en-US,en;q=0.9",
    "Content-Type" -> "application/json;charset=UTF-8",
    "Origin" -> "http://192.168.3.205:32677",
    "User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")

  //Go to home page of application
  val homePage: ChainBuilder = exec(http("Home Page")
    .get("/index.html")
    .headers(mainPageHeader))
    .pause(2)

  //Go to admin login page
  val adminLoginPage: ChainBuilder = exec(http("Go to Admin Login Page")
    .get("/adminlogin.html")
    .headers(mainPageHeader))
    .pause(3)

  //Complete login for admin
  val login: ChainBuilder = exec(http("Send Login Request")
    .post("/api/v1/users/login")
    .headers(loginHeader)
    .body(RawFileBody("com/gatling/tests/Login/admin_login.json"))
    .check(jsonPath("$.data.token").saveAs("token")))
    .exec { session =>
      token = session("token").as[String]
      session
    }
  //Go to admin home page and get needed resources
  val adminHomePage: ChainBuilder = exec(http("Go to Admin Page")
    .get("/admin.html")
    .resources(http("Get User List")
      .get("/api/v1/userservice/users") //Get users
      .headers(apiV1Header),
      http("Get Order List")
        .get("/api/v1/adminorderservice/adminorder") //Get orders
        .headers(apiV1Header),
      http("Get Travel List")
        .get("/api/v1/admintravelservice/admintravel") //Get travel
        .headers(apiV1Header)))


  //Go to main user login page
  val userLoginPage: ChainBuilder = exec(http("Go to User Login Page")
    .get("/client_login.html")
    .resources(http("Generate CAPTCHA")
      .get("/api/v1/verifycode/generate") //Generate CAPTCHA
      .headers(loginHeader)))
    .pause(4)

  //Scenario to log in admin
  val loginScenario: ChainBuilder = exec(homePage, adminLoginPage, login)

}