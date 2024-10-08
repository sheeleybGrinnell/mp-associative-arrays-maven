package edu.grinnell.csc207.util;

import static java.lang.reflect.Array.newInstance;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @param <K> the key type
 * @param <V> the value type
 *
 * @author Benjamin Sheeley
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V> pairs[];

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({ "unchecked" })
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(),
        DEFAULT_CAPACITY);
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   *
   * @return a new copy of the array
   */
  public AssociativeArray<K, V> clone() {
    AssociativeArray<K, V> clonedArray = new AssociativeArray<K, V>();
    try {
    for (KVPair<K, V> pair : this.pairs) {
      clonedArray.set(pair.key, pair.val);
    }
    return null;
   } catch (Exception e) {
    System.err.println("Exception Occurred: " + e);
   }
   return clonedArray;
  } // clone()

  /**
   * Convert the array to a string.
   *
   * @return a string of the form "{Key0:Value0, Key1:Value1, ... KeyN:ValueN}"
   */
  public String toString() {
    String result = "";
    for (KVPair<K, V> pair : this.pairs) {
      result = result.concat(pair + "\n");
    }
    return result;
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to
   * get(key) will return value.
   *
   * @param K
   *   The key whose value we are seeting.
   * @param V
   *   The value of that key.
   *
   * @throws NullKeyException
   *   If the client provides a null key.
   */
  public void set(K key, V value) throws NullKeyException, KeyNotFoundException {
    try {
      int keyIndex = this.find(key);
      pairs[keyIndex].val = value;
    } catch (NullKeyException e) {
      throw new NullKeyException();
    } catch (KeyNotFoundException e) {
      pairs[this.size] = new KVPair<K, V>(key, value);
      this.size++;
      if (this.size > DEFAULT_CAPACITY) {
        expand();
      }
    }
  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @param key
   *   A key
   *
   * @throws KeyNotFoundException
   *   when the key is null or does not appear in the associative array.
   */
  public V get(K key) throws KeyNotFoundException, NullKeyException {
    try {
      int keyIndex = this.find(key);
      return this.pairs[keyIndex].val;
    } catch (NullKeyException e) {
      throw new NullKeyException();
    }
  } // get(K)

  /**
   * Determine if key appears in the associative array. Should
   * return false for the null key.
   */
  public boolean hasKey(K key) throws KeyNotFoundException, NullKeyException {
     try {
      find(key);
      return true;
     } catch (NullKeyException e) {
      throw new NullKeyException();
     } catch (KeyNotFoundException e) {
      return false;
     }
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls
   * to get(key) will throw an exception. If the key does not appear
   * in the associative array, does nothing.
   */
  public void remove(K key) throws KeyNotFoundException{
    try {
      int rmIndex = find(key);
      KVPair<K, V> oldPair = pairs[rmIndex];
      KVPair<K, V> newPair = pairs[this.size - 1];
      oldPair.key = newPair.key;
      oldPair.val = newPair.val;
      newPair.key = null;
      newPair.val = null;
      this.size--;
    } catch (Exception e) {
      return;
    }
  } // remove(K)

  /**
   * Determine how many key/value pairs are in the associative array.
   */
  public int size() {
    return this.size;
  } // size()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key.
   * If no such entry is found, throws an exception.
   *
   * @param key
   *   The key of the entry.
   *
   * @throws KeyNotFoundException
   *   If the key does not appear in the associative array.
   */
  int find(K key) throws KeyNotFoundException, NullKeyException {
    if (key == null) {
      throw new NullKeyException();
    } else {
      for (int i = 0; i < this.size; i++) {
        if (this.pairs[i].key == key) {
          return i;
        }
      }
      throw new KeyNotFoundException();
    } // find(K)
  }

} // class AssociativeArray
