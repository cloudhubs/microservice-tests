package com.gatling.tests.ContactListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.AdminModules.*

class AddContact extends Simulation {

	val users = scenario("Users Adding Contact").exec(contactsPage, addContact, contactsPage)

	setUp(
		users.inject(rampUsers(20).during(15))
	).protocols(httpProtocol)
}