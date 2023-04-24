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

  //Test scenario to check add (post) endpoints
  val contactAdd: ScenarioBuilder = scenario("Admins Adding Contact")
    .exec(loginScenario, contactsPage) //Log into system as admin
    .exec(http("Add Contact (Admin Service)")
      .post("/api/v1/adminbasicservice/adminbasic/contacts") //Add contact using admin service
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/ContactListAdmin/add_contact_form.json")))
    .exec(http("Add Contact")
      .post("/api/v1/contactservice/contacts") //Add contact using main service
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/ContactListAdmin/add_contact_form.json")))
    .exec(http("Add Contact (Admin)")
      .post("/api/v1/contactservice/contacts/admin") //Add admin contact
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/ContactListAdmin/add_contact_form.json")))
    .pause(1)

  //Test scenario to check deletion endpoints
  val contactDelete: ScenarioBuilder = scenario("Admins Deleting Contacts")
    .exec(loginScenario, configPage)
    .exec(http("Delete Contact (Admin)")
      .delete("/api/v1/adminbasicservice/adminbasic/contacts/6324ed73-482e-411b-bc26-d79b5bccec06")
      .headers(apiV1Header)) //Delete contact using admin service
    .exec(http("Delete Contact")
      .delete("/api/v1/contactservice/contacts/6324ed73-482e-411b-bc26-d79b5bccec06")
      .headers(apiV1Header)) //Delete contact using main service
    .pause(1)

  //Test scenario to check update (put) endpoints
  val contactUpdate: ScenarioBuilder = scenario("Admins Updating Contact")
    .exec(loginScenario, contactsPage) //Log into system as admin
    .exec(http("Update Contact (Admin)")
      .put("/api/v1/adminbasicservice/adminbasic/contacts") //Update contact using admin service
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/ContactListAdmin/update_contact_form.json")))
    .exec(http("Update Contact")
      .put("/api/v1/contactservice/contacts") //Update contact using main service
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/ContactListAdmin/update_contact_form.json")))
    .pause(1)

  //Test scenario to check general contact endpoints
  val contactGeneral: ScenarioBuilder = scenario("Check General Contact Endpoints")
    .exec(loginScenario)
    .exec(http("Get Contact Service Welcome")
        .get("/api/v1/contactservice/contacts/welcome") //Get contact service welcome
        .headers(apiV1Header))
    .exec(http("Get Contacts from Service")
        .get("/api/v1/contactservice/contacts") //Get all contacts
        .headers(apiV1Header))
    .exec(http("Get Contacts by Account ID")
        .get("/api/v1/contactservice/contacts/account/4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f")
        .headers(apiV1Header)) //Get contact by account id
    .exec(http("Get Contacts by ID")
        .get("/api/v1/contactservice/contacts/ac9f0ab0-9005-41e1-a4d2-2deb3535227f")
        .headers(apiV1Header)) //Get contact by contact id

  //Run the test simulation with the scenarios
  setUp(
    contactAdd.inject(rampUsers(1).during(15)),
    contactDelete.inject(rampUsers(1).during(15)),
    contactUpdate.inject(rampUsers(1).during(15)),
    contactGeneral.inject(rampUsers(1).during(15))
  ).protocols(httpProtocolTrainTicket)
}