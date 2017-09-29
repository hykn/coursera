import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
	
	private int n;
	private Node first;
	private Node last;

	private class Node {
		private Item item;
		private Node previous;
		private Node next;
	}

	public Deque() {
		first = null;
		last = null;
		n = 0;
	}

	public boolean isEmpty() {
		return n == 0;
	}

	public int size() {
		return n;
	}

	public void addFirst(Item item) {
		
		if (item == null) throw new java.lang.NullPointerException();

		Node oldfirst = first;
		first = new Node();
		first.item = item;
		first.previous = null;
		if (isEmpty()) {
			last = first;
		}
		else {
			first.next = oldfirst;
			oldfirst.previous = first;
		}
		
		n++;
	}

	public void addLast(Item item) {

		if (item == null) throw new java.lang.NullPointerException();

		Node oldlast = last;
		last = new Node();
		last.item = item;
		last.next = null;
		if (isEmpty())
			first = last;
		else {
			last.previous = oldlast;
			oldlast.next = last;
		}

		n++;
	}

	public Item removeFirst() {
		if (isEmpty()) throw new java.util.NoSuchElementException();

		Item item = first.item;

		if (n == 1) {
			first = null;
			last = null;
		}
		else {
			first = first.next;
			first.previous = null;
		}
		n--;

		return item;

	}

	public Item removeLast() {
		if (isEmpty()) throw new java.util.NoSuchElementException();

		Item item = last.item;

		if (n == 1) {
			first = null;
			last = null;
		}
		else {
			last = last.previous;
			last.next = null;
		}

		n--;

		return item;

	}

	public Iterator<Item> iterator() { return new ListIterator(); }

	private class ListIterator implements Iterator<Item> {
		private Node current = first;

		public boolean hasNext() 	{ return current != null; }
		public void remove()		{ throw new java.lang.UnsupportedOperationException(); }

		public Item next() {
			if (!hasNext()) throw new java.util.NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}

	public static void main(String[] args) {
		Deque<Integer> deque = new Deque<Integer>();

		deque.isEmpty();
        deque.isEmpty();
        deque.isEmpty();
        deque.addFirst(3);
        deque.removeFirst();
        StdOut.println("Output: ");
		for (Integer x : deque) {
			StdOut.print(x + " ");
		}
		
	}

}
