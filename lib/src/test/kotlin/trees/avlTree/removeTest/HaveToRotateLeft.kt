package trees.avlTree.removeTest

import trees.avlTree.AuxiliaryFunctions.isTreeConsistsOf
import trees.avlTree.AuxiliaryFunctions.isTreeSStructureThat
import trees.avlTree.AuxiliaryFunctions.isSonsHeightDiffCorrect
import main.vertexes.AVLVertex
import org.junit.jupiter.api.Test
import trees.avlTree.AVLTreeForTest

class HaveToRotateLeft {
    private fun makeTreeForRemoveWithLeftRotation1Test(): AVLTreeForTest<Char, Boolean> {
        val rightSonSRightSon = AVLVertex('o', false, null, AVLVertex('p', true), -1)
        val rightSon = AVLVertex('m', true, AVLVertex('l', true), rightSonSRightSon, -1)
        val root = AVLVertex('k', true, AVLVertex('i', false, AVLVertex('a', false), null, 1), rightSon, -1)
        return AVLTreeForTest(root, 7L)
    }

    private fun makeTreeForRemoveWithLeftRotation2Test(): AVLTreeForTest<Char, Boolean> {
        val rightSonSRightSon = AVLVertex('o', false, AVLVertex('n', true), AVLVertex('p', true), 0)
        val rightSon = AVLVertex('m', true, AVLVertex('l', true), rightSonSRightSon, -1)
        val root = AVLVertex('k', true, AVLVertex('i', false, AVLVertex('a', false), null, 1), rightSon, -1)
        return AVLTreeForTest(root, 8L)
    }

    // new subtree's root initially had sonSHeightDiff == -1 in (1) and 0 in (2)
    @Test
    fun `returns due value after deletion (1)`() {
        val tree = makeTreeForRemoveWithLeftRotation1Test()
        assert(tree.remove('l') == true)
    }

    @Test
    fun `returns due value after deletion (2)`() {
        val tree = makeTreeForRemoveWithLeftRotation2Test()
        assert(tree.remove('l') == true)
    }

    @Test
    fun `content is correct after deletion (1)`() {
        val tree = makeTreeForRemoveWithLeftRotation1Test()
        tree.remove('l')
        val expectedContent =
                setOf(
                        'k' to true,
                        'i' to false,
                        'm' to true,
                        'o' to false,
                        'p' to true,
                        'a' to false,
                )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `content is correct after deletion (2)`() {
        val tree = makeTreeForRemoveWithLeftRotation2Test()
        tree.remove('l')
        val expectedContent =
                setOf(
                        'k' to true,
                        'i' to false,
                        'm' to true,
                        'o' to false,
                        'p' to true,
                        'n' to true,
                        'a' to false,
                )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size decreased by 1 after deletion (1)`() {
        val tree = makeTreeForRemoveWithLeftRotation1Test()
        tree.remove('l')
        assert(tree.size() == 6L)
    }

    @Test
    fun `size decreased by 1 after deletion (2)`() {
        val tree = makeTreeForRemoveWithLeftRotation2Test()
        tree.remove('l')
        assert(tree.size() == 7L)
    }

    @Test
    fun `structure is correct after deletion (1)`() {
        val tree = makeTreeForRemoveWithLeftRotation1Test()
        tree.remove('l')
        val expectedDependencies = listOf(Triple(0, 1, 3), Triple(3, 4, 5), Triple(1, 2, null))
        val expectedOrder = arrayOf('k', 'i', 'a', 'o', 'm', 'p')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependencies))
    }

    @Test
    fun `structure is correct after deletion (2)`() {
        val tree = makeTreeForRemoveWithLeftRotation2Test()
        tree.remove('l')
        val expectedDependencies =
                listOf(
                        Triple(0, 1, 3),
                        Triple(3, 4, 6),
                        Triple(4, null, 5),
                        Triple(1, 2, null),
                )
        val expectedOrder = arrayOf('k', 'i', 'a', 'o', 'm', 'n', 'p')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependencies))
    }

    @Test
    fun `sonsHeightDiff values are correct after deletion (1)`() {
        val tree = makeTreeForRemoveWithLeftRotation1Test()
        tree.remove('l')
        assert(isSonsHeightDiffCorrect(tree))
    }

    @Test
    fun `sonsHeightDiff values are correct after deletion (2)`() {
        val tree = makeTreeForRemoveWithLeftRotation2Test()
        tree.remove('l')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForLeftRotationChangesRootRemoveTest(): AVLTreeForTest<Int, Int> {
        val root = AVLVertex(1, 1, AVLVertex(0, 0), AVLVertex(3, 3, AVLVertex(2, 2), AVLVertex(5, 5)), -1)
        return AVLTreeForTest(root, 5L)
    }

    @Test
    fun `returns due value after deletion (rotateLeft changes root)`() {
        val tree = makeTreeForLeftRotationChangesRootRemoveTest()
        assert(tree.remove(0) == 0)
    }

    @Test
    fun `content is correct after deletion (rotateLeft changes root)`() {
        val tree = makeTreeForLeftRotationChangesRootRemoveTest()
        tree.remove(0)
        val expectedContent = setOf(1 to 1, 2 to 2, 3 to 3, 5 to 5)
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size decreased by 1 after deletion (rotateLeft changes root)`() {
        val tree = makeTreeForLeftRotationChangesRootRemoveTest()
        tree.remove(0)
        assert(tree.size() == 4L)
    }

    @Test
    fun `structure is correct after deletion (rotateLeft changes root)`() {
        val tree = makeTreeForLeftRotationChangesRootRemoveTest()
        tree.remove(0)
        val expectedDependencies = listOf(Triple(0, 1, 3), Triple(1, null, 2))
        val expectedOrder = arrayOf(3, 1, 2, 5)
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependencies))
    }

    @Test
    fun `sonsHeightDiff values are correct after deletion (rotateLeft changes root)`() {
        val tree = makeTreeForLeftRotationChangesRootRemoveTest()
        tree.remove(0)
        assert(isSonsHeightDiffCorrect(tree))
    }

}