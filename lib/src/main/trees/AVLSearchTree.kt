package main.trees
import main.vertexes.AVLVertex

class AVLSearchTree<K, V> : AbstractBinarySearchTree<K, V, AVLVertex<K,V>> {

    override fun put(key: K, value: V, replaceIfExists: Boolean) {TODO()}

    override fun remove(key: K): V? {TODO()}

    private fun rotateLeft(curVertex: AVLVertex<K,V>) : AVLVertex<K,V> {
        val rightSon : AVLVertex<K,V>  = (curVertex.rightSon as AVLVertex<K,V>)
        curVertex.rightSon = rightSon.leftSon
        rightSon.leftSon = curVertex
        when (rightSon.sonsHeightDiff) {
            0 -> rightSon.sonsHeightDiff = 1
            -1 -> {curVertex.sonsHeightDiff = 0; rightSon.sonsHeightDiff = 0}
        }
        return rightSon
    }

    private fun rotateRight(curVertex: AVLVertex<K,V>) : AVLVertex<K,V> {
        val leftSon : AVLVertex<K,V>  = (curVertex.leftSon as AVLVertex<K,V>)
        curVertex.leftSon = leftSon.rightSon
        leftSon.rightSon = curVertex
        when (leftSon.sonsHeightDiff) {
            0 -> leftSon.sonsHeightDiff = 1
            -1 -> {curVertex.sonsHeightDiff = 0; leftSon.sonsHeightDiff = 0}
        }
        return leftSon
    } 

    constructor (comparator: Comparator<K>? = null) : super(comparator)

    constructor(map: Map<K, V>, comparator: Comparator<K>? = null) : super(map, comparator)
}
