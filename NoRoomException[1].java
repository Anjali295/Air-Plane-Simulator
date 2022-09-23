class NoRoomException extends Exception{
	

	public NoRoomException() {
		super("No room available !");
	}

}

class NoPassengerException extends Exception{
	
	public NoPassengerException() {
		super("No passenger !");
	}
}
