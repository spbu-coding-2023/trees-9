package vertexes

/**
 * Represents a simple vertex in a binary search tree
 *
 * @param K key type
 * @param V value type
 * @property key
 * @property value
 * @property leftSon `SimpleBSTVertex<K, V>?` type, `null` by default
 * @property rightSon `SimpleBSTVertex<K, V>?` type, `null` by default
 */
class SimpleBSTVertex<K, V>(
    override var key: K,
    override var value: V,
) : InterfaceBSTVertex<K, V, SimpleBSTVertex<K, V>> {
    override var leftSon: SimpleBSTVertex<K, V>? = null
    override var rightSon: SimpleBSTVertex<K, V>? = null

    /**
     * Constructs a simple vertex with the specified key and value
     *
     * @param key `K` type
     * @param value `V` type
     * @param leftSon `SimpleBSTVertex<K, V>?` type
     * @param rightSon `SimpleBSTVertex<K, V>?` type
     */
    constructor(
        key: K,
        value: V,
        leftSon: SimpleBSTVertex<K, V>?,
        rightSon: SimpleBSTVertex<K, V>?,
    ) : this(key, value) {
        this.leftSon = leftSon
        this.rightSon = rightSon
    }
}
