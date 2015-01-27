///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  WordFrequencyMain.java
// File:             SimpleHashMap.java
// Semester:         CS367 Fall 2013
//
// Author:           Navneet Reddy
// CS Login:         navneet
// Lecturer's Name:  Jim Skrentny
// Lab Section:      Lecture 1
//
//                   PAIR PROGRAMMERS COMPLETE THIS SECTION
// Pair Partner:     Jason Tiedt
// CS Login:         jtiedt
// Lecturer's Name:  Jim Skrentny
// Lab Section:      Lecture 1
//
//                   STUDENTS WHO GET HELP FROM ANYONE OTHER THAN THEIR PARTNER
// Credits:          N/A
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.List;
import java.util.LinkedList;

/**
 * This class implements a generic map based on hash tables using chained
 * buckets for collision resolution.
 *
 * <p>A map is a data structure that creates a key-value mapping. Keys are
 * unique in the map. That is, there cannot be more than one value associated
 * with a same key. However, two keys can map to a same value.</p>
 *
 * <p>The <tt>SimpleHashMap</tt> class takes two generic parameters, <tt>K</tt>
 * and <tt>V</tt>, standing for the types of keys and values respectively. Items
 * are stored in a hash table. Hash values are computed from the
 * <tt>hashCode()</tt> method of the <tt>K</tt> type objects.</p>
 *
 * <p>The chained buckets are implemented using Java's <tt>LinkedList</tt>
 * class.  When a hash table is created, its initial table size and maximum
 * load factor is set to <tt>11</tt> and <tt>0.75</tt>. The hash table can hold
 * arbitrarily many key-value pairs and resizes itself whenever it reaches its
 * maximum load factor.</p>
 *
 * <p><tt>null</tt> values are not allowed in <tt>SimpleHashMap</tt> and a
 * NullPointerException is thrown if used. You can assume that <tt>equals()</tt>
 * and <tt>hashCode()</tt> on <tt>K</tt> are defined, and that, for two
 * non-<tt>null</tt> keys <tt>k1</tt> and <tt>k2</tt>, if <tt>k1.equals(k2)</tt>
 * then <tt>k1.hashCode() == k2.hashCode()</tt>. Do not assume that if the hash
 * codes are the same that the keys are equal since collisions are possible.</p>
 */
public class SimpleHashMap<K, V> {

    /**
     * A map entry (key-value pair).
     */
    public static class Entry<K, V> {
        private K key;
        private V value;

        /**
         * Constructs the map entry with the specified key and value.
         */
        public Entry(K k, V v) {
        	key = k;
        	value = v;
        }

        /**
         * Returns the key corresponding to this entry.
         *
         * @return the key corresponding to this entry
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns the value corresponding to this entry.
         *
         * @return the value corresponding to this entry
         */
        public V getValue() {
            return value;
        }

        /**
         * Replaces the value corresponding to this entry with the specified
         * value.
         *
         * @param value new value to be stored in this entry
         * @return old value corresponding to the entry
         */
        public V setValue(V value) {
        	V tempValue = value;
            this.value = value;
            
            return tempValue;
        }
    }

    //Initial capacity of the table
    private int INITIAL_CAPACITY;
    //Maximum load factor of the table
    private double LOAD_FACTOR;
    //Number of entries in the table
    private int size;
    //Table to store the entries
    private LinkedList<Entry<K, V>>[] table;

