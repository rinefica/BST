import java.util.Objects;

/**
 * @author Rinefica
 * @version 1
 *
 * Бинарное дерево поиска с уникальными целочисленными значениями.
 *
 * Поле:    корень дерева.
 * @see Node
 *
 * Методы:  добавление числа,
 *          удаление числа,
 *          поиск числа в дереве,
 *          определение соседей числа в дереве (предок, левый потомок, правый потомок).
 */
class BST {
    /**
     * Поле корневого узла дерева
     */
    private Node root;

    /**
     * Конструктор
     * @param value Значение корневого элемента
     */
    BST(int value) {
        root = new Node(value, null);
    }

    /**
     * Получение корня
     * @return Корневой элемент дерева
     */

    Node getRoot() {
        return root;
    }

    /**
     * Добавление узла со значением. В правое поддерево попадают значения больше корневого элемента, в левое - меньше.
     * @param value Значение, которое необходимо добавить в дерево
     * @return <b>true</b>, когда произошло добавление значения, <b>false</b>, когда значение не было добавлено
     */

    boolean addNode(int value) {
        Node curRoot = root;

        while (curRoot != null) {
            if (curRoot.getValue() == value)
                return false;

            if (value > curRoot.getValue()) {

                if (curRoot.getRight() != null)
                    curRoot = curRoot.getRight();
                else {
                    curRoot.setRight(new Node(value, curRoot));
                    break;
                }

            } else {
                if (curRoot.getLeft() != null)
                    curRoot = curRoot.getLeft();
                else {
                    curRoot.setLeft(new Node(value, curRoot));
                    break;
                }
            }
        }

        return true;
    }

    /**
     * Поиск узла по значению
     * @param value Значение, по которому производится поиск в дереве
     * @return Узел при успешном поиске, <b>null</b>, если значение в дереве не найдено
     */

    Node searchNode(int value) {
        Node curRoot = root;
        while (curRoot != null && curRoot.getValue() != value) {
            if (value > curRoot.getValue())
                curRoot = curRoot.getRight();
            else
                curRoot = curRoot.getLeft();
        }

        return curRoot;
    }

    /**
     * Поиск минимального элемента в поддереве
     * @param root Узел корня поддерева, в котором будет произведен поиск
     * @return <b>int</b> значение минимального элемента
     */
    private int findMinInSubTree(Node root) {
        Node curRoot = root;

        while(curRoot.getLeft() != null)
            curRoot = curRoot.getLeft();

        return curRoot.getValue();
    }

    /**
     * Рекурсивное удаление узла в дереве
     * @param root Корневой элемент, с которого начнется поиск элемента для удаления
     * @param value Значение элемента для удаления
     * @return Текущий корневой узел, <b>null</b> при отсутствии в дереве элемента со значением {@value}
     */

    private Node deleteNode(Node root, int value) {
        if (root == null)
            return null;

        if (value < root.getValue())
            root.setLeft(deleteNode(root.getLeft(), value));
        else if (value > root.getValue())
            root.setRight(deleteNode(root.getRight(), value));
        else if (root.hasTwoChildren()) {
            int min = findMinInSubTree(root.getRight());
            root.setValue(min);
            root.setRight(deleteNode(root.getRight(), min));
        } else {
            Node parent = root.getParent();

            if (root.getLeft() != null)
                root = root.getLeft();
            else
                root = root.getRight();

            if (root != null)
                root.setParent(parent);
        }

        return root;
    }

    /**
     * Удаление узла по значению
     * @param value Значение, которое нужно удалить из дерева
     * @return Корневой узел, <b>null</b> если узла со значением {@value} не было в дереве
     */
    Node deleteNode(int value) {
        return deleteNode(root, value);
    }

    /**
     * Получение массива соседей узла в порядке <b>родитель, левый потомок, правый потомок</b>
     * @param value Значение узла, для которого нужно найти соседей
     * @return Массив из соседних узлов, <b>{null, null, null}</b>, если узла со значением {@value} нет в дереве
     */
    Node[] getNeighbours(int value) {
        Node curNode = searchNode(value);
        if (curNode == null)
            return new Node[] {null, null, null};
        return curNode.getNeighbours();
    }

    private static boolean equalsNodes(Node node, Node oNode) {

        if ((oNode == null) && (node == null)) {
            return true;
        } else if ((oNode == null) || (node == null)) {
            return false;
        } else if (node.getValue() == oNode.getValue()) {
            return equalsNodes(node.getLeft(), oNode.getLeft()) &&
                    equalsNodes(node.getRight(), oNode.getRight());
        } else {
            return false;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BST bst = (BST) o;

        return equalsNodes(this.root, bst.root);
    }

    @Override
    public int hashCode() {
        return Objects.hash(root);
    }

    private static StringBuffer nodeToString(Node node) {
        StringBuffer answer = new StringBuffer();
        if (node != null) {
            answer  .append(node.getValue() + " ")
                    .append(nodeToString(node.getLeft()))
                    .append('\n')
                    .append(nodeToString(node.getRight()));
        }
        return answer;

    }

    @Override
    public String toString() {
        return "BST:\n" +
                nodeToString(root);
    }

    public static void main(String[] args) {
        BST myBST = new BST(14);
        int[] a = {5, 23, 3, 9, 17, 18, 34, 8, 7};
        for (int i : a) myBST.addNode(i);

//        nodeToString(myBST.root);
        System.out.println(myBST);
    }
}
