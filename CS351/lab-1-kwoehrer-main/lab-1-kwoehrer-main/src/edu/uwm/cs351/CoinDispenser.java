package edu.uwm.cs351;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Coin Dispenser
 * Allows a user to set a starting value and dispense that value into the
 * amount of quarters, dimes, nickels and pennies. The starting value should
 * be positive and in the value of pennies. I.e. $1.52 should be 152.
 */
public class CoinDispenser {
	// Details the value remaining to dispense
	private int _remainingValue = 0;
	
	/**
	 * Sets the starting value to be dispensed
	 * @param startValue
	 */
	public void setStartValue(int startValue) {
		_remainingValue = startValue > 0 ? startValue : 0;
	}
	
	/**
	 * This accessor method returns the remaining value.
	 * @return the remaining value to be dispensed
	 */
	public int getRemainingValue() {
		return _remainingValue;
	}
	
	/**
	 * Dispenses the remaining value in terms of quarters, leaving the
	 * remaining balance as the remaining value.
	 * 
	 * @return number of quarters that can be dispensed
	 */
	public int dispenseQuarters() {
		return dispenseCoin(25);
	}
	
	/**
	 * Dispenses the remaining value in terms of dimes, leaving the
	 * remaining balance as the remaining value.
	 * 
	 * @return number of dimes that can be dispensed
	 */
	public int dispenseDimes() {
		return dispenseCoin(10);
	}
	
	/**
	 * Dispenses the remaining value in terms of nickels, leaving the
	 * remaining balance as the remaining value.
	 * 
	 * @return amount of nickels that can be dispensed
	 */
	public int dispenseNickels() {
		return dispenseCoin(5);
	}
	
	/**
	 * Dispenses the remaining value in terms of pennies, leaving the
	 * remaining balance as the remaining value.
	 * 
	 * @return number of pennies that can be dispensed
	 */
	public int dispensePennies() {
		return dispenseCoin(1);
	}
	
	/**
	 * The next few methods are for a debugging exercise
	 * Don't change them. Just follow the directions in the lab handout
	 */
	public int dispenseSevens() {
		/*put a breakpoint here*/int ret = dispenseCoin(7);
		doWork();
		return ret;
	}
	
	public int dispenseThrees() {
		return dispenseCoin(3);
	}
	
	public void doWork() {
		for(int i=0; i<1000000; )
			i++;
	}
	
	/**
	 * Dispenses the remaining value in terms of a given coin value.
	 * Leaving the remaining balance as the new remaining value, and
	 * returning the amount of coins of the particular given value that
	 * can be dispensed.
	 * 
	 * @param coinValue, value of the coin to be dispensed
	 * @return amount of the given coin value that can be dispensed
	 */
	private int dispenseCoin(int coinValue)
	{
		int count = 0;
		
		// check if the remaining value can disburse at least one coin, 
		// if not do not perform any calculations
		if (_remainingValue >= coinValue)
		{
			count = _remainingValue / coinValue;
			_remainingValue %= coinValue;
		}
		return count;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CoinDispenser dispenser = new CoinDispenser();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int value = 0;
		do {
			System.out.println("Enter the value to dispense in pennies (0 to quit):");
			try {
				value = Integer.parseInt(br.readLine());
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			dispenser.setStartValue(value);
			System.out.printf("Quarters: %d%nDimes: %d%nNickels: %d%nPennies: %d%n",dispenser.dispenseQuarters(),dispenser.dispenseDimes(),
					dispenser.dispenseNickels(),dispenser.dispensePennies());
		} while (value != 0);
	}

}
