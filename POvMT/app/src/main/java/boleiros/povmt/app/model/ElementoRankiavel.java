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

    public int getProporcao() {
        return proporcao;
    }

    public void setProporcao(int proporcao) {
        this.proporcao = proporcao;
    }

    private int proporcao;

    public ElementoRankiavel(String nome, int horas, int proporcao) {
        this.nome = nome;
        this.horas = horas;
        this.proporcao = proporcao;

    }



    @Override

    public String toString(){
        return getNome()+": "+getHoras()+"Prop: "+getProporcao();
    }
}
