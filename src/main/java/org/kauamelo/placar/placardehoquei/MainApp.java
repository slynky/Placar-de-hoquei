package org.kauamelo.placar.placardehoquei;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import org.kauamelo.placar.placardehoquei.controle.TelaControleController;
import org.kauamelo.placar.placardehoquei.exibicao.TelaExibicaoController;

//Classe principal do app
public class MainApp extends Application {
    private ModeloPlacar modelo;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cria o modelo compartilhado
        modelo = new ModeloPlacar();

        // Carrega a fonte para ser usada pelo CSS
        Font.loadFont(getClass().getResourceAsStream("/fonts/DS-DIGI.TTF"), 10);

        // --- Configuração da Janela de Exibição ---
        FXMLLoader loaderExibicao = new FXMLLoader(
                getClass().getResource("/org/kauamelo/placar/placardehoquei/exibicao/TelaExibicao.fxml")
        );
        Scene sceneExibicao = new Scene(loaderExibicao.load(), 1600, 900);
        sceneExibicao.getStylesheets().add(
                getClass().getResource("/org/kauamelo/placar/placardehoquei/exibicao/estilo-placar.css").toExternalForm()
        );

        TelaExibicaoController ctrlExibicao = loaderExibicao.getController();
        ctrlExibicao.setModelo(modelo);
        Stage stageExibicao = new Stage();
        stageExibicao.setScene(sceneExibicao);
        stageExibicao.setTitle("Placar - Exibição");
        stageExibicao.setResizable(true);

        // --- Lógica de Tela Cheia (Fullscreen) ---
        stageExibicao.setFullScreenExitHint("Pressione F11 ou ESC para sair da tela cheia");
        sceneExibicao.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.F11 ||( event.getCode() == KeyCode.ESCAPE && stageExibicao.isFullScreen())) {
                stageExibicao.setFullScreen(!stageExibicao.isFullScreen());
            }
        });

        stageExibicao.show();

        // --- Configuração da Janela de Controle ---
        FXMLLoader loaderControle = new FXMLLoader(
                getClass().getResource("/org/kauamelo/placar/placardehoquei/controle/TelaControle.fxml")
        );

        // MELHORIA: Remove o tamanho fixo da Scene para que ela se ajuste ao conteúdo do FXML.
        Scene sceneControle = new Scene(loaderControle.load());

        TelaControleController ctrlControle = loaderControle.getController();
        ctrlControle.setModelo(modelo);
        Stage stageControle = primaryStage;
        stageControle.setScene(sceneControle);
        stageControle.setTitle("Placar - Controle");
        stageControle.setResizable(true); // Permite redimensionar a janela de controle
        stageControle.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}