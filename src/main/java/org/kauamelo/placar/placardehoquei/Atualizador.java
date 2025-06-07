package org.kauamelo.placar.placardehoquei;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

//Classe responsavel por instanciar uma timeline que ticka a cada 1 segundo para alterar os tempos do placar
public class Atualizador {

    private final ModeloPlacar modelo;
    private final Timeline timeline;

    public Atualizador(ModeloPlacar modelo) {
        this.modelo = modelo;
        this.timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> modelo.tick())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void iniciar() {
        timeline.play();
    }

    public void pausar() {
        timeline.pause();
    }

    public void parar() {
        timeline.stop();
    }

    public boolean isRodando() {
        return timeline.getStatus() == Timeline.Status.RUNNING;
    }
}
