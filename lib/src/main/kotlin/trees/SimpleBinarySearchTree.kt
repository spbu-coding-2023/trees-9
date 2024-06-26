package trees

import vertexes.SimpleBSTVertex

/**
 * Simple implementation of a binary search tree.
 *
 * It extends [AbstractBinarySearchTree] and uses [SimpleBSTVertex] as vertices.
 *
 * If the tree has an incomparable key type and comparator is `null`, if the tree
 * is non-empty, when trying to call the search, insert and delete methods, the tree
 * will remain unchanged, the operation throws an exception with the message "Key's
 * type is incomparable and comparator was not given".
 *
 * @param K key type
 * @param V value type
 * @property comparator `Comparator<K>?` type; used optionally to compare keys in a tree. If `null`, it is expected that keys of comparable type.
 * @property size `Long` type; number of key-value pairs in this tree
 * @property root `SimpleBSTVertex<K, V>?` type, `null` by default
 */
open class SimpleBinarySearchTree<K, V> : AbstractBinarySearchTree<K, V, SimpleBSTVertex<K, V>> {
    /**
     * Puts a new vertex of a certain type into the tree with a given key and value
     *
     * @param key `K` type
     * @param value `V` type
     * @param replaceIfExists `Boolean` type,
     *
     * If `true` - replaces the value if the key already exists. If `false` - ignores it.
     */
    override fun put(
        key: K,
        value: V,
        replaceIfExists: Boolean,
    ) {
        putRec(key, value, replaceIfExists)
    }

    /**
     * Recursively inserts a key-value pair into the tree.
     *
     * @param key `K` type
     * @param value `V` type
     * @param replaceIfExists `Boolean` type,
     * If `true` - replaces the value if the key already exists. If `false` - ignores it.
     * @param vertex `SimpleBSTVertex<K, V>?` type, `root` by default; The current vertex in the recursion.
     */
    private fun putRec(
        key: K,
        value: V,
        replaceIfExists: Boolean,
        vertex: SimpleBSTVertex<K, V>? = root,
    ) {
        if (vertex == null) {
            root = SimpleBSTVertex(key, value)
            size++
            return
        }
        when (compareKeys(key, vertex.key)) {
            0 -> if (replaceIfExists) vertex.value = value
            -1 -> {
                if (vertex.leftSon == null) {
                    vertex.leftSon = SimpleBSTVertex(key, value)
                    size++
                } else {
                    putRec(key, value, replaceIfExists, vertex.leftSon)
                }
            }

            1 -> {
                if (vertex.rightSon == null) {
                    vertex.rightSon = SimpleBSTVertex(key, value)
                    size++
                } else {
                    putRec(key, value, replaceIfExists, vertex.rightSon)
                }
            }
        }
    }

    /**
     * Removes the key-value pair associated with the specified key from the tree.
     *
     * @param key `K` type
     * @return The value associated with the removed key, or null if the key is not found.
     */
    override fun remove(key: K): V? {
        val (_, deletedValue, isRemoved) = removeRec(key)
        if (isRemoved) size--
        return deletedValue
    }

    /**
     * Recursively removes the key-value pair associated with the specified key from the tree.
     *
     * This method traverses the tree recursively to find the node with the given key and removes it.
     * If the key is found and the corresponding node has both left and right children,
     * the method replaces the node's key and value with those of the minimum key node in its right subtree,
     * and then removes the minimum key node from the right subtree.
     * If the key is not found, it returns a pair containing the vertex and null value.
     *
     * @param key `K` type
     * @param vertex `SimpleBSTVertex<K, V>?` type, `root` by default; The current vertex being examined in the recursion.
     * @return A pair containing the updated vertex and the value associated with the removed key, or null if the key is not found.
     */
    private fun removeRec(
        key: K,
        vertex: SimpleBSTVertex<K, V>? = root,
    ): Triple<SimpleBSTVertex<K, V>?, V?, Boolean> {
        if (vertex == null) return Triple(null, null, false)

        when (compareKeys(key, vertex.key)) {
            -1 -> {
                val (updatedLeftSon, deletedValue, isRemoved) = removeRec(key, vertex.leftSon)
                vertex.leftSon = updatedLeftSon
                return Triple(vertex, deletedValue, isRemoved)
            }

            1 -> {
                val (updatedRightSon, deletedValue, isRemoved) = removeRec(key, vertex.rightSon)
                vertex.rightSon = updatedRightSon
                return Triple(vertex, deletedValue, isRemoved)
            }

            else -> {
                val deletedValue: V = vertex.value
                if (vertex.leftSon == null || vertex.rightSon == null) {
                    if (vertex.leftSon == null) {
                        if (vertex == root) root = root?.rightSon
                        return Triple(vertex.rightSon, deletedValue, true)
                    }
                    if (vertex == root) root = root?.leftSon
                    return Triple(vertex.leftSon, deletedValue, true)
                }
                val minKeyRightSubtreeNode: SimpleBSTVertex<K, V>? = getMinKeyNodeRec(vertex.rightSon)
                minKeyRightSubtreeNode?.let {
                    vertex.key = it.key
                    vertex.value = it.value
                    val (updatedRightSon, _, _) = removeRec(it.key, vertex.rightSon)
                    vertex.rightSon = updatedRightSon
                }
                return Triple(vertex, deletedValue, true)
            }
        }
    }

    /**
     * Constructs a new binary search tree with the specified comparator
     *
     * @param comparator `Comparator<K>?` type, `null `by default; used optionally to compare keys in a tree. If `null`, it is expected that keys of comparable type.
     */
    constructor(comparator: Comparator<K>? = null) : super(comparator)

    /**
     * Constructs a new binary search tree and puts all key-value pairs from the specified map to this tree
     *
     * @param map `Map<K, V>` type
     * @param replaceIfExists `Boolean` type.
     * If `true` - replaces the value if the key already exists. If `false` - ignores it.
     * Supplied only if a [comparator] is present. If comparator is `null`, the value is replaced
     * by the last value from the key-value pair in the map, where the key is the one already existing in the tree.
     * @param comparator `Comparator<K>?` type, `null `by default; used optionally to compare keys in a tree. If `null`, it is expected that keys of comparable type.
     */
    constructor(map: Map<K, V>, replaceIfExists: Boolean = true, comparator: Comparator<K>? = null) : super(
        map,
        replaceIfExists,
        comparator,
    )
}
