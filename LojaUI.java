import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LojaUI extends JFrame {

    private LojaDAO dao = new LojaDAO();
    private JTabbedPane tabbedPane;

    public LojaUI() {
        setTitle("🛒 Sistema de Loja Online");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.add("Clientes", criarPainelClientes());
        tabbedPane.add("Produtos", criarPainelProdutos());
        tabbedPane.add("Pedidos", criarPainelPedidos()); // Pedidos é mais complexo, faremos visualização

        add(tabbedPane);
    }

    // ================== PAINEL CLIENTES ==================
    private JPanel criarPainelClientes() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Modelo da Tabela (Colunas: ID, Nome, Email, Endereço, EDITAR, EXCLUIR)
        String[] colunas = {"ID", "Nome", "Email", "Endereço", "Ação 1", "Ação 2"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } // Bloqueia edição direta
        };
        JTable table = new JTable(model);
        estilizarTabela(table);

        // Botão Novo
        JButton btnNovo = new JButton("➕ Novo Cliente");
        btnNovo.addActionListener(e -> abrirFormularioCliente(null, model));

        // Evento de Clique na Tabela (Simulando botões)
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (row >= 0) {
                    int id = (int) model.getValueAt(row, 0);
                    if (col == 4) { // Editar
                        String nome = (String) model.getValueAt(row, 1);
                        String email = (String) model.getValueAt(row, 2);
                        String end = (String) model.getValueAt(row, 3);
                        Cliente c = new Cliente(id, nome, email, end);
                        abrirFormularioCliente(c, model);
                    } else if (col == 5) { // Excluir
                        if (JOptionPane.showConfirmDialog(null, "Excluir cliente?") == 0) {
                            try {
                                dao.deletarCliente(id);
                                carregarClientes(model);
                            } catch (Exception ex) { ex.printStackTrace(); }
                        }
                    }
                }
            }
        });

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnNovo, BorderLayout.SOUTH);
        
        carregarClientes(model);
        return panel;
    }

    private void abrirFormularioCliente(Cliente c, DefaultTableModel model) {
        JDialog dialog = new JDialog(this, (c == null ? "Novo" : "Editar") + " Cliente", true);
        dialog.setLayout(new GridLayout(4, 2));
        dialog.setSize(300, 200);

        JTextField txtNome = new JTextField(c != null ? c.getNome() : "");
        JTextField txtEmail = new JTextField(c != null ? c.getEmail() : "");
        JTextField txtEnd = new JTextField(c != null ? c.getEndereco() : "");

        dialog.add(new JLabel("Nome:")); dialog.add(txtNome);
        dialog.add(new JLabel("Email:")); dialog.add(txtEmail);
        dialog.add(new JLabel("Endereço:")); dialog.add(txtEnd);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            try {
                if (c == null) {
                    dao.salvarCliente(new Cliente(0, txtNome.getText(), txtEmail.getText(), txtEnd.getText()));
                } else {
                    dao.atualizarCliente(new Cliente(c.getId(), txtNome.getText(), txtEmail.getText(), txtEnd.getText()));
                }
                carregarClientes(model);
                dialog.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Erro ao salvar: " + ex.getMessage());
            }
        });

        dialog.add(btnSalvar);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void carregarClientes(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            List<Cliente> lista = dao.listarClientes();
            for (Cliente c : lista) {
                model.addRow(new Object[]{c.getId(), c.getNome(), c.getEmail(), c.getEndereco(), "✏️ Editar", "🗑️ Excluir"});
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // ================== PAINEL PRODUTOS ==================
    private JPanel criarPainelProdutos() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] colunas = {"ID", "Nome", "Preço", "Estoque", "Ação 1", "Ação 2"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable table = new JTable(model);
        estilizarTabela(table);

        JButton btnNovo = new JButton("➕ Novo Produto");
        btnNovo.addActionListener(e -> abrirFormularioProduto(null, model));

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (row >= 0) {
                    int id = (int) model.getValueAt(row, 0);
                    if (col == 4) { // Editar
                        Produto p = new Produto(id, 
                            (String) model.getValueAt(row, 1), 
                            (Double) model.getValueAt(row, 2), 
                            (Integer) model.getValueAt(row, 3));
                        abrirFormularioProduto(p, model);
                    } else if (col == 5) { // Excluir
                        if (JOptionPane.showConfirmDialog(null, "Excluir produto?") == 0) {
                            try { dao.deletarProduto(id); carregarProdutos(model); } 
                            catch (Exception ex) { ex.printStackTrace(); }
                        }
                    }
                }
            }
        });

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnNovo, BorderLayout.SOUTH);
        carregarProdutos(model);
        return panel;
    }

    private void abrirFormularioProduto(Produto p, DefaultTableModel model) {
        JDialog dialog = new JDialog(this, (p == null ? "Novo" : "Editar") + " Produto", true);
        dialog.setLayout(new GridLayout(4, 2));
        dialog.setSize(300, 200);

        JTextField txtNome = new JTextField(p != null ? p.getNome() : "");
        JTextField txtPreco = new JTextField(p != null ? String.valueOf(p.getPreco()) : "");
        JTextField txtEstoque = new JTextField(p != null ? String.valueOf(p.getEstoque()) : "");

        dialog.add(new JLabel("Nome:")); dialog.add(txtNome);
        dialog.add(new JLabel("Preço:")); dialog.add(txtPreco);
        dialog.add(new JLabel("Estoque:")); dialog.add(txtEstoque);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            try {
                double preco = Double.parseDouble(txtPreco.getText());
                int estoque = Integer.parseInt(txtEstoque.getText());
                if (p == null) {
                    dao.salvarProduto(new Produto(0, txtNome.getText(), preco, estoque));
                } else {
                    dao.atualizarProduto(new Produto(p.getId(), txtNome.getText(), preco, estoque));
                }
                carregarProdutos(model);
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Erro (verifique números): " + ex.getMessage());
            }
        });

        dialog.add(btnSalvar);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void carregarProdutos(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            List<Produto> lista = dao.listarProdutos();
            for (Produto p : lista) {
                model.addRow(new Object[]{p.getId(), p.getNome(), p.getPreco(), p.getEstoque(), "✏️ Editar", "🗑️ Excluir"});
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // ================== PAINEL PEDIDOS (Visualização) ==================
    private JPanel criarPainelPedidos() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] colunas = {"ID Pedido", "Cliente", "Status", "Data"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        JTable table = new JTable(model);
        
        // Botão para atualizar lista (em um sistema real, seria automático)
        JButton btnAtualizar = new JButton("🔄 Atualizar Lista");
        btnAtualizar.addActionListener(e -> carregarPedidos(model));
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnAtualizar, BorderLayout.SOUTH);
        
        carregarPedidos(model);
        return panel;
    }

    private void carregarPedidos(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            List<Pedido> lista = dao.listarPedidos();
            for (Pedido p : lista) {
                model.addRow(new Object[]{p.getId(), p.getCliente().getNome(), p.getStatus(), p.getDataHora()});
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // ================== UTILITÁRIOS ==================
    private void estilizarTabela(JTable table) {
        table.setRowHeight(25);
        table.getColumnModel().getColumn(4).setMaxWidth(80); // Coluna Editar
        table.getColumnModel().getColumn(5).setMaxWidth(80); // Coluna Excluir
    }

    public static void main(String[] args) {
        // Look and Feel do sistema operacional
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        
        SwingUtilities.invokeLater(() -> {
            new LojaUI().setVisible(true);
        });
    }
}