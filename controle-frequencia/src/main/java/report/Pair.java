package report;

public class Pair<T1, T2> {
	private T1 firstElement;
	private T2 secondElement;
	
	public Pair(T1 obj1, T2 obj2) {
		this.setFirstElement(obj1);
		this.setSecondElement(obj2);
	}

	public T1 getFirstElement() {
		return firstElement;
	}

	public void setFirstElement(T1 firstElement) {
		this.firstElement = firstElement;
	}

	public T2 getSecondElement() {
		return secondElement;
	}

	public void setSecondElement(T2 secondElement) {
		this.secondElement = secondElement;
	}
}
