package view;

import controller.LivroController;
import model.LivroModel;
import repository.LivroRepository;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;
import java.util.List;

public class BuscarLivroView extends JFrame {
    private LivroRepository livroRepository = new LivroRepository();
    private JPanel jPanelPrincipal;
    private JTextField textFieldBuscar;
    private JButton buscarButton;
    private JButton removerButton;
    private JScrollPane scrollPaneBusca;
    private JButton editarButton;
    private JButton voltarButton;
    private JTable tableBuscaLivro;

    public BuscarLivroView() {
        this.setTitle("Biblioteca");

        jPanelPrincipal = new JPanel();
        textFieldBuscar = new JTextField(20);
        buscarButton = new JButton("Buscar");
        removerButton = new JButton("Remover");
        editarButton = new JButton("Editar");
        voltarButton = new JButton("Voltar");

        tableBuscaLivro = new JTable();
        scrollPaneBusca = new JScrollPane(tableBuscaLivro);
        jPanelPrincipal.add(textFieldBuscar);
        jPanelPrincipal.add(buscarButton);
        jPanelPrincipal.add(removerButton);
        jPanelPrincipal.add(editarButton);
        jPanelPrincipal.add(voltarButton);
        jPanelPrincipal.add(scrollPaneBusca);

        BuscaTabelaLivro buscaTabelaLivro = new BuscaTabelaLivro();
        tableBuscaLivro.setModel(buscaTabelaLivro);
        tableBuscaLivro.setAutoCreateRowSorter(true);

        this.setContentPane(jPanelPrincipal);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);

        buscarButton.addActionListener(e -> {
            BuscaTabelaLivro buscarLivro = new BuscaTabelaLivro(textFieldBuscar.getText());
            tableBuscaLivro.setModel(buscarLivro);
        });

        removerButton.addActionListener(e -> {

            LivroController livroController = new LivroController();


            int linhaSelecionada = tableBuscaLivro.getSelectedRow();
            if (linhaSelecionada != -1) {

                Long idDoLivroSelecionado = Long.parseLong(tableBuscaLivro.getValueAt(linhaSelecionada, 0).toString());


                try {
                    String resultado = livroController.remover(idDoLivroSelecionado);
                    JOptionPane.showMessageDialog(null, resultado);


                    tableBuscaLivro.setModel(buscaTabelaLivro);

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao remover livro: " + ex.getMessage());
                }
            } else {
                // Exibe uma mensagem se o usuário não selecionou um livro
                JOptionPane.showMessageDialog(null, "Selecione o livro que deseja remover");
            }
        });

        editarButton.addActionListener(e -> {
            int linhaSelecionada = tableBuscaLivro.getSelectedRow();
            if (linhaSelecionada != -1) {
                try {
                    Long idDoLivroSelecionado = Long.parseLong(tableBuscaLivro.getValueAt(linhaSelecionada, 0).toString());
                    LivroModel livro = livroRepository.buscarPorId(idDoLivroSelecionado);
                    if (livro != null) {
                        String novoTitulo = JOptionPane.showInputDialog("Novo título", livro.getTitulo());
                        if (novoTitulo != null && !novoTitulo.isEmpty()) {
                            livro.setTitulo(novoTitulo);
                            try {
                                String resultado = livroRepository.EditarLivro(livro);
                                JOptionPane.showMessageDialog(null, resultado);
                                BuscaTabelaLivro buscaTabelaLivro1 = new BuscaTabelaLivro();
                                tableBuscaLivro.setModel(buscaTabelaLivro1);
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Erro ao editar livro: " + ex.getMessage());
                            }
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao converter ID do livro para Long");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione o livro que deseja editar");
            }
        });

        voltarButton.addActionListener(e -> dispose());
    }

    private static class BuscaTabelaLivro extends AbstractTableModel {
        private LivroRepository livroRepository = new LivroRepository();
        private final String[] COLUMNS = new String[]{"Id", "Autor", "Data", "Isbn", "Quantidade", "Titulo", "Tema"};
        private List<LivroModel> listaLivros = livroRepository.buscarLivros();

        @Override
        public int getRowCount() {
            return listaLivros.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> listaLivros.get(rowIndex).getIdLivro();
                case 1 -> listaLivros.get(rowIndex).getAutor();
                case 2 -> listaLivros.get(rowIndex).getData();
                case 3 -> listaLivros.get(rowIndex).getIsbn();
                case 4 -> listaLivros.get(rowIndex).getQuantidade();
                case 5 -> listaLivros.get(rowIndex).getTitulo();
                case 6 -> listaLivros.get(rowIndex).getTema();
                default -> "-";
            };
        }

        @Override
        public String getColumnName(int columnIndex) {
            return COLUMNS[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            } else {
                return Object.class;
            }
        }

        public BuscaTabelaLivro(String autor) {
            this.listaLivros = LivroRepository.getInstance().buscarLivrosAutor(autor);
        }

        public BuscaTabelaLivro() {
            this.listaLivros = LivroRepository.getInstance().buscarLivros();
        }
    }
}
