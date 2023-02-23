package com.gatling.tests.other

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*

class FeederDemo extends Simulation {

  // protocol
  val httpProtocol = http.baseUrl("https://computer-database.gatling.io")

  // scenario
  val feeder = csv("data/valid_accounts.csv").circular

  val scn = scenario("Feeder Demo")
    .repeat(1) {
      feed(feeder)
        .exec { session =>
          println("Name: " + session("name").as[String])
          println("Email: " + session("email").as[String])
          println("Page: " + session("page").as[String])
          session
        }
        .pause(1)
        .exec(http("GoTo ${page}")
        .get("/#{page}"))
    }

  // setup
  setUp(scn.inject(atOnceUsers(3))).protocols(httpProtocol)
}
