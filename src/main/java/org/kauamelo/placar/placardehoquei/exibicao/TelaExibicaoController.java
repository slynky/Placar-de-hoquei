package org.kauamelo.placar.placardehoquei.exibicao;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.kauamelo.placar.placardehoquei.ModeloPlacar;
import org.kauamelo.placar.placardehoquei.Atualizador;

/*
  MODIFICAÇÕES:
  1. Removido o ChangeListener e o método updateFontSize(). A responsividade agora é controlada 100% pelo CSS.
  2. A inicialização dos listeners no método initialize() foi removida.
  3. O listener da propriedade 'periodo' foi ajustado para mostrar apenas a representação curta (o número),
     conforme o desenho que mostra apenas "P" (implicando um valor simples).
*/

public class TelaExibicaoController {

    private ModeloPlacar modelo;

    @FXML private BorderPane rootPane;
    @FXML private Label lblTempo;
    @FXML private Label lblPeriodo;
    @FXML private Label lblTimeA;
    @FXML private Label lblTimeB;
    @FXML private Label lblGolsA;
    @FXML private Label lblGolsB;

    @FXML
    public void initialize() {
        // A lógica de redimensionamento foi removida daqui e passada para o CSS.
    }

    public void setModelo(ModeloPlacar modelo) {
        this.modelo = modelo;

        // Listener para atualizar o cronômetro
        modelo.segundosProperty().addListener((obs, oldV, newV) ->
                lblTempo.setText(modelo.getTempoFormatado())
        );

        // Listener para atualizar o período (mostrando apenas o número)
        modelo.periodoProperty().addListener((obs, oldV, newV) ->
                lblPeriodo.setText(newV.getRepCurta()) // Ex: "1", "2", etc.
        );
        // Define o valor inicial do período
        lblPeriodo.setText(modelo.getPeriodo().getRepCurta());

        // Binds para os nomes dos times e placares
        lblTimeA.textProperty().bind(modelo.timeAProperty());
        lblTimeB.textProperty().bind(modelo.timeBProperty());
        lblGolsA.textProperty().bind(modelo.golsAProperty().asString());
        lblGolsB.textProperty().bind(modelo.golsBProperty().asString());
    }
}