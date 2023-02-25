package com.gatling.tests.ContactListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.AdminModules.*

class ContactListTest extends Simulation {

  /** operation = Add, file_path = , endpoint = adminbasicservice/adminbasic/contacts */
  val contactAdd = scenario("Users Adding Contact").exec(contactsPage, action, contactsPage)

  /** delete_id = 5729b7bf-f1c8-4d82-8773-9bbb682489f5, type = Contact, endpoint = adminbasicservice/adminbasic/contacts */
  val contactDelete = scenario("Users Deleting Contact").exec(contactsPage, delete, contactsPage)

  /** operation = Update, file_path = , endpoint = adminbasicservice/adminbasic/contacts */
  val contactUpdate = scenario("Users Updating Contact").exec(contactsPage, action, contactsPage)

  setUp(
    contactAdd.inject(rampUsers(20).during(15)),
    contactDelete.inject(rampUsers(20).during(15)),
    contactUpdate.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}