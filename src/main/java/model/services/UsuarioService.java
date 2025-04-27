/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.services;

import java.util.List;
import model.DB.DB;
import model.classes.Usuario;
import model.dao.UsuarioDAO;

/**
 *
 * @author Bruna
 */
public class UsuarioService {

    private UsuarioDAO dao;

    public UsuarioService() {
        dao = new UsuarioDAO(DB.getConnection());
    }

    public List<Usuario> getAll(String pesquisa) {
        return dao.getAll(pesquisa);
    }

    public boolean salvarOuAlterarSenha(Usuario usuario, StringBuilder mensagem) {
        dao = new UsuarioDAO(DB.getConnection());
         mensagem.append("");
        
        if (usuario.getCodUsuario() <= 0) {
            // Inclusão (cadastro novo)

            boolean inserido = dao.inserir(usuario);
            if (inserido) {
                // Após inserir, salva a primeira senha no histórico
                dao.salvarSenhaNaLista(usuario.getCodUsuario(), dao.senhaCriptografada(usuario));
                dao.manterApenasSeteSenhas(usuario.getCodUsuario()); // já garantir que mantém só 7 (não custa)
            }
            return inserido;

        } else {
            // Alteração de senha
            List<String> ultimasSenhas = dao.buscarUltimasSenhas(usuario.getCodUsuario());
            String novaSenhaCriptografada = dao.senhaCriptografada(usuario);

            for (String senhaAntiga : ultimasSenhas) {
                if (senhaAntiga.equals(novaSenhaCriptografada)) {
                    mensagem.append("Senha repetida! Não foi possível cadastrar a nova senha. Por favor, escolha uma senha que não tenha sido usada nas últimas 7 alterações.");
                    return false; // Nova senha repetida!
                }
            }

//            Usuario usuarioAtual = buscarUsuarioPorId(usuario.getCodUsuario());
            if (usuario != null) {
                dao.salvarSenhaNaLista(usuario.getCodUsuario(), dao.senhaCriptografada(usuario));
                dao.manterApenasSeteSenhas(usuario.getCodUsuario());
            } else {
                 mensagem.append("Erro ao alterar senha!");
            }

            return dao.alterarDados(usuario);
        }
    }

    public Usuario fazerLogin(Usuario usuario) {
        return dao.efetuarLogin(usuario);
    }
}
