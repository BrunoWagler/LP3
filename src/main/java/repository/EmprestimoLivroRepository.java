package repository;

import model.EmprestimoLivroModel;
import javax.persistence.*;
import java.util.List;

public class EmprestimoLivroRepository {

    private static EmprestimoLivroRepository instance;
    protected EntityManager entityManager;

    public EmprestimoLivroRepository() {
        entityManager = Persistence.createEntityManagerFactory("crudHibernatePU").createEntityManager();
    }

    public static EmprestimoLivroRepository getInstance() {
        if (instance == null) {
            instance = new EmprestimoLivroRepository();
        }
        return instance;
    }


    public String salvar(EmprestimoLivroModel emprestimo) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (emprestimo.getId() == null) {
                entityManager.persist(emprestimo);
            } else {
                entityManager.merge(emprestimo);
            }
            transaction.commit();
            return "Empréstimo realizado com sucesso!";
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return "Erro ao realizar empréstimo: " + e.getMessage();
        }
    }


    public List<EmprestimoLivroModel> buscarEmprestimos(Long usuarioId) {
        return entityManager.createQuery("FROM EmprestimoLivroModel e WHERE e.usuario.id = :usuarioId", EmprestimoLivroModel.class)
                .setParameter("usuarioId", usuarioId)
                .getResultList();
    }


    public EmprestimoLivroModel buscarEmprestimoPorUsuarioELivro(Long usuarioId, Long livroId) {
        try {
            return entityManager.createQuery("FROM EmprestimoLivroModel e WHERE e.usuario.id = :usuarioId AND e.livro.id = :livroId AND e.devolucao IS NULL", EmprestimoLivroModel.class)
                    .setParameter("usuarioId", usuarioId)
                    .setParameter("livroId", livroId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            List<EmprestimoLivroModel> emprestimos = entityManager.createQuery("FROM EmprestimoLivroModel e WHERE e.usuario.id = :usuarioId AND e.livro.id = :livroId AND e.devolucao IS NULL", EmprestimoLivroModel.class)
                    .setParameter("usuarioId", usuarioId)
                    .setParameter("livroId", livroId)
                    .getResultList();
            return emprestimos.isEmpty() ? null : emprestimos.get(0);
        }
    }


    public List<EmprestimoLivroModel> buscarEmprestimosPendentes() {
        return entityManager.createQuery("FROM EmprestimoLivroModel e WHERE e.devolucao IS NULL", EmprestimoLivroModel.class).getResultList();
    }
}
