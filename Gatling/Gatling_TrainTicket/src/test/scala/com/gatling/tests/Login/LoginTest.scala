package com.gatling.tests.Login

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.*
import io.gatling.core.structure.ScenarioBuilder
class LoginTest extends Simulation {

  //Test scenario to check login scenario
  val loginUser: ScenarioBuilder = scenario("User Logging In")
    //Go to home page and view cart
    .exec(loginScenario)
    .pause(1)

  //Test scenario to check general login endpoints
  val loginGeneral: ScenarioBuilder = scenario("Check General Login Endpoints")
    .exec(loginScenario)
    .exec(http("Get Login Information")
      .get("/api/v1/users/login") //Get login information
      .headers(apiV1Header))
    .exec(http("Delete Login Information")
      .delete("/api/v1/users/login")) //Delete login information
    .exec(http("Post Authorization")
      .post("/api/v1/auth") //Post authorization
      .body(RawFileBody("com/gatling/tests/Login/admin_login.json"))
      .headers(apiV1Header))
    .exec(http("Verify Code")
      .get("/api/v1/verifycode/verify/test")) //Get verification code

  //Run the test simulation with the scenarios
  setUp(
    loginUser.inject(rampUsers(1).during(20)),
    loginGeneral.inject(rampUsers(1).during(15))
  ).protocols(httpProtocolTrainTicket)
}