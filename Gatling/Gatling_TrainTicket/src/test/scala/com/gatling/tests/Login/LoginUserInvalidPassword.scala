package com.gatling.tests.Login

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.NavigationModules.*
class LoginUserInvalidPassword extends Simulation {

  val users = scenario("User Logging In").exec(userLoginPage, submitUserLogin, homePage)

  setUp(
    users.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}