package com.gatling.tests.UserTests

import com.gatling.tests.Modules.LoginModule.*
import com.gatling.tests.Modules.UserModules.*
import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import io.gatling.core.Predef.*
import io.gatling.http.Predef.*

class RebookTest extends Simulation {

  val rebookGeneral = scenario("Checking General Rebook Endpoints")
    .exec(adminLoginScenario)
    .exec(http("Get Rebook Welcome")
      .get("/api/v1/rebookservice/welcome")
      .headers(apiV1Header))

  //Scenario to test the order change feature
  val rebookTests = scenario("Users Rebooking Order")
    .exec(adminLoginScenario)
    .exec(http("Rebook")
      .post("/api/v1/rebookservice/rebook")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/OrderListUser/rebook_form.json")))
    .exec(http("Rebook Difference")
      .post("/api/v1/rebookservice/rebook/difference")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/OrderListUser/rebook_form.json")))

  setUp(
    rebookGeneral.inject(rampUsers(1).during(10)),
    rebookTests.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}
