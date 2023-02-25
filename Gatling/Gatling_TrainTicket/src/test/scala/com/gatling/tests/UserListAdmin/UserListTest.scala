package com.gatling.tests.UserListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*

class UserListTest extends Simulation {

  /** operation = Add, file_path = , endpoint = adminuserservice/users */
  val userAdd = scenario("Users Adding User").exec(userPage, action, userPage)

  /** delete_id = 6b5b0b6d-b233-4443-89e4-d28c72dc237b, type = User, endpoint = adminuserservice/users */
  val userDelete = scenario("Users Deleting User").exec(userPage, delete, userPage)

  /** operation = Update, file_path = , endpoint = adminuserservice/users */
  val userUpdate = scenario("Users Updating User").exec(userPage, action, userPage)

  setUp(
    userAdd.inject(rampUsers(20).during(15)),
    userDelete.inject(rampUsers(20).during(15)),
    userUpdate.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}