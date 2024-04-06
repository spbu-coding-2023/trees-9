package main.vertexes

/**
 * Represents a simple vertex in a Binary Search Tree.
 * @param K Type of keys.
 * @param V Type of values.
 * @property key The key associated with this vertex.
 * @property value The value associated with this vertex.
 * @property leftSon The left child vertex of this vertex, of type SimpleBSTVertex.
 * @property rightSon The right child vertex of this vertex, of type SimpleBSTVertex.
 */
class SimpleBSTVertex<K, V>(
    override var key: K,
    override var value: V,
) : InterfaceBSTVertex<K, V, SimpleBSTVertex<K, V>> {
    override var leftSon: SimpleBSTVertex<K, V>? = null
    override var rightSon: SimpleBSTVertex<K, V>? = null

    /**
     * Constructs a simple vertex with the specified key and value.
     * @param key The key associated with this vertex.
     * @param value The value associated with this vertex.
     * @param leftSon The left child vertex of this vertex.
     * @param rightSon The right child vertex of this vertex.
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
