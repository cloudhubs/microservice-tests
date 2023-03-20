package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.{adminLoginScenario, apiV1Header}
import io.gatling.core.Predef.*
import io.gatling.core.structure.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class TrainListTest extends Simulation {

  //Scenario that tests adding train
  val trainAdd: ScenarioBuilder = scenario("Admins Adding Train")
    .exec(adminLoginScenario, trainPage) //Log into system as admin
    //Go to train page and complete add
    .exec(http("Add Train (Admin)")
      .post("/api/v1/adminbasicservice/adminbasic/trains")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/TrainListAdmin/add_train_form.json")))
    .exec(http("Add Train")
      .post("/api/v1/trainservice/trains")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/TrainListAdmin/add_train_form.json")))
    .exec(http("Add Train by Names")
      .post("/api/v1/trainservice/trains/byNames")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/TrainListAdmin/add_train_form.json")))
    .pause(1)

  //Scenario that tests deleting train
  val trainDelete: ScenarioBuilder = scenario("Admins Deleting Train")
    .exec(adminLoginScenario, trainPage)
    //Go to train page and delete train
    .exec(http("Delete Train (Admin)")
      .delete("/api/v1/adminbasicservice/adminbasic/trains/4d02f2f3-d08e-4bae-bf38-dc2c955f7afd")
      .headers(apiV1Header))
    .exec(http("Delete Train")
      .delete("/api/v1/trainservice/trains/4d02f2f3-d08e-4bae-bf38-dc2c955f7afd")
      .headers(apiV1Header))
    .pause(1)

  val trainUpdate: ScenarioBuilder = scenario("Admins Updating Train")
    .exec(adminLoginScenario, trainPage) //Log into system as admin
    //Go to train page and complete add
    .exec(http("Update Train (Admin)")
      .put("/api/v1/adminbasicservice/adminbasic/trains")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/TrainListAdmin/add_train_form.json")))
    .exec(http("Update Train")
      .put("/api/v1/trainservice/trains")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/TrainListAdmin/add_train_form.json")))
    .pause(1)

  val trainGeneral: ScenarioBuilder = scenario("Check General Train Endpoints")
    .exec(http("Get Train Service Welcome")
      .get("/api/v1/trainservice/trains/welcome"))
    .exec(http("Get Trains")
      .get("/api/v1/trainservice/trains"))
    .exec(http("Get Train by ID")
      .get("/api/v1/trainservice/trains/0f2fcaa7-46aa-49df-932f-476f327f112e"))
    .exec(http("Get Train by Name")
      .get("/api/v1/trainservice/trains/byName/KuaiSu"))

  setUp(
    trainAdd.inject(rampUsers(1).during(15)),
    trainDelete.inject(rampUsers(1).during(15)),
    trainUpdate.inject(rampUsers(1).during(10)),
    trainGeneral.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}