package boleiros.povmt.app.model;

/**
 * Criado por Filipe Ramos em 03/06/14 as 22.
 */
public class ElementoRankiavel {
    private static final int NUMERO_DE_MINUTOS_EM_UMA_HORA = 60;
    private String nome;
    private int horas;
    private String prioridade;
    private double proporcao;

    public ElementoRankiavel(String nome, int horas, double proporcao , String prioridade) {
        this.nome = nome;
        this.horas = horas;
        this.proporcao = proporcao;
        this.prioridade = prioridade;
    }

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

    public double getProporcao() {
        return proporcao;
    }

    public void setProporcao(double proporcao) {
        this.proporcao = proporcao;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public void somaMinutos(int valor){
        setHoras(this.horas+valor);
    }
    public void somaProp(double valor){
        setProporcao(this.proporcao+valor);
    }

    @Override
    public  boolean equals(Object el){
        if(el == null){
            return false;
        }
        if (!(el instanceof ElementoRankiavel)) {
            return false;
        }
        if(((ElementoRankiavel) el).getNome().equalsIgnoreCase(this.nome)){
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        String prop = ""+(getProporcao()*100);
        prop = prop.substring(0,4);
        int numHoras = getHoras();
        if(numHoras%NUMERO_DE_MINUTOS_EM_UMA_HORA == 0) {
            if (numHoras/NUMERO_DE_MINUTOS_EM_UMA_HORA == 1) {
                return getNome() + ": " + getHoras()/NUMERO_DE_MINUTOS_EM_UMA_HORA + " hora | Prop: " + prop + " %" + " | " + getPrioridade();
            }
            return getNome() + ": " + getHoras()/NUMERO_DE_MINUTOS_EM_UMA_HORA + " horas | Prop: " + prop + " %"+ " | " + getPrioridade();
        } else{
            int numMinutos = numHoras%NUMERO_DE_MINUTOS_EM_UMA_HORA;
            if (numHoras/NUMERO_DE_MINUTOS_EM_UMA_HORA == 1) {
                return getNome() + ": " + getHoras()/NUMERO_DE_MINUTOS_EM_UMA_HORA + " hora e " + numMinutos +" minutos | Prop: " + prop + " %"+ " | " + getPrioridade();
            }
            return getNome() + ": " + getHoras()/NUMERO_DE_MINUTOS_EM_UMA_HORA + " horas e " + numMinutos +" minutos | Prop: " + prop + " %"+ " | " + getPrioridade();
        }
    }
    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42; // any arbitrary constant will do
    }
}

