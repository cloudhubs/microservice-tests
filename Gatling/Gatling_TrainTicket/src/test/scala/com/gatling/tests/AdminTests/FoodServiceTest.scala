package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*
import com.gatling.tests.Modules.LoginModule.*

class FoodServiceTest extends Simulation {

  val stationFoodService = scenario("Check Station Food Service")
    .exec(loginScenario)
    .exec(
      http("Get Station Food Service Welcome")
        .get("/api/v1/stationfoodservice/stationfoodstores/welcome"))
    .exec(
      http("Get Station Food Stores")
        .get("/api/v1/stationfoodservice/stationfoodstores"))
    .exec(
      http("Get Station Food By ID")
        .get("/api/v1/stationfoodservice/stationfoodstores/3fd67b64-3c80-4b51-823f-f1219247827f")
        .headers(apiV1Header))
    .exec(
      http("Post Station Food Store")
        .post("/api/v1/stationfoodservice/stationfoodstores")
        .body(RawFileBody("com/gatling/tests/station_food.json"))
        .headers(apiV1Header))
    .exec(
      http("Get Station Food Store By Store ID")
        .get("/api/v1/stationfoodservice/stationfoodstores/bystoreid/e7431fd9-7202-4834-90d6-e33f045dfd1a"))

  val trainFoodService = scenario("Check Train Food Service")
    .exec(
      http("Get Train Food Service Welcome")
        .get("/api/v1/trainfoodservice/trainfoods/welcome"))
    .exec(
      http("Get Train Food")
        .get("/api/v1/trainfoodservice/trainfoods"))
    .exec(
      http("Get Train Food by Trip ID")
        .get("/api/v1/trainfoodservice/trainfoods/G1234"))

  val foodDeliveryService = scenario("Check Food Delivery")
    .exec(
      http("Get Food Delivery Service Welcome")
        .get("/api/v1/fooddeliveryservice/welcome"))
    .exec(
      http("Get Food Delivery Order")
        .get("/api/v1/fooddeliveryservice/orders/a7e77fa2-8197-4682-9f6e-85e7d34d90a8"))
    .exec(
      http("Get All Food Delivery")
        .get("/api/v1/fooddeliveryservice/orders/all"))
    .exec(
      http("Get Food Delivery Order")
        .get("/api/v1/fooddeliveryservice/orders/store/a7e77fa2-8197-4682-9f6e-85e7d34d90a8"))
    .exec(
      http("Delete Food Delivery Order")
        .delete("/api/v1/fooddeliveryservice/orders/d/a7e77fa2-8197-4682-9f6e-85e7d34d90a8"))
    .exec(
      http("Add Food Delivery")
        .post("/api/v1/fooddeliveryservice/orders")
        .body(RawFileBody("com/gatling/tests/station_food.json")))
    .exec(
      http("Update Food Delivery by Trip ID")
        .put("/api/v1/fooddeliveryservice/orders/tripid")
        .body(RawFileBody("com/gatling/tests/station_food.json")))
    .exec(
      http("Update Food Delivery by Seat Num")
        .put("/api/v1/fooddeliveryservice/orders/seatnos")
        .body(RawFileBody("com/gatling/tests/station_food.json")))
    .exec(
      http("Update Food Delivery by Departure Time")
        .put("/api/v1/fooddeliveryservice/orders/dtime")
        .body(RawFileBody("com/gatling/tests/station_food.json")))

  val foodService = scenario("Check Food Service Endpoints")
    .exec(
      http("Get Food Service Welcome")
        .get("/api/v1/foodservice/welcome"))
    .exec(
      http("Get Food Service Test Delivery")
        .get("/api/v1/foodservice/test_send_delivery"))
    .exec(
      http("Get Food Service Orders")
        .get("/api/v1/foodservice/orders"))
    .exec(
      http("Get Food Service Orders by Order ID")
        .get("/api/v1/foodservice/orders/a7e77fa2-8197-4682-9f6e-85e7d34d90a8"))
    .exec(
      http("Get Food Service Orders by Date")
        .get("/api/v1/foodservice/foods/08:00-21:00/shanghai/taiyuan/a7e77fa2-8197-4682-9f6e-85e7d34d90a8"))
    .exec(
      http("Add Food Order")
        .post("/api/v1/foodservice/orders")
        .body(RawFileBody("com/gatling/tests/station_food.json")))
    .exec(
      http("Add Food Order Batch")
        .post("/api/v1/foodservice/createOrderBatch")
        .body(RawFileBody("com/gatling/tests/station_food.json")))
    .exec(
      http("Update Food Order")
        .put("/api/v1/foodservice/orders")
        .body(RawFileBody("com/gatling/tests/station_food.json")))
    .exec(
      http("Delete Food Order")
        .delete("/api/v1/foodservice/orders/a7e77fa2-8197-4682-9f6e-85e7d34d90a8"))

  val foodMapService = scenario("Check Food Map Service Endpoints")
    .exec(loginScenario)
    .exec(
      http("Get Food Stores")
        .get("/api/v1/foodmapservice/foodstores")
        .headers(apiV1Header))
    .exec(
      http("Get Food Stores by Station ID")
        .get("/api/v1/foodmapservice/foodstores/3fd67b64-3c80-4b51-823f-f1219247827f")
        .headers(apiV1Header))
    .exec(
      http("Get Train Foods")
        .get("/api/v1/foodmapservice/trainfoods")
        .headers(apiV1Header))
    .exec(
      http("Get Train Foods by Trip ID")
        .get("/api/v1/foodmapservice/trainfoods/3fd67b64-3c80-4b51-823f-f1219247827f")
        .headers(apiV1Header))
    .exec(
      http("Add Food Store")
        .post("/api/v1/foodmapservice/foodstores")
        .body(RawFileBody("com/gatling/tests/station_food.json"))
        .headers(apiV1Header))

  setUp(
    //stationFoodService.inject(rampUsers(1).during(10)),
    //trainFoodService.inject(rampUsers(1).during(10)),
    //foodDeliveryService.inject(rampUsers(1).during(10)),
    foodService.inject(rampUsers(1).during(10)),
    //foodMapService.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}
