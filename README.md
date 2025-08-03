# Assumptions and Design Decisions
* Used SQL database since strong consistency is needed
* Uses pessimistic locking when booking a seat
* Uses lombok to generate boilerplate code
* HATEOAS is not considered
* Does not consider transit scenarios when searching
* When flight booking, payment processing is not considered
* Assumes only adults are travelling
* Assumes only one-way trips are considered
* Flight frequency is assumed to be daily
* Assumes that this air line has only one type of airplane and therefore all of them have the same seat configuration
* A passenger can select either economy or business class seat. But cannot select a specific seat number. The system assigns the available seat number.
* Create and save a passenger’s flight booking API
  * Assumes that the user would search for available flights which sends the flight instance Id. User would send this id to do the booking
  * We would not create a separate table to store Bookings due to time constraints
  * Assumes that the user can book only one seat per request

# Improvements
* Search for available flights API should provide pagination

# Missing APIs
* Update passenger details on an existing booking
* Cancel an existing passenger’s flight booking

# Curls
curl --location 'http://localhost:8080/v1.0/flight/NZ_1'

curl --location 'http://localhost:8080/v1.0/flight/search?origin=Auckland&destination=Wellington&departureDate=2025-08-03&numOfSeats=4'

curl --location 'http://localhost:8080/v1.0/booking' \
--header 'Content-Type: application/json' \
--data '{
"flightInstanceId" : 2,
"seatType" : "BUSINESS_CLASS"
}'

# How to run the tests
* Please go to the following test classes and run or else use the "gradle test" command and open the report under "build/reports/tests/test/index.html" 
* Unit test classes
  * FlightBookingServiceTest
  * FlightSearchServiceTest
* Component tests
  * FlightBookingComponentTest
  * FlightSearchComponentTest

# How to run the application
* Build the application using "gradle build" command
* Run "ExerciseApplication"
