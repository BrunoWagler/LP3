package repository;

import model.LivroModel;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivroRepository
{
    private static LivroRepository instance;
    protected EntityManager entityManager;
    public LivroRepository() {
        entityManager = getEntityManager();
    }
    private EntityManager getEntityManager()
    {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null)
        {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }
    public static LivroRepository getInstance()
    {
        if (instance == null)
        {
            instance = new LivroRepository();
        }
        return instance;
    }

    public String salvar(LivroModel livro) throws SQLException
    {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.persist(livro);
            entityManager.getTransaction().commit();
            return "Salvo!";
        }
        catch (Exception e)
        {
           return e.getMessage();
        }
    }

    public List<LivroModel> buscarLivros()
    {
        try
        {
            List<LivroModel> livros = entityManager.createQuery("From LivroModel").getResultList();
            return livros;
        }
        catch (Exception e)
        {
            return new ArrayList<>();
        }
    }

    public List<LivroModel> buscarLivrosAutor(String autor)
    {
        try
        {
            List<LivroModel> livros = entityManager.createQuery("From LivroModel where autor like '%"+autor+"%'").getResultList();
            return livros;
        }
        catch (Exception e)
        {
            return new ArrayList<>();
        }
    }

    public String Remover(Long idLivro)
    {
        try
        {

            LivroModel livro = entityManager.find(LivroModel.class, idLivro);

            if (livro != null) {

                entityManager.getTransaction().begin();
                entityManager.remove(livro); // Remove o objeto encontrado
                entityManager.getTransaction().commit();
                return "Livro removido com sucesso!";
            } else {

                return "Livro não encontrado!";
            }
        }
        catch (Exception e)
        {

            e.printStackTrace();
            return "Erro ao remover o livro: " + e.getMessage();
        }
    }


    public LivroModel buscarPorId(Long id)
    {
        LivroModel livro = new LivroModel();
        try
        {
           livro = entityManager.find(LivroModel.class, id);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return livro;
    }

    public String EditarLivro(LivroModel livro)
    {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.merge(livro);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return "Editado!";
    }

}