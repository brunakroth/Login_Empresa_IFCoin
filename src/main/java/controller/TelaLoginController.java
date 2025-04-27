/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.classes.Usuario;
import model.classes.UsuarioLogado;
import model.exceptions.ValidacaoException;
import model.services.UsuarioService;
import view.utils.Utils;

/**
 * FXML Controller class
 *
 * @author Bruna
 */
public class TelaLoginController implements Initializable {

    @FXML
    TextField txtEmail;
    @FXML
    HBox hbVoltar;
    @FXML
    PasswordField pfSenha;
    @FXML
    TextField txtSenha;
    @FXML
    Label lblErro;
    @FXML
    Label lblErroEmail;
    @FXML
    Label lblErroSenha;
    @FXML
    Button btnEntrar;
    @FXML
    ImageView imgOlho;
    @FXML
    ImageView imgOlhoFechado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtSenha.setVisible(false);
        imgOlho.setVisible(false);

        // Botão voltar:
        hbVoltar.setOnMouseClicked((t) -> {
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("/application/TelaInicial.fxml"));
                Scene scene = new Scene(parent);//scene é a tela
                Stage stage = new Stage();

                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false); //para não ser possível maximizar a tela
                stage.setTitle("IFCoin");
                // Caso pressione ESC para sair:
                Utils.escParaFechar(stage, false);
                stage.setScene(scene);
                ((Stage) hbVoltar.getScene().getWindow()).close();
                stage.initOwner(hbVoltar.getScene().getWindow());
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Botão entrar:
        btnEntrar.setOnAction((t) -> {
            fazerLogin(); // Chamando o método para fazer login
        });

        // Ao teclar Enter
        pfSenha.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                fazerLogin();
            }
        });

        txtSenha.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                fazerLogin();
            }
        });

        // Lógica para alternar entre os campos
        imgOlhoFechado.setOnMouseClicked(event -> {
            txtSenha.setText(pfSenha.getText());
            txtSenha.setVisible(true);
            pfSenha.setVisible(false);
            imgOlho.setVisible(true);
            imgOlhoFechado.setVisible(false);
        });
        imgOlho.setOnMouseClicked(event -> {
            pfSenha.setText(txtSenha.getText());
            pfSenha.setVisible(true);
            txtSenha.setVisible(false);
            imgOlho.setVisible(false);
            imgOlhoFechado.setVisible(true);
        });
    }

    private void fazerLogin() {
        String senha = null;
        
        try {
            ValidacaoException exc = new ValidacaoException("Erro validando!!");

            if (txtEmail.getText() == null || txtEmail.getText().equals("")) {
                //se o uusário não preenchou os campos adicionar uma exception na validação
                exc.AdicionarErro("Email", "O campo não pode ser vazio!");
            }

            if ((pfSenha.getText() == null) || (pfSenha.getText().equals("")) && ((txtSenha.getText() == null) || (txtSenha.getText().equals("")))) {
                //se o uusário não preenchou os campos adicionar uma exception na validação
                exc.AdicionarErro("Senha", "O campo não pode ser vazio!");
            } else {
                //passou nos testes de nulo
                //obtendo dados do usuário
                if (pfSenha.isVisible()) {
                    senha = pfSenha.getText();
                } else {
                    senha = txtSenha.getText();
                }
                Usuario user = new Usuario(txtEmail.getText(), senha);

                //mandando comando para o servidor
                Usuario userLogado = (new UsuarioService().fazerLogin(user));
                UsuarioLogado.setUsuarioLogado(userLogado); // Salva o usuário logado
                if (userLogado != null) {
                    //o usuario existe, continua com o login, caso a senha não esteja expirada   
                    if (!Utils.isSenhaExpirada(userLogado.getDataCadastroSenha())) {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/TelaBoasVindas.fxml"));
                            Parent parent = loader.load();
                            TelaBoasVindasController controller = loader.getController();
                            controller.setLabelBemVindo("Bem-Vindo " + userLogado.getNomeUsuario() + "!");
                            Scene scene = new Scene(parent);
                            Stage stage = new Stage();

                            stage.initModality(Modality.WINDOW_MODAL);
                            stage.setResizable(false); //para não ser possível maximizar a tela
                            stage.initOwner(btnEntrar.getScene().getWindow());
                            stage.setTitle("Tela Boas Vindas");
                            // Caso pressione ESC para sair:
                            Utils.escParaFechar(stage, true);
                            stage.setScene(scene);
                            ((Stage) btnEntrar.getScene().getWindow()).close();
                            stage.showAndWait();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        // senha expirada!
                        try {
                            Alert mens = new Alert(Alert.AlertType.INFORMATION);
                            mens.setTitle("Senha Expirada!");
                            mens.initOwner(btnEntrar.getScene().getWindow());
                            mens.setContentText("Necessário cadastrar nova senha!");
                            mens.showAndWait();
                            
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/TelaCadastroUsuario.fxml"));
                            Scene scene = new Scene(loader.load());
                            Stage stage = new Stage();

                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setResizable(false); //para não ser possível maximizar a tela
                            stage.setScene(scene);
                            stage.setTitle("Edição de Senha do Usuário");
                            // Caso pressione ESC para sair:
                            Utils.escParaFechar(stage, false);
                            stage.initOwner(btnEntrar.getScene().getWindow());
                            // chamando o controller, para invocar o metodo dentro dele
                            TelaCadastroUsuarioController cont = loader.getController();
                            cont.setUsuario(userLogado);
                            cont.setLabelBemVindo("Alteração de Senha");
                            cont.setLabelDataSenha("Alterado em:");
                            stage.showAndWait();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    //o usuario NÃO EXISTE!
                    exc.AdicionarErro("Erro", "Usuário ou senha inválidos!");
                }
            }

            if (!exc.getErrors().isEmpty()) {
                //disparando a exception
                throw exc;
            }
        } catch (ValidacaoException e) {
            setErrorMessages(e.getErrors());
        }
    }

    private void setErrorMessages(Map<String, String> errors) {
        // pegar toos os campos de erro
        Set<String> campos = errors.keySet();
        // mostrar o erro no label

        lblErroEmail.setText(campos.contains("Email") ? errors.get("Email") : "");
        lblErroSenha.setText(campos.contains("Senha") ? errors.get("Senha") : "");
        lblErro.setText(campos.contains("Erro") ? errors.get("Erro") : "");
    }
}
