/**
 * 🛒 Sistema de Loja Online - POO Java
 * Exceção customizada para tratamento de estoque insuficiente
 */

public class EstoqueInsuficienteException extends Exception {
    
    // Construtor que recebe mensagem de erro
    public EstoqueInsuficienteException(String mensagem) {
        super(mensagem);
    }
    
    // Construtor com mensagem e causa raiz
    public EstoqueInsuficienteException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}