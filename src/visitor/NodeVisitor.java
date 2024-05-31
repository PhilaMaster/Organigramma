package visitor;

import node.CompositeNode;

public interface NodeVisitor {
    void visit(CompositeNode node);
}
