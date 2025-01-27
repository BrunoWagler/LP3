package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal extends JFrame
{
    private JPanel panel1;
    private JPanel JpanelPrincipal;
    private JMenuBar JmenuBarPrincipal = new JMenuBar();

    public Principal()
    {
        criacaoDoMenu();
        this.setContentPane(JpanelPrincipal);
        this.setSize(640,480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);

    }

    public void criacaoDoMenu()
    {
        this.setJMenuBar(JmenuBarPrincipal);
        JMenu menu = new JMenu("Cadastrar:");
        JMenuItem cadastroLivro = new JMenuItem("Livro");
        JMenuItem cadastroUsuario = new JMenuItem("Usuario");
        /*/

         */
        JMenu menuBusca = new JMenu("Buscar: ");
        JMenuItem buscaLivro = new JMenuItem("Livro");
        JMenuItem buscaUsuario = new JMenuItem("Usuario");
        /*/

         */

        JMenu menuApagar = new JMenu("Emprestimo: ");
        JMenuItem apagarLivro = new JMenuItem("Livro");
        JMenuItem apagarUsuario = new JMenuItem("Usuario");

        menu.add(cadastroLivro);
        menu.add(cadastroUsuario);
        menuBusca.add(buscaLivro);
        menuBusca.add(buscaUsuario);
        menuApagar.add(apagarLivro);
        menuApagar.add(apagarUsuario);
        JmenuBarPrincipal.add(menu);
        JmenuBarPrincipal.add(menuBusca);
        JmenuBarPrincipal.add(menuApagar);


        cadastroUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                UsuarioView usuarioView = new UsuarioView();
            }
        });

        buscaUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {

            }
        });

        cadastroLivro.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                LivroView livroView = new LivroView();
            }
        });

        buscaLivro.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                BuscarLivroView buscar= new BuscarLivroView();
            }
        });
    }

}





