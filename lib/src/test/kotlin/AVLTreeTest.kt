import kotlin.collections.hashMapOf
import main.vertexes.AVLVertex
import org.junit.jupiter.api.Test

class AVLTreeTest {

    private fun makeEmptyTree(): AVLTreeForTest<Int, Char> {
        return AVLTreeForTest<Int, Char>()
    }

    // putTest

    @Test
    fun `entry became root after it was added to empty tree`() {
        val tree = makeEmptyTree()
        tree.put(0, '0')
        val root = tree.getRootT()
        assert((root?.key to root?.value) == (0 to '0'))
    }

    @Test
    fun `size equals 1 after entry was added to empty tree`() {
        val tree = makeEmptyTree()
        tree.put(0, '0')
        assert(tree.size() == 1L)
    }

    private fun makeSize1Tree(): AVLTreeForTest<Char, Char> {
        return AVLTreeForTest(AVLVertex('b', '+'), 1L) // init tree by root and size (don't use put)
    }

    @Test
    fun `entry with larger key became right son after it was added to size 1 tree`() {
        val tree = makeSize1Tree()
        tree.put('c', '+')
        val root = tree.getRootT()
        assert((root?.rightSon?.key to root?.rightSon?.value) == ('c' to '+'))
    }

    @Test
    fun `entry with lesser key became left son after it was added to size 1 tree`() {
        val tree = makeSize1Tree()
        tree.put('a', '-')
        val root = tree.getRootT()
        assert((root?.leftSon?.key to root?.leftSon?.value) == ('a' to '-'))
    }

    @Test
    fun `root's sonsHeightDiff decreased by 1 after entry with larger key was added to size 1 tree`() {
        val tree = makeSize1Tree()
        tree.put('c', '+')
        val root = tree.getRootT()
        assert(root?.sonsHeightDiff == -1)
    }

    @Test
    fun `root's sonsHeightDiff increased by 1 after entry with lesser key was added to size 1 tree`() {
        val tree = makeSize1Tree()
        tree.put('a', '-')
        val root = tree.getRootT()
        assert(root?.sonsHeightDiff == 1)
    }

    @Test
    fun `size equals 2 after entry with larger key was added to size 1 tree`() {
        val tree = makeSize1Tree()
        tree.put('c', '+')
        assert(tree.size() == 2L)
    }

    @Test
    fun `size equals 2 after entry with lesser key was added to size 1 tree`() {
        val tree = makeSize1Tree()
        tree.put('a', '-')
        assert(tree.size() == 2L)
    }

    private fun makeTreeForNeedNotBalanceingPutTest(): AVLTreeForTest<Int, Char> {
        return AVLTreeForTest(AVLVertex(4, 'A', AVLVertex(1, 'I'), AVLVertex(5, 'S')), 3L)
    }

    private fun <K, V> isTreeConsistsOf(expectedContent: Set<Pair<K, V>>, tree: AVLTreeForTest<K, V>): Boolean {
        val vertexes = tree.getVertexesInDFSOrder()
        val pairsFromVertexes = (Array(vertexes.size) { i -> (vertexes[i].key to vertexes[i].value) }).toSet()
        return pairsFromVertexes == expectedContent
    }

    @Test
    fun `content is correct after entry was added (needn't balancing)`() {
        val tree = makeTreeForNeedNotBalanceingPutTest()
        tree.put(0, 'O')
        assert(isTreeConsistsOf(setOf(0 to 'O', 1 to 'I', 5 to 'S', 4 to 'A'), tree))
    }

    @Test
    fun `size increased by 1 after added to 'size 2+' tree (needn't balancing)`() {
        val tree = makeTreeForNeedNotBalanceingPutTest()
        tree.put(0, 'O')
        assert(tree.size() == 4L)
    }

    private fun <K, V> isTreeSStructureThat(
        tree: AVLTreeForTest<K, V>,
        order: Array<K>,
        deps: List<Triple<Int, Int?, Int?>>
    ): Boolean {
        // Tiple consists of indexes in order of (1)vertex, one's (2)leftSon and (3)RightSon
        val vertexes = tree.getVertexesInDFSOrder()
        if (vertexes.size != order.size) return false
        for (i in order.indices)
            if (order[i] != vertexes[i].key) return false
        for (dep in deps) {
            if (dep.component2() != null)
                if (vertexes[dep.component1()].leftSon != vertexes[dep.component2() as Int])
                    return false
            if (dep.component3() != null)
                if (vertexes[dep.component1()].rightSon != vertexes[dep.component3() as Int])
                    return false
        }
        return true
    }

    @Test
    fun `structure is correct after added to 'size 2+' tree (needn't balancing)`() {
        val tree = makeTreeForNeedNotBalanceingPutTest()
        tree.put(0, 'O')
        val expectedDependences = listOf(Triple(0, 1, 3), Triple(1, 2, null))
        assert(isTreeSStructureThat(tree, arrayOf(4, 1, 0, 5), expectedDependences))
    }

