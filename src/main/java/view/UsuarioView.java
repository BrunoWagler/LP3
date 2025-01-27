package view;

import controller.UsuarioController;
import model.UsuarioModel;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

public class UsuarioView extends JFrame {
    private JPanel JPanelUsuario;
    private JButton button1;
    private JPanel panel1;
    private JTextField textFieldNome;
    private JRadioButton masculinoRadioButton;
    private JRadioButton femininoRadioButton;
    private JFormattedTextField formattedTextFieldCelular;
    private JFormattedTextField formattedTextFieldEmail;
    UsuarioController usuarioController = new UsuarioController();

    public UsuarioView() {
        MaskFormatter mascaraCelular = null;
        MaskFormatter mascaraEmail = null;

        this.setContentPane(panel1);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);


        try {
            mascaraCelular = new MaskFormatter("(##)####-####");
            mascaraCelular.setPlaceholder("_");


            mascaraEmail = new MaskFormatter("************************@********.***");
            mascaraEmail.setPlaceholder("_");


            formattedTextFieldCelular.setFormatterFactory(new DefaultFormatterFactory(mascaraCelular));
            formattedTextFieldEmail.setFormatterFactory(new DefaultFormatterFactory(mascaraEmail));

        } catch (ParseException e) {
            e.printStackTrace();
        }


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioModel usuarioModel = new UsuarioModel();
                usuarioModel.setNome(textFieldNome.getText());
                usuarioModel.setTelefone(formattedTextFieldCelular.getText());
                usuarioModel.setEmail(formattedTextFieldEmail.getText());


                if (masculinoRadioButton.isSelected()) {
                    usuarioModel.setSexo("Masculino");
                } else if (femininoRadioButton.isSelected()) {
                    usuarioModel.setSexo("Feminino");
                }


                try {
                    JOptionPane.showMessageDialog(null, usuarioController.salvar(usuarioModel));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao salvar usuário: " + ex.getMessage());
                }
            }
        });


    }}
