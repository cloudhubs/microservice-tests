package com.gatling.tests.StationListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*


class StationListTest extends Simulation {

  /**Change json for invalid add*/
  val stationAdd = scenario("Users Adding Station").exec(stationPage, addStation, stationPage)

  val stationDelete = scenario("Users Deleting Station").exec(stationPage, deleteStation, stationPage)

  val stationUpdate = scenario("Users Updating Station").exec(stationPage, updateStation, stationPage)

  setUp(
    stationAdd.inject(rampUsers(20).during(15)),
    stationDelete.inject(rampUsers(20).during(15)),
    stationUpdate.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}