    /**
     * Constructs an empty hash map with initial capacity <tt>11</tt> and
     * maximum load factor <tt>0.75</tt>.
     **/
    @SuppressWarnings("unchecked")
	public SimpleHashMap() {
    	INITIAL_CAPACITY = 11;
    	LOAD_FACTOR = .75;
    	size = 0;
    	table = new LinkedList[INITIAL_CAPACITY];
    	
    	for (int i = 0; i < table.length; i++)
    		table[i] = new LinkedList<Entry<K, V>>();
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or <tt>null</tt>
     * if this map contains no mapping for the key
     * @throws NullPointerException if the specified key is <tt>null</tt>
     */
    @SuppressWarnings("unchecked")
	public V get(Object key) {
    	if (key == null)
    		throw new NullPointerException();
    	
    	//Store the old mapping of the key
    	Entry<K,V> entry = null;
    	
    	//Hash code of the key
    	int hash = ((K)key).hashCode();
    	//Index of the place in the table to add the new element
    	int index = hash % table.length;
    	
    	if (index < 0)
    		index += table.length;
    	
    	//Search through table starting at given hash code
    	for (int i = 0; i < table[index].size(); i++)
    	{
    		Entry<K,V> tempEntry = ((Entry<K, V>) table[index].get(i));

    		//Check if the the place in the table is the same key
    		if (tempEntry.getKey().equals((K)key))
    			entry = tempEntry;
    	}
    	
    	if (entry == null)
    		return null;
    	else
    		return entry.getValue();
    }

    /**
     * <p>Associates the specified value with the specified key in this map.
     * Neither the key nor the value can be <tt>null</tt>. If the map
     * previously contained a mapping for the key, the old value is replaced.</p>
     *
     * <p>If the load factor of the hash table after the insertion would exceed
     * the maximum load factor <tt>0.75</tt>, then the resizing mechanism is
     * triggered. The size of the table should grow at least by a constant
     * factor in order to ensure the amortized constant complexity, but you
     * are free to decide the exact value of the new table size (e.g. whether
     * to use a prime or not). After that, all of the mappings are rehashed to
     * the new table.</p>
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with <tt>key</tt>, or
     * <tt>null</tt> if there was no mapping for <tt>key</tt>.
     * @throws NullPointerException if the key or value is <tt>null</tt>
     */
    public V put(K key, V value) {
    	if (key == null || value == null)
    		throw new NullPointerException();
    	
    	//Store the old mapping of the key
    	Entry<K,V> oldEntry = null;
    	//New Entry to add to the table
    	Entry<K,V> entry = new Entry<K,V>(key,value);

    	//Hash code of the key
    	int hash = key.hashCode();
    	//Index of the place in the table to add the new element
    	int index = hash % table.length;
    	
    	if (index < 0)
    		index += table.length;
    	
    	//Check if the key is in the table
    	if (table[index].size() == 0 || get(key) == null)
    	{
    		table[index].add(entry);
    	}
    	else
    	{
    		for (int i = 0; i < table[index].size(); i++)
    		{
    			Entry<K,V> tempEntry = ((Entry<K, V>) table[index].get(i));
    			
    			//Check if the the place in the table is the same key
    			if (tempEntry.getKey().equals(key))
    			{
    				oldEntry = tempEntry;
    				table[index].get(i).setValue(value);
    			}
    		}
    	}

    	size++;
    	
    	//Check if the threshold for the load factor has been reached
    	if ((size/ table.length) > LOAD_FACTOR)
    		resize();
    	
    	if (oldEntry == null)
    		return null;
    	else
    		return oldEntry.getValue();
    }
    
    /**
     * Resize the hash table and transfer all the entries from the old table.
     */
    @SuppressWarnings("unchecked")
	private void resize() {
    	//Old table
    	LinkedList<Entry<K, V>>[] oldTable = table;
    	//Resize table
    	table = new LinkedList[(oldTable.length * 2) + 1];
    	
    	for (int i = 0; i < table.length; i++)
    		table[i] = new LinkedList<Entry<K, V>>();
    	
    	size = 0;

    	//Re-enter entries from the old table to the new table
    	for (int i = 0; i < oldTable.length; i++)
    	{
    		for (int j = 0; j < oldTable[i].size(); j++)
    		{
    			Entry<K,V> tempEntry = ((Entry<K, V>) oldTable[i].get(j));
    			
        		put(tempEntry.getKey(),tempEntry.getValue());
    		}
    	}
    }

    /**
     * Removes the mapping for the specified key from this map if present. This
     * method does nothing if the key is not in the hash table.
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with <tt>key</tt>, or <tt>null</tt>
     * if there was no mapping for <tt>key</tt>.
     * @throws NullPointerException if key is <tt>null</tt>
     */
    public V remove(Object key) {
    	if (key == null)
    		throw new NullPointerException();
    	
    	//Store the old mapping of the key
    	Entry<K,V> entry = null;
    	
    	//Hash code of the key
    	int hash = key.hashCode();
    	//Index of the place in the table to add the new element
    	int index = hash % table.length;
    	
    	if (index < 0)
    		index += table.length;
    	
    	//Check if the key is in the hash table
    	if (table[index].size() == 0 || get(key) == null)
    		return null;
    	
    	//Search through the table for the key
    	for (int i = 0; i < table[index].size(); i++)
    	{
    		Entry<K,V> tempEntry = ((Entry<K, V>) table[index].get(i));
			
			//Check if the the place in the table is the same key
			if (tempEntry.getKey().equals(key))
			{
				entry = tempEntry;
				table[index].remove(i);
			}
    	}
    	
    	size--;
    	
    	if (entry == null)
    		return null;
    	else
    		return entry.getValue();
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map
     */
    public int size() {
    	return size;
    }

    /**
     * Returns a list of all the mappings contained in this map. This method
     * will iterate over the hash table and collect all the entries into a new
     * list. If the map is empty, return an empty list (not <tt>null</tt>).
     * The order of entries in the list can be arbitrary.
     *
     * @return a list of mappings in this map
     */
    public List<Entry<K, V>> entries() {
    	//List of entries to be returned
    	List<Entry<K, V>> entries = new LinkedList<Entry<K, V>>();
    	
    	//Add all the chained buckets to one list
    	for (int i = 0; i < table.length; i++)
    		entries.addAll(table[i]);
    	
    	return entries;
    }
}
