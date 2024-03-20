package com.example.projeto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Pontos {
    private Connection connection;

    public Pontos() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/trabalhocriativo", "root", "LeoUbi_2003");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar à base de dados: " + e.getMessage());
        }
    }

    public void adicionarPontos(int pontos, String username) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE users SET pontos = pontos + ? WHERE username = ?")) {
            statement.setInt(1, pontos);
            statement.setString(2, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao executar a instrução SQL: " + e.getMessage());
        }
    }

    public int resetPontos(String username) {
        int recordAtual = 0;
        int pontosAtual = 0;
        try (PreparedStatement selectStatement = connection.prepareStatement("SELECT record, pontos FROM users WHERE username = ?")) {
            selectStatement.setString(1, username);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    recordAtual = resultSet.getInt("record");
                    pontosAtual = resultSet.getInt("pontos");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao executar a instrução SQL: " + e.getMessage());
        }

        if (pontosAtual > recordAtual) {
            try (PreparedStatement updateRecordStatement = connection.prepareStatement("UPDATE users SET record = ? WHERE username = ?")) {
                updateRecordStatement.setInt(1, pontosAtual);
                updateRecordStatement.setString(2, username);
                updateRecordStatement.executeUpdate();

                System.out.println("Bateu o seu novo recorde: " + pontosAtual);
                pontosAtual = -1;
            } catch (SQLException e) {
                System.out.println("Erro ao executar a instrução SQL: " + e.getMessage());
            }
        }

        try (PreparedStatement updatePontosStatement = connection.prepareStatement("UPDATE users SET pontos = 0 WHERE username = ?")) {
            updatePontosStatement.setString(1, username);
            updatePontosStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao executar a instrução SQL: " + e.getMessage());
        }
        return pontosAtual;
    }

    public int getPontos(String username) {
        int pontosAtual = 0;
        try (PreparedStatement selectStatement = connection.prepareStatement("SELECT pontos FROM users WHERE username = ?")) {
            selectStatement.setString(1, username);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) { // Move o cursor para a primeira linha de dados (se existir)

                pontosAtual = resultSet.getInt("pontos");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao executar a instrução SQL: " + e.getMessage());
        }
        return pontosAtual;
    }

}