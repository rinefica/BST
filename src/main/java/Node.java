/**
 * @author Rinefica
 * @version 1
 *
 * Узел бинарного дерева.
 * @see BST
 *
 * Поля:    целочисленное значение,
 *          ссылка на узел родителя,
 *          ссылка на узлы потомков (левый и правый).
 *
 * Методы:  получение и замена значения, родителя, потомков,
 *          проверка, является ли узел листом,
 *          проверка на наличие у узла только одного потомка,
 *          проверка на наличие у узла обоих потомков,
 *          получение всех соседей (родитель, левый потомок, правый потомок),
 *          проверка одинаковых потомков у двух узлов.
 *
 */
class Node {
    /**
     * Целочисленное значение, хранимое в узле
     */
    private int value;

    /**
     * Ссылка на <b>узел-родитель</b>
     */
    private Node parent;

    /**
     * Ссылка на <b>левого потомка</b>
     */
    private Node left;

    /**
     * Ссылка на <b>прpublicавого потомка</b>
     */
    private Node right;

    /**
     * Конструктор
     * @param value Значение, хранимое в узле
     * @param parent Ссылка на узел родителя
     */
    Node(int value, Node parent) {
        this.value = value;
        this.parent = parent;
    }

    /**
     * Получение значения
     * @return Значение узла
     */

    int getValue() {
        return value;
    }

    /**
     * Замена значения
     * @param value Новое значение узла
     */
    void setValue(int value) {
        this.value = value;
    }

    /**
     * Получение узла-родителя
     * @return Узел-родитель
     */
    Node getParent() {
        return parent;
    }

    /**
     * Замена родителя
     * @param parent Новый родитель
     */
    void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Получение левого потомка
     * @return Левый потомок узла
     */
    Node getLeft() {
        return left;
    }

    /**
     * Замена левого потомка
     * @param left Новый левый потомок узла
     */
    void setLeft(Node left) {
        this.left = left;
    }

    /**
     * Получение правого потомка
     * @return Правый потомок узла
     */
    Node getRight() {
        return right;
    }

    /**
     * Замена правого потомка
     * @param right Новый правый потомок
     */
    void setRight(Node right) {
        this.right = right;
    }

    /**
     * Является ли узел листом
     * @return <b>true</b>, если у узла нет потомков, <b>false</b>, если у узла есть хотя бы один потомок
     */
    boolean isList() { return (left == null && right == null); }

    /**
     * Определяет, имеет ли узел только одного потомка
     * @return <b>true</b>, если у узла ровно один потомок, <b>false</b> в остальных случаях
     */
    boolean hasOneChild() { return (left == null ^ right == null); }

    /**
     * Определяет, имеет ли узел обоих потомков
     * @return <b>true</b>, если у узла ровно два потомка, <b>false</b> в остальных случаях
     */
    boolean hasTwoChildren() {
        return (left != null && right != null);
    }

    /**
     * Получение всех соседних узлов
     * @return массив узлов в порядке родитель, левый потомок, правый потомок
     */
    Node[] getNeighbours() {
        return new Node[]{parent, left, right};
    }

    /**
     * Проверяет, одинаковые ли потомки у двух узлов
     * @param node Первый узел
     * @param oNode Второй узел
     * @return <b>true</b>, если у узлов одинаковые потомки, <b>false</b>, если разные
     */
    private static boolean equalsNodes(Node node, Node oNode) {
        if (oNode == null ^ node == null)
            return false;

        if (oNode != null && node != null)
            return oNode.value == node.value;

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;

        return equalsNodes(this, node) &&
                equalsNodes(this.parent, node.parent) &&
                equalsNodes(this.left, node.left) &&
                equalsNodes(this.right, node.right);
    }

    @Override
    public int hashCode() {
        int answer = value;
        if (parent != null)
            answer += parent.value;
        if (left != null)
            answer += left.value;
        if (right != null)
            answer += right.value;
        return answer;
    }

    private static StringBuilder writeNeighbourNode(StringBuilder name, Node node) {
        if (node != null)
            return name.append(": ").append(node.value).append("\n");
        else
            return name.append(": none\n");
    }

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder("Node: " + value + "\n");

        StringBuilder[] neighbours = {
                new StringBuilder("parent"),
                new StringBuilder("left"),
                new StringBuilder("right")};

        Node[] nodesNeighbours = getNeighbours();

        for(int i = 0; i < 3; i++) {
            answer.append(
                    writeNeighbourNode(neighbours[i], nodesNeighbours[i]));
        }

        return answer.toString();
    }
}
