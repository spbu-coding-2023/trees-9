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
     * @param replaceIfExists `Boolean` type;
     * If `true` - replaces the value if the key already exists. If `false` - ignores it.
     */
    override fun put(
        key: K,
        value: V,
        replaceIfExists: Boolean,
    ) {
        if (root == null) {
            root = SimpleBSTVertex(key, value)
            size++
            return
        }
        var vertex: SimpleBSTVertex<K, V> = root as SimpleBSTVertex<K, V>
        while (true) {
            when (compareKeys(key, vertex.key)) {
                // keys are equal
                0 -> {
                    if (replaceIfExists) vertex.value = value
                    return
                }

                // the first key is less than the second key
                -1 -> {
                    if (vertex.leftSon == null) {
                        vertex.leftSon = SimpleBSTVertex(key, value)
                        size++
                        return
                    } else {
                        vertex = vertex.leftSon as SimpleBSTVertex<K, V>
                        continue
                    }
                }

                // the first key is greater than the second key
                1 -> {
                    if (vertex.rightSon == null) {
                        vertex.rightSon = SimpleBSTVertex(key, value)
                        size++
                        return
                    } else {
                        vertex = vertex.rightSon as SimpleBSTVertex<K, V>
                        continue
                    }
                }
            }
        }
    }

    /**
     * A property of the parent vertex that indicates which son the toRemoveVertex is
     */
    enum class Place {
        RIGHTSon,
        LEFTSon,
    }

    /**
     * Removes the key-value pair associated with the specified key from the tree.
     *
     * @param key `K` type
     * @return The value associated with the removed key, or null if the key is not found.
     */
    override fun remove(key: K): V? {
        if (root == null) {
            return null
        }
        var sonPlace = Place.RIGHTSon
        var toRemoveVertex: SimpleBSTVertex<K, V>? = root
        var parentOfToRemoveVertex: SimpleBSTVertex<K, V>? = null

        while (toRemoveVertex != null) {
            if (toRemoveVertex.key == key) {
                break
            }
            when (compareKeys(key, toRemoveVertex.key)) {
                // the first key is less than the second key
                -1 -> {
                    sonPlace = Place.LEFTSon
                    parentOfToRemoveVertex = toRemoveVertex
                    toRemoveVertex = toRemoveVertex.leftSon
                }

                // the first key is greater than the second key
                1 -> {
                    sonPlace = Place.RIGHTSon
                    parentOfToRemoveVertex = toRemoveVertex
                    toRemoveVertex = toRemoveVertex.rightSon
                }
            }
        }
        if (toRemoveVertex == null) {
            return null
        }
        val deletedValue = toRemoveVertex.value
        if (toRemoveVertex == root) {
            if (toRemoveVertex.leftSon == null && toRemoveVertex.rightSon == null) {
                root = null
                size--
            } else if (toRemoveVertex.leftSon != null && toRemoveVertex.rightSon == null) {
                root = toRemoveVertex.leftSon
                size--
            } else if (toRemoveVertex.leftSon == null) {
                root = toRemoveVertex.rightSon
                size--
            }
        } else if (toRemoveVertex.leftSon == null && toRemoveVertex.rightSon == null) {
            size--
            if (sonPlace == Place.RIGHTSon) {
                parentOfToRemoveVertex?.rightSon = null
            } else {
                parentOfToRemoveVertex?.leftSon = null
            }
        } else if (toRemoveVertex.leftSon == null) {
            size--
            if (sonPlace == Place.RIGHTSon) {
                parentOfToRemoveVertex?.rightSon = toRemoveVertex.rightSon
            } else {
                parentOfToRemoveVertex?.leftSon = toRemoveVertex.rightSon
            }
        } else if (toRemoveVertex.rightSon == null) {
            size--
            if (sonPlace == Place.RIGHTSon) {
                parentOfToRemoveVertex?.rightSon = toRemoveVertex.leftSon
            } else {
                parentOfToRemoveVertex?.leftSon = toRemoveVertex.leftSon
            }
        } else {
            size--
            var minKeyRightSubtreeVertex: SimpleBSTVertex<K, V>? = toRemoveVertex.rightSon
            var parentOfMinKeyRightSubtreeVertex: SimpleBSTVertex<K, V>? = null
            while (minKeyRightSubtreeVertex?.leftSon != null) {
                parentOfMinKeyRightSubtreeVertex = minKeyRightSubtreeVertex
                minKeyRightSubtreeVertex = minKeyRightSubtreeVertex.leftSon
            }
            if (sonPlace == Place.RIGHTSon) {
                minKeyRightSubtreeVertex?.let {
                    parentOfToRemoveVertex?.rightSon?.key = it.key
                    parentOfToRemoveVertex?.rightSon?.value = it.value
                }
                parentOfMinKeyRightSubtreeVertex?.leftSon = null
            } else {
                minKeyRightSubtreeVertex?.let {
                    parentOfToRemoveVertex?.leftSon?.key = it.key
                    parentOfToRemoveVertex?.leftSon?.value = it.value
                }
                parentOfMinKeyRightSubtreeVertex?.leftSon = null
            }
        }
        return deletedValue
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
