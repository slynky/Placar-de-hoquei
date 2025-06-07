package org.kauamelo.placar.placardehoquei.exibicao;

import javafx.beans.binding.Bindings;
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

    @FXML private Label lblJogadorA1;
    @FXML private Label lblTempoA1;
    @FXML private Label lblJogadorA2;
    @FXML private Label lblTempoA2;

    @FXML private Label lblJogadorB1;
    @FXML private Label lblTempoB1;
    @FXML private Label lblJogadorB2;
    @FXML private Label lblTempoB2;

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

        // Quando penalidades mudarem, atualiza manualmente
        modelo.segundosProperty().addListener((obs, o, n) -> atualizarPenalidades());
        atualizarPenalidades();
    }

    private void atualizarPenalidades() {
        // Pega lista imutável
        var listaA = modelo.getPenalidadesA();
        var listaB = modelo.getPenalidadesB();

        // Preenche ou mostra "-" se não houver
        if (listaA.size() > 0) {
            lblJogadorA1.setText(String.valueOf(listaA.get(0).getNumeroJogador()));
            lblTempoA1.setText(formatar(listaA.get(0).getTempo()));
        } else {
            lblJogadorA1.setText("-");
            lblTempoA1.setText("-");
        }
        if (listaA.size() > 1) {
            lblJogadorA2.setText(String.valueOf(listaA.get(1).getNumeroJogador()));
            lblTempoA2.setText(formatar(listaA.get(1).getTempo()));
        } else {
            lblJogadorA2.setText("-");
            lblTempoA2.setText("-");
        }

        if (listaB.size() > 0) {
            lblJogadorB1.setText(String.valueOf(listaB.get(0).getNumeroJogador()));
            lblTempoB1.setText(formatar(listaB.get(0).getTempo()));
        } else {
            lblJogadorB1.setText("-");
            lblTempoB1.setText("-");
        }
        if (listaB.size() > 1) {
            lblJogadorB2.setText(String.valueOf(listaB.get(1).getNumeroJogador()));
            lblTempoB2.setText(formatar(listaB.get(1).getTempo()));
        } else {
            lblJogadorB2.setText("-");
            lblTempoB2.setText("-");
        }
    }

    private String formatar(int totalSegundos) {
        int min = totalSegundos / 60;
        int seg = totalSegundos % 60;
        return String.format("%02d:%02d", min, seg);
    }
}
