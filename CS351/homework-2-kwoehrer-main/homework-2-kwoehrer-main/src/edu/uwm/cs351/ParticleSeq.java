// This is an assignment for students to complete after reading Chapter 3 of
// "Data Structures and Other Objects Using Java" by Michael Main.

package edu.uwm.cs351;

import junit.framework.TestCase;

//Krischan Woehrer, Homework #2, CS 351

/******************************************************************************
 * This class is a homework assignment; A ParticleSeq is a collection of
 * Particles. The sequence can have a special "current element," which is
 * specified and accessed through four methods that are not available in the
 * sequence class (start, getCurrent, advance and isCurrent).
 *
 * Note:
 * <ol>
 * <li>The capacity of a sequence can change after it's created, but the maximum
 * capacity is limited by the amount of free memory on the machine. The
 * constructor, append, addAll, clone and concatenation will result in an
 * OutOfMemoryError when free memory is exhausted.
 * <li>A sequence's capacity cannot exceed the maximum integer 2,147,483,647
 * ({@link Integer#MAX_VALUE}. Any attempt to create a larger capacity results
 * in a failure due to an arithmetic overflow.
 * </ol>
 * Neither of these conditions require any work for the implementors (students).
 ******************************************************************************/
public class ParticleSeq implements Cloneable {
	// Implementation of the ParticleSeq class:
	// 1. The number of elements in the sequences is in the instance variable
	// manyItems. The elements may be Particle objects or nulls.
	// 2. For any sequence, the elements of the
	// sequence are stored in data[0] through data[manyItems-1], and we
	// don't care what's in the rest of data.
	// 3. If there is a current element, then it lies in data[currentIndex];
	// if there is no current element, then currentIndex equals -1.

	private Particle[] data;
	private int manyItems;
	private int currentIndex;

	private static int INITIAL_CAPACITY = 1;

	private static boolean doReport = true; // changed only by invariant tester

	private boolean report(String error) {
		if (doReport)
			System.out.println("Invariant error: " + error);
		else
			System.out.println("Caught problem: " + error);
		return false;
	}

	private boolean wellFormed() {
		// Check the invariant.
		// 1. data is never null
		if (data == null)
			return report("data is null"); // test the NEGATION of the condition

		// 2. The data array is at least as long as the number of items
		// claimed by the sequence.
		if (this.data.length < this.manyItems) {
			return report("manyItems is greater than the data.length");
		}

		// 3. currentIndex is and never equal or more than the number of
		// items claimed by the sequence, and never less than -1.
		if (this.currentIndex < -1 || this.currentIndex >= this.manyItems) {
			return report("Current Index is either less than -1 or greater than the number of items in manyItems");
		}

		// If no problems discovered, return true
		return true;
	}

	// This is only for testing the invariant. Do not change!
	private ParticleSeq(boolean testInvariant) {
	}

	/**
	 * Initialize an empty sequence with an initial capacity of INITIAL_CAPACITY.
	 * The append method works efficiently (without needing more memory) until this
	 * capacity is reached.
	 * 
	 * @param - none
	 * @postcondition This sequence is empty and has an initial capacity of
	 *                INITIAL_CAPACITY
	 * @exception OutOfMemoryError Indicates insufficient memory for initial array.
	 **/
	public ParticleSeq() {
		this(INITIAL_CAPACITY);
		assert wellFormed() : "Invariant false at end of constructor";
	}

	/**
	 * Initialize an empty sequence with a specified initial capacity. Note that the
	 * append method works efficiently (without needing more memory) until this
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
	public ParticleSeq(int initialCapacity) {
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("initialCapacity must not be negative.");
		}
		this.currentIndex = -1;
		this.data = new Particle[initialCapacity];
		this.manyItems = 0;
		assert wellFormed() : "Invariant false at end of constructor";
	}

	/**
	 * Determine the number of elements in this sequence.
	 * 
	 * @param - none
	 * @return the number of elements in this sequence
	 **/
	public int size() {
		assert wellFormed() : "invariant failed at start of size";
		return this.manyItems;
		// size() should not modify anything, so we omit testing the invariant here
	}

	/**
	 * The first element (if any) of this sequence is now current.
	 * 
	 * @param - none
	 * @postcondition The front element of this sequence (if any) is now the current
	 *                element (but if this sequence has no elements at all, then
	 *                there is no current element).
	 **/
	public void start() {
		assert wellFormed() : "invariant failed at start of start";
		if (this.size() > 0) {
			this.currentIndex = 0;
		} else {
			this.currentIndex = -1;
		}

		assert wellFormed() : "invariant failed at end of start";
	}

