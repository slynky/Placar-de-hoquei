package org.kauamelo.placar.placardehoquei;

import javafx.beans.property.*;
import java.util.ArrayList;
import java.util.List;

public class ModeloPlacar {

    // NOVO: Enum para os tipos de penalidade. Mais seguro e legível que usar 1 ou 2.
    public enum TipoPenalidade {
        LEVE(120),  // 2 minutos
        PESADA(300); // 5 minutos

        private final int duracaoEmSegundos;

        TipoPenalidade(int duracaoEmSegundos) {
            this.duracaoEmSegundos = duracaoEmSegundos;
        }

        public int getDuracaoEmSegundos() {
            return duracaoEmSegundos;
        }
    }

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

    // A classe Penalidade não precisa de alterações
    public static class Penalidade {
        private final int numeroJogador;
        private final IntegerProperty tempo; // em segundos

        public Penalidade(int numero, int tempoInicial) {
            this.numeroJogador = numero;
            this.tempo = new SimpleIntegerProperty(tempoInicial);
        }

        public int getNumeroJogador() { return numeroJogador; }
        public IntegerProperty tempoProperty() { return tempo; }
        public int getTempo() { return tempo.get(); }

        public void reduzirUMSegundo() {
            if (tempo.get() > 0) {
                tempo.set(tempo.get() - 1);
            }
        }
    }

    private final List<Penalidade> penalidadesA = new ArrayList<>(2);
    private final List<Penalidade> penalidadesB = new ArrayList<>(2);

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
            penalidadesA.forEach(Penalidade::reduzirUMSegundo);
            penalidadesB.forEach(Penalidade::reduzirUMSegundo);
            penalidadesA.removeIf(p -> p.getTempo() <= 0);
            penalidadesB.removeIf(p -> p.getTempo() <= 0);
        }
    }

    public List<Penalidade> getPenalidadesA() { return List.copyOf(penalidadesA); }
    public List<Penalidade> getPenalidadesB() { return List.copyOf(penalidadesB); }

    // ALTERADO: Método agora recebe o TipoPenalidade
    public void adicionarPenalidadeA(int numeroJogador, TipoPenalidade tipo) {
        if (penalidadesA.size() < 2) {
            penalidadesA.add(new Penalidade(numeroJogador, tipo.getDuracaoEmSegundos()));
        }
    }

    // ALTERADO: Método agora recebe o TipoPenalidade
    public void adicionarPenalidadeB(int numeroJogador, TipoPenalidade tipo) {
        if (penalidadesB.size() < 2) {
            penalidadesB.add(new Penalidade(numeroJogador, tipo.getDuracaoEmSegundos()));
        }
    }

    public void removerTodasPenalidadesA() { penalidadesA.clear(); }
    public void removerTodasPenalidadesB() { penalidadesB.clear(); }

    public void incrementaGolA() { golsA.set(golsA.get() + 1); }
    public void incrementaGolB() { golsB.set(golsB.get() + 1); }


    public void resetarPlacar() {
        setTimeA("HOME");
        setTimeB("GUEST");
        setGolsA(0);
        setGolsB(0);
        setPeriodo(1);
        setSegundos(20 * 60); // Define o tempo para 20 minutos
        removerTodasPenalidadesA();
        removerTodasPenalidadesB();
    }

    /**
     * Adiciona segundos ao tempo atual.
     * @param segundosParaAdicionar A quantidade de segundos a ser adicionada.
     */
    public void retrocederTempo(int segundosParaAdicionar) {
        setSegundos(segundosProperty().get() + segundosParaAdicionar);
    }
}