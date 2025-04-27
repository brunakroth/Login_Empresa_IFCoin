/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author Bruna
 */
public class Utils {

    public static String dateParaString(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String dataFormatada = sdf.format(date);

        return dataFormatada;
    }

    public static String DataAtual() {
        // Pegar a data atual
        LocalDate currentDate = LocalDate.now();

        // Converter LocalDate para java.sql.Date
        java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = formato.format(sqlDate);

        return dataFormatada;
    }

    public static boolean validaCampo(String entrada, String tipo, StringBuilder mensagem) {
        String regexSenha = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        // (?=.*[a-z]): Verifica se contém ao menos uma letra minúscula.
        // (?=.*[A-Z]): Verifica se contém ao menos uma letra maiúscula.
        // (?=.*\\d): Verifica se contém ao menos um número.
        // (?=.*[@#$%^&+=!]): Verifica se contém ao menos um caractere especial.
        // .{8,}: Garante que a senha tem pelo menos 8 caracteres.
        
        // Limpar a mensagem inicial
        mensagem.setLength(0);

        // Verificar se o campo está vazio
        if (entrada == null || entrada.trim().isEmpty()) {
            mensagem.append("O campo não pode ser vazio!");
            return false;
        }

        // Validação de senha
        if ("senha".equals(tipo)) {
            // Verificar o formato da senha
            if (!entrada.matches(regexSenha)) {
                mensagem.append("A senha não está em acordo com as regras!");
                return false;
            }
        }
        mensagem.setLength(0); // Limpa qualquer mensagem anterior
        return true; // Entrada válida
    }

    // Caso pressione ESC para sair:
    public static void escParaFechar(Stage stage, boolean teste) {
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                stage.close(); // Fecha a janela quando ESC for pressionado
                if (teste) {
                    Platform.exit();
                }
            }
        });
    }

    public static int calcularDiasEntreDatas(Date dataInicial, LocalDate dataFinal) {
        // Formatar a data inicial no formato esperado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataConvertida = LocalDate.parse(dateParaString(dataInicial), formatter);

        // Calcular a diferença em dias
        return (int) ChronoUnit.DAYS.between(dataConvertida, dataFinal);
    }

    public static boolean isSenhaExpirada(Date dataCadastroSenha) {
        int diasDesdeCadastro = calcularDiasEntreDatas(dataCadastroSenha, LocalDate.now());
        return diasDesdeCadastro >= 45;
    }
}
