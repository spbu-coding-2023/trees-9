package main.vertexes

/**
 * Represents a vertex in an AVL tree.
 * @param K Type of keys.
 * @param V Type of values.
 * @property key The key associated with this vertex.
 * @property value The value associated with this vertex.
 * @property leftSon The left child vertex of this vertex, of type [AVLVertex].
 * @property rightSon The right child vertex of this vertex, of type [AVLVertex].
 * @property sonsHeightDiff The difference in height between the left and right subtrees.
 */

class AVLVertex<K, V>(
    override var key: K,
    override var value: V,
) : InterfaceBSTVertex<K, V, AVLVertex<K, V>> {
    override var leftSon: AVLVertex<K, V>? = null
    override var rightSon: AVLVertex<K, V>? = null

    /**
     * The difference in height between the left and right subtrees.
     */
    var sonsHeightDiff: Int = 0

    /**
     * Constructs a vertex with the specified key and value.
     * @param key The key associated with this vertex.
     * @param value The value associated with this vertex.
     * @param leftSon The left child vertex of this vertex.
     * @param rightSon The right child vertex of this vertex.
     * @param sonsHeightDiff The difference in height between the left and right subtrees.
     */
    constructor(
        key: K,
        value: V,
        leftSon: AVLVertex<K, V>?,
        rightSon: AVLVertex<K, V>?,
        sonsHeightDiff: Int = 0,
    ) : this(key, value) {
        this.leftSon = leftSon
        this.rightSon = rightSon
        this.sonsHeightDiff = sonsHeightDiff
    }
}
