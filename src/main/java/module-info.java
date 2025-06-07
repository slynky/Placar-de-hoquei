module org.kauamelo.placar.placardehoquei {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens org.kauamelo.placar.placardehoquei to javafx.fxml;
    opens org.kauamelo.placar.placardehoquei.controle to javafx.fxml;
    opens org.kauamelo.placar.placardehoquei.exibicao to javafx.fxml;
    exports org.kauamelo.placar.placardehoquei;
    exports org.kauamelo.placar.placardehoquei.controle;
    exports org.kauamelo.placar.placardehoquei.exibicao;
}
