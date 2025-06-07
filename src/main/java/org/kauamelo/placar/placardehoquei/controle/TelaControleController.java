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
    @FXML private Button btnIniciar;
    @FXML private Button btnPausar;
    @FXML private Button btnParar;
    @FXML private Button btnGolA;
    @FXML private Button btnGolB;
    @FXML private Button btnAvancarPeriodo;
    @FXML private TextField txtNumA;
    @FXML private TextField txtTempoA;
    @FXML private Button btnPenA;
    @FXML private TextField txtNumB;
    @FXML private TextField txtTempoB;
    @FXML private Button btnPenB;

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
    }

    @FXML
    private void initialize() {
        btnIniciar.setOnAction(e -> {
            if (modelo.segundosProperty().get() == 0) {
                modelo.setSegundos(20 * 60);
            }
            atualizador.iniciar();
        });

        btnPausar.setOnAction(e -> atualizador.pausar());

        btnParar.setOnAction(e -> {
            atualizador.parar();
            modelo.setSegundos(0);
        });

        btnGolA.setOnAction(e -> modelo.incrementaGolA());
        btnGolB.setOnAction(e -> modelo.incrementaGolB());

        btnAvancarPeriodo.setOnAction(e -> modelo.setPeriodo(modelo.periodoProperty().get() + 1));


        btnPenA.setOnAction(e -> {
            try {
                int numJogador = Integer.parseInt(txtNumA.getText());
                int tipoInt = Integer.parseInt(txtTempoA.getText()); // Agora lê o tipo (1 ou 2)

                ModeloPlacar.TipoPenalidade tipo;
                if (tipoInt == 2) {
                    tipo = ModeloPlacar.TipoPenalidade.PESADA;
                } else {
                    // Assume 1 ou qualquer outro valor como LEVE
                    tipo = ModeloPlacar.TipoPenalidade.LEVE;
                }

                modelo.adicionarPenalidadeA(numJogador, tipo); // Chama o novo método

                txtNumA.clear();
                txtTempoA.clear();
            } catch (NumberFormatException ex) {

            }
        });

        btnPenB.setOnAction(e -> {
            try {
                int numJogador = Integer.parseInt(txtNumB.getText());
                int tipoInt = Integer.parseInt(txtTempoB.getText()); // Agora lê o tipo (1 ou 2)

                ModeloPlacar.TipoPenalidade tipo;
                if (tipoInt == 2) {
                    tipo = ModeloPlacar.TipoPenalidade.PESADA;
                } else {
                    tipo = ModeloPlacar.TipoPenalidade.LEVE;
                }

                modelo.adicionarPenalidadeB(numJogador, tipo); // Chama o novo método

                txtNumB.clear();
                txtTempoB.clear();
            } catch (NumberFormatException ex) {
                // ignorar ou mostrar alerta
            }
        });

        // ======================================================
        // AQUI ESTÁ A CORREÇÃO PARA ATUALIZAR OS NOMES EM TEMPO REAL
        // ======================================================
        txtTimeA.textProperty().addListener((obs, textoAntigo, textoNovo) -> {
            modelo.setTimeA(textoNovo);
        });

        txtTimeB.textProperty().addListener((obs, textoAntigo, textoNovo) -> {
            modelo.setTimeB(textoNovo);
        });
    }
}