import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LojaDAO {

    // ===== PRODUTO =====
    public void salvarProduto(Produto p) throws SQLException {
        String sql = "INSERT INTO Produto (nome, preco, estoque) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.setDouble(2, p.getPreco());
            stmt.setInt(3, p.getEstoque());
            stmt.execute();
        }
    }

    public void atualizarProduto(Produto p) throws SQLException {
        String sql = "UPDATE Produto SET nome=?, preco=?, estoque=? WHERE id=?";
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.setDouble(2, p.getPreco());
            stmt.setInt(3, p.getEstoque());
            stmt.setInt(4, p.getId());
            stmt.execute();
        }
    }

    public void deletarProduto(int id) throws SQLException {
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Produto WHERE id=?")) {
            stmt.setInt(1, id);
            stmt.execute();
        }
    }

    public List<Produto> listarProdutos() throws SQLException {
        List<Produto> lista = new ArrayList<>();
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Produto")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Produto(
                    rs.getInt("id"), rs.getString("nome"), 
                    rs.getDouble("preco"), rs.getInt("estoque")
                ));
            }
        }
        return lista;
    }

    // ===== CLIENTE =====
    public void salvarCliente(Cliente c) throws SQLException {
        String sql = "INSERT INTO Cliente (nome, email, endereco) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getEmail());
            stmt.setString(3, c.getEndereco());
            stmt.execute();
        }
    }

    public void atualizarCliente(Cliente c) throws SQLException {
        String sql = "UPDATE Cliente SET nome=?, email=?, endereco=? WHERE id=?";
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getEmail());
            stmt.setString(3, c.getEndereco());
            stmt.setInt(4, c.getId());
            stmt.execute();
        }
    }

    public void deletarCliente(int id) throws SQLException {
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Cliente WHERE id=?")) {
            stmt.setInt(1, id);
            stmt.execute();
        }
    }

    public List<Cliente> listarClientes() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Cliente")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Cliente(
                    rs.getInt("id"), rs.getString("nome"), 
                    rs.getString("email"), rs.getString("endereco")
                ));
            }
        }
        return lista;
    }

    // ===== PEDIDO SIMPLIFICADO (Apenas Listagem para o exemplo caber aqui) =====
    public List<Pedido> listarPedidos() throws SQLException {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.data_hora, p.status, c.id as cid, c.nome, c.email, c.endereco " +
                     "FROM Pedido p JOIN Cliente c ON p.cliente_id = c.id";
        
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente(rs.getInt("cid"), rs.getString("nome"), rs.getString("email"), rs.getString("endereco"));
                Pedido p = new Pedido(rs.getInt("id"), c);
                p.setStatus(rs.getString("status"));
                // Nota: Carregar itens seria uma segunda query aqui
                lista.add(p);
            }
        }
        return lista;
    }
}
