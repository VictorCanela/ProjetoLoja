/**
 * 🛒 Sistema de Loja Online - POO Java
 * Classe principal que gerencia todo o sistema
 * Demonstra uso de Collections (HashMap, ArrayList)
 */

import java.util.*;

public class Loja {
    
    private String nome;
    private Map<Integer, Produto> produtos;
    private Map<Integer, Cliente> clientes;
    private Map<Integer, Pedido> pedidos;
    
    public Loja(String nome) {
        this.nome = nome;
        this.produtos = new HashMap<>();
        this.clientes = new HashMap<>();
        this.pedidos = new HashMap<>();
    }
    
    // ===== PRODUTOS =====
    
    public void adicionarProduto(Produto produto) {
        produtos.put(produto.getId(), produto);
        System.out.println("✅ Produto adicionado: " + produto.getNome());
    }
    
    public Produto buscarProduto(int id) {
        return produtos.get(id);
    }
    
    public List<Produto> listarProdutos() {
        return new ArrayList<>(produtos.values());
    }
    
    // ===== CLIENTES =====
    
    public void cadastrarCliente(Cliente cliente) {
        clientes.put(cliente.getId(), cliente);
        System.out.println("✅ Cliente cadastrado: " + cliente.getNome());
    }
    
    public Cliente buscarCliente(int id) {
        return clientes.get(id);
    }
    
    // ===== PEDIDOS =====
    
    public Pedido criarPedido(Cliente cliente) {
        int novoId = pedidos.size() + 1;
        Pedido pedido = new Pedido(novoId, cliente);
        pedidos.put(novoId, pedido);
        System.out.println("✅ Pedido #" + novoId + " criado para " + cliente.getNome());
        return pedido;
    }
    
    public void processarPedido(Pedido pedido) {
        System.out.println("\n🔄 Processando pedido #" + pedido.getId() + "...");
        
        try {
            // Valida estoque de TODOS os itens primeiro
            for (ItemPedido item : pedido.getItens()) {
                Produto produto = item.getProduto();
                if (produto.getEstoque() < item.getQuantidade()) {
                    throw new EstoqueInsuficienteException(
                        "Produto '" + produto.getNome() + "' com estoque insuficiente"
                    );
                }
            }
            
            // Diminui estoque apenas após validação completa
            for (ItemPedido item : pedido.getItens()) {
                item.getProduto().diminuirEstoque(item.getQuantidade());
            }
            
            pedido.setStatus("PROCESSADO");
            System.out.println("✅ Pedido #" + pedido.getId() + " processado com sucesso!");
            System.out.println("💰 Valor total: R$" + String.format("%.2f", pedido.calcularTotal()));
            
        } catch (EstoqueInsuficienteException e) {
            pedido.setStatus("CANCELADO");
            System.err.println("❌ Falha ao processar pedido: " + e.getMessage());
            System.err.println("⚠️ Pedido #" + pedido.getId() + " foi cancelado.");
        }
    }
    
    public void exibirRelatorioPedidos() {
        System.out.println("\n📊 RELATÓRIO DE PEDIDOS - " + nome);
        System.out.println("=".repeat(50));
        
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido realizado.");
            return;
        }
        
        for (Pedido pedido : pedidos.values()) {
            System.out.println(pedido);
            for (ItemPedido item : pedido.getItens()) {
                System.out.println("  └─ " + item);
            }
        }
        System.out.println("=".repeat(50));
    }
    
    public void exibirEstoque() {
        System.out.println("\n📦 ESTOQUE ATUAL - " + nome);
        System.out.println("=".repeat(50));
        
        for (Produto p : produtos.values()) {
            String status = p.getEstoque() < 5 ? "⚠️ BAIXO" : "✅";
            System.out.printf("%s %s - Estoque: %d unidades\n", 
                            status, p.getNome(), p.getEstoque());
        }
        System.out.println("=".repeat(50));
    }
}