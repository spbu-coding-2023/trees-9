package trees.RBSearchTree

import main.trees.RBSearchTree
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RBSearchTreeTest {
    private lateinit var rbTree: RBSearchTree<Int, String>
    private lateinit var expectedResult: List<Pair<Int, String>>
    private var result: MutableList<Pair<Int, String>> = mutableListOf()

    @BeforeEach
    fun setup() {
        rbTree = RBSearchTree()
    }

    @AfterEach
    fun end() {
        for (el in rbTree) result.add(el)
        assertEquals(expectedResult, result)
    }

    @Test
    fun `put a root to the tree`() {
        rbTree.put(7, "hi")
        expectedResult = listOf(Pair(7, "hi"))
    }

    @Test
    fun `put new vertex to the left`() {
        rbTree.put(7, "hi")
        rbTree.put(5, "my")
        expectedResult = listOf(Pair(7, "hi"), Pair(5, "my"))
    }

    @Test
    fun `put new vertex to the right`() {
        rbTree.put(7, "hi")
        rbTree.put(9, "name")
        expectedResult = listOf(Pair(7, "hi"), Pair(9, "name"))
    }

    @Test
    fun `put existing vertex without replace`() {
        rbTree.put(7, "hi")
        rbTree.put(7, "is", false)
        expectedResult = listOf(Pair(7, "hi"))
    }

    @Test
    fun `put existing vertex with replace`() {
        rbTree.put(7, "hi")
        rbTree.put(7, "Slim")
        expectedResult = listOf(Pair(7, "Slim"))
    }

    @Test
    fun `balance after put - uncle is rightSon and red`() {
        rbTree.put(7, "hi")
        rbTree.put(5, "my")
        rbTree.put(9, "name")
        rbTree.put(6, "is")
        expectedResult = listOf(Pair(7, "hi"), Pair(9, "name"), Pair(5, "my"), Pair(6, "is"))
    }

    @Test
    fun `balance after put - uncle is rightSon and black + newVertex is leftSon`() {
        rbTree.put(7, "hi")
        rbTree.put(5, "my")
        rbTree.put(3, "name")
        expectedResult = listOf(Pair(5, "my"), Pair(7, "hi"), Pair(3, "name"))
    }

    @Test
    fun `balance after put - uncle is rightSon and black + newVertex is rightSon`() {
        rbTree.put(7, "hi")
        rbTree.put(5, "my")
        rbTree.put(6, "name")
        expectedResult = listOf(Pair(6, "name"), Pair(7, "hi"), Pair(5, "my"))
    }

    @Test
    fun `balance after put - uncle is leftSon and red`() {
        rbTree.put(7, "hi")
        rbTree.put(5, "my")
        rbTree.put(9, "name")
        rbTree.put(8, "is")
        expectedResult = listOf(Pair(7, "hi"), Pair(9, "name"), Pair(8, "is"), Pair(5, "my"))
    }

    @Test
    fun `balance after put - uncle is leftSon and black + newVertex is rightSon`() {
        rbTree.put(7, "hi")
        rbTree.put(9, "my")
        rbTree.put(10, "name")
        expectedResult = listOf(Pair(9, "my"), Pair(10, "name"), Pair(7, "hi"))
    }

    @Test
    fun `balance after put - uncle is leftSon and black + newVertex is leftSon`() {
        rbTree.put(7, "hi")
        rbTree.put(9, "my")
        rbTree.put(8, "name")
        expectedResult = listOf(Pair(8, "name"), Pair(9, "my"), Pair(7, "hi"))
    }

    @Test
    fun `remove non-existent vertex`() {
        rbTree.put(7, "hi")
        rbTree.remove(9)
        expectedResult = listOf(Pair(7, "hi"))
    }

    @Test
    fun `delete red vertex with 0 children`() {
        rbTree.put(7, "hi")
        rbTree.put(9, "my")
        rbTree.remove(9)
        expectedResult = listOf(Pair(7, "hi"))
    }

    @Test
    fun `delete black vertex with 1 rightSon`() {
        rbTree.put(7, "hi")
        rbTree.put(9, "my")
        rbTree.remove(7)
        expectedResult = listOf(Pair(9, "my"))
    }

    @Test
    fun `delete black vertex with 1 leftSon`() {
        rbTree.put(7, "hi")
        rbTree.put(5, "my")
        rbTree.remove(7)
        expectedResult = listOf(Pair(5, "my"))
    }

    @Test
    fun `delete black vertex with 2 children`() {
        rbTree.put(7, "hi")
        rbTree.put(4, "my")
        rbTree.put(10, "name")
        rbTree.remove(7)
        expectedResult = listOf(Pair(10, "name"), Pair(4, "my"))
    }

    @Test
    fun `delete red vertex with 2 children`() {
        rbTree.put(7, "hi")
        rbTree.put(4, "my")
        rbTree.put(10, "name")
        rbTree.put(3, "is")
        rbTree.put(5, "chka")
        rbTree.put(9, "chka")
        rbTree.put(12, "chka")
        rbTree.put(11, "Slim")
        rbTree.put(13, "Shady")
        rbTree.remove(10)
        expectedResult =
            listOf(
                Pair(7, "hi"),
                Pair(11, "Slim"),
                Pair(12, "chka"),
                Pair(13, "Shady"),
                Pair(9, "chka"),
                Pair(4, "my"),
                Pair(5, "chka"),
                Pair(3, "is"),
            )
    }

    @Test
    fun `balance after remove - brother is right and black, brother's rightSon - red`() {
        rbTree.put(7, "hi")
        rbTree.put(4, "my")
        rbTree.put(10, "name")
        rbTree.put(3, "is")
        rbTree.put(5, "chka")
        rbTree.put(9, "chka")
        rbTree.put(12, "chka")
        rbTree.put(11, "Slim")
        rbTree.put(13, "Shady")
        rbTree.remove(9)
        expectedResult =
            listOf(
                Pair(7, "hi"),
                Pair(12, "chka"),
                Pair(13, "Shady"),
                Pair(10, "name"),
                Pair(11, "Slim"),
                Pair(4, "my"),
                Pair(5, "chka"),
                Pair(3, "is"),
            )
    }

    @Test
    fun `balance after remove - brother is right and black, brother's leftSon - red (rightSon - black)`() {
        rbTree.put(7, "hi")
        rbTree.put(4, "my")
        rbTree.put(10, "name")
        rbTree.put(3, "is")
        rbTree.put(5, "chka")
        rbTree.put(9, "chka")
        rbTree.put(12, "Slim")
        rbTree.put(11, "Shady")
        rbTree.remove(9)
        expectedResult =
            listOf(
                Pair(7, "hi"),
                Pair(11, "Shady"),
                Pair(12, "Slim"),
                Pair(10, "name"),
                Pair(4, "my"),
                Pair(5, "chka"),
                Pair(3, "is"),
            )
    }

    @Test
    fun `balance after remove - brother is right and black, both sons - black`() {
        rbTree.put(7, "hi")
        rbTree.put(4, "my")
        rbTree.put(10, "name")
        rbTree.put(9, "is")
        rbTree.put(12, "Slim")
        rbTree.put(11, "Shady")
        rbTree.remove(11)

        // now vertexes with keys 9 and 12 are black
        rbTree.remove(9)

        expectedResult = listOf(Pair(7, "hi"), Pair(10, "name"), Pair(12, "Slim"), Pair(4, "my"))
    }

    @Test
    fun `balance after remove - right brother is red`() {
        rbTree.put(60, "sixty")
        rbTree.put(33, "thirty-three")
        rbTree.put(84, "eighty-four")
        rbTree.put(15, "fifteen")
        rbTree.put(51, "fifty-one")
        rbTree.put(65, "sixty-five")
        rbTree.put(96, "ninety-six")
        rbTree.put(34, "thirty-four")
        rbTree.put(52, "Alblack")
        rbTree.put(94, "ninety-four")
        rbTree.put(97, "ninety-seven")
        rbTree.put(95, "ninety-five")

        // now vertex with key 96 is red, with key 65 - black
        rbTree.remove(65)

        expectedResult =
            listOf(
                Pair(60, "sixty"),
                Pair(96, "ninety-six"),
                Pair(97, "ninety-seven"),
                Pair(94, "ninety-four"),
                Pair(95, "ninety-five"),
                Pair(84, "eighty-four"),
                Pair(33, "thirty-three"),
                Pair(51, "fifty-one"),
                Pair(52, "Alblack"),
                Pair(34, "thirty-four"),
                Pair(15, "fifteen"),
            )
    }

    @Test
    fun `balance after remove - brother is left and black, brother's rightSon - red`() {
        rbTree.put(7, "hi")
        rbTree.put(4, "my")
        rbTree.put(10, "name")
        rbTree.put(2, "is")
        rbTree.put(5, "chka")
        rbTree.put(9, "chka")
        rbTree.put(12, "chka")
        rbTree.put(1, "Slim")
        rbTree.put(3, "Shady")
        rbTree.remove(5)
        expectedResult =
            listOf(
                Pair(7, "hi"),
                Pair(10, "name"),
                Pair(12, "chka"),
                Pair(9, "chka"),
                Pair(2, "is"),
                Pair(4, "my"),
                Pair(3, "Shady"),
                Pair(1, "Slim"),
            )
    }

    @Test
    fun `balance after remove - brother is left and black, brother's rightSon - red (leftSon - black)`() {
        rbTree.put(7, "hi")
        rbTree.put(4, "my")
        rbTree.put(10, "name")
        rbTree.put(2, "is")
        rbTree.put(5, "chka")
        rbTree.put(9, "chka")
        rbTree.put(12, "Slim")
        rbTree.put(3, "Shady")
        rbTree.remove(5)
        expectedResult =
            listOf(
                Pair(7, "hi"),
                Pair(10, "name"),
                Pair(12, "Slim"),
                Pair(9, "chka"),
                Pair(3, "Shady"),
                Pair(4, "my"),
                Pair(2, "is"),
            )
    }

    @Test
    fun `balance after remove - brother is left and black, both sons - black`() {
        rbTree.put(7, "hi")
        rbTree.put(4, "my")
        rbTree.put(10, "name")
        rbTree.put(2, "is")
        rbTree.put(5, "Slim")
        rbTree.put(3, "Shady")
        rbTree.remove(3)

        // now vertexes with keys 2 and 5 are black
        rbTree.remove(5)

        expectedResult = listOf(Pair(7, "hi"), Pair(10, "name"), Pair(4, "my"), Pair(2, "is"))
    }

    @Test
    fun `balance after remove - left brother is red`() {
        rbTree.put(60, "sixty")
        rbTree.put(33, "thirty-three")
        rbTree.put(84, "eighty-four")
        rbTree.put(15, "fifteen")
        rbTree.put(51, "fifty-one")
        rbTree.put(65, "sixty-five")
        rbTree.put(96, "ninety-six")
        rbTree.put(5, "five")
        rbTree.put(27, "twenty-seven")
        rbTree.put(61, "sixty-one")
        rbTree.put(69, "sixty-nine")
        rbTree.put(17, "seventeen")

        // now vertex with key 15 is red, with key 51 - black
        rbTree.remove(51)

        expectedResult =
            listOf(
                Pair(60, "sixty"),
                Pair(84, "eighty-four"),
                Pair(96, "ninety-six"),
                Pair(65, "sixty-five"),
                Pair(69, "sixty-nine"),
                Pair(61, "sixty-one"),
                Pair(15, "fifteen"),
                Pair(27, "twenty-seven"),
                Pair(33, "thirty-three"),
                Pair(17, "seventeen"),
                Pair(5, "five"),
            )
    }

    @Test
    fun `test secondary constructor`() {
        val testMap = mapOf(3 to "three", 1 to "one", 2 to "two")
        rbTree = RBSearchTree(testMap)
        expectedResult = listOf(Pair(2, "two"), Pair(3, "three"), Pair(1, "one"))
    }
}
