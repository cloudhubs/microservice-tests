package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.HeaderModules.{httpProtocolTrainTicket}
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*
import com.gatling.tests.Modules.LoginModule.*

class FoodServiceTest extends Simulation {

  val checkStationFood = scenario("Check Station Food Service")
    .exec(adminLoginScenario)
    .exec(
      http("Get Station Food Service Welcome")
        .get("/api/v1/stationfoodservice/stationfoodstores/welcome"))
    .exec(
      http("Get Station Food Stores")
        .get("/api/v1/stationfoodservice/stationfoodstores"))
    .exec(
      http("Get Station Food By ID")
        .get("/api/v1/stationfoodservice/stationfoodstores/e7431fd9-7202-4834-90d6-e33f045dfd1a")
        .headers(apiV1Header))
    .exec(
      http("Post Station Food Store")
        .post("/api/v1/stationfoodservice/stationfoodstores")
        .body(RawFileBody("com/gatling/tests/station_food.json"))
        .headers(apiV1Header))
    .exec(
      http("Get Station Food Store By Store ID")
        .get("/api/v1/stationfoodservice/stationfoodstores/bystoreid/e7431fd9-7202-4834-90d6-e33f045dfd1a"))

  val checkTrainFood = scenario("Check Train Food Service")
    .exec(
      http("Get Train Food Service Welcome")
        .get("/api/v1/trainfoodservice/trainfoods/welcome"))
    .exec(
      http("Get Train Food")
        .get("/api/v1/trainfoodservice/trainfoods"))
    .exec(
      http("Get Train Food by Trip ID")
        .get("/api/v1/trainfoodservice/trainfoods/G1234"))

  val checkFoodDelivery = scenario("Check Food Delivery")
    .exec(
      http("Get Food Delivery Order")
        .get("/api/v1/fooddeliveryservice/orders/a7e77fa2-8197-4682-9f6e-85e7d34d90a8"))
    .exec(
      http("Delete Food Delivery Order")
        .delete("/api/v1/fooddeliveryservice/orders/a7e77fa2-8197-4682-9f6e-85e7d34d90a8"))

  val checkFoodService = scenario("Check Food Service Endpoints")
    .exec(
      http("Get Food Service Welcome")
        .get("/api/v1/foodservice/welcome"))
    .exec(
      http("Get Food Service Test Delivery")
        .get("/api/v1/foodservice/test_send_delivery"))

  setUp(
    checkStationFood.inject(rampUsers(1).during(10)),
    //checkTrainFood.inject(rampUsers(1).during(10)),
    //checkFoodDelivery.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}
