
public class Passenger {
	private int passengerID;
	private int arrivalTime;
	enum TravelClass{
		 FirstClass,
		 BuisnessClass,
		 PremiumEconomy,
		 Economy;
	 }
	private Passenger.TravelClass travelClass;
	
	public Passenger() {
		passengerID = 0;
		arrivalTime = 0;
		
	}
	public Passenger(int passenger, int arrival, Passenger.TravelClass Travel) {
		passengerID = passenger;
		arrivalTime = arrival;
		travelClass = Travel;
				
	}
	public Passenger.TravelClass getTravelClass(){
		return travelClass;
	}
	public void setTravelClass(Passenger.TravelClass c) {
		travelClass = c;
	}
	public int getPassengerID() {
		return passengerID;
	}
	public int getArrivalTime() {
		return arrivalTime;
	}
	public void setPassengerID(int a) {
		passengerID = a;
	}
	public void setArrivalTime(int c) {
		arrivalTime = c;
	}
	 

}
