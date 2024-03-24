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

    private fun removeRec(key: K, vertex : AVLVertex<K,V>) : Triple <RemoveStage, AVLVertex<K,V>, V?> {
        val nextCallReturned : Triple <RemoveStage, AVLVertex<K,V>?, V?>
        // Triple consists of:
        // 1) remove stage
        // 2) if RemoveStage == a : just a vertex (don't need it later)
        // if RemoveStage == b : the root of the changed subtree
        // if RemoveStage == c : the removed vertex
        // 3) a value of the removed vertex (or null if key not exists)
        when (compare(key, vertex.key)) {
            -1 -> {
                if (vertex.leftSon == null) return Triple(RemoveStage.a, vertex, null)
                nextCallReturned = removeRec(key, vertex.leftSon as AVLVertex<K,V>)
            }
            1 -> {
                if (vertex.rightSon == null) return Triple(RemoveStage.a, vertex, null)
                nextCallReturned = removeRec(key, vertex.rightSon as AVLVertex<K,V>)
            }
            else -> {
                size--
                return when ((vertex.leftSon == null) to (vertex.rightSon == null)) {
                    true to true -> Triple(RemoveStage.c, vertex, vertex.value)
                    true to false -> Triple(RemoveStage.b,
                        vertex.rightSon as AVLVertex<K,V>, vertex.value)
                    false to true -> Triple(RemoveStage.b,
                        vertex.leftSon as AVLVertex<K,V>, vertex.value)
                    else -> Triple(RemoveStage.b,
                        prepareLargestLowerToReplaceVertex(vertex) as AVLVertex<K,V>, vertex.value)
                }
            } 
        }
        fun doBalanceChoreWhenLeftSubTreeChanged() : Triple <RemoveStage, AVLVertex<K,V>, V?> {
            if (nextCallReturned.component2().sonsHeightDiff in listOf(-1,1))
                return Triple(RemoveStage.a, vertex, nextCallReturned.third)
            if (vertex.sonsHeightDiff - 1 == -2) 
                return Triple(RemoveStage.b, balance(vertex), nextCallReturned.third)
            vertex.sonsHeightDiff--
            return Triple(RemoveStage.b, vertex, nextCallReturned.third) 
        }
        fun doBalanceChoreWhenRightSubTreeChanged() : Triple <RemoveStage, AVLVertex<K,V>, V?> {
            if (nextCallReturned.component2().sonsHeightDiff in listOf(-1,1))
                return Triple(RemoveStage.a, vertex, nextCallReturned.third)
            if (vertex.sonsHeightDiff + 1 == 2) 
                return Triple(RemoveStage.b, balance(vertex), nextCallReturned.third)
            vertex.sonsHeightDiff++
            return Triple(RemoveStage.b, vertex, nextCallReturned.third) 
        }
        when (nextCallReturned.component1()) {
            RemoveStage.a -> return nextCallReturned
            RemoveStage.b -> when (nextCallReturned.component2()) {
                vertex.leftSon -> return doBalanceChoreWhenLeftSubTreeChanged()
                vertex.rightSon -> return doBalanceChoreWhenRightSubTreeChanged()
                else ->
                    when (compare(nextCallReturned.component2().key, vertex.key)) {
                        -1 -> {
                            vertex.leftSon = nextCallReturned.component2()
                            return doBalanceChoreWhenLeftSubTreeChanged()
                        }
                        else -> {
                            vertex.rightSon = nextCallReturned.component2()
                            return doBalanceChoreWhenRightSubTreeChanged()
                        }
                    }
            }
            RemoveStage.c ->
                when (compare(nextCallReturned.component2().key, vertex.key)) {
                    -1 -> {
                        vertex.leftSon = null
                        return doBalanceChoreWhenLeftSubTreeChanged()
                    }
                    else -> {
                        vertex.rightSon = null
                        return doBalanceChoreWhenRightSubTreeChanged()
                    }
                }  
            
        }
    }

    private fun prepareLargestLowerToReplaceVertex(vertex : AVLVertex<K,V> ) : AVLVertex<K,V>? {
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
