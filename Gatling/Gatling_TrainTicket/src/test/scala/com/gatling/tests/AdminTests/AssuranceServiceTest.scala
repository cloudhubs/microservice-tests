package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.*
import io.gatling.core.Predef.*
import io.gatling.core.structure.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*
import scala.concurrent.duration.*

class AssuranceServiceTest extends Simulation {

  //Test scenario to check general assurance endpoints
  val generalAssurance: ScenarioBuilder = scenario("Check General Assurance Endpoints")
    .exec(loginScenario) //Complete login scenario
    .exec(http("Get Assurance Service Welcome")
      .get("/api/v1/assuranceservice/welcome") //Get the assurance service welcome page
      .headers(apiV1Header))
    .exec(http("Get Assurances")
      .get("/api/v1/assuranceservice/assurances") //Get the assurances
      .headers(apiV1Header))
    .exec(http("Get Assurance Types")
      .get("/api/v1/assuranceservice/types") //Get the assurance types
      .headers(apiV1Header))
    .exec(http("Get Assurance by Assurance ID")
      .get("/api/v1/assuranceservice/assurances/assuranceid/1") //Get an assurance by the assurance ID
      .headers(apiV1Header))
    .exec(http("Get Assurance by Order ID")
      .get("/api/v1/assuranceservice/assurances/orderid/6279c919-e6e3-4d2b-8f72-81e06971490e")
      .headers(apiV1Header)) //Get the assurance by the order ID
    .exec(http("Get Assurance by Type and Order ID")
      .get("/api/v1/assuranceservice/assurances/1/6279c919-e6e3-4d2b-8f72-81e06971490e")
      .headers(apiV1Header)) //Get the assurance by the type and order ID

  //Testing scenario to check deletion endpoints
  val deleteAssurance: ScenarioBuilder = scenario("Check Delete Assurance Endpoints")
    .exec(loginScenario) //Complete login scenario
    .exec(http("Delete Assurance by Assurance ID")
      .delete("/api/v1/assuranceservice/assurances/assuranceid/1") //Delete the assurance by the assurance ID
      .headers(apiV1Header))
    .exec(http("Delete Assurance by Order ID")
      .delete("/api/v1/assuranceservice/assurances/orderid/6279c919-e6e3-4d2b-8f72-81e06971490e")
      .headers(apiV1Header)) //Delete the assurance by order ID

  //Testing scenario to check patch endpoint
  val patchAssurance: ScenarioBuilder = scenario("Check Patch Assurance Endpoints")
    .exec(loginScenario) //Complete login scenario
    .exec(http("Get Assurance by Assurance ID")
      .patch("/api/v1/assuranceservice/assurances/1/6279c919-e6e3-4d2b-8f72-81e06971490e/1")
      .headers(apiV1Header)) //Patch the assurance by the assurance ID

  //Run the test simulation with the scenarios
  setUp(
    generalAssurance.inject(rampUsers(1).during(15)),
    deleteAssurance.inject(rampUsers(1).during(10)),
    patchAssurance.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}
