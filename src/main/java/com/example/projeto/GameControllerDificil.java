package com.example.projeto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;
import java.io.IOException;
import java.text.Normalizer;

public class GameControllerDificil {
    @FXML
    private Button butEnter;
    private int numLetras = 0;
    private int numTentativas = 0;
    @FXML
    private GridPane letrasGrid;
    @FXML
    private GridPane botoesGrid;
    private final int numMaxTentativas = 5;
    private final int numMaxLetras = 7;
    private final Label[][] labelsLetra = new Label[numMaxTentativas][numMaxLetras];
    private final StringBuilder palavra = new StringBuilder();
    private String idioma;

    Palavras palavras = new Palavras();


    private String escolherPalavraAleatoria() {
        String[] palavrasDisponiveis;
        if (idioma.equals("ingles")){
            palavrasDisponiveis = palavras.EN;
        }else if (idioma.equals("portugues")){
            palavrasDisponiveis = palavras.PT;
        }else {
            palavrasDisponiveis = palavras.FR;
        }

        int tamanhoPalavra = 0;
        int indice = 0;
        while(tamanhoPalavra != numMaxLetras) {
            indice = (int) (Math.random() * palavrasDisponiveis.length);
            tamanhoPalavra = palavrasDisponiveis[indice].length();
        }
        return palavrasDisponiveis[indice];
    }

    String palavraCorreta;

    public void initialize() {
        //Muda o estilo das labels
        for (int i = 0; i < numMaxTentativas; i++) {
            for (int j = 0; j < numMaxLetras; j++) {
                Label labelLetra = (Label) letrasGrid.lookup("#letra" + ((i * numMaxLetras) + j + 1));
                labelLetra.setStyle("-fx-border-color: rgba(192, 192, 192, 1.0); -fx-text-fill: white;-fx-border-radius: 10px;");
                labelsLetra[i][j] = labelLetra;
            }
        }

        //Muda o estilo dos botões do teclado

        for (Node node : botoesGrid.getChildren()) {
            if (node instanceof Button button) {
                button.setStyle("-fx-background-color: white; -fx-text-fill: black;");
            }
        }
    }


    private Label getLetraLabel(int posicao) {
        int linha = (posicao - 1) / numMaxLetras;
        int coluna = (posicao - 1) % numMaxLetras;
        return labelsLetra[linha][coluna];
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        //Seleciona uma palavra
        if (palavraCorreta == null){
            palavraCorreta = escolherPalavraAleatoria().toUpperCase();

            palavraCorreta = removerAcentos(palavraCorreta);
        }

        Button button = (Button) event.getSource();
        String letra = button.getText();

        if (letra.equals("⌫")) {
            apagarLetra();
        } else if (letra.equals("Enter")) {
            handleEnterButton();
        } else {
            adicionaLetra(letra);
        }
    }

    private void apagarLetra() {
        if (numLetras <= 0) {
            return;
        }
        numLetras--;
        palavra.deleteCharAt(numLetras);

        int linha = numTentativas;
        int coluna = numLetras;

        Label labelLetraAtual = labelsLetra[linha][coluna];
        labelLetraAtual.setText("");
    }

    //Faz a verificação se a palavra do jogador está na lista de palavras
    private boolean palavraExistente(String palavraJogador){
        //loop que percorre cada palavra do array das palavras
        if(idioma.equals("portugues")){
            for (String palavra : palavras.PT) {
                palavra = removerAcentos(palavra);
                if (palavra.equalsIgnoreCase(palavraJogador)) {
                    return true;
                }
            }
            return false;
        } else if (idioma.equals("ingles")) {
            for (String palavra : palavras.EN) {
                if (palavra.equalsIgnoreCase(palavraJogador)) {
                    return true;
                }
            }
            return false;
        }else{
            for (String palavra : palavras.FR) {
                palavra = removerAcentos(palavra);
                if (palavra.equalsIgnoreCase(palavraJogador)) {
                    return true;
                }
            }
            return false;
        }
    }

