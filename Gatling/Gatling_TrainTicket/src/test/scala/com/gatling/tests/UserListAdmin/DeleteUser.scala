package com.gatling.tests.UserListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*

class DeleteUser extends Simulation {

  val users = scenario("Users Deleting User").exec(userPage, deleteUser, userPage)

  setUp(
    users.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}