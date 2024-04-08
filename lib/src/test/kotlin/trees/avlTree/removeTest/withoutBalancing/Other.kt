package trees.avlTree.removeTest.withoutBalancing

import org.junit.jupiter.api.Test
import trees.avlTree.AVLTreeForTest
import trees.avlTree.AuxiliaryFunctions.isSonsHeightDiffCorrect
import trees.avlTree.AuxiliaryFunctions.isTreeConsistsOf
import trees.avlTree.AuxiliaryFunctions.isTreeSStructureThat
import vertexes.AVLVertex

class Other {
    private fun makeEmptyTree(): AVLTreeForTest<Int, Char> {
        return AVLTreeForTest<Int, Char>()
    }

    @Test
    fun `delete from emptyTree returns null`() {
        val tree = makeEmptyTree()
        assert(tree.remove(0) == null)
    }

    private fun makeSize1Tree(): AVLTreeForTest<Char, Char> {
        return AVLTreeForTest(AVLVertex('b', '+'), 1L) // init tree by root and size (don't use put)
    }

    @Test
    fun `tree is empty after root of 'size 1' tree was deleted `() {
        val tree = makeSize1Tree()
        tree.remove('b')
        assert(tree.getVertexesInDFSOrder().isEmpty())
    }

    @Test
    fun `size equals 0 after root of 'size 1' tree was deleted`() {
        val tree = makeSize1Tree()
        tree.remove('b')
        assert(tree.size() == 0L)
    }

    @Test
    fun `remove fun return null if entry's key isn't exists(1)`() {
        val tree = makeSize1Tree()
        assert(tree.remove('a') == null)
    }

    @Test
    fun `tree has no changes after deletion by non existed larger key`() {
        val tree = makeSize1Tree()
        tree.remove('c')
        assert(tree.size() == 1L)
        assert(tree.getRootT()?.value == '+')
    }

    @Test
    fun `tree has no changes after deletion by non existed lesser key`() {
        val tree = makeSize1Tree()
        tree.remove('a')
        assert(tree.size() == 1L)
        assert(tree.getRootT()?.value == '+')
    }

    private fun makeTreeForRemoveLeafWithoutBalancingTest(): AVLTreeForTest<Char, String> {
        return AVLTreeForTest(AVLVertex('r', "r", AVLVertex('n', "n"), AVLVertex('z', "z")), 3L)
    }

    @Test
    fun `right leaf deleted after remove with one's key was called`() {
        val tree = makeTreeForRemoveLeafWithoutBalancingTest()
        tree.remove('z')
        val root = tree.getRootT()
        if (root != null) {
            assert(root.rightSon == null)
        } else {
            assert(false)
        }
    }

    @Test
    fun `left leaf deleted after remove with one's key was called`() {
        val tree = makeTreeForRemoveLeafWithoutBalancingTest()
        tree.remove('n')
        val root = tree.getRootT()
        if (root != null) {
            assert(root.leftSon == null)
        } else {
            assert(false)
        }
    }

    @Test
    fun `remove by right leaf's key return due value`() {
        val tree = makeTreeForRemoveLeafWithoutBalancingTest()
        assert(tree.remove('z') == "z")
    }

    @Test
    fun `remove by left leaf's key return due value`() {
        val tree = makeTreeForRemoveLeafWithoutBalancingTest()
        assert(tree.remove('n') == "n")
    }

    @Test
    fun `vertex's sonsHeightDiff increased by 1 after one's right leaf was deleted`() {
        val tree = makeTreeForRemoveLeafWithoutBalancingTest()
        tree.remove('z')
        val root = tree.getRootT()
        assert(root?.sonsHeightDiff == 1)
    }

    @Test
    fun `vertex's sonsHeightDiff decreased by 1 after one's left leaf was deleted`() {
        val tree = makeTreeForRemoveLeafWithoutBalancingTest()
        tree.remove('n')
        val root = tree.getRootT()
        assert(root?.sonsHeightDiff == -1)
    }

    @Test
    fun `size decreased by 1 after right leaf was deleted`() {
        val tree = makeTreeForRemoveLeafWithoutBalancingTest()
        tree.remove('z')
        assert(tree.size() == 2L)
    }

    @Test
    fun `size decreased by 1 after left leaf was deleted`() {
        val tree = makeTreeForRemoveLeafWithoutBalancingTest()
        tree.remove('n')
        assert(tree.size() == 2L)
    }

    private fun makeTreeForRemoveLeftSonWithOnlyLeftSonTest(): AVLTreeForTest<Char, Int> {
        val leftSon = AVLVertex('q', 9, AVLVertex('o', 0), null, 1)
        val root = AVLVertex('s', 5, leftSon, AVLVertex('z', 2), 1)
        return AVLTreeForTest(root, 4L)
    }

    @Test
    fun `remove be key of left son with only left son returns due value`() {
        val tree = makeTreeForRemoveLeftSonWithOnlyRightSonTest()
        assert(tree.remove('q') == 9)
    }

    @Test
    fun `content is correct after removed left son with only left son`() {
        val tree = makeTreeForRemoveLeftSonWithOnlyLeftSonTest()
        tree.remove('q')
        assert(isTreeConsistsOf(setOf('o' to 0, 's' to 5, 'z' to 2), tree))
    }

    @Test
    fun `size decreased by 1 after left son with only left son was deleted`() {
        val tree = makeTreeForRemoveLeftSonWithOnlyLeftSonTest()
        tree.remove('q')
        assert(tree.size() == 3L)
    }

