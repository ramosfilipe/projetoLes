package boleiros.povmt.app.model;

public class Atividade {
    private int id;
    private String nome;
    private String created_at;
    private String prioridade;

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws Exception {
        if(id < 0) {
            throw new Exception("id inválido");
        }
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws Exception {
        if (nome == null || nome.trim().equals("")) {
            throw new Exception("nome invalido");
        }
        this.nome = nome;
    }

    @Override
    public String toString(){
        return getNome()+"|"+getPrioridade();
    }
}
