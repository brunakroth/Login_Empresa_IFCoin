/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.classes;

import java.util.Date;
import view.utils.Utils;

/**
 *
 * @author Bruna
 */
public class Usuario {

    private int codUsuario;
    private String nomeUsuario;
    private String emailUsuario;
    private String senhaUsuario;
    private Date dataCadastroSenha;

    public Usuario() {
    }

    public Usuario(String emailUsuario, String senhaUsuario) {
        this.emailUsuario = emailUsuario;
        this.senhaUsuario = senhaUsuario;
    }

    //para usar no UsuarioDAO
    public Usuario(int codUsuario, String nomeUsuario, String emailUsuario, Date dataCadastroSenha) {
        this.codUsuario = codUsuario;
        this.nomeUsuario = nomeUsuario;
        this.emailUsuario = emailUsuario;
        this.dataCadastroSenha = dataCadastroSenha;
    }

    public Usuario(int codUsuario, String nomeUsuario, String emailUsuario, String senhaUsuario, Date dataCadastroSenha) {
        this.codUsuario = codUsuario;
        this.nomeUsuario = nomeUsuario;
        this.emailUsuario = emailUsuario;
        this.senhaUsuario = senhaUsuario;
        this.dataCadastroSenha = dataCadastroSenha;
    }

    public int getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(int codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }

    public Date getDataCadastroSenha() {
        return dataCadastroSenha;
    }

    public void setDataCadastroSenha(Date dataCadastroSenha) {
        this.dataCadastroSenha = dataCadastroSenha;
    }

    public String getDataCadastroSenhaFormatada() {
        String dataFormatada = Utils.dateParaString(dataCadastroSenha);
        return dataFormatada;
    }

    @Override
    public String toString() {
        return "Usuario{" + "codUsuario=" + codUsuario + ", nomeUsuario=" + nomeUsuario + ", emailUsuario=" + emailUsuario + ", senhaUsuario=" + senhaUsuario + ", dataCadastroSenha=" + dataCadastroSenha + '}';
    }

}
