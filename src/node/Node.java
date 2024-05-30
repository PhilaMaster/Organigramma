package node;

import gui.GraphicNode;
import visitor.NodeVisitor;

import java.util.ArrayList;
import java.util.List;

public interface Node extends Iterable<Node>{
    /**
     * @param node il nodo da aggiungere
     */
    void add(Node node);

    /**
     * @param node il nodo da rimuovere
     */
    void remove(Node node);

    /**
     * Restituisce il <code>Node</code> figlio in posizione i
     * @param i, l'indice del nodo
     * @return il nodo
     */
    Node getChild(int i);

    /**
     * @return true se e solo se il nodo è foglia
     */
    boolean isLeaf();

    /**
     * @return true se e solo se il nodo è radice
     */
    boolean isRoot();

    /**
     * @return l'altezza del nodo
     */
    int getHeight();

    /**
     * @return la lista dei <code>Node</code> figli
     */
    List<Node> getChildren();


    void accept(NodeVisitor visitor);

    /**
     * @return l'oggetto grafico relativo al nodo
     */
    GraphicNode getGraphic();

    /**
     * Restituisce il padre del nodo
     * @return il padre se il nodo non è radice, null altrimenti
     */
    Node getParent();
    void setParent(Node parent);

    default int getChildrenCount(){
        return getChildren().size();
    }

    void setRemovable(boolean removable);

    /**
     * @return la lista degli impiegati associati al nodo
     */
    List<Employee> getEmployees();

    /**
     * @return la lista dei ruoli registrati nel nodo
     */
    List<Role> getRoles();

    /**
     * @return la lista dei ruoli che vengono ereditati dalla gerarchia
     */
    default List<Role> getInheritedRoles(){
        List<Role> ret = new ArrayList<>();
        Node pNode = getParent();
        while(pNode != null){
            for(Role role : pNode.getRoles())
                if(role.extend())
                    ret.add(role);
            pNode = pNode.getParent();
        }
        return ret;
    }

    default void addRole(Role role){
        getRoles().add(role);
    }

    default void removeRole(Role role){
        getRoles().remove(role);
    }

    default void addEmployee(Employee employee){
        getEmployees().add(employee);
    }

    default void removeEmployee(Employee employee){
        getEmployees().remove(employee);
    }
}
