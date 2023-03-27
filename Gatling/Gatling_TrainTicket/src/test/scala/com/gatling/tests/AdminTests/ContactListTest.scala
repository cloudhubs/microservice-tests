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

  //Scenario that tests adding contact
  val contactAdd: ScenarioBuilder = scenario("Admins Adding Contact")
    .exec(adminLoginScenario) //Log into system as admin
    .exec { session =>
      val newSession = session.setAll("operation" -> "Add", //Set up general session info
        "endpoint" -> "adminbasicservice/adminbasic/contacts",
        "file_path" -> "ContactListAdmin/add_contact_form.json")
      newSession
    }
    //Go to contacts page and complete add
    .exec(contactsPage, completeAction, contactsPage)
    .pause(1)

  //Scenario that tests deleting contact
  val contactDelete: ScenarioBuilder = scenario("Admins Deleting Contacts")
    .exec(adminLoginScenario)
    .exec { session => //Set up session information
      val newSession = session.setAll("delete_id" -> "5729b7bf-f1c8-4d82-8773-9bbb682489f5",
        "endpoint" -> "adminbasicservice/adminbasic/configs",
        "type" -> "Contacts")
      newSession
    }
    //Go to contact page and delete contact
    .exec(configPage, delete, configPage)
    .pause(1)

  /**TODO: Same process as add just different file*/

  setUp(
    contactAdd.inject(rampUsers(10).during(15)),
    contactDelete.inject(rampUsers(15).during(15))
  ).protocols(httpProtocolTrainTicket)
}