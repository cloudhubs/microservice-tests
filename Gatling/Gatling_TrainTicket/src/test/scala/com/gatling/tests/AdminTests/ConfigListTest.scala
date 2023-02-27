package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.*
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class ConfigListTest extends Simulation {

  val configAdd: ScenarioBuilder = scenario("Admins Adding Config")
    .exec(adminLoginScenario)
    .exec { session =>
      val newSession = session.setAll("operation" -> "Add",
        "endpoint" -> "adminbasicservice/adminbasic/configs",
        "file_path" -> "ConfigListAdmin/add_config_form.json")
      newSession
    }
    //Go to home page and view cart
    .exec(configPage, action, configPage)
    .pause(1)

  val configDelete: ScenarioBuilder = scenario("Admins Deleting Config")
    .exec(adminLoginScenario)
    .exec { session =>
      val newSession = session.setAll("delete_id" -> "DirectTicketAllocationProportion",
        "endpoint" -> "adminbasicservice/adminbasic/configs",
        "type" -> "Config")
      newSession
    }
    //Go to home page and view cart
    .exec(configPage, delete, configPage)
    .pause(1)

  /**TODO: Same process as add just different file*/
  val configUpdate: ScenarioBuilder = scenario("Admins Updating Config")
    .exec(adminLoginScenario)
    .exec { session =>
      val newSession = session.setAll("operation" -> "Update",
        "endpoint" -> "adminbasicservice/adminbasic/configs",
        "file_path" -> "ConfigListAdmin/update_config_form.json")
      newSession
    }
    //Go to home page and view cart
    .exec(configPage, action, configPage)
    .pause(1)

  setUp(
    configAdd.inject(rampUsers(10).during(10)),
    configDelete.inject(rampUsers(10).during(10)),
    configUpdate.inject(rampUsers(10).during(10))
  ).protocols(httpProtocolTrainTicket)
}