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

  //Test scenario to check to add (put) endpoints
  val stationAdd: ScenarioBuilder = scenario("Admins Adding Station")
    .exec(loginScenario, stationPage) //Log into system as admin
    .exec(http("Add Station (Admin)")
      .post("/api/v1/adminbasicservice/adminbasic/stations") //Add station using admin service
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/StationListAdmin/add_station_form.json")))
    .exec(http("Add Station")
      .post("/api/v1/stationservice/stations") //Add station using main service
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/StationListAdmin/add_station_form.json")))
    .exec(http("Add Station (ID List)")
      .post("/api/v1/stationservice/stations/idlist") //Add station using id list
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/StationListAdmin/add_station_form.json")))
    .exec(http("Add Station (Name List)")
      .post("/api/v1/stationservice/stations/namelist") //Add station using name list
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/StationListAdmin/add_station_form.json")))
    .pause(1)

  //Test scenario to check deletion endpoints
  val stationDelete: ScenarioBuilder = scenario("Admins Deleting Station")
    .exec(loginScenario, stationPage)
    .exec(http("Delete Station (Admin)") //Delete station using admin service
      .delete("/api/v1/adminbasicservice/adminbasic/stations/ac9f0ab0-9005-41e1-a4d2-2deb3535227f")
      .headers(apiV1Header))
    .exec(http("Delete Station") //Delete station using main service
      .delete("/api/v1/stationservice/stations/ac9f0ab0-9005-41e1-a4d2-2deb3535227f")
      .headers(apiV1Header))
    .pause(1)

  //Test scenario to check update (put) endpoints
  val stationUpdate: ScenarioBuilder = scenario("Admins Updating Station")
    .exec(loginScenario, stationPage) //Log into system as admin
    .exec(http("Update Station (Admin)")
      .put("/api/v1/adminbasicservice/adminbasic/stations") //Update station using admin service
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/StationListAdmin/update_station_form.json")))
    .exec(http("Delete Station") //Delete station to prime for update
      .delete("/api/v1/stationservice/stations/ac9f0ab0-9005-41e1-a4d2-2deb3535227f")
      .headers(apiV1Header))
    .exec(http("Update Station")
      .put("/api/v1/stationservice/stations") //Update station using main service
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/StationListAdmin/update_station_form.json")))
    //Go to station page and complete add
    .pause(1)

  //Test scenario to check general station endpoints
  val stationGeneral: ScenarioBuilder = scenario("Check General Station Endpoints")
    .exec(http("Get Station Service Welcome")
      .get("/api/v1/stationservice/welcome")) //Get station service welcome
    .exec(http("Get Stations")
      .get("/api/v1/stationservice/stations")) //Get stations
    .exec(http("Get Station by ID")
      .get("/api/v1/stationservice/stations/id/0001a222-4e65-4341-bbb6-7ea9bbb228da")) //Get stations by id
    .exec(http("Get Station by Name")
      .get("/api/v1/stationservice/stations/name/nanjing")) //Get station by name

  //Run the test simulation with the scenarios
  setUp(
    stationAdd.inject(rampUsers(1).during(15)),
    stationDelete.inject(rampUsers(1).during(15)),
    stationUpdate.inject(rampUsers(1).during(10)),
    stationGeneral.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}