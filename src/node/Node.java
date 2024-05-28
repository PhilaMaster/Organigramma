package node;

import gui.GraphicNode;
import node.users_management.Employee;
import node.users_management.Role;
import visitor.NodeVisitor;

import java.util.List;

public interface Node extends Iterable<Node>{
    /**
     *
     * @param node il nodo da aggiungere
     * @return true se il nodo è stato aggiunto correttamente, false altrimenti
     */
    boolean add(Node node);

    /**
     *
     * @param node il nodo da rimuovere
     * @return true se il nodo è stato rimosso correttamente, false altrimenti
     */
    boolean remove(Node node);

    Node getChild(int i);

    boolean isLeaf();
    boolean isRoot();

    int getHeight();

    List<Node> getChildren();

    //boolean getRemovable();

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
