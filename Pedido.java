/**
 * 🛒 Sistema de Loja Online - POO Java
 * Classe que representa um pedido de compra
 */

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Pedido {
    
    private int id;
    private Cliente cliente;
    private List<ItemPedido> itens;
    private String status;
    private LocalDateTime dataHora;
    
    public Pedido(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.itens = new ArrayList<>();
        this.status = "ABERTO";
        this.dataHora = LocalDateTime.now();
    }
    
    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public List<ItemPedido> getItens() { return new ArrayList<>(itens); }
    public String getStatus() { return status; }
    public LocalDateTime getDataHora() { return dataHora; }
    
    public void adicionarItem(Produto produto, int quantidade) {
        // Verifica se produto já existe no pedido
        for (ItemPedido item : itens) {
            if (item.getProduto().getId() == produto.getId()) {
                item.setQuantidade(item.getQuantidade() + quantidade);
                return;
            }
        }
        // Se não existe, adiciona novo
        itens.add(new ItemPedido(produto, quantidade));
    }
    
    public void removerItem(int produtoId) {
        itens.removeIf(item -> item.getProduto().getId() == produtoId);
    }
    
    // Programação funcional com Streams API
    public double calcularTotal() {
        return itens.stream()
                    .mapToDouble(ItemPedido::getPrecoTotal)
                    .sum();
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return String.format("Pedido #%d - Cliente: %s - Total: R$%.2f - Status: %s", 
                            id, cliente.getNome(), calcularTotal(), status);
    }
}