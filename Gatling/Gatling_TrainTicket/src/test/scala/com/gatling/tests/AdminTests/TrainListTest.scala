package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.adminLoginScenario
import io.gatling.core.Predef.*
import io.gatling.core.structure.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class TrainListTest extends Simulation {

  val trainAdd: ScenarioBuilder = scenario("Admins Adding Train")
    .exec(adminLoginScenario)
    .exec { session =>
      val newSession = session.setAll("operation" -> "Add",
        "endpoint" -> "adminbasicservice/adminbasic/trains",
        "file_path" -> "TrainListAdmin/add_train_form.json")
      newSession
    }
    //Go to home page and view cart
    .exec(trainPage, action, trainPage)
    .pause(1)

  val trainDelete: ScenarioBuilder = scenario("Admins Deleting Train")
    .exec(adminLoginScenario)
    .exec { session =>
      val newSession = session.setAll("delete_id" -> "4d02f2f3-d08e-4bae-bf38-dc2c955f7afd",
        "endpoint" -> "adminbasicservice/adminbasic/trains",
        "type" -> "Train")
      newSession
    }
    //Go to home page and view cart
    .exec(trainPage, delete, trainPage)
    .pause(1)

  //TODO: Add feeder for update files

  setUp(
    trainAdd.inject(rampUsers(20).during(15)),
    trainDelete.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}