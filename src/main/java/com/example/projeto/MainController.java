package com.example.projeto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private Button botaoJogar;
    @FXML
    private Button facilButton;
    @FXML
    private Button medioButton;
    @FXML
    private Button dificilButton;
    @FXML
    private Button muitoFacil4Button;
    @FXML
    private Button muitoFacil3Button;
    private String dificuldade = "facil";
    private String idioma = "portugues";

    @FXML
    public void selecionarNivel(ActionEvent event) {
        Button button = (Button) event.getSource(); // Obtém o botão que disparou o evento
        String id = button.getId(); // Atualiza o nível de dificuldade com o texto do botão
        if (id.equals("facilButton")){
            dificuldade = "facil";
            facilButton.setStyle("-fx-background-color: #798890;");
            medioButton.setStyle(null);
            dificilButton.setStyle(null);
            muitoFacil4Button.setStyle(null);
            muitoFacil3Button.setStyle(null);
        }else if (id.equals("medioButton")){
            dificuldade = "medio";
            facilButton.setStyle(null);
            medioButton.setStyle("-fx-background-color: #798890;");
            dificilButton.setStyle(null);
            muitoFacil4Button.setStyle(null);
            muitoFacil3Button.setStyle(null);
        }else if (id.equals("dificilButton")){
            dificuldade = "dificil";
            facilButton.setStyle(null);
            medioButton.setStyle(null);
            dificilButton.setStyle("-fx-background-color: #798890;");
            muitoFacil4Button.setStyle(null);
            muitoFacil3Button.setStyle(null);
        }else if (id.equals("muitoFacil4Button")){
            dificuldade = "muitoFacil4";
            facilButton.setStyle(null);
            medioButton.setStyle(null);
            dificilButton.setStyle(null);
            muitoFacil4Button.setStyle("-fx-background-color: #798890;");

            muitoFacil3Button.setStyle(null);
        }else{
            dificuldade = "muitoFacil3";
            facilButton.setStyle(null);
            medioButton.setStyle(null);
            dificilButton.setStyle(null);
            muitoFacil4Button.setStyle(null);
            muitoFacil3Button.setStyle("-fx-background-color: #798890;");
        }
    }

    @FXML
    ImageView Portugal;
    @FXML
    ImageView Ingles;
    @FXML
    ImageView Frances;
    @FXML
    public void selecionarIdiomaPortugues() {
        idioma = "portugues";
        Portugal.setStyle("-fx-effect: dropshadow(gaussian, gray, 2, 1, 0, 0);");
        Ingles.setStyle(null);
        Frances.setStyle(null);
        botaoJogar.setText("Jogar");
        facilButton.setText("Médio");
        medioButton.setText("Difícil");
        dificilButton.setText("Muito Difícil");
        muitoFacil4Button.setText("Fácil");
        muitoFacil3Button.setText("Muito Fácil");
    }
    @FXML
    public void selecionarIdiomaIngles() {
        idioma = "ingles";
        Ingles.setStyle("-fx-effect: dropshadow(gaussian, gray, 2, 1, 0, 0);");
        Portugal.setStyle(null);
        Frances.setStyle(null);
        botaoJogar.setText("Play");
        facilButton.setText("Medium");
        medioButton.setText("Hard");
        dificilButton.setText("Very Hard");
        muitoFacil4Button.setText("Easy");
        muitoFacil3Button.setText("Very Easy");
    }
    @FXML
    public void selecionarIdiomaFrances() {
        idioma = "frances";
        Frances.setStyle("-fx-effect: dropshadow(gaussian, gray, 2, 1, 0, 0);");
        Portugal.setStyle(null);
        Ingles.setStyle(null);
        botaoJogar.setText("Jouer");
        facilButton.setText("Moyenne");
        medioButton.setText("Difficile");
        dificilButton.setText("Très Difficile");
        muitoFacil4Button.setText("Facile");
        muitoFacil3Button.setText("Très Facile");
    }
    public void initialize(){
        Portugal.setStyle("-fx-effect: dropshadow(gaussian, gray, 2, 1, 0, 0);");
        facilButton.setStyle("-fx-background-color: #798890;");
    }

    public void trocarView() throws IOException {
        if(dificuldade.equals("dificil")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameDificil-view.fxml"));
            Parent root = loader.load();

            // Recupera o controlador da outra view
            //com.example.trabalhofinalihc.MainController mainViewController = loader.getController();
            // Realiza qualquer configuração necessária na outra view antes de mostrar

            //Passar o idioma escolhidos para o GameControllerDificil
            GameControllerDificil gameController = loader.getController();
            if(idioma.equals("portugues")){
                gameController.selecionarIdiomaPortugues();
            }else if(idioma.equals("ingles")){
                gameController.selecionarIdiomaIngles();
            }else{
                gameController.selecionarIdiomaFrances();
            }
            gameController.setUsername(username);

            Scene scene = new Scene(root);
            Stage stage = (Stage) botaoJogar.getScene().getWindow();
            stage.setScene(scene);

            // Centrar a view no ecra
            double screenWidth = Screen.getPrimary().getBounds().getWidth();
            double screenHeight = Screen.getPrimary().getBounds().getHeight();
            double sceneWidth = scene.getWidth();
            double sceneHeight = scene.getHeight();
            double centerX = (screenWidth - sceneWidth) / 2;
            double centerY = (screenHeight - sceneHeight) / 2;
            stage.setX(centerX);
            stage.setY(centerY);

            stage.show();

        }else if(dificuldade.equals("facil")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameFacil-view.fxml"));
            Parent root = loader.load();

            // Recupera o controlador da outra view
            //com.example.trabalhofinalihc.MainController mainViewController = loader.getController();
            // Realiza qualquer configuração necessária na outra view antes de mostrar

            //Passar a dificuldade e o idioma escolhidos para o GameControllerMedio
            GameControllerFacil gameController = loader.getController();
            if(idioma.equals("portugues")){
                gameController.selecionarIdiomaPortugues();
            }else if(idioma.equals("ingles")){
                gameController.selecionarIdiomaIngles();
            }else{
                gameController.selecionarIdiomaFrances();
            }
            gameController.setUsername(username);

            Scene scene = new Scene(root);
            Stage stage = (Stage) botaoJogar.getScene().getWindow();
            stage.setScene(scene);

            // Centrar a view no ecra
            double screenWidth = Screen.getPrimary().getBounds().getWidth();
            double screenHeight = Screen.getPrimary().getBounds().getHeight();
            double sceneWidth = scene.getWidth();
            double sceneHeight = scene.getHeight();
            double centerX = (screenWidth - sceneWidth) / 2;
            double centerY = (screenHeight - sceneHeight) / 2;
            stage.setX(centerX);
            stage.setY(centerY);

            stage.show();
        } else if(dificuldade.equals("medio")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameMedio-view.fxml"));
            Parent root = loader.load();

            // Recupera o controlador da outra view
            //com.example.trabalhofinalihc.MainController mainViewController = loader.getController();
            // Realiza qualquer configuração necessária na outra view antes de mostrar

            //Passar a dificuldade e o idioma escolhidos para o GameControllerMedio
            GameControllerMedio gameController = loader.getController();
            if(idioma.equals("portugues")){
                gameController.selecionarIdiomaPortugues();
            }else if(idioma.equals("ingles")){
                gameController.selecionarIdiomaIngles();
            }else{
                gameController.selecionarIdiomaFrances();
            }
            gameController.setUsername(username);

            Scene scene = new Scene(root);
            Stage stage = (Stage) botaoJogar.getScene().getWindow();
            stage.setScene(scene);

            // Centrar a view no ecra
            double screenWidth = Screen.getPrimary().getBounds().getWidth();
            double screenHeight = Screen.getPrimary().getBounds().getHeight();
            double sceneWidth = scene.getWidth();
            double sceneHeight = scene.getHeight();
            double centerX = (screenWidth - sceneWidth) / 2;
            double centerY = (screenHeight - sceneHeight) / 2;
            stage.setX(centerX);
            stage.setY(centerY);

            stage.show();
        }else if(dificuldade.equals("muitoFacil3")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameMuitoFacil3.fxml"));
            Parent root = loader.load();

            // Recupera o controlador da outra view
            //com.example.trabalhofinalihc.MainController mainViewController = loader.getController();
            // Realiza qualquer configuração necessária na outra view antes de mostrar

            //Passar a dificuldade e o idioma escolhidos para o GameControllerMedio
            GameControllerMuitoFacil3 gameController = loader.getController();
            if(idioma.equals("portugues")){
                gameController.selecionarIdiomaPortugues();
            }else if(idioma.equals("ingles")){
                gameController.selecionarIdiomaIngles();
            }else{
                gameController.selecionarIdiomaFrances();
            }
            gameController.setUsername(username);

            Scene scene = new Scene(root);
            Stage stage = (Stage) botaoJogar.getScene().getWindow();
            stage.setScene(scene);

            // Centrar a view no ecra
            double screenWidth = Screen.getPrimary().getBounds().getWidth();
            double screenHeight = Screen.getPrimary().getBounds().getHeight();
            double sceneWidth = scene.getWidth();
            double sceneHeight = scene.getHeight();
            double centerX = (screenWidth - sceneWidth) / 2;
            double centerY = (screenHeight - sceneHeight) / 2;
            stage.setX(centerX);
            stage.setY(centerY);

            stage.show();
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameMuitoFacil4.fxml"));
            Parent root = loader.load();

            // Recupera o controlador da outra view
            //com.example.trabalhofinalihc.MainController mainViewController = loader.getController();
            // Realiza qualquer configuração necessária na outra view antes de mostrar

            //Passar a dificuldade e o idioma escolhidos para o GameControllerMedio
            GameControllerMuitoFacil4 gameController = loader.getController();
            if(idioma.equals("portugues")){
                gameController.selecionarIdiomaPortugues();
            }else if(idioma.equals("ingles")){
                gameController.selecionarIdiomaIngles();
            }else{
                gameController.selecionarIdiomaFrances();
            }
            gameController.setUsername(username);

            Scene scene = new Scene(root);
            Stage stage = (Stage) botaoJogar.getScene().getWindow();
            stage.setScene(scene);

            // Centrar a view no ecra
            double screenWidth = Screen.getPrimary().getBounds().getWidth();
            double screenHeight = Screen.getPrimary().getBounds().getHeight();
            double sceneWidth = scene.getWidth();
            double sceneHeight = scene.getHeight();
            double centerX = (screenWidth - sceneWidth) / 2;
            double centerY = (screenHeight - sceneHeight) / 2;
            stage.setX(centerX);
            stage.setY(centerY);

            stage.show();
        }
    }


    @FXML
    public void trocarViewToRanking() throws IOException {
        // Carregar o arquivo FXML da nova página
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ranking-view.fxml"));
        Parent root = loader.load();
        RankingController rankingController = loader.getController();
        rankingController .getIdioma(idioma);
        // Criar uma nova cena com a nova página
        Scene scene = new Scene(root);

        // Criar um novo Stage para a nova janela
        Stage stage = new Stage();
        stage.setScene(scene);

        // Centrar a view no ecra
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        double sceneWidth = scene.getWidth();
        double sceneHeight = scene.getHeight();
        double centerX = (screenWidth - sceneWidth) / 2;
        double centerY = (screenHeight - sceneHeight) / 2;
        stage.setX(centerX);
        stage.setY(centerY);


        stage.setResizable(false);
        stage.show();
    }


    private String username;
    public void setUsername(String username) {
        this.username = username;
    }

}