package main.trees

import main.vertexes.SimpleBSTVertex

/**
 * This class represents a simple implementation of a binary search tree.
 * It extends AbstractBinarySearchTree and uses SimpleBSTVertex as vertices.
 *
 * When attempting to perform insertion, removal, or search operations on a non-empty binary search tree with a key that
 * is incomparable with the keys in the tree, the behavior is as follows:
 *
 * **Put**: If an attempt is made to put a key-value pair with a key that is incomparable with the existing
 * keys in the tree, the insertion operation will fail and the tree will remain unchanged.
 *
 * **Remove**: If an attempt is made to remove a key-value pair with a key that is incomparable with the existing keys
 * in the tree, the removal operation will fail and the tree will remain unchanged.
 *
 * **Get**: When getting for a key that is incomparable with the keys in the tree, the search operation will not
 * find any matching key-value pair the get operation will fail.
 *
 * @param K The type of keys in the tree.
 * @param V The type of values in the tree.
 * @property comparator The comparator used to order the keys. If null, keys are expected to be comparable.
 * @property size The number of elements in the tree.
 * @property root The root vertex of the tree.
 */
open class SimpleBinarySearchTree<K, V> : AbstractBinarySearchTree<K, V, SimpleBSTVertex<K, V>> {
    /**
     * Associates the specified value with the specified key in this tree.
     * If parameter replaceIfExists is true and the key already exists, the value is replaced; otherwise, the value is ignored.
     * @param key the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @param replaceIfExists if true, replaces the value if the key already exists, otherwise ignores it
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
     * @param key The key to insert.
     * @param value The value associated with the key.
     * @param replaceIfExists If true, replaces the existing value for the key.
     * @param vertex The current vertex in the recursion.
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
     * @param key The key to remove.
     * @return The value associated with the removed key, or null if the key is not found.
     */
    override fun remove(key: K): V? {
        val (_, deletedValue, isRemoved) = removeRec(key)
        if (isRemoved) size--
        return deletedValue
    }

    /**
     * Recursively removes the key-value pair associated with the specified key from the tree.
     * This method traverses the tree recursively to find the node with the given key and removes it.
     * If the key is found and the corresponding node has both left and right children,
     * the method replaces the node's key and value with those of the minimum key node in its right subtree,
     * and then removes the minimum key node from the right subtree.
     * If the key is not found, it returns a pair containing the vertex and null value.
     * @param key The key to remove.
     * @param vertex The current vertex being examined in the recursion.
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
     * Constructs a new binary search tree with the specified comparator.
     * @param comparator the comparator to use for comparing keys, or null to use natural ordering
     */
    constructor(comparator: Comparator<K>? = null) : super(comparator)

    /**
     * Constructs a new binary search tree and initializes it with the mappings from the specified map.
     * @param map the map whose mappings are to be added to this tree
     * @param replaceIfExists if true, replaces the value if the key already exists, otherwise ignores it
     * @param comparator the comparator to use for comparing keys, or null to use natural ordering
     */
    constructor(map: Map<K, V>, replaceIfExists: Boolean = true, comparator: Comparator<K>? = null) : super(
        map,
        replaceIfExists,
        comparator,
    )
}
