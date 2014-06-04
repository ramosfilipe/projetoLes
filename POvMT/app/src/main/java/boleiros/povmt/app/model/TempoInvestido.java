package boleiros.povmt.app.model;

import java.util.Date;

/**
 * Created by Walter on 28/05/2014.
 */
public class TempoInvestido {
    private int id;
    private Date dataInicio;
    private String created_at;
    private int tempoInvestidoMinuto;
    private int idAtividade;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public int getTempoInvestidoMinuto() {
        return tempoInvestidoMinuto;
    }
    public void setTempoInvestidoMinuto(int tempoInvestidoMinuto) {
        this.tempoInvestidoMinuto = tempoInvestidoMinuto;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) throws Exception {
        if(id < 0)
            throw new Exception("id inválido");
        this.id = id;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) throws Exception {
        if(dataInicio == null)
            throw new Exception("data de inicio inválida");
        this.dataInicio = dataInicio;
    }


    public int getIdAtividade() {
        return idAtividade;
    }

    public void setIdAtividade(int idAtividade) throws Exception {
        if(idAtividade < 0)
            throw new Exception("id da atividade inválido");
        this.idAtividade = idAtividade;
    }


    @Override
    public String toString(){
        return ""+getTempoInvestidoMinuto();
    }
}
