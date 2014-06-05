package boleiros.povmt.app.model;

/**
 * Criado por Filipe Ramos em 03/06/14 as 22.
 */
public class ElementoRankiavel {
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    private String nome;

    private int horas;

    public double getProporcao() {
        return proporcao;
    }

    public void setProporcao(double proporcao) {
        this.proporcao = proporcao;
    }

    private double proporcao;

    public ElementoRankiavel(String nome, int horas, double proporcao) {
        this.nome = nome;
        this.horas = horas;
        this.proporcao = proporcao;

    }



    @Override

    public String toString(){
        String prop = ""+(getProporcao()*100);
        prop = prop.substring(0,4);
        return getNome()+": "+getHoras()+"  Prop: "+prop+" %";
    }
}
