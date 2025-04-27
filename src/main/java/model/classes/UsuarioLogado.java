/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.classes;

/**
 *
 * @author Bruna
 */
public class UsuarioLogado {

    private static Usuario usuarioLogado;

    public UsuarioLogado() {
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void setUsuarioLogado(Usuario usuarioLogado) {
        UsuarioLogado.usuarioLogado = usuarioLogado;
    }

    public static void logout() {
        usuarioLogado = null;
    }
}
