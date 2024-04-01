package main.trees
import main.vertexes.InterfaceBSTVertex
import main.iterator.TreeIterator

/**
 * An abstract class representing a binary search tree.
 *
 * When attempting to perform insertion, removal, or search operations on a non-empty binary search tree with a key that
 * is incomparable with the keys in the tree, the behavior is as follows:
 *
 * **Put**: If an attempt is made to insert a key-value pair with a key that is incomparable with the existing
 * keys in the tree, the insertion operation will fail and the tree will remain unchanged.
 *
 * **Remove**: If an attempt is made to remove a key-value pair with a key that is incomparable with the existing keys
 * in the tree, the removal operation will fail and the tree will remain unchanged.
 *
 * **Get**: When searching for a key that is incomparable with the keys in the tree, the search operation will not
 * find any matching key-value pair.
 *
 * @param K The type of keys.
 * @param V The type of values.
 * @param N The type of vertices implementing the [InterfaceBSTVertex] interface.
 * @property comparator The comparator used to order the keys. If null, keys are expected to be comparable.
 * @property size The number of elements in the tree.
 * @property root The root vertex of the tree.
 */
abstract class AbstractBinarySearchTree<K, V, N : InterfaceBSTVertex<K, V, N>> {

    /** Comparator used for comparing keys. */
    protected var comparator: Comparator<K>? = null

    /** The number of elements in the tree. */
    protected var size : Long = 0L

    /** The root vertex of the tree. */
    protected var root : N? = null

    /**
     * Returns an iterator over the elements in this tree in proper sequence.
     * @return an iterator over the elements in this tree in proper sequence
     */
    operator fun iterator(): Iterator<Pair<K, V>> {
        return TreeIterator(root)
    }

    /**
     * Returns the number of key-value pairs in this tree.
     * @return the number of key-value pairs in this tree
     */
    fun size(): Long {return size}

    /**
     * Returns true if this tree contains no key-value pairs.
     * @return true if this tree contains no key-value pairs
     */
    fun isEmpty(): Boolean {return size == 0L}

    /**
     * Returns the value associated with the specified key in this tree.
     * If the key is not found, returns null.
     * @param key the key whose associated value is to be returned
     * @return the value associated with the specified key, or null if the key is not found
     */
    fun get(key: K): V? {return getRec(key)}

    /**
     * Returns a Pair containing the specified key-value mapping, if found.
     * If the key is not found, returns null.
     * @param key the key whose associated value is to be returned
     * @return a Pair containing the specified key-value mapping, or null if the key is not found
     */
    fun getPair(key: K): Pair<K, V>? {
        val value = get(key)
        return if (value == null) null else Pair(key, value)
    }

    /**
     * Returns the minimum value in the tree, or null if the tree is empty.
     * @return the minimum value in the tree, or null if the tree is empty
     */
    fun getMin(): V? {
        val minKeyNode = getMinKeyNodeRec()
        return if (minKeyNode == null) null else minKeyNode.value
    }

    /**
     * Returns the maximum value in the tree, or null if the tree is empty.
     * @return the maximum value in the tree, or null if the tree is empty
     */
    fun getMax(): V?{
        val maxKeyNode = getMaxKeyNodeRec()
        return if (maxKeyNode == null) null else maxKeyNode.value
    }

    /**
     * Returns a Pair containing the minimum key-value mapping in the tree, or null if the tree is empty.
     * @return a Pair containing the minimum key-value mapping in the tree, or null if the tree is empty
     */
    fun getMinKeyPair(): Pair<K, V>? {
        val minKeyNode = getMinKeyNodeRec()
        return if (minKeyNode == null) null else Pair(minKeyNode.key, minKeyNode.value)
    }

    /**
     * Returns a Pair containing the maximum key-value mapping in the tree, or null if the tree is empty.
     * @return a Pair containing the maximum key-value mapping in the tree, or null if the tree is empty
     */
    fun getMaxKeyPair(): Pair<K, V>? {
        val maxKeyNode = getMaxKeyNodeRec()
        return if (maxKeyNode == null) null else Pair(maxKeyNode.key, maxKeyNode.value)
    }

