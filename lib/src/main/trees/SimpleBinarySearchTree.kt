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
        if (vertex == null) return Pair(null, null)

        when (compareKeys(key, vertex.key)) {
            -1 -> {
                val (updateLeftSon, deletedValue) = removeRec(key, vertex.leftSon)
                vertex.leftSon = updateLeftSon
                return Pair(vertex, deletedValue)
            }
            1 -> {
                val (updateLeftSon, deletedValue) = removeRec(key, vertex.rightSon)
                vertex.rightSon = updateLeftSon
                return Pair(vertex, deletedValue)
            }
            else -> {
                val deletedValue: V = vertex.value
                if (vertex.leftSon == null || vertex.rightSon == null) {
                    if (vertex.leftSon == null) return Pair(vertex.rightSon, deletedValue)
                    else return Pair(vertex.leftSon, deletedValue)
                }
                else {
                    val minKeyRightSubtreeNode: SimpleBSTVertex<K, V>? = getMinKeyNodeRec(vertex.rightSon)
                    minKeyRightSubtreeNode?.let {
                        vertex.key = it.key
                        vertex.value = it.value
                        val (updatedRightSon, _) = removeRec(it.key, vertex.rightSon)
                        vertex.rightSon = updatedRightSon
                    }
                    return Pair(vertex, deletedValue)
                }
            }
        }
    }

    constructor(comparator: Comparator<K>?) : super(comparator)

    constructor(map: Map<K, V>, replaceIfExists: Boolean = true, comparator: Comparator<K>? = null) : super(map, replaceIfExists, comparator)
}
