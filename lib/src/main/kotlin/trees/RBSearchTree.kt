package main.trees
import main.vertexes.RBVertex

class RBSearchTree<K, V> : AbstractBinarySearchTree<K, V, RBVertex<K,V>> {

    //4 cases we need to look at
    //1) remove red vertex with 0 children -> just remove vetrex

    //2) remove red or black vertex with 2 children ->
    //find min vertex on the right subtree and swap it's key and value with
    //key and value of vertex that we need to remove
    //Now we can work with vertex which has 1 or 0 children

    //3) remove black vetrex with 1 child -> child can be only red
    //so we just swap child's key and value with key and value that we need to remove
    //and look at case 1)

    //4) remove black vertex with 0 children -> just remove vertex
    override fun remove(key: K): V? {
        val vertex: RBVertex<K, V> = getVertex(key) ?: return null
        val value = vertex.value
        var isVertexRed = vertex.isRed
        var child: RBVertex<K, V>? = null

        if (countChildren(vertex) < 2){
            child = getChild(vertex)
            replaceVertexBy(vertex, child)
        }

        else{
            val vertexForSwap = getMinKeyNodeRec(vertex.rightSon)
            vertexForSwap?.let{
                vertex.key = it.key
                vertex.value = it.value
                isVertexRed = it.isRed
                child = getChild(it)
                replaceVertexBy(it, child)
            }
        }

        if (!isVertexRed) balanceAfterRemove(child)

        return value
    }

    //we need to balance tree after removal black vertex with 0 children
    //in this fun we need to look at vertex's parent and brother

    //1) brother is black and brother's rightSon is red -> we paint
    //brother in parent's color, parent and brother's rightSon in black
    //then rotate left

    //2) brother is black and brother's leftSon is red (rightSon - black) ->
    //we swap colors of brother and brother's leftSon and rotate right
    //then look at case 1

    //3) brother is black and both sons are black -> we make brother red
    //then we need to launch algorithm from the parent because of it
    //can be red, so we have red parent and red son or black so
    //the black height of all subtree decreased

    //4) brother is red -> make brother black, parent red and
    //rotate left. We move conflict on level below, then we look at the previous cases
    private fun balanceAfterRemove(vertex: RBVertex<K, V>?){
        var currentVertex = vertex
        while (currentVertex != root && (currentVertex?.isRed == false || currentVertex == null)){
            var brother: RBVertex<K, V>?
            if (currentVertex == currentVertex?.parent?.leftSon){
                brother = currentVertex?.parent?.rightSon

                if (brother?.isRed == true){
                    brother.isRed = false
                    currentVertex?.parent?.isRed = true
                    val vertexForRotate = currentVertex?.parent
                    vertexForRotate?.let { rotateLeft(vertexForRotate) }
                    brother = currentVertex?.parent?.rightSon
                }

                if ((brother?.leftSon?.isRed == false || brother?.leftSon == null) &&
                    (brother?.rightSon?.isRed == false || brother?.rightSon == null)){
                    brother?.isRed = true
                    currentVertex = currentVertex?.parent
                }

                else{
                    if (brother.rightSon?.isRed == false || brother.rightSon == null){
                        brother.leftSon?.isRed = false
                        brother.isRed = true
                        rotateRight(brother)
                        brother = currentVertex?.parent?.rightSon
                    }

                    val parentColor = currentVertex?.parent?.isRed
                    parentColor?.let { brother?.isRed = parentColor }
                    currentVertex?.parent?.isRed = false
                    brother?.rightSon?.isRed = false
                    val vertexForRotate = currentVertex?.parent
                    vertexForRotate?.let { rotateLeft(vertexForRotate) }
                    currentVertex = root
                }
            }

            else{
                brother = currentVertex?.parent?.leftSon

                if (brother?.isRed == true){
                    brother.isRed = false
                    currentVertex?.parent?.isRed = true
                    val vertexForRotate = currentVertex?.parent
                    vertexForRotate?.let { rotateRight(vertexForRotate) }
                    brother = currentVertex?.parent?.leftSon
                }

                if ((brother?.leftSon?.isRed == false || brother?.leftSon == null) &&
                    (brother?.rightSon?.isRed == false || brother?.rightSon == null)){
                    brother?.isRed = true
                    currentVertex = currentVertex?.parent
                }

                else{
                    if (brother.leftSon?.isRed == false || brother.leftSon == null){
                        brother.rightSon?.isRed = false
                        brother.isRed = true
                        rotateLeft(brother)
                        brother = currentVertex?.parent?.leftSon
                    }

                    val parentColor = currentVertex?.parent?.isRed
                    parentColor?.let { brother?.isRed = parentColor }
                    currentVertex?.parent?.isRed = false
                    brother?.rightSon?.isRed = false
                    val vertexForRotate = currentVertex?.parent
                    vertexForRotate?.let { rotateRight(vertexForRotate) }
                    currentVertex = root
                }
            }
        }
        currentVertex?.isRed = false
    }

    //finds vertex by corresponding key
    //if such vertex doesn't exist returns null
    private fun getVertex(key: K): RBVertex<K, V>? {
        var currentVertex: RBVertex<K, V>? = root

        while (currentVertex?.key != key){
            if (currentVertex == null) return null
            when (compareKeys(key, currentVertex.key)){
                -1 -> {
                    currentVertex = currentVertex.leftSon
                }
                1 -> {
                    currentVertex = currentVertex.rightSon
                }
            }
        }
        return currentVertex
    }

