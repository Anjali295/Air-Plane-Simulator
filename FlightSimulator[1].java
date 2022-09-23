import java.util.Random;

import java.util.Scanner;

public class FlightSimulator {
	static Flight flightTerminals[] = new Flight[20];
	static Random randomNumberGenerator = new Random();

	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);
		System.out.println("Welcome to RFK International airport !!!!");
		System.out.println("Enter a seed for this program: ");
		long seed = stdin.nextLong();
		System.out.println("Enter a probability of a passenger arrival: ");
		float pass = stdin.nextFloat();
		System.out.println("Enter a probability that a passenger is dequeued: ");
		float passDequeue = stdin.nextFloat();
		System.out.println("Enter a probability that there is a new flight at RFK: ");
		float newFlight = stdin.nextFloat();
		System.out.println("Enter how many minutes this simulation should take: ");
		int min = stdin.nextInt();
		System.out.println("Starting simulation...........");

		String event1 = "";
		String currBoarding = "";
		String departing = "";
		String finalDeparture = "";
		int covidCount = 0;

		randomNumberGenerator.setSeed(seed);

		boolean boardingAirForce = false;

		if ((pass < 0 || pass > 1) || (passDequeue < 0 || passDequeue > 1) || (newFlight < 0 || newFlight > 1)) {
			throw new IllegalArgumentException();
		}

		int currentPassId = 1;

		// For every minute
		for (int currentTime = 0; currentTime < min; currentTime++) {

			System.out.println("Minute " + currentTime);
			System.out.println();
			float rand = randomNumberGenerator.nextFloat();

//			System.out.println("Rand num: " + rand);

			event1 = "";
			currBoarding = "";
			departing = "";
			finalDeparture = "";

			if (rand < newFlight) {

				if (!boardingAirForce) {
					Flight f = new Flight();
					float rand2 = randomNumberGenerator.nextFloat();
					System.out.println("Enter the destination: ");
					String des;
					des = stdin.next();

					f.setDestination(des);

					

					if (rand2 < 0.95) {

						for (int w = 0; w < flightTerminals.length; w++) {
							if (flightTerminals[w] == null) {
								flightTerminals[w] = f;
								f.setAirForce(false);
								event1 += "A new Flight to " + des + " has begun boarding !" + '\n';

								break;
							}
						}

					} else {
						f.setAirForce(true);
						for (int s = 0; s < flightTerminals.length; s++) {
							if (flightTerminals[s] == null) {
								flightTerminals[s] = f;
								event1 += "A new Flight on Air Force 1 to " + des + " has begun boarding!" + '\n';
								break;
							}
						}
						boardingAirForce = true;

					}
				}

			}

			for (int i = 0; i < flightTerminals.length; i++) {

				Flight boardingFlight = flightTerminals[i];

				// Step 1
				if (boardingFlight == null)
					continue;

				if (boardingAirForce && !boardingFlight.isAirForce()) {

					continue;
				}

				// Is time left for boarding 0?

				if (boardingFlight.getMinutesLeftDeparting() == 0) {

					event1 += boardingFlight.getDestination() + " has departed " + '\n';
					finalDeparture += "Flight: RFK--> " + boardingFlight.getDestination() + '\n';
					finalDeparture += boardingFlight.toString();
				}

				if (boardingFlight.getMinutesLeftDeparting() <= 0) {

					if (boardingFlight.isAirForce() == true) {
						boardingAirForce = false;
					}

					flightTerminals[i] = null;

					flightTerminals[flightTerminals.length - 1] = null;

					i--;
					continue;
				}

				if (rand < passDequeue) {

					if (boardingFlight.getMinutesLeftBoarding() > 0) {
						if (boardingFlight == null)
							continue;

						try {
							Passenger person = boardingFlight.getBoardQueue().dequeuePassenger();
							boardingFlight.addToFlight(person);

							event1 += person.getTravelClass() + "(" + person.getPassengerID() + ")" + " on flight to "
									+ boardingFlight.getDestination() + " has boarded on a " + person.getTravelClass()
									+ " seat!" + '\n';

						} catch (NoPassengerException e) {
							System.out.println("");
						}
					}

				}

				// Step 2 - Does passenger arrive?
				if (rand < pass) {

					if (boardingFlight.getMinutesLeftBoarding() > 0) {
						Passenger newPass = new Passenger();
						newPass.setPassengerID(currentPassId++);
						newPass.setArrivalTime(currentTime);

						float tenPercent = pass * 0.1f;
						try {

							if (rand < tenPercent) {
								newPass.setTravelClass(Passenger.TravelClass.FirstClass);

								boardingFlight.getBoardQueue().enqueuePassenger(newPass);
								event1 += "First Class Passenger (" + newPass.getPassengerID() + ") on flight to "
										+ boardingFlight.getDestination() + " has entered the flight's boarding queue!"
										+ '\n';

							} else if (rand < tenPercent * 2) {
								newPass.setTravelClass(Passenger.TravelClass.BuisnessClass);
								boardingFlight.getBoardQueue().enqueuePassenger(newPass);
								event1 += "Buisness Class Passenger (" + newPass.getPassengerID() + ") on flight to "
										+ boardingFlight.getDestination() + " has entered the flight's boarding queue!"
										+ '\n';

							} else if (rand < (tenPercent * 5)) {
								newPass.setTravelClass(Passenger.TravelClass.PremiumEconomy);
								boardingFlight.getBoardQueue().enqueuePassenger(newPass);
								event1 += "Premium Economy Class Passenger (" + newPass.getPassengerID()
										+ ") on flight to " + boardingFlight.getDestination()
										+ " has entered the flight's boarding queue!" + '\n';

							} else if (rand < (tenPercent * 9)) {
								newPass.setTravelClass(Passenger.TravelClass.Economy);
								boardingFlight.getBoardQueue().enqueuePassenger(newPass);
								event1 += "Economy Class Passenger (" + newPass.getPassengerID() + ") on flight to "
										+ boardingFlight.getDestination() + " has entered the flight's boarding queue!"
										+ '\n';

							} else {
								// Has COVID
								covidCount++;
								
								for (int z = 0; z < flightTerminals.length; z++) {
									Flight f2 = flightTerminals[z];

									if (f2 == null)
										continue;
									int q = 0;
									int countOfAirForce = 0;
									while (q < flightTerminals.length) {
										if (flightTerminals[q] == null) {
											q++;
											continue;
										}
										if (flightTerminals[q].isAirForce()) {
											countOfAirForce++;
										}
										q++;
									}

									if (countOfAirForce >= 1) {

										if (f2.isAirForce() && f2.isBoarding()) {
											f2.setMinutesLeftBoarding(f2.getMinutesLeftBoarding() + 10);
										} else if (f2.isAirForce() && !f2.isBoarding()) {
											f2.setMinutesLeftBoarding(f2.getMinutesLeftBoarding() + 11);
											f2.setMinutesLeftDeparting(f2.getMinutesLeftDeparting() + 11);

										}
									} else {
										if (f2.isBoarding()) {
											f2.setMinutesLeftBoarding(f2.getMinutesLeftBoarding() + 10);

										} else {
											f2.setMinutesLeftDeparting(f2.getMinutesLeftDeparting() + 11);
											f2.setMinutesLeftBoarding(f2.getMinutesLeftBoarding() + 11);
										}
									}

									if (covidCount == 1 && countOfAirForce >= 1) {
										f2.setMinutesLeftBoarding(f2.getMinutesLeftBoarding() + 1);
									}

								}
								event1 += "COVID Positive passenger found attempting to board flight to "
										+ boardingFlight.getDestination()
										+ "! All current departures and boarding extended by 10 minutes!" + '\n';

							}
						} catch (NoRoomException e) {
							System.out.println("Flight is full !");
						}
					}
				}

				if (boardingFlight.getMinutesLeftBoarding() > 0 && boardingFlight.getCount() <= 15) {
					if (boardingFlight.isAirForce()) {
						currBoarding += "Flight to " + boardingFlight.getDestination() + " has "
								+ boardingFlight.getMinutesLeftBoarding() + " minutes to board,"
								+ boardingFlight.getCount() + " passenger(s), and "
								+ boardingFlight.getBoardQueue().getSize() + " person(s) waiting to board." + '\n';
						currBoarding += "Boarding will resume once Air Force 1 departs" + '\n';
					} else
						currBoarding += "Flight to " + boardingFlight.getDestination() + " has "
								+ boardingFlight.getMinutesLeftBoarding() + " minutes to board,"
								+ boardingFlight.getCount() + " passenger(s), and "
								+ boardingFlight.getBoardQueue().getSize() + " person(s) waiting to board." + '\n';
				}

				if (boardingFlight.getMinutesLeftDeparting() <= 5 && boardingFlight.getMinutesLeftDeparting() > 0) {
					if (boardingFlight.isAirForce()) {
						departing += "Flight to " + boardingFlight.getDestination() + " has "
								+ boardingFlight.getCount() + " passengers and "
								+ boardingFlight.getMinutesLeftDeparting() + " minutes before departure" + '\n';
						departing += "Departures will resume once Air Force 1 departs" + '\n';
					} else
						departing += "Flight to " + boardingFlight.getDestination() + " has "
								+ boardingFlight.getCount() + " passengers and "
								+ boardingFlight.getMinutesLeftDeparting() + " minutes before departure" + '\n';
				}

				boardingFlight.setMinutesLeftBoarding(boardingFlight.getMinutesLeftBoarding() - 1);
				boardingFlight.setMinutesLeftDeparting(boardingFlight.getMinutesLeftDeparting() - 1);
				if (boardingFlight.getCount() == 15) {
					boardingFlight.setMinutesLeftDeparting(5);
				}

			}
			if (event1 == "") {
				System.out.println("Events: \n" + "Nothing to note.");
				System.out.println();
			} else {
				System.out.println("Events: \n" + event1);
			}
			if (currBoarding == "") {
				System.out.println("Currently Boarding: \n" + "Nothing to note.");
				System.out.println();
			} else {
				System.out.println("Currently Boarding: \n" + currBoarding);
			}
			if (departing == "") {
				System.out.println("Departing:  \n" + "Nothing to note.");
				System.out.println();
			} else {
				System.out.println("Departing:  \n" + departing);
			}
			if (finalDeparture == "") {
				System.out.println("Final Departures:  \n" + "Nothing to note.");
				System.out.println();
			} else
				System.out.println("Final Departures:  \n" + finalDeparture);
			System.out.println();
		}
		
		System.out.println("Minute: "+ min);
		System.out.println("Simulation terminated. Thank you for choosing RFK!");
		stdin.close();

	}

}
