package node;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import visitor.design.PositionerVisitor;

import java.util.List;

import static node.GraphicNode.*;
import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {
    private CompositeNode root;
    private CompositeNode child1;
    private CompositeNode child2;
    private CompositeNode child3;
    private GraphicNode gRoot;
    private GraphicNode gChild1;
    private GraphicNode gChild2;
    private GraphicNode gChild3;

    @BeforeEach
    public void setUp() {
        gRoot = new GraphicNode("radice");
        gChild1 = new GraphicNode("figlio 1");
        gChild2 = new GraphicNode("figlio 2");
        gChild3 = new GraphicNode("figlio 3");
        root = new CompositeNode(0, gRoot);
        child1 = new CompositeNode(1, gChild1);
        child2 = new CompositeNode(2, gChild2);
        child3 = new CompositeNode(1, gChild3);
    }

    @Test
    public void testAddAndRemoveChild() {
        root.add(child1);
        assertEquals(1, root.getChildrenCount());
        assertEquals(child1, root.getChild(0));

        root.remove(child1);
        assertEquals(0, root.getChildrenCount());
    }

    @Test
    public void testIsLeaf() {
        assertTrue(root.isLeaf());

        root.add(child1);
        assertFalse(root.isLeaf());
    }

    @Test
    public void testGetHeight() {
        assertEquals(0, root.getHeight());
        assertEquals(1, child1.getHeight());
    }

    @Test
    public void testGetChildren() {
        root.add(child1);
        root.add(child2);
        List<Node> children = root.getChildren();
        assertEquals(2, children.size());
        assertTrue(children.contains(child1));
        assertTrue(children.contains(child2));
    }

    @Test
    public void testGetParentAndSetParent() {
        child1.setParent(root);
        assertEquals(root, child1.getParent());
    }

    @Test
    public void testGetGraphic() {
        assertEquals(gRoot, root.getGraphic());
    }

    @Test
    public void testAddAndRemoveRole() {
        Role role = new Role("Direttore", false);
        root.addRole(role);
        assertTrue(root.getRoles().contains(role));

        root.removeRole(role);
        assertFalse(root.getRoles().contains(role));
    }

    @Test
    public void testAddAndRemoveEmployee() {
        Employee employee = new Employee("Pasquale","Papalia",new Role("Direttore", false));
        root.addEmployee(employee);
        assertTrue(root.getEmployees().contains(employee));

        root.removeEmployee(employee);
        assertFalse(root.getEmployees().contains(employee));
    }

    @Test
    public void testGetInheritedRoles() {
        Role role1 = new Role("Direttore", true);
        Role role2 = new Role("Manager", false);
        root.addRole(role1);
        root.addRole(role2);
        child1.setParent(root);
        child2.setParent(child1);

        List<Role> inheritedRoles = child2.getInheritedRoles();
        assertTrue(inheritedRoles.contains(role1));
        assertFalse(inheritedRoles.contains(role2));
    }


    @Test
    public void testPositionerVisitor(){
        PositionerVisitor visitor = new PositionerVisitor();

        //solo nodo radice
        root.accept(visitor);
        assertEquals(HORIZONTAL_OFFSET, gRoot.getX());
        assertEquals(VERTICAL_OFFSET, gRoot.getY());

        //radice + un figlio (sono posizionati in colonna)
        visitor = new PositionerVisitor();//resetto lo stato del visitor
        root.add(child1);
        root.accept(visitor);

        assertEquals(HORIZONTAL_OFFSET, gRoot.getX());
        assertEquals(HORIZONTAL_OFFSET, gChild1.getX());

        assertEquals(VERTICAL_OFFSET, gRoot.getY());
        assertEquals(VERTICAL_OFFSET+(VERTICAL_SPACE+HEIGHT), gChild1.getY());

        //radice + due figli, la radice è posizionata in mezzo ai due figli lungo l'asse delle x
        visitor = new PositionerVisitor();
        root.add(child3);
        root.accept(visitor);

        assertEquals(HORIZONTAL_OFFSET + (HORIZONTAL_SPACE+WIDTH)/2, gRoot.getX());
        assertEquals(HORIZONTAL_OFFSET, gChild1.getX());
        assertEquals(HORIZONTAL_OFFSET+(HORIZONTAL_SPACE+WIDTH), gChild3.getX());

        assertEquals(VERTICAL_OFFSET, gRoot.getY());
        assertEquals(VERTICAL_OFFSET + (VERTICAL_SPACE+HEIGHT), gChild1.getY());
        assertEquals(VERTICAL_OFFSET + (VERTICAL_SPACE+HEIGHT), gChild3.getY());
    }
}
