package trees

import iterator.TreeIterator
import vertexes.InterfaceBSTVertex

/**
 * An abstract class representing a binary search tree.
 *
 * If the tree has an incomparable key type and comparator is `null`, if the tree
 * is non-empty, when trying to call the search, insert and delete methods, the tree
 * will remain unchanged, the operation throws an exception with the message "Key's
 * type is incomparable and comparator was not given".
 *
 * @param K key type
 * @param V value type
 * @param N vertex type implementing the [InterfaceBSTVertex] interface
 * @property comparator `Comparator<K>?` type; used optionally to compare keys in a tree. If `null`, it is expected that keys of comparable type.
 * @property size `Long` type; number of key-value pairs in this tree
 * @property root `N?` type, `null` by default
 */
abstract class AbstractBinarySearchTree<K, V, N : InterfaceBSTVertex<K, V, N>> {

    protected var comparator: Comparator<K>? = null
    protected var size: Long = 0L
    protected var root: N? = null

    /**
     * Returns an iterator over the elements of this tree in a certain sequence
     */
    operator fun iterator(): Iterator<Pair<K, V>> {
        return TreeIterator(root)
    }

    /**
     * Returns the number of key-value pairs in this tree
     */
    fun size(): Long {
        return size
    }

    /**
     * Returns `true` if this tree contains no key-value pairs, and `false` otherwise
     */
    fun isEmpty(): Boolean {
        return size == 0L
    }

    /**
     * Returns `true` if this tree contains at least one key-value pair, and `false` otherwise
     */
    fun isNotEmpty(): Boolean {
        return size != 0L
    }

    /**
     * Returns the value associated with the specified key in this tree
     *
     * @param key `K` type
     * @return If the key exists - corresponding value
     * If the key does not exist - `null`
     */
    fun get(key: K): V? {
        return getRec(key)
    }

    /**
     * Returns a pair containing the specified key-value mapping
     *
     * @param key `K` type
     * @return If the key exists - pair. If the key does not exist - `null`.
     */
    fun getPair(key: K): Pair<K, V>? {
        val value = get(key)
        return if (value == null) null else Pair(key, value)
    }

    /**
     * Returns the minimum key in the tree
     *
     * @return If the tree is not empty - minimum key, and `null` otherwise
     */
    fun getMin(): V? {
        val minKeyNode = getMinKeyNodeRec()
        return if (minKeyNode == null) null else minKeyNode.value
    }

    /**
     * Returns the maximum key in the tree
     *
     * @return If the tree is not empty - maximum key, and `null` otherwise
     */
    fun getMax(): V? {
        val maxKeyNode = getMaxKeyNodeRec()
        return if (maxKeyNode == null) null else maxKeyNode.value
    }

    /**
     * Returns key-value pair with the minimum key in the tree
     *
     * @return If the tree is not empty - pair with minimum key, and `null` otherwise
     */
    fun getMinKeyPair(): Pair<K, V>? {
        val minKeyNode = getMinKeyNodeRec()
        return if (minKeyNode == null) null else Pair(minKeyNode.key, minKeyNode.value)
    }

    /**
     * Returns key-value pair with the maximum key in the tree
     *
     * @return If the tree is not empty - pair with maximum key, and `null` otherwise
     */
    fun getMaxKeyPair(): Pair<K, V>? {
        val maxKeyNode = getMaxKeyNodeRec()
        return if (maxKeyNode == null) null else Pair(maxKeyNode.key, maxKeyNode.value)
    }

    /**
     * Puts a new vertex of a certain type into the tree with a given key and value
     *
     * @param key `K` type
     * @param value `V` type, associated with this key
     * @param replaceIfExists `Boolean` type,
     *
     * If `true` - replaces the value if the key already exists. If `false` - ignores it.
     */
    abstract fun put(
        key: K,
        value: V,
        replaceIfExists: Boolean = true,
    )

    /**
     * Puts all key-value pairs from the specified map to this tree
     *
     * @param map `Map<K, V>` type
     * @param replaceIfExists `Boolean` type.
     * If `true` - replaces the value if the key already exists. If `false` - ignores it.
     * Supplied only if a [comparator] is present. If [comparator] is `null`, the value is replaced
     * by the last value from the key-value pair in the map, where the key is the one already existing in the tree.
     */
    fun putAll(
        map: Map<K, V>,
        replaceIfExists: Boolean = true,
    ) {
        for (pair in map) put(pair.key, pair.value, replaceIfExists)
    }

