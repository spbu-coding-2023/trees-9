package main.trees

import main.vertexes.AVLVertex

class AVLSearchTree<K, V> : AbstractBinarySearchTree<K, V, AVLVertex<K, V>> {

    override fun put(key: K, value: V, replaceIfExists: Boolean) {
        TODO()
    }

    override fun remove(key: K): V? {
        TODO()
    }

    constructor (comparator: Comparator<K>? = null) : super(comparator)

    constructor(map: Map<K, V>, replaceIfExists: Boolean = true, comparator: Comparator<K>? = null) : super(
        map,
        replaceIfExists,
        comparator
    )
}