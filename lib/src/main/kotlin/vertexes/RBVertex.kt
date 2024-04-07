package main.vertexes

/**
 * Represents a vertex in a Red-Black Tree.
 * @param K Type of keys.
 * @param V Type of values.
 * @property key The key associated with this vertex.
 * @property value The value associated with this vertex.
 * @property color The color of this vertex (red or black).
 * @property parent The parent vertex of this vertex.
 * @property leftSon The left child vertex of this vertex.
 * @property rightSon The right child vertex of this vertex.
 */
class RBVertex<K, V>(
    override var key: K,
    override var value: V,
) : InterfaceBSTVertex<K, V, RBVertex<K, V>> {
    enum class Color {
        RED,
        BLACK
    }
    var color: Color = Color.RED
    var parent: RBVertex<K, V>? = null
    override var leftSon: RBVertex<K, V>? = null
    override var rightSon: RBVertex<K, V>? = null

    /**
     * Creates a new RBVertex with the specified parameters.
     * @param key The key associated with this vertex.
     * @param value The value associated with this vertex.
     * @param leftSon The left child vertex of this vertex.
     * @param rightSon The right child vertex of this vertex.
     * @param color The color of this vertex (red or black).
     * @param parent The parent vertex of this vertex.
     */
    constructor(
        key: K,
        value: V,
        leftSon: RBVertex<K, V>?,
        rightSon: RBVertex<K, V>?,
        color: Color,
        parent: RBVertex<K, V>?,
    ) : this(key, value) {
        this.leftSon = leftSon
        this.rightSon = rightSon
        this.parent = parent
        this.color = color
    }
}
