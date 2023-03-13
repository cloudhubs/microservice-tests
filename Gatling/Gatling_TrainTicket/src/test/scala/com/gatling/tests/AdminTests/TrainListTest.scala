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

  //Scenario that tests adding train
  val trainAdd: ScenarioBuilder = scenario("Admins Adding Train")
    .exec(adminLoginScenario) //Log into system as admin
    .exec { session =>
      val newSession = session.setAll("operation" -> "Add",
        "endpoint" -> "adminbasicservice/adminbasic/trains",
        "file_path" -> "TrainListAdmin/add_train_form.json")
      newSession
    }
    //Go to train page and complete add
    .exec(trainPage, completeAction, trainPage)
    .pause(1)

  //Scenario that tests deleting train
  val trainDelete: ScenarioBuilder = scenario("Admins Deleting Train")
    .exec(adminLoginScenario)
    .exec { session => //Set up session information
      val newSession = session.setAll("delete_id" -> "4d02f2f3-d08e-4bae-bf38-dc2c955f7afd",
        "endpoint" -> "adminbasicservice/adminbasic/trains",
        "type" -> "Train")
      newSession
    }
    //Go to train page and delete train
    .exec(trainPage, delete, trainPage)
    .pause(1)

  val checkTrain = scenario("Check Train Endpoint")
    .exec(
      http("Check Train Seats")
        .post("/api/v1/seatservice/seats"))

  setUp(
    trainAdd.inject(rampUsers(20).during(15)),
    trainDelete.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}