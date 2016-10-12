import static org.junit.Assert.assertEquals;

import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashTable;

/**
 * The Class SimpleHashTableTests.
 *
 * @author Ivan Anić
 * @version 1.0
 */
public class SimpleHashTableTests {

//	@Before
//	public void setUp() {
//		
//	}
	
	/**
	 * Test constructor.
	 */
	@Test
	public void testConstructor() {
		SimpleHashTable<Integer, String> table = new SimpleHashTable<>(250);
		assertEquals("size should be 256.", 256, table.capacity());
	}

	/**
	 * Test get.
	 */
	@Test
	public void testGet() {
		SimpleHashTable<String, Integer> table = new SimpleHashTable<>(133);
		table.put("Petra", 5);
		assertEquals("value should be 5", (Integer) 5, table.get("Petra"));
		assertEquals("should return null", null, table.get("Petar"));
	}

	/**
	 * Test get null.
	 */
	@Test
	public void testGetNull() {
		SimpleHashTable<String, Integer> table = new SimpleHashTable<>();
		assertEquals("value should be null", null, table.get("Petra"));
	}

	/**
	 * Test put.
	 */
	@Test
	public void testPut() {
		SimpleHashTable<String, Integer> table = new SimpleHashTable<>();
		table.put("Petra", 5);
		table.put("Petra", 6);
		table.put("Petar", 20);
		assertEquals("value should be 6.", (Integer) 6, table.get("Petra"));
		assertEquals("value should be 20.", (Integer) 20, table.get("Petar"));

	}

	/**
	 * Test put illegal.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testPutIllegal() {
		SimpleHashTable<String, Integer> table = new SimpleHashTable<>();
		table.put(null, 5);
	}

	/**
	 * Test contains.
	 */
	@Test
	public void testContainsKey() {
		SimpleHashTable<String, Integer> table = new SimpleHashTable<>();
		table.put("Petra", 6);
		assertEquals("value should be true", true, table.containsKey("Petra"));
	}

	/**
	 * Test contains.
	 */
	@Test
	public void testContainsValue() {
		SimpleHashTable<String, Integer> table = new SimpleHashTable<>();
		table.put("Petra", 6);
		assertEquals("value should be true", true, table.containsValue(6));
	}

	/**
	 * Test contains.
	 */
	@Test
	public void testContainsValueNull() {
		SimpleHashTable<String, Integer> table = new SimpleHashTable<>();
		table.put("Petra", null);
		assertEquals("value should be true", true, table.containsValue(null));
	}

	/** The test is empty. */
	@Test
	public void testisEmpty() {
		SimpleHashTable<String, Integer> table = new SimpleHashTable<>();
		assertEquals("value should be true", true, table.isEmpty());
		table.put("Petra", null);
		assertEquals("value should be false", false, table.isEmpty());
	}

	/** The daj mi size. */
	@Test
	public void testResize() {
		SimpleHashTable<String, Integer> table = new SimpleHashTable<>(4);
		table.put("Petra", 1);
		table.put("Petar", 2);
		table.put("Pero", 3);
		table.put("Pijetlić", 4);
		table.put("Petar", 5);

		assertEquals("capacity should be 8", 8, table.capacity());
		assertEquals("value should be 6.", (Integer) 5, table.get("Petar"));
		assertEquals("size should be 4.", (Integer) 4, (Object) table.size());
	}

	/**
	 * Test clear.
	 */
	@Test
	public void testClear() {
		SimpleHashTable<String, Integer> table = new SimpleHashTable<>(4);
		table.put("Petra", 1);
		table.put("Petar", 2);
		table.put("Pero", 3);
		table.put("Pijetlić", 4);
		table.clear();
		assertEquals("capacity should be 8", 8, table.capacity());
		assertEquals("size should be 4.", (Integer) 0, (Object) table.size());
	}
	
	/**
	 * Test remove.
	 */
	@Test
	public void testRemove() {
		SimpleHashTable<String, Integer> table = new SimpleHashTable<>(2);
		table.put("Petra", 1);
		table.put("Peta", 1);
		table.put("Petar", 2);
		table.put("Pe", 2);
		table.put("Petarasf", 2);
		table.put("Petargs", 2);
		table.put("Petargdaga", 2);

		table.remove("Peta");
		assertEquals("size should be 1.", (Integer) 6, (Object) table.size());
	}
	
	/**
	 * Test iterator.
	 */
	@Test
	public void testIterator() {
		SimpleHashTable<String, Integer> table = new SimpleHashTable<>(4);
		StringBuilder sb = new StringBuilder();
		table.put("Petra", 1);
		table.put("Petar", 2);
		table.put("Pero", 3);
		table.put("Pijetlić", 4);
		table.put("Pe", 2);
		table.put("Petarasf", 2);
		table.put("Petargs", 2);
		table.put("Petargdaga", 2);
		table.put("Pžagžasge", 2);
		table.put("zzzaa", 2);
		for (SimpleHashTable.TableEntry<String, Integer> entry : table){
			sb.append(entry.getKey()+" -> "+entry.getValue()+"\n");
		}
		System.out.println(sb.toString());
	}
	
//	/**
//	 * Test iterator2.
//	 */
//	@Test(expected = ConcurrentModificationException.class)
//	public void testIterator2() {
//		Iterator<SimpleHashTable.TableEntry<String,Integer>> iter = examMarks.iterator();
//		while(iter.hasNext()) {
//		SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
//		if(pair.getKey().equals("Ivana")) {
//		examMarks.remove("Ivana");
//		}
//		}
//	}
}
