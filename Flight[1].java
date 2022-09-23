
public class Flight {
	private String destination;
	private BoardingQueue boardQueue;
	private Passenger[] firstClass = new Passenger[2];
	private Passenger[] buisnessClass = new Passenger[3];
	private Passenger[] premiumEconomy = new Passenger[4];
	private Passenger[] economy = new Passenger[6];
	private int minutesLeftBoarding;
	private int minutesLeftDeparting;
	private int firstClassInd =0;
	private int buisnessClassInd =0 ;
	private int premiumEconomyInd= 0;
	private int economyInd=0 ;
	private boolean boarding;
	private boolean isAirForce;
	
	private int count = firstClassInd+ buisnessClassInd+ premiumEconomyInd + economyInd;
	public Flight() {
		boardQueue=new BoardingQueue();
		setDestination("");
		setMinutesLeftBoarding(25);
		setMinutesLeftDeparting(30);
	}
	public Flight(String des, int minLeft, int minLeftDepart) {
		setDestination(des);
		setMinutesLeftBoarding(minLeft);
	//	minutesLeftDeparting = minLeftDepart;
	}
	public void addToFlight(Passenger boardedPass) {
		if(boardedPass.getTravelClass()== Passenger.TravelClass.FirstClass) {
			if(firstClassInd>=2) {
				if(buisnessClassInd<3) {
					buisnessClass[buisnessClassInd]= boardedPass;
					buisnessClassInd++;
					setCount(getCount() + 1);
				}else if(buisnessClassInd>=3) {
					if(premiumEconomyInd < 4) {
						premiumEconomy[premiumEconomyInd]= boardedPass;
					    premiumEconomyInd++;
					    setCount(getCount() + 1);
					}else if(premiumEconomyInd>=4) {
						if(economyInd < economy.length) {
							economy[economyInd]= boardedPass;
							economyInd++;
							setCount(getCount() + 1);
						}else return ;
					}
				}
				
			}else {
			firstClass[firstClassInd]= boardedPass;
			firstClassInd++;
			setCount(getCount() + 1);
			}
		}else if(boardedPass.getTravelClass()== Passenger.TravelClass.BuisnessClass) {
			if(buisnessClassInd>=3) {
				if(premiumEconomyInd < 4) {
					premiumEconomy[premiumEconomyInd]= boardedPass;
				    premiumEconomyInd++;
				    setCount(getCount() + 1);
				}else if(premiumEconomyInd>=4) {
					if(economyInd < economy.length) {
						economy[economyInd]= boardedPass;
						economyInd++;
						setCount(getCount() + 1);
					}else return ;
				}
				
			}else {
			buisnessClass[buisnessClassInd]= boardedPass;
			buisnessClassInd++;
			setCount(getCount() + 1);
			}
		}else if(boardedPass.getTravelClass()== Passenger.TravelClass.PremiumEconomy) {
			if(premiumEconomyInd>=4) {
				if(economyInd < economy.length) {
					economy[economyInd]= boardedPass;
					economyInd++;
					setCount(getCount() + 1);
				}else 
					return;
				
				
			}else {
			premiumEconomy[premiumEconomyInd]= boardedPass;
		    premiumEconomyInd++;
		    setCount(getCount() + 1);
			}
		}else if(boardedPass.getTravelClass()== Passenger.TravelClass.Economy) {
			if(economyInd>=economy.length) {
				return ;
			}else {
			economy[economyInd]= boardedPass;
			economyInd++;
			setCount(getCount() + 1);
			}
		}
	}
	public int getMinutesLeftBoarding() {
		return minutesLeftBoarding;
	}
	public void setMinutesLeftBoarding(int minutesLeftBoarding) {
		this.minutesLeftBoarding = minutesLeftBoarding;
	}
	public int getMinutesLeftDeparting() {
		return minutesLeftDeparting;
	}
	public void setMinutesLeftDeparting(int minutesLeftDeparting) {
		this.minutesLeftDeparting = minutesLeftDeparting;
	}
	public boolean isBoarding() {
		return boarding;
	}
	public void setBoarding(boolean boarding) {
		this.boarding = boarding;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public BoardingQueue getBoardQueue() {
		return boardQueue;
	}
	public void setBoardQueue(BoardingQueue boardQueue) {
		this.boardQueue = boardQueue;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public void sort(Passenger[] p) {
		Passenger temp;
		
		for(int i=0;i<p.length;i++) {
			
			if(p[i]==null) continue;
			
			
			for(int j=0;j<p.length-1;j++) {
				if(p[j+1]== null) continue;
				
				if(p[j].getArrivalTime()> p[j+1].getArrivalTime()) {
					temp= p[j];
					p[j] = p[j+1];
					p[j+1]= temp;	
				}
			}
		}
	}
	public String toString() {
		String st="";
		st +=" Seat Type    |  ID  | Arrival Time "  + '\n';

		sort(firstClass);
		sort(buisnessClass);
		sort(premiumEconomy);
		sort(economy);
		for(int i=0;i<firstClass.length;i++) {
			if(firstClass.length!=0 && firstClass[i]!= null) {
				st += "FirstClass       " + firstClass[i].getPassengerID()+ "      "+  firstClass[i].getArrivalTime()+ '\n';
			}
		}
		for(int i=0;i<buisnessClass.length;i++) {
			if(buisnessClass.length!=0 && buisnessClass[i]!= null) {
				st += "Buisness Class   "+ buisnessClass[i].getPassengerID()+ "     "+ buisnessClass[i].getArrivalTime() + '\n';
			}
		}
		for(int i=0; i< premiumEconomy.length;i++) {
			if(premiumEconomy.length!=0 && premiumEconomy[i]!= null ) {
				st += "Premium Economy  "+ premiumEconomy[i].getPassengerID()+"      "+ premiumEconomy[i].getArrivalTime() + '\n';
			}
		}
		for(int i=0;i< economy.length;i++) {
			if(economy.length!=0 && economy[i]!= null) {
				st += "Economy Class    "+ economy[i].getPassengerID()+ "      "+economy[i].getArrivalTime() + '\n';
			}
		}
		
		return st;
	}
	public boolean isAirForce() {
		return isAirForce;
	}
	public void setAirForce(boolean isAirForce) {
		this.isAirForce = isAirForce;
	}

}
