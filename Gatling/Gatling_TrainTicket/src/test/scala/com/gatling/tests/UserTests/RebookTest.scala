package com.gatling.tests.UserTests

import com.gatling.tests.Modules.LoginModule.*
import com.gatling.tests.Modules.UserModules.*
import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*

class RebookTest extends Simulation {

  //Test scenario to check general rebook endpoints
  val rebookGeneral: ScenarioBuilder = scenario("Checking General Rebook Endpoints")
    .exec(loginScenario)
    .exec(http("Get Rebook Welcome")
      .get("/api/v1/rebookservice/welcome") //Get rebook service welcome
      .headers(apiV1Header))

  //Test scenario to check add (post) endpoints
  val rebookTests: ScenarioBuilder = scenario("Users Rebooking Order")
    .exec(loginScenario)
    .exec(http("Rebook")
      .post("/api/v1/rebookservice/rebook") //Rebook scenario
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/OrderListUser/rebook_form.json")))
    .exec(http("Rebook Difference")
      .post("/api/v1/rebookservice/rebook/difference") //Rebook difference
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/OrderListUser/rebook_form.json")))

  //Run the test simulation with the scenarios
  setUp(
    rebookGeneral.inject(rampUsers(1).during(10)),
    rebookTests.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}
