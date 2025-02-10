package view;

import controller.EmprestimoLivroController;
import model.LivroModel;
import model.UsuarioModel;
import repository.LivroRepository;
import repository.UsuarioRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EmprestimoLivroView extends JFrame {

    private JTable tableUsuario;
    private JTable tableLivro;
    private JPanel panel1;
    private JSpinner spinnerQuantidade;
    private JButton enviarButton;
    private JButton devolucaoButton;
    private JFormattedTextField dataDevolucaoField;

    public EmprestimoLivroView() {
        inicializarComponentes();
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void inicializarComponentes() {
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        setContentPane(panel1);

        enviarButton = new JButton("Realizar Empréstimo");
        devolucaoButton = new JButton("Realizar Devolução");

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2));
        buttonsPanel.add(enviarButton);
        buttonsPanel.add(devolucaoButton);
        panel1.add(buttonsPanel, BorderLayout.NORTH);

        tableUsuario = new JTable();
        tableLivro = new JTable();

        JScrollPane scrollPaneUsuario = new JScrollPane(tableUsuario);
        JScrollPane scrollPaneLivro = new JScrollPane(tableLivro);

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(1, 2));
        tablePanel.add(scrollPaneUsuario);
        tablePanel.add(scrollPaneLivro);
        panel1.add(tablePanel, BorderLayout.CENTER);

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 5, 1);
        spinnerQuantidade = new JSpinner(spinnerModel);
        JPanel spinnerPanel = new JPanel();
        spinnerPanel.add(new JLabel("Quantidade:"));
        spinnerPanel.add(spinnerQuantidade);

        try {
            MaskFormatter dataMask = new MaskFormatter("##/##/####");
            dataDevolucaoField = new JFormattedTextField(dataMask);
            dataDevolucaoField.setColumns(10);

            JPanel dataPanel = new JPanel();
            dataPanel.add(new JLabel("Data de Devolução (dd/MM/yyyy):"));
            dataPanel.add(dataDevolucaoField);

            spinnerPanel.add(dataPanel);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        panel1.add(spinnerPanel, BorderLayout.SOUTH);

        preencherTabelaUsuarios();
        preencherTabelaLivros();

        enviarButton.addActionListener(e -> {
            int selectedUsuarioRow = tableUsuario.getSelectedRow();
            int selectedLivroRow = tableLivro.getSelectedRow();

            if (selectedUsuarioRow == -1 || selectedLivroRow == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um usuário e um livro.");
                return;
            }

            Long usuarioId = (Long) tableUsuario.getValueAt(selectedUsuarioRow, 0);
            Long livroId = (Long) tableLivro.getValueAt(selectedLivroRow, 0);
            int quantidadeLivros = (int) spinnerQuantidade.getValue();

            try {
                realizarEmprestimo(usuarioId, livroId, quantidadeLivros);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao realizar empréstimo: " + ex.getMessage());
            }
        });

        devolucaoButton.addActionListener(e -> {
            int selectedUsuarioRow = tableUsuario.getSelectedRow();
            int selectedLivroRow = tableLivro.getSelectedRow();

            if (selectedUsuarioRow == -1 || selectedLivroRow == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um usuário e um livro.");
                return;
            }

            Long usuarioId = (Long) tableUsuario.getValueAt(selectedUsuarioRow, 0);
            Long livroId = (Long) tableLivro.getValueAt(selectedLivroRow, 0);

            String dataTexto = dataDevolucaoField.getText();
            if (dataTexto.isEmpty() || !dataTexto.matches("\\d{2}/\\d{2}/\\d{4}")) {
                JOptionPane.showMessageDialog(this, "Data inválida. Por favor, insira uma data no formato dd/MM/yyyy.");
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataDevolucao = LocalDate.parse(dataTexto, formatter);

            try {
                realizarDevolucao(usuarioId, livroId, dataDevolucao);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao realizar devolução: " + ex.getMessage());
            }
        });
    }

    private void preencherTabelaUsuarios() {
        List<UsuarioModel> usuarios = UsuarioRepository.getInstance().buscarUsuario();

        String[] colunas = {"ID", "Nome", "Email"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        for (UsuarioModel usuario : usuarios) {
            Object[] row = {usuario.getIdUsuario(), usuario.getNome(), usuario.getEmail()};
            model.addRow(row);
        }

        tableUsuario.setModel(model);
    }

    private void preencherTabelaLivros() {
        List<LivroModel> livros = LivroRepository.getInstance().buscarLivros();

        String[] colunas = {"ID", "Título", "Autor", "Quantidade"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        for (LivroModel livro : livros) {
            Object[] row = {livro.getIdLivro(), livro.getTitulo(), livro.getAutor(), livro.getQuantidade()};
            model.addRow(row);
        }

        tableLivro.setModel(model);
    }

    private void realizarEmprestimo(Long usuarioId, Long livroId, int quantidadeLivros) throws SQLException {
        EmprestimoLivroController controller = new EmprestimoLivroController();
        String resultado = controller.realizarEmprestimo(livroId, usuarioId, quantidadeLivros);
        JOptionPane.showMessageDialog(this, resultado);
        preencherTabelaLivros();
    }

    private void realizarDevolucao(Long usuarioId, Long livroId, LocalDate dataDevolucao) throws SQLException {
        EmprestimoLivroController controller = new EmprestimoLivroController();
        controller.realizarDevolucao(usuarioId, livroId, dataDevolucao);
        preencherTabelaLivros();
    }
}
