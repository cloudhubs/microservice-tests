package com.gatling.tests.Login

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.*
import io.gatling.core.structure.ScenarioBuilder
class LoginTest extends Simulation {

  //Scenario to test valid login for admin
  val loginAdmin: ScenarioBuilder = scenario("Admin Logging In")
    //Go to login and complete process
    .exec(adminLoginScenario)
    .pause(1)

  //Scenario to test valid login for user
  val loginUser: ScenarioBuilder = scenario("User Logging In")
    //Go to home page and view cart
    .exec(userLoginScenario)
    .pause(1)

  setUp(
    loginAdmin.inject(rampUsers(40).during(15)),
    loginUser.inject(rampUsers(60).during(20))
  ).protocols(httpProtocolTrainTicket)
}