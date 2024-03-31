import AVLTreeForTest
import main.vertexes.AVLVertex
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.condition.DisabledIf
import kotlin.collections.hashMapOf

class AVLTreeTest {

    private fun makeEmptyTree() : AVLTreeForTest<Int, Char> {return AVLTreeForTest<Int, Char>()}

    //putTest

    @Test
    fun `entry became root after it was added to empty tree`() {
        val tree = makeEmptyTree()
        tree.put(0, '0')
        val root = tree.getRootT()
        assert((root?.key to root?.value) == (0 to '0'))
    }

    @Test
    fun `size equals 1 after entry was added to empty tree`() {
        val tree = makeEmptyTree()
        tree.put(0, '0')
        assert(tree.size() == 1L)
    }

    private fun makeSize1Tree() : AVLTreeForTest<Char, Char> {
        return AVLTreeForTest(AVLVertex('b', '+'), 1L) // init tree by root and size (don't use put)
    }

    @Test
    fun `entry with larger key became right son after it was added to size 1 tree`() {
        val tree = makeSize1Tree()
        tree.put('c', '+')
        val root = tree.getRootT()
        assert((root?.rightSon?.key to root?.rightSon?.value) == ('c' to '+'))
    }
    
    @Test
    fun `entry with lesser key became left son after it was added to size 1 tree`() {
        val tree = makeSize1Tree()
        tree.put('a', '-')
        val root = tree.getRootT()
        assert((root?.leftSon?.key to root?.leftSon?.value) == ('a' to '-'))
    }

    @Test
    fun `root's sonsHeightDiff decreased by 1 after entery with larger key was added to size 1 tree`(){
        val tree = makeSize1Tree()
        tree.put('c', '+')
        val root = tree.getRootT()
        assert(root?.sonsHeightDiff == -1)
    }

    @Test
    fun `root's sonsHeightDiff increased by 1 after entery with lesser key was added to size 1 tree`(){
        val tree = makeSize1Tree()
        tree.put('a', '-')
        val root = tree.getRootT()
        assert(root?.sonsHeightDiff == 1)
    }

    @Test
    fun `size equals 2 after entry with larger key was added to size 1 tree`() {
        val tree = makeSize1Tree()
        tree.put('c', '+')
        assert(tree.size() == 2L)
    }

    @Test
    fun `size equals 2 after entry with lesser key was added to size 1 tree`() {
        val tree = makeSize1Tree()
        tree.put('a', '-')
        assert(tree.size() == 2L)
    }

    private  fun makeTreeForNeedNotBalanceingTest() : AVLTreeForTest<Int, Char> {
        return AVLTreeForTest(AVLVertex(4, 'A', AVLVertex(1, 'I'), AVLVertex(5, 'S')), 3L)
    }

    private fun <K,V> isTreeConsistsOf(expectedContent : Set<Pair<K,V>>, tree: AVLTreeForTest<K,V> ) : Boolean {
        val vertexes = tree.getVertexesInDFSOrder()
        val pairsFromVertexes = (Array(vertexes.size){i -> (vertexes[i].key to vertexes[i].value)}).toSet()
        return pairsFromVertexes == expectedContent
    }

    @Test
    fun `content is correct after entery was added (needn't balancing)`() {
        val tree = makeTreeForNeedNotBalanceingTest()
        tree.put(0, 'O')
        assert(isTreeConsistsOf(setOf(0 to 'O', 1 to 'I', 5 to 'S', 4 to 'A'), tree))
    }

    @Test
    fun `size increased by 1 after added to 'size 2+' tree (needn't balancing)`() {
        val tree = makeTreeForNeedNotBalanceingTest()
        tree.put(0, 'O')
        assert(tree.size() == 4L)
    }

    private fun <K,V> isTreeSStructureThat(tree : AVLTreeForTest<K,V>,
     order : Array<K>, deps : List<Triple<Int,Int?,Int?>>) : Boolean {
        // Tiple consists of indexes in order of (1)vertex, one's (2)leftSon and (3)RightSon
        val vertexes = tree.getVertexesInDFSOrder()
        if (vertexes.size != order.size) return false
        for (i in order.indices)
            if (order[i] != vertexes[i].key)  return false
        for (dep in deps){
            if (dep.component2() != null)
                if (vertexes[dep.component1()].leftSon != vertexes[dep.component2() as Int])
                    return false
            if (dep.component3() != null)
                if (vertexes[dep.component1()].rightSon != vertexes[dep.component3() as Int])
                    return false
        }
        return true
    }

