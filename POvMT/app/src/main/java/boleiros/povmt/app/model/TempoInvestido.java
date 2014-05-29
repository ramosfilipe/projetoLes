package boleiros.povmt.app.model;

import java.util.Date;

/**
 * Created by Walter on 28/05/2014.
 */
public class TempoInvestido {
    private int id;
    private Date dataInicio;
    private int tempoInvestido;
    private int idAtividade;

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

    public int getTempoInvestido() {
        return tempoInvestido;
    }

    public void setTempoInvestido(int tempoInvestido) throws Exception {
        if(tempoInvestido <= 0)
            throw new Exception("tempo investido invalido");
        this.tempoInvestido = tempoInvestido;
    }

    public int getIdAtividade() {
        return idAtividade;
    }

    public void setIdAtividade(int idAtividade) throws Exception {
        if(idAtividade < 0)
            throw new Exception("id da atividade inválido");
        this.idAtividade = idAtividade;
    }
}
