package com.gatling.tests

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class FeederDemo extends Simulation {

  // protocol
  val httpProtocol = http.baseUrl("https://computer-database.gatling.io")

  // scenario
  val feeder = csv("data/accounts.csv").circular

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
