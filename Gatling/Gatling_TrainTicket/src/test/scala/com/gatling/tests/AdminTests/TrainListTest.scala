package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.*
import io.gatling.core.Predef.*
import io.gatling.core.structure.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class TrainListTest extends Simulation {

  //Test scenario to check add (post) endpoints
  val trainAdd: ScenarioBuilder = scenario("Admins Adding Train")
    .exec(loginScenario, trainPage) //Log into system as admin
    .exec(http("Add Train (Admin)")
      .post("/api/v1/adminbasicservice/adminbasic/trains") //Add train using admin service
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/TrainListAdmin/add_train_form.json")))
    .exec(http("Add Train")
      .post("/api/v1/trainservice/trains") //Add train using main service
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/TrainListAdmin/add_train_form.json")))
    .exec(http("Add Train by Names")
      .post("/api/v1/trainservice/trains/byNames") //Add train by names
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/TrainListAdmin/add_train_form.json")))
    .pause(1)

  //Test scenario to check deletion endpoints
  val trainDelete: ScenarioBuilder = scenario("Admins Deleting Train")
    .exec(loginScenario, trainPage)
    .exec(http("Delete Train (Admin)") //Delete train using admin service
      .delete("/api/v1/adminbasicservice/adminbasic/trains/4d02f2f3-d08e-4bae-bf38-dc2c955f7afd")
      .headers(apiV1Header))
    .exec(http("Delete Train") //Delete train using main service
      .delete("/api/v1/trainservice/trains/4d02f2f3-d08e-4bae-bf38-dc2c955f7afd")
      .headers(apiV1Header))
    .pause(1)

  //Test scenario to check update (put) endpoints
  val trainUpdate: ScenarioBuilder = scenario("Admins Updating Train")
    .exec(loginScenario, trainPage) //Log into system as admin
    .exec(http("Update Train (Admin)")
      .put("/api/v1/adminbasicservice/adminbasic/trains") //Update train using admin service
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/TrainListAdmin/update_train_form.json")))
    .exec(http("Update Train")
      .put("/api/v1/trainservice/trains") //Update train using main service
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/TrainListAdmin/update_train_form.json")))
    .pause(1)

  //Test scenario to check general train service endpoints
  val trainGeneral: ScenarioBuilder = scenario("Check General Train Endpoints")
    .exec(http("Get Train Service Welcome")
      .get("/api/v1/trainservice/trains/welcome")) //Get train service welcome
    .exec(http("Get Trains")
      .get("/api/v1/trainservice/trains")) //Get trains from service
    .exec(http("Get Train by ID")
      .get("/api/v1/trainservice/trains/0f2fcaa7-46aa-49df-932f-476f327f112e")) //Get train by id
    .exec(http("Get Train by Name")
      .get("/api/v1/trainservice/trains/byName/KuaiSu")) //Get train by name

  //Run the test simulation with the scenarios
  setUp(
    trainAdd.inject(rampUsers(1).during(15)),
    trainDelete.inject(rampUsers(1).during(15)),
    trainUpdate.inject(rampUsers(1).during(10)),
    trainGeneral.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}