package view;

import controller.UsuarioController;
import model.UsuarioModel;
import repository.UsuarioRepository;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;
import java.util.List;

public class BuscarUsuarioView extends JFrame {
    private JPanel jPanelPrincipal;
    private JTextField textFieldBuscar;
    private JButton buscarButton;
    private JButton removerButton;
    private JButton voltarButton;
    private JButton editarButton;
    private JTable tableBuscaUsuario;

    public BuscarUsuarioView() {
        this.setTitle("Usuarios");

        jPanelPrincipal = new JPanel();
        textFieldBuscar = new JTextField(20);
        buscarButton = new JButton("Buscar");
        removerButton = new JButton("Remover");
        editarButton = new JButton("Editar");
        voltarButton = new JButton("Voltar");

        tableBuscaUsuario = new JTable();
        JScrollPane scrollPaneBusca = new JScrollPane(tableBuscaUsuario);
        jPanelPrincipal.add(textFieldBuscar);
        jPanelPrincipal.add(buscarButton);
        jPanelPrincipal.add(removerButton);
        jPanelPrincipal.add(editarButton);
        jPanelPrincipal.add(voltarButton);
        jPanelPrincipal.add(scrollPaneBusca);

        BuscaTabelaUsuario buscaTabelaUsuario = new BuscaTabelaUsuario();
        tableBuscaUsuario.setModel(buscaTabelaUsuario);
        tableBuscaUsuario.setAutoCreateRowSorter(true);

        this.setContentPane(jPanelPrincipal);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);

        buscarButton.addActionListener(e -> {
            BuscaTabelaUsuario buscaTabelaUsuario1 = new BuscaTabelaUsuario(textFieldBuscar.getText());
            tableBuscaUsuario.setModel(buscaTabelaUsuario1);
        });

        removerButton.addActionListener(e -> {

            UsuarioController usuarioController = new UsuarioController();

            int linhaSelecionada = tableBuscaUsuario.getSelectedRow();
            if (linhaSelecionada != -1) {
                Long idDoUsuarioSelecionado = Long.parseLong(tableBuscaUsuario.getValueAt(linhaSelecionada, 0).toString());

                try {

                    String resultado = usuarioController.remover(idDoUsuarioSelecionado);
                    JOptionPane.showMessageDialog(null, resultado);


                    BuscaTabelaUsuario buscaTabelaUsuario1 = new BuscaTabelaUsuario();
                    tableBuscaUsuario.setModel(buscaTabelaUsuario);

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao remover usuário: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione o usuário que deseja remover");
            }
        });

        editarButton.addActionListener(e -> {
            int linhaSelecionada = tableBuscaUsuario.getSelectedRow();
            if (linhaSelecionada != -1) {
                Long idDoUsuarioSelecionado = Long.parseLong(tableBuscaUsuario.getValueAt(linhaSelecionada, 0).toString());
                UsuarioModel usuario = UsuarioRepository.getInstance().buscarPorId(idDoUsuarioSelecionado);
                if (usuario != null) {
                    String novoNome = JOptionPane.showInputDialog("Novo nome", usuario.getNome());
                    if (novoNome != null && !novoNome.isEmpty()) {
                        usuario.setNome(novoNome);
                        try {
                            String resultado = UsuarioRepository.getInstance().EditarUsuario(usuario);
                            JOptionPane.showMessageDialog(null, resultado);

                            // Atualiza a tabela após a edição
                            BuscaTabelaUsuario buscaTabelaUsuario1 = new BuscaTabelaUsuario();
                            tableBuscaUsuario.setModel(buscaTabelaUsuario1);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Erro ao editar usuário: " + ex.getMessage());
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione o usuário que deseja editar");
            }
        });

        voltarButton.addActionListener(e ->
        {tableBuscaUsuario.setModel(buscaTabelaUsuario);
        tableBuscaUsuario.setAutoCreateRowSorter(true);
        });
    }

    private static class BuscaTabelaUsuario extends AbstractTableModel {
        private final String[] COLUMNS = new String[]{"Id", "Nome", "Telefone", "Sexo", "Email"};
        private List<UsuarioModel> listaUsuarios;

        public BuscaTabelaUsuario() {
            this.listaUsuarios = UsuarioRepository.getInstance().buscarUsuario();
        }

        public BuscaTabelaUsuario(String nome)
        {
            this.listaUsuarios = UsuarioRepository.getInstance().buscarUsuarioNome(nome);
        }

        @Override
        public int getRowCount() {
            return listaUsuarios.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return listaUsuarios.get(rowIndex).getIdUsuario();
                case 1:
                    return listaUsuarios.get(rowIndex).getNome();
                case 2:
                    return listaUsuarios.get(rowIndex).getTelefone();
                case 3:
                    return listaUsuarios.get(rowIndex).getSexo();
                case 4:
                    return listaUsuarios.get(rowIndex).getEmail();
                default:
                    return "-";
            }
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
    }

}
