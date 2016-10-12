package hr.fer.zemris.java.simplecomp.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.simplecomp.RegisterUtil;

/**
 * The Class RegisterUtilTest.
 */
public class RegisterUtilTest {

	/**
	 * Test unpacking1.
	 */
	@Test
	public void testUnpacking1() {
		int registerDescriptor = 0x1000102;
		assertEquals(2, RegisterUtil.getRegisterIndex(registerDescriptor));
		assertEquals(1, RegisterUtil.getRegisterOffset(registerDescriptor));
		assertEquals(true, RegisterUtil.isIndirect(registerDescriptor));
	}

	/**
	 * Test unpacking2.
	 */
	@Test
	public void testUnpacking2() {
		int registerDescriptor = 0x1FFFF02;
		assertEquals(2, RegisterUtil.getRegisterIndex(registerDescriptor));
		assertEquals(-1, RegisterUtil.getRegisterOffset(registerDescriptor));
		assertEquals(true, RegisterUtil.isIndirect(registerDescriptor));
	}

	/**
	 * Test unpacking3.
	 */
	@Test
	public void testUnpacking3() {
		int registerDescriptor = 0x18000FE;
		assertEquals(254, RegisterUtil.getRegisterIndex(registerDescriptor));
		assertEquals(-32768, RegisterUtil.getRegisterOffset(registerDescriptor));
		assertEquals(true, RegisterUtil.isIndirect(registerDescriptor));
	}

	/**
	 * Test unpacking4.
	 */
	@Test
	public void testUnpacking4() {
		int registerDescriptor = 0x17FFFFF;
		assertEquals(255, RegisterUtil.getRegisterIndex(registerDescriptor));
		assertEquals(32767, RegisterUtil.getRegisterOffset(registerDescriptor));
		assertEquals(true, RegisterUtil.isIndirect(registerDescriptor));
	}

}
