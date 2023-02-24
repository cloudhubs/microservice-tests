package com.gatling.tests.UserListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*

class UserListTest extends Simulation {

  val userAdd = scenario("Users Adding User").exec(userPage, addUser, userPage)

  val userDelete = scenario("Users Deleting User").exec(userPage, deleteUser, userPage)

  val userUpdate = scenario("Users Updating User").exec(userPage, updateUser, userPage)

  setUp(
    userAdd.inject(rampUsers(20).during(15)),
    userDelete.inject(rampUsers(20).during(15)),
    userUpdate.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}