package visitor;

import static gui.GraphicNode.*;

import node.Node;

import java.util.ArrayDeque;
import java.util.Deque;

public class PositionerVisitor implements NodeVisitor {

    int height,width;
    int x;
//    x_father,n,first,last;
    //int y = VERTICAL_OFFSET;
    //int h;
//    Deque<Integer> stack = new ArrayDeque<>();

    public PositionerVisitor(){
        x = HORIZONTAL_OFFSET;
        //x_father = HORIZONTAL_OFFSET;
//        first = HORIZONTAL_OFFSET;
//        last = HORIZONTAL_OFFSET+WIDTH;
    }
    /**
     * Metodo che visita la gerarchia di nodi, calcolando la posizione di ognuno di essi.
     * @param node il nodo da visitare
     */
    @Override
    public void visit(Node node) {
        for(Node n : node.getChildren())
            n.accept(this);
        //POSTFIX VISIT


        //CALCOLO ALTEZZA E SPESSORE
        if(node.isLeaf()) width++;
        height = Math.max(node.getHeight(), height);

        //CALCOLO POSIZIONE
        if (node.isLeaf()){
            node.getGraphic().setLocation(x,getY(node));
            x+=WIDTH + HORIZONTAL_SPACE;
        }else {
            if(node.getChildrenCount()==1){
                int x_parent = node.getChild(0).getGraphic().getX();
                node.getGraphic().setLocation(x_parent,getY(node));
            }else{
                int n = node.getChildrenCount();
                node.getGraphic().setLocation(getXParent(node),getY(node));
            }

        }
    }

    private int getXParent(Node parent) {
        int x_first = parent.getChild(0).getGraphic().getX();
        int x_last = parent.getChild(parent.getChildrenCount()-1).getGraphic().getX();
        return x_first + (x_last-x_first)/2;
    }
//    @Override
//    public void visit(Node node) {
//        if (!node.isLeaf()) stack.push(1);//numero di nodi fratelli visitati
//
//        for(Node n:node.getChildren())
//            n.accept(this);
//        //POSTFIX TRAVERSAL
//
//        if(node.isLeaf()) width++;
//        height = Math.max(node.getHeight(), height);
//
//        if (!node.isRoot()) n = stack.pop();
//
////        if(node.isLeaf()){
////            node.getGraphic().setLocation(x, getY(node));
////            x += HORIZONTAL_SPACE;
////            int nFratelli = node.getParent().getChildrenCount(); //incluso il nodo this stesso
////            if(nFratelli>1){
////                if(n==0)//primo fratello che visito
////                    first = x;
////                else if (n == nFratelli) //ultimo fratello che visito
////                    last = x;
////                else{
////                    //new DrawLine(first,last,node.getHeight());//node.getGraphic().getParent(); per riferimento al panel
////                    x_father = HORIZONTAL_OFFSET + first + (first-last)/2;
////                }
////            }else{//è un solo fratello, il padre sarà disegnato nella stessa posizione
////                x_father = x;
////            }
////        }else{
////            //se ultimo dei fratelli, command che fa un drawLine()?
////            node.getGraphic().setLocation(x_father, getY(node));
////        }
//
//
//        if (!node.isRoot()){
//            int nFratelli = node.getParent().getChildrenCount(); //incluso il nodo this stesso
//            if(nFratelli>1) {
//                if (n == 1) {//primo fratello che visito
//                    first = x;
//                    stack.push(n+1);//ho visitato un nodo fratello in più
//                }
//                else if (n == nFratelli) { //ultimo fratello che visito
//                    last = x+WIDTH;
//                }
//                else {
//                    //new DrawLine(first,last,node.getHeight());//node.getGraphic().getParent(); per riferimento al panel
//                    //x_father = HORIZONTAL_OFFSET + first + (first - last) / 2;
//                    stack.push(n+1);//ho visitato un nodo fratello in più
//                }
//            }
//        }
//
//        if(node.isLeaf()){
//            node.getGraphic().setLocation(x, getY(node));
//            x += HORIZONTAL_SPACE+WIDTH;//conto lo spazio del nodo stesso che ho inserito, più lo spazio da lasciare per il successivo
//
//        }
//        else//(!node.isLeaf()){
//            node.getGraphic().setLocation(HORIZONTAL_OFFSET + (last-first) / 2, getY(node));
//            //node.getGraphic().setLocation(first+ (node.getChildrenCount()-1)*(WIDTH+HORIZONTAL_SPACE)/2, getY(node)); Questo approccio non funziona su più livelli, purtroppo
//
////        System.out.println("first"+first);
////        System.out.println("count"+node.getChildrenCount());
//
//        System.out.println(first+ (node.getChildrenCount()-1)*(WIDTH+HORIZONTAL_SPACE)/2);
//        //}
////        {
////            //se ultimo dei fratelli, command che fa un drawLine()?
////            node.getGraphic().setLocation(x_father, getY(node));
////        }
//
//
//        //mantengo uno stack di interi che mi rappresenta il numero di figli che ogni nodo ha, faccio una pop qui e continuo ad aggiungere a destra finchè ho ancora fratelli, quando ne manca uno, faccio la media
//        //alternativa: mantengo un metodo getNumeroFratelli() e sfrutto quello, però forse lo stack mi serve comunque
//
//    }


    private static int getY(Node n) {
        return n.getHeight() * (VERTICAL_SPACE+HEIGHT) + VERTICAL_OFFSET;
    }

    public int getHeight() {return height;}
    public int getWidth() {return width;}
}
