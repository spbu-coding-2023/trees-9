package trees.avlTree.putTest

import trees.avlTree.AuxiliaryFunctions.isTreeConsistsOf
import main.vertexes.AVLVertex
import org.junit.jupiter.api.Test
import trees.avlTree.AVLTreeForTest

class Other {
    private fun makeSize1Tree(): AVLTreeForTest<Char, Char> {
        return AVLTreeForTest(AVLVertex('b', '+'), 1L)
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
}