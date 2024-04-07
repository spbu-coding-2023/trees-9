package trees

import vertexes.RBVertex

/**
 * Red-Black Tree implementation. It extends AbstractBinarySearchTree and uses RBVertex as vertices.
 * Red-Black Tree is a balanced binary search tree, where each vertex is colored either red or black.
 * This implementation ensures the following properties:
 *
 *   - Every vertex is either red or black.
 *   - The root is black.
 *   - Every leaf (NIL) is black.
 *   - If a vertex is red, then both its children are black.
 *   - Every simple path from a vertex to a descendant leaf (NIL) has the same number of black vertexes.
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
class RBSearchTree<K, V> : AbstractBinarySearchTree<K, V, RBVertex<K, V>> {
    /**
     * This method removes the vertex with the given key from the tree and returns its associated value,
     * maintaining the properties of the red-black tree.
     *
     * 4 cases we need to look at:
     *
     * 1) remove red vertex with 0 children -> just remove vertex
     *
     * 2) remove red or black vertex with 2 children ->
     * find min vertex on the right subtree and swap it's key and value with
     * key and value of vertex that we need to remove.
     * Now we can work with vertex which has 1 or 0 children
     *
     * 3) remove black vertex with 1 child -> child can be only red,
     * so we just swap child's key and value with key and value that we need to remove
     * and look at case 1)
     *
     * 4) remove black vertex with 0 children -> just remove vertex
     *
     * @param key the key of the vertex to be removed
     * @return the value associated with the removed vertex, or null if the key is not found
     */
    override fun remove(key: K): V? {
        val vertex: RBVertex<K, V> = getVertex(key) ?: return null
        --size
        val value = vertex.value

        if (vertex == root && size == 0L) {
            root = null
        } else if (needToBalance(vertex)) {
            balanceAfterRemove(vertex)
        }

        return value
    }

    /**
     * Determines whether balancing is required after removing a vertex from the Red-Black Search Tree.
     * @param vertex The vertex to be checked for balancing.
     * @return true if further balancing is required, false otherwise.
     */
    private fun needToBalance(vertex: RBVertex<K, V>): Boolean {
        when (countChildren(vertex)) {
            0 -> {
                if (vertex.isRed) {
                    replaceVertexBy(vertex, null)
                    return false
                }
                return true
            }

            1 -> {
                replaceVertexBy(vertex, getChild(vertex))
                return false
            }

            2 -> {
                val vertexForSwap = getMinKeyNodeRec(vertex.rightSon)
                vertexForSwap?.let {
                    val key = vertex.key
                    vertex.key = it.key
                    it.key = key

                    val value = vertex.value
                    vertex.value = it.value
                    it.value = value

                    needToBalance(vertexForSwap)
                }
            }
        }
        return false
    }

    /**
     * We need to balance tree after removal black vertex with 0 children.
     * In this fun we need to look at vertex's parent and brother:
     * 1) brother is black and brother's rightSon is red -> we paint
     * brother in parent's color, parent and brother's rightSon in black
     * then rotate left
     *
     * 2) brother is black and brother's leftSon is red (rightSon - black) ->
     * we swap colors of brother and brother's leftSon and rotate right
     * then look at case 1
     *
     * 3) brother is black and both sons are black -> we make brother red
     * then we need to launch algorithm from the parent because of it
     * can be red, so we have red parent and red son or black so
     * the black height of all subtree decreased
     *
     * 4) brother is red -> make brother black, parent red and
     * rotate left. We move conflict on level below, then we look at the previous cases
     * @param vertex The child vertex of the removed vertex or null if the removed vertex had no children.
     */
    private fun balanceAfterRemove(vertex: RBVertex<K, V>?) {
        var currentVertex = vertex
        while (currentVertex != root && (currentVertex?.isRed == false || currentVertex == null)) {
            var brother: RBVertex<K, V>?
            if (currentVertex == currentVertex?.parent?.leftSon) {
                brother = currentVertex?.parent?.rightSon

                if (brother?.isRed == true) {
                    brother.isRed = false
                    currentVertex?.parent?.isRed = true
                    val vertexForRotate = currentVertex?.parent
                    vertexForRotate?.let { rotateLeft(vertexForRotate) }
                    brother = currentVertex?.parent?.rightSon
                }

                if ((brother?.leftSon?.isRed == false || brother?.leftSon == null) &&
                    (brother?.rightSon?.isRed == false || brother?.rightSon == null)
                ) {
                    brother?.isRed = true
                    currentVertex = currentVertex?.parent
                    if (vertex == currentVertex?.leftSon) currentVertex?.leftSon = null
                } else {
                    if (brother.rightSon?.isRed == false || brother.rightSon == null) {
                        brother.leftSon?.isRed = false
                        brother.isRed = true
                        rotateRight(brother)
                        brother = currentVertex?.parent?.rightSon
                    }

                    val parentColor = currentVertex?.parent?.isRed
                    parentColor?.let { brother?.isRed = parentColor }
                    currentVertex?.parent?.isRed = false
                    brother?.rightSon?.isRed = false
                    val vertexForRotate = currentVertex?.parent
                    vertexForRotate?.let { rotateLeft(vertexForRotate) }
                    if (currentVertex == vertex) currentVertex?.parent?.leftSon = null
                    currentVertex = root
                }
            } else {
                brother = currentVertex?.parent?.leftSon

                if (brother?.isRed == true) {
                    brother.isRed = false
                    currentVertex?.parent?.isRed = true
                    val vertexForRotate = currentVertex?.parent
                    vertexForRotate?.let { rotateRight(vertexForRotate) }
                    brother = currentVertex?.parent?.leftSon
                }

                if ((brother?.leftSon?.isRed == false || brother?.leftSon == null) &&
                    (brother?.rightSon?.isRed == false || brother?.rightSon == null)
                ) {
                    brother?.isRed = true
                    currentVertex = currentVertex?.parent
                    if (vertex == currentVertex?.rightSon) currentVertex?.rightSon = null
                } else {
                    if (brother.leftSon?.isRed == false || brother.leftSon == null) {
                        brother.rightSon?.isRed = false
                        brother.isRed = true
                        rotateLeft(brother)
                        brother = currentVertex?.parent?.leftSon
                    }

                    val parentColor = currentVertex?.parent?.isRed
                    parentColor?.let { brother?.isRed = parentColor }
                    currentVertex?.parent?.isRed = false
                    brother?.leftSon?.isRed = false
                    val vertexForRotate = currentVertex?.parent
                    vertexForRotate?.let { rotateRight(vertexForRotate) }
                    if (currentVertex == vertex) currentVertex?.parent?.rightSon = null
                    currentVertex = root
                }
            }
        }
        currentVertex?.isRed = false
    }

    /**
     * Finds a vertex by corresponding key. If such vertex doesn't exist returns null.
     * @param key The key to search for.
     * @return The vertex with the corresponding key, or null if such vertex doesn't exist.
     */
    private fun getVertex(key: K): RBVertex<K, V>? {
        var currentVertex: RBVertex<K, V>? = root

        while (currentVertex?.key != key) {
            if (currentVertex == null) return null
            when (compareKeys(key, currentVertex.key)) {
                -1 -> {
                    currentVertex = currentVertex.leftSon
                }

                1 -> {
                    currentVertex = currentVertex.rightSon
                }
            }
        }
        return currentVertex
    }

    /**
     * Finds free place and inserts newVertex, colors it in red.
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
        var currentVertex: RBVertex<K, V>? = root
        var parent: RBVertex<K, V>? = null
        var isLeft: Boolean = false
        ++size

        while (currentVertex != null) {
            when (compareKeys(key, currentVertex.key)) {
                -1 -> {
                    parent = currentVertex
                    currentVertex = currentVertex.leftSon
                    isLeft = true
                }

                0 -> {
                    if (replaceIfExists) currentVertex.value = value
                    --size
                    break
                }

                1 -> {
                    parent = currentVertex
                    currentVertex = currentVertex.rightSon
                    isLeft = false
                }
            }
        }

        if (currentVertex == null) {
            currentVertex = RBVertex(key, value, null, null, true, parent)
            if (root == null) {
                root = currentVertex
            } else if (isLeft) {
                parent?.let { parent.leftSon = currentVertex }
            } else {
                parent?.let { parent.rightSon = currentVertex }
            }
        }

        balanceAfterPut(currentVertex)
    }

    /**
     * Balances the tree after inserting a new vertex.
     * We need to balance tree in two cases:
     * 1) when newVertex is root, so our root is red
     * 2) when parent of our newVertex is red(because newVertex is also red)
     *
     * in first case we just make the root black
     * in second case we need to look at the newVertex's uncle
     * if uncle is red, we make it black and newVertex's parent black and grandparent red
     * launch algorithm to grandfather because now it's color changed to red
     * if uncle is black we also make newVertex's parent black, grandparent red
     * and rotate it right
     * @param vertex The newly inserted vertex.
     */
    private fun balanceAfterPut(vertex: RBVertex<K, V>) {
        var currentVertex = vertex

        while (currentVertex.parent?.isRed == true) {
            val grandparent = currentVertex.parent?.parent

            if (currentVertex.parent == grandparent?.leftSon) {
                val uncle = grandparent?.rightSon

                if (uncle?.isRed == true) {
                    currentVertex.parent?.isRed = false
                    uncle.isRed = false
                    grandparent.isRed = true
                    currentVertex = grandparent
                } else {
                    if (currentVertex == currentVertex.parent?.rightSon) {
                        currentVertex = currentVertex.parent ?: currentVertex
                        rotateLeft(currentVertex)
                    }

                    currentVertex.parent?.isRed = false
                    currentVertex.parent?.parent?.isRed = true
                    val vertexForRightRotate = currentVertex.parent?.parent
                    vertexForRightRotate?.let { rotateRight(vertexForRightRotate) }
                }
            } else {
                val uncle = grandparent?.leftSon

                if (uncle?.isRed == true) {
                    currentVertex.parent?.isRed = false
                    uncle.isRed = false
                    grandparent.isRed = true
                    currentVertex = grandparent
                } else {
                    if (currentVertex == currentVertex.parent?.leftSon) {
                        currentVertex = currentVertex.parent ?: currentVertex
                        rotateRight(currentVertex)
                    }

                    currentVertex.parent?.isRed = false
                    currentVertex.parent?.parent?.isRed = true
                    val vertexForLeftRotate = currentVertex.parent?.parent
                    vertexForLeftRotate?.let { rotateLeft(vertexForLeftRotate) }
                }
            }
        }
        root?.isRed = false
    }

    /**
     * Counts the number of children of the given vertex.
     * @param vertex The vertex whose children count is to be determined.
     * @return The number of children of the given vertex.
     */
    private fun countChildren(vertex: RBVertex<K, V>): Int {
        var numOfChild = 0
        if (vertex.leftSon != null) ++numOfChild
        if (vertex.rightSon != null) ++numOfChild
        return numOfChild
    }

    /**
     * Retrieves the child vertex of the given vertex.
     * @param vertex The vertex whose child is to be retrieved.
     * @return The child vertex of the given vertex.
     */
    private fun getChild(vertex: RBVertex<K, V>) = if (vertex.leftSon != null) vertex.leftSon else vertex.rightSon

    /**
     * Replaces the old vertex with the new vertex in the tree structure.
     * @param oldVertex The old vertex to be replaced.
     * @param newVertex The new vertex that replaces the old vertex.
     */
    private fun replaceVertexBy(
        oldVertex: RBVertex<K, V>,
        newVertex: RBVertex<K, V>?,
    ) {
        if (root == oldVertex) {
            root = newVertex
        } else if (oldVertex == oldVertex.parent?.leftSon) {
            oldVertex.parent?.leftSon = newVertex
        } else {
            oldVertex.parent?.rightSon = newVertex
        }
        newVertex?.parent = oldVertex.parent
    }

    /**
     * Performs a left rotation on the given vertex.
     * Suppose that vertex has a rightSon. Swap parent and rightSon, rightSon's leftSon becomes parent's rightSon.
     * @param vertex The vertex on which the left rotation is to be performed.
     */
    private fun rotateLeft(vertex: RBVertex<K, V>) {
        val rightVertex: RBVertex<K, V>? = vertex.rightSon
        vertex.rightSon = rightVertex?.leftSon
        rightVertex?.leftSon.let { rightVertex?.leftSon?.parent = vertex }
        rightVertex?.parent = vertex.parent
        when {
            vertex.parent == null -> root = rightVertex
            vertex == vertex.parent?.rightSon -> vertex.parent?.rightSon = rightVertex
            else -> vertex.parent?.leftSon = rightVertex
        }
        vertex.parent = rightVertex
        rightVertex?.leftSon = vertex
    }

    /**
     * Performs a right rotation on the given vertex.
     * Suppose that vertex has a leftSon. Swap parent and leftSon, leftSon's rightSon becomes parent's leftSon.
     * @param vertex The vertex on which the right rotation is to be performed.
     */
    private fun rotateRight(vertex: RBVertex<K, V>) {
        val leftVertex: RBVertex<K, V>? = vertex.leftSon
        vertex.leftSon = leftVertex?.rightSon
        leftVertex?.rightSon.let { leftVertex?.rightSon?.parent = vertex }
        leftVertex?.parent = vertex.parent
        when {
            vertex.parent == null -> root = leftVertex
            vertex == vertex.parent?.leftSon -> vertex.parent?.leftSon = leftVertex
            else -> vertex.parent?.rightSon = leftVertex
        }
        vertex.parent = leftVertex
        leftVertex?.rightSon = vertex
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