    private void handleEnterButton() {
        if (numLetras == numMaxLetras && numTentativas < 5 ) {
            String palavraJogador = palavra.toString();
            String[] letrasCorretas = new String[numMaxLetras];
            String palavraEscolhida = palavraCorreta;
            //Verifica se a palavra do jogador está na lista
            if(palavraExistente(palavraJogador)){
                for (int i = 0; i < palavraCorreta.length(); i++) {
                    if (palavraJogador.charAt(i) == palavraCorreta.charAt(i)) {
                        palavraEscolhida = palavraEscolhida.replaceFirst(Character.toString(palavraJogador.charAt(i)), "");
                        letrasCorretas[i] = "green";
                        Button buttonLetra = (Button) botoesGrid.lookup("#but" + palavraJogador.charAt(i));
                        buttonLetra.setStyle("-fx-background-color: green; -fx-text-fill: black");
                    }

                }
                for (int i = 0; i < palavraCorreta.length(); i++) {
                    if (letrasCorretas[i] == null && palavraEscolhida.length() > 0) {
                        Button buttonLetra = (Button) botoesGrid.lookup("#but" + palavraJogador.charAt(i));
                        if (palavraEscolhida.contains(Character.toString(palavraJogador.charAt(i)))) {
                            palavraEscolhida = palavraEscolhida.replaceFirst(Character.toString(palavraJogador.charAt(i)), "");
                            letrasCorretas[i] = "gold";
                            if(!buttonLetra.getStyle().equals("-fx-background-color: green; -fx-text-fill: black")){
                                buttonLetra.setStyle("-fx-background-color: gold; -fx-text-fill: black");
                            }
                        }else {
                            letrasCorretas[i] = "gray";
                            if(!buttonLetra.getStyle().equals("-fx-background-color: green; -fx-text-fill: black") && !buttonLetra.getStyle().equals("-fx-background-color: gold; -fx-text-fill: black")){
                                buttonLetra.setStyle("-fx-background-color: gray; -fx-text-fill: black");
                            }
                        }
                    }
                }

                // Define a cor de cada letra na interface do usuário com base nos arrays acima
                for (int i = 0; i < numMaxLetras; i++) {
                    Label labelLetraAtual = getLetraLabel((numTentativas * numMaxLetras) + i + 1);
                    labelLetraAtual.setStyle("-fx-border-color: " + letrasCorretas[i] + "; -fx-text-fill: " + letrasCorretas[i] + "; -fx-border-radius: 10px;");
                }

                // Verifica se o jogador acertou a palavra e exibe a mensagem apropriada
                if (palavraJogador.equalsIgnoreCase(palavraCorreta)) {
                    if (idioma.equals("portugues")) {
                        String titlo = "Parabéns, você ganhou! 🏆";
                        //Adicionar pontos do jogador
                        int pontosJogador = (numMaxTentativas - numTentativas) * 5;
                        Pontos pontos = new Pontos();
                        pontos.adicionarPontos(pontosJogador, username);
                        try {
                            // Carrega o arquivo FXML da cena com a palavra correta
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("pagFinal.fxml"));
                            Parent root = loader.load();

                            //Passar o texto para o pagFinalController
                            pagFinalController pagFinalController = loader.getController();
                            pagFinalController.getUsername(username);
                            pagFinalController.getIdioma(idioma);
                            pagFinalController.getTitlo(titlo);
                            pagFinalController.getDificuldade("dificil");


                            // Obtém o objeto Stage atual
                            Stage stage = (Stage) butEnter.getScene().getWindow();

                            // Cria uma nova cena com o conteúdo carregado do arquivo FXML
                            Scene scene = new Scene(root);


                            // Define a nova cena no objeto Stage
                            stage.setScene(scene);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else if (idioma.equals("ingles")){
                        String titlo = "Congratulations, you won! 🏆";
                        int pontosJogador = (numMaxTentativas - numTentativas) * 5;
                        Pontos pontos = new Pontos();
                        pontos.adicionarPontos(pontosJogador, username);
                        try {
                            // Carrega o arquivo FXML da cena com a palavra correta
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("pagFinal.fxml"));
                            Parent root = loader.load();

                            //Passar o texto para o pagFinalController
                            pagFinalController pagFinalController = loader.getController();
                            pagFinalController.getUsername(username);
                            pagFinalController.getIdioma(idioma);
                            pagFinalController.getTitlo(titlo);
                            pagFinalController.getDificuldade("dificil");

                            // Obtém o objeto Stage atual
                            Stage stage = (Stage) butEnter.getScene().getWindow();

                            // Cria uma nova cena com o conteúdo carregado do arquivo FXML
                            Scene scene = new Scene(root);

                            // Define a nova cena no objeto Stage
                            stage.setScene(scene);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        String titlo = "Félicitations, vous avez gagné! 🏆";
                        int pontosJogador = (numMaxTentativas - numTentativas) * 5;
                        Pontos pontos = new Pontos();
                        pontos.adicionarPontos(pontosJogador, username);
                        try {
                            // Carrega o arquivo FXML da cena com a palavra correta
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("pagFinal.fxml"));
                            Parent root = loader.load();

                            //Passar o texto para o pagFinalController
                            pagFinalController pagFinalController = loader.getController();
                            pagFinalController.getUsername(username);
                            pagFinalController.getIdioma(idioma);
                            pagFinalController.getTitlo(titlo);
                            pagFinalController.getDificuldade("dificil");

                            // Obtém o objeto Stage atual
                            Stage stage = (Stage) butEnter.getScene().getWindow();

                            // Cria uma nova cena com o conteúdo carregado do arquivo FXML
                            Scene scene = new Scene(root);

                            // Define a nova cena no objeto Stage
                            stage.setScene(scene);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    numTentativas++;
                    if (numTentativas == numMaxTentativas) {
                        if (idioma.equals("portugues")) {
                            String titlo = "Oops, você perdeu! \uD83D\uDE15";
                            String label = "A palavra correta era: " + palavraCorreta;
                            try {
                                // Carrega o arquivo FXML da cena com a palavra correta
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("pagFinal.fxml"));
                                Parent root = loader.load();

                                //Passar o texto para o pagFinalController
                                pagFinalController pagFinalController = loader.getController();
                                pagFinalController.getLabel(label);
                                pagFinalController.getUsername(username);
                                pagFinalController.getIdioma(idioma);
                                pagFinalController.getTitlo(titlo);
                                pagFinalController.resetPontos(username);
                                pagFinalController.getDificuldade("dificil");

                                // Obtém o objeto Stage atual
                                Stage stage = (Stage) butEnter.getScene().getWindow();

                                // Cria uma nova cena com o conteúdo carregado do arquivo FXML
                                Scene scene = new Scene(root);

                                // Define a nova cena no objeto Stage
                                stage.setScene(scene);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }else if (idioma.equals("ingles")){
                            String titlo = "Oops, you missed it! \uD83D\uDE15";
                            String label = "The correct word was: " + palavraCorreta;
                            try {
                                // Carrega o arquivo FXML da cena com a palavra correta
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("pagFinal.fxml"));
                                Parent root = loader.load();

                                //Passar o texto para o pagFinalController
                                pagFinalController pagFinalController = loader.getController();
                                pagFinalController.getLabel(label);
                                pagFinalController.getUsername(username);
                                pagFinalController.getIdioma(idioma);
                                pagFinalController.getTitlo(titlo);
                                pagFinalController.resetPontos(username);
                                pagFinalController.getDificuldade("dificil");

                                // Obtém o objeto Stage atual
                                Stage stage = (Stage) butEnter.getScene().getWindow();

                                // Cria uma nova cena com o conteúdo carregado do arquivo FXML
                                Scene scene = new Scene(root);

                                // Define a nova cena no objeto Stage
                                stage.setScene(scene);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                            String titlo = "Oups, vous l'avez raté! \uD83D\uDE15";
                            String label = "Le mot correct était : " + palavraCorreta;
                            try {
                                // Carrega o arquivo FXML da cena com a palavra correta
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("pagFinal.fxml"));
                                Parent root = loader.load();

                                //Passar o texto para o pagFinalController
                                pagFinalController pagFinalController = loader.getController();
                                pagFinalController.getLabel(label);
                                pagFinalController.getUsername(username);
                                pagFinalController.getIdioma(idioma);
                                pagFinalController.getTitlo(titlo);
                                pagFinalController.resetPontos(username);
                                pagFinalController.getDificuldade("dificil");

                                // Obtém o objeto Stage atual
                                Stage stage = (Stage) butEnter.getScene().getWindow();

                                // Cria uma nova cena com o conteúdo carregado do arquivo FXML
                                Scene scene = new Scene(root);

                                // Define a nova cena no objeto Stage
                                stage.setScene(scene);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                palavra.setLength(0);
                numLetras = 0;
            }else{
                if(idioma.equals("portugues")){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Palavra Errada");
                    alert.setHeaderText(null);
                    alert.setContentText("Palavra não encontrada na lista de palavras!");
                    alert.showAndWait();
                }else if(idioma.equals("ingles")){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Wrong word");
                    alert.setHeaderText(null);
                    alert.setContentText("Word not found in the word list!");
                    alert.showAndWait();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Mauvais mot");
                    alert.setHeaderText(null);
                    alert.setContentText("Mot introuvable dans la liste de mots!");
                    alert.showAndWait();
                }
            }
        } else {
            if (idioma.equals("portugues")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Palavra curta");
                alert.setHeaderText(null);
                alert.setContentText("A palavra deve conter " + numMaxLetras + " letras.");

                alert.showAndWait();
            }else if (idioma.equals("ingles")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Short word");
                alert.setHeaderText(null);
                alert.setContentText("The word must contain " + numMaxLetras + " letters.");

                alert.showAndWait();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Mot court");
                alert.setHeaderText(null);
                alert.setContentText("Le mot doit contenir " + numMaxLetras + " lettres.");

                alert.showAndWait();
            }
        }
    }

    private void resetGame() {
        numLetras = 0;
        numTentativas = 0;
        palavra.setLength(0);
        for (Label[] labels : labelsLetra) {
            for (Label label : labels) {
                label.setText("");
                label.setStyle("-fx-border-color: black; -fx-text-fill: black;-fx-border-radius: 10px;");
            }
        }
        palavraCorreta = null;
        initialize();
    }

    private void adicionaLetra(String letra) {
        if (numLetras < numMaxLetras) {
            int linha = numTentativas;
            int coluna = numLetras;
            palavra.append(letra);

            Label labelLetraAtual = labelsLetra[linha][coluna];
            labelLetraAtual.setText(letra);

            numLetras++;
        }
    }

    @FXML
    private void resetButton(){
        resetGame();
    }

    private String removerAcentos(String palavra) {
        //Separa os caracteres acentuados com base na sua marca diacrítica correspondente.
        String palavraSemAcentos = Normalizer.normalize(palavra, Normalizer.Form.NFD);
        //remove esses acentos agudos, circunflexos, entre outros,
        palavraSemAcentos = palavraSemAcentos.replaceAll("\\p{M}", "");
        //remove qualquer caractere que não seja ASCII, exemplo ~
        palavraSemAcentos = palavraSemAcentos.replaceAll("[^\\p{ASCII}]", "");
        return palavraSemAcentos;
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
    Button pagInicialView;
    @FXML
    public void trocarViewToPagInicial() throws IOException {
        //Mudar de view para o ranking
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Parent root = loader.load();

        MainController mainController = loader.getController();
        if(idioma.equals("ingles")){
            mainController.selecionarIdiomaIngles();
        }else if(idioma.equals("portugues")){
            mainController.selecionarIdiomaPortugues();
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
        resetGame();
    }
    @FXML
    public void selecionarIdiomaIngles() {
        idioma = "ingles";
        Ingles.setStyle("-fx-effect: dropshadow(gaussian, gray, 2, 1, 0, 0);");
        Portugal.setStyle(null);
        Frances.setStyle(null);
        resetGame();
    }
    @FXML
    public void selecionarIdiomaFrances() {
        idioma = "frances";
        Frances.setStyle("-fx-effect: dropshadow(gaussian, gray, 2, 1, 0, 0);");
        Portugal.setStyle(null);
        Ingles.setStyle(null);
        resetGame();
    }

    private String username;
    public void setUsername(String username) {
        this.username = username;
    }
}