import BST.BST;
import BST.Node;
import org.junit.Test;

import static org.junit.Assert.*;

public class BSTTest {

    @Test
    public void addNode() {
        BST bst = new BST(14);
        BST actual = new BST(14);

        assertEquals(bst, actual);

        bst.addNode(5);
        Node root = actual.getRoot();
        Node second = new Node(5, root);
        root.setLeft(second);

        assertEquals(bst, actual);

        bst.addNode(23);
        Node third = new Node(23, root);
        root.setRight(third);

        assertEquals(bst, actual);

        bst.addNode(3);
        Node four = new Node(3, second);
        second.setLeft(four);

        assertEquals(bst, actual);

    }

    /*
                      14
                5             23
            3       9    17       34
          _  _    8  _  _   18   _   _
                7 _      _  _
     */
    private BST createBST() {
        BST myBST = new BST(14);
        int[] a = {5, 23, 3, 9, 17, 18, 34, 8, 7};
        for (int i : a) myBST.addNode(i);
        return myBST;
    }


    private void assertNeighbours(int value, Integer[] actual) {
        Integer iToEq;

        Node[] bstN = createBST().getNeighbours(value);

        //Проверка соседей только по значению\null
        for (int i = 0; i < 3; i++) {

            if (bstN[i] == null)
                iToEq = null;
            else
                iToEq = bstN[i].getValue();

            assertEquals(iToEq, actual[i]);
        }
    }

    @Test
    public void equalsBST() {
        BST bst = createBST();
        BST oBst = new BST(14);
        //Деревья с одинаковым значением корня не равны
        assertNotEquals(bst, oBst);


        Node root = oBst.getRoot();
        Node second = new Node(5, root);
        root.setLeft(second);
        Node third = new Node(23, root);
        root.setRight(third);
        //Деревья с одинаковыми значениями корня и первого уровня потомков не равны
        assertNotEquals(bst, oBst);

        BST bstEqual = createBST();
        //Деревья с одинаковыми значениями всех узлов равны
        assertEquals(bst, bstEqual);

        bstEqual.deleteNode(7);
        //Удалили лист из второго дерева, деревья перестали быть равны
        assertNotEquals(bst, bstEqual);
    }

    @Test
    public void searchNode() {
        BST bst = createBST();

        //Поиск корневого элемента
        assertEquals(bst.searchNode(14), bst.getRoot());
        //Поиск несуществующего узла
        assertNull(bst.searchNode(13));


        //Проверка узла со значением 9 - родитель 5, левый потомок 8, правый null

        Node searchNode = bst.searchNode(9);
        assertEquals(searchNode.getValue(), 9);

        assertNeighbours(9, new Integer[]{5, 8, null});
    }

    @Test
    public void deleteInnerNode() {
        BST bstToDel = createBST();

        Node n17 = bstToDel.searchNode(17);
        bstToDel.deleteNode(17);
        //Проверяем, что удаленного узла больше нет в структуре
        assertNull(bstToDel.searchNode(17));

        Node parent17 = bstToDel.searchNode(n17.getParent().getValue());
        Node right17 = bstToDel.searchNode(n17.getRight().getValue());

        //Сравниваем родителя (неизменный) и ссылку на родителя у нового потомка
        assertEquals(parent17, right17.getParent());
        //Сравниваем ссылку на нового потомка у родителя
        assertEquals(parent17.getLeft(), right17);

        bstToDel.deleteNode(5);
        assertNull(bstToDel.searchNode(5));
        //Проверка узла, оказавшегося на месте 5 - 7
        assertEquals(bstToDel.searchNode(7), bstToDel.searchNode(14).getLeft());
        //Проверка корректного переноса 7 на новое место
        assertNull(bstToDel.searchNode(8).getLeft());
    }

    @Test
    public void deleteExternalNode(){
        BST bstToDel = createBST();
        BST bst = createBST();

        //Удаление листа
        bstToDel.deleteNode(34);
        assertNull(bstToDel.searchNode(34));
        assertNull(bstToDel.searchNode(23).getRight());


        //Удаление корня
        bst.deleteNode(14);
        //Проверяем, что узла нет
        assertNull(bst.searchNode(14));

        Node root = bst.getRoot();
        //Проверяем, что узел со значением 17 стал корневым
        assertEquals(root, bst.searchNode(17));
        //Проверяем, что потомки остались неизменными
        assertEquals(root.getLeft(), bst.searchNode(5));
        assertEquals(root.getRight(), bst.searchNode(23));
        //Проверяем корректность переноса узла 17
        assertEquals(bst.searchNode(23).getLeft(), bst.searchNode(18));
        assertEquals(bst.searchNode(18).getParent(), bst.searchNode(23));


    }

    @Test
    public void getNeighbours() {
        Integer[] actual = {null, 5, 23};
        assertNeighbours(14, actual);

        actual = new Integer[] {8, null, null};
        assertNeighbours(7, actual);

        actual = new Integer[] {5, 8, null};
        assertNeighbours(9, actual);

        actual = new Integer[] {14, 17, 34};
        assertNeighbours(23, actual);

        actual = new Integer[] {null, null, null};
        assertNeighbours(13, actual);
    }
}