package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.adminLoginScenario
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class UserListTest extends Simulation {

  //Scenario that tests adding user
  val userAdd: ScenarioBuilder = scenario("Admins Adding User")
    .exec(adminLoginScenario) //Log into system as admin
    .exec { session =>
      val newSession = session.setAll("operation" -> "Add",
        "endpoint" -> "adminuserservice/users",
        "file_path" -> "UserListAdmin/add_user_form.json")
      newSession
    }
    //Go to user page and complete add
    .exec(userPage, action, userPage)
    .pause(1)

  //Scenario that tests deleting user
  val userDelete: ScenarioBuilder = scenario("Admins Deleting User")
    .exec(adminLoginScenario)
    .exec { session => //Set up session information
      val newSession = session.setAll("delete_id" -> "6b5b0b6d-b233-4443-89e4-d28c72dc237b",
        "endpoint" -> "adminuserservice/users",
        "type" -> "User")
      newSession
    }
    //Go to user page and delete user
    .exec(userPage, delete, userPage)
    .pause(1)

  //TODO: Add feeder for update files

  setUp(
    userAdd.inject(rampUsers(20).during(15)),
    userDelete.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}