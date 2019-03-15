import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class CountTest {
	

	 File filein = new File("input.txt");   	  
	public Count count=new Count(filein);
	
	@Test
	public void testCountcharacter() {
		
		assertEquals(0, count.CountCharacter());
	}

	@Test
	public void testCountline() {
	
		assertEquals(0, count.CountLine());
	}

	@Test
	public void testCountword() {
		
		assertEquals(0, count.CountWord());
	}

	

}
