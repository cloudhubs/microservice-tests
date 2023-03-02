package com.gatling.tests.UserTests

import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.*
import com.gatling.tests.Modules.UserModules.*
import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class BookingTest extends Simulation {

	val bookUsers = scenario("Users Booking")
		.exec(userLoginScenario)
		.exec { session =>
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

		setUp(
			bookUsers.inject(rampUsers(5).during(10))
		).protocols(httpProtocolTrainTicket)
}