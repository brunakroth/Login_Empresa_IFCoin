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
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.utils.Utils;

/**
 * FXML Controller class
 *
 * @author Bruna
 */
public class TelaBoasVindasController implements Initializable {

    @FXML
    Label lblBoasVindas;
    @FXML
    HBox hbVoltar;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Botão voltar:
        hbVoltar.setOnMouseClicked((t) -> {
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("/application/TelaLogin.fxml"));
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
    }

    public void setLabelBemVindo(String bemVindo) {
        lblBoasVindas.setText(bemVindo);
    }
}
