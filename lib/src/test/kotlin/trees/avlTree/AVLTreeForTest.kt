package trees.avlTree

import main.trees.AVLSearchTree
import main.vertexes.AVLVertex
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.math.max

class AVLTreeForTest<K, V> : AVLSearchTree<K, V> {
    fun getRootT(): AVLVertex<K, V>? {
        return root
    }

    fun getVertexesInDFSOrder(): MutableList<AVLVertex<K, V>> {
        val vertexes: MutableList<AVLVertex<K, V>> = mutableListOf()
        getVertexesRec(root, vertexes)
        return vertexes
    }

    private fun getVertexesRec(
        curVertex: AVLVertex<K, V>?,
        vrtList: MutableList<AVLVertex<K, V>>,
    ) {
        if (curVertex == null) return
        vrtList.add(curVertex)
        getVertexesRec(curVertex.leftSon, vrtList)
        getVertexesRec(curVertex.rightSon, vrtList)
    }

    fun getHeights(): MutableMap<K, Int> {
        val heights: MutableMap<K, Int> = mutableMapOf()
        if (root == null) return heights
        getHeightsRec(root as AVLVertex<K, V>, heights)
        return heights
    }

    private fun getHeightsRec(
        rootOfSubtree: AVLVertex<K, V>,
        heights: MutableMap<K, Int>,
    ): Int {
        return when ((rootOfSubtree.leftSon == null) to (rootOfSubtree.rightSon == null)) {
            true to true -> {
                heights.put(rootOfSubtree.key, 0)
                0
            }

            true to false -> {
                val height = getHeightsRec(rootOfSubtree.rightSon as AVLVertex<K, V>, heights) + 1
                heights.put(rootOfSubtree.key, height)
                height
            }

            false to true -> {
                val height = getHeightsRec(rootOfSubtree.leftSon as AVLVertex<K, V>, heights) + 1
                heights.put(rootOfSubtree.key, height)
                height
            }

            else -> {
                val heightOfLeftSubtree = getHeightsRec(rootOfSubtree.leftSon as AVLVertex<K, V>, heights)
                val heightOfRightSubtree = getHeightsRec(rootOfSubtree.rightSon as AVLVertex<K, V>, heights)
                val height = max(heightOfLeftSubtree, heightOfRightSubtree) + 1
                heights.put(rootOfSubtree.key, height)
                max(heightOfLeftSubtree, heightOfRightSubtree) + 1
            }
        }
    }

    constructor (root: AVLVertex<K, V>, size: Long, comparator: Comparator<K>? = null) : super(comparator) {
        this.root = root
        this.size = size
    }

    constructor (comparator: Comparator<K>? = null) : super(comparator)

    constructor(
        map: Map<K, V>,
        replaceIfExists: Boolean = true,
        comparator: Comparator<K>? = null,
    ) : super(map, replaceIfExists, comparator)
}
