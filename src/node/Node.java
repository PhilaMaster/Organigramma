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
     * @return la lista di <code>Node</code> figli
     */
    List<Node> getChildren();


    void accept(NodeVisitor visitor);

    GraphicNode getGraphic();

    Node getParent();
    void setParent(Node parent);

    default int getChildrenCount(){
        return getChildren().size();
    }

    void setRemovable(boolean removable);

    List<Employee> getEmployees();
    List<Role> getRoles();
    default List<Role> getInheritedRoles(){
        List<Role> ret = new ArrayList<>();
        Node pNode = getParent();
        while(pNode != null){
            for(Role role : pNode.getRoles())
                if(role.isExtend())
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
