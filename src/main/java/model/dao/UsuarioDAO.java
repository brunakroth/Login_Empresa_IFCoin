/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.DB.DB;
import model.classes.Usuario;

/**
 *
 * @author Bruna
 */
public class UsuarioDAO {

    private Connection con;

    public UsuarioDAO(Connection con) {
        this.con = con;
    }

    public List<Usuario> getAll(String pesquisa) {
        //lista temporária dos Usuários
        List<Usuario> list = new ArrayList<>();
        //listagens dos registros que virão do banco
        ResultSet res = null;
        //um statement é um objeto que executa o script sql
        PreparedStatement stmt = null;
        try {
            String sql = "select * from usuario ";

            //preparando a String sql para execução
            stmt = con.prepareStatement(sql);

            //executar o script SQL
            //executa o script e guarda o resultado dentro do res
            res = stmt.executeQuery();
            //percorrer o res e ir criando objetos
            while (res.next()) {
                //obtendo as informações de usuario
                int codUsuario = res.getInt("pk_codUsuario");
                String nomeUsuario = res.getString("nomeUsuario");
                String emailUsuario = res.getString("emailUsuario");
                String senhaUsuario = res.getString("senhaUsuario");
                Date dataCadastroSenha = res.getDate("dataCadastroSenha");

                //criando o objeto do usuario
                Usuario usuario = new Usuario(codUsuario, nomeUsuario, emailUsuario, senhaUsuario, dataCadastroSenha);
                //adicionando esse usuário na lista temporaria
                list.add(usuario);
            }
        } catch (Exception e) {
            //aqui entra quando da erro
            e.printStackTrace();
        } finally {
            //aqui entra sempre, dando erro ou não
            //fehcar as conexões e retornar resultados
            DB.closeStatement(stmt);
            DB.closeResultSet(res);
            return list;
        }
    }

    public boolean inserir(Usuario usuario) {
        PreparedStatement stmt = null;
        boolean result = false;

        try {
            //SQL para inserir 
            String sql = "insert into usuario (nomeUsuario, emailUsuario, senhaUsuario, dataCadastroSenha)"
                    + "values (?, ?, ?, ?)";
            //o return_generate_keys retorna a chave primária gerada no momento do insert
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //trocando as ?
            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setString(2, usuario.getEmailUsuario());
            stmt.setString(3, senhaCriptografada(usuario));
            stmt.setDate(4, (Date) usuario.getDataCadastroSenha());

            //executar o script
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {

                //pegando o codigo gerado no exception
                ResultSet rs = stmt.getGeneratedKeys();

                if (rs.next()) {
                    //getint(1) pega o codigo que foi gerado e que esta no primeiro campo do resultSet
                    int id = rs.getInt(1);
                    //atualiza o codigo do usuário no parâmetro recebido pelo metodo
                    usuario.setCodUsuario(id);
                    result = true;
                    //depois daqui vai para o finally
                }
            } else {
                // falhou a execption faz com que vá para o catch e depois no finally
                throw new SQLException("Não foi possível inserir");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }

    public boolean alterarDados(Usuario usuario) {
        PreparedStatement stmt = null;
        boolean result = false;

        try {
            //SQL para alterarDados 
            String sql = "update usuario set nomeUsuario = ?, emailUsuario = ?, "
                    + "senhaUsuario = ?, dataCadastroSenha = ? where pk_codUsuario = ?";

            stmt = con.prepareStatement(sql);

            int paramIndex = 1;
            //trocando as ?
            stmt.setString(paramIndex++, usuario.getNomeUsuario());
            stmt.setString(paramIndex++, usuario.getEmailUsuario());
            stmt.setString(paramIndex++, senhaCriptografada(usuario));
            stmt.setDate(paramIndex++, (Date) usuario.getDataCadastroSenha());
            stmt.setInt(paramIndex++, usuario.getCodUsuario());
            //executar o script
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }

    public String senhaCriptografada(Usuario user) {
        String senhaHash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5"); // MD5, SHA-1, SHA-256

            BigInteger a = new BigInteger(1, md.digest(user.getSenhaUsuario().getBytes()));
            senhaHash = a.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Erro ao carregar o MessageDigest");
        }

        try {
            return senhaHash;
        } catch (Exception e) {
            e.printStackTrace();
            return senhaHash;
        }
    }

    public Usuario efetuarLogin(Usuario user) {
        //essa variavel ira guardar o usuario selecionado do banco de dados        
        Usuario userSelecionado = null;
        //essa variável prepara o SQL para ser executado
        PreparedStatement stmt = null;

        try {
            String sql = "select * from usuario"
                    + " where emailUsuario = ? and senhaUsuario = ?";
            // preparando o script para ser executado
            stmt = con.prepareStatement(sql);
            //substituir os ? pelo que o usuario digitou na tela
            stmt.setString(1, user.getEmailUsuario());
            stmt.setString(2, senhaCriptografada(user));
            //executar o script SQL

            ResultSet result = stmt.executeQuery();
            // acessando o resultado 
            if (result.next()) {
                userSelecionado = new Usuario(
                        result.getInt("pk_codUsuario"),
                        result.getString("nomeUsuario"),
                        result.getString("emailUsuario"),
                        result.getDate("dataCadastroSenha"));
            }
            //fechando o script e a conexão com o banco
            result.close();
            stmt.close();
            con.close();
            return userSelecionado;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> buscarUltimasSenhas(int codUsuario) {
        List<String> ultimasSenhas = new ArrayList<>();
        String sql = "SELECT senhaCadastrada FROM HistoricoSenha WHERE fk_codUsuario = ? ORDER BY dataTroca DESC LIMIT 7";

        try (Connection conn = DB.getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, codUsuario);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ultimasSenhas.add(rs.getString("senhaCadastrada"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ultimasSenhas;
    }

    public void salvarSenhaNaLista(int codUsuario, String senhaAntigaCriptografada) {
        String sql = "INSERT INTO HistoricoSenha (fk_codUsuario, senhaCadastrada, dataTroca) VALUES (?, ?, ?)";

        try (Connection conn = DB.getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, codUsuario);
            st.setString(2, senhaAntigaCriptografada);
            st.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void manterApenasSeteSenhas(int codUsuario) {
        String sqlCount = "SELECT COUNT(*) AS total FROM HistoricoSenha WHERE fk_codUsuario = ?";
        String sqlDelete = "DELETE FROM HistoricoSenha WHERE pk_codHistorico = (SELECT pk_codHistorico FROM HistoricoSenha WHERE fk_codUsuario = ? ORDER BY dataTroca ASC LIMIT 1)";

        try (Connection conn = DB.getConnection(); PreparedStatement stCount = conn.prepareStatement(sqlCount)) {

            stCount.setInt(1, codUsuario);
            ResultSet rs = stCount.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total");

                if (total > 7) {
                    try (PreparedStatement stDelete = conn.prepareStatement(sqlDelete)) {
                        stDelete.setInt(1, codUsuario);
                        stDelete.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
