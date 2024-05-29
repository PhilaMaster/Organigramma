package node;

import gui.GraphicNode;
import visitor.NodeVisitor;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class CompositeNode implements Node, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final List<Role> roles = new ArrayList<>();
    private final List<Employee> employees = new ArrayList<>();
    private final List<Node> children = new ArrayList<>();
    private final int altezza;
    //private String nome;
    private boolean removable = true;
    private Node parent;
    private final GraphicNode graphicNode;

    public CompositeNode(int altezza, GraphicNode graphicNode) {
        this.altezza = altezza;
        this.graphicNode = graphicNode;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

    @Override
    public List<Employee> getEmployees() {
        return employees;
    }

    @Override
    public List<Role> getRoles() {
        return roles;
    }



    public boolean isRoot(){return !removable;}

    public Node getParent() {return parent;}
    public void setParent(Node parent) {this.parent = parent;}

    public int getHeight(){
        return altezza;
    }

    @Override
    public List<Node> getChildren() {
        return children;
    }

    @Override
    public void add(Node node) {
        children.add(node);
    }

    @Override
    public void remove(Node node) {
        children.remove(node);
    }

    @Override
    public Node getChild(int i){
        return children.get(i);
    }

    @Override
    public boolean isLeaf(){
        return children.isEmpty();
    }

    @Override
    public Iterator<Node> iterator() {
        return new InnerIterator();
    }

    private class InnerIterator implements Iterator<Node> {
        Iterator<Node> it = children.iterator();
        private Node last = null;

        @Override
        public void remove() {
            if (last == null)
                throw new NoSuchElementException();

            it.remove();
            last = null;
        }

        @Override
        public boolean hasNext() {

            return it.hasNext();
        }

        @Override
        public Node next() {
            last = it.next();
            return last;
        }

    }

    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public GraphicNode getGraphic() {
        return graphicNode;
    }
}
