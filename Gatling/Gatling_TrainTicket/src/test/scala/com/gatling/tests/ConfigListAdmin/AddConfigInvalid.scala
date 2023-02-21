package com.gatling.tests.ConfigListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.AdminModules.*

class AddConfigInvalid extends Simulation {

	/**Change json to be add_config_invalid*/
	val user = scenario("Users Adding Config").exec(configPage, addConfig, configPage)

	setUp(
		user.inject(rampUsers(20).during(10))
	).protocols(httpProtocolTrainTicket)
}