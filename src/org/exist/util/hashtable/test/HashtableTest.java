/*
 *  eXist Open Source Native XML Database
 *  Copyright (C) 2001-03 Wolfgang M. Meier
 *  wolfgang@exist-db.org
 *  http://exist.sourceforge.net
 *  
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *  
 *  $Id$
 */
package org.exist.util.hashtable.test;

import java.util.Iterator;
import java.util.Random;

import junit.framework.TestCase;

import org.exist.util.hashtable.Int2ObjectHashMap;
import org.exist.util.hashtable.SequencedLongHashMap;

/**
 * @author Wolfgang Meier (wolfgang@exist-db.org)
 */
public class HashtableTest extends TestCase {

    private int tabSize = 100000;
	private Object values[] = new Object[tabSize];

	public static void main(String[] args) {
		junit.textui.TestRunner.run(HashtableTest.class);
	}

	public HashtableTest(String testname) {
		super(testname);
	}
	
	public void testPut() {
		int keys[] = new int[tabSize];
		Int2ObjectHashMap table = new Int2ObjectHashMap(tabSize);
		Random rand = new Random(System.currentTimeMillis());
		System.out.println("Generating " + tabSize + " random keys...");
		for(int i = 0; i < tabSize; i++) {
			do {
				keys[i] = rand.nextInt(Integer.MAX_VALUE);
			} while(table.get(keys[i]) != null);
			values[i] = new String("a" + keys[i]);
			table.put(keys[i], values[i]);
		}
		System.out.println("Testing get(key) ...");
		for(int i = 0; i < tabSize; i++) {
			Object v = table.get(keys[i]);
			assertEquals( values[i], v );
		}
		System.out.println("Testing remove(key) ...");
		int r;
		for(int i = 0; i < tabSize / 10; i++) {
			do {
				r = rand.nextInt(tabSize - 1);
			} while(values[r] == null); 
			table.remove(keys[r]);
			values[r] = null;
		}
		for(int i = 0; i <tabSize; i++) {
			while(values[i] == null)
				i++;
			String v = (String)table.get(keys[i]);
			assertNotNull("Key not found", v);
			assertEquals(values[i], v);
		}
		int c = 0;
		for(Iterator i = table.iterator(); i.hasNext(); c++) {
			Integer v = (Integer)i.next();
		}
		System.out.println(table.size() + " = " + c);
		System.out.println("maxRehash: " + table.getMaxRehash());
		assertEquals(table.size(), c);
	}

	public void testSequencedMap() {
		long keys[] = new long[tabSize];
		SequencedLongHashMap table = new SequencedLongHashMap(tabSize);
		Random rand = new Random(System.currentTimeMillis());
		System.out.println("Generating " + tabSize + " random keys...");
		for(int i = 0; i < tabSize; i++) {
			do {
				keys[i] = rand.nextInt(Integer.MAX_VALUE);
			} while(table.get(keys[i]) != null);
			values[i] = new String("a" + keys[i]);
			table.put(keys[i], values[i]);
		}
		// check SequencedLongHashMap.get()
		for(int i = 0; i < tabSize; i++) {
			Object v = table.get(keys[i]);
			assertEquals( values[i], v);
		}
		// check SequencedLongHashMap.iterator()
		int c = 0;
		for(Iterator i = table.iterator(); i.hasNext(); c++) {
			Long v = (Long)i.next();
			assertEquals(keys[c], v.longValue());
		}
		assertEquals(c, table.size());
		
		// remove 1000 random items
		for(int i = 0; i < 1000; i++) {
			int k;
			do {
				k = rand.nextInt(tabSize - 1);
			} while(values[k] == null);
			table.remove(keys[k]);
			values[k] = null;
			assertNull(table.get(keys[k]));
		}
		System.out.println("Hashtable size: " + table.size());
		
		// iterate through the sequence again
		int k = 0;
		c = 0;
		for(Iterator i = table.iterator(); i.hasNext(); k++, c++) {
			while(values[k] == null)
				k++;
			Long v = (Long)i.next();
			assertTrue("Value has been removed and should be null", values[k] != null);
			assertEquals("Keys don't match", keys[k], v.longValue());
		}
		assertEquals("Hashtable size is incorrect", table.size(), values.length - 1000);
		assertEquals("Hashtable size is incorrect", table.size(), c);
		
		System.gc();
		
		// add some new items
		for(int i = 0; i < values.length; i++) {
			if(values[i] == null) {
				do {
					keys[i] = rand.nextInt(Integer.MAX_VALUE);
				} while(table.get(keys[i]) != null);
				values[i] = new String("a" + keys[i]);
				table.put(keys[i], values[i]);
			}
		}
		
		// check SequencedLongHashMap.get()
		for(int i = 0; i < tabSize; i++) {
			Object v = table.get(keys[i]);
			assertEquals( values[i], v);
		}
		
		// check SequencedLongHashMap.iterator()
		c = 0;
		for(Iterator i = table.iterator(); i.hasNext(); c++) {
			Long v = (Long)i.next();
		}
		assertEquals(c, table.size());
		System.gc();
		
		for(int i = 0; i < values.length; i++) {
			table.removeFirst();
		}
		System.gc();
		Iterator iter = table.iterator();
		assertFalse("Hashtable should be empty", iter.hasNext());
		assertTrue(table.size() == 0);
		
		System.out.println("Hashtable size: " + table.size());
		System.out.println("maxRehash: " + table.getMaxRehash());
	}
}
