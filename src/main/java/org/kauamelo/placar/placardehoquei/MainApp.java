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

        Font.loadFont(getClass().getResourceAsStream("/fonts/DS-DIGI.TTF"), 10);

        // Carrega a janela de exibição
        FXMLLoader loaderExibicao = new FXMLLoader(
                getClass().getResource("/org/kauamelo/placar/placardehoquei/exibicao/TelaExibicao.fxml")
        );
        Scene sceneExibicao = new Scene(loaderExibicao.load(), 1280, 720);
        sceneExibicao.getStylesheets().add(
                getClass().getResource("/org/kauamelo/placar/placardehoquei/exibicao/estilo-placar.css").toExternalForm()
        );

        TelaExibicaoController ctrlExibicao = loaderExibicao.getController();
        ctrlExibicao.setModelo(modelo);
        Stage stageExibicao = new Stage();
        stageExibicao.setScene(sceneExibicao);
        stageExibicao.setTitle("Placar - Exibição");
        stageExibicao.setResizable(true);

        // --- INÍCIO DA LÓGICA DE TELA CHEIA ---
        // Define uma dica que aparece quando o usuário entra em tela cheia.
        stageExibicao.setFullScreenExitHint("Pressione F11 ou ESC para sair da tela cheia.");

        // Adiciona um listener de evento para teclas pressionadas na cena de exibição.
        sceneExibicao.setOnKeyPressed((KeyEvent event) -> {
            // Verifica se a tecla pressionada foi a F11.
            if (event.getCode() == KeyCode.F11) {
                // Alterna o modo de tela cheia.
                stageExibicao.setFullScreen(!stageExibicao.isFullScreen());
            }
        });
        // --- FIM DA LÓGICA DE TELA CHEIA ---

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
        stageControle.setResizable(true);
        stageControle.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}