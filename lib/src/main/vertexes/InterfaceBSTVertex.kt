package main.vertexes

interface InterfaceBSTVertex<K, V, N> {
    var key: K
    var value: V
    var leftSon: N?
    var rightSon: N?
}