    /**
     * Associates the specified value with the specified key in this tree.
     * If parameter replaceIfExists is true and the key already exists, the value is replaced; otherwise, the value is ignored.
     * @param key the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @param replaceIfExists if true, replaces the value if the key already exists, otherwise ignores it
     */
    abstract fun put(key: K, value: V, replaceIfExists : Boolean = true)

    /**
     * Put all of the pairs from the specified map to this tree.
     * If parameter replaceIfExists is true and a key already exists, the value is replaced; otherwise, the value is ignored.
     * @param map the map whose mappings are to be added to this tree
     * @param replaceIfExists if true, replaces the value if the key already exists, otherwise ignores it
     */
    fun putAll(map: Map<K, V>, replaceIfExists: Boolean = true) {
        for (pair in map) put(pair.key, pair.value, replaceIfExists)
    }

    /**
     * Removes the mapping for a key from this tree if it is present.
     * @param key the key whose mapping is to be removed from the tree
     * @return the previous value associated with key, or null if there was no mapping for key
     */
    abstract fun remove(key: K): V?

    /**
     * Removes the mapping for a key from this tree if it is present, and returns it as a Pair.
     * @param key the key whose mapping is to be removed from the tree
     * @return a Pair containing the removed key-value mapping, or null if the key was not found
     */
    fun removeAndReturnPair(key: K): Pair<K, V>? {
        val value = remove(key)
        return if (value == null) null else Pair(key, value)
    }

    /**
     * Recursively searches for the value associated with the specified key.
     * @param key the key to search for
     * @param vertex the current vertex being examined
     * @return the value associated with the specified key, or null if not found
     */
    private fun getRec(key: K, vertex: N? = root): V? {
        if (vertex == null) return null
        return when (compareKeys(key, vertex.key)) {
            0 -> vertex.value
            -1 -> getRec(key, vertex.leftSon)
            else -> getRec(key, vertex.rightSon)
        }
    }

    /**
     * Recursively finds the vertex with the minimum key in the tree.
     * @param vertex the current vertex being examined
     * @return the vertex with the minimum key in the tree, or null if the tree is empty
     */
    protected fun getMinKeyNodeRec(vertex: N? = root) : N? {
        if (vertex == null) return null
        else {
            return if (vertex.leftSon == null) vertex
            else getMinKeyNodeRec(vertex.leftSon)
        }
    }

    /**
     * Recursively finds the vertex with the maximum key in the tree.
     * @param vertex the current vertex being examined
     * @return the vertex with the maximum key in the tree, or null if the tree is empty
     */
    protected fun getMaxKeyNodeRec(vertex: N? = root) : N? {
        if (vertex == null) return null
        else {
            return if (vertex.rightSon == null) vertex
            else getMaxKeyNodeRec(vertex.rightSon)
        }
    }

    /**
     * Compares two keys.
     * @param firstKey the first key to compare
     * @param secondKey the second key to compare
     * @return -1 if the first key is less than the second key, 0 if they are equal, or 1 if the first key is greater than the second key
     */
    protected fun compareKeys(firstKey: K, secondKey: K): Int{
        val cpr = comparator
        return if (cpr != null) {
            when (cpr.compare(firstKey, secondKey)) {
                in Int.MIN_VALUE .. -1 -> -1
                0 ->  0
                else -> 1
            }
        }
        else {
            val comparableKey = firstKey as Comparable<K>
            when (comparableKey.compareTo(secondKey)) { 
                in Int.MIN_VALUE .. -1 -> -1
                0 -> 0
                else -> 1
            }

        }
    }

    /**
     * Constructs a new binary search tree with the specified comparator.
     * @param comparator the comparator to use for comparing keys, or null to use natural ordering
     */
    constructor(comparator: Comparator<K>? = null) {
        this.comparator = comparator
    }

    /**
     * Constructs a new binary search tree and initializes it with the mappings from the specified map.
     * @param map the map whose mappings are to be added to this tree
     * @param replaceIfExists if true, replaces the value if the key already exists, otherwise ignores it
     * @param comparator the comparator to use for comparing keys, or null to use natural ordering
     */
    constructor(map: Map<K, V>, replaceIfExists: Boolean = true, comparator: Comparator<K>? = null) {
        this.comparator = comparator
        putAll(map, replaceIfExists)
    }
}
