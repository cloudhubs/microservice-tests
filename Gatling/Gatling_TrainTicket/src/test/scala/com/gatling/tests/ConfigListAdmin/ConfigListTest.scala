package com.gatling.tests.ConfigListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.AdminModules.*

class ConfigListTest extends Simulation {

  /**Change json to be add_config_invalid for invalid*/
  val configAdd = scenario("Users Adding Config").exec(configPage, addConfig, configPage)

  val configDelete = scenario("Users Deleting Config").exec(configPage, deleteConfig, configPage)

  val configUpdate = scenario("Users Updating Config").exec(configPage, updateConfig, configPage)

  setUp(
    configAdd.inject(rampUsers(20).during(10)),
    configDelete.inject(rampUsers(20).during(10)),
    configUpdate.inject(rampUsers(20).during(10))
  ).protocols(httpProtocolTrainTicket)
}