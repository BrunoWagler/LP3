package controller;

import model.EmprestimoLivroModel;
import model.LivroModel;
import model.UsuarioModel;
import repository.EmprestimoLivroRepository;
import repository.LivroRepository;
import repository.UsuarioRepository;

import javax.swing.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.sql.SQLException;

public class EmprestimoLivroController {

    public void realizarDevolucao(Long usuarioId, Long livroId, LocalDate dataDevolucao) throws SQLException {
        EmprestimoLivroModel emprestimo = EmprestimoLivroRepository.getInstance().buscarEmprestimoPorUsuarioELivro(usuarioId, livroId);

        if (emprestimo == null) {
            JOptionPane.showMessageDialog(null, "Emprestimo não encontrado para este usuario e livro.");
            return;
        }

        LocalDate devolucaoPrevista = emprestimo.getDevolucaoPrevista();

        double multa = 0.0;
        if (dataDevolucao.isAfter(devolucaoPrevista)) {
            long diasAtraso = ChronoUnit.DAYS.between(devolucaoPrevista, dataDevolucao);
            multa = diasAtraso * 1.0;
        }

        emprestimo.setDevolucao(dataDevolucao);
        emprestimo.setMulta(multa);

        String resultado = EmprestimoLivroRepository.getInstance().salvar(emprestimo);
        JOptionPane.showMessageDialog(null, "Devolução realizada com sucesso. Multa: R$ " + multa);

        LivroModel livro = LivroRepository.getInstance().buscarPorId(livroId);
        livro.setQuantidade(livro.getQuantidade() + emprestimo.getQuantidade());
        LivroRepository.getInstance().salvar(livro);
    }

    public String realizarEmprestimo(Long livroId, Long usuarioId, int quantidadeLivros) throws SQLException {
        LivroModel livro = LivroRepository.getInstance().buscarPorId(livroId);
        if (livro == null) {
            return "Livro não encontrado.";
        }

        if (livro.getQuantidade() < quantidadeLivros) {
            return "Não há exemplares suficientes disponíveis para o empréstimo.";
        }

        long livrosEmprestados = EmprestimoLivroRepository.getInstance().buscarEmprestimos(usuarioId).stream()
                .filter(emprestimo -> emprestimo.getDevolucao() == null)
                .count();
        if (livrosEmprestados + quantidadeLivros > 5) {
            return "O usuário não pode pegar mais de 5 livros emprestados ao mesmo tempo.";
        }

        EmprestimoLivroModel emprestimo = new EmprestimoLivroModel();
        UsuarioModel usuario = UsuarioRepository.getInstance().buscarPorId(usuarioId);
        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        emprestimo.setQuantidade(quantidadeLivros);

        LocalDate dataPrevistaDevolucao = LocalDate.now().plusDays(14);
        emprestimo.setDevolucaoPrevista(dataPrevistaDevolucao);

        String resultado = EmprestimoLivroRepository.getInstance().salvar(emprestimo);
        if (resultado.equals("Empréstimo realizado com sucesso!")) {
            livro.setQuantidade(livro.getQuantidade() - quantidadeLivros);
            LivroRepository.getInstance().salvar(livro);

            return "Empréstimo realizado com sucesso! A devolução está prevista para: " + dataPrevistaDevolucao;
        } else {
            return "Erro ao realizar o empréstimo.";
        }
    }
}
