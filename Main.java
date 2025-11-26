/**
 * 🛒 Sistema de Loja Online - POO Java
 * Classe principal para executar e testar o sistema
 * 
 * COMPILAR: javac *.java
 * EXECUTAR: java Main
 */

public class Main {
    
    public static void main(String[] args) {
        System.out.println("🛒 SISTEMA DE LOJA ONLINE - POO em Java");
        System.out.println("=".repeat(60));
        
        // 1. Criar a loja
        Loja loja = new Loja("TechStore Brasil");
        
        // 2. Cadastrar produtos
        System.out.println("\n📦 CADASTRANDO PRODUTOS...");
        Produto p1 = new Produto(1, "Notebook Dell", 3500.00, 10);
        Produto p2 = new Produto(2, "Mouse Logitech", 150.00, 50);
        Produto p3 = new Produto(3, "Teclado Mecânico", 450.00, 3);
        Produto p4 = new Produto(4, "Monitor LG 24\"", 800.00, 15);
        
        loja.adicionarProduto(p1);
        loja.adicionarProduto(p2);
        loja.adicionarProduto(p3);
        loja.adicionarProduto(p4);
        
        // 3. Cadastrar clientes
        System.out.println("\n👥 CADASTRANDO CLIENTES...");
        Cliente c1 = new Cliente(1, "Ana Silva", "ana@email.com", "Rua A, 123");
        Cliente c2 = new Cliente(2, "Carlos Souza", "carlos@email.com", "Av. B, 456");
        
        loja.cadastrarCliente(c1);
        loja.cadastrarCliente(c2);
        
        // 4. Criar pedido bem-sucedido
        System.out.println("\n🛍️ CRIANDO PEDIDO 1...");
        Pedido pedido1 = loja.criarPedido(c1);
        pedido1.adicionarItem(p1, 1);
        pedido1.adicionarItem(p2, 2);
        loja.processarPedido(pedido1);
        
        // 5. Exibir estoque
        loja.exibirEstoque();
        
        // 6. Criar pedido que deve falhar (estoque insuficiente)
        System.out.println("\n🛍️ CRIANDO PEDIDO 2 (deve falhar)...");
        Pedido pedido2 = loja.criarPedido(c2);
        pedido2.adicionarItem(p3, 5); // Tenta comprar 5, mas só há 3
        loja.processarPedido(pedido2);
        
        // 7. Criar pedido com quantidade válida
        System.out.println("\n🛍️ CRIANDO PEDIDO 3...");
        Pedido pedido3 = loja.criarPedido(c2);
        pedido3.adicionarItem(p3, 2);
        pedido3.adicionarItem(p4, 1);
        loja.processarPedido(pedido3);
        
        // 8. Relatórios finais
        loja.exibirRelatorioPedidos();
        loja.exibirEstoque();
        
        System.out.println("\n✅ Sistema executado com sucesso!");
    }
}