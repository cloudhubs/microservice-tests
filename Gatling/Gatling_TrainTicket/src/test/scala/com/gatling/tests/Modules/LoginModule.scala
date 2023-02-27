package com.gatling.tests.Modules

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*
import com.gatling.tests.Modules.HeaderModules.*
import io.gatling.core.structure.ChainBuilder

object LoginModule {

  val homePage = exec(http("Home Page")
    .get("/index.html")
    .headers(mainPageHeader))
    .pause(2)

  val adminLoginPage = exec(http("Go to Login Page")
    .get("/adminlogin.html")
    .headers(mainPageHeader))
    .pause(3)

  val adminLogin = exec(http("Send Login Request")
    .post("/api/v1/users/login")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/${login_file}")))

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

  val userLoginPage = exec(http("Go to Login Page")
    .get("/client_login.html")
    .resources(http("Generate CAPTCHA")
      .get("/api/v1/verifycode/generate")
      .headers(apiV1Header)))
    .pause(4)

  val submitUserLogin = exec(http("Request Login")
    .post("/api/v1/users/login")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/${login_file}")))

  /**Change to have feeder*/
  val adminLoginScenario: ChainBuilder = exec { session =>
    //Change to csv to hold multiple user login info
    val newSession = session.setAll("login_file" -> "Login/admin_login.json")
    newSession
  }
  //Go to home page and view cart
  .exec(homePage, adminLoginPage, adminLogin, adminHomePage)

  /**Change to have feeder*/
  val userLoginScenario: ChainBuilder = exec { session =>
    //Change to csv to hold multiple user login info
    val newSession = session.setAll("login_file" -> "Login/user_login.json")
    newSession
  }
  .exec(homePage, userLoginPage, submitUserLogin)
}
