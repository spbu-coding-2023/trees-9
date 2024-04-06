package trees.avlTree.removeTest

import trees.avlTree.AuxiliaryFunctions.isTreeConsistsOf
import trees.avlTree.AuxiliaryFunctions.isTreeSStructureThat
import trees.avlTree.AuxiliaryFunctions.isSonsHeightDiffCorrect
import main.vertexes.AVLVertex
import org.junit.jupiter.api.Test
import trees.avlTree.AVLTreeForTest

class HaveToDoBigRotation {
    private fun makeTreeForBigLeftRotationChangesRootRemoveTest(): AVLTreeForTest<Char, Char> {
        val rightSonSLeftSon = AVLVertex('d', 'D', AVLVertex('c', 'C'), AVLVertex('e', 'E'))
        val rightSon = AVLVertex('f', 'F', rightSonSLeftSon, AVLVertex('g', 'G'), 1)
        val root = AVLVertex('b', 'B', AVLVertex('a', 'A', AVLVertex(' ', ' '), null, 1), rightSon, -1)
        return AVLTreeForTest(root, 8L)
    }

    @Test
    fun `remove returns due value after deletion (bigRotateLeft changes root)`() {
        val tree = makeTreeForBigLeftRotationChangesRootRemoveTest()
        assert(tree.remove(' ') == ' ')
    }

    @Test
    fun `content is correct after deletion (bigRotateLeft changes root)`() {
        val tree = makeTreeForBigLeftRotationChangesRootRemoveTest()
        tree.remove(' ')
        val expectedContent =
                setOf(
                        'd' to 'D',
                        'c' to 'C',
                        'e' to 'E',
                        'f' to 'F',
                        'g' to 'G',
                        'b' to 'B',
                        'a' to 'A',
                )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size decreased by 1 after deletion (bigRotateLeft changes root)`() {
        val tree = makeTreeForBigLeftRotationChangesRootRemoveTest()
        tree.remove(' ')
        assert(tree.size() == 7L)
    }

    @Test
    fun `structure is correct after deletion (bigRotateLeft changes root)`() {
        val tree = makeTreeForBigLeftRotationChangesRootRemoveTest()
        tree.remove(' ')
        val expectedDependencies = listOf(Triple(0, 1, 4), Triple(1, 2, 3), Triple(4, 5, 6))
        val expectedOrder = arrayOf('d', 'b', 'a', 'c', 'f', 'e', 'g')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependencies))
    }

    @Test
    fun `sonsHeightDiff values are correct after deletion (bigRotateLeft changes root)`() {
        val tree = makeTreeForBigLeftRotationChangesRootRemoveTest()
        tree.remove(' ')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForBigRightRotationChangesRootRemoveTest(): AVLTreeForTest<Char, Int> {
        val leftSonSRightSon = AVLVertex('e', 5, AVLVertex('c', 3), AVLVertex('d', 4))
        val leftSon = AVLVertex('b', 2, AVLVertex('a', 1), leftSonSRightSon, -1)
        val root = AVLVertex('f', 8, leftSon, AVLVertex('i', 9, null, AVLVertex('k', 10), -1), 1)
        return AVLTreeForTest(root, 8L)
    }

    @Test
    fun `remove returns due value after deletion (bigRotateRight changes root)`() {
        val tree = makeTreeForBigRightRotationChangesRootRemoveTest()
        assert(tree.remove('k') == 10)
    }

    @Test
    fun `content is correct after deletion (bigRotateRight changes root)`() {
        val tree = makeTreeForBigRightRotationChangesRootRemoveTest()
        tree.remove('k')
        val expectedContent =
                setOf(
                        'a' to 1,
                        'b' to 2,
                        'c' to 3,
                        'd' to 4,
                        'e' to 5,
                        'i' to 9,
                        'f' to 8,
                )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size decreased by 1 after deletion (bigRotateRight changes root)`() {
        val tree = makeTreeForBigRightRotationChangesRootRemoveTest()
        tree.remove('k')
        assert(tree.size() == 7L)
    }

    @Test
    fun `structure is correct after deletion (bigRotateRight changes root)`() {
        val tree = makeTreeForBigRightRotationChangesRootRemoveTest()
        tree.remove('k')
        val expectedDependencies = listOf(Triple(0, 1, 4), Triple(1, 2, 3), Triple(4, 5, 6))
        val expectedOrder = arrayOf('e', 'b', 'a', 'c', 'f', 'd', 'i')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependencies))
    }

    @Test
    fun `sonsHeightDiff values are correct after deletion (bigRotateRight changes root)`() {
        val tree = makeTreeForBigRightRotationChangesRootRemoveTest()
        tree.remove('k')
        assert(isSonsHeightDiffCorrect(tree))
    }
}
