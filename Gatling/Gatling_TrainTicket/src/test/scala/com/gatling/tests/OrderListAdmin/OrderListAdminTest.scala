package com.gatling.tests.OrderListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.AdminModules.*

class OrderListAdminTest extends Simulation {

  val orderAdd = scenario("Admin Adding Order").exec(adminOrderPage, addOrderAdmin, adminOrderPage)

  val orderDelete = scenario("Admin Deleting Order").exec(adminOrderPage, deleteOrder, adminOrderPage)

  val orderUpdate = scenario("Admin Updating Order").exec(adminOrderPage, addOrderAdmin, adminOrderPage)

  setUp(
    orderAdd.inject(rampUsers(20).during(15)),
    orderDelete.inject(rampUsers(20).during(15)),
    orderUpdate.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}