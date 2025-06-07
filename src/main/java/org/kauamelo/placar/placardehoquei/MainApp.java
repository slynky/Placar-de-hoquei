package org.kauamelo.placar.placardehoquei;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kauamelo.placar.placardehoquei.controle.TelaControleController;
import org.kauamelo.placar.placardehoquei.exibicao.TelaExibicaoController;

//Classe principal do app
public class MainApp extends Application {
    private ModeloPlacar modelo;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cria o modelo compartilhado
        modelo = new ModeloPlacar();

        // Carrega a janela de exibição
        FXMLLoader loaderExibicao = new FXMLLoader(
                getClass().getResource("/org/kauamelo/placar/placardehoquei/exibicao/TelaExibicao.fxml")
        );
        // Define um tamanho grande para a janela de exibição, ideal para um placar
        Scene sceneExibicao = new Scene(loaderExibicao.load(), 1280, 720);
        sceneExibicao.getStylesheets().add(
                getClass().getResource("/org/kauamelo/placar/placardehoquei/exibicao/estilo-placar.css").toExternalForm()
        );

        TelaExibicaoController ctrlExibicao = loaderExibicao.getController();
        ctrlExibicao.setModelo(modelo);
        Stage stageExibicao = new Stage();
        stageExibicao.setScene(sceneExibicao);
        stageExibicao.setTitle("Placar - Exibição");
        stageExibicao.setResizable(true); // true para conforto do user
        stageExibicao.show();

        // Carrega a janela de controle (usa o primeiro Stage)
        FXMLLoader loaderControle = new FXMLLoader(
                getClass().getResource("/org/kauamelo/placar/placardehoquei/controle/TelaControle.fxml")
        );
        Scene sceneControle = new Scene(loaderControle.load(), 800, 250);
        TelaControleController ctrlControle = loaderControle.getController();
        ctrlControle.setModelo(modelo);
        Stage stageControle = primaryStage;
        stageControle.setScene(sceneControle);
        stageControle.setTitle("Placar - Controle");
        stageControle.setResizable(true); // true para conforto do user
        stageControle.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
