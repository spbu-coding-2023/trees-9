package main.trees
import main.vertexes.RBVertex

class RBSearchTree<K, V> : AbstractBinarySearchTree<K, V, RBVertex<K,V>> {

    override fun remove(key: K): V? {
        val vertex: RBVertex<K, V> = getVertex(key) ?: return null
        val value = vertex.value
        var isVertexRed = vertex.isRed
        var child: RBVertex<K, V>? = null

        if (countChildren(vertex) < 2){
            child = getChild(vertex)
            replaceVertexBy(vertex, child)
        }

        else{
            val vertexForSwap = getMinKeyNodeRec(vertex.rightSon)
            vertexForSwap?.let{
                vertex.key = it.key
                vertex.value = it.value
                isVertexRed = it.isRed
                child = getChild(it)
                replaceVertexBy(it, child)
            }
        }

        if (!isVertexRed) balanceAfterRemove(child)

        return value
    }

    private fun balanceAfterRemove(vertex: RBVertex<K, V>?){ TODO() }

    private fun getVertex(key: K): RBVertex<K, V>? {
        var currentVertex: RBVertex<K, V>? = root

        while (currentVertex?.key != key){
            if (currentVertex == null) return null
            when (compareKeys(comparator, key, currentVertex.key)){
                -1 -> {
                    currentVertex = currentVertex.leftSon
                }
                1 -> {
                    currentVertex = currentVertex.rightSon
                }
            }
        }
        return currentVertex
    }

    override fun put(key: K, value: V, replaceIfExists: Boolean) {
        var currentVertex: RBVertex<K, V>? = root
        var parent: RBVertex<K, V>? = null
        var isLeft: Boolean = false

        while (currentVertex != null) {
            when (compareKeys(comparator, key, currentVertex.key)){
                -1 -> {
                    parent = currentVertex
                    currentVertex = currentVertex.leftSon
                    isLeft = true
                }
                0 -> {
                    if (replaceIfExists) currentVertex.value = value
                    break
                }
                1 -> {
                    parent = currentVertex
                    currentVertex = currentVertex.rightSon
                    isLeft = false
                }
            }
        }

        if (currentVertex == null){
            currentVertex = RBVertex(key, value, null, null, true, parent)
            if (isLeft) parent?.let { parent.leftSon = currentVertex }
            else parent?.let { parent.rightSon = currentVertex }
        }

        balanceAfterPut(currentVertex)
    }

    private fun balanceAfterPut(vertex: RBVertex<K, V>) {
        var currentVertex = vertex

        while (currentVertex.parent?.isRed == true){
            val grandparent = currentVertex.parent?.parent

            if (currentVertex.parent == grandparent?.leftSon){
                val uncle = grandparent?.rightSon

                if (uncle?.isRed == true){
                    currentVertex.parent?.isRed = false
                    uncle.isRed = false
                    grandparent.isRed = true
                    currentVertex = grandparent
                }

                else {
                    if (currentVertex == currentVertex.parent?.rightSon) {
                        currentVertex = currentVertex.parent ?: currentVertex
                        rotateLeft(currentVertex)
                    }

                    currentVertex.parent?.isRed = false
                    currentVertex.parent?.parent?.isRed = true
                    val vertexForRightRotate = currentVertex.parent?.parent
                    vertexForRightRotate?.let { rotateRight(vertexForRightRotate) }
                }
            }

            else {
                val uncle = grandparent?.leftSon

                if (uncle?.isRed == true){
                    currentVertex.parent?.isRed = false
                    uncle.isRed = false
                    grandparent.isRed = true
                    currentVertex = grandparent
                }

                else {
                    if (currentVertex == currentVertex.parent?.leftSon) {
                        currentVertex = currentVertex.parent ?: currentVertex
                        rotateRight(currentVertex)
                    }

                    currentVertex.parent?.isRed = false
                    currentVertex.parent?.parent?.isRed = true
                    val vertexForLeftRotate = currentVertex.parent?.parent
                    vertexForLeftRotate?.let { rotateLeft(vertexForLeftRotate) }
                }
            }
        }
        root?.isRed = false
    }

    private fun countChildren(vertex: RBVertex<K, V>): Int {
        var numOfChild = 0
        if (vertex.leftSon != null) ++numOfChild
        if (vertex.rightSon != null) ++numOfChild
        return numOfChild
    }

    private fun getChild(vertex: RBVertex<K, V>) = if (vertex.leftSon != null) vertex.leftSon else vertex.rightSon

    private fun replaceVertexBy(oldVertex: RBVertex<K, V>, newVertex: RBVertex<K, V>?){
        if (root == oldVertex) root = newVertex
        else if (oldVertex == oldVertex.parent?.leftSon) oldVertex.parent?.leftSon = newVertex
        else oldVertex.parent?.rightSon = newVertex
        newVertex?.parent = oldVertex.parent
    }

    //suppose that vertex has a rightSon
    private fun rotateLeft(vertex: RBVertex<K, V>) {
        val rightVertex: RBVertex<K, V>? = vertex.rightSon
        vertex.rightSon = rightVertex?.leftSon
        rightVertex?.leftSon.let { rightVertex?.leftSon?.parent = vertex }
        rightVertex?.parent = vertex.parent
        when {
            vertex.parent == null -> root = rightVertex
            vertex == vertex.parent?.leftSon -> vertex.parent?.leftSon = rightVertex
            else -> vertex.parent?.rightSon = rightVertex
        }
        vertex.parent = rightVertex
        rightVertex?.leftSon = vertex
    }

    //suppose that vertex has a leftSon
    private fun rotateRight(vertex: RBVertex<K, V>) {
        val leftVertex: RBVertex<K, V>? = vertex.leftSon
        vertex.leftSon = leftVertex?.rightSon
        leftVertex?.rightSon.let { leftVertex?.rightSon?.parent = vertex}
        leftVertex?.parent = vertex.parent
        when {
            vertex.parent == null -> root = leftVertex
            vertex == vertex.parent?.leftSon -> vertex.parent?.leftSon = leftVertex
            else -> vertex.parent?.rightSon = leftVertex
        }
        vertex.parent = leftVertex
        leftVertex?.rightSon = vertex
    }

    private fun compareKeys(cpr: Comparator<K>?, firstKey: K, secondKey: K): Int{
        return if (cpr != null) {
            if (cpr.compare(firstKey, secondKey) < 0) -1
            else if (cpr.compare(firstKey, secondKey) == 0) 0
            else 1
        }
        else {
            val comparableKey = firstKey as Comparable<K>
            if (comparableKey.compareTo(secondKey) < 0) -1
            else if (comparableKey.compareTo(secondKey) == 0) 0
            else 1
        }
    }

    constructor(comparator: Comparator<K>? = null) : super(comparator)

    constructor(map: Map<K, V>, comparator: Comparator<K>? = null) : super(map, comparator)
}
