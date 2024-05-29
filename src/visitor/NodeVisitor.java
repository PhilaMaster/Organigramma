package visitor;

import gui.GraphicNode;
import node.Node;

public interface NodeVisitor {
    void visit(Node node);
    /**
     * il pattern visitor è stato qui implementato nonostante ci sia un solo tipo di Node, per poter garantire l'evolvibilità del
     * sistema. Nel caso in cui in futuro verranno aggiunti altri tipi di oggetti compositi, potrà essere stabilita
     * una diversa politica per posizionare gli oggetti nello schermo (ad esempio organigrammi ibridi, con sottolivelli a bandiera)
     */

}
