package com.gatling.tests.UserTests

import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.*
import com.gatling.tests.Modules.UserModules.*
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class BookingTest extends Simulation {

	//Test scenario to check book test case
	val bookUsers: ScenarioBuilder = scenario("Users Booking")
		.exec(loginScenario)
		.pause(1)
		.exec { session => //Create session with information
			val newSession = session.setAll("trip_id" -> "D1345", //Select Needs
				"from" -> "shanghai",
				"to" -> "suzhou",
				"seat_type" -> "2",
				"seat_price" -> "50.0",
				"date" -> "2023-02-16",
				"login_id" -> "4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f",
				"submit_file" -> "Booking/booking_1st_class_form.json", //Submit Needs
				"search_file" -> "Booking/search_form.json") //Search Needs
			newSession
		}
		.exec(searchTrip, selectTrip, submitTripBooking, homePage)

	//Run the test simulation with the scenarios
	setUp(
			bookUsers.inject(rampUsers(2500).during(30))
		).protocols(httpProtocolTrainTicket)
}