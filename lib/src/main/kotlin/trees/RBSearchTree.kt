package main.trees

import main.vertexes.RBVertex

class RBSearchTree<K, V> : AbstractBinarySearchTree<K, V, RBVertex<K, V>> {

    override fun put(key: K, value: V, replaceIfExists: Boolean) {
        TODO()
    }

    override fun remove(key: K): V? {
        TODO()
    }

    constructor(comparator: Comparator<K>? = null) : super(comparator)

    constructor(map: Map<K, V>, replaceIfExists: Boolean = true, comparator: Comparator<K>? = null) : super(
        map,
        replaceIfExists,
        comparator
    )
}