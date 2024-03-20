package main.nodes

class RBVertex<K, V>(
    override var key: K,
    override var value: V
) : InterfaceBSTVertex<K, V, RBVertex<K, V>> {

    var isRed = false
    var parent: RBVertex<K, V>? = null
    override var leftSon: RBVertex<K, V>? = null
    override var rightSon: RBVertex<K, V>? = null

    constructor(
        key: K,
        value: V,
        leftSon: RBVertex<K, V>?,
        rightSon: RBVertex<K, V>?,
        isRed: Boolean,
        parent: RBVertex<K, V>?
    ) : this(key, value) {
        this.leftSon = leftSon; this.rightSon = rightSon; this.parent = parent; this.isRed = isRed
    }
}

