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

  val loginGeneral = scenario("Check General Login Endpoints")
    .exec(adminLoginScenario)
    .exec(
      http("Get Login Information")
        .get("/api/v1/users/login"))
    .exec(
      http("Delete Login Information")
        .delete("/api/v1/users/login"))
    .exec(
      http("Post Authorization")
        .post("/api/v1/auth")
        .body(RawFileBody("com/gatling/tests/Login/auth.json"))
        .headers(apiV1Header))
    .exec(
      http("Verify Code")
        .get("/api/v1/verifycode/verify/test")
    )

  setUp(
    loginAdmin.inject(rampUsers(1).during(15)),
    loginUser.inject(rampUsers(1).during(20)),
    loginGeneral.inject(rampUsers(1).during(15))
  ).protocols(httpProtocolTrainTicket)
}