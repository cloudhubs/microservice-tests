package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.adminLoginScenario
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class PriceListTest extends Simulation {

  //Scenario that tests adding price
  val priceAdd: ScenarioBuilder = scenario("Admins Adding Price")
    .exec(adminLoginScenario) //Log into system as admin
    .exec { session =>
      val newSession = session.setAll("operation" -> "Add",
        "endpoint" -> "adminbasicservice/adminbasic/prices",
        "file_path" -> "PriceListAdmin/add_price_form.json")
      newSession
    }
    //Go to price page and complete add
    .exec(pricePage, completeAction, pricePage)
    .pause(1)

  //Scenario that tests deleting price
  val priceDelete: ScenarioBuilder = scenario("Admins Deleting Price")
    .exec(adminLoginScenario)
    .exec { session => //Set up session information
      val newSession = session.setAll("delete_id" -> "dd0e572e-7443-420c-8280-7d8215636069",
        "endpoint" -> "adminbasicservice/adminbasic/prices",
        "type" -> "Price")
      newSession
    }
    //Go to price page and delete price
    .exec(pricePage, delete, pricePage)
    .pause(1)

  val checkPrice = scenario("Check Price Endpoints")
    .exec(
      http("Get Prices")
        .get("/api/v1/priceservice/prices"))

  setUp(
    //priceAdd.inject(rampUsers(20).during(15)),
    //priceDelete.inject(rampUsers(20).during(15)),
    checkPrice.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}