package trees.avlTree.putTest

import org.junit.jupiter.api.Test
import trees.avlTree.AVLTreeForTest
import trees.avlTree.AuxiliaryFunctions.isSonsHeightDiffCorrect
import trees.avlTree.AuxiliaryFunctions.isTreeConsistsOf
import trees.avlTree.AuxiliaryFunctions.isTreeSStructureThat
import vertexes.AVLVertex

class HaveToDoBigRotation {
    private fun makeTreeForHaveToDoBigLeftRotationPutTest(): AVLTreeForTest<Char, Char> {
        val rightSonSRightSon = AVLVertex('i', 'I', AVLVertex('g', 'G'), AVLVertex('j', 'J'))
        val rightSon = AVLVertex('e', 'E', AVLVertex('d', 'D'), rightSonSRightSon, -1)
        val root = AVLVertex('c', 'C', AVLVertex('b', 'B', AVLVertex('a', 'A'), null, 1), rightSon, -1)
        return AVLTreeForTest(root, 8L)
    }

    // (1) - add grandson's right son, (2) add grandson's left son
    @Test
    fun `content is correct after entry was added (big left rotation) (1)`() {
        val tree = makeTreeForHaveToDoBigLeftRotationPutTest()
        tree.put('f', 'F')
        val expectedContent =
            setOf(
                'a' to 'A',
                'b' to 'B',
                'c' to 'C',
                'd' to 'D',
                'e' to 'E',
                'g' to 'G',
                'f' to 'F',
                'i' to 'I',
                'j' to 'J',
            )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `content is correct after entry was added (big left rotation) (2)`() {
        val tree = makeTreeForHaveToDoBigLeftRotationPutTest()
        tree.put('h', 'H')
        val expectedContent =
            setOf(
                'a' to 'A',
                'b' to 'B',
                'c' to 'C',
                'd' to 'D',
                'e' to 'E',
                'h' to 'H',
                'g' to 'G',
                'i' to 'I',
                'j' to 'J',
            )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size increased by 1 after entry was added (big left rotation) (1)`() {
        val tree = makeTreeForHaveToDoBigLeftRotationPutTest()
        tree.put('f', 'F')
        assert(tree.size() == 9L)
    }

    @Test
    fun `size increased by 1 after entry was added (big left rotation) (2)`() {
        val tree = makeTreeForHaveToDoBigLeftRotationPutTest()
        tree.put('h', 'H')
        assert(tree.size() == 9L)
    }

    @Test
    fun `structure is correct after entry was added (big left rotation) (1)`() {
        val tree = makeTreeForHaveToDoBigLeftRotationPutTest()
        tree.put('f', 'F')
        val expectedDependencies =
            listOf(
                Triple(0, 1, 3),
                Triple(1, 2, null),
                Triple(3, 4, 7),
                Triple(4, 5, 6),
                Triple(7, null, 8),
            )
        val expectedOrder = arrayOf('c', 'b', 'a', 'g', 'e', 'd', 'f', 'i', 'j')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependencies))
    }

    @Test
    fun `structure is correct after entry was added (big left rotation) (2)`() {
        val tree = makeTreeForHaveToDoBigLeftRotationPutTest()
        tree.put('h', 'H')
        val expectedDependencies =
            listOf(
                Triple(0, 1, 3),
                Triple(1, 2, null),
                Triple(3, 4, 6),
                Triple(4, 5, null),
                Triple(6, 7, 8),
            )
        val expectedOrder = arrayOf('c', 'b', 'a', 'g', 'e', 'd', 'i', 'h', 'j')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependencies))
    }

    @Test
    fun `sonsHeightDiff values are correct after entry was added (big left rotation) (1)`() {
        val tree = makeTreeForHaveToDoBigLeftRotationPutTest()
        tree.put('f', 'F')
        assert(isSonsHeightDiffCorrect(tree))
    }

    @Test
    fun `sonsHeightDiff values are correct after entry was added (big left rotation) (2)`() {
        val tree = makeTreeForHaveToDoBigLeftRotationPutTest()
        tree.put('h', 'H')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForHaveToDoBigRightRotationPutTest(): AVLTreeForTest<Char, Char> {
        val leftSonSLeftSon = AVLVertex('b', 'B', AVLVertex('a', 'A'), AVLVertex('d', 'D'))
        val leftSon = AVLVertex('f', 'F', leftSonSLeftSon, AVLVertex('g', 'G'), 1)
        val root = AVLVertex('h', 'H', leftSon, AVLVertex('i', 'I', null, AVLVertex('j', 'J'), -1), 1)
        return AVLTreeForTest(root, 8L)
    }

    // (1) - add grandson's left son, (2) add grandson's right son
    @Test
    fun `content is correct after entry was added (big right rotation) (1)`() {
        val tree = makeTreeForHaveToDoBigRightRotationPutTest()
        tree.put('c', 'C')
        val expectedContent =
            setOf(
                'a' to 'A',
                'b' to 'B',
                'c' to 'C',
                'd' to 'D',
                'h' to 'H',
                'g' to 'G',
                'f' to 'F',
                'i' to 'I',
                'j' to 'J',
            )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `content is correct after entry was added (big right rotation) (2)`() {
        val tree = makeTreeForHaveToDoBigRightRotationPutTest()
        tree.put('e', 'E')
        val expectedContent =
            setOf(
                'a' to 'A',
                'b' to 'B',
                'f' to 'F',
                'd' to 'D',
                'e' to 'E',
                'h' to 'H',
                'g' to 'G',
                'i' to 'I',
                'j' to 'J',
            )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size increased by 1 after entry was added (big right rotation) (1)`() {
        val tree = makeTreeForHaveToDoBigRightRotationPutTest()
        tree.put('c', 'C')
        assert(tree.size() == 9L)
    }

    @Test
    fun `size increased by 1 after entry was added (big right rotation) (2)`() {
        val tree = makeTreeForHaveToDoBigRightRotationPutTest()
        tree.put('e', 'E')
        assert(tree.size() == 9L)
    }

    @Test
    fun `structure is correct after entry was added (big right rotation) (1)`() {
        val tree = makeTreeForHaveToDoBigRightRotationPutTest()
        tree.put('c', 'C')
        val expectedDependencies =
            listOf(
                Triple(0, 1, 7),
                Triple(7, null, 8),
                Triple(1, 2, 5),
                Triple(5, null, 6),
                Triple(2, 3, 4),
            )
        val expectedOrder = arrayOf('h', 'd', 'b', 'a', 'c', 'f', 'g', 'i', 'j')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependencies))
    }

    @Test
    fun `structure is correct after entry was added (big right rotation)  (2)`() {
        val tree = makeTreeForHaveToDoBigRightRotationPutTest()
        tree.put('e', 'E')
        val expectedDependencies =
            listOf(
                Triple(0, 1, 7),
                Triple(7, null, 8),
                Triple(1, 2, 4),
                Triple(2, 3, null),
                Triple(4, 5, 6),
            )
        val expectedOrder = arrayOf('h', 'd', 'b', 'a', 'f', 'e', 'g', 'i', 'j')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependencies))
    }

    @Test
    fun `sonsHeightDiff values are correct after entry was added (big right rotation) (1)`() {
        val tree = makeTreeForHaveToDoBigRightRotationPutTest()
        tree.put('c', 'C')
        assert(isSonsHeightDiffCorrect(tree))
    }

    @Test
    fun `sonsHeightDiff values are correct after entry was added (big right rotation) (2)`() {
        val tree = makeTreeForHaveToDoBigLeftRotationPutTest()
        tree.put('e', 'E')
        assert(isSonsHeightDiffCorrect(tree))
    }
}
