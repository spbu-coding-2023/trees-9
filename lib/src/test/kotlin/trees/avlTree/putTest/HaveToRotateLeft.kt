package trees.avlTree.putTest

import org.junit.jupiter.api.Test
import trees.avlTree.AVLTreeForTest
import trees.avlTree.AuxiliaryFunctions.isSonsHeightDiffCorrect
import trees.avlTree.AuxiliaryFunctions.isTreeConsistsOf
import trees.avlTree.AuxiliaryFunctions.isTreeSStructureThat
import vertexes.AVLVertex

class HaveToRotateLeft {
    private fun makeTreeForHaveToRotateLeftPutTest(): AVLTreeForTest<Char, Char> {
        val rightSonSRightSon = AVLVertex('h', 'H', AVLVertex('f', 'F'), AVLVertex('i', 'I'))
        val rightSon = AVLVertex('e', 'E', AVLVertex('d', 'D'), rightSonSRightSon, -1)
        val root = AVLVertex('c', 'C', AVLVertex('b', 'B', AVLVertex('a', 'A'), null, 1), rightSon, -1)
        return AVLTreeForTest(root, 8L)
    }
    @Test
    fun `content is correct after entry was added`() {
        val tree = makeTreeForHaveToRotateLeftPutTest()
        tree.put('j', 'J')
        val expectedContent =
                setOf(
                        'a' to 'A',
                        'b' to 'B',
                        'c' to 'C',
                        'd' to 'D',
                        'e' to 'E',
                        'h' to 'H',
                        'f' to 'F',
                        'i' to 'I',
                        'j' to 'J',
                )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size increased by 1 after entry was added`() {
        val tree = makeTreeForHaveToRotateLeftPutTest()
        tree.put('j', 'J')
        assert(tree.size() == 9L)
    }

    @Test
    fun `structure is correct after entry was added`() {
        val tree = makeTreeForHaveToRotateLeftPutTest()
        tree.put('j', 'J')
        val expectedDependencies =
                listOf(
                        Triple(0, 1, 3),
                        Triple(1, 2, null),
                        Triple(3, 4, 7),
                        Triple(4, 5, 6),
                        Triple(7, null, 8),
                )
        val expectedOrder = arrayOf('c', 'b', 'a', 'h', 'e', 'd', 'f', 'i', 'j')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependencies))
    }

    @Test
    fun `sonsHeightDiff values are correct after entry was added`() {
        val tree = makeTreeForHaveToRotateLeftPutTest()
        tree.put('j', 'J')
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
    fun `size increased by 1 after entry was added (rotateLeft changes root)`() {
        val tree = makeTreeForHaveToChangeRootByRotateLeftPutTest()
        tree.put('j', 'J')
        assert(tree.size() == 6L)
    }

    @Test
    fun `structure is correct after entry was added (rotateLeft changes root)`() {
        val tree = makeTreeForHaveToChangeRootByRotateLeftPutTest()
        tree.put('j', 'J')
        val expectedDependencies = listOf(Triple(0, 1, 4), Triple(1, 2, 3), Triple(4, null, 5))
        val expectedOrder = arrayOf('h', 'e', 'd', 'f', 'i', 'j')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependencies))
    }

    @Test
    fun `sonsHeightDiff values are correct after entry was added (rotateLeft changes root)`() {
        val tree = makeTreeForHaveToChangeRootByRotateLeftPutTest()
        tree.put('j', 'J')
        assert(isSonsHeightDiffCorrect(tree))
    }
}
