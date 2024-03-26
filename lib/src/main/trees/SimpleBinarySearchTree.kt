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

    private fun removeRec(key: K, vertex: SimpleBSTVertex<K, V>? = root): Pair<SimpleBSTVertex<K, V>?, V?> {
        var returnedVertexAndDeletedValue: Pair<SimpleBSTVertex<K, V>?, V?> = Pair(null, null)
        if (vertex != null) {
            when (compareKeys(key, vertex.key)) {
                -1 -> {
                    returnedVertexAndDeletedValue = removeRec(key, vertex.leftSon)
                    vertex.leftSon = returnedVertexAndDeletedValue.first
                }
                1 -> {
                    returnedVertexAndDeletedValue = removeRec(key, vertex.rightSon)
                    vertex.rightSon = returnedVertexAndDeletedValue.first
                }
                else -> {
                    if (vertex.leftSon == null || vertex.rightSon == null) {
                        if (vertex.leftSon == null) return Pair(vertex.rightSon, vertex.value)
                        else return Pair(vertex.leftSon, vertex.value)
                    }
                    else if (vertex.leftSon != null && vertex.rightSon != null) {
                        var returnValue: V? = null
                        val minKeyRightSubtreeNode: SimpleBSTVertex<K, V>? = getMinKeyNodeRec(vertex.rightSon)
                        minKeyRightSubtreeNode?.let {
                            returnValue = vertex.value
                            vertex.key = it.key
                            vertex.value = it.value
                            removeRec(it.key, vertex.rightSon)
                        }
                        return Pair(vertex, returnValue)
                    }
                }
            }
        }
        return Pair(vertex, returnedVertexAndDeletedValue.second)
    }

    constructor(comparator: Comparator<K>?) : super(comparator)

    constructor(map: Map<K, V>, replaceIfExists: Boolean = true, comparator: Comparator<K>? = null) : super(map, replaceIfExists, comparator)
}
