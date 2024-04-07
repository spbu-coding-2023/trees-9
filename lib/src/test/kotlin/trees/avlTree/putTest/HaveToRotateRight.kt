package trees.avlTree.putTest

import trees.avlTree.AuxiliaryFunctions.isTreeConsistsOf
import trees.avlTree.AuxiliaryFunctions.isTreeSStructureThat
import trees.avlTree.AuxiliaryFunctions.isSonsHeightDiffCorrect
import main.vertexes.AVLVertex
import org.junit.jupiter.api.Test
import trees.avlTree.AVLTreeForTest
class HaveToRotateRight {
    private fun makeTreeForHaveToRotateRightPutTest(): AVLTreeForTest<Char, Char> {
        val leftSonSLeftSon = AVLVertex('c', 'C', AVLVertex('b', 'B'), AVLVertex('d', 'D'))
        val leftSon = AVLVertex('f', 'F', leftSonSLeftSon, AVLVertex('e', 'E'), 1)
        val root = AVLVertex('h', 'H', leftSon, AVLVertex('i', 'I', null, AVLVertex('j', 'J'), -1), 1)
        return AVLTreeForTest(root, 8L)
    }

    @Test
    fun `content is correct after entry was added `() {
        val tree = makeTreeForHaveToRotateRightPutTest()
        tree.put('a', 'A')
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
        val tree = makeTreeForHaveToRotateRightPutTest()
        tree.put('a', 'A')
        assert(tree.size() == 9L)
    }

    @Test
    fun `structure is correct after entry was added`() {
        val tree = makeTreeForHaveToRotateRightPutTest()
        tree.put('a', 'A')
        val expectedDependencies =
                listOf(
                        Triple(0, 1, 7),
                        Triple(1, 2, 4),
                        Triple(2, 3, null),
                        Triple(4, 5, 6),
                        Triple(7, null, 8),
                )
        val expectedOrder = arrayOf('h', 'c', 'b', 'a', 'f', 'd', 'e', 'i', 'j')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependencies))
    }

    @Test
    fun `sonsHeightDiff values are correct after entry was added`() {
        val tree = makeTreeForHaveToRotateRightPutTest()
        tree.put('a', 'A')
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
    fun `size increased by 1 after entry was added (rotateRight changes root)`() {
        val tree = makeTreeForHaveToChangeRootByRotateRightPutTest()
        tree.put('a', 'A')
        assert(tree.size() == 6L)
    }

    @Test
    fun `structure is correct after entry was added (rotateRight changes root)`() {
        val tree = makeTreeForHaveToChangeRootByRotateRightPutTest()
        tree.put('a', 'A')
        val expectedDependencies = listOf(Triple(0, 1, 3), Triple(1, 2, null), Triple(3, 4, 5))
        val expectedOrder = arrayOf('c', 'b', 'a', 'f', 'd', 'e')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependencies))
    }

    @Test
    fun `sonsHeightDiff values are correct after entry was added (rotateRight changes root)`() {
        val tree = makeTreeForHaveToChangeRootByRotateRightPutTest()
        tree.put('a', 'A')
        assert(isSonsHeightDiffCorrect(tree))
    }
}
