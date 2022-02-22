package edu.uwm.cs351;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

//KrischanWoehrer , Assignment 3, CS 351
/**
 * A variant of the ParticleSeq ADT that follows the Collection model.
 * 
 * @author Krischan
 */
public class ParticleCollection extends AbstractCollection<Particle> implements Cloneable, Iterable<Particle> {

	private Particle[] data;
	private int manyItems;
	private int version;

	private static int INITIAL_CAPACITY = 1;

	private static boolean doReport = true; // changed only by invariant tester

	private boolean report(String error) {
		if (doReport)
			System.out.println("Invariant error: " + error);
		else
			System.out.println("Caught problem: " + error);
		return false;
	}

	// Change invariant
	private boolean wellFormed() {
		// Check the invariant.
		// 1. data is never null
		if (data == null) {
			return report("data is null");
		}

		// 2. The data array is at least as long as the number of items
		// claimed by the sequence.
		if (this.data.length < this.manyItems) {
			return report("manyItems is greater than the data.length");
		}

		// 3. Many items must be positive.
		if (this.manyItems < 0) {
			return report("Many items is less than 0.");
		}

		// If no problems discovered, return true
		return true;
	}

	/**
	 * Initialize an empty collection with an initial capacity of INITIAL_CAPACITY.
	 * The add methods work efficiently (without needing more memory) until this
	 * capacity is reached.
	 * 
	 * @param - none
	 * @postcondition This collection is empty and has an initial capacity of
	 *                INITIAL_CAPACITY
	 * @exception OutOfMemoryError Indicates insufficient memory for initial array.
	 **/
	public ParticleCollection() {
		this(INITIAL_CAPACITY);
		assert wellFormed() : "Invariant false at end of constructor";
	}

