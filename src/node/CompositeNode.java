package node;

import gui.GraphicNode;
import visitor.NodeVisitor;

import java.util.*;

public class CompositeNode implements Node {

    private final LinkedList<Node> children = new LinkedList<>();
    private final int altezza;
    //private String nome;
    private boolean removable = true;
    private Node parent;
    private final GraphicNode graphicNode;

    public CompositeNode(int altezza, GraphicNode graphicNode) {
        this.altezza = altezza;
        this.graphicNode = graphicNode;
        //this.nome = nome;
    }


    public void setRemovable(boolean removable) {
        this.removable = removable;
    }
    public boolean isRoot(){return !removable;}

    public Node getParent() {return parent;}
    public void setParent(Node parent) {this.parent = parent;}

    public boolean removeThis(){
        if (!removable) throw new UnsupportedOperationException("Impossibile rimuovere il nodo radice");
        if (!children.isEmpty()){
            //TODO Popup "Sei sicuro di voler eliminare nodo con dei figli?"
            //return false
        }
        getParent().remove(this);
        return true;
    }

    public int getHeight(){
        return altezza;
    }

    @Override
    public List<Node> getChildren() {
        return children;
    }
//
//    public String getNome() {return this.nome;}
//    public void setNome(String nome) {this.nome = nome;}


    @Override
    public boolean add(Node node) {
        return children.add(node);
    }

    @Override
    public boolean remove(Node node) {
        return children.remove(node);
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
