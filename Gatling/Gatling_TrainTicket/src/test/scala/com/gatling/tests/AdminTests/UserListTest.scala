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

  //Test scenario to check add (post) endpoints
  val userAdd: ScenarioBuilder = scenario("Admins Adding User")
    .exec(loginScenario, userPage) //Log into system as admin
    .exec(http("Add User")
      .post("/api/v1/adminuserservice/users") //Add user using admin service
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/UserListAdmin/add_user_form.json")))
    .pause(1)

  //Test scenario to check deletion endpoints
  val userDelete: ScenarioBuilder = scenario("Admins Deleting User")
    .exec(loginScenario, userPage)
    .exec(http("Delete User") //Delete user using admin service
      .delete("/api/v1/adminuserservice/users/6b5b0b6d-b233-4443-89e4-d28c72dc237b")
      .headers(apiV1Header))
    .pause(1)

  //Test scenario to check update (put) endpoints
  val userUpdate: ScenarioBuilder = scenario("Admins Updating User")
    .exec(loginScenario, userPage) //Log into system as admin
    .exec(http("Update User")
      .put("/api/v1/adminuserservice/users") //Update user using admin service
      .body(RawFileBody("com/gatling/tests/UserListAdmin/add_user_form.json"))
      .headers(apiV1Header))
    .pause(1)

  //Test scenario to check general user endpoints
  val userGeneral: ScenarioBuilder = scenario("Check General User Endpoints")
    .exec(loginScenario)
    .exec(http("Get Admin User Service Welcome")
      .get("/api/v1/adminuserservice/users/welcome") //Get admin user service welcome
      .headers(apiV1Header))
    .exec(http("Get All Users")
      .get("/api/v1/userservice/users")) //Get all users
    .exec(http("Get User by ID")
      .get("/api/v1/userservice/users/id/4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f"))
    .exec(http("Get User by Username") //Get user by username
      .get("/api/v1/userservice/users/fdse_microservice"))
    .exec(http("Delete User by ID")
      .delete("/api/v1/userservice/users/4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f") //Delete user by id
      .headers(apiV1Header))
    .exec(http("Register User")
      .post("/api/v1/userservice/users/register") //Add user register
      .body(RawFileBody("com/gatling/tests/UserListAdmin/add_user_form.json"))
      .headers(apiV1Header))

  //Run the test simulation with the scenarios
  setUp(
    userAdd.inject(rampUsers(1).during(15)),
    userDelete.inject(rampUsers(1).during(15)),
    userUpdate.inject(rampUsers(1).during(15)),
    userGeneral.inject(rampUsers(1).during(15))
  ).protocols(httpProtocolTrainTicket)
}