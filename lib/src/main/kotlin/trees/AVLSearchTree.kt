package trees

import vertexes.AVLVertex

/**
 * An implementation of a binary search tree that automatically balances itself using AVL rotations.
 * It extends AbstractBinarySearchTree and uses AVLVertex as vertices.
 * This class extends AbstractBinarySearchTree and provides methods to put, remove for key-value pairs.
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
 * @param K the type of keys in the tree
 * @param V the type of values associated with the keys
 * @property comparator The comparator used to order the keys. If null, keys are expected to be comparable.
 * @property size The number of elements in the tree.
 * @property root The root vertex of the tree.
 */
open class AVLSearchTree<K, V> : AbstractBinarySearchTree<K, V, AVLVertex<K, V>> {
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
        if (isNotEmpty()) {
            when (val putRecReturned = putRec(key, value, replaceIfExists, root as AVLVertex<K, V>)) {
                null, root -> {}
                else -> root = putRecReturned
            }
            return
        }
        root = AVLVertex(key, value)
        size++
    }

    /**
     * Associates the specified value with the specified key in this tree.
     * If replaceIfExists is true and the key already exists, the value is replaced; otherwise, the value is ignored.
     * @param key the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @param replaceIfExists if true, replaces the value if the key already exists, otherwise ignores it
     * @param vertex the current vertex in the recursion
     * @return the root vertex of the tree after the operation
     */
    private fun putRec(
        key: K,
        value: V,
        replaceIfExists: Boolean,
        vertex: AVLVertex<K, V>,
    ): AVLVertex<K, V>? {
        fun putRecShort(vertex: AVLVertex<K, V>): AVLVertex<K, V>? {
            return putRec(key, value, replaceIfExists, vertex)
        }

        val nextCallReturned: AVLVertex<K, V>?
        when (compareKeys(key, vertex.key)) {
            -1 -> {
                if (vertex.leftSon == null) {
                    vertex.leftSon = AVLVertex(key, value)
                    vertex.sonsHeightDiff++
                    size++
                    return vertex
                }
                nextCallReturned = putRecShort(vertex.leftSon as AVLVertex<K, V>)
            }

            0 -> {
                if (replaceIfExists) {
                    vertex.key = key
                    vertex.value = value
                }
                return null
            }

            else -> {
                if (vertex.rightSon == null) {
                    vertex.rightSon = AVLVertex(key, value)
                    vertex.sonsHeightDiff--
                    size++
                    return vertex
                }
                nextCallReturned = putRecShort(vertex.rightSon as AVLVertex<K, V>)
            }
        }
        if (nextCallReturned == null) return null

        fun doBalanceChoreWhenLeftSubTreeChanged(): AVLVertex<K, V>? {
            if (nextCallReturned.sonsHeightDiff == 0) return null
            if (++vertex.sonsHeightDiff == 2) return balance(vertex)
            return vertex
        }

        fun doBalanceChoreWhenRightSubTreeChanged(): AVLVertex<K, V>? {
            if (nextCallReturned.sonsHeightDiff == 0) return null
            if (--vertex.sonsHeightDiff == -2) return balance(vertex)
            return vertex
        }
        when (nextCallReturned) {
            vertex.leftSon -> return doBalanceChoreWhenLeftSubTreeChanged()
            vertex.rightSon -> return doBalanceChoreWhenRightSubTreeChanged()
            else -> {
                if (compareKeys(nextCallReturned.key, vertex.key) == -1) {
                    vertex.leftSon = nextCallReturned
                    return doBalanceChoreWhenLeftSubTreeChanged()
                }
                vertex.rightSon = nextCallReturned
                return doBalanceChoreWhenRightSubTreeChanged()
            }
        }
    }

    /**
     * Removes the mapping for a key from this tree if it is present.
     * @param key the key whose mapping is to be removed from the tree
     * @return the previous value associated with key, or null if there was no mapping for key
     */
    override fun remove(key: K): V? {
        if (isNotEmpty()) {
            val removeRecReturned = removeRec(key, root as AVLVertex<K, V>)
            when (removeRecReturned.first) {
                RemovalStage.B -> {
                    if (removeRecReturned.component2() != root) {
                        root = removeRecReturned.component2()
                    }
                }

                RemovalStage.C -> root = null
                else -> {}
            }
            return removeRecReturned.component3()
        }
        return null
    }

    /**
     * An enumeration representing different stages of removal during the removal process.
     */
    private enum class RemovalStage {
        /**
         * Don't need tree changes anymore
         */
        A,

        /**
         * Probably need some tree changes, but not null
         */
        B,

        /**
         * Need to null due "Son" property of (if exists) the parent of removed vertex + b
         */
        C,
    }

    /**
     * Recursively removes a key-value pair from the subtree rooted at the given vertex.
     * @param key the key to remove
     * @param vertex the root of the subtree to remove from
     * @return Triple that consists of:
     *
     *          1) removal stage;
     *
     *          2) if RemovalStage == a : just a vertex (don't need it later);
     *
     *          if RemovalStage == b : the root of the changed subtree;
     *
     *          if RemovalStage == c : the removed vertex;
     *
     *          3) a value of the removed vertex (or null if key not exists). */
    private fun removeRec(
        key: K,
        vertex: AVLVertex<K, V>,
    ): Triple<RemovalStage, AVLVertex<K, V>, V?> {
        /**
         * Triple consists of:
         *
         * 1) removal stage;
         *
         * 2) if RemovalStage == a : just a vertex (don't need it later);
         * if RemovalStage == b : the root of the changed subtree;
         * if RemovalStage == c : the removed vertex;
         *
         * 3) a value of the removed vertex (or null if key not exists).
         */
        val nextCallReturned: Triple<RemovalStage, AVLVertex<K, V>?, V?>
        when (compareKeys(key, vertex.key)) {
            -1 -> {
                if (vertex.leftSon == null) return Triple(RemovalStage.A, vertex, null)
                nextCallReturned = removeRec(key, vertex.leftSon as AVLVertex<K, V>)
            }

            1 -> {
                if (vertex.rightSon == null) return Triple(RemovalStage.A, vertex, null)
                nextCallReturned = removeRec(key, vertex.rightSon as AVLVertex<K, V>)
            }

            else -> {
                return when ((vertex.leftSon == null) to (vertex.rightSon == null)) {
                    true to true -> {
                        size--
                        Triple(RemovalStage.C, vertex, vertex.value)
                    }

                    true to false -> {
                        size--
                        Triple(RemovalStage.B, vertex.rightSon as AVLVertex<K, V>, vertex.value)
                    }

                    false to true -> {
                        size--
                        Triple(RemovalStage.B, vertex.leftSon as AVLVertex<K, V>, vertex.value)
                    }

                    else -> {
                        val valueOfVertex = vertex.value
                        Triple(
                            RemovalStage.B,
                                replaceSubtreeSRootByLargestInItsLeftSubtree(vertex),
                                valueOfVertex,
                        )
                    }
                }
            }
        }

        fun doBalanceChoreWhenLeftSubTreeChanged(): Triple<RemovalStage, AVLVertex<K, V>, V?> {
            if (nextCallReturned.component2().sonsHeightDiff in listOf(-1, 1)) {
                return Triple(RemovalStage.A, vertex, nextCallReturned.component3())
            }
            if (--vertex.sonsHeightDiff == -2) {
                return Triple(RemovalStage.B, balance(vertex), nextCallReturned.component3())
            }
            return Triple(RemovalStage.B, vertex, nextCallReturned.third)
        }

        fun doBalanceChoreWhenRightSubTreeChanged(): Triple<RemovalStage, AVLVertex<K, V>, V?> {
            if (nextCallReturned.component2().sonsHeightDiff in listOf(-1, 1)) {
                return Triple(RemovalStage.A, vertex, nextCallReturned.component3())
            }
            if (++vertex.sonsHeightDiff == 2) {
                return Triple(RemovalStage.B, balance(vertex), nextCallReturned.component3())
            }
            return Triple(RemovalStage.B, vertex, nextCallReturned.component3())
        }
        when (nextCallReturned.component1()) {
            RemovalStage.A -> return nextCallReturned
            RemovalStage.B ->
                when (nextCallReturned.component2()) {
                    vertex.leftSon -> return doBalanceChoreWhenLeftSubTreeChanged()
                    vertex.rightSon -> return doBalanceChoreWhenRightSubTreeChanged()
                    else ->
                        when (compareKeys(nextCallReturned.component2().key, vertex.key)) {
                            -1 -> {
                                vertex.leftSon = nextCallReturned.component2()
                                return doBalanceChoreWhenLeftSubTreeChanged()
                            }

                            else -> {
                                vertex.rightSon = nextCallReturned.component2()
                                return doBalanceChoreWhenRightSubTreeChanged()
                            }
                        }
                }

            RemovalStage.C ->
                when (compareKeys(nextCallReturned.component2().key, vertex.key)) {
                    -1 -> {
                        vertex.leftSon = null
                        return doBalanceChoreWhenLeftSubTreeChanged()
                    }

                    else -> {
                        vertex.rightSon = null
                        return doBalanceChoreWhenRightSubTreeChanged()
                    }
                }
        }
    }

    /**
     * Prepares the largest lower vertex to replace the specified vertex.
     * @param subtreeSInitiallyRoot the vertex to be replaced
     * @return the substitute vertex prepared to replace the specified vertex
     */
    private fun replaceSubtreeSRootByLargestInItsLeftSubtree (subtreeSInitiallyRoot: AVLVertex<K, V>): AVLVertex<K, V> {
        val substitute = getMaxKeyNodeRec(subtreeSInitiallyRoot.leftSon) as AVLVertex<K, V>
        val removeRecReturned = removeRec(substitute.key, subtreeSInitiallyRoot)
        subtreeSInitiallyRoot.key = substitute.key
        subtreeSInitiallyRoot.value = substitute.value
        return if (removeRecReturned.component1() == RemovalStage.A) subtreeSInitiallyRoot
        else removeRecReturned.component2()
    }

    /**
     * Balances the subtree.
     * @param curVertex the root vertex of subtree to be balanced
     * @return the root vertex of the subtree after balancing
     */
    private fun balance(curVertex: AVLVertex<K, V>): AVLVertex<K, V> {
        var (rightSon, leftSon) = List<AVLVertex<K, V>?>(2) { null }

        fun setSonSHeightDiffsOfTwoVertices(values: Pair<Int, Int>) {
            curVertex.sonsHeightDiff = values.component1()
            if (rightSon != null) {
                (rightSon as AVLVertex<K, V>).sonsHeightDiff = values.component2()
                return
            }
            (leftSon as AVLVertex<K, V>).sonsHeightDiff = values.component2()
        }

        if (curVertex.sonsHeightDiff == -2) {
            rightSon = curVertex.rightSon as AVLVertex<K, V>
            return if (rightSon.sonsHeightDiff == 1) {
                val rightSonSLeftSon = rightSon.leftSon as AVLVertex<K, V>
                val subtreeRoot = bigRotateLeft(curVertex, rightSon)
                setSonSHeightDiffsOfTwoVertices(
                    when (rightSonSLeftSon.sonsHeightDiff) {
                        1 -> 0 to -1
                        -1 -> 1 to 0
                        else -> 0 to 0
                    },
                )
                rightSonSLeftSon.sonsHeightDiff = 0
                subtreeRoot
            } else {
                val subtreeRoot = rotateLeft(curVertex, rightSon)
                setSonSHeightDiffsOfTwoVertices(
                    if (rightSon.sonsHeightDiff == 0) {
                        -1 to 1
                    } else {
                        0 to 0
                    },
                )
                subtreeRoot
            }
        }
        leftSon = curVertex.leftSon as AVLVertex<K, V>
        return if (leftSon.sonsHeightDiff == -1) {
            val leftSonSRightSon = leftSon.rightSon as AVLVertex<K, V>
            val subtreeRoot = bigRotateRight(curVertex, leftSon)
            setSonSHeightDiffsOfTwoVertices(
                when (leftSonSRightSon.sonsHeightDiff) {
                    -1 -> 0 to 1
                    1 -> -1 to 0
                    else -> 0 to 0
                },
            )
            leftSonSRightSon.sonsHeightDiff = 0
            subtreeRoot
        } else {
            val subtreeRoot = rotateRight(curVertex, leftSon)
            setSonSHeightDiffsOfTwoVertices(
                if (leftSon.sonsHeightDiff == 0) {
                    1 to -1
                } else {
                    0 to 0
                },
            )
            subtreeRoot
        }
    }

    /**
     * Performs a single left rotation of the subtree.
     * @param curVertex the current vertex to rotate around (the subtree's root)
     * @param rightSon the right son of the subtree's root
     * @return the new root of the subtree after rotation
     */
    private fun rotateLeft(
        curVertex: AVLVertex<K, V>,
        rightSon: AVLVertex<K, V>,
    ): AVLVertex<K, V> {
        curVertex.rightSon = rightSon.leftSon
        rightSon.leftSon = curVertex
        return rightSon
    }

    /**
     * Performs a single right rotation of the subtree.
     * @param curVertex the current vertex to rotate around (the subtree's root)
     * @param leftSon the left son of the subtree's root
     * @return the new root of the subtree after rotation
     */
    private fun rotateRight(
        curVertex: AVLVertex<K, V>,
        leftSon: AVLVertex<K, V>,
    ): AVLVertex<K, V> {
        curVertex.leftSon = leftSon.rightSon
        leftSon.rightSon = curVertex
        return leftSon
    }

    /**
     * Performs a big left rotation of the subtree.
     * @param curVertex the current vertex to rotate around (the subtree's root)
     * @param rightSon the right son of the subtree's root
     * @return the new root of the subtree after rotation
     */
    private fun bigRotateLeft(
        curVertex: AVLVertex<K, V>,
        rightSon: AVLVertex<K, V>,
    ): AVLVertex<K, V> {
        val curRightSon = rotateRight(rightSon, rightSon.leftSon as AVLVertex<K, V>)
        curVertex.rightSon = curRightSon
        return rotateLeft(curVertex, curRightSon)
    }

    /**
     * Performs a big right rotation of the subtree.
     * @param curVertex the current vertex to rotate around (the subtree's root)
     * @param leftSon the left son vertex of the subtree's root
     * @return the new root of the subtree after rotation
     */
    private fun bigRotateRight(
        curVertex: AVLVertex<K, V>,
        leftSon: AVLVertex<K, V>,
    ): AVLVertex<K, V> {
        val curLeftSon = rotateLeft(leftSon, leftSon.rightSon as AVLVertex<K, V>)
        curVertex.leftSon = curLeftSon
        return rotateRight(curVertex, curLeftSon)
    }

    /**
     * Constructs a new binary search tree with the specified comparator.
     * @param comparator the comparator to use for comparing keys, or null to use natural ordering
     */
    constructor (comparator: Comparator<K>? = null) : super(comparator)

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