	/**
	 * Initialize an empty collection with a specified initial capacity. Note that
	 * the add methods work efficiently (without needing more memory) until this
	 * capacity is reached.
	 * 
	 * @param initialCapacity the initial capacity of this sequence
	 * @precondition initialCapacity is non-negative.
	 * @postcondition This sequence is empty and has the given initial capacity.
	 * @exception IllegalArgumentException Indicates that initialCapacity is
	 *                                     negative.
	 * @exception OutOfMemoryError         Indicates insufficient memory for an
	 *                                     array with this many elements. new
	 *                                     Particle[initialCapacity].
	 **/
	public ParticleCollection(int initialCapacity) {
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("initialCapacity must not be negative.");
		}
		this.data = new Particle[initialCapacity];
		this.manyItems = 0;
		assert wellFormed() : "Invariant false at end of constructor";
	}
	
	/**
	 * @see java.util.AbstractCollection
	 */
	@Override // functionality
	public boolean add(Particle element) {
		assert wellFormed() : "invariant failed at start of add";
		this.ensureCapacity(++this.manyItems);
		this.data[this.manyItems - 1] = element;

		++this.version;
		assert wellFormed() : "invariant failed at end of add";
		return true;
	}

	/**
	 * @see java.util.AbstractCollection
	 */
	@Override // Functionality
	public void clear() {
		if (this.manyItems == 0) {
			return;
		}
		this.data = new Particle[INITIAL_CAPACITY];
		this.manyItems = 0;
		this.version++;
		assert wellFormed() : "invariant failed at start of clear";
	}
	
	/**
	 * @see java.util.AbstractCollection
	 */
	@Override // Required
	public int size() {
		return this.manyItems;
	}

	/**
	 * Generate a copy of this collection.
	 * 
	 * @param - none
	 * @return The return value is a copy of this sequence. Subsequent changes to
	 *         the copy will not affect the original, nor vice versa.
	 * @exception OutOfMemoryError Indicates insufficient memory for creating the
	 *                             clone.
	 **/
	@Override // implementation
	public ParticleCollection clone() {
		assert wellFormed() : "invariant failed at start of clone";
		ParticleCollection copyCollection;
		try {
			copyCollection = (ParticleCollection) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("This class does not implement cloneable");
		}
		// Currently cloning 1 past what we want to
		copyCollection.data = new Particle[this.data.length];
		// using add all here is ineffecient and messes with values in the clone
		for (int i = 0; i < this.size(); i++) {
			copyCollection.data[i] = this.data[i];
		}

		assert wellFormed() : "invariant failed at end of clone";
		assert copyCollection.wellFormed() : "invariant failed for clone";
		return copyCollection;
	}

	/**
	 * Change the current capacity of this sequence if needed.
	 * 
	 * @param minimumCapacity the new capacity for this sequence
	 * @postcondition This sequence's capacity has been changed to at least
	 *                minimumCapacity. If the capacity was already at or greater
	 *                than minimumCapacity, then the capacity is left unchanged. If
	 *                the capacity is changed, it must be at least twice as big as
	 *                before.
	 * @exception OutOfMemoryError Indicates insufficient memory for: new array of
	 *                             minimumCapacity elements.
	 **/
	private void ensureCapacity(int minimumCapacity) {
		if (this.data.length < minimumCapacity) {
			Particle[] newArr;
			if (this.data.length * 2 < minimumCapacity) {
				newArr = new Particle[minimumCapacity];
			} else {
				newArr = new Particle[this.data.length * 2];
			}

			for (int i = 0; i < this.data.length; i++) {
				newArr[i] = this.data[i];
			}

			this.data = newArr;
		}
		// This is a private method: don't check invariants
	}

	@Override // required
	public Iterator<Particle> iterator() {
		return new MyIterator();
	}

	private class MyIterator implements Iterator<Particle> {
		private int current;
		private int version;
		private int next;
		// Can also compare next and current for canRemove. I think canRemove is more
		// intuitive.
		private boolean canRemove;

		public boolean wellFormed() {
			if (!ParticleCollection.this.wellFormed())
				return false;
			if (version != ParticleCollection.this.version)
				return true; // Our code should handle this, not the invariant we throw exception for this
			if (current < 0 || current > manyItems)
				return report("current out of range: " + current + " not in range [0," + manyItems + "]");
			if (next < 0 || next > manyItems)
				return report("next out of range: " + next + " not in range[0," + manyItems + "]");
			if (next != current && next != current + 1)
				return report("next " + next + " isn't current or its successor (current = " + current + ")");

			return true;
		}

		public MyIterator() {
			this.current = -1;
			this.next = 0;
			this.version = ParticleCollection.this.version;
			this.canRemove = false;
		}

		/**
		 * Returns if there is a next particle
		 * @see java.util.Iterator
		 */
		@Override // required
		public boolean hasNext() {
			// I guess if its empty we always exit
			// Fail-Fast implementation
			if (this.version != ParticleCollection.this.version) {
				throw new ConcurrentModificationException("Fail-Fast Version mismatch");
			}

			if (this.next < ParticleCollection.this.manyItems && this.next >= 0) {
				return true;
			} else {
				return false;
			}

			// Invariant not required as we do not have any changes to the data structure
		}

		/**
		 * Returns the next particle if there is one
		 * @see java.util.Iterator
		 */
		@Override // required
		public Particle next() {
			// Fail-Fast implementation is called by hasNext()

			if (!this.hasNext() || next >= ParticleCollection.this.manyItems) {
				throw new NoSuchElementException("There is no next element in this iterator.");
			}
			this.current++;
			this.next++;
			this.canRemove = true;
			assert wellFormed() : "invariant failed at end of next";
			return (Particle) data[current];

		}

		/**
		 * Remove the current element
		 * @see java.util.Iterator
		 */
		@Override // functionality
		public void remove() {

			// Fail-Fast implementation
			if (this.version != ParticleCollection.this.version) {
				throw new ConcurrentModificationException("Fail-Fast Version mismatch");
			}
			// Can also use current >= next instead of canRemove
			if (!this.canRemove) {
				throw new IllegalStateException("Cannot remove twice in a row without calling next.");
			}

			assert wellFormed() : "invariant failed at start of remove";
			for (int i = current; i < data.length - 1; i++) {
				data[i] = data[i + 1];
			}

			ParticleCollection.this.manyItems--;
			this.canRemove = false;
			this.current--;
			this.next--;

			// Adjust Version number
			++this.version;
			++ParticleCollection.this.version;
		}
	}

}
