/**
 * 🛒 Sistema de Loja Online - POO Java
 * Classe que representa um item dentro de um pedido
 * Demonstra COMPOSIÇÃO: ItemPedido TEM-UM Produto
 */

public class ItemPedido {
    
    private Produto produto;
    private int quantidade;
    
    public ItemPedido(Produto produto, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        this.produto = produto;
        this.quantidade = quantidade;
    }
    
    public Produto getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }
    
    // Método calculado (não armazenamos o total)
    public double getPrecoTotal() {
        return produto.getPreco() * quantidade;
    }
    
    public void setQuantidade(int quantidade) {
        if (quantidade > 0) {
            this.quantidade = quantidade;
        }
    }
    
    @Override
    public String toString() {
        return String.format("%dx %s - Subtotal: R$%.2f", 
                            quantidade, produto.getNome(), getPrecoTotal());
    }
}