    @Test
    fun `structure is correct after left son with only left son was deleted`() {
        val tree = makeTreeForRemoveLeftSonWithOnlyLeftSonTest()
        tree.remove('q')
        assert(isTreeSStructureThat(tree, arrayOf('s', 'o', 'z'), listOf(Triple(0, 1, 2))))
    }

    @Test
    fun `sonsHeightDiff values are correct after LSon with only LSon was deleted`() {
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
    fun `remove by key of left son with only right son returns due value`() {
        val tree = makeTreeForRemoveLeftSonWithOnlyRightSonTest()
        assert(tree.remove('q') == 9)
    }

    @Test
    fun `content is correct after left son with only right son was deleted`() {
        val tree = makeTreeForRemoveLeftSonWithOnlyRightSonTest()
        tree.remove('q')
        assert(isTreeConsistsOf(setOf('r' to 7, 's' to 5, 'z' to 2), tree))
    }

    @Test
    fun `size decreased by 1 after left son with only right son was deleted`() {
        val tree = makeTreeForRemoveLeftSonWithOnlyRightSonTest()
        tree.remove('q')
        assert(tree.size() == 3L)
    }

    @Test
    fun `structure is correct after left son with only right son was deleted`() {
        val tree = makeTreeForRemoveLeftSonWithOnlyRightSonTest()
        tree.remove('q')
        assert(isTreeSStructureThat(tree, arrayOf('s', 'r', 'z'), listOf(Triple(0, 1, 2))))
    }

    @Test
    fun `sonsHeightDiff values are correct after LSon with only RSon was deleted`() {
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
    fun `remove by key of right son with only left son returns due value`() {
        val tree = makeTreeForRemoveRightSonWithOnlyLeftSonTest()
        assert(tree.remove('q') == 9)
    }

    @Test
    fun `content is correct after right son with only left son was deleted`() {
        val tree = makeTreeForRemoveRightSonWithOnlyLeftSonTest()
        tree.remove('q')
        assert(isTreeConsistsOf(setOf('o' to 0, 'b' to 6, 'i' to 1), tree))
    }

    @Test
    fun `size decreased by 1 after right son with only left son was deleted`() {
        val tree = makeTreeForRemoveRightSonWithOnlyLeftSonTest()
        tree.remove('q')
        assert(tree.size() == 3L)
    }

    @Test
    fun `structure is correct after right son with only left son was deleted`() {
        val tree = makeTreeForRemoveRightSonWithOnlyLeftSonTest()
        tree.remove('q')
        assert(isTreeSStructureThat(tree, arrayOf('i', 'b', 'o'), listOf(Triple(0, 1, 2))))
    }

    @Test
    fun `sonsHeightDiff values are correct after RSon with only LSon was deleted`() {
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
    fun `remove by key of right son with only right son returns due value`() {
        val tree = makeTreeForRemoveRightSonWithOnlyRightSonTest()
        assert(tree.remove('q') == 9)
    }

    @Test
    fun `content is correct after right son with only right son was deleted`() {
        val tree = makeTreeForRemoveRightSonWithOnlyRightSonTest()
        tree.remove('q')
        assert(isTreeConsistsOf(setOf('r' to 7, 'b' to 6, 'i' to 1), tree))
    }

    @Test
    fun `size decreased by 1 after right son with only right son was deleted`() {
        val tree = makeTreeForRemoveRightSonWithOnlyRightSonTest()
        tree.remove('q')
        assert(tree.size() == 3L)
    }

    @Test
    fun `structure is correct after right son with only right son was deleted`() {
        val tree = makeTreeForRemoveRightSonWithOnlyRightSonTest()
        tree.remove('q')
        assert(isTreeSStructureThat(tree, arrayOf('i', 'b', 'r'), listOf(Triple(0, 1, 2))))
    }

    @Test
    fun `sonsHeightDiff values are correct after RSon with only RSon was deleted`() {
        val tree = makeTreeForRemoveRightSonWithOnlyRightSonTest()
        tree.remove('q')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForRemoveRootWithOnlyLeftSon(): AVLTreeForTest<String, String> {
        return AVLTreeForTest(AVLVertex("be", "es", (AVLVertex("and", "et")), null, 1), 2L)
    }

    @Test
    fun `remove by key of root with only left son returns due value`() {
        val tree = makeTreeForRemoveRootWithOnlyLeftSon()
        assert(tree.remove("be") == "es")
    }

    @Test
    fun `content is correct after root with only left son was deleted`() {
        val tree = makeTreeForRemoveRootWithOnlyLeftSon()
        tree.remove("be")
        assert(isTreeConsistsOf(setOf("and" to "et"), tree))
    }

    @Test
    fun `sonsHeightDiff values are correct after root with only LSon was deleted`() {
        val tree = makeTreeForRemoveRootWithOnlyLeftSon()
        tree.remove("and")
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForRemoveRootWithOnlyRightSon(): AVLTreeForTest<String, String> {
        return AVLTreeForTest(AVLVertex("and", "et", null, (AVLVertex("be", "es")), -1), 2L)
    }

    @Test
    fun `remove by key of root with only right son returns due value`() {
        val tree = makeTreeForRemoveRootWithOnlyRightSon()
        assert(tree.remove("and") == "et")
    }

    @Test
    fun `content is correct after root with only right son was deleted`() {
        val tree = makeTreeForRemoveRootWithOnlyRightSon()
        tree.remove("and")
        assert(isTreeConsistsOf(setOf("be" to "es"), tree))
    }

    @Test
    fun `sonsHeightDiff values are correct after root with only RSon was deleted`() {
        val tree = makeTreeForRemoveRootWithOnlyRightSon()
        tree.remove("and")
        assert(isSonsHeightDiffCorrect(tree))
    }
}
