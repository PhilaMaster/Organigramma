package visitor.nodes_management;

import node.CompositeNode;
import node.Node;
import node.Role;
import visitor.NodeVisitor;


public class RemoveRoleVisitor implements NodeVisitor {
    private final Role daRimuovere;
    public RemoveRoleVisitor(Role daRimuovere){
        this.daRimuovere = daRimuovere;
    }
    @Override
    public void visit(CompositeNode node) {
        node.getEmployees().removeIf(employee -> employee.role().equals(daRimuovere));
        if(daRimuovere.extend())//se si estende ai figli, li rimuovo anche da loro
            for(Node child : node.getChildren()){child.accept(this);}
    }
}
