/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.DB.DB;
import model.classes.UsuarioLogado;
import view.utils.Utils;

/**
 *
 * @author Bruna
 */
public class Login_Empresa_IFCoin extends Application {

    @Override
    public void start(Stage stage) {
        if (DB.getConnection() != null) {
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("/application/TelaInicial.fxml"));
                Scene scene = new Scene(parent);
                stage.initStyle(StageStyle.UNDECORATED); // para não aparecer a barra superior
                stage.setScene(scene);
                stage.setResizable(false); //para não ser possível maximizar a tela
                stage.setTitle("IFCoin");

                // Adiciona o listener para fechamento da aplicação
                stage.setOnCloseRequest(event -> {
                    realizarLogout();
                });

                // Caso pressione ESC para sair:
                Utils.escParaFechar(stage, false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void realizarLogout() {
        if (UsuarioLogado.getUsuarioLogado() != null) {
            UsuarioLogado.logout(); // Limpa o estado do usuário logado
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    
}
