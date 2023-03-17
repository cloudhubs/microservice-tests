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
    "Origin" -> "http://192.168.3.205:32677",
    "X-Requested-With" -> "XMLHttpRequest",
    "authorization" -> "Bearer ${token}")

  val loginHeader = Map(
    "Accept" -> "application/json, text/plain, */*",
    "Accept-Encoding" -> "gzip, deflate",
    "Accept-Language" -> "en-US,en;q=0.9",
    "Content-Type" -> "application/json;charset=UTF-8",
    "Origin" -> "http://192.168.3.205:32677",
    "User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")

  //Go to home page of application
  val homePage = exec(http("Home Page")
    .get("/index.html")
    .headers(mainPageHeader))
    .pause(2)

  //Go to admin login page
  val adminLoginPage = exec(http("Go to Admin Login Page")
    .get("/adminlogin.html")
    .headers(mainPageHeader))
    .pause(3)

  //Complete login for admin
  val adminLogin = exec(http("Send Admin Login Request")
    .post("/api/v1/users/login")
    .headers(loginHeader)
    .body(RawFileBody("com/gatling/tests/Login/admin_login.json"))
    .check(jsonPath("$.data.token").saveAs("token")))
    .exec { session =>
      token = session("token").as[String]
      println(s"Token: $token")
      session
    }

  //Go to admin home page and get needed resources
  val adminHomePage = exec(http("Go to Admin Page")
    .get("/admin.html")
    .resources(http("Get User List")
      .get("/api/v1/userservice/users")
      .headers(apiV1Header),
      http("Get Order List")
        .get("/api/v1/adminorderservice/adminorder")
        .headers(apiV1Header),
      http("Get Travel List")
        .get("/api/v1/admintravelservice/admintravel")
        .headers(apiV1Header)))

  //Go to main user login page
  val userLoginPage = exec(http("Go to User Login Page")
    .get("/client_login.html")
    .resources(http("Generate CAPTCHA")
      .get("/api/v1/verifycode/generate")
      .headers(apiV1Header)))
    .pause(4)

  //Submit login request for user
  val submitUserLogin = exec(http("Send User Login Request")
    .post("/api/v1/users/login")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/${login_file}")))

  /**Change to have feeder*/
  //Scenario to log in admin
  val adminLoginScenario: ChainBuilder = exec(homePage, adminLoginPage, adminLogin, adminHomePage)

  /**Change to have feeder*/
  //Scenario to log in user
  val userLoginScenario: ChainBuilder = exec { session =>
    val newSession = session.setAll("login_file" -> "Login/user_login.json")
    newSession
  }
    .exec(homePage, userLoginPage, submitUserLogin)
}