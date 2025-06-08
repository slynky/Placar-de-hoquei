package org.kauamelo.placar.placardehoquei.controle;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import org.kauamelo.placar.placardehoquei.Atualizador;
import org.kauamelo.placar.placardehoquei.ModeloPlacar;

public class TelaControleController {

    private ModeloPlacar modelo;
    private Atualizador atualizador;

    // --- @FXML declarations ---
    @FXML private TextField txtTimeA, txtTimeB;
    @FXML private Label lblGolsA, lblGolsB, lblTempo, lblPeriodo;
    @FXML private TextField txtTempoEdit;
    @FXML private Button btnIniciarPausar, btnParar, btnReset;
    @FXML private Button btnAvancarPeriodo, btnRetrocederPeriodo; // NOVO BOTÃO DECLARADO
    @FXML private Button btnAGolA, btnRGolA, btnAGolB, btnRGolB;

    // ... (método setModelo sem alterações) ...
    public void setModelo(ModeloPlacar m) {
        this.modelo = m;
        this.atualizador = new Atualizador(modelo);
        lblGolsA.textProperty().bind(modelo.golsAProperty().asString());
        lblGolsB.textProperty().bind(modelo.golsBProperty().asString());
        lblPeriodo.textProperty().bind(
                Bindings.createStringBinding(() -> modelo.getPeriodo().getRepCurta(), modelo.periodoProperty())
        );
        modelo.segundosProperty().addListener((obs, oldV, newV) -> {
            lblTempo.setText(modelo.getTempoFormatado());
        });
        lblTempo.setText(modelo.getTempoFormatado());
        txtTimeA.setText(modelo.timeAProperty().get());
        txtTimeB.setText(modelo.timeBProperty().get());
    }

    @FXML
    private void initialize() {
        // --- (Configuração da Edição Inline do Tempo e outros botões sem alterações) ---
        lblTempo.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                txtTempoEdit.setText(modelo.getTempoFormatado());
                txtTempoEdit.setVisible(true);
                lblTempo.setVisible(false);
                txtTempoEdit.requestFocus();
            }
        });
        txtTempoEdit.textProperty().addListener((obs, oldVal, newVal) -> {
            String digitsOnly = newVal.replaceAll("[^\\d]", "");
            if (digitsOnly.length() > 4) {
                digitsOnly = digitsOnly.substring(0, 4);
            }
            String formattedText = digitsOnly;
            if (digitsOnly.length() > 2) {
                formattedText = digitsOnly.substring(0, 2) + ":" + digitsOnly.substring(2);
            }
            String finalFormattedText = formattedText;
            Platform.runLater(() -> {
                txtTempoEdit.setText(finalFormattedText);
                txtTempoEdit.positionCaret(finalFormattedText.length());
            });
        });
        txtTempoEdit.setOnAction(event -> commitTempoEdit());
        txtTempoEdit.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                commitTempoEdit();
            }
        });
        btnIniciarPausar.setOnAction(e -> {
            if (atualizador.isRodando()) {
                atualizador.pausar();
                btnIniciarPausar.setText("Retomar");
            } else {
                if (modelo.segundosProperty().get() == 0) {
                    modelo.setSegundos(20 * 60);
                }
                atualizador.iniciar();
                btnIniciarPausar.setText("Pausar");
            }
        });
        btnParar.setOnAction(e -> {
            atualizador.parar();
            modelo.setSegundos(0);
            btnIniciarPausar.setText("Iniciar");
        });
        btnReset.setOnAction(e -> {
            atualizador.parar();
            modelo.resetarPlacar();
            btnIniciarPausar.setText("Iniciar");
            txtTimeA.setText(modelo.timeAProperty().get());
            txtTimeB.setText(modelo.timeBProperty().get());
        });
        btnAGolA.setOnAction(e -> modelo.incrementaGolA());
        btnRGolA.setOnAction(e -> modelo.reduzGolA());
        btnAGolB.setOnAction(e -> modelo.incrementaGolB());
        btnRGolB.setOnAction(e -> modelo.reduzGolB());
        txtTimeA.textProperty().addListener((obs, textoAntigo, textoNovo) -> modelo.setTimeA(textoNovo));
        txtTimeB.textProperty().addListener((obs, textoAntigo, textoNovo) -> modelo.setTimeB(textoNovo));

        // --- LÓGICA DO BOTÃO DE PERÍODO ---
        btnAvancarPeriodo.setOnAction(e -> modelo.avancarPeriodo());
        btnRetrocederPeriodo.setOnAction(e -> modelo.retrocederPeriodo()); // AÇÃO DO NOVO BOTÃO
    }

    private void commitTempoEdit() {
        // ... (metodo commitTempoEdit sem alteração) ...
        try {
            String novoTempoStr = txtTempoEdit.getText();
            if (novoTempoStr.length() == 2 && !novoTempoStr.contains(":")) {
                novoTempoStr += ":00";
            }
            String[] partes = novoTempoStr.split(":");
            if (partes.length != 2) throw new NumberFormatException();
            int minutos = Integer.parseInt(partes[0].trim());
            int segundos = Integer.parseInt(partes[1].trim());
            int totalSegundos = (minutos * 60) + segundos;
            atualizador.pausar();
            btnIniciarPausar.setText("Retomar");
            modelo.setSegundos(totalSegundos);
        } catch (NumberFormatException ex) {
            // Ignora
        } finally {
            txtTempoEdit.setVisible(false);
            lblTempo.setVisible(true);
        }
    }
}