    //finds free place and inserts newVertex, colors it in red
    //if vertex with such key exists, replaces it
    override fun put(key: K, value: V, replaceIfExists: Boolean) {
        var currentVertex: RBVertex<K, V>? = root
        var parent: RBVertex<K, V>? = null
        var isLeft: Boolean = false

        while (currentVertex != null) {
            when (compareKeys(key, currentVertex.key)){
                -1 -> {
                    parent = currentVertex
                    currentVertex = currentVertex.leftSon
                    isLeft = true
                }
                0 -> {
                    if (replaceIfExists) currentVertex.value = value
                    break
                }
                1 -> {
                    parent = currentVertex
                    currentVertex = currentVertex.rightSon
                    isLeft = false
                }
            }
        }

        if (currentVertex == null){
            currentVertex = RBVertex(key, value, null, null, true, parent)
            if (root == null) root = currentVertex
            else if (isLeft) parent?.let { parent.leftSon = currentVertex }
            else parent?.let { parent.rightSon = currentVertex }
        }

        balanceAfterPut(currentVertex)
    }

    //we need to balance tree in two cases
    //1) when newVertex is root, so our root is red
    //2) when parent of our newVertex is red(because newVertex is also red)

    //in first case we just make the root black
    //in second case we need to look at the newVertex's uncle

    //if uncle is red, we make it black and newVertex's parent black and grandparent red
    //launch algorithm to grandfather because now it's color changed to red

    //if uncle is black we also make newVertex's parent black, grandparent red
    //and rotate it right
    private fun balanceAfterPut(vertex: RBVertex<K, V>) {
        var currentVertex = vertex

        while (currentVertex.parent?.isRed == true){
            val grandparent = currentVertex.parent?.parent

            if (currentVertex.parent == grandparent?.leftSon){
                val uncle = grandparent?.rightSon

                if (uncle?.isRed == true){
                    currentVertex.parent?.isRed = false
                    uncle.isRed = false
                    grandparent.isRed = true
                    currentVertex = grandparent
                }

                else {
                    if (currentVertex == currentVertex.parent?.rightSon) {
                        currentVertex = currentVertex.parent ?: currentVertex
                        rotateLeft(currentVertex)
                    }

                    currentVertex.parent?.isRed = false
                    currentVertex.parent?.parent?.isRed = true
                    val vertexForRightRotate = currentVertex.parent?.parent
                    vertexForRightRotate?.let { rotateRight(vertexForRightRotate) }
                }
            }

            else {
                val uncle = grandparent?.leftSon

                if (uncle?.isRed == true){
                    currentVertex.parent?.isRed = false
                    uncle.isRed = false
                    grandparent.isRed = true
                    currentVertex = grandparent
                }

                else {
                    if (currentVertex == currentVertex.parent?.leftSon) {
                        currentVertex = currentVertex.parent ?: currentVertex
                        rotateRight(currentVertex)
                    }

                    currentVertex.parent?.isRed = false
                    currentVertex.parent?.parent?.isRed = true
                    val vertexForLeftRotate = currentVertex.parent?.parent
                    vertexForLeftRotate?.let { rotateLeft(vertexForLeftRotate) }
                }
            }
        }
        root?.isRed = false
    }

    private fun countChildren(vertex: RBVertex<K, V>): Int {
        var numOfChild = 0
        if (vertex.leftSon != null) ++numOfChild
        if (vertex.rightSon != null) ++numOfChild
        return numOfChild
    }

    private fun getChild(vertex: RBVertex<K, V>) = if (vertex.leftSon != null) vertex.leftSon else vertex.rightSon

    private fun replaceVertexBy(oldVertex: RBVertex<K, V>, newVertex: RBVertex<K, V>?){
        if (root == oldVertex) root = newVertex
        else if (oldVertex == oldVertex.parent?.leftSon) oldVertex.parent?.leftSon = newVertex
        else oldVertex.parent?.rightSon = newVertex
        newVertex?.parent = oldVertex.parent
    }

    //suppose that vertex has a rightSon
    //swap parent and rightSon, rightSon's leftSon becomes parent's rightSon
    private fun rotateLeft(vertex: RBVertex<K, V>) {
        val rightVertex: RBVertex<K, V>? = vertex.rightSon
        vertex.rightSon = rightVertex?.leftSon
        rightVertex?.leftSon.let { rightVertex?.leftSon?.parent = vertex }
        rightVertex?.parent = vertex.parent
        when {
            vertex.parent == null -> root = rightVertex
            vertex == vertex.parent?.leftSon -> vertex.parent?.leftSon = rightVertex
            else -> vertex.parent?.rightSon = rightVertex
        }
        vertex.parent = rightVertex
        rightVertex?.leftSon = vertex
    }

    //suppose that vertex has a leftSon
    //swap parent and leftSon, leftSon's rightSon becomes parent's leftSon
    private fun rotateRight(vertex: RBVertex<K, V>) {
        val leftVertex: RBVertex<K, V>? = vertex.leftSon
        vertex.leftSon = leftVertex?.rightSon
        leftVertex?.rightSon.let { leftVertex?.rightSon?.parent = vertex}
        leftVertex?.parent = vertex.parent
        when {
            vertex.parent == null -> root = leftVertex
            vertex == vertex.parent?.leftSon -> vertex.parent?.leftSon = leftVertex
            else -> vertex.parent?.rightSon = leftVertex
        }
        vertex.parent = leftVertex
        leftVertex?.rightSon = vertex
    }

    constructor(comparator: Comparator<K>? = null) : super(comparator)

    constructor(map: Map<K, V>, replaceIfExists: Boolean = true, comparator: Comparator<K>? = null) : super(map, replaceIfExists, comparator)
}
