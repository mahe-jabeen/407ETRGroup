package test;

import org.junit.Test;

import main.java.TollCalculator;

public class TollCalculatorTest {
	@Test  
    public void testCostofTrip(){  
		TollCalculator.costofTrip("QEW", "Highway 400");
	}
	
	@Test  
    public void testCostofTripNoLocation(){  
		TollCalculator.costofTrip("QEW", "");
	}
}
