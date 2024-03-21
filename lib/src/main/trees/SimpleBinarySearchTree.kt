package main.trees
import main.vertexes.SimpleBSTVertex

class SimpleBinarySearchTree<K, V> : AbstractBinarySearchTree<K, V, SimpleBSTVertex<K,V>> {

    override fun put(key: K, value: V, replaceIfExists: Boolean) {
        putRec(key, value, replaceIfExists)
    }

    private fun putRec(key: K, value: V, replaceIfExists: Boolean, vertex: SimpleBSTVertex<K, V>? = root) {
        if (root == null) {
            root = SimpleBSTVertex(key, value)
            return
        }

        val cpr = comparator

        if (cpr != null && vertex != null) {
            if (vertex.key == key && replaceIfExists) {
                vertex.value = value
                return
            } else if (vertex.key == key && !replaceIfExists) {
                return
            }

            if (cpr.compare(key, vertex.key) < 0) {
                if (vertex.leftSon == null) {
                    vertex.leftSon = SimpleBSTVertex(key, value)
                    return
                }
                putRec(key, value, replaceIfExists, vertex.leftSon)
            }
            else {
                if (vertex.rightSon == null) {
                    vertex.rightSon = SimpleBSTVertex(key, value)
                    return
                }
                putRec(key, value, replaceIfExists, vertex.rightSon)
            }
        }
        else if (cpr == null && vertex != null) {
            if (vertex.key == key && replaceIfExists) {
                vertex.value = value
                return
            } else if (vertex.key == key && !replaceIfExists) {
                return
            }

            val comparableKey: Comparable<K> = key as Comparable<K>
            if (comparableKey.compareTo(vertex.key) < 0) {
                if (vertex.leftSon == null) {
                    vertex.leftSon = SimpleBSTVertex(key, value)
                    return
                }
                putRec(key, value, replaceIfExists, vertex.leftSon)
            }
            else {
                if (vertex.rightSon == null) {
                    vertex.rightSon = SimpleBSTVertex(key, value)
                    return
                }
                putRec(key, value, replaceIfExists, vertex.rightSon)
            }
        }
    }

    override fun remove(key: K): V? {TODO()}

    constructor(comparator: Comparator<K>?) : super(comparator)

    constructor(map: Map<K, V>, comparator: Comparator<K>? = null) : super(map, comparator)
}
