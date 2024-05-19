package diagram;

public interface Diagram extends Iterable<Diagram>{
    /**
     *
     * @param diagram il diagramma da aggiungere
     * @return true se il diagramma è stato aggiunto correttamente, false altrimenti
     */
    boolean add(Diagram diagram);

    /**
     *
     * @param diagram il diagramma da rimuovere
     * @return true se il diagramma è stato rimosso correttamente, false altrimenti
     */
    boolean remove(Diagram diagram);

    Diagram getChild(int i);

    boolean isLeaf();
}
