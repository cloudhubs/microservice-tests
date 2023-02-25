package com.gatling.tests.ConfigListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.AdminModules.*

class ConfigListTest extends Simulation {

  /**Change json to be add_config_invalid for invalid*/
  /** operation = Add, file_path = , endpoint = adminbasicservice/adminbasic/configs */
  val configAdd = scenario("Users Adding Config").exec(configPage, action, configPage)

  /** delete_id = TestConfig (config name), type = Config, endpoint = adminbasicservice/adminbasic/configs */
  val configDelete = scenario("Users Deleting Config").exec(configPage, delete, configPage)

  /** operation = Update, file_path = ,  endpoint = adminbasicservice/adminbasic/configs */
  val configUpdate = scenario("Users Updating Config").exec(configPage, action, configPage)

  setUp(
    configAdd.inject(rampUsers(20).during(10)),
    configDelete.inject(rampUsers(20).during(10)),
    configUpdate.inject(rampUsers(20).during(10))
  ).protocols(httpProtocolTrainTicket)
}