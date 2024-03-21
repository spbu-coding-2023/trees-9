package main.vertexes

class SimpleBSTVertex<K, V>(
    override var key: K,
    override var value: V
): InterfaceBSTVertex<K, V, SimpleBSTVertex<K, V>> {

    override var leftSon: SimpleBSTVertex<K, V>? = null
    override var rightSon: SimpleBSTVertex<K, V>? = null

    constructor(
        key: K,
        value: V,
        leftSon: SimpleBSTVertex<K, V>?,
        rightSon: SimpleBSTVertex<K, V>?
    ) : this(key, value) {
        this.leftSon = leftSon; this.rightSon = rightSon
    }
}
