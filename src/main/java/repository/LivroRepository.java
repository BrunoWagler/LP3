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

    public String Remover(LivroModel livro)
    {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.remove(livro);
            entityManager.getTransaction().commit();
            return "Removido";
        }catch(Exception e)
        {
            return e.getMessage();
        }

    }

}