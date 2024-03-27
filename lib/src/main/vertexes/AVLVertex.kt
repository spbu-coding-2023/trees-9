package main.vertexes

class AVLVertex<K, V>(
    override var key: K,
    override var value: V
): InterfaceBSTVertex<K, V, AVLVertex<K, V>> {

    override var leftSon: AVLVertex<K, V>? = null
    override var rightSon: AVLVertex<K, V>? = null
    var sonsHeightDiff: Int = 0

    constructor(
        key: K,
        value: V,
        leftSon: AVLVertex<K, V>?,
        rightSon: AVLVertex<K, V>?
    ) : this(key, value) {
        this.leftSon = leftSon; this.rightSon = rightSon
    }
}
