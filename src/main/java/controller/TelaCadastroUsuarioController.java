/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.classes.Usuario;
import model.exceptions.ValidacaoException;
import model.services.UsuarioService;
import view.utils.Utils;

/**
 * FXML Controller class
 *
 * @author Bruna
 */
public class TelaCadastroUsuarioController implements Initializable {

    @FXML
    HBox hbVoltar;
    @FXML
    TextField txtNome;
    @FXML
    TextField txtEmail;
    @FXML
    TextField txtSenha;
    @FXML
    TextField txtSenha2;
    @FXML
    PasswordField pfSenha;
    @FXML
    PasswordField pfSenha2;
    @FXML
    TextField txtId;
    @FXML
    TextField txtDataCadastroSenha;
    @FXML
    Label lblErroNome;
    @FXML
    Label lblErroEmail;
    @FXML
    Label lblErroSenha;
    @FXML
    Label lblErroSenha2;
    @FXML
    Label lblErroSenhaIgual;
    @FXML
    Label lblCadastroUsuario;
    @FXML
    Label lblDataCadastro;
    @FXML
    Button btnSalvar;
    @FXML
    ImageView imgOlho;
    @FXML
    ImageView imgOlhoFechado;

    //atributo Usuario que será usado para edição
    private Usuario usuario;

    private StringBuilder mensagem = new StringBuilder();

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        //carregando o usuario para os campos da tela
        txtNome.setText(String.valueOf(usuario.getNomeUsuario()));
        txtEmail.setText(String.valueOf(usuario.getEmailUsuario()));
        txtId.setText(String.valueOf(usuario.getCodUsuario()));
        txtDataCadastroSenha.setText(Utils.DataAtual());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        txtDataCadastroSenha.setText(Utils.DataAtual());

        // Botão voltar:
        hbVoltar.setOnMouseClicked((t) -> {
            ((Stage) hbVoltar.getScene().getWindow()).close();
        });

        // Botão salvar:
        btnSalvar.setOnAction((t) -> {
            try {
                ValidacaoException exc = new ValidacaoException("Erro validando!!");
                //criando o objeto do Usuario se ele ainda não existe
                //quando é uma inserção ele não existe
                if (usuario == null) {
                    usuario = new Usuario();
                }

                if (!Utils.validaCampo(txtNome.getText(), "", mensagem)) {
                    // se o usuário não preencheu os campos, aicionar uma exception na validacao
                    exc.AdicionarErro("Nome", mensagem.toString());
                } else {
                    // se estiver preenchido, atualiza o objeto com o nome
                    usuario.setNomeUsuario(txtNome.getText());
                }

                if (!Utils.validaCampo(txtEmail.getText(), "", mensagem)) {
                    // se o usuário não preencheu os campos, aicionar uma exception na validacao
                    exc.AdicionarErro("Email", mensagem.toString());
                } else {
                    // se estiver preenchido, atualiza o objeto com o email
                    usuario.setEmailUsuario(txtEmail.getText());
                }

                // Validação de Senha
                String senha = pfSenha.isVisible() ? pfSenha.getText() : txtSenha.getText();
                String senha2 = pfSenha2.isVisible() ? pfSenha2.getText() : txtSenha2.getText();

                // Verificação do primeiro campo de senha
                if (!Utils.validaCampo(senha, "senha", mensagem)) {
                    exc.AdicionarErro("Senha", mensagem.toString());
                } else if (senha.equals(senha2)) {
                    usuario.setSenhaUsuario(senha); // Configuração da senha apenas se válida
                }

                // Verificação do segundo campo de senha
                if (!Utils.validaCampo(senha2, "", mensagem)) {
                    exc.AdicionarErro("Senha2", mensagem.toString());
                } else if (!senha.isEmpty() && !senha2.isEmpty() && !senha.equals(senha2)) {
                    exc.AdicionarErro("SenhaIgual", "As senhas devem ser iguais!");
                } else if (senha.equals(senha2)) {
                    usuario.setSenhaUsuario(senha); // Configuração da senha apenas se válida
                }

                //setando data de cadastro da senha no usuario
                usuario.setDataCadastroSenha(java.sql.Date.valueOf(LocalDate.now()));

                if (!exc.getErrors().isEmpty()) {
                    //disparando a exception
                    throw exc;
                }
                //criando o objeto de UsuarioService
                //chamando o metodo SalvarOuAlterarSenha
                if (new UsuarioService().salvarOuAlterarSenha(usuario, mensagem)) {
                    // Fechar a janela, pois deu certo
                    ((Stage) btnSalvar.getScene().getWindow()).close();
                } else {
                    // Deu erro
                    Alert al = new Alert(Alert.AlertType.WARNING);
                    al.setTitle("Erro");
                    al.setContentText(mensagem.toString());
                    al.showAndWait();
                }
            } catch (ValidacaoException e) {
                setErrorMessages(e.getErrors());
            }
        });

        // Lógica para alternar entre os campos
        imgOlhoFechado.setOnMouseClicked(event -> {
            txtSenha.setText(pfSenha.getText());
            txtSenha.setVisible(true);
            pfSenha.setVisible(false);
            //senha 2
            txtSenha2.setText(pfSenha2.getText());
            txtSenha2.setVisible(true);
            pfSenha2.setVisible(false);
            imgOlho.setVisible(true);
            imgOlhoFechado.setVisible(false);
        });
        imgOlho.setOnMouseClicked(event -> {
            pfSenha.setText(txtSenha.getText());
            pfSenha.setVisible(true);
            txtSenha.setVisible(false);
            //senha 2
            pfSenha2.setText(txtSenha2.getText());
            pfSenha2.setVisible(true);
            txtSenha2.setVisible(false);
            imgOlho.setVisible(false);
            imgOlhoFechado.setVisible(true);
        });
    }

    private void setErrorMessages(Map<String, String> errors) {
        // pegar toos os campos o erro
        Set<String> campos = errors.keySet();
        // motrar o erro no label que definimos
        lblErroNome.setText(campos.contains("Nome") ? errors.get("Nome") : "");
        lblErroEmail.setText(campos.contains("Email") ? errors.get("Email") : "");
        lblErroSenha.setText(campos.contains("Senha") ? errors.get("Senha") : "");
        lblErroSenha2.setText(campos.contains("Senha2") ? errors.get("Senha2") : "");
        lblErroSenhaIgual.setText(campos.contains("SenhaIgual") ? errors.get("SenhaIgual") : "");
    }

    public void setImgOlhoFechado(boolean isVisible) {
        imgOlhoFechado.setVisible(isVisible);
    }

    public void setLabelBemVindo(String bemVindo) {
        lblCadastroUsuario.setText(bemVindo);
    }

    public void setLabelDataSenha(String dataSenha) {
        lblDataCadastro.setText(dataSenha);
    }
}
