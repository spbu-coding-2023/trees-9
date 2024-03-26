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
        when (compareKeys(key, vertex.key)) {
            0 -> if (replaceIfExists) vertex.value = value
            -1 -> {
                if (vertex.leftSon == null) vertex.leftSon = SimpleBSTVertex(key, value)
                else putRec(key, value, replaceIfExists, vertex.leftSon)
            }
            1 -> {
                if (vertex.rightSon == null) vertex.rightSon = SimpleBSTVertex(key, value)
                else putRec(key, value, replaceIfExists, vertex.rightSon)
            }
        }
    }

    override fun remove(key: K): V? {
        val returnedVertexAndDeletedValue: Pair<SimpleBSTVertex<K, V>?, V?> = removeRec(key)
        return returnedVertexAndDeletedValue.second
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
