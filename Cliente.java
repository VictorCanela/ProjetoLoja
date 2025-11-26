/**
 * 🛒 Sistema de Loja Online - POO Java
 * Classe que representa um cliente da loja
 */

public class Cliente {
    
    private int id;
    private String nome;
    private String email;
    private String endereco;
    
    public Cliente(int id, String nome, String email, String endereco) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
    }
    
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getEndereco() { return endereco; }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return String.format("Cliente[ID=%d, Nome='%s', Email='%s']", 
                            id, nome, email);
    }
}