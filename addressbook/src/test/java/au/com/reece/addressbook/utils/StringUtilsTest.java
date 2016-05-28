package au.com.reece.addressbook.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test the StringUtils class.
 *
 */
public class StringUtilsTest {

	/**
	 * Given the parameter is null
	 * When the isBlank method called
	 * Than the true should be return true
	 */
	@Test
	public void whenParamIsNullThenIsBlankShouldBeReturnTrue() {
		//Given the parameter is null
		String param = null;
		
		//When isBlank method called
		boolean flag = StringUtils.isBlank(param);
		
		//Then the flag should be true
		assertTrue(flag);
	}
	
	/**
	 * Given the parameter is empty
	 * When the isBlank method called
	 * Than the true should be return true
	 */
	@Test
	public void whenParamIsEmptyThenIsBlankShouldBeReturnTrue() {
		//Given the parameter is empty
		String param = "";
		
		//When isBlank method called
		boolean flag = StringUtils.isBlank(param);
		
		//Then the flag should be true
		assertTrue(flag);
	}
	
	/**
	 * Given the parameter is a space
	 * When the isBlank method called
	 * Than the true should be return true
	 */
	@Test
	public void whenParamIsSpaceThenIsBlankShouldBeReturnTrue() {
		//Given the parameter is space
		String param = " ";
		
		//When isBlank method called
		boolean flag = StringUtils.isBlank(param);
		
		//Then the flag should be true
		assertTrue(flag);
	}
	
	/**
	 * Given the parameter is multi-space
	 * When the isBlank method called
	 * Than the true should be return true
	 */
	@Test
	public void whenParamIsMultiSpaceThenIsBlankShouldBeReturnTrue() {
		//Given the parameter is multi-space
		String param = "   ";
		
		//When isBlank method called
		boolean flag = StringUtils.isBlank(param);
		
		//Then the flag should be true
		assertTrue(flag);
	}
	
	/**
	 * Given the parameter is multi-space and a line feed
	 * When the isBlank method called
	 * Than the true should be return true
	 */
	@Test
	public void whenParamIsMultiSpaceLindFeedThenIsBlankShouldBeReturnTrue() {
		//Given the parameter is multi-space and line Feed
		String param = "   \n";
		
		//When isBlank method called
		boolean flag = StringUtils.isBlank(param);
		
		//Then the flag should be true
		assertTrue(flag);
	}
	
	/**
	 * Given the parameter is multi-space and mulit line feed
	 * When the isBlank method called
	 * Than the true should be return true
	 */
	@Test
	public void whenParamIsMultiSpaceMultiLindFeedThenIsBlankShouldBeReturnTrue() {
		//Given the parameter is multi-space and line Feed
		String param = "   \t\n\f\r";
		
		//When isBlank method called
		boolean flag = StringUtils.isBlank(param);
		
		//Then the flag should be true
		assertTrue(flag);
	}
	
	/**
	 * Given the parameter is a string
	 * When the isBlank method called
	 * Than the true should be return true
	 */
	@Test
	public void whenParamIsStringThenIsBlankShouldBeReturnFalse() {
		//Given the parameter is a string
		String param = "abc";
		
		//When isBlank method called
		boolean flag = StringUtils.isBlank(param);
		
		//Then the flag should be true
		assertFalse(flag);
	}
	
	/**
	 * Given the parameter is a space front string
	 * When the isBlank method called
	 * Than the true should be return true
	 */
	@Test
	public void whenParamIsSpaceStringThenIsBlankShouldBeReturnFalse() {
		//Given the parameter is a space front string
		String param = " abc";
		
		//When isBlank method called
		boolean flag = StringUtils.isBlank(param);
		
		//Then the flag should be true
		assertFalse(flag);
	}
	
	/**
	 * Given the parameter is a space tail string
	 * When the isBlank method called
	 * Than the true should be return true
	 */
	@Test
	public void whenParamIsSpaceTailStringThenIsBlankShouldBeReturnFalse() {
		//Given the parameter is a space tail string
		String param = "abc ";
		
		//When isBlank method called
		boolean flag = StringUtils.isBlank(param);
		
		//Then the flag should be true
		assertFalse(flag);
	}
	
	/**
	 * Given the parameter is a space tail string
	 * When the isBlank method called
	 * Than the true should be return true
	 */
	@Test
	public void whenParamIsSpaceFrontAndTailStringThenIsBlankShouldBeReturnFalse() {
		//Given the parameter is a space front and tail string
		String param = " abc ";
		
		//When isBlank method called
		boolean flag = StringUtils.isBlank(param);
		
		//Then the flag should be true
		assertFalse(flag);
	}
}
