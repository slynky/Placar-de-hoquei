package org.kauamelo.placar.placardehoquei.controle;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.kauamelo.placar.placardehoquei.Atualizador;
import org.kauamelo.placar.placardehoquei.ModeloPlacar;

public class TelaControleController {

    private ModeloPlacar modelo;
    private Atualizador atualizador;

    @FXML private TextField txtTimeA;
    @FXML private TextField txtTimeB;
    @FXML private Label lblGolsA;
    @FXML private Label lblGolsB;
    @FXML private Label lblTempo;
    @FXML private Label lblPeriodo;
    @FXML private Button btnIniciarPausar;
    @FXML private Button btnParar;
    @FXML private Button btnReset;
    @FXML private Button btnAvancarPeriodo;

    // Botões de Gols para Time A
    @FXML private Button btnAGolA; // Adicionar Gol
    @FXML private Button btnRGolA; // Reduzir Gol

    // Botões de Gols para Time B
    @FXML private Button btnAGolB; // Adicionar Gol
    @FXML private Button btnRGolB; // Reduzir Gol


    public void setModelo(ModeloPlacar m) {
        this.modelo = m;
        this.atualizador = new Atualizador(modelo);

        lblGolsA.textProperty().bind(modelo.golsAProperty().asString());
        lblGolsB.textProperty().bind(modelo.golsBProperty().asString());
        lblPeriodo.textProperty().bind(modelo.periodoProperty().asString());

        modelo.segundosProperty().addListener((obs, oldV, newV) -> {
            lblTempo.setText(modelo.getTempoFormatado());
        });

        lblTempo.setText(modelo.getTempoFormatado());
        txtTimeA.setText(modelo.timeAProperty().get());
        txtTimeB.setText(modelo.timeBProperty().get());
    }

    @FXML
    private void initialize() {
        // ... (código dos botões de tempo e reset) ...
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

        btnAvancarPeriodo.setOnAction(e -> modelo.setPeriodo(modelo.periodoProperty().get() + 1));


        // --- AÇÕES DOS BOTÕES DE GOL ---
        btnAGolA.setOnAction(e -> modelo.incrementaGolA());
        btnRGolA.setOnAction(e -> modelo.reduzGolA()); // NOVO

        btnAGolB.setOnAction(e -> modelo.incrementaGolB());
        btnRGolB.setOnAction(e -> modelo.reduzGolB()); // NOVO


        // --- Listeners para nomes dos times ---
        txtTimeA.textProperty().addListener((obs, textoAntigo, textoNovo) -> modelo.setTimeA(textoNovo));
        txtTimeB.textProperty().addListener((obs, textoAntigo, textoNovo) -> modelo.setTimeB(textoNovo));
    }
}