	/**
	 * Accessor method to determine whether this sequence has a specified current
	 * element (a Particle or null) that can be retrieved with the getCurrent
	 * method. This depends on the status of the cursor.
	 * 
	 * @param - none
	 * @return true (there is a current element) or false (there is no current
	 *         element at the moment)
	 **/
	public boolean isCurrent() {
		assert wellFormed() : "invariant failed at start of isCurrent";
		if (this.currentIndex == -1) {
			return false;
		} else if (this.size() > 0 && this.currentIndex < this.size()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Accessor method to get the current element of this sequence.
	 * 
	 * @param - none
	 * @precondition isCurrent() returns true.
	 * @return the current element of this sequence, possibly null
	 * @exception IllegalStateException Indicates that there is no current element,
	 *                                  so getCurrent may not be called.
	 **/
	public Particle getCurrent() {
		assert wellFormed() : "invariant failed at start of getCurrent";
		if (!this.isCurrent()) {
			throw new IllegalStateException("No current element. Cannot call getCurrent()");
		} else {
			return this.data[this.currentIndex];
		}
	}

	/**
	 * Move forward, so that the next element is now the current element in this
	 * sequence.
	 * 
	 * @param - none
	 * @precondition isCurrent() returns true.
	 * @postcondition If the current element was already the end element of this
	 *                sequence (with nothing after it), then there is no longer any
	 *                current element. Otherwise, the new current element is the
	 *                element immediately after the original current element.
	 * @exception IllegalStateException If there was no current element, so advance
	 *                                  may not be called (the precondition was
	 *                                  false).
	 **/
	public void advance() {
		assert wellFormed() : "invariant failed at start of advance";
		if (!this.isCurrent()) {
			throw new IllegalStateException("No current element assigned. Cannot advance");
		}

		if (this.currentIndex < this.size() - 1) {
			++this.currentIndex;
		} else {
			this.currentIndex = -1;
		}
		assert wellFormed() : "invariant failed at end of advance";
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

	/**
	 * Add a new element to this sequence, after the current element. If the new
	 * element would take this sequence beyond its current capacity, then the
	 * capacity is increased before adding the new element.
	 * 
	 * @param element the new element that is being added, may be null
	 * @postcondition A new copy of the element has been added to this sequence. If
	 *                there was a current element, then the new element is placed
	 *                after the current element. If there was no current element,
	 *                then the new element is placed at the beginning of the
	 *                sequence. In all cases, the new element becomes the new
	 *                current element of this sequence.
	 * @exception OutOfMemoryError Indicates insufficient memory for increasing the
	 *                             sequence's capacity.
	 * @note An attempt to increase the capacity beyond Integer.MAX_VALUE will cause
	 *       the sequence to fail with an arithmetic overflow.
	 **/
	public void append(Particle element) {
		assert wellFormed() : "invariant failed at start of append";

		this.ensureCapacity(++this.manyItems);

		++this.currentIndex;
		if (this.currentIndex < this.size() - 1) {
			for (int i = this.size() - 1; i > this.currentIndex; i--) {
				this.data[i] = this.data[i - 1];
			}

		} else if (this.currentIndex >= this.size() || this.currentIndex < 0) {
			this.currentIndex = 0;
			for (int i = this.size(); i > 0; i--) {
				this.data[i] = this.data[i - 1];
			}
		}

		this.data[currentIndex] = element;

		assert wellFormed() : "invariant failed at end of append";
	}

	/**
	 * Remove the current element from this sequence.
	 * 
	 * @param - none
	 * @precondition isCurrent() returns true.
	 * @postcondition The current element has been removed from this sequence, and
	 *                the following element (if there is one) is now the new current
	 *                element. If there was no following element, then there is now
	 *                no current element.
	 * @exception IllegalStateException Indicates that there is no current element,
	 *                                  so removeCurrent may not be called.
	 **/
	public void removeCurrent() {
		assert wellFormed() : "invariant failed at start of removeCurrent";
		// throw exception if we try to remove current index element and there is none
		if (!this.isCurrent()) {
			throw new IllegalStateException("No current index to remove");
		}
		if (this.size() - 1 == this.currentIndex) {
			this.data[currentIndex] = null;
			this.currentIndex = -1;
			--this.manyItems;
		} else {
			// move all elements to the left
			for (int i = this.currentIndex; i < this.size() - 1; i++) {
				this.data[i] = this.data[i + 1];
			}
			--this.manyItems;
		}
		assert wellFormed() : "invariant failed at end of removeCurrent";
	}

	/**
	 * Place the contents of another sequence at the end of this sequence.
	 * 
	 * @param addend a sequence whose contents will be placed at the end of this
	 *               sequence
	 * @precondition The parameter, addend, is not null.
	 * @postcondition The elements from addend have been placed at the end of this
	 *                sequence. The current element of this sequence if any, remains
	 *                unchanged. The addend is unchanged.
	 * @exception NullPointerException Indicates that addend is null.
	 * @exception OutOfMemoryError     Indicates insufficient memory to increase the
	 *                                 size of this sequence.
	 * @note An attempt to increase the capacity beyond Integer.MAX_VALUE will cause
	 *       an arithmetic overflow that will cause the sequence to fail.
	 **/
	public void addAll(ParticleSeq addend) {
		assert wellFormed() : "invariant failed at start of addAll";
		if (addend == null) {
			throw new NullPointerException("The particle sequence argument addend cannot be null");
		}
		// Determine size of addend
		int addSize = addend.size();
		// Ensure capacity in this sequence
		this.ensureCapacity(this.size() + addSize);
		// From manyitems index to manyitems + size of addend, copy elements over.
		for (int i = 0; i < addSize; i++) {
			this.data[this.size() + i] = addend.data[i];
		}
		this.manyItems += addSize;

		assert wellFormed() : "invariant failed at end of addAll";
	}

	/**
	 * Generate a copy of this sequence.
	 * 
	 * @param - none
	 * @return The return value is a copy of this sequence. Subsequent changes to
	 *         the copy will not affect the original, nor vice versa.
	 * @exception OutOfMemoryError Indicates insufficient memory for creating the
	 *                             clone.
	 **/
	@Override // implementation
	public ParticleSeq clone() { // Clone a ParticleSeq object. (We follow textbook form.)
		assert wellFormed() : "invariant failed at start of clone";
		ParticleSeq answer;

		try {
			// super.clone() will create a new object with the
			// same value for *all* the fields. This is a "shallow" clone.
			answer = (ParticleSeq) super.clone();
		} catch (CloneNotSupportedException e) { // This exception should not occur. But if it does, it would probably
													// indicate a programming error that made super.clone unavailable.
													// The most common error would be forgetting the "Implements
													// Cloneable"
													// clause at the start of this class.
			throw new RuntimeException("This class does not implement Cloneable");
		}

		// Copy the data over
		answer.data = new Particle[this.size()];
		for (int i = 0; i < this.size(); i++) {
			answer.data[i] = this.data[i];
		}

		assert wellFormed() : "invariant failed at end of clone";
		assert answer.wellFormed() : "invariant failed for clone";

		return answer;
	}

	public static class TestInvariant extends TestCase {
		private ParticleSeq hs;

		@Override // implementation
		public void setUp() {
			hs = new ParticleSeq(false);
			doReport = false;
		}

		public void test0() {
			hs.currentIndex = -1;
			assertFalse(hs.wellFormed());
		}

		public void test1() {
			hs.data = new Particle[2];
			assertFalse(hs.wellFormed());
		}

		public void test2() {
			hs.data = new Particle[0];
			hs.currentIndex = -1;
			assertTrue(hs.wellFormed());
		}

		public void test3() {
			hs.data = new Particle[3];
			hs.manyItems = 4;
			assertFalse(hs.wellFormed());
		}

		public void test4() {
			hs.data = new Particle[4];
			hs.manyItems = 4;
			assertTrue(hs.wellFormed());
		}

		public void test5() {
			hs.data = new Particle[10];
			hs.manyItems = 4;
			assertTrue(hs.wellFormed());
		}

		public void test6() {
			hs.data = new Particle[5];
			hs.manyItems = 4;
			hs.currentIndex = 4;
			assertFalse(hs.wellFormed());
		}

		public void test7() {
			hs.data = new Particle[3];
			hs.manyItems = 3;
			hs.currentIndex = -1;
			assertTrue(hs.wellFormed());
		}

		public void test8() {
			hs.data = new Particle[5];
			hs.manyItems = 3;
			hs.currentIndex = 3;
			assertFalse(hs.wellFormed());
		}

		public void test9() {
			hs.data = new Particle[5];
			hs.manyItems = 5;
			hs.currentIndex = 4;
			assertTrue(hs.wellFormed());
		}
	}
}