    @Test
    fun `structure is correct after added to 'size 2+' tree (needn't balancing)`() {
        val tree = makeTreeForNeedNotBalanceingTest()
        tree.put(0, 'O')
        val expectedDependences = listOf(Triple(0, 1, 3), Triple(1, 2, null))
        assert(isTreeSStructureThat(tree, arrayOf(4, 1, 0, 5), expectedDependences))
    }

    private fun <K,V> isSonsHeightDiffCorrect(tree : AVLTreeForTest<K,V>) : Boolean {
        val vertexes = tree.getVertexesInDFSOrder()
        val heights = tree.getHeights()
        if (vertexes.size != heights.size) return false
        for (vertex in vertexes) {
            val expectedSonsHeightDiff = 
            when ((vertex.leftSon == null) to (vertex.rightSon == null)) {
                true to true -> 0
                true to false -> -1 - (heights[(vertex.rightSon as AVLVertex<K,V>).key] ?: return false)
                false to true -> 1 + (heights[(vertex.leftSon as AVLVertex<K,V>).key] ?: return false)
                else -> {
                    val heightOfLeftSubtree = heights[(vertex.leftSon as AVLVertex<K,V>).key] ?: return false
                    val heightOfRightSubtree = heights[(vertex.rightSon as AVLVertex<K,V>).key] ?: return false
                    heightOfLeftSubtree - heightOfRightSubtree
                }
            }
            if (expectedSonsHeightDiff != vertex.sonsHeightDiff) return false
        }
        return true
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after entery was added to 'size 2+' tree (needn't balancing)`() {
        val tree = makeTreeForNeedNotBalanceingTest()
        tree.put(0, 'O')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForHaveToRotateLeftPutTest() : AVLTreeForTest<Char, Char> {
        val rightSonSRightSon = AVLVertex('h', 'H', AVLVertex('f', 'F'), AVLVertex('i', 'I'))
        val rightSon = AVLVertex('e', 'E', AVLVertex('d','D'), rightSonSRightSon, -1)
        val root = AVLVertex('c', 'C', AVLVertex('b', 'B', AVLVertex('a', 'A'), null, 1), rightSon, -1) 
        return AVLTreeForTest(root, 8L)
    }

    @Test
    fun `content is correct after entery was added (have to rotate left)`() {
        val tree = makeTreeForHaveToRotateLeftPutTest()
        tree.put('j', 'J')
        val expextedContent = setOf('a' to 'A','b' to 'B', 'c' to 'C',
         'd' to 'D', 'e' to 'E', 'h' to 'H', 'f' to 'F', 'i' to 'I', 'j' to 'J')
        assert(isTreeConsistsOf(expextedContent, tree))
    }


    @Test
    fun `size increased by 1 after added (have to rotate left)`() {
        val tree = makeTreeForHaveToRotateLeftPutTest()
        tree.put('j', 'J')
        assert(tree.size() == 9L)
    }

    @Test
    fun `structure is correct after added (have to rotate left)`() {
        val tree = makeTreeForHaveToRotateLeftPutTest()
        tree.put('j', 'J')
        val expectedDependences = listOf(Triple(0, 1, 3), Triple(1, 2, null), Triple(3, 4, 7),
         Triple(4, 5, 6), Triple(7, null, 8))
        val expextedOrder = arrayOf('c', 'b', 'a', 'h', 'e', 'd', 'f', 'i', 'j' )
        assert(isTreeSStructureThat(tree,expextedOrder, expectedDependences))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after added (have to rotate left)`() {
        val tree = makeTreeForHaveToRotateLeftPutTest()
        tree.put('j', 'J')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForHaveToRotateRightPutTest() : AVLTreeForTest<Char, Char> {
        val leftSonSLeftSon = AVLVertex('c', 'C', AVLVertex('b', 'B'), AVLVertex('d', 'D'))
        val leftSon = AVLVertex('f', 'F', leftSonSLeftSon, AVLVertex('e','E'), 1)
        val root = AVLVertex('h', 'H', leftSon, AVLVertex('i', 'I', null, AVLVertex('j', 'J'), -1), 1) 
        return AVLTreeForTest(root, 8L)
    }

    @Test
    fun `content is correct after entery was added (have to rotate right)`() {
        val tree = makeTreeForHaveToRotateRightPutTest()
        tree.put('a', 'A')
        val expextedContent = setOf('a' to 'A','b' to 'B', 'c' to 'C',
         'd' to 'D', 'e' to 'E', 'h' to 'H', 'f' to 'F', 'i' to 'I', 'j' to 'J')
        assert(isTreeConsistsOf(expextedContent, tree))
    }

    @Test
    fun `size increased by 1 after added (have to rotate right)`() {
        val tree = makeTreeForHaveToRotateRightPutTest()
        tree.put('a', 'A')
        assert(tree.size() == 9L)
    }

    @Test
    fun `structure is correct after added (have to rotate right)`() {
        val tree = makeTreeForHaveToRotateRightPutTest()
        tree.put('a', 'A')
        val expectedDependences = listOf(Triple(0, 1, 7), Triple(1, 2, 4), Triple(2, 3, null),
         Triple(4, 5, 6), Triple(7, null, 8))
        val expextedOrder = arrayOf('h', 'c', 'b', 'a', 'f', 'd', 'e', 'i', 'j')
        assert(isTreeSStructureThat(tree, expextedOrder, expectedDependences))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after added (have to rotate right)`() {
        val tree = makeTreeForHaveToRotateRightPutTest()
        tree.put('a', 'A')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForHaveToChangeRootByRotateLeftPutTest() : AVLTreeForTest<Char, Char> {
        val rightSon = AVLVertex('h', 'H', AVLVertex('f', 'F'), AVLVertex('i', 'I'))
        val root = AVLVertex('e', 'E', AVLVertex('d','D'), rightSon, -1)
        return AVLTreeForTest(root, 5L)
    }

    @Test
    fun `content is correct after entery was added (rotateLeft changes root)`() {
        val tree = makeTreeForHaveToChangeRootByRotateLeftPutTest()
        tree.put('j', 'J')
        val expextedContent = setOf('d' to 'D', 'e' to 'E', 'h' to 'H', 'f' to 'F', 'i' to 'I', 'j' to 'J')
        assert(isTreeConsistsOf(expextedContent, tree))
    }

    @Test
    fun `size increased by 1 after added (rotateLeft changes root)`() {
        val tree = makeTreeForHaveToChangeRootByRotateLeftPutTest()
        tree.put('j', 'J')
        assert(tree.size() == 6L)
    } 

    @Test
    fun `structure is correct after added (rotateLeft changes root)`() {
        val tree = makeTreeForHaveToChangeRootByRotateLeftPutTest()
        tree.put('j', 'J')
        val expectedDependences = listOf(Triple(0, 1, 4), Triple(1, 2, 3), Triple(4, null, 5))
        val expextedOrder = arrayOf('h', 'e', 'd', 'f', 'i', 'j')
        assert(isTreeSStructureThat(tree, expextedOrder, expectedDependences))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after added (rotateLeft changes root)`() {
        val tree = makeTreeForHaveToChangeRootByRotateLeftPutTest()
        tree.put('j', 'J')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForHaveToChangeRootByRotateRightPutTest() : AVLTreeForTest<Char, Char> {
        val leftSon = AVLVertex('c', 'C', AVLVertex('b', 'B'), AVLVertex('d', 'D'))
        val root = AVLVertex('f', 'F', leftSon, AVLVertex('e','E'), 1)
        return AVLTreeForTest(root, 5L)
    }

    @Test
    fun `tree consists of initially content and entery after entery was added (rotateRight changes root)`() {
        val tree = makeTreeForHaveToChangeRootByRotateRightPutTest()
        tree.put('a', 'A')
        val expextedContent = setOf('a' to 'A', 'b' to 'B', 'c' to 'C', 'd' to 'D', 'e' to 'E', 'f' to 'F')
        assert(isTreeConsistsOf(expextedContent, tree))
    }

    @Test
    fun `size increased by 1 after added (rotateRight changes root)`() {
        val tree = makeTreeForHaveToChangeRootByRotateRightPutTest()
        tree.put('a', 'A')
        assert(tree.size() == 6L)
    } 

    @Test
    fun `tree's structure is correct after added (rotateRight changes root)`() {
        val tree = makeTreeForHaveToChangeRootByRotateRightPutTest()
        tree.put('a', 'A')
        val expectedDependences = listOf(Triple(0, 1, 3), Triple(1, 2, null), Triple(3, 4, 5))
        val expextedOrder = arrayOf('c', 'b', 'a', 'f', 'd', 'e')
        assert(isTreeSStructureThat(tree, expextedOrder, expectedDependences))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after added (rotateRight changes root)`() {
        val tree = makeTreeForHaveToChangeRootByRotateRightPutTest()
        tree.put('a', 'A')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForHaveToBigRotateLeftPutTest() : AVLTreeForTest<Char, Char> {
        val rightSonSRightSon = AVLVertex('i', 'I', AVLVertex('g', 'G'), AVLVertex('j', 'J'))
        val rightSon = AVLVertex('e', 'E', AVLVertex('d','D'), rightSonSRightSon, -1)
        val root = AVLVertex('c', 'C', AVLVertex('b', 'B', AVLVertex('a', 'A'), null, 1), rightSon, -1) 
        return AVLTreeForTest(root, 8L)
    }

    // (1) - add grandson's right son, (2) add grandson's left son
    @Test
    fun `content is correct after entery was added (have to big rotate left)(1)`() {
        val tree = makeTreeForHaveToBigRotateLeftPutTest()
        tree.put('f', 'F')
        val expextedContent = setOf('a' to 'A','b' to 'B', 'c' to 'C',
         'd' to 'D', 'e' to 'E', 'g' to 'G', 'f' to 'F', 'i' to 'I', 'j' to 'J')
        assert(isTreeConsistsOf(expextedContent, tree))
    }

    @Test
    fun `content is correct after entery was added (have to big rotate left)(2)`() {
        val tree = makeTreeForHaveToBigRotateLeftPutTest()
        tree.put('h', 'H')
        val expextedContent = setOf('a' to 'A','b' to 'B', 'c' to 'C',
         'd' to 'D', 'e' to 'E', 'h' to 'H', 'g' to 'G', 'i' to 'I', 'j' to 'J')
        assert(isTreeConsistsOf(expextedContent, tree))
    }

    @Test
    fun `size increased by 1 after added (have to big rotate left)(1)`() {
        val tree = makeTreeForHaveToBigRotateLeftPutTest()
        tree.put('f', 'F')
        assert(tree.size() == 9L)
    }

    @Test
    fun `size increased by 1 after added (have to big rotate left)(2)`() {
        val tree = makeTreeForHaveToBigRotateLeftPutTest()
        tree.put('h', 'H')
        assert(tree.size() == 9L)
    }

    @Test
    fun `structure is correct after added (have to big rotate left)(1)`() {
        val tree = makeTreeForHaveToBigRotateLeftPutTest()
        tree.put('f', 'F')
        val expectedDependences = listOf(Triple(0, 1, 3), Triple(1, 2, null), Triple(3, 4, 7),
         Triple(4, 5, 6), Triple(7, null, 8))
        val expextedOrder = arrayOf('c', 'b', 'a', 'g', 'e', 'd', 'f', 'i', 'j')
        assert(isTreeSStructureThat(tree,expextedOrder, expectedDependences))
    }
    @Test
    fun `structure is correct after added (have to big rotate left)(2)`() {
        val tree = makeTreeForHaveToBigRotateLeftPutTest()
        tree.put('h', 'H')
        val expectedDependences = listOf(Triple(0, 1, 3), Triple(1, 2, null), Triple(3, 4, 6),
         Triple(4, 5, null), Triple(6, 7, 8))
        val expextedOrder = arrayOf('c', 'b', 'a', 'g', 'e', 'd', 'i', 'h', 'j')
        assert(isTreeSStructureThat(tree,expextedOrder, expectedDependences))
    }
    @Test
    fun `vertexes' sonsHeightDiff are correct after added (have to big rotate left)(1)`() {
        val tree = makeTreeForHaveToBigRotateLeftPutTest()
        tree.put('f', 'F')
        assert(isSonsHeightDiffCorrect(tree))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after added (have to big rotate left)(2)`() {
        val tree = makeTreeForHaveToBigRotateLeftPutTest()
        tree.put('h', 'H')
        assert(isSonsHeightDiffCorrect(tree))
    }

    private fun makeTreeForHaveToBigRotateRightPutTest() : AVLTreeForTest<Char, Char> {
        val leftSonSLeftSon = AVLVertex('b', 'B', AVLVertex('a', 'A'), AVLVertex('d', 'D')) 
        val leftSon = AVLVertex('f', 'F', leftSonSLeftSon, AVLVertex('g','G'), 1)
        val root = AVLVertex('h', 'H', leftSon, AVLVertex('i', 'I',null,  AVLVertex('j', 'J'), -1), 1) 
        return AVLTreeForTest(root, 8L)
    }

    // (1) - add grandson's left son, (2) add grandson's right son
    @Test
    fun `content is correct after entery was added (have to big rotate right)(1)`() {
        val tree = makeTreeForHaveToBigRotateRightPutTest()
        tree.put('c', 'C')
        val expextedContent = setOf('a' to 'A','b' to 'B', 'c' to 'C',
        'd' to 'D', 'h' to 'H', 'g' to 'G', 'f' to 'F', 'i' to 'I', 'j' to 'J')
        assert(isTreeConsistsOf(expextedContent, tree))
    }

    @Test
    fun `content is correct after entery was added (have to big rotate right)(2)`() {
        val tree = makeTreeForHaveToBigRotateRightPutTest()
        tree.put('e', 'E')
        val expextedContent = setOf('a' to 'A','b' to 'B', 'f' to 'F',
        'd' to 'D', 'e' to 'E', 'h' to 'H', 'g' to 'G', 'i' to 'I', 'j' to 'J')
        assert(isTreeConsistsOf(expextedContent, tree))
    }

    @Test
    fun `size increased by 1 after added (have to big rotate right)(1)`() {
        val tree = makeTreeForHaveToBigRotateRightPutTest()
        tree.put('c', 'C')
        assert(tree.size() == 9L)
    }

    @Test
    fun `size increased by 1 after added (have to big rotate right)(2)`() {
        val tree = makeTreeForHaveToBigRotateRightPutTest()
        tree.put('e', 'E')
        assert(tree.size() == 9L)
    }


    @Test
    fun `structure is correct after added (have to big rotate right)(1)`() {
        val tree = makeTreeForHaveToBigRotateRightPutTest()
        tree.put('c', 'C')
        val expectedDependences = listOf(Triple(0, 1, 7), Triple(7, null, 8), Triple(1, 2, 5),
        Triple(5, null, 6), Triple(2, 3, 4))
        val expextedOrder = arrayOf('h', 'd', 'b', 'a', 'c', 'f', 'g', 'i', 'j')
        assert(isTreeSStructureThat(tree,expextedOrder, expectedDependences))
    }

    @Test
    fun `structure is correct after added (have to big rotate right)(2)`() {
        val tree = makeTreeForHaveToBigRotateRightPutTest()
        tree.put('e', 'E')
        val expectedDependences = listOf(Triple(0, 1, 7), Triple(7, null, 8), Triple(1, 2, 4),
        Triple(2, 3, null), Triple(4, 5, 6))
        val expextedOrder = arrayOf('h', 'd', 'b', 'a', 'f', 'e', 'g', 'i', 'j')
        assert(isTreeSStructureThat(tree,expextedOrder, expectedDependences))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after added (have to big rotate right)(1)`() {
        val tree = makeTreeForHaveToBigRotateRightPutTest()
        tree.put('c', 'C')
        assert(isSonsHeightDiffCorrect(tree))
    }

    @Test
    fun `vertexes' sonsHeightDiff are correct after added (have to big rotate right)(2)`() {
        val tree = makeTreeForHaveToBigRotateLeftPutTest()
        tree.put('e', 'E')
        assert(isSonsHeightDiffCorrect(tree))
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
        val expextedContent = setOf('a' to 'A', 'b' to 'B')
        assert(isTreeConsistsOf(expextedContent, tree))
    } 
}

