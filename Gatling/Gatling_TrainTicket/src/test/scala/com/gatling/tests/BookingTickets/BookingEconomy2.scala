package com.gatling.tests.BookingTickets

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.NavigationModules.*

class BookingEconomy2 extends Simulation {

	val searchUsers = scenario("Users Searching").exec(homePage, searchTrip, homePage)

	val bookUsers = scenario("Users Booking").exec(homePage, searchTrip, selectTrip, submitTripBooking, homePage)

	setUp(
		searchUsers.inject(rampUsers(5).during(10)),
		bookUsers.inject(rampUsers(5).during(10))
	).protocols(httpProtocolTrainTicket)
}