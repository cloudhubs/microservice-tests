package com.gatling.tests.ContactListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.AdminModules.*

class ContactListTest extends Simulation {

  val contactAdd = scenario("Users Adding Contact").exec(contactsPage, addContact, contactsPage)

  val contactDelete = scenario("Users Deleting Contact").exec(contactsPage, deleteContact, contactsPage)

  val contactUpdate = scenario("Users Updating Contact").exec(contactsPage, updateContact, contactsPage)

  setUp(
    contactAdd.inject(rampUsers(20).during(15)),
    contactDelete.inject(rampUsers(20).during(15)),
    contactUpdate.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}