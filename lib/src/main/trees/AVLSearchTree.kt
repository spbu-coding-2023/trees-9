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

    private fun putRec(key: K, value: V,
     replaceIfExists: Boolean, vertex: AVLVertex<K,V>) : AVLVertex<K,V>? {
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
                if (compare(nextCallReturned.key, vertex.key) == -1) {
                        vertex.leftSon = nextCallReturned
                        return doBalanceChoreWhenLeftSubTreeChanged()
                }
                        vertex.rightSon = nextCallReturned
                        return doBalanceChoreWhenRightSubTreeChanged()
            }
        }
    }
    
    override fun remove(key: K): V? {TODO()}

    enum class RemovalStage {A, B, C}
    // a - don't need tree changes anymore
    // b - probably need some tree changes, but not nulling
    // c - need to null due "Son" property of (if exists) the parent of removed vertex + b

    private fun removeRec(key: K, vertex : AVLVertex<K,V>) : Triple <RemovalStage, AVLVertex<K,V>, V?> {
        val nextCallReturned : Triple <RemovalStage, AVLVertex<K,V>?, V?>
        // Triple consists of:
        // 1) removal stage
        // 2) if RemovalStage == a : just a vertex (don't need it later)
        // if RemovalStage == b : the root of the changed subtree
        // if RemovalStage == c : the removed vertex
        // 3) a value of the removed vertex (or null if key not exists)
        when (compare(key, vertex.key)) {
            -1 -> {
                if (vertex.leftSon == null) return Triple(RemovalStage.A, vertex, null)
                nextCallReturned = removeRec(key, vertex.leftSon as AVLVertex<K,V>)
            }
            1 -> {
                if (vertex.rightSon == null) return Triple(RemovalStage.A, vertex, null)
                nextCallReturned = removeRec(key, vertex.rightSon as AVLVertex<K,V>)
            }
            else -> {
                size--
                return when ((vertex.leftSon == null) to (vertex.rightSon == null)) {
                    true to true -> Triple(RemovalStage.C, vertex, vertex.value)
                    true to false -> Triple(RemovalStage.B,
                     vertex.rightSon as AVLVertex<K,V>, vertex.value)
                    false to true -> Triple(RemovalStage.B,
                     vertex.leftSon as AVLVertex<K,V>, vertex.value)
                    else -> Triple(RemovalStage.B,
                     prepareLargestLowerToReplaceVertex(vertex) as AVLVertex<K,V>, vertex.value)
                }
            } 
        }
        fun doBalanceChoreWhenLeftSubTreeChanged() : Triple <RemovalStage, AVLVertex<K,V>, V?> {
            if (nextCallReturned.component2().sonsHeightDiff in listOf(-1, 1))
                return Triple(RemovalStage.A, vertex, nextCallReturned.component3())
            if (vertex.sonsHeightDiff - 1 == -2) 
                return Triple(RemovalStage.B, balance(vertex), nextCallReturned.component3())
            vertex.sonsHeightDiff--
            return Triple(RemovalStage.B, vertex, nextCallReturned.third) 
        }
        fun doBalanceChoreWhenRightSubTreeChanged() : Triple <RemovalStage, AVLVertex<K,V>, V?> {
            if (nextCallReturned.component2().sonsHeightDiff in listOf(-1, 1))
                return Triple(RemovalStage.A, vertex, nextCallReturned.component3())
            if (vertex.sonsHeightDiff + 1 == 2) 
                return Triple(RemovalStage.B, balance(vertex), nextCallReturned.component3())
            vertex.sonsHeightDiff++
            return Triple(RemovalStage.B, vertex, nextCallReturned.component3()) 
        }
        when (nextCallReturned.component1()) {
            RemovalStage.A -> return nextCallReturned
            RemovalStage.B -> when (nextCallReturned.component2()) {
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
            RemovalStage.C ->
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
        substitute.sonsHeightDiff = vertex.sonsHeightDiff
        return substitute
    }

    private fun balance(curVertex: AVLVertex<K,V>) : AVLVertex<K,V> {
        var (rightSon, leftSon) = List<AVLVertex<K,V>?>(2){null}
        fun setTwoSonHeightDiffs(values : Pair<Int, Int>) {
            curVertex.sonsHeightDiff = values.component1()
            if (rightSon != null){
                (rightSon as AVLVertex<K,V>).sonsHeightDiff = values.component2()
                return
            }
            (leftSon as AVLVertex<K,V>).sonsHeightDiff = values.component2()
        }
            
        if(curVertex.sonsHeightDiff == -1) {
            rightSon = curVertex.rightSon as AVLVertex<K,V>
            return if (rightSon.sonsHeightDiff == 1) {
                val rightSonSLeftSon = rightSon.leftSon as AVLVertex<K,V>
                val subtreeRoot = bigRotateLeft(curVertex, rightSon)
                setTwoSonHeightDiffs(
                    when (rightSonSLeftSon.sonsHeightDiff) {
                        1 -> 0 to -1
                        -1 -> 1 to 0
                        else -> 0 to 0
                    }
                )
                rightSonSLeftSon.sonsHeightDiff = 0
                subtreeRoot
            }
            else {
                val subtreeRoot = rotateLeft(curVertex, rightSon)
                setTwoSonHeightDiffs(
                    if (rightSon.sonsHeightDiff == 0) -1 to 1
                    else 0 to 0
                    )
                subtreeRoot
            }

        }
            leftSon = curVertex.leftSon as AVLVertex<K,V>
            return if (leftSon.sonsHeightDiff == -1) {
                val leftSonSRightSon = leftSon.rightSon as AVLVertex<K,V>
                val subtreeRoot = bigRotateRight(curVertex, leftSon)
                setTwoSonHeightDiffs(
                    when (leftSonSRightSon.sonsHeightDiff) {
                        -1 -> 0 to 1
                        1 -> -1 to 0
                        else -> 0 to 0
                    }
                )
                leftSonSRightSon.sonsHeightDiff = 0
                subtreeRoot
            }
            else {
                val subtreeRoot = rotateRight(curVertex, leftSon)
                setTwoSonHeightDiffs(
                    if (leftSon.sonsHeightDiff == 0) 1 to -1
                    else 0 to 0
                    )
                subtreeRoot
            }
    }

    private fun rotateLeft(curVertex: AVLVertex<K,V>, rightSon : AVLVertex<K,V> ) : AVLVertex<K,V> {
        curVertex.rightSon = rightSon.leftSon
        rightSon.leftSon = curVertex
        return rightSon
    }

    private fun rotateRight(curVertex: AVLVertex<K,V>, leftSon : AVLVertex<K,V>) : AVLVertex<K,V> {
        curVertex.leftSon = leftSon.rightSon
        leftSon.rightSon = curVertex
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
