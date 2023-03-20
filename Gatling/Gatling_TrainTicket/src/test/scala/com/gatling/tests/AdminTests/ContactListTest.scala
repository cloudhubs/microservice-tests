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
    .exec(adminLoginScenario, contactsPage) //Log into system as admin
    .exec(http("Add Contact (Admin Service)")
      .post("/api/v1/adminbasicservice/adminbasic/contacts")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/ContactListAdmin/add_contact_form.json")))
    //Go to contacts page and complete add
    .exec(http("Add Contact")
      .post("/api/v1/contactservice/contacts")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/ContactListAdmin/add_contact_form.json")))
    .exec(http("Add Contact (Admin)")
      .post("/api/v1/contactservice/contacts/admin")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/ContactListAdmin/add_contact_form.json")))
    .pause(1)

  //Scenario that tests deleting contact
  val contactDelete: ScenarioBuilder = scenario("Admins Deleting Contacts")
    .exec(adminLoginScenario, configPage)
    .exec(http("Delete Contact (Admin)")
      .delete("/api/v1/adminbasicservice/adminbasic/contacts/459731a3-70a5-4c93-acbc-e0c42fd1813d")
      .headers(apiV1Header))
    //Go to contact page and delete contact
    .exec(
      http("Delete Contact")
        .delete("/api/v1/contactservice/contacts/b4206321-eece-41a0-9525-fcab6fd594d3")
        .headers(apiV1Header))
    .pause(1)

  val contactUpdate: ScenarioBuilder = scenario("Admins Updating Contact")
    .exec(adminLoginScenario, contactsPage) //Log into system as admin
    .exec(http("Update Contact (Admin)")
      .put("/api/v1/adminbasicservice/adminbasic/contacts")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/ContactListAdmin/add_contact_form.json")))
    .exec(http("Update Contact")
      .put("/api/v1/contactservice/contacts")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/ContactListAdmin/add_contact_form.json")))
    .pause(1)

  val contactGeneral = scenario("Check General Contact Endpoints")
    .exec(adminLoginScenario)
    .exec(
      http("Get Contact Service Welcome")
        .get("/api/v1/contactservice/contacts/welcome")
        .headers(apiV1Header))
    .exec(
      http("Get Contacts from Service")
        .get("/api/v1/contactservice/contacts")
        .headers(apiV1Header))
    .exec(
      http("Get Contacts by Account ID")
        .get("/api/v1/contactservice/contacts/account/4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f")
        .headers(apiV1Header))
    .exec(
      http("Get Contacts by ID")
        .get("/api/v1/contactservice/contacts/ac9f0ab0-9005-41e1-a4d2-2deb3535227f")
        .headers(apiV1Header))

  setUp(
    contactAdd.inject(rampUsers(1).during(15)),
    contactDelete.inject(rampUsers(1).during(15)),
    contactUpdate.inject(rampUsers(1).during(15)),
    contactGeneral.inject(rampUsers(1).during(15))
  ).protocols(httpProtocolTrainTicket)
}