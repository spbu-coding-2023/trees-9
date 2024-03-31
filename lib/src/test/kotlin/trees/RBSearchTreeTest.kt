package trees
import main.trees.RBSearchTree
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RBSearchTreeTest {
    private lateinit var rbTree: RBSearchTree<Int, String>

    @BeforeEach
    fun setup(){
        rbTree = RBSearchTree()
    }

    @Test
    fun `put a root to the tree`(){
        rbTree.put(7, "hi")
        assertEquals(rbTree.get(7), "hi")
    }

    @Test
    fun `put new vertex to the left`(){
        rbTree.put(7, "hi")
        rbTree.put(5, "my")
        assertEquals(rbTree.get(5), "my")
    }

    @Test
    fun `put new vertex to the right`(){
        rbTree.put(7, "hi")
        rbTree.put(5, "my")
        rbTree.put(9, "name")
        assertEquals(rbTree.get(9), "name")
    }

}