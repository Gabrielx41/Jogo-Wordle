package com.example.projeto;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class pagFinalController {
    @FXML
    private Label titlo;
    @FXML
    private Label texto;
    @FXML
    private Label labPontos;
    private String idioma;
    private String dificuldade;
    private String username;
    public void getUsername(String username) {
        this.username = username;
    }
    public void getIdioma(String idioma){
        this.idioma = idioma;
    }
    public void getTitlo(String title){
        titlo.setText(title);
        Pontos pontos = new Pontos();
        int pontosJogador = pontos.getPontos(username);
        if(idioma.equals("portugues")){
            labPontos.setText("Pontos feitos: " + pontosJogador);
        }else if(idioma.equals("ingles")){
            labPontos.setText("Points made: " + pontosJogador);
        }else{
            labPontos.setText("Points gagnés: " + pontosJogador);
        }
    }

    public void getLabel(String text){
        texto.setText(text);
    }

    public void getDificuldade(String dificuldade){
        if(dificuldade.equals("facil")){
            this.dificuldade = "facil";
        }else if(dificuldade.equals("medio")){
            this.dificuldade = "medio";
        }else if(dificuldade.equals("dificilButton")){
            this.dificuldade = "dificil";
        }else if(dificuldade.equals("muitoFacil4Button")){
            this.dificuldade = "muitoFacil4";
        }else{
            this.dificuldade = "muitoFacil3";
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
        stage.setResizable(false);
        stage.show();
    }


    @FXML
    private Button pagInicialView;
    @FXML
    public void trocarViewToPagInicial() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Parent root = loader.load();

        MainController mainController = loader.getController();
        if(idioma.equals("ingles")){
            mainController.selecionarIdiomaIngles();
        }else if(idioma.equals("portugues")){
            mainController.selecionarIdiomaPortugues ();
        }else{
            mainController.selecionarIdiomaFrances();
        }
        mainController.setUsername(username);
        Scene scene = new Scene(root);
        Stage stage = (Stage) pagInicialView.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private Button butReset;
    public void resetButton() throws IOException {
        if(dificuldade.equals("facil")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameFacil-view.fxml"));
            Parent root = loader.load();

            GameControllerFacil gameControllerFacil = loader.getController();
            if(idioma.equals("ingles")){
                gameControllerFacil.selecionarIdiomaIngles();
            }else if(idioma.equals("portugues")){
                gameControllerFacil.selecionarIdiomaPortugues();
            }else{
                gameControllerFacil.selecionarIdiomaFrances();
            }
            gameControllerFacil.setUsername(username);
            Scene scene = new Scene(root);
            Stage stage = (Stage) butReset.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }else if (dificuldade.equals("medio")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameMedio-view.fxml"));
            Parent root = loader.load();

            GameControllerMedio gameControllerMedio = loader.getController();
            if(idioma.equals("ingles")){
                gameControllerMedio.selecionarIdiomaIngles();
            }else if(idioma.equals("portugues")){
                gameControllerMedio.selecionarIdiomaPortugues();
            }else{
                gameControllerMedio.selecionarIdiomaFrances();
            }
            gameControllerMedio.setUsername(username);
            Scene scene = new Scene(root);
            Stage stage = (Stage) butReset.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }else if (dificuldade.equals("dificil")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameDificil-view.fxml"));
            Parent root = loader.load();

            GameControllerDificil gameControllerDificil = loader.getController();
            if(idioma.equals("ingles")){
                gameControllerDificil.selecionarIdiomaIngles();
            }else if(idioma.equals("portugues")){
                gameControllerDificil.selecionarIdiomaPortugues();
            }else{
                gameControllerDificil.selecionarIdiomaFrances();
            }
            gameControllerDificil.setUsername(username);
            Scene scene = new Scene(root);
            Stage stage = (Stage) butReset.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }else if (dificuldade.equals("muitoFacil4")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameMuitoFacil4.fxml"));
            Parent root = loader.load();

            GameControllerMuitoFacil4 gameControllerMuitoFacil4 = loader.getController();
            if(idioma.equals("ingles")){
                gameControllerMuitoFacil4.selecionarIdiomaIngles();
            }else if(idioma.equals("portugues")){
                gameControllerMuitoFacil4.selecionarIdiomaPortugues();
            }else{
                gameControllerMuitoFacil4.selecionarIdiomaFrances();
            }
            gameControllerMuitoFacil4.setUsername(username);
            Scene scene = new Scene(root);
            Stage stage = (Stage) butReset.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameMuitoFacil3.fxml"));
            Parent root = loader.load();

            GameControllerMuitoFacil3 gameControllerMuitoFacil3 = loader.getController();
            if(idioma.equals("ingles")){
                gameControllerMuitoFacil3.selecionarIdiomaIngles();
            }else if(idioma.equals("portugues")){
                gameControllerMuitoFacil3.selecionarIdiomaPortugues();
            }else{
                gameControllerMuitoFacil3.selecionarIdiomaFrances();
            }
            gameControllerMuitoFacil3.setUsername(username);
            Scene scene = new Scene(root);
            Stage stage = (Stage) butReset.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    public void resetPontos(String username){
        Pontos pontos = new Pontos();
        int pontosJogador = pontos.getPontos(username);
        if(pontos.resetPontos(username) == -1){
            if(idioma.equals("portugues")){
                labPontos.setText("Novo record! Pontos: " + pontosJogador);
            }else if(idioma.equals("ingles")){
                labPontos.setText("New record! Points: " + pontosJogador);
            }else{
                labPontos.setText("Nouvel enregistrement! Points: " + pontosJogador);
            }

        }else{
            if(idioma.equals("portugues")){
                labPontos.setText("Pontos feitos: " + pontosJogador);
            }else if(idioma.equals("ingles")){
                labPontos.setText("Points made: " + pontosJogador);
            }else{
                labPontos.setText("Points gagnés: " + pontosJogador);
            }
        }
    }
}