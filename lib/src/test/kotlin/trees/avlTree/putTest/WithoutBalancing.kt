package trees.avlTree.putTest

import org.junit.jupiter.api.Test
import trees.avlTree.AVLTreeForTest
import trees.avlTree.AuxiliaryFunctions.isSonsHeightDiffCorrect
import trees.avlTree.AuxiliaryFunctions.isTreeConsistsOf
import trees.avlTree.AuxiliaryFunctions.isTreeSStructureThat
import vertexes.AVLVertex

class WithoutBalancing {
    private fun makeEmptyTree(): AVLTreeForTest<Int, Char> {
        return AVLTreeForTest<Int, Char>()
    }

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

    private fun makeTreeForNeedNotBalancingPutTest(): AVLTreeForTest<Int, Char> {
        return AVLTreeForTest(AVLVertex(4, 'A', AVLVertex(1, 'I'), AVLVertex(5, 'S')), 3L)
    }

    @Test
    fun `content is correct after entry was added`() {
        val tree = makeTreeForNeedNotBalancingPutTest()
        tree.put(0, 'O')
        assert(isTreeConsistsOf(setOf(0 to 'O', 1 to 'I', 5 to 'S', 4 to 'A'), tree))
    }

    @Test
    fun `size increased by 1 after entry was added to 'size 2+' tree`() {
        val tree = makeTreeForNeedNotBalancingPutTest()
        tree.put(0, 'O')
        assert(tree.size() == 4L)
    }

    @Test
    fun `structure is correct after entry was added to 'size 2+' tree`() {
        val tree = makeTreeForNeedNotBalancingPutTest()
        tree.put(0, 'O')
        val expectedDependencies = listOf(Triple(0, 1, 3), Triple(1, 2, null))
        assert(isTreeSStructureThat(tree, arrayOf(4, 1, 0, 5), expectedDependencies))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after entry was added to 'size 2+' tree`() {
        val tree = makeTreeForNeedNotBalancingPutTest()
        tree.put(0, 'O')
        assert(isSonsHeightDiffCorrect(tree))
    }
}
