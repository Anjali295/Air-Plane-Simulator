public class BoardingQueue implements Cloneable {
	public final int Capacity = 10;
	private int front;
	private int rear;
	private int size;
	private Passenger[] data;

	public BoardingQueue() {
		front = -1;
		rear = -1;
		data = new Passenger[Capacity];

	}

	public BoardingQueue(int f, int r, int capacity) {
		front = f;
		rear = r;
		data = new Passenger[capacity];
	}

	public boolean isEmpty() {
		return front == -1;
	}

	public void enqueuePassenger(Passenger newPass) throws NoRoomException {
		if ((rear + 1) % Capacity == front) {
			throw new NoRoomException();
		}
		if (front == -1) {
			front = 0;
			rear = 0;
		} else {
			rear = (rear + 1) % Capacity;
		}
		data[rear] = newPass;
		size++;
	}

	public Passenger dequeuePassenger() throws NoPassengerException {
		Passenger answer;
		if (front == -1) {
			throw new NoPassengerException();
		}
		answer = data[front];
		if (front == rear) {
			front = -1;
			rear = -1;

		} else {
			front = (front + 1) % Capacity;
		}
		size--;
		return answer;
		

	}

	public int getFront() {
		return front;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}


