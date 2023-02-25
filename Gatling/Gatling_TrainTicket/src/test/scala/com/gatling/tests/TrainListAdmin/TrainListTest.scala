package com.gatling.tests.TrainListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*
import io.gatling.core.structure._

import scala.concurrent.duration.*
import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*

class TrainListTest extends Simulation {

  /** operation = Add, file_path = , endpoint = adminbasicservice/adminbasic/trains */
  val trainAdd = scenario("Users Adding Train").exec(trainPage, action, trainPage)

  /** delete_id = 4d02f2f3-d08e-4bae-bf38-dc2c955f7afd, type = Train, endpoint = adminbasicservice/adminbasic/trains */
  val trainDelete = scenario("Users Deleting Train").exec(trainPage, delete, trainPage)

  /** operation = Update, file_path = , endpoint = adminbasicservice/adminbasic/trains */
  val trainUpdate = scenario("Users Updating Train").exec(trainPage, action, trainPage)

  setUp(
    trainAdd.inject(rampUsers(20).during(15)),
    trainDelete.inject(rampUsers(20).during(15)),
    trainUpdate.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}