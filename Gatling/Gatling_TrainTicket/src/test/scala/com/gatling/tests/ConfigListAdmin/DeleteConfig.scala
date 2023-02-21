package com.gatling.tests.ConfigListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.AdminModules.*

class DeleteConfig extends Simulation {

	val user = scenario("Users Deleting Config").exec(configPage, deleteConfig, configPage)

	setUp(
		user.inject(rampUsers(20).during(10))
	).protocols(httpProtocolTrainTicket)
}