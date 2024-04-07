package trees.avlTree.removeTest.withoutBalancing

import trees.avlTree.AuxiliaryFunctions.isTreeConsistsOf
import trees.avlTree.AuxiliaryFunctions.isTreeSStructureThat
import trees.avlTree.AuxiliaryFunctions.isSonsHeightDiffCorrect
import vertexes.AVLVertex
import org.junit.jupiter.api.Test
import trees.avlTree.AVLTreeForTest

class VertexHadTwoSons {

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
    fun `remove by key of left son with both sons returns due value`() {
        val tree = makeTreeForRemoveSonWithBothSons()
        assert(tree.remove('f') == 'F')
    }

    @Test
    fun `content is correct after left son with both sons was deleted`() {
        val tree = makeTreeForRemoveSonWithBothSons()
        tree.remove('f')
        val expectedContent =
                setOf(
                        'a' to 'A',
                        'b' to 'B',
                        'c' to 'C',
                        'e' to 'E',
                        'i' to 'I',
                        'z' to 'Z',
                        'k' to 'K',
                        'n' to 'N',
                        'u' to 'U',
                        'q' to 'Q',
                        's' to 'S',
                        'w' to 'W',
                )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size decreased by 1 after left son with both sons was deleted`() {
        val tree = makeTreeForRemoveSonWithBothSons()
        tree.remove('f')
        assert(tree.size() == 12L)
    }

    @Test
    fun `structure is correct after left son with both sons was deleted`() {
        val tree = makeTreeForRemoveSonWithBothSons()
        tree.remove('f')
        val expectedDependencies =
                listOf(
                        Triple(0, 1, 7),
                        Triple(1, 2, 5),
                        Triple(2, 3, 4),
                        Triple(5, null, 6),
                        Triple(7, 8, 10),
                        Triple(10, null, 11),
                )
        val expectedOrder = arrayOf('n', 'e', 'b', 'a', 'c', 'i', 'k', 'u', 'q', 's', 'w', 'z')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependencies))
    }

    @Test
    fun `sonsHeightDiff values are correct after LSon with both sons was deleted`() {
        val tree = makeTreeForRemoveSonWithBothSons()
        tree.remove('f')
        assert(isSonsHeightDiffCorrect(tree))
    }

    @Test
    fun `remove by key of right son with both sons returns due value`() {
        val tree = makeTreeForRemoveSonWithBothSons()
        assert(tree.remove('u') == 'U')
    }

    @Test
    fun `content is correct after right son with both sons was deleted`() {
        val tree = makeTreeForRemoveSonWithBothSons()
        tree.remove('u')
        val expectedContent =
                setOf(
                        'a' to 'A',
                        'b' to 'B',
                        'c' to 'C',
                        'e' to 'E',
                        'i' to 'I',
                        'z' to 'Z',
                        'k' to 'K',
                        'n' to 'N',
                        'f' to 'F',
                        'q' to 'Q',
                        's' to 'S',
                        'w' to 'W',
                )
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size decreased by 1 after right son with both sons was deleted`() {
        val tree = makeTreeForRemoveSonWithBothSons()
        tree.remove('u')
        assert(tree.size() == 12L)
    }

    @Test
    fun `structure is correct after right son with both sons was deleted`() {
        val tree = makeTreeForRemoveSonWithBothSons()
        tree.remove('u')
        val expectedDependencies =
                listOf(
                        Triple(0, 1, 8),
                        Triple(1, 2, 6),
                        Triple(2, 3, 4),
                        Triple(6, null, 7),
                        Triple(4, 5, null),
                        Triple(8, 9, 10),
                        Triple(10, null, 11),
                )
        val expectedOrder = arrayOf('n', 'f', 'b', 'a', 'e', 'c', 'i', 'k', 's', 'q', 'w', 'z')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependencies))
    }

    @Test
    fun `sonsHeightDiff values are correct after RSon with both sons was deleted`() {
        val tree = makeTreeForRemoveSonWithBothSons()
        tree.remove('u')
        assert(isSonsHeightDiffCorrect(tree))
    }

    @Test
    fun `remove fun return null if entry's key isn't exists(2)`() {
        val tree = makeTreeForRemoveSonWithBothSons()
        assert(tree.remove('x') == null)
    }

    private fun makeTreeForRemoveRootWithBothSonsTest(): AVLTreeForTest<Char, Int> {
        val leftSon = AVLVertex('b', 2, AVLVertex('a', 1), AVLVertex('e', 5), 0)
        val rightSon = AVLVertex('i', 9, null, AVLVertex('k', 11), -1)
        val root = AVLVertex('f', 6, leftSon, rightSon, 0)
        return AVLTreeForTest(root, 6L)
    }

    @Test
    fun `remove by key of  root with both sons returns due value`() {
        val tree = makeTreeForRemoveRootWithBothSonsTest()
        assert(tree.remove('f') == 6)
    }

    @Test
    fun `content is correct after root with both sons was deleted`() {
        val tree = makeTreeForRemoveRootWithBothSonsTest()
        tree.remove('f')
        val expectedContent = setOf('a' to 1, 'b' to 2, 'e' to 5, 'i' to 9, 'k' to 11)
        assert(isTreeConsistsOf(expectedContent, tree))
    }

    @Test
    fun `size decreased by 1 after root with both sons was deleted`() {
        val tree = makeTreeForRemoveRootWithBothSonsTest()
        tree.remove('f')
        assert(tree.size() == 5L)
    }

    @Test
    fun `structure is correct after root with both sons was deleted`() {
        val tree = makeTreeForRemoveRootWithBothSonsTest()
        tree.remove('f')
        val expectedDependencies = listOf(Triple(0, 1, 3), Triple(1, 2, null), Triple(3, null, 4))
        val expectedOrder = arrayOf('e', 'b', 'a', 'i', 'k')
        assert(isTreeSStructureThat(tree, expectedOrder, expectedDependencies))
    }

    @Test
    fun `sonsHeightDiff values are correct after root with both sons was deleted`() {
        val tree = makeTreeForRemoveRootWithBothSonsTest()
        tree.remove('f')
        assert(isSonsHeightDiffCorrect(tree))
    }
}
