package com.gatling.tests.Modules

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*
import com.gatling.tests.Modules.HeaderModules.*
import io.gatling.core.structure.ChainBuilder

object LoginModule {

  //Go to home page of application
  val homePage = exec(http("Home Page")
    .get("/index.html")
    .headers(mainPageHeader)
    .check(status.is(200)))
    .pause(2)

  //Go to admin login page
  val adminLoginPage = exec(http("Go to Admin Login Page")
    .get("/adminlogin.html")
    .headers(mainPageHeader)
    .check(status.is(200)))
    .pause(3)

  //Complete login for admin
  val adminLogin = exec(http("Send Admin Login Request")
    .post("/api/v1/users/login")
    .headers(apiV1Header)
    .check(status.is(200))
    .formParam("username", "${username}")
    .formParam("password", "${password}"))

  //Go to admin home page and get needed resources
  val adminHomePage = exec(http("Go to Admin Page")
    .get("/admin.html")
    .check(status.is(200))
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
    .check(status.is(200))
    .resources(http("Generate CAPTCHA")
      .get("/api/v1/verifycode/generate")
      .headers(apiV1Header)))
    .pause(4)

  //Submit login request for user
  val submitUserLogin = exec(http("Send User Login Request")
    .post("/api/v1/users/login")
    .headers(apiV1Header)
    .formParam("username", "${username}")
    .formParam("password", "${password}")
    .formParam("verificationCode", "${verification_code}")
    .check(status.is(200)))

  /**Change to have feeder*/
  //Scenario to log in admin
  val adminLoginScenario: ChainBuilder = exec {
    feed(csv("Login/admin_login.csv").random)
      .exec(homePage, adminLoginPage, adminLogin, adminHomePage)
  }

  /**Change to have feeder*/
  //Scenario to log in user
  val userLoginScenario: ChainBuilder = exec {
    feed(csv("Login/user_login.csv").random)
      .exec(homePage, userLoginPage, submitUserLogin, homePage)
  }
}
