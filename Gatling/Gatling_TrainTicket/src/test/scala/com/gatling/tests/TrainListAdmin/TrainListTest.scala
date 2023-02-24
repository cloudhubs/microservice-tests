package com.gatling.tests.TrainListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*
import io.gatling.core.structure._

import scala.concurrent.duration.*
import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*

class TrainListTest extends Simulation {

  val trainAdd = scenario("Users Adding Train").exec(trainPage, addTrain, trainPage)

  val trainDelete = scenario("Users Deleting Train").exec(trainPage, deleteTrain, trainPage)

  val trainUpdate = scenario("Users Updating Train").exec(trainPage, updateTrain, trainPage)

  setUp(
    trainAdd.inject(rampUsers(20).during(15)),
    trainDelete.inject(rampUsers(20).during(15)),
    trainUpdate.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}