    /**
     * Deletes a vertex by the entered key and returns the value stored in it
     *
     * @param key `K` type
     * @return If a vertex with this key exists in the tree - the value stored in this vertex, otherwise `null`
     */
    abstract fun remove(key: K): V?

    /**
     * Deletes a vertex by the entered key and returns it as a key-value pair
     *
     * @param key `K` type
     * @return If a vertex with such a key exists in the tree - the key-value pair corresponding to this vertex, otherwise `null`
     */
    fun removeAndReturnPair(key: K): Pair<K, V>? {
        val value = remove(key)
        return if (value == null) null else Pair(key, value)
    }

    /**
     * Recursively searches for the value associated with the entered key
     *
     * @param key `K` type
     * @param vertex `N?` type, `root` by default; current vertex being examined
     * @return If a vertex with this key exists in the tree - the value stored in this vertex, otherwise `null`
     */
    private fun getRec(
        key: K,
        vertex: N? = root,
    ): V? {
        if (vertex == null) return null
        return when (compareKeys(key, vertex.key)) {
            0 -> vertex.value
            -1 -> getRec(key, vertex.leftSon)
            else -> getRec(key, vertex.rightSon)
        }
    }

    /**
     * Recursively searches for the value associated with the minimum key in the tree
     *
     * @param vertex `N?` type, `root` by default; current vertex being examined
     * @return If the tree is not empty - the vertex with the minimum key in the tree, otherwise `null`
     */
    protected fun getMinKeyNodeRec(vertex: N? = root): N? {
        if (vertex == null) {
            return null
        } else {
            return if (vertex.leftSon == null) {
                vertex
            } else {
                getMinKeyNodeRec(vertex.leftSon)
            }
        }
    }

    /**
     * Recursively searches for the value associated with the maximum key in the tree
     *
     * @param vertex `N?` type, `root` by default; current vertex being examined
     * @return If the tree is not empty - the vertex with the maximum key in the tree, otherwise `null`
     */
    protected fun getMaxKeyNodeRec(vertex: N? = root): N? {
        if (vertex == null) {
            return null
        } else {
            return if (vertex.rightSon == null) {
                vertex
            } else {
                getMaxKeyNodeRec(vertex.rightSon)
            }
        }
    }

    /**
     * Compares two keys
     *
     * Comparing with a comparator if it is not `null`, or without one if the comparator is null and
     * the keys are of comparable type
     *
     * @param firstKey `K` type
     * @param secondKey `K` type
     * @return
     * -1 if the first key is less than the second key;
     * 0 if they are equal;
     * 1 if the first key is greater than the second key.
     */
    protected fun compareKeys(
        firstKey: K,
        secondKey: K,
    ): Int {
        val cpr = comparator
        return if (cpr != null) {
            when (cpr.compare(firstKey, secondKey)) {
                in Int.MIN_VALUE..-1 -> -1
                0 -> 0
                else -> 1
            }
        } else {
            val comparableKey = firstKey as? Comparable<K> ?:
                throw Exception("Key's type is incomparable and comparator wasn't given")
            when (comparableKey.compareTo(secondKey)) {
                in Int.MIN_VALUE..-1 -> -1
                0 -> 0
                else -> 1
            }
        }
    }

    /**
     * Constructs a new binary search tree with the specified comparator
     *
     * @param comparator `Comparator<K>?` type, `null `by default; used optionally to compare keys in a tree. If `null`, it is expected that keys of comparable type.
     */
    constructor(comparator: Comparator<K>? = null) {
        this.comparator = comparator
    }

    /**
     * Constructs a new binary search tree and puts all key-value pairs from the specified map to this tree
     *
     * @param map `Map<K, V>` type
     * @param replaceIfExists `Boolean` type.
     * If `true` - replaces the value if the key already exists. If `false` - ignores it.
     * Supplied only if a [comparator] is present. If comparator is `null`, the value is replaced
     * by the last value from the key-value pair in the map, where the key is the one already existing in the tree.
     *
     * @param comparator `Comparator<K>?` type, `null `by default; used optionally to compare keys in a tree. If `null`, it is expected that keys of comparable type.
     */
    constructor(map: Map<K, V>, replaceIfExists: Boolean = true, comparator: Comparator<K>? = null) {
        this.comparator = comparator
        putAll(map, replaceIfExists)
    }
}