    private fun <K, V> isSonsHeightDiffCorrect(tree: AVLTreeForTest<K, V>): Boolean {
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

    @Test
    fun `vertexes' sonsHeightDiff are correct after entry was added to 'size 2+' tree (needn't balancing)`() {
        val tree = makeTreeForNeedNotBalanceingPutTest()
        tree.put(0, 'O')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForHaveToRotateLeftPutTest(): AVLTreeForTest<Char, Char> {
        val rightSonSRightSon = AVLVertex('h', 'H', AVLVertex('f', 'F'), AVLVertex('i', 'I'))
        val rightSon = AVLVertex('e', 'E', AVLVertex('d', 'D'), rightSonSRightSon, -1)
        val root = AVLVertex('c', 'C', AVLVertex('b', 'B', AVLVertex('a', 'A'), null, 1), rightSon, -1)
        return AVLTreeForTest(root, 8L)
    }

    @Test
    fun `content is correct after entry was added (have to rotate left)`() {
        val tree = makeTreeForHaveToRotateLeftPutTest()
        tree.put('j', 'J')
        val expectedContent = setOf(
            'a' to 'A', 'b' to 'B', 'c' to 'C',
            'd' to 'D', 'e' to 'E', 'h' to 'H', 'f' to 'F', 'i' to 'I', 'j' to 'J'
        )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size increased by 1 after added (have to rotate left)`() {
        val tree = makeTreeForHaveToRotateLeftPutTest()
        tree.put('j', 'J')
        assert(tree.size() == 9L)
    }

    @Test
    fun `structure is correct after added (have to rotate left)`() {
        val tree = makeTreeForHaveToRotateLeftPutTest()
        tree.put('j', 'J')
        val expectedDependences = listOf(
            Triple(0, 1, 3), Triple(1, 2, null), Triple(3, 4, 7),
            Triple(4, 5, 6), Triple(7, null, 8)
        )
        val expectedOrder = arrayOf('c', 'b', 'a', 'h', 'e', 'd', 'f', 'i', 'j')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependences))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after added (have to rotate left)`() {
        val tree = makeTreeForHaveToRotateLeftPutTest()
        tree.put('j', 'J')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForHaveToRotateRightPutTest(): AVLTreeForTest<Char, Char> {
        val leftSonSLeftSon = AVLVertex('c', 'C', AVLVertex('b', 'B'), AVLVertex('d', 'D'))
        val leftSon = AVLVertex('f', 'F', leftSonSLeftSon, AVLVertex('e', 'E'), 1)
        val root = AVLVertex('h', 'H', leftSon, AVLVertex('i', 'I', null, AVLVertex('j', 'J'), -1), 1)
        return AVLTreeForTest(root, 8L)
    }

    @Test
    fun `content is correct after entry was added (have to rotate right)`() {
        val tree = makeTreeForHaveToRotateRightPutTest()
        tree.put('a', 'A')
        val expectedContent = setOf(
            'a' to 'A', 'b' to 'B', 'c' to 'C',
            'd' to 'D', 'e' to 'E', 'h' to 'H', 'f' to 'F', 'i' to 'I', 'j' to 'J'
        )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size increased by 1 after added (have to rotate right)`() {
        val tree = makeTreeForHaveToRotateRightPutTest()
        tree.put('a', 'A')
        assert(tree.size() == 9L)
    }

    @Test
    fun `structure is correct after added (have to rotate right)`() {
        val tree = makeTreeForHaveToRotateRightPutTest()
        tree.put('a', 'A')
        val expectedDependences = listOf(
            Triple(0, 1, 7), Triple(1, 2, 4), Triple(2, 3, null),
            Triple(4, 5, 6), Triple(7, null, 8)
        )
        val expectedOrder = arrayOf('h', 'c', 'b', 'a', 'f', 'd', 'e', 'i', 'j')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependences))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after added (have to rotate right)`() {
        val tree = makeTreeForHaveToRotateRightPutTest()
        tree.put('a', 'A')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForHaveToChangeRootByRotateLeftPutTest(): AVLTreeForTest<Char, Char> {
        val rightSon = AVLVertex('h', 'H', AVLVertex('f', 'F'), AVLVertex('i', 'I'))
        val root = AVLVertex('e', 'E', AVLVertex('d', 'D'), rightSon, -1)
        return AVLTreeForTest(root, 5L)
    }

    @Test
    fun `content is correct after entry was added (rotateLeft changes root)`() {
        val tree = makeTreeForHaveToChangeRootByRotateLeftPutTest()
        tree.put('j', 'J')
        val expectedContent = setOf('d' to 'D', 'e' to 'E', 'h' to 'H', 'f' to 'F', 'i' to 'I', 'j' to 'J')
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size increased by 1 after added (rotateLeft changes root)`() {
        val tree = makeTreeForHaveToChangeRootByRotateLeftPutTest()
        tree.put('j', 'J')
        assert(tree.size() == 6L)
    }

    @Test
    fun `structure is correct after added (rotateLeft changes root)`() {
        val tree = makeTreeForHaveToChangeRootByRotateLeftPutTest()
        tree.put('j', 'J')
        val expectedDependences = listOf(Triple(0, 1, 4), Triple(1, 2, 3), Triple(4, null, 5))
        val expectedOrder = arrayOf('h', 'e', 'd', 'f', 'i', 'j')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependences))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after added (rotateLeft changes root)`() {
        val tree = makeTreeForHaveToChangeRootByRotateLeftPutTest()
        tree.put('j', 'J')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForHaveToChangeRootByRotateRightPutTest(): AVLTreeForTest<Char, Char> {
        val leftSon = AVLVertex('c', 'C', AVLVertex('b', 'B'), AVLVertex('d', 'D'))
        val root = AVLVertex('f', 'F', leftSon, AVLVertex('e', 'E'), 1)
        return AVLTreeForTest(root, 5L)
    }

    @Test
    fun `content is correct after entry was added (rotateRight changes root)`() {
        val tree = makeTreeForHaveToChangeRootByRotateRightPutTest()
        tree.put('a', 'A')
        val expectedContent = setOf('a' to 'A', 'b' to 'B', 'c' to 'C', 'd' to 'D', 'e' to 'E', 'f' to 'F')
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size increased by 1 after added (rotateRight changes root)`() {
        val tree = makeTreeForHaveToChangeRootByRotateRightPutTest()
        tree.put('a', 'A')
        assert(tree.size() == 6L)
    }

    @Test
    fun `structure is correct after added (rotateRight changes root)`() {
        val tree = makeTreeForHaveToChangeRootByRotateRightPutTest()
        tree.put('a', 'A')
        val expectedDependences = listOf(Triple(0, 1, 3), Triple(1, 2, null), Triple(3, 4, 5))
        val expectedOrder = arrayOf('c', 'b', 'a', 'f', 'd', 'e')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependences))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after added (rotateRight changes root)`() {
        val tree = makeTreeForHaveToChangeRootByRotateRightPutTest()
        tree.put('a', 'A')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForHaveToBigRotateLeftPutTest(): AVLTreeForTest<Char, Char> {
        val rightSonSRightSon = AVLVertex('i', 'I', AVLVertex('g', 'G'), AVLVertex('j', 'J'))
        val rightSon = AVLVertex('e', 'E', AVLVertex('d', 'D'), rightSonSRightSon, -1)
        val root = AVLVertex('c', 'C', AVLVertex('b', 'B', AVLVertex('a', 'A'), null, 1), rightSon, -1)
        return AVLTreeForTest(root, 8L)
    }

    // (1) - add grandson's right son, (2) add grandson's left son
    @Test
    fun `content is correct after entry was added (have to big rotate left)(1)`() {
        val tree = makeTreeForHaveToBigRotateLeftPutTest()
        tree.put('f', 'F')
        val expectedContent = setOf(
            'a' to 'A', 'b' to 'B', 'c' to 'C',
            'd' to 'D', 'e' to 'E', 'g' to 'G', 'f' to 'F', 'i' to 'I', 'j' to 'J'
        )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `content is correct after entry was added (have to big rotate left)(2)`() {
        val tree = makeTreeForHaveToBigRotateLeftPutTest()
        tree.put('h', 'H')
        val expectedContent = setOf(
            'a' to 'A', 'b' to 'B', 'c' to 'C',
            'd' to 'D', 'e' to 'E', 'h' to 'H', 'g' to 'G', 'i' to 'I', 'j' to 'J'
        )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size increased by 1 after added (have to big rotate left)(1)`() {
        val tree = makeTreeForHaveToBigRotateLeftPutTest()
        tree.put('f', 'F')
        assert(tree.size() == 9L)
    }

    @Test
    fun `size increased by 1 after added (have to big rotate left)(2)`() {
        val tree = makeTreeForHaveToBigRotateLeftPutTest()
        tree.put('h', 'H')
        assert(tree.size() == 9L)
    }

    @Test
    fun `structure is correct after added (have to big rotate left)(1)`() {
        val tree = makeTreeForHaveToBigRotateLeftPutTest()
        tree.put('f', 'F')
        val expectedDependences = listOf(
            Triple(0, 1, 3), Triple(1, 2, null), Triple(3, 4, 7),
            Triple(4, 5, 6), Triple(7, null, 8)
        )
        val expectedOrder = arrayOf('c', 'b', 'a', 'g', 'e', 'd', 'f', 'i', 'j')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependences))
    }

    @Test
    fun `structure is correct after added (have to big rotate left)(2)`() {
        val tree = makeTreeForHaveToBigRotateLeftPutTest()
        tree.put('h', 'H')
        val expectedDependences = listOf(
            Triple(0, 1, 3), Triple(1, 2, null), Triple(3, 4, 6),
            Triple(4, 5, null), Triple(6, 7, 8)
        )
        val expectedOrder = arrayOf('c', 'b', 'a', 'g', 'e', 'd', 'i', 'h', 'j')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependences))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after added (have to big rotate left)(1)`() {
        val tree = makeTreeForHaveToBigRotateLeftPutTest()
        tree.put('f', 'F')
        assert(isSonsHeightDiffCorrect(tree))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after added (have to big rotate left)(2)`() {
        val tree = makeTreeForHaveToBigRotateLeftPutTest()
        tree.put('h', 'H')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForHaveToBigRotateRightPutTest(): AVLTreeForTest<Char, Char> {
        val leftSonSLeftSon = AVLVertex('b', 'B', AVLVertex('a', 'A'), AVLVertex('d', 'D'))
        val leftSon = AVLVertex('f', 'F', leftSonSLeftSon, AVLVertex('g', 'G'), 1)
        val root = AVLVertex('h', 'H', leftSon, AVLVertex('i', 'I', null, AVLVertex('j', 'J'), -1), 1)
        return AVLTreeForTest(root, 8L)
    }

    // (1) - add grandson's left son, (2) add grandson's right son
    @Test
    fun `content is correct after entry was added (have to big rotate right)(1)`() {
        val tree = makeTreeForHaveToBigRotateRightPutTest()
        tree.put('c', 'C')
        val expectedContent = setOf(
            'a' to 'A', 'b' to 'B', 'c' to 'C',
            'd' to 'D', 'h' to 'H', 'g' to 'G', 'f' to 'F', 'i' to 'I', 'j' to 'J'
        )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `content is correct after entry was added (have to big rotate right)(2)`() {
        val tree = makeTreeForHaveToBigRotateRightPutTest()
        tree.put('e', 'E')
        val expectedContent = setOf(
            'a' to 'A', 'b' to 'B', 'f' to 'F',
            'd' to 'D', 'e' to 'E', 'h' to 'H', 'g' to 'G', 'i' to 'I', 'j' to 'J'
        )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size increased by 1 after added (have to big rotate right)(1)`() {
        val tree = makeTreeForHaveToBigRotateRightPutTest()
        tree.put('c', 'C')
        assert(tree.size() == 9L)
    }

    @Test
    fun `size increased by 1 after added (have to big rotate right)(2)`() {
        val tree = makeTreeForHaveToBigRotateRightPutTest()
        tree.put('e', 'E')
        assert(tree.size() == 9L)
    }

    @Test
    fun `structure is correct after added (have to big rotate right)(1)`() {
        val tree = makeTreeForHaveToBigRotateRightPutTest()
        tree.put('c', 'C')
        val expectedDependences = listOf(
            Triple(0, 1, 7), Triple(7, null, 8), Triple(1, 2, 5),
            Triple(5, null, 6), Triple(2, 3, 4)
        )
        val expectedOrder = arrayOf('h', 'd', 'b', 'a', 'c', 'f', 'g', 'i', 'j')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependences))
    }

    @Test
    fun `structure is correct after added (have to big rotate right)(2)`() {
        val tree = makeTreeForHaveToBigRotateRightPutTest()
        tree.put('e', 'E')
        val expectedDependences = listOf(
            Triple(0, 1, 7), Triple(7, null, 8), Triple(1, 2, 4),
            Triple(2, 3, null), Triple(4, 5, 6)
        )
        val expectedOrder = arrayOf('h', 'd', 'b', 'a', 'f', 'e', 'g', 'i', 'j')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependences))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after added (have to big rotate right)(1)`() {
        val tree = makeTreeForHaveToBigRotateRightPutTest()
        tree.put('c', 'C')
        assert(isSonsHeightDiffCorrect(tree))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after added (have to big rotate right)(2)`() {
        val tree = makeTreeForHaveToBigRotateLeftPutTest()
        tree.put('e', 'E')
        assert(isSonsHeightDiffCorrect(tree))
    }

    @Test
    fun `tree has no changes after entry with existed key and param replaceIfExists = false was added`() {
        val tree = makeSize1Tree()
        tree.put('b', '-', false)
        assert(tree.size() == 1L)
        assert(tree.getRootT()?.value == '+')
    }

    @Test
    fun `content is correct after init by map`() {
        val tree = AVLTreeForTest(hashMapOf('a' to 'A', 'b' to 'B'))
        val expectedContent = setOf('a' to 'A', 'b' to 'B')
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    // remove test

    @Test
    fun `delete from emptyTree returns null`() {
        val tree = makeEmptyTree()
        assert(tree.remove(0) == null)
    }

    @Test
    fun `tree is empty after deleted root of 'size 1' tree `() {
        val tree = makeSize1Tree()
        tree.remove('b')
        assert(tree.getVertexesInDFSOrder().isEmpty())
    }

    @Test
    fun `size equals 0 after deleted root of 'size 1' tree`() {
        val tree = makeSize1Tree()
        tree.remove('b')
        assert(tree.size() == 0L)
    }

    @Test
    fun `remove fun return null if entry's key isn't exists`() {
        val tree = makeSize1Tree()
        assert(tree.remove('a') == null)
    }

    @Test
    fun `tree has no changes after tried to delete by non existed larger key`() {
        val tree = makeSize1Tree()
        tree.remove('c')
        assert(tree.size() == 1L)
        assert(tree.getRootT()?.value == '+')
    }

    @Test
    fun `tree has no changes after tried to delete by non existed lesser key`() {
        val tree = makeSize1Tree()
        tree.remove('a')
        assert(tree.size() == 1L)
        assert(tree.getRootT()?.value == '+')
    }

    private fun makeTreeForRemoveLeafWithoutBalanceingTest(): AVLTreeForTest<Char, String> {
        return AVLTreeForTest(AVLVertex('r', "r", AVLVertex('n', "n"), AVLVertex('z', "z")), 3L)
    }

    @Test
    fun `right leaf deleted after remove with one's key was called (needn't balanceing)`() {
        val tree = makeTreeForRemoveLeafWithoutBalanceingTest()
        tree.remove('z')
        val root = tree.getRootT()
        if (root != null) assert(root.rightSon == null)
        else assert(false)
    }

    @Test
    fun `left leaf deleted after remove with one's key was called (needn't balanceing)`() {
        val tree = makeTreeForRemoveLeafWithoutBalanceingTest()
        tree.remove('n')
        val root = tree.getRootT()
        if (root != null) assert(root.leftSon == null)
        else assert(false)
    }

    @Test
    fun `remove by right leaf's key return due value (needn't balanceing)`() {
        val tree = makeTreeForRemoveLeafWithoutBalanceingTest()
        assert(tree.remove('z') == "z")
    }

    @Test
    fun `remove by left leaf's key return due value (needn't balanceing)`() {
        val tree = makeTreeForRemoveLeafWithoutBalanceingTest()
        assert(tree.remove('n') == "n")
    }

    @Test
    fun `vertex's sonsHeightDiff increased by 1 after deleted one's right leaf (needn't balanceing)`() {
        val tree = makeTreeForRemoveLeafWithoutBalanceingTest()
        tree.remove('z')
        val root = tree.getRootT()
        assert(root?.sonsHeightDiff == 1)
    }

    @Test
    fun `vertex's sonsHeightDiff decreased by 1 after deleted one's left son (needn't balanceing)`() {
        val tree = makeTreeForRemoveLeafWithoutBalanceingTest()
        tree.remove('n')
        val root = tree.getRootT()
        assert(root?.sonsHeightDiff == -1)
    }

    @Test
    fun `size decreased by 1 after deleted right leaf (needn't balanceing)`() {
        val tree = makeTreeForRemoveLeafWithoutBalanceingTest()
        tree.remove('z')
        assert(tree.size() == 2L)
    }

    @Test
    fun `size decreased by 1 after deleted left leaf (needn't balanceing)`() {
        val tree = makeTreeForRemoveLeafWithoutBalanceingTest()
        tree.remove('n')
        assert(tree.size() == 2L)
    }

    private fun makeTreeForRemoveLeftSonWithOnlyLeftSonTest(): AVLTreeForTest<Char, Int> {
        val leftSon = AVLVertex('q', 9, AVLVertex('o', 0), null, 1)
        val root = AVLVertex('s', 5, leftSon, AVLVertex('z', 2), 1)
        return AVLTreeForTest(root, 4L)
    }

    @Test
    fun `remove left son with only left son returns due value (needn't balanceing)`() {
        val tree = makeTreeForRemoveLeftSonWithOnlyRightSonTest()
        assert(tree.remove('q') == 9)
    }

    @Test
    fun `content is correct after removed left son with only left son (needn't balanceing)`() {
        val tree = makeTreeForRemoveLeftSonWithOnlyLeftSonTest()
        tree.remove('q')
        assert(isTreeConsistsOf(setOf('o' to 0, 's' to 5, 'z' to 2), tree))
    }

    @Test
    fun `size decreased by 1 after removed left son with only left son (needn't balanceing)`() {
        val tree = makeTreeForRemoveLeftSonWithOnlyLeftSonTest()
        tree.remove('q')
        assert(tree.size() == 3L)
    }

    @Test
    fun `structure is correct after removed left son with only leftt son (needn't balanceing)`() {
        val tree = makeTreeForRemoveLeftSonWithOnlyLeftSonTest()
        tree.remove('q')
        assert(isTreeSStructureThat(tree, arrayOf('s', 'o', 'z'), listOf(Triple(0, 1, 2))))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after removed LSon with only LSon (needn't balanceing)`() {
        val tree = makeTreeForRemoveLeftSonWithOnlyLeftSonTest()
        tree.remove('q')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForRemoveLeftSonWithOnlyRightSonTest(): AVLTreeForTest<Char, Int> {
        val leftSon = AVLVertex('q', 9, null, AVLVertex('r', 7), -1)
        val root = AVLVertex('s', 5, leftSon, AVLVertex('z', 2), 1)
        return AVLTreeForTest(root, 4L)
    }

    @Test
    fun `remove left son with only right son returns due value (needn't balanceing)`() {
        val tree = makeTreeForRemoveLeftSonWithOnlyRightSonTest()
        assert(tree.remove('q') == 9)
    }

    @Test
    fun `content is correct after removed left son with only right son (needn't balanceing)`() {
        val tree = makeTreeForRemoveLeftSonWithOnlyRightSonTest()
        tree.remove('q')
        assert(isTreeConsistsOf(setOf('r' to 7, 's' to 5, 'z' to 2), tree))
    }

    @Test
    fun `size decreased by 1 after removed left son with only right son (needn't balanceing)`() {
        val tree = makeTreeForRemoveLeftSonWithOnlyRightSonTest()
        tree.remove('q')
        assert(tree.size() == 3L)
    }

    @Test
    fun `structure is correct after removed left son with only right son (needn't balanceing)`() {
        val tree = makeTreeForRemoveLeftSonWithOnlyRightSonTest()
        tree.remove('q')
        assert(isTreeSStructureThat(tree, arrayOf('s', 'r', 'z'), listOf(Triple(0, 1, 2))))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after removed LSon with only RSon (needn't balanceing)`() {
        val tree = makeTreeForRemoveLeftSonWithOnlyRightSonTest()
        tree.remove('q')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForRemoveRightSonWithOnlyLeftSonTest(): AVLTreeForTest<Char, Int> {
        val rightSon = AVLVertex('q', 9, AVLVertex('o', 0), null, 1)
        val root = AVLVertex('i', 1, AVLVertex('b', 6), rightSon, -1)
        return AVLTreeForTest(root, 4L)
    }

    @Test
    fun `remove right son with only left son returns due value (needn't balanceing)`() {
        val tree = makeTreeForRemoveRightSonWithOnlyLeftSonTest()
        assert(tree.remove('q') == 9)
    }

    @Test
    fun `content is correct after removed right son with only left son (needn't balanceing)`() {
        val tree = makeTreeForRemoveRightSonWithOnlyLeftSonTest()
        tree.remove('q')
        assert(isTreeConsistsOf(setOf('o' to 0, 'b' to 6, 'i' to 1), tree))
    }

    @Test
    fun `size decreased by 1 after removed right son with only left son (needn't balanceing)`() {
        val tree = makeTreeForRemoveRightSonWithOnlyLeftSonTest()
        tree.remove('q')
        assert(tree.size() == 3L)
    }

    @Test
    fun `structure is correct after removed right son with only left son (needn't balanceing)`() {
        val tree = makeTreeForRemoveRightSonWithOnlyLeftSonTest()
        tree.remove('q')
        assert(isTreeSStructureThat(tree, arrayOf('i', 'b', 'o'), listOf(Triple(0, 1, 2))))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after removed RSon with only LSon (needn't balanceing)`() {
        val tree = makeTreeForRemoveRightSonWithOnlyLeftSonTest()
        tree.remove('q')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForRemoveRightSonWithOnlyRightSonTest(): AVLTreeForTest<Char, Int> {
        val rightSon = AVLVertex('q', 9, null, AVLVertex('r', 7), -1)
        val root = AVLVertex('i', 1, AVLVertex('b', 6), rightSon, -1)
        return AVLTreeForTest(root, 4L)
    }

    @Test
    fun `remove right son with only right son returns due value (needn't balanceing)`() {
        val tree = makeTreeForRemoveRightSonWithOnlyRightSonTest()
        assert(tree.remove('q') == 9)
    }

    @Test
    fun `content is correct after removed right son with only right son (needn't balanceing)`() {
        val tree = makeTreeForRemoveRightSonWithOnlyRightSonTest()
        tree.remove('q')
        assert(isTreeConsistsOf(setOf('r' to 7, 'b' to 6, 'i' to 1), tree))
    }

    @Test
    fun `size decreased by 1 after removed right son with only right son (needn't balanceing)`() {
        val tree = makeTreeForRemoveRightSonWithOnlyRightSonTest()
        tree.remove('q')
        assert(tree.size() == 3L)
    }

    @Test
    fun `structure is correct after removed right son with only right son (needn't balanceing)`() {
        val tree = makeTreeForRemoveRightSonWithOnlyRightSonTest()
        tree.remove('q')
        assert(isTreeSStructureThat(tree, arrayOf('i', 'b', 'r'), listOf(Triple(0, 1, 2))))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after removed RSon with only RSon (needn't balanceing)`() {
        val tree = makeTreeForRemoveRightSonWithOnlyRightSonTest()
        tree.remove('q')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForRemoveRootWithOnlyLeftSon(): AVLTreeForTest<String, String> {
        return AVLTreeForTest(AVLVertex("be", "es", (AVLVertex("and", "et")), null, 1), 2L)
    }

    @Test
    fun `remove root with only left son returns due value (needn't balanceing)`() {
        val tree = makeTreeForRemoveRootWithOnlyLeftSon()
        assert(tree.remove("be") == "es")
    }

    @Test
    fun `content is correct after removed root with only left son (needn't balanceing)`() {
        val tree = makeTreeForRemoveRootWithOnlyLeftSon()
        tree.remove("be")
        assert(isTreeConsistsOf(setOf("and" to "et"), tree))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after removed root with only LSon (needn't balanceing)`() {
        val tree = makeTreeForRemoveRootWithOnlyLeftSon()
        tree.remove("and")
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForRemoveRootWithOnlyRightSon(): AVLTreeForTest<String, String> {
        return AVLTreeForTest(AVLVertex("and", "et", null, (AVLVertex("be", "es")), -1), 2L)
    }

    @Test
    fun `remove root with only right son returns due value (needn't balanceing)`() {
        val tree = makeTreeForRemoveRootWithOnlyRightSon()
        assert(tree.remove("and") == "et")
    }

    @Test
    fun `content is correct after removed root with only right son (needn't balanceing)`() {
        val tree = makeTreeForRemoveRootWithOnlyRightSon()
        tree.remove("and")
        assert(isTreeConsistsOf(setOf("be" to "es"), tree))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after removed root with only RSon (needn't balanceing)`() {
        val tree = makeTreeForRemoveRootWithOnlyRightSon()
        tree.remove("and")
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForRemoveSonWithBothSons(): AVLTreeForTest<Char, Char> {
        val eVrt = AVLVertex('e', 'E', AVLVertex('c', 'C'), null, 1)
        val bVrt = AVLVertex('b', 'B', AVLVertex('a', 'A'), eVrt, -1)
        val leftSon = AVLVertex('f', 'F', bVrt, AVLVertex('i', 'I', null, AVLVertex('k', 'K'), -1), 1)
        val qVrt = AVLVertex('q', 'Q', null, AVLVertex('s', 'S'), -1)
        val wVrt = AVLVertex('w', 'W', null, AVLVertex('z', 'Z'), -1)
        val root = AVLVertex('n', 'N', leftSon, AVLVertex('u', 'U', qVrt, wVrt, 0), 1)
        return AVLTreeForTest(root, 13L)
    }

    @Test
    fun `remove left son with both sons returns due value (needn't balanceing)`() {
        val tree = makeTreeForRemoveSonWithBothSons()
        assert(tree.remove('f') == 'F')
    }

    @Test
    fun `content is correct after removed left son with both sons (needn't balanceing)`() {
        val tree = makeTreeForRemoveSonWithBothSons()
        tree.remove('f')
        val expectedContent = setOf(
            'a' to 'A', 'b' to 'B', 'c' to 'C', 'e' to 'E', 'i' to 'I',
            'z' to 'Z', 'k' to 'K', 'n' to 'N', 'u' to 'U', 'q' to 'Q', 's' to 'S', 'w' to 'W'
        )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size decreased by 1 after removed left son with both sons (needn't balanceing)`() {
        val tree = makeTreeForRemoveSonWithBothSons()
        tree.remove('f')
        assert(tree.size() == 12L)
    }

    @Test
    fun `structure is correct after removed left son with both sons (needn't balanceing)`() {
        val tree = makeTreeForRemoveSonWithBothSons()
        tree.remove('f')
        val expectedDependences = listOf(
            Triple(0, 1, 7), Triple(1, 2, 5), Triple(2, 3, 4),
            Triple(5, null, 6), Triple(7, 8, 10), Triple(10, null, 11)
        )
        val expectedOrder = arrayOf('n', 'e', 'b', 'a', 'c', 'i', 'k', 'u', 'q', 's', 'w', 'z')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependences))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after removed LSon with both sons (needn't balanceing)`() {
        val tree = makeTreeForRemoveSonWithBothSons()
        tree.remove('f')
        assert(isSonsHeightDiffCorrect(tree))
    }

    @Test
    fun `remove right son with both sons returns due value (needn't balanceing)`() {
        val tree = makeTreeForRemoveSonWithBothSons()
        assert(tree.remove('u') == 'U')
    }

    @Test
    fun `content is correct after removed right son with both sons (needn't balanceing)`() {
        val tree = makeTreeForRemoveSonWithBothSons()
        tree.remove('u')
        val expectedContent = setOf(
            'a' to 'A', 'b' to 'B', 'c' to 'C', 'e' to 'E', 'i' to 'I',
            'z' to 'Z', 'k' to 'K', 'n' to 'N', 'f' to 'F', 'q' to 'Q', 's' to 'S', 'w' to 'W'
        )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size decreased by 1 after removed right son with both sons (needn't balanceing)`() {
        val tree = makeTreeForRemoveSonWithBothSons()
        tree.remove('u')
        assert(tree.size() == 12L)
    }

    @Test
    fun `structure is correct after removed right son with both sons (needn't balanceing)`() {
        val tree = makeTreeForRemoveSonWithBothSons()
        tree.remove('u')
        val expectedDependences = listOf(
            Triple(0, 1, 8), Triple(1, 2, 6), Triple(2, 3, 4),
            Triple(6, null, 7), Triple(4, 5, null), Triple(8, 9, 10), Triple(10, null, 11)
        )
        val expectedOrder = arrayOf('n', 'f', 'b', 'a', 'e', 'c', 'i', 'k', 's', 'q', 'w', 'z')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependences))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after removed RSon with both sons (needn't balanceing)`() {
        val tree = makeTreeForRemoveSonWithBothSons()
        tree.remove('u')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForRemoveRootWithBothSonsTest(): AVLTreeForTest<Char, Int> {
        val leftSon = AVLVertex('b', 2, AVLVertex('a', 1), AVLVertex('e', 5), 0)
        val rightSon = AVLVertex('i', 9, null, AVLVertex('k', 11), -1)
        val root = AVLVertex('f', 6, leftSon, rightSon, 0)
        return AVLTreeForTest(root, 6L)
    }

    @Test
    fun `remove root with both sons returns due value (needn't balanceing)`() {
        val tree = makeTreeForRemoveRootWithBothSonsTest()
        assert(tree.remove('f') == 6)
    }

    @Test
    fun `content is correct after removed root with both sons (needn't balanceing)`() {
        val tree = makeTreeForRemoveRootWithBothSonsTest()
        tree.remove('f')
        val expectedContent = setOf('a' to 1, 'b' to 2, 'e' to 5, 'i' to 9, 'k' to 11)
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size decreased by 1 after removed root with both sons (needn't balanceing)`() {
        val tree = makeTreeForRemoveRootWithBothSonsTest()
        tree.remove('f')
        assert(tree.size() == 5L)
    }

    @Test
    fun `structure is correct after removed root with both sons (needn't balanceing)`() {
        val tree = makeTreeForRemoveRootWithBothSonsTest()
        tree.remove('f')
        val expectedDependences = listOf(Triple(0, 1, 3), Triple(1, 2, null), Triple(3, null, 4))
        val expectedOrder = arrayOf('e', 'b', 'a', 'i', 'k')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependences))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after removed root with both sons (needn't balanceing)`() {
        val tree = makeTreeForRemoveRootWithBothSonsTest()
        tree.remove('f')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForRemoveWithLeftRotate1Test(): AVLTreeForTest<Char, Boolean> {
        val rightSonSRightSon = AVLVertex('o', false, null, AVLVertex('p', true), -1)
        val rightSon = AVLVertex('m', true, AVLVertex('l', true), rightSonSRightSon, -1)
        val root = AVLVertex('k', true, AVLVertex('i', false, AVLVertex('a', false), null, 1), rightSon, -1)
        return AVLTreeForTest(root, 7L)
    }

    private fun makeTreeForRemoveWithLeftRotate2Test(): AVLTreeForTest<Char, Boolean> {
        val rightSonSRightSon = AVLVertex('o', false, AVLVertex('n', true), AVLVertex('p', true), 0)
        val rightSon = AVLVertex('m', true, AVLVertex('l', true), rightSonSRightSon, -1)
        val root = AVLVertex('k', true, AVLVertex('i', false, AVLVertex('a', false), null, 1), rightSon, -1)
        return AVLTreeForTest(root, 8L)
    }

    // new subroot initially had sonSHeightDiff == -1 in (1) and 0 in (2)
    @Test
    fun `returns due value after removed and rotated left(1)`() {
        val tree = makeTreeForRemoveWithLeftRotate1Test()
        assert(tree.remove('l') == true)
    }

    @Test
    fun `returns due value after removed and rotated left(2)`() {
        val tree = makeTreeForRemoveWithLeftRotate2Test()
        assert(tree.remove('l') == true)
    }

    @Test
    fun `content is correct after removed and rotated left (1)`() {
        val tree = makeTreeForRemoveWithLeftRotate1Test()
        tree.remove('l')
        val expectedContent = setOf(
            'k' to true, 'i' to false, 'm' to true,
            'o' to false, 'p' to true, 'a' to false
        )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `content is correct after removed and rotated left (2)`() {
        val tree = makeTreeForRemoveWithLeftRotate2Test()
        tree.remove('l')
        val expectedContent = setOf(
            'k' to true, 'i' to false, 'm' to true,
            'o' to false, 'p' to true, 'n' to true, 'a' to false
        )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size decreased by 1 after removed and rotated left (1)`() {
        val tree = makeTreeForRemoveWithLeftRotate1Test()
        tree.remove('l')
        assert(tree.size() == 6L)
    }

    @Test
    fun `size decreased by 1 after removed and rotated left (2)`() {
        val tree = makeTreeForRemoveWithLeftRotate2Test()
        tree.remove('l')
        assert(tree.size() == 7L)
    }

    @Test
    fun `structure is correct after removed and rotated left (1)`() {
        val tree = makeTreeForRemoveWithLeftRotate1Test()
        tree.remove('l')
        val expectedDependences = listOf(Triple(0, 1, 3), Triple(3, 4, 5), Triple(1, 2, null))
        val expectedOrder = arrayOf('k', 'i', 'a', 'o', 'm', 'p')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependences))
    }

    @Test
    fun `structure is correct after removed and rotated left (2)`() {
        val tree = makeTreeForRemoveWithLeftRotate2Test()
        tree.remove('l')
        val expectedDependences = listOf(
            Triple(0, 1, 3), Triple(3, 4, 6),
            Triple(4, null, 5), Triple(1, 2, null)
        )
        val expectedOrder = arrayOf('k', 'i', 'a', 'o', 'm', 'n', 'p')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependences))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after removed and rotated left (1)`() {
        val tree = makeTreeForRemoveWithLeftRotate1Test()
        tree.remove('l')
        assert(isSonsHeightDiffCorrect(tree))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after removed and rotated left (2)`() {
        val tree = makeTreeForRemoveWithLeftRotate2Test()
        tree.remove('l')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForRemoveWithRightRotate1Test(): AVLTreeForTest<Char, Boolean> {
        val leftSonSLeftSon = AVLVertex('b', true, AVLVertex('a', false), null, 1)
        val leftSon = AVLVertex('d', true, leftSonSLeftSon, AVLVertex('e', false), 1)
        val root = AVLVertex('k', true, leftSon, AVLVertex('m', true, null, AVLVertex('o', false), -1), 1)
        return AVLTreeForTest(root, 7L)
    }

    private fun makeTreeForRemoveWithRightRotate2Test(): AVLTreeForTest<Char, Boolean> {
        val leftSonSLeftSon = AVLVertex('b', true, AVLVertex('a', false), AVLVertex('c', true), 0)
        val leftSon = AVLVertex('d', true, leftSonSLeftSon, AVLVertex('e', false), 1)
        val root = AVLVertex('k', true, leftSon, AVLVertex('m', true, null, AVLVertex('o', false), -1), 1)
        return AVLTreeForTest(root, 8L)
    }

    // new subroot initially had sonSHeightDiff == 1 in (1) and 0 in (2)
    @Test
    fun `returns due value after removed and rotated right(1)`() {
        val tree = makeTreeForRemoveWithRightRotate1Test()
        assert(tree.remove('e') == false)
    }

    @Test
    fun `returns due value after removed and rotated right(2)`() {
        val tree = makeTreeForRemoveWithRightRotate2Test()
        assert(tree.remove('e') == false)
    }

    @Test
    fun `content is correct after removed and rotated right (1)`() {
        val tree = makeTreeForRemoveWithRightRotate1Test()
        tree.remove('e')
        val expectedContent = setOf(
            'k' to true, 'b' to true, 'm' to true,
            'o' to false, 'd' to true, 'a' to false
        )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `content is correct after removed and rotated right (2)`() {
        val tree = makeTreeForRemoveWithRightRotate2Test()
        tree.remove('e')
        val expectedContent = setOf(
            'k' to true, 'b' to true, 'm' to true,
            'o' to false, 'd' to true, 'a' to false, 'c' to true
        )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size decreased by 1 after removed and rotated right (1)`() {
        val tree = makeTreeForRemoveWithRightRotate1Test()
        tree.remove('e')
        assert(tree.size() == 6L)
    }

    @Test
    fun `size decreased by 1 after removed and rotated right (2)`() {
        val tree = makeTreeForRemoveWithRightRotate2Test()
        tree.remove('e')
        assert(tree.size() == 7L)
    }

    @Test
    fun `structure is correct after removed and rotated right (1)`() {
        val tree = makeTreeForRemoveWithRightRotate1Test()
        tree.remove('e')
        val expectedDependences = listOf(Triple(0, 1, 4), Triple(1, 2, 3), Triple(4, null, 5))
        val expectedOrder = arrayOf('k', 'b', 'a', 'd', 'm', 'o')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependences))
    }

    @Test
    fun `structure is correct after removed and rotated right (2)`() {
        val tree = makeTreeForRemoveWithRightRotate2Test()
        tree.remove('e')
        val expectedDependences = listOf(
            Triple(0, 1, 5), Triple(1, 2, 3),
            Triple(5, null, 6), Triple(3, 4, null)
        )
        val expectedOrder = arrayOf('k', 'b', 'a', 'd', 'c', 'm', 'o')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependences))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after removed and rotated right (1)`() {
        val tree = makeTreeForRemoveWithRightRotate1Test()
        tree.remove('e')
        assert(isSonsHeightDiffCorrect(tree))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after removed and rotated right (2)`() {
        val tree = makeTreeForRemoveWithRightRotate2Test()
        tree.remove('e')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForRotateLeftChangesRootRemoveTest(): AVLTreeForTest<Int, Int> {
        val root = AVLVertex(1, 1, AVLVertex(0, 0), AVLVertex(3, 3, AVLVertex(2, 2), AVLVertex(5, 5)), -1)
        return AVLTreeForTest(root, 5L)
    }

    @Test
    fun `returns due value after removed and rotated right(rotateLeft changed root)`() {
        val tree = makeTreeForRotateLeftChangesRootRemoveTest()
        assert(tree.remove(0) == 0)
    }

    @Test
    fun `content is correct after removed (rotateLeft changed root)`() {
        val tree = makeTreeForRotateLeftChangesRootRemoveTest()
        tree.remove(0)
        val expectedContent = setOf(1 to 1, 2 to 2, 3 to 3, 5 to 5)
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size decreased by 1 after removed (rotateLeft changed root)`() {
        val tree = makeTreeForRotateLeftChangesRootRemoveTest()
        tree.remove(0)
        assert(tree.size() == 4L)
    }

    @Test
    fun `structure is correct after removed (rotateLeft changed root)`() {
        val tree = makeTreeForRotateLeftChangesRootRemoveTest()
        tree.remove(0)
        val expectedDependences = listOf(Triple(0, 1, 3), Triple(1, null, 2))
        val expectedOrder = arrayOf(3, 1, 2, 5)
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependences))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after removed (rotateLeft changed root)`() {
        val tree = makeTreeForRotateLeftChangesRootRemoveTest()
        tree.remove(0)
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForRotateRightChangesRootRemoveTest(): AVLTreeForTest<Int, Int> {
        val root = AVLVertex(-1, 1, AVLVertex(-3, 3, AVLVertex(-5, 5), AVLVertex(-2, 2)), AVLVertex(0, 0), 1)
        return AVLTreeForTest(root, 5L)
    }

    @Test
    fun `returns due value after removed and rotated right(rotateRight changed root)`() {
        val tree = makeTreeForRotateRightChangesRootRemoveTest()
        assert(tree.remove(0) == 0)
    }

    @Test
    fun `content is correct after removed (rotateRight changed root)`() {
        val tree = makeTreeForRotateRightChangesRootRemoveTest()
        tree.remove(0)
        val expectedContent = setOf(-1 to 1, -2 to 2, -3 to 3, -5 to 5)
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size decreased by 1 after removed (rotateRight changed root)`() {
        val tree = makeTreeForRotateRightChangesRootRemoveTest()
        tree.remove(0)
        assert(tree.size() == 4L)
    }

    @Test
    fun `structure is correct after removed (rotateRight changed root)`() {
        val tree = makeTreeForRotateRightChangesRootRemoveTest()
        tree.remove(0)
        val expectedDependences = listOf(Triple(0, 1, 2), Triple(2, 3, null))
        val expectedOrder = arrayOf(-3, -5, -1, -2)
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependences))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after removed (rotateRight changed root)`() {
        val tree = makeTreeForRotateRightChangesRootRemoveTest()
        tree.remove(0)
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForBigRotateLeftChangesRootRemoveTest(): AVLTreeForTest<Char, Char> {
        val rightSonSLeftSon = AVLVertex('d', 'D', AVLVertex('c', 'C'), AVLVertex('e', 'E'))
        val rightSon = AVLVertex('f', 'F', rightSonSLeftSon, AVLVertex('g', 'G'), 1)
        val root = AVLVertex('b', 'B', AVLVertex('a', 'A', AVLVertex(' ', ' '), null, 1), rightSon, -1)
        return AVLTreeForTest(root, 8L)
    }

    @Test
    fun `remove returns due value(bigRotateLeft changed root)`() {
        val tree = makeTreeForBigRotateLeftChangesRootRemoveTest()
        assert(tree.remove(' ') == ' ')
    }

    @Test
    fun `content is correct after removed (bigRotateLeft changed root)`() {
        val tree = makeTreeForBigRotateLeftChangesRootRemoveTest()
        tree.remove(' ')
        val expectedContent = setOf(
            'd' to 'D', 'c' to 'C', 'e' to 'E', 'f' to 'F',
            'g' to 'G', 'b' to 'B', 'a' to 'A'
        )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size decreased by 1 after removed (bigRotateLeft changed root)`() {
        val tree = makeTreeForBigRotateLeftChangesRootRemoveTest()
        tree.remove(' ')
        assert(tree.size() == 7L)
    }

    @Test
    fun `structure is correct after removed (bigRotateLeft changed root)`() {
        val tree = makeTreeForBigRotateLeftChangesRootRemoveTest()
        tree.remove(' ')
        val expectedDependences = listOf(Triple(0, 1, 4), Triple(1, 2, 3), Triple(4, 5, 6))
        val expectedOrder = arrayOf('d', 'b', 'a', 'c', 'f', 'e', 'g')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependences))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after removed (bigRotateLeft changed root)`() {
        val tree = makeTreeForBigRotateLeftChangesRootRemoveTest()
        tree.remove(' ')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForBigRotateRightChangesRootRemoveTest(): AVLTreeForTest<Char, Int> {
        val leftSonSRightSon = AVLVertex('e', 5, AVLVertex('c', 3), AVLVertex('d', 4))
        val leftSon = AVLVertex('b', 2, AVLVertex('a', 1), leftSonSRightSon, -1)
        val root = AVLVertex('f', 8, leftSon, AVLVertex('i', 9, null, AVLVertex('k', 10), -1), 1)
        return AVLTreeForTest(root, 8L)
    }

    @Test
    fun `remove returns due value(bigRotateRight changed root)`() {
        val tree = makeTreeForBigRotateRightChangesRootRemoveTest()
        assert(tree.remove('k') == 10)
    }

    @Test
    fun `content is correct after removed (bigRotateRight changed root)`() {
        val tree = makeTreeForBigRotateRightChangesRootRemoveTest()
        tree.remove('k')
        val expectedContent = setOf(
            'a' to 1, 'b' to 2, 'c' to 3, 'd' to 4, 'e' to 5,
            'i' to 9, 'f' to 8
        )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size decreased by 1 after removed (bigRotateRight changed root)`() {
        val tree = makeTreeForBigRotateRightChangesRootRemoveTest()
        tree.remove('k')
        assert(tree.size() == 7L)
    }

    @Test
    fun `structure is correct after removed (bigRotateRight changed root)`() {
        val tree = makeTreeForBigRotateRightChangesRootRemoveTest()
        tree.remove('k')
        val expectedDependences = listOf(Triple(0, 1, 4), Triple(1, 2, 3), Triple(4, 5, 6))
        val expectedOrder = arrayOf('e', 'b', 'a', 'c', 'f', 'd', 'i')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependences))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after removed (bigRotateRight changed root)`() {
        val tree = makeTreeForBigRotateRightChangesRootRemoveTest()
        tree.remove('k')
        assert(isSonsHeightDiffCorrect(tree))
    }
}
