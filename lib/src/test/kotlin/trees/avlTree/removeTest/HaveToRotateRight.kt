package trees.avlTree.removeTest

import org.junit.jupiter.api.Test
import trees.avlTree.AVLTreeForTest
import trees.avlTree.AuxiliaryFunctions.isSonsHeightDiffCorrect
import trees.avlTree.AuxiliaryFunctions.isTreeConsistsOf
import trees.avlTree.AuxiliaryFunctions.isTreeSStructureThat
import vertexes.AVLVertex

class HaveToRotateRight {
    private fun makeTreeForRemoveWithRightRotation1Test(): AVLTreeForTest<Char, Boolean> {
        val leftSonSLeftSon = AVLVertex('b', true, AVLVertex('a', false), null, 1)
        val leftSon = AVLVertex('d', true, leftSonSLeftSon, AVLVertex('e', false), 1)
        val root = AVLVertex('k', true, leftSon, AVLVertex('m', true, null, AVLVertex('o', false), -1), 1)
        return AVLTreeForTest(root, 7L)
    }

    private fun makeTreeForRemoveWithRightRotation2Test(): AVLTreeForTest<Char, Boolean> {
        val leftSonSLeftSon = AVLVertex('b', true, AVLVertex('a', false), AVLVertex('c', true), 0)
        val leftSon = AVLVertex('d', true, leftSonSLeftSon, AVLVertex('e', false), 1)
        val root = AVLVertex('k', true, leftSon, AVLVertex('m', true, null, AVLVertex('o', false), -1), 1)
        return AVLTreeForTest(root, 8L)
    }

    // new subtree's root initially had sonSHeightDiff == 1 in (1) and 0 in (2)
    @Test
    fun `returns due value after deletion (1)`() {
        val tree = makeTreeForRemoveWithRightRotation1Test()
        assert(tree.remove('e') == false)
    }

    @Test
    fun `returns due value after deletion (2)`() {
        val tree = makeTreeForRemoveWithRightRotation2Test()
        assert(tree.remove('e') == false)
    }

    @Test
    fun `content is correct after deletion (1)`() {
        val tree = makeTreeForRemoveWithRightRotation1Test()
        tree.remove('e')
        val expectedContent =
            setOf(
                'k' to true,
                'b' to true,
                'm' to true,
                'o' to false,
                'd' to true,
                'a' to false,
            )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `content is correct after deletion (2)`() {
        val tree = makeTreeForRemoveWithRightRotation2Test()
        tree.remove('e')
        val expectedContent =
            setOf(
                'k' to true,
                'b' to true,
                'm' to true,
                'o' to false,
                'd' to true,
                'a' to false,
                'c' to true,
            )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size decreased by 1 after deletion (1)`() {
        val tree = makeTreeForRemoveWithRightRotation1Test()
        tree.remove('e')
        assert(tree.size() == 6L)
    }

    @Test
    fun `size decreased by 1 after deletion (2)`() {
        val tree = makeTreeForRemoveWithRightRotation2Test()
        tree.remove('e')
        assert(tree.size() == 7L)
    }

    @Test
    fun `structure is correct after deletion (1)`() {
        val tree = makeTreeForRemoveWithRightRotation1Test()
        tree.remove('e')
        val expectedDependencies = listOf(Triple(0, 1, 4), Triple(1, 2, 3), Triple(4, null, 5))
        val expectedOrder = arrayOf('k', 'b', 'a', 'd', 'm', 'o')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependencies))
    }

    @Test
    fun `structure is correct after deletion (2)`() {
        val tree = makeTreeForRemoveWithRightRotation2Test()
        tree.remove('e')
        val expectedDependencies =
            listOf(
                Triple(0, 1, 5),
                Triple(1, 2, 3),
                Triple(5, null, 6),
                Triple(3, 4, null),
            )
        val expectedOrder = arrayOf('k', 'b', 'a', 'd', 'c', 'm', 'o')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependencies))
    }

    @Test
    fun `sonsHeightDiff values are correct after deletion (1)`() {
        val tree = makeTreeForRemoveWithRightRotation1Test()
        tree.remove('e')
        assert(isSonsHeightDiffCorrect(tree))
    }

    @Test
    fun `sonsHeightDiff values are correct after deletion (2)`() {
        val tree = makeTreeForRemoveWithRightRotation2Test()
        tree.remove('e')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForRightRotationChangesRootRemoveTest(): AVLTreeForTest<Int, Int> {
        val root = AVLVertex(-1, 1, AVLVertex(-3, 3, AVLVertex(-5, 5), AVLVertex(-2, 2)), AVLVertex(0, 0), 1)
        return AVLTreeForTest(root, 5L)
    }

    @Test
    fun `returns due value after deletion (rotateRight changes root)`() {
        val tree = makeTreeForRightRotationChangesRootRemoveTest()
        assert(tree.remove(0) == 0)
    }

    @Test
    fun `content is correct after deletion (rotateRight changes root)`() {
        val tree = makeTreeForRightRotationChangesRootRemoveTest()
        tree.remove(0)
        val expectedContent = setOf(-1 to 1, -2 to 2, -3 to 3, -5 to 5)
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size decreased by 1 after deletion (rotateRight changes root)`() {
        val tree = makeTreeForRightRotationChangesRootRemoveTest()
        tree.remove(0)
        assert(tree.size() == 4L)
    }

    @Test
    fun `structure is correct after deletion (rotateRight changes root)`() {
        val tree = makeTreeForRightRotationChangesRootRemoveTest()
        tree.remove(0)
        val expectedDependencies = listOf(Triple(0, 1, 2), Triple(2, 3, null))
        val expectedOrder = arrayOf(-3, -5, -1, -2)
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependencies))
    }

    @Test
    fun `sonsHeightDiff values are correct after deletion (rotateRight changes root)`() {
        val tree = makeTreeForRightRotationChangesRootRemoveTest()
        tree.remove(0)
        assert(isSonsHeightDiffCorrect(tree))
    }
}
