package controller;

import model.LivroModel;
import model.UsuarioModel;
import repository.UsuarioRepository;

import java.sql.SQLException;
import java.util.List;

public class UsuarioController
{
    private UsuarioRepository usuarioRepository = new UsuarioRepository();

    public String salvar(UsuarioModel usuario) throws SQLException
    {
        return usuarioRepository.salvar(usuario);
    }

    public List<UsuarioModel> buscarUsuario() throws SQLException
    {
        return usuarioRepository.buscarUsuario();
    }

    public String remover (Long idLivroelecionado) throws SQLException
    {
        UsuarioModel usuario = usuarioRepository.buscarPorId(idLivroelecionado);
        return usuarioRepository.Remover(usuario);
    }

    public String Editar(Long idUsu) throws  SQLException
    {
       UsuarioModel usuarioModel = usuarioRepository.buscarPorId(idUsu);
        return usuarioRepository.EditarUsuario(usuarioModel);
    }

}
