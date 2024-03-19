package main.trees
import nodes.RBVertex

class RBSearchTree<K, V> : AbstractBinarySearchTree<K, V, RBVertex<K,V>> {

    override fun put(key: K, value: V) {TODO()}

    override fun remove(key: K): V? {TODO()}

    constructor(comparator: Comparator<K>? = null) : super(comparator)

    constructor(map: Map<K, V>, comparator: Comparator<K>? = null) : super(map, comparator)
}
