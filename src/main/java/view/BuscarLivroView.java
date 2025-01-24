package view;

import model.LivroModel;
import repository.LivroRepository;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BuscarLivroView extends JFrame {
    private LivroRepository livroRepository = new LivroRepository();
    private JPanel panel1;
    private JTextField textFieldBuscar;
    private JButton buscarButton;
    private JTable JtableBusca;
    private JScrollPane JscrollBuscar;
    private JButton removerButton;


    public BuscarLivroView()
    {
        this.setTitle("Biblioteca");
        BuscaTabelaLivro buscatabelaLivro = new BuscaTabelaLivro();
        JtableBusca.setModel(buscatabelaLivro);
        JtableBusca.setAutoCreateRowSorter(true);
        this.setContentPane(panel1);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);

        buscarButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {


                BuscaTabelaLivro buscarLivro = new BuscaTabelaLivro(textFieldBuscar.getText());
                JtableBusca.setModel(buscarLivro);


            }
        });
    }

    private static class BuscaTabelaLivro extends AbstractTableModel {
        private LivroRepository livroRepository = new LivroRepository();
        private final String[] COLUMNS = new String[]{"Id", "Autor", "Data", "Isbn", "Quantidade", "Titulo", "Tema"};
        private List<LivroModel> listaLivros;

        public BuscaTabelaLivro()
        {
            this.listaLivros = LivroRepository.getInstance().buscarLivros();
        }

        public BuscaTabelaLivro(String autor)
        {
            this.listaLivros = LivroRepository.getInstance().buscarLivrosAutor(autor);
        }

        @Override
        public int getRowCount()
        {
            return listaLivros.size();

    }
        @Override
        public int getColumnCount()
        {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            return switch (columnIndex)
            {
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
        public String getColumnName(int columnIndex)
        {
            return COLUMNS[columnIndex];
        }
        @Override
        public Class<?> getColumnClass(int columnIndex)
        {
            if(getValueAt(0,columnIndex) != null)
            {
                return getValueAt(0, columnIndex).getClass();
            }else
            {
                return Object.class;
            }
        }
    }
}

