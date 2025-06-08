package org.kauamelo.placar.placardehoquei.exibicao;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.kauamelo.placar.placardehoquei.ModeloPlacar;

public class TelaExibicaoController {

    private ModeloPlacar modelo;

    @FXML private Label lblTempo;
    @FXML private Label lblPeriodo;
    @FXML private Label lblTimeA;
    @FXML private Label lblTimeB;
    @FXML private Label lblGolsA;
    @FXML private Label lblGolsB;

    public void setModelo(ModeloPlacar modelo) {
        this.modelo = modelo;

        // Bind do tempo e período
        modelo.segundosProperty().addListener((obs, oldV, newV) ->
                lblTempo.setText(modelo.getTempoFormatado())
        );
        modelo.periodoProperty().addListener((obs, oldV, newV) ->
                lblPeriodo.setText("PERÍODO " + newV)
        );

        // Bind dos nomes e gols
        lblTimeA.textProperty().bind(modelo.timeAProperty());
        lblTimeB.textProperty().bind(modelo.timeBProperty());
        lblGolsA.textProperty().bind(modelo.golsAProperty().asString());
        lblGolsB.textProperty().bind(modelo.golsBProperty().asString());
    }
}