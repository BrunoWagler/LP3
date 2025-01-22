package view;

import controller.LivroController;
import model.LivroModel;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

public class LivroView extends JFrame
{
    private JPanel JpanelLivro;
    private JTextField textFieldTitulo;
    private JTextField textFieldTema;
    private JTextField textFieldAutor;
    private JTextField textFieldISBN;
    private JSpinner spinnerQuantidade;
    private JButton button1;
    private JFormattedTextField formattedTextFieldData;
    private LivroController livroController = new LivroController();


    public LivroView()
    {
        MaskFormatter mascaratelefone = null;

        this.setContentPane(JpanelLivro);
        this.setSize(640,480);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        try {
            mascaratelefone = new MaskFormatter("##/##/####");
            mascaratelefone.setPlaceholderCharacter('_');

            formattedTextFieldData.setFormatterFactory(new DefaultFormatterFactory(mascaratelefone));

        } catch (ParseException ex) {
            System.out.println("Deu ruim");
        }


        button1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                LivroModel livroModel = new LivroModel();
                livroModel.setTitulo(textFieldTitulo.getText());
                livroModel.setTema(textFieldTema.getText());
                livroModel.setAutor(textFieldAutor.getText());
                livroModel.setIsbn(Integer.parseInt(textFieldISBN.getText()));
                livroModel.setData(formattedTextFieldData.getText());
                livroModel.setQuantidade((Integer) spinnerQuantidade.getValue());
                try {

                    JOptionPane.showMessageDialog(null, livroController.salvar(livroModel));
                }
                catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
    }
}
