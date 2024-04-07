package trees.avlTree

import main.vertexes.AVLVertex

object AuxiliaryFunctions {
    fun <K, V> isTreeConsistsOf(
            expectedContent: Set<Pair<K, V>>,
            tree: AVLTreeForTest<K, V>,
    ): Boolean {
        val vertexes = tree.getVertexesInDFSOrder()
        val pairsFromVertexes = (Array(vertexes.size) { i -> (vertexes[i].key to vertexes[i].value) }).toSet()
        return pairsFromVertexes == expectedContent
    }

    fun <K, V> isTreeSStructureThat(
            tree: AVLTreeForTest<K, V>,
            order: Array<K>,
            deps: List<Triple<Int, Int?, Int?>>,
    ): Boolean {
        // Triple consists of indexes in order of (1)vertex, one's (2)leftSon and (3)RightSon
        val vertexes = tree.getVertexesInDFSOrder()
        if (vertexes.size != order.size) return false
        for (i in order.indices)
            if (order[i] != vertexes[i].key) return false
        for (dep in deps) {
            if (dep.component2() != null) {
                if (vertexes[dep.component1()].leftSon != vertexes[dep.component2() as Int]) {
                    return false
                }
            }
            if (dep.component3() != null) {
                if (vertexes[dep.component1()].rightSon != vertexes[dep.component3() as Int]) {
                    return false
                }
            }
        }
        return true
    }

    fun <K, V> isSonsHeightDiffCorrect(tree: AVLTreeForTest<K, V>): Boolean {
        val vertexes = tree.getVertexesInDFSOrder()
        val heights = tree.getHeights()
        if (vertexes.size != heights.size) return false
        for (vertex in vertexes) {
            val expectedSonsHeightDiff =
                    when ((vertex.leftSon == null) to (vertex.rightSon == null)) {
                        true to true -> 0
                        true to false -> -1 - (heights[(vertex.rightSon as AVLVertex<K, V>).key] ?: return false)
                        false to true -> 1 + (heights[(vertex.leftSon as AVLVertex<K, V>).key] ?: return false)
                        else -> {
                            val heightOfLeftSubtree = heights[(vertex.leftSon as AVLVertex<K, V>).key] ?: return false
                            val heightOfRightSubtree = heights[(vertex.rightSon as AVLVertex<K, V>).key] ?: return false
                            heightOfLeftSubtree - heightOfRightSubtree
                        }
                    }
            if (expectedSonsHeightDiff != vertex.sonsHeightDiff) return false
        }
        return true
    }
}
