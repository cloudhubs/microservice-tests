package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.*
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class PriceListTest extends Simulation {

  //Test scenario to check add (post) endpoints
  val priceAdd: ScenarioBuilder = scenario("Admins Adding Price")
    .exec(loginScenario, pricePage) //Log into system as admin
    .exec(http("Add Price (Admin)")
      .post("/api/v1/adminbasicservice/adminbasic/prices") //Add price using admin service
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/PriceListAdmin/add_price_form.json")))
    .exec(http("Post Price By ID")
      .post("/api/v1/priceservice/prices/byRouteIdsAndTrainTypes") //Add price by id and train
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/PriceListAdmin/add_price_form.json")))
    .exec(http("Add Price")
      .post("/api/v1/priceservice/prices") //Add price using main service
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/PriceListAdmin/add_price_form.json")))
    .pause(1)

  //Test scenario to check deletion endpoints
  val priceDelete: ScenarioBuilder = scenario("Admins Deleting Price")
    .exec(loginScenario, pricePage)
    .exec(http("Delete Price (Admin)") //Delete price using admin service
      .delete("/api/v1/adminbasicservice/adminbasic/prices/dd0e572e-7443-420c-8280-7d8215636069")
      .headers(apiV1Header))
    .exec(http("Delete Price") //Delete price using main service
      .delete("/api/v1/priceservice/prices/dd0e572e-7443-420c-8280-7d8215636069")
      .headers(apiV1Header))
    .pause(1)

  //Test scenario to check update (put) endpoints
  val priceUpdate: ScenarioBuilder = scenario("Admins Update Price")
    .exec(loginScenario, pricePage) //Log into system as admin
    .exec(http("Update Price (Admin)")
      .put("/api/v1/adminbasicservice/adminbasic/prices") //Update price using admin service
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/PriceListAdmin/update_price_form.json")))
    .exec(http("Update Price")
      .put("/api/v1/priceservice/prices") //Update price using main service
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/PriceListAdmin/update_price_form.json")))
    .pause(1)

  //Test scenario to check general price endpoints
  val priceGeneral: ScenarioBuilder = scenario("Check General Price Endpoints")
    .exec(http("Get Price Service Welcome")
      .get("/api/v1/priceservice/prices/welcome")) //Get price service welcome
    .exec(http("Get Price by Route and Train")
      .get("/api/v1/priceservice/prices/9fc9c261-3263-4bfa-82f8-bb44e06b2f52/GaoTieOne")) //Get price by route and train
    .exec(http("Get Prices")
      .get("/api/v1/priceservice/prices")) //Get prices using main service

  //Run the test simulation with the scenarios
  setUp(
    priceAdd.inject(rampUsers(1).during(15)),
    priceDelete.inject(rampUsers(1).during(15)),
    priceUpdate.inject(rampUsers(1).during(10)),
    priceGeneral.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}