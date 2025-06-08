package org.kauamelo.placar.placardehoquei;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ModeloPlacar {

    // Nomes dos times
    private final StringProperty timeA = new SimpleStringProperty("HOME");
    private final StringProperty timeB = new SimpleStringProperty("GUEST");

    // Gols
    private final IntegerProperty golsA = new SimpleIntegerProperty(0);
    private final IntegerProperty golsB = new SimpleIntegerProperty(0);

    // Período atual (1, 2, 3, etc.)
    private final IntegerProperty periodo = new SimpleIntegerProperty(1);

    // Tempo restante em segundos (padrão atual = 1200s)
    private final IntegerProperty segundos = new SimpleIntegerProperty(1200);

    // Getters de properties para bind:
    public StringProperty timeAProperty() { return timeA; }
    public StringProperty timeBProperty() { return timeB; }
    public IntegerProperty golsAProperty() { return golsA; }
    public IntegerProperty golsBProperty() { return golsB; }
    public IntegerProperty periodoProperty() { return periodo; }
    public IntegerProperty segundosProperty() { return segundos; }

    // Métodos convencionais:
    public void setTimeA(String nome) { timeA.set(nome); }
    public void setTimeB(String nome) { timeB.set(nome); }
    public void setGolsA(int v) { golsA.set(v); }
    public void setGolsB(int v) { golsB.set(v); }
    public void setPeriodo(int p) { periodo.set(p); }
    public void setSegundos(int s) { segundos.set(s); }

    public String getTempoFormatado() {
        int total = segundos.get();
        int min = total / 60;
        int seg = total % 60;
        return String.format("%02d:%02d", min, seg);
    }

    public void tick() {
        if (segundos.get() > 0) {
            segundos.set(segundos.get() - 1);
        }
    }

    public void incrementaGolA() { golsA.set(golsA.get() + 1); }
    public void incrementaGolB() { golsB.set(golsB.get() + 1); }
    public void reduzGolA() { golsA.set(golsA.get() - 1); }
    public void reduzGolB() { golsB.set(golsB.get() - 1); }


    public void resetarPlacar() {
        setTimeA("HOME");
        setTimeB("GUEST");
        setGolsA(0);
        setGolsB(0);
        setPeriodo(1);
        setSegundos(20 * 60); // Define o tempo para 20 minutos
    }

}