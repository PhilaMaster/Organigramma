package diagram;

public class CompositeDiagram extends AbstractDiagram {

    private String nome;

    public CompositeDiagram(int altezza, String nome) {
        super(altezza);
        this.nome = nome;
    }

    public String getNome() {return this.nome;}
    public void setNome(String nome) {this.nome = nome;}


}
