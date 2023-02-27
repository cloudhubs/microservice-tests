package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.*
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class ContactListTest extends Simulation {

  val contactAdd: ScenarioBuilder = scenario("Admins Adding Contact")
    .exec(adminLoginScenario)
    .exec { session =>
      val newSession = session.setAll("operation" -> "Add",
        "endpoint" -> "adminbasicservice/adminbasic/contacts",
        "file_path" -> "ContactListAdmin/add_contact_form.json")
      newSession
    }
    //Go to home page and view cart
    .exec(contactsPage, action, contactsPage)
    .pause(1)

  val contactDelete: ScenarioBuilder = scenario("Admins Deleting Contacts")
    .exec(adminLoginScenario)
    .exec { session =>
      val newSession = session.setAll("delete_id" -> "5729b7bf-f1c8-4d82-8773-9bbb682489f5",
        "endpoint" -> "adminbasicservice/adminbasic/configs",
        "type" -> "Contacts")
      newSession
    }
    //Go to home page and view cart
    .exec(configPage, delete, configPage)
    .pause(1)

  /**TODO: Same process as add just different file*/
  val contactUpdate: ScenarioBuilder = scenario("Admins Updating Contact")
    .exec(adminLoginScenario)
    .exec { session =>
      val newSession = session.setAll("operation" -> "Update",
        "endpoint" -> "adminbasicservice/adminbasic/contacts",
        "file_path" -> "ContactListAdmin/update_contact_form.json")
      newSession
    }
    //Go to home page and view cart
    .exec(contactsPage, action, contactsPage)
    .pause(1)

  setUp(
    contactAdd.inject(rampUsers(10).during(15)),
    contactDelete.inject(rampUsers(15).during(15)),
    contactUpdate.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}