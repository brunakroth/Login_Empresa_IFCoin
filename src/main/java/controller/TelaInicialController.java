/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.utils.Utils;

/**
 * FXML Controller class
 *
 * @author Bruna
 */
public class TelaInicialController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Button btnCadastrar;
    @FXML
    Button btnLogin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Botão sou aluno:
        btnCadastrar.setOnAction((t) -> {
              try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/TelaCadastroUsuario.fxml"));
                Parent parent = loader.load();
                TelaCadastroUsuarioController controller = loader.getController();
                controller.setImgOlhoFechado(true);
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false); //para não ser possível maximizar a tela
                stage.setTitle("Cadastro de Usuários");
                // Caso pressione ESC para sair:
                Utils.escParaFechar(stage, false);
                stage.setScene(scene);
                //indicando quem é o dono da tela de cadastro
                stage.initOwner(btnCadastrar.getScene().getWindow());
                stage.showAndWait();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Botão login:
        btnLogin.setOnAction((t) -> {
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("/application/TelaLogin.fxml"));
                Scene scene = new Scene(parent);//scene é a tela
                Stage stage = new Stage();

                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false); //para não ser possível maximizar a tela
                stage.setTitle("Login");
                // Caso pressione ESC para sair:
                Utils.escParaFechar(stage, false);

                stage.setScene(scene);
                ((Stage) btnLogin.getScene().getWindow()).close();
                stage.initOwner(btnLogin.getScene().getWindow());
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
