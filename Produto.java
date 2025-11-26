/**
 * 🛒 Sistema de Loja Online - POO Java
 * Classe que representa um produto à venda
 */

public class Produto {
    
    // Atributos privados (Encapsulamento)
    private int id;
    private String nome;
    private double preco;
    private int estoque;
    
    // Construtor
    public Produto(int id, String nome, double preco, int estoque) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }
    
    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public int getEstoque() { return estoque; }
    
    // Métodos de negócio
    public void diminuirEstoque(int quantidade) throws EstoqueInsuficienteException {
        if (this.estoque < quantidade) {
            throw new EstoqueInsuficienteException(
                "Estoque insuficiente para '" + nome + "'. Disponível: " + 
                estoque + ", Solicitado: " + quantidade
            );
        }
        this.estoque -= quantidade;
    }
    
    public void aumentarEstoque(int quantidade) {
        if (quantidade > 0) {
            this.estoque += quantidade;
        }
    }
    
    @Override
    public String toString() {
        return String.format("Produto[ID=%d, Nome='%s', Preço=R$%.2f, Estoque=%d]", 
                            id, nome, preco, estoque);
    }
}