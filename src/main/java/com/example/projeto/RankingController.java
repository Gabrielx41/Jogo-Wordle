package com.example.projeto;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RankingController {
    private Connection connection;
    @FXML
    private GridPane gridPane;
    @FXML
    private ImageView rankinginfo;



    public void getIdioma(String idioma) {
        if(idioma.equals("portugues")){
            Tooltip tooltip = new Tooltip("Cálculo de pontos:\n" +
                    "Muito fácil: 1*(5-N)\nFácil: 2*(5-N)\nMédio: 3*(5-N)\nDifícil: 4*(5-N)\nMuito difícil: 5*(5-N)\n" +
                    "N: Número de tentativas até acertar\nNota: Os pontos são adicionado até o jogador errar a palavra.");
            Tooltip.install(rankinginfo, tooltip);
        }else if (idioma.equals("ingles")){
            Tooltip tooltip = new Tooltip("Points calculation:\n" +
                    "Very easy: 1(5-N)\nEasy: 2(5-N)\nMedium: 3(5-N)\nHard: 4(5-N)\nVery difficult: 5(5- N)\n" +
                    "N: Number of attempts to get it right\nNote: Points are added until the player misses the word.");
            Tooltip.install(rankinginfo, tooltip);
        }else {
            Tooltip tooltip = new Tooltip("Calcul des points :\n" +
                    "Très facile : 1(5-N)\nFacile : 2(5-N)\nMoyen : 3(5-N)\nDifficile : 4(5-N)\nTrès difficile : 5(5- N)\n" +
                    "N : nombre de tentatives pour réussir\nRemarque : des points sont ajoutés jusqu'à ce que le joueur rate le mot.");
            Tooltip.install(rankinginfo, tooltip);
        }
    }

    public void initialize() {
        List<Label> labels = obterTop10Jogadores();
        // Adicionar os labels ao GridPane
        for (int i = 0; i < labels.size(); i++) {
            Label label = labels.get(i);
            gridPane.add(label, 0, i);
        }
    }

    public List<Label> obterTop10Jogadores() {
        List<Label> top10Jogadores = new ArrayList<>();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/trabalhocriativo", "root", "LeoUbi_2003");
            String sql = "SELECT username, record FROM users ORDER BY record DESC LIMIT 10";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            int posicao = 1;
            while (resultSet.next()) {
                String jogador = resultSet.getString("username");
                int recorde = resultSet.getInt("record");
                String textoLabel = posicao + ". " + jogador + " - Record: " + recorde;
                Label label = new Label(textoLabel);
                label.setStyle("-fx-alignment: center; -fx-text-fill: white; -fx-padding: 20px; -fx-font-family: 'Gil Sans MT'; -fx-font-size: 18px;");


                top10Jogadores.add(label);

                posicao++;
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Erro ao consultar à base de dados: " + e.getMessage());
        }
        return top10Jogadores;
    }
}
