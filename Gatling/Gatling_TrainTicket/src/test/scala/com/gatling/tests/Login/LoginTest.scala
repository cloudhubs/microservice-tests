package com.gatling.tests.Login

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.*
import io.gatling.core.structure.ScenarioBuilder
class LoginTest extends Simulation {

  val loginAdmin: ScenarioBuilder = scenario("Admin Logging In")
    //Go to home page and view cart
    .exec(adminLoginScenario)
    .pause(1)

  val loginUser: ScenarioBuilder = scenario("User Logging In")
    //Go to home page and view cart
    .exec(userLoginScenario)
    .pause(1)

  setUp(
    loginAdmin.inject(rampUsers(10).during(15)),
    //loginAdminInvalid.inject(rampUsers(20).during(15)),
    loginUser.inject(rampUsers(10).during(15)),
    //loginUserInvalid.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}