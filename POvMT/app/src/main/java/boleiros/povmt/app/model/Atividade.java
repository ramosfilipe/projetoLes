package boleiros.povmt.app.model;

public class Atividade {
    private int id;
    private String nome;

    public int getId() {
        return id;
    }

    public void setId(int id) throws Exception {
        if(id < 0)
            throw new Exception("id inválido");
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws Exception {
        if(nome == null || nome.trim().equals(""))
            throw new Exception("nome inválido");
        this.nome = nome;
    }
}
