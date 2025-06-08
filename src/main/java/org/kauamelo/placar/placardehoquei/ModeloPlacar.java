package org.kauamelo.placar.placardehoquei;

import javafx.beans.property.*;

public class ModeloPlacar {

    public enum Periodo {
        PRIMEIRO("1", "1º"),
        SEGUNDO("2", "2º"),
        TERCEIRO("3", "3º"),
        QUARTO("4", "4º"),
        PENALTIS("PEN", "PEN");

        private final String repCurta;
        private final String repLonga;

        Periodo(String repCurta, String repLonga) {
            this.repCurta = repCurta;
            this.repLonga = repLonga;
        }

        public String getRepCurta() { return repCurta; }
        public String getRepLonga() { return repLonga; }

        public Periodo proximo() {
            if (this == PENALTIS) {
                return PENALTIS;
            }
            return values()[this.ordinal() + 1];
        }

        /**
         * NOVO: Retorna o período anterior na sequência.
         * @return O período anterior, ou PRIMEIRO se já for o primeiro.
         */
        public Periodo anterior() {
            if (this == PRIMEIRO) {
                return PRIMEIRO; // Não retrocede antes do primeiro
            }
            return values()[this.ordinal() - 1];
        }
    }

    // ... (Propriedades existentes)
    private final StringProperty timeA = new SimpleStringProperty("HOME");
    private final StringProperty timeB = new SimpleStringProperty("GUEST");
    private final IntegerProperty golsA = new SimpleIntegerProperty(0);
    private final IntegerProperty golsB = new SimpleIntegerProperty(0);
    private final ObjectProperty<Periodo> periodo = new SimpleObjectProperty<>(Periodo.PRIMEIRO);
    private final IntegerProperty segundos = new SimpleIntegerProperty(1200);

    // ... (Getters de properties existentes)
    public StringProperty timeAProperty() { return timeA; }
    public StringProperty timeBProperty() { return timeB; }
    public IntegerProperty golsAProperty() { return golsA; }
    public IntegerProperty golsBProperty() { return golsB; }
    public ObjectProperty<Periodo> periodoProperty() { return periodo; }
    public IntegerProperty segundosProperty() { return segundos; }

    // ... (Métodos convencionais existentes)
    public void setTimeA(String nome) { timeA.set(nome); }
    public void setTimeB(String nome) { timeB.set(nome); }
    public void setGolsA(int v) { golsA.set(v); }
    public void setGolsB(int v) { golsB.set(v); }
    public void setPeriodo(Periodo p) { periodo.set(p); }
    public Periodo getPeriodo() { return periodo.get(); }
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

    public void avancarPeriodo() {
        setPeriodo(getPeriodo().proximo());
    }

    /**
     * NOVO: A lógica para retroceder o período.
     */
    public void retrocederPeriodo() {
        setPeriodo(getPeriodo().anterior());
    }

    public void incrementaGolA() { golsA.set(golsA.get() + 1); }
    public void incrementaGolB() { golsB.set(golsB.get() + 1); }
    public void reduzGolA() {
        if(golsA.get() > 0 ) golsA.set(golsA.get() - 1);
    }
    public void reduzGolB() {
        if(golsB.get() > 0 ) golsB.set(golsB.get() - 1);
    }

    public void resetarPlacar() {
        setTimeA("HOME");
        setTimeB("GUEST");
        setGolsA(0);
        setGolsB(0);
        setPeriodo(Periodo.PRIMEIRO);
        setSegundos(20 * 60);
    }
}