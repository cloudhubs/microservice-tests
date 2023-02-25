package com.gatling.tests.OrderListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.AdminModules.*

class OrderListAdminTest extends Simulation {

  /** operation = Add, file_path = , endpoint = adminorderservice/adminorder */
  val orderAdd = scenario("Admin Adding Order").exec(adminOrderPage, action, adminOrderPage)

  /** delete_id = 301f39ba-f31d-4795-bac2-cbc8909a7e97/G1237, type = Order, endpoint = adminorderservice/adminorder */
  val orderDelete = scenario("Admin Deleting Order").exec(adminOrderPage, delete, adminOrderPage)

  /** operation = Update, file_path = , endpoint = adminorderservice/adminorder */
  val orderUpdate = scenario("Admin Updating Order").exec(adminOrderPage, action, adminOrderPage)

  setUp(
    orderAdd.inject(rampUsers(20).during(15)),
    orderDelete.inject(rampUsers(20).during(15)),
    orderUpdate.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}