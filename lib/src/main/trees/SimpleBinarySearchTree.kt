package main.trees
import main.vertexes.SimpleBSTVertex

class SimpleBinarySearchTree<K, V> : AbstractBinarySearchTree<K, V, SimpleBSTVertex<K,V>> {

    override fun put(key: K, value: V, replaceIfExists: Boolean) {
        putRec(key, value, replaceIfExists)
    }

    private fun putRec(key: K, value: V, replaceIfExists: Boolean = true, vertex: SimpleBSTVertex<K, V>? = root) {
        if (vertex == null) {
            root = SimpleBSTVertex(key, value)
            return
        }

        val cpr = comparator
        if (cpr != null) {
            if (cpr.compare(key, vertex.key) == 0 && replaceIfExists) vertex.value = value
            else if (cpr.compare(key, vertex.key) == 0 && !replaceIfExists) return

            else if (cpr.compare(key, vertex.key) < 0) {
                if (vertex.leftSon == null) vertex.leftSon = SimpleBSTVertex(key, value)
                else putRec(key, value, replaceIfExists, vertex.leftSon)
            }
            else {
                if (vertex.rightSon == null) vertex.rightSon = SimpleBSTVertex(key, value)
                else putRec(key, value, replaceIfExists, vertex.rightSon)
            }
        }
        else {
            val comparableKey: Comparable<K> = key as Comparable<K>
            if (comparableKey.compareTo(vertex.key) == 0 && replaceIfExists) vertex.value = value
            else if (comparableKey.compareTo(vertex.key) == 0 && !replaceIfExists) return

            else if (comparableKey.compareTo(vertex.key) < 0) {
                if (vertex.leftSon == null) vertex.leftSon = SimpleBSTVertex(key, value)
                else putRec(key, value, replaceIfExists, vertex.leftSon)
            }
            else {
                if (vertex.rightSon == null) vertex.rightSon = SimpleBSTVertex(key, value)
                else putRec(key, value, replaceIfExists, vertex.rightSon)
            }
        }
    }

    override fun remove(key: K): V? {
        val value = get(key)
        if (value == null) return null
        else removeRec(key)
        return value
    }

    private fun removeRec(key: K, vertex: SimpleBSTVertex<K, V>? = root): SimpleBSTVertex<K, V>? {
        if (vertex == null) return null
        val cpr = comparator
        if (cpr != null) {
            if (cpr.compare(key, vertex.key) < 0) vertex.leftSon = removeRec(key, vertex.leftSon)
            else if (cpr.compare(key, vertex.key) > 0) vertex.rightSon = removeRec(key, vertex.rightSon)

            else if (cpr.compare(key, vertex.key) == 0) {
                if (vertex.leftSon == null || vertex.rightSon == null) return if (vertex.leftSon == null) vertex.rightSon else vertex.leftSon
                else if (vertex.leftSon != null && vertex.rightSon != null) {
                    val minKeyRightSubtreeNode = getMinKeyNodeRec(vertex.rightSon)
                    if (minKeyRightSubtreeNode != null) {
                        vertex.key = minKeyRightSubtreeNode.key
                        vertex.value = minKeyRightSubtreeNode.value
                        removeRec(minKeyRightSubtreeNode.key, vertex.rightSon)
                    }
                }
            }
        }
        else {
            val comparableKey = key as Comparable<K>
            if (comparableKey.compareTo(vertex.key) < 0) vertex.leftSon = removeRec(key, vertex.leftSon)
            else if (comparableKey.compareTo(vertex.key) > 0) vertex.rightSon = removeRec(key, vertex.rightSon)

            else if (vertex.leftSon != null && vertex.rightSon != null) {
                val minKeyRightSubtreeNode = getMinKeyNodeRec(vertex.rightSon)
                if (minKeyRightSubtreeNode != null) {
                    vertex.key = minKeyRightSubtreeNode.key
                    vertex.value = minKeyRightSubtreeNode.value
                    removeRec(minKeyRightSubtreeNode.key, vertex.rightSon)
                }
            }
        }
        return vertex
    }

    constructor(comparator: Comparator<K>?) : super(comparator)

    constructor(map: Map<K, V>, replaceIfExists: Boolean = true, comparator: Comparator<K>? = null) : super(map, replaceIfExists, comparator)
}
