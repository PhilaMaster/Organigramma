package diagram;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public abstract class AbstractDiagram implements Diagram {

    private final LinkedList<Diagram> children = new LinkedList<>();
    private int altezza;

    protected AbstractDiagram(int altezza) {
        this.altezza = altezza;
    }

    public int getAltezza(){
        return altezza;
    }

    @Override
    public boolean add(Diagram diagram) {
        return children.add(diagram);
    }

    @Override
    public boolean remove(Diagram diagram) {
        return children.remove(diagram);
    }

    @Override
    public Diagram getChild(int i){
        return children.get(i);
    }

    @Override
    public boolean isLeaf(){
        return children.isEmpty();
    }


    @Override
    public Iterator<Diagram> iterator() {
        return new InnerIterator();
    }

    private class InnerIterator implements Iterator<Diagram> {
        Iterator<Diagram> it = children.iterator();
        private Diagram last = null;

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
        public Diagram next() {
            last = it.next();
            return last;
        }

    }

}
