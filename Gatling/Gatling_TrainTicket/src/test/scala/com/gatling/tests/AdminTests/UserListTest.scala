package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*
import com.gatling.tests.Modules.LoginModule.*

import scala.concurrent.duration.*

class UserListTest extends Simulation {

  //Scenario that tests adding user
  val userAdd: ScenarioBuilder = scenario("Admins Adding User")
    .exec(loginScenario, userPage) //Log into system as admin
    .exec(http("Add User")
      .post("/api/v1/adminuserservice/users")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/UserListAdmin/add_user_form.json")))
    .pause(1)

  //Scenario that tests deleting user
  val userDelete: ScenarioBuilder = scenario("Admins Deleting User")
    .exec(loginScenario, userPage)
    .exec(http("Delete User")
      .delete("/api/v1/adminuserservice/users/6b5b0b6d-b233-4443-89e4-d28c72dc237b")
      .headers(apiV1Header))
    .pause(1)

  val userUpdate: ScenarioBuilder = scenario("Admins Updating User")
    .exec(loginScenario, userPage) //Log into system as admin
    .exec(
      http("Update User")
        .put("/api/v1/adminuserservice/users")
        .body(RawFileBody("com/gatling/tests/UserListAdmin/add_user_form.json"))
        .headers(apiV1Header))
    .pause(1)

  val userGeneral = scenario("Check General User Endpoints")
    .exec(loginScenario)
    .exec(
      http("Get Admin User Service Welcome")
        .get("/api/v1/adminuserservice/users/welcome")
        .headers(apiV1Header))
    .exec(
      http("Get All Users")
        .get("/api/v1/userservice/users"))
    .exec(
      http("Get User by ID")
        .get("/api/v1/userservice/users/id/4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f"))
    .exec(
      http("Get User by Username")
        .get("/api/v1/userservice/users/fdse_microservice"))
    .exec(
      http("Delete User by ID")
        .delete("/api/v1/userservice/users/4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f")
        .headers(apiV1Header))
    .exec(
      http("Register User")
        .post("/api/v1/userservice/users/register")
        .body(RawFileBody("com/gatling/tests/UserListAdmin/add_user_form.json"))
        .headers(apiV1Header))

  setUp(
    userAdd.inject(rampUsers(1).during(15)),
    userDelete.inject(rampUsers(1).during(15)),
    userUpdate.inject(rampUsers(1).during(15)),
    userGeneral.inject(rampUsers(1).during(15))
  ).protocols(httpProtocolTrainTicket)
}