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
        this.setContentPane(panel1);
        this.setSize(640,480);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    public void criacaoDoMenu()
    {
        this.setJMenuBar(JmenuBarPrincipal);
        JMenu arquivo = new JMenu("Arquivo");
        JMenu menu = new JMenu("Cadastrar:");
        JMenuItem cadastroLivro = new JMenuItem("Livro");
        JMenuItem cadastroUsuario = new JMenuItem("Usuario");
        menu.add(cadastroLivro);
        menu.add(cadastroUsuario);
        JmenuBarPrincipal.add(menu);
        cadastroLivro.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                LivroView livroView = new LivroView();
            }
        });
    }
}





