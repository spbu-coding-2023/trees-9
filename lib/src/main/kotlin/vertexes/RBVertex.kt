package vertexes

/**
 * Represents a vertex in a Red-Black tree
 *
 * @param K key type
 * @param V value type
 * @property key
 * @property value
 * @property leftSon `RBVertex<K, V>?` type, `null` by default
 * @property rightSon `RBVertex<K, V>?` type, `null` by default
 * @property parent `RBVertex<K, V>?` type, `null` by default
 */
class RBVertex<K, V>(
    override var key: K,
    override var value: V,
) : InterfaceBSTVertex<K, V, RBVertex<K, V>> {
    var isRed = true
    var parent: RBVertex<K, V>? = null
    override var leftSon: RBVertex<K, V>? = null
    override var rightSon: RBVertex<K, V>? = null

    /**
     * Constructs a new RBVertex with the specified parameters
     *
     * @param key `K` type
     * @param value `V` type
     * @param leftSon `RBVertex<K, V>?` type
     * @param rightSon `RBVertex<K, V>?` type
     * @param parent `RBVertex<K, V>?` type
     */
    constructor(
        key: K,
        value: V,
        leftSon: RBVertex<K, V>?,
        rightSon: RBVertex<K, V>?,
        isRed: Boolean,
        parent: RBVertex<K, V>?,
    ) : this(key, value) {
        this.leftSon = leftSon
        this.rightSon = rightSon
        this.parent = parent
        this.isRed = isRed
    }
}
