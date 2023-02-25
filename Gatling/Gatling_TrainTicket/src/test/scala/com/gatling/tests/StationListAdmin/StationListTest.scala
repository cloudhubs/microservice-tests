package com.gatling.tests.StationListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*


class StationListTest extends Simulation {

  /** operation = Add, file_path = , endpoint = adminbasicservice/adminbasic/stations */
  val stationAdd = scenario("Users Adding Station").exec(stationPage, action, stationPage)

  /** delete_id = 5307f68c-dc6d-4461-a262-354be961827f, type = Station, endpoint = adminbasicservice/adminbasic/stations */
  val stationDelete = scenario("Users Deleting Station").exec(stationPage, delete, stationPage)

  /** operation = Update, file_path = , endpoint = adminbasicservice/adminbasic/stations */
  val stationUpdate = scenario("Users Updating Station").exec(stationPage, action, stationPage)

  setUp(
    stationAdd.inject(rampUsers(20).during(15)),
    stationDelete.inject(rampUsers(20).during(15)),
    stationUpdate.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}