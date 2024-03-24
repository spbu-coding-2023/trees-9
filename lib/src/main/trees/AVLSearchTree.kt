package main.trees
import main.vertexes.AVLVertex

class AVLSearchTree<K, V> : AbstractBinarySearchTree<K, V, AVLVertex<K,V>> {

    override fun put(key: K, value: V, replaceIfExists: Boolean) {
        if (!isEmpty()) {
            when (val putRecReturned = putRec(key, value, replaceIfExists, root as AVLVertex<K,V>)) {
                null, root -> {}
                else -> root = putRecReturned
            }
            return
        }
        root = AVLVertex(key, value)
        size++
    }

    private fun putRec
    (key: K, value: V, replaceIfExists: Boolean, vertex: AVLVertex<K,V>) : AVLVertex<K,V>? {
        fun putRecShort(vrtx : AVLVertex<K,V>) : AVLVertex<K,V>? {
            return putRec(key, value, replaceIfExists, vrtx)
        } 
        val nextCallReturned : AVLVertex<K,V>? 
        when (compare(key, vertex.key)) {
            -1 -> {
                if (vertex.leftSon == null){
                    vertex.leftSon = AVLVertex(key, value)
                    vertex.sonsHeightDiff++
                    size++
                    return vertex
                }
                nextCallReturned = putRecShort(vertex.leftSon as AVLVertex<K,V>)
            }
            0 -> {
                if (replaceIfExists) {
                    vertex.key = key
                    vertex.value = value
                }
                return null
            }
            else -> {
                if (vertex.rightSon == null) {
                    vertex.rightSon = AVLVertex(key, value)
                    vertex.sonsHeightDiff--
                    size++
                    return vertex
                }
                nextCallReturned = putRecShort(vertex.rightSon as AVLVertex<K,V> )
            }
        }
        if (nextCallReturned == null) return null
        fun doBalanceChoreWhenLeftSubTreeChanged() : AVLVertex<K,V>? {
            if (nextCallReturned.sonsHeightDiff == 0) return null
            if (vertex.sonsHeightDiff + 1 == 2) return balance(vertex)
            vertex.sonsHeightDiff++
            return vertex 
        }
        fun doBalanceChoreWhenRightSubTreeChanged() : AVLVertex<K,V>? {
            if (nextCallReturned.sonsHeightDiff == 0) return null
            if (vertex.sonsHeightDiff - 1 == -2) return balance(vertex)
            vertex.sonsHeightDiff--
            return vertex
        }
        when (nextCallReturned){
            vertex.leftSon -> return doBalanceChoreWhenLeftSubTreeChanged()
            vertex.rightSon -> return doBalanceChoreWhenRightSubTreeChanged()             
            else -> {
                when (compare(nextCallReturned.key, vertex.key)) {
                    -1 -> {
                        vertex.leftSon = nextCallReturned
                        return doBalanceChoreWhenLeftSubTreeChanged()
                    }
                    else -> {
                        vertex.rightSon = nextCallReturned
                        return doBalanceChoreWhenRightSubTreeChanged()
                    }
                }
            }
        }
    }
    
    override fun remove(key: K): V? {TODO()}

    enum class RemoveStage {a, b, c}
    // a - don't need tree changes anymore
    // b - probably need some tree changes, but not nulling
    // c - need to null due "Son" property of (if exists) the parent of removed vertex + b

    private fun replaceByLargestLower(vertex : AVLVertex<K,V> ) : AVLVertex<K,V>? {
        val substitute = getMaxKeyNodeRec(vertex.leftSon)
        if (substitute == null) return null
        remove(substitute.key)
        substitute.leftSon = vertex.leftSon
        substitute.rightSon = vertex.rightSon
        return substitute
    }

    private fun balance(curVertex: AVLVertex<K,V>) : AVLVertex<K,V> {
        if(curVertex.sonsHeightDiff == -1) {
            val rightSon = curVertex.rightSon as AVLVertex<K,V>
            return if (rightSon.sonsHeightDiff == 1) bigRotateLeft(curVertex, rightSon)
            else rotateLeft(curVertex, rightSon)
        }
        else {
            val leftSon = curVertex.leftSon as AVLVertex<K,V>
            return if (leftSon.sonsHeightDiff == -1) bigRotateRight(curVertex, leftSon)
            else rotateRight(curVertex, leftSon)
        }    
    }

    private fun rotateLeft(curVertex: AVLVertex<K,V>, rightSon : AVLVertex<K,V> ) : AVLVertex<K,V> {
        curVertex.rightSon = rightSon.leftSon
        rightSon.leftSon = curVertex
        when (rightSon.sonsHeightDiff) {
            0 -> rightSon.sonsHeightDiff = 1
            -1 -> {curVertex.sonsHeightDiff = 0; rightSon.sonsHeightDiff = 0}
        }
        return rightSon
    }

    private fun rotateRight(curVertex: AVLVertex<K,V>, leftSon : AVLVertex<K,V>) : AVLVertex<K,V> {
        curVertex.leftSon = leftSon.rightSon
        leftSon.rightSon = curVertex
        when (leftSon.sonsHeightDiff) {
            0 -> leftSon.sonsHeightDiff = -1
            1 -> {curVertex.sonsHeightDiff = 0; leftSon.sonsHeightDiff = 0}
        }
        return leftSon
    }
    
    private fun bigRotateLeft(curVertex: AVLVertex<K,V>, rightSon : AVLVertex<K,V> ) : AVLVertex<K,V> {
        val curRightSon = rotateRight(rightSon, rightSon.leftSon as AVLVertex<K,V>)
        curVertex.rightSon = curRightSon
        return rotateLeft(curVertex, curRightSon)
    }

    private fun bigRotateRight(curVertex: AVLVertex<K,V>, leftSon : AVLVertex<K,V>) : AVLVertex<K,V> {
        val curLeftSon = rotateLeft(leftSon, leftSon.rightSon as AVLVertex<K,V>)
        curVertex.leftSon = curLeftSon
        return rotateRight(curVertex, curLeftSon)
    }

    constructor (comparator: Comparator<K>? = null) : super(comparator)

    constructor(map: Map<K, V>, comparator: Comparator<K>? = null) : super(map, comparator)
}
