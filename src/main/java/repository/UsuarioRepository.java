package repository;

import model.UsuarioModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository
{
    private static UsuarioRepository instance;
    protected EntityManager entityManager;
    public UsuarioRepository()
    {
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
    public static UsuarioRepository getInstance()
    {
        if (instance == null)
        {
            instance = new UsuarioRepository();
        }
        return instance;
    }

    public String salvar(UsuarioModel usuario) throws SQLException
    {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.persist(usuario);
            entityManager.getTransaction().commit();
            return "Salvo!";
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
    }

    public List<UsuarioModel> buscarUsuario()
    {
        try
        {
            List<UsuarioModel> usuario = entityManager.createQuery("From UsuarioModel").getResultList();
            return usuario;
        }
        catch (Exception e)
        {
            return new ArrayList<>();
        }
    }

    public String Remover(UsuarioModel livro)
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

    public UsuarioModel buscarPorId(Long id)
    {
        UsuarioModel livro = new UsuarioModel();
        try
        {
            livro = entityManager.find(UsuarioModel.class, id);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return livro;
    }

    public String EditarUsuario(UsuarioModel usuarioModel)
    {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.merge(usuarioModel);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return "Editado!";
    }

}


