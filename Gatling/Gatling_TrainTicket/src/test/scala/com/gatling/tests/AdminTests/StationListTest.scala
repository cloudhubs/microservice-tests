package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.*
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class StationListTest extends Simulation {

  //Scenario that tests adding station
  val stationAdd: ScenarioBuilder = scenario("Admins Adding Station")
    .exec(loginScenario, stationPage) //Log into system as admin
    .exec(http("Add Station (Admin)")
      .post("/api/v1/adminbasicservice/adminbasic/stations")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/StationListAdmin/add_station_form.json")))
    .exec(http("Add Station")
      .post("/api/v1/stationservice/stations")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/StationListAdmin/add_station_form.json")))
    .exec(http("Add Station (ID List)")
      .post("/api/v1/stationservice/stations/idlist")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/StationListAdmin/add_station_form.json")))
    .exec(http("Add Station (Name List)")
      .post("/api/v1/stationservice/stations/namelist")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/StationListAdmin/add_station_form.json")))
    //Go to station page and complete add
    .pause(1)

  //Scenario that tests deleting station
  val stationDelete: ScenarioBuilder = scenario("Admins Deleting Station")
    .exec(loginScenario, stationPage)
    .exec(http("Delete Station (Admin)")
      .delete("/api/v1/adminbasicservice/adminbasic/stations/ac9f0ab0-9005-41e1-a4d2-2deb3535227f")
      .headers(apiV1Header))
    .exec(http("Delete Station")
      .delete("/api/v1/stationservice/stations/ac9f0ab0-9005-41e1-a4d2-2deb3535227f")
      .headers(apiV1Header))
    .pause(1)

  //Scenario that tests adding station
  val stationUpdate: ScenarioBuilder = scenario("Admins Updating Station")
    .exec(loginScenario, stationPage) //Log into system as admin
    .exec(http("Update Station (Admin)")
      .put("/api/v1/adminbasicservice/adminbasic/stations")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/StationListAdmin/update_station_form.json")))
    .exec(http("Delete Station")
      .delete("/api/v1/stationservice/stations/ac9f0ab0-9005-41e1-a4d2-2deb3535227f")
      .headers(apiV1Header))
    .exec(http("Update Station")
      .put("/api/v1/stationservice/stations")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/StationListAdmin/update_station_form.json")))
    //Go to station page and complete add
    .pause(1)

  val stationGeneral: ScenarioBuilder = scenario("Check General Station Endpoints")
    .exec(http("Get Station Service Welcome")
      .get("/api/v1/stationservice/welcome"))
    .exec(http("Get Stations")
      .get("/api/v1/stationservice/stations"))
    .exec(http("Get Station by ID")
      .get("/api/v1/stationservice/stations/id/0001a222-4e65-4341-bbb6-7ea9bbb228da"))
    .exec(http("Get Station by Name")
      .get("/api/v1/stationservice/stations/name/nanjing"))

  setUp(
    stationAdd.inject(rampUsers(1).during(15)),
    stationDelete.inject(rampUsers(1).during(15)),
    stationUpdate.inject(rampUsers(1).during(10)),
    stationGeneral.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}