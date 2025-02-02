package controller;

import model.LivroModel;
import repository.LivroRepository;

import java.sql.SQLException;
import java.util.List;


public class LivroController
{
    private LivroRepository livroRepository = new LivroRepository();

    public String salvar(LivroModel livro) throws SQLException
    {
        return livroRepository.salvar(livro);
    }

    public List<LivroModel> buscarLivros() throws SQLException
    {
        return livroRepository.buscarLivros();
    }
    public String remover(Long idLivroSelecionado) throws SQLException {
        return livroRepository.Remover(idLivroSelecionado);
    }


   public String Editar(Long idLivro) throws  SQLException
   {
       LivroModel livro = livroRepository.buscarPorId(idLivro);
       return livroRepository.EditarLivro(livro);
   }

}
