import edu.uwm.cs351.CoinDispenser;
import edu.uwm.cs.junit.LockedTestCase;


public class TestCoinDispenser extends LockedTestCase {
	private CoinDispenser dispenser = new CoinDispenser();
	
	public void testSetValue() {
		
		dispenser.setStartValue(100);
		assertEquals(100, dispenser.getRemainingValue());
		
		dispenser.setStartValue(-100);
		assertEquals(0, dispenser.getRemainingValue());
	}
	
	public void testQuarterDispense() {
		dispenser.setStartValue(100);
		assertEquals(4, dispenser.dispenseQuarters());
		assertEquals(0, dispenser.getRemainingValue());
		
		dispenser.setStartValue(78);
		assertEquals(Ti(480802330), dispenser.dispenseQuarters());
		assertEquals(3, dispenser.getRemainingValue());
		
		dispenser.setStartValue(24);
		assertEquals(0, dispenser.dispenseQuarters());
		assertEquals(Ti(101806402), dispenser.getRemainingValue());
		
		dispenser.setStartValue(25);
		assertEquals(Ti(1891568934), dispenser.dispenseQuarters());
		assertEquals(0, dispenser.getRemainingValue());
		
		dispenser.setStartValue(49);
		assertEquals(1, dispenser.dispenseQuarters());
		assertEquals(24, dispenser.getRemainingValue());
	}
	
	public void testDimesDispense() {
		dispenser.setStartValue(100);
		assertEquals(Ti(1061992055), dispenser.dispenseDimes());
		assertEquals(Ti(1530953437), dispenser.getRemainingValue());
		
		dispenser.setStartValue(78);
		assertEquals(7, dispenser.dispenseDimes());
		assertEquals(8, dispenser.getRemainingValue());
		
		dispenser.setStartValue(9);
		assertEquals(0, dispenser.dispenseDimes());
		assertEquals(9, dispenser.getRemainingValue());
		
		dispenser.setStartValue(10);
		assertEquals(1, dispenser.dispenseDimes());
		assertEquals(0, dispenser.getRemainingValue());
		
		dispenser.setStartValue(19);
		assertEquals(1, dispenser.dispenseDimes());
		assertEquals(9, dispenser.getRemainingValue());
	}
	
	public void testNickelsDispense() {
		dispenser.setStartValue(100);
		assertEquals(20, dispenser.dispenseNickels());
		assertEquals(0, dispenser.getRemainingValue());
		
		dispenser.setStartValue(78);
		assertEquals(15, dispenser.dispenseNickels());
		assertEquals(3, dispenser.getRemainingValue());
		
		dispenser.setStartValue(4);
		assertEquals(0, dispenser.dispenseNickels());
		assertEquals(4, dispenser.getRemainingValue());
		
		dispenser.setStartValue(5);
		assertEquals(1, dispenser.dispenseNickels());
		assertEquals(0, dispenser.getRemainingValue());
		
		dispenser.setStartValue(9);
		assertEquals(1, dispenser.dispenseNickels());
		assertEquals(4, dispenser.getRemainingValue());
	}
	
	public void testPenniesDispense() {
		dispenser.setStartValue(100);
		assertEquals(100, dispenser.dispensePennies());
		assertEquals(0, dispenser.getRemainingValue());
		
		dispenser.setStartValue(78);
		assertEquals(78, dispenser.dispensePennies());
		assertEquals(0, dispenser.getRemainingValue());
		
		dispenser.setStartValue(0);
		assertEquals(0, dispenser.dispensePennies());
		assertEquals(0, dispenser.getRemainingValue());
		
		dispenser.setStartValue(1);
		assertEquals(1, dispenser.dispensePennies());
		assertEquals(0, dispenser.getRemainingValue());
	}
	
	public void testForDebugPractice() {
		dispenser.setStartValue(75);
		assertEquals(10, dispenser.dispenseSevens());
		dispenser.doWork();
		assertEquals(1, dispenser.dispenseThrees());
	}
	
	public void testLocked(){
		dispenser.setStartValue(100);
		assertEquals(Tb(1552098993), dispenser.getRemainingValue()==100);
		assertEquals(Ts(595159155), Integer.toString(dispenser.dispenseQuarters()));	
	}

}
