package vertexes

/**
 * Represents a vertex in an AVL tree
 *
 * @param K key type
 * @param V value type
 * @property key
 * @property value
 * @property leftSon `AVLVertex<K, V>?` type,
 * @property rightSon `AVLVertex<K, V>?` type
 * @property sonsHeightDiff 'Int' type, difference in height between the left and right subtrees
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
     * Constructs vertex for AVL tree with the specified key and value
     *
     * @param key `K` type
     * @param value `V` type
     * @param leftSon `AVLVertex<K, V>?` type
     * @param rightSon `AVLVertex<K, V>?` type
     * @param sonsHeightDiff 'Int' type, 0 by default; difference in height between the left and right subtrees
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
