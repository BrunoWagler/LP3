package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal extends JFrame {
    private JPanel panel1;
    private JPanel JpanelPrincipal; // Declarando o painel
    private JMenuBar JmenuBarPrincipal = new JMenuBar();

    public Principal() {
        JpanelPrincipal = new JPanel(); // Inicializando o painel
        criacaoDoMenu(); // Configura o menu
        this.setContentPane(JpanelPrincipal); // Define o painel como o contentPane
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void criacaoDoMenu() {
        this.setJMenuBar(JmenuBarPrincipal);
        JMenu menu = new JMenu("Cadastrar:");
        JMenuItem cadastroLivro = new JMenuItem("Livro");
        JMenuItem cadastroUsuario = new JMenuItem("Usuario");

        JMenu menuBusca = new JMenu("Buscar: ");
        JMenuItem buscaLivro = new JMenuItem("Livro");
        JMenuItem buscaUsuario = new JMenuItem("Usuario");

        JMenu menuEmprestimo = new JMenu("Emprestimo: ");
        JMenuItem emprestimo = new JMenuItem("Realizar Emprestimo");

        menu.add(cadastroLivro);
        menu.add(cadastroUsuario);
        menuBusca.add(buscaLivro);
        menuBusca.add(buscaUsuario);
        menuEmprestimo.add(emprestimo);
        JmenuBarPrincipal.add(menu);
        JmenuBarPrincipal.add(menuBusca);
        JmenuBarPrincipal.add(menuEmprestimo);

        cadastroUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioView usuarioView = new UsuarioView();
            }
        });

        buscaUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuscarUsuarioView usuarioView = new BuscarUsuarioView();
            }
        });

        cadastroLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LivroView livroView = new LivroView();
            }
        });

        buscaLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuscarLivroView buscar = new BuscarLivroView();
            }
        });

        emprestimo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmprestimoLivroView emprestimoView = new EmprestimoLivroView();
            }
        });
    }
}
