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
        rbTree.put(9, "name")
        assertEquals(rbTree.get(9), "name")
    }
    @Test
    fun `put existing vertex without replace`(){
        rbTree.put(7, "hi")
        rbTree.put(7, "is", false)
        assertEquals(rbTree.get(7), "hi")
    }
    @Test
    fun `put existing vertex with replace`(){
        rbTree.put(7, "hi")
        rbTree.put(7, "Slim")
        assertEquals(rbTree.get(7), "Slim")
    }

    @Test
    fun `balance after put - uncle is rightSon and red`(){
        rbTree.put(7, "hi")
        rbTree.put(5, "my")
        rbTree.put(9, "name")
        rbTree.put(6, "is")
        assertEquals(rbTree.get(6), "is")
    }

    @Test
    fun `balance after put - uncle is rightSon and black + newVertex is leftSon`(){
        rbTree.put(7, "hi")
        rbTree.put(5, "my")
        rbTree.put(3, "name")
        assertEquals(rbTree.get(3), "name")
    }

    @Test
    fun `balance after put - uncle is rightSon and black + newVertex is rightSon`(){
        rbTree.put(7, "hi")
        rbTree.put(5, "my")
        rbTree.put(6, "name")
        assertEquals(rbTree.get(6), "name")
    }

    @Test
    fun `balance after put - uncle is leftSon and red`(){
        rbTree.put(7, "hi")
        rbTree.put(5, "my")
        rbTree.put(9, "name")
        rbTree.put(8, "is")
        assertEquals(rbTree.get(8), "is")
    }

    @Test
    fun `balance after put - uncle is leftSon and black + newVertex is rightSon`(){
        rbTree.put(7, "hi")
        rbTree.put(9, "my")
        rbTree.put(10, "name")
        assertEquals(rbTree.get(10), "name")
    }

    @Test
    fun `balance after put - uncle is leftSon and black + newVertex is leftSon`(){
        rbTree.put(7, "hi")
        rbTree.put(9, "my")
        rbTree.put(8, "name")
        assertEquals(rbTree.get(8), "name")
    }

}