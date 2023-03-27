package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.*
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class AssuranceServiceTest extends Simulation {

  val generalAssurance = scenario("Check General Assurance Endpoints")
    .exec(loginScenario)
    .exec(
      http("Get Assurance Service Welcome")
        .get("/api/v1/assuranceservice/welcome")
        .headers(apiV1Header))
    .exec(
      http("Get Assurances")
        .get("/api/v1/assuranceservice/assurances")
        .headers(apiV1Header))
    .exec(
      http("Get Assurance Types")
        .get("/api/v1/assuranceservice/types")
        .headers(apiV1Header))
    .exec(
      http("Get Assurance by Assurance ID")
        .get("/api/v1/assuranceservice/assurances/assuranceid/1")
        .headers(apiV1Header))
    .exec(
      http("Get Assurance by Order ID")
        .get("/api/v1/assuranceservice/assurances/orderid/6279c919-e6e3-4d2b-8f72-81e06971490e")
        .headers(apiV1Header))
    .exec(
      http("Get Assurance by Type and Order ID")
        .get("/api/v1/assuranceservice/assurances/1/6279c919-e6e3-4d2b-8f72-81e06971490e")
        .headers(apiV1Header))

  val deleteAssurance = scenario("Check Delete Assurance Endpoints")
    .exec(loginScenario)
    .exec(
      http("Delete Assurance by Assurance ID")
        .delete("/api/v1/assuranceservice/assurances/assuranceid/1")
        .headers(apiV1Header))
    .exec(
      http("Delete Assurance by Order ID")
        .delete("/api/v1/assuranceservice/assurances/orderid/6279c919-e6e3-4d2b-8f72-81e06971490e")
        .headers(apiV1Header))

  val patchAssurance = scenario("Check Patch Assurance Endpoints")
    .exec(loginScenario)
    .exec(
      http("Get Assurance by Assurance ID")
        .patch("/api/v1/assuranceservice/assurances/1/6279c919-e6e3-4d2b-8f72-81e06971490e/1")
        .headers(apiV1Header))

  setUp(
    generalAssurance.inject(rampUsers(1).during(15)),
    //deleteAssurance.inject(rampUsers(1).during(10)),
    //patchAssurance.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}
