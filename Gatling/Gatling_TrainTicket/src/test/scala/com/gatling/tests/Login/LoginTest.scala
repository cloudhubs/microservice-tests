package com.gatling.tests.Login

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.NavigationModules.*
class LoginTest extends Simulation {

  val loginAdmin = scenario("Admin Logging In").exec(adminLoginPage, adminLogin, adminHomePage)

  val loginAdminInvalid = scenario("Admin Logging In (Invalid)").exec(adminLoginPage, adminLogin)

  val loginUser = scenario("User Logging In").exec(userLoginPage, submitUserLogin, homePage)

  val loginUserInvalid = scenario("User Logging In (Invalid)").exec(userLoginPage, submitUserLogin, homePage)

  setUp(
    loginAdmin.inject(rampUsers(20).during(15)),
    loginAdminInvalid.inject(rampUsers(20).during(15)),
    loginUser.inject(rampUsers(20).during(15)),
    loginUserInvalid.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}