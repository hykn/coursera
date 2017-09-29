import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	
	private int n;
	private Item[] queue;

	public RandomizedQueue() {	
		n = 0;
		queue = (Item[]) new Object[1];

	}

	public boolean isEmpty() {
		return n == 0;
	}

   	public int size() {
   		return n;
   	}

   	private void resize(int capacity) {
   		Item[] copy = (Item[]) new Object[capacity];
   		for (int i = 0; i < n; i++)
   			copy[i] = queue[i];
   		queue = copy;
   	}

   	public void enqueue(Item item) {
   		if (item == null) throw new java.lang.NullPointerException();

   		if (queue.length == n) {
   			resize(queue.length * 2);
   			queue[n++] = item;
   		}
   		else queue[n++] = item;
   	}

   	public Item dequeue() {
   		if (isEmpty()) throw new java.util.NoSuchElementException();

   		int randomIndex = StdRandom.uniform(n);
   		Item returnitem = queue[randomIndex];

   		if (randomIndex == n - 1) queue[n - 1] = null;
   		else {
   			queue[randomIndex] = queue[n - 1];
   			queue[n - 1] = null;
   		}
   		if (n == queue.length / 4) resize(queue.length / 2);
   		n--;
   		return returnitem;

   	}

   	public Item sample() {
   		if (isEmpty()) throw new java.util.NoSuchElementException();
   		return queue[StdRandom.uniform(n)];
   	}

   	public Iterator<Item> iterator() {
   		return new RandomizedQueueIterator();
   	}

   	private class RandomizedQueueIterator implements Iterator<Item> {
		
		private int i = 0;
		private int[] indices;

		public RandomizedQueueIterator() {
			indices = new int[n];
			for (int j = 0; j < indices.length; j++)	indices[j] = j;
			StdRandom.shuffle(indices);
		}

		public boolean hasNext() 	{ return i < n;}
		public void remove()		{ throw new java.lang.UnsupportedOperationException(); }

		public Item next() {
			if (!hasNext()) throw new java.util.NoSuchElementException();
			return queue[indices[i++]];
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<Integer>();
		// randomizedQueue.enqueue(1);
		// randomizedQueue.enqueue(3);
		// randomizedQueue.enqueue(2);
		// randomizedQueue.enqueue(4);
		// randomizedQueue.enqueue(5);

		// StdOut.print("sample: " + randomizedQueue.sample() + '\n');
		// StdOut.print("sample: " + randomizedQueue.sample() + '\n');
		// StdOut.print("sample: " + randomizedQueue.sample() + '\n');
		// StdOut.print("sample: " + randomizedQueue.sample() + '\n');

		// int dequeueItem = randomizedQueue.dequeue();
		// StdOut.print("dequeueItem is " + dequeueItem + "\n");
		// StdOut.print("Output is: \n");
		// for (Integer x : randomizedQueue)
		// 	StdOut.print(x + " ");
	}

}
