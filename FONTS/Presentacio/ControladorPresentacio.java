package Presentacio;

import com.google.gson.Gson;
import domini.Mastermind;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import java.util.Optional;
import javafx.geometry.Insets;


public class ControladorPresentacio{

    private Mastermind m = new Mastermind();
    Gson gson = new Gson();
    public Button testbut;
    public Button checkbutton;
    public Button rankingbut;
    public Button loadgamebut;
    public Button exitbut;
    public Pane fPrincipal;
    public Pane fPlayset;
    public Pane fRanking;
    public ChoiceBox<String> difficulty;
    public ChoiceBox<String> nGames;
    public Button playbutton;
    public Button back1button;
    public ImageView ImatgeMastermind;
    public Button helpbut;
    public Pane fHelp;
    public Button back2button;
    public BorderPane fPlay;
    public ChoiceBox<String> nColors;
    public ChoiceBox<String> nRows;
    public ChoiceBox<String> nColumns;
    public Label textNrows;
    public Label textNcolumns;
    public Label textNcolors;
    public Label textNrowsTest;
    public Label textNcolumnsTest;
    public Label textNcolorsTest;
    public Pane fGameTest;
    public Button TestAgainbutton;
    public Button backPrincTestbut;
    public Label fiveGuessScore;
    public Label geneticScore;
    public ChoiceBox<String> nColumnsTest;
    public ChoiceBox<String> nRowsTest;
    public ChoiceBox<String> nColorsTest;
    public Button backTestbutton;
    public ChoiceBox<String> difficultyTest;
    public ChoiceBox<String> nGamesTest;
    public Button playTestbutton;
    public Pane fTest;
    public Button optionsbutton;
    public Button endgamebut1;
    public Button exitgamebut;
    public Button savegamebut;
    public Button gobackgamebut;
    public Pane fOptions;
    @FXML ListView rankingList;
    @FXML GridPane board;
    @FXML GridPane colors;
    public Label player1Score;
    public Label player2Score;
    public Label checkInvalid;
    private Circle source = new Circle();
    private Circle target = new Circle();
    private GridPane gameBoard;
    private GridPane colorBoard;
    private int nrows;
    private int ncolumns;
    private int ncolors;
    private boolean firstTurn;


    public ControladorPresentacio () {
    }

    //Quan es clica el boto PLAYSET del menu principal
    public void playset(ActionEvent actionEvent) {
        fPrincipal.setVisible(false);
        ImatgeMastermind.setVisible(false);
        fPlayset.setVisible(true);
        difficulty.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> selected, String oldValue, String newValue) {
                if (oldValue != null) {
                    if(oldValue.equals("CUSTOM") ) {
                        nColors.setVisible(false);
                        nRows.setVisible(false);
                        nColumns.setVisible(false);
                        textNrows.setVisible(false);
                        textNcolumns.setVisible(false);
                        textNcolors.setVisible(false);
                    }
                }
                if (newValue != null) {
                    if(newValue.equals("CUSTOM") ) {
                        nColors.setVisible(true);
                        nRows.setVisible(true);
                        nColumns.setVisible(true);
                        textNrows.setVisible(true);
                        textNcolumns.setVisible(true);
                        textNcolors.setVisible(true);
                    }
                }
            }
        });
    }

    //Quan es clica el boto RANKING del menu principal
    public void ranking(ActionEvent actionEvent) {
        fPrincipal.setVisible(false);
        ImatgeMastermind.setVisible(false);
        fRanking.setVisible(true);
        rankingList.getItems().clear();
        ObservableList<String> items = rankingList.getItems();
        items.add("PLAYER MIN.GUESSES DIFF");
        for(int i = 0;i<10;i++)items.add(m.getRankingRow(i));
    }

    //Quan es clica el boto LOADGAME del menu principal
    public void loadGame(ActionEvent actionEvent) {
        if (m.savedGame()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Load game");
            alert.setContentText("Game loaded successfully!");
            alert.showAndWait();
            fPrincipal.setVisible(false);
            ImatgeMastermind.setVisible(false);
            String game = m.loadSet();
            m = gson.fromJson(game, Mastermind.class);
            nrows = m.getNumberRows();
            ncolumns = m.getNumberColumns();
            ncolors = m.getNumberColors();
            fPlay.setVisible(true);
            initializeBoards();
            FillBoard();
            if (m.getGameHeight() == 0) {
                firstTurn = true;
                if (!m.humanPlaying) checkbutton.setText("SET SOLUTION");
                else checkbutton.setText("CHECK");
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Load game");
            alert.setContentText("There is no saved game!");
            alert.showAndWait();
        }
    }

    //Funcio per ompir el taulell quan es carrega una partida guardada
    private void FillBoard() {
        //Carrega scores
        player1Score.setText(String.valueOf(m.score.a));
        player2Score.setText(String.valueOf(m.score.b));

        //Carregar cercles taulell
        int h = m.getGameHeight();
        for (int r = 0; r < h; r++) {
            String combination = m.boardcomb(r);
            for (int i = 0; i < ncolumns; ++i) {
                String id = String.valueOf(ncolumns * (nrows - 1 - r) + i);
                Circle cercle = (Circle) gameBoard.lookup('#' + id);
                switch (combination.charAt(i)) {
                    case '0':
                        cercle.setFill(Color.BLUE);
                        break;
                    case '1':
                        cercle.setFill(Color.RED);
                        break;
                    case '2':
                        cercle.setFill(Color.GREEN);
                        break;
                    case '3':
                        cercle.setFill(Color.YELLOW);
                        break;
                    case '4':
                        cercle.setFill(Color.BROWN);
                        break;
                    case '5':
                        cercle.setFill(Color.PINK);
                        break;
                    case '6':
                        cercle.setFill(Color.TURQUOISE);
                        break;
                    case '7':
                        cercle.setFill(Color.ORANGE);
                        break;
                }
            }
        }
        //Carregar cercles answers
        for (int r = 0; r < h; r++) {
            int a = m.boardmatchPos(r);
            int b = m.boardmatchCol(r);
            //Bucle per donar color per color i posicio acertada
            for (int i = 0; i < a; ++i) {
                String id = "a" + String.valueOf(ncolumns * (nrows - 1 - r) + i);
                Circle cercle = (Circle) gameBoard.lookup('#' + id);
                cercle.setFill(Color.WHITE);
            }
            //Bucle per donar color per solament color acertat
            for (int i = 0; i < b; ++i) {
                String id = "a" + String.valueOf(ncolumns * (nrows - 1 - r) + i + a);
                Circle cercle = (Circle) gameBoard.lookup('#' + id);
                cercle.setFill(Color.BLACK);
            }
        }
    }

    //Quan es clica el boto EXIT del menu principal
    public void exit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        String s = "Are you sure you want to exit?";
        alert.setContentText(s);
        Optional<ButtonType> result = alert.showAndWait();
        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            System.exit(0);
        }
    }

    //Quan es clica el boto PLAY del menu Playset
    public void play(ActionEvent actionEvent) {
        int ngames = Integer.parseInt(nGames.getSelectionModel().getSelectedItem().toString());
        String diff = difficulty.getSelectionModel().getSelectedItem();
        if (diff.equals("CUSTOM")) {
            nrows = Integer.parseInt(nRows.getSelectionModel().getSelectedItem().toString());
            ncolumns = Integer.parseInt(nColumns.getSelectionModel().getSelectedItem().toString());
            ncolors = Integer.parseInt(nColors.getSelectionModel().getSelectedItem().toString());
        }
        else {
            if (diff.equals("RETARDED")){
                nrows = 11;
                ncolumns = 4;
                ncolors = 6;
            }
            else if (diff.equals("AMATEUR")){
                nrows = 10;
                ncolumns = 5;
                ncolors = 7;
            }
            else {
                nrows = 9;
                ncolumns = 6;
                ncolors = 8;
            }
        }

        fPlayset.setVisible(false);
        fPlay.setVisible(true);
        initializeBoards();
        m = new Mastermind(nrows, ncolumns, ncolors, ngames);
        firstTurn = true;

    }

    //Funcio per inicialitzar el taulell i colors
    private void initializeBoards(){
        //Per si lusuari ha sortit del joc quan havia posat combinacio malament
        checkbutton.setText("CHECK");
        checkInvalid.setText("");
        fPlay.setDisable(false);

        colorBoard = colors;
        colorBoard.setPadding(new Insets(5, 5, 5, 5));
        colorBoard.setHgap(2);
        colorBoard.setVgap(2);

        //Inicialitzacio cercles colors
        for (int r = 0; r < 2; r++) {           //N de files i columnes on hi hauran colors
            for (int c = 0; c < ncolors/2; c++) {
                int number = c * 2 + r;
                source = new Circle();
                source.setRadius(10);
                source.setStrokeWidth(1);
                source.setStroke(Color.BLACK);
                source.setId(String.valueOf(number));
                switch (number){
                    case 0: source.setFill(Color.BLUE); break;
                    case 1: source.setFill(Color.RED); break;
                    case 2: source.setFill(Color.GREEN); break;
                    case 3: source.setFill(Color.YELLOW); break;
                    case 4: source.setFill(Color.BROWN); break;
                    case 5: source.setFill(Color.PINK); break;
                    case 6: source.setFill(Color.TURQUOISE); break;
                    case 7: source.setFill(Color.ORANGE); break;
                }
                source.setOnMousePressed(sourceOnMousePressedEventHandler);
                colorBoard.add(source, c, r);
            }
        }
        //Inicialitzacio ultim cercle de colors en cas de que sigui imparell el nombre de colors
        if (ncolors % 2 != 0) {
            int number = ncolors-1;
            source = new Circle();
            source.setRadius(10);
            source.setStrokeWidth(1);
            source.setStroke(Color.BLACK);
            source.setId(String.valueOf(number));
            switch (number){
                case 0: source.setFill(Color.BLUE); break;
                case 1: source.setFill(Color.RED); break;
                case 2: source.setFill(Color.GREEN); break;
                case 3: source.setFill(Color.YELLOW); break;
                case 4: source.setFill(Color.BROWN); break;
                case 5: source.setFill(Color.PINK); break;
                case 6: source.setFill(Color.TURQUOISE); break;
                case 7: source.setFill(Color.ORANGE); break;
            }
            source.setOnMousePressed(sourceOnMousePressedEventHandler);
            colorBoard.add(source, ncolors/2 + 1, 0);
        }

        gameBoard = board;
        gameBoard.setPadding(new Insets(2, 2, 2, 2));
        gameBoard.setHgap(2);
        gameBoard.setVgap(2);

        //Inicialitzacio cercles taulell
        for (int r = 0; r <= nrows; r++) {          //N de files
            for (int c = 0; c < ncolumns; c++) {       //N de columnes
                int number = ncolumns * r + c;
                target = new Circle();
                target.setRadius(10);
                target.setStrokeWidth(1);
                target.setStroke(Color.BLACK);
                target.setId(String.valueOf(number));
                if (r == nrows)target.setFill(Color.GREY); //Cercles per fer la guess
                else target.setFill(Color.SILVER);
                gameBoard.add(target, c, r);
            }
        }
        //Inicialitzacio cercles answers
        for (int r = 0; r < nrows; r++) {
            for (int c = 0; c < ncolumns; c++) {
                int number = ncolumns * r + c;
                Circle cercle = new Circle();
                cercle.setRadius(5);
                cercle.setStrokeWidth(1);
                cercle.setStroke(Color.BLACK);
                cercle.setId("a"+String.valueOf(number));
                cercle.setFill(Color.SILVER);
                gameBoard.add(cercle, c + ncolumns, r);
            }
        }

        colorBoard.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                Dragboard db = source.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                content.putString(source.getId());
                db.setContent(content);
                event.consume();
            }
        });

        gameBoard.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                if (event.getGestureSource() != target) {
                    event.acceptTransferModes(TransferMode.COPY);
                }
                event.consume();
            }
        });

        gameBoard.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                event.consume();
            }
        });

        gameBoard.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                event.consume();
            }
        });

        gameBoard.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                Node node = event.getPickResult().getIntersectedNode();
                Integer cIndex = GridPane.getColumnIndex(node);
                Integer rIndex = GridPane.getRowIndex(node);
                int x = cIndex == null ? 0 : cIndex;
                int y = rIndex == null ? 0 : rIndex;
                String id = String.valueOf(ncolumns * y + x);
                Circle cercle = (Circle) gameBoard.lookup('#'+id);
                if (ncolumns*y+x >= ncolumns*nrows) cercle.setFill(source.getFill());
                event.consume();
            }
        });

        colorBoard.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                event.consume();
            }
        });
    }

    //Funcio que determina si algun cercle de la guess no sha omplert
    private boolean invalidCheck() {
        for (int c = 0; c < ncolumns; c++) {
            String id = String.valueOf(ncolumns * nrows + c);
            Circle cercle = (Circle) gameBoard.lookup('#'+id);
            if(cercle.getFill().equals(Color.GREY)) {
                checkInvalid.setText("The colors are not well-introduced");
                return true;
            }
        }
        checkInvalid.setText("");
        return false;
    }

    //Quan es clica el boto GO BACK de la pantalla Playset
    public void back(ActionEvent actionEvent) {
        fPrincipal.setVisible(true);
        ImatgeMastermind.setVisible(true);
        fPlayset.setVisible(false);
    }

    //Quan es clica el boto HELP del menu principal
    public void help(ActionEvent actionEvent) {
        fPrincipal.setVisible(false);
        ImatgeMastermind.setVisible(false);
        fHelp.setVisible(true);
    }

    //Quan es clica el boto GO BACK de la pantalla Help
    public void back2(ActionEvent actionEvent) {
        fPrincipal.setVisible(true);
        ImatgeMastermind.setVisible(true);
        fHelp.setVisible(false);
    }
    //Quan es clica el boto GO BACK del ranquing
    public void back3(ActionEvent actionEvent){
        fPrincipal.setVisible(true);
        ImatgeMastermind.setVisible(true);
        fRanking.setVisible(false);
    }

    //Quan es clica el boto CHECK, SET SOLUTION o START IA PLAY de la pantalla Play
    public void check(ActionEvent actionEvent) {
        String solution = "";
        if (!m.humanPlaying && firstTurn) {//Si es la primera jugada i lhuma posa la solucio
            if (!invalidCheck()) {
                for (int i = 0; i < ncolumns; ++i) {
                    String id = String.valueOf(ncolumns * nrows + i);
                    Circle cercle = (Circle) gameBoard.lookup('#' + id);
                    if (cercle.getFill().equals(Color.BLUE)) solution += "0";
                    else if (cercle.getFill().equals(Color.RED)) solution += "1";
                    else if (cercle.getFill().equals(Color.GREEN)) solution += "2";
                    else if (cercle.getFill().equals(Color.YELLOW)) solution += "3";
                    else if (cercle.getFill().equals(Color.BROWN)) solution += "4";
                    else if (cercle.getFill().equals(Color.PINK)) solution += "5";
                    else if (cercle.getFill().equals(Color.TURQUOISE)) solution += "6";
                    else solution += "7";
                    cercle.setFill(Color.GREY);
                }
                m.setSolution(solution);
                firstTurn = false;
                checkbutton.setText("START IA PLAY");
            }
        }
        else {
            if (m.humanPlaying) {
                if (firstTurn) {//Si es primer torn, IA posa la solucio
                    m.setSolution(solution);
                    firstTurn = false;
                }
                String guess = "";

                if (!invalidCheck()) {
                    for (int i = 0; i < ncolumns; ++i) {
                        String id = String.valueOf(ncolumns * nrows + i);
                        Circle cercle = (Circle) gameBoard.lookup('#' + id);
                        if (cercle.getFill().equals(Color.BLUE)) guess += "0";
                        else if (cercle.getFill().equals(Color.RED)) guess += "1";
                        else if (cercle.getFill().equals(Color.GREEN)) guess += "2";
                        else if (cercle.getFill().equals(Color.YELLOW)) guess += "3";
                        else if (cercle.getFill().equals(Color.BROWN)) guess += "4";
                        else if (cercle.getFill().equals(Color.PINK)) guess += "5";
                        else if (cercle.getFill().equals(Color.TURQUOISE)) guess += "6";
                        else guess += "7";
                        cercle.setFill(Color.GREY);
                    }
                    m.setComb(guess);
                    int h = m.getGameHeight();
                    m.doPlay();
                    String s = m.boardcomb(h);
                    int a = m.boardmatchPos(h);
                    int b = m.boardmatchCol(h);

                    //Bucle per donar color a la fila del taulell jugada
                    for (int i = 0; i < ncolumns; ++i) {
                        String id = String.valueOf(ncolumns * (nrows - 1 - h) + i);
                        Circle cercle = (Circle) gameBoard.lookup('#' + id);
                        char number = s.charAt(i);
                        switch (Character.getNumericValue(number)) {
                            case 0:
                                cercle.setFill(Color.BLUE);
                                break;
                            case 1:
                                cercle.setFill(Color.RED);
                                break;
                            case 2:
                                cercle.setFill(Color.GREEN);
                                break;
                            case 3:
                                cercle.setFill(Color.YELLOW);
                                break;
                            case 4:
                                cercle.setFill(Color.BROWN);
                                break;
                            case 5:
                                cercle.setFill(Color.PINK);
                                break;
                            case 6:
                                cercle.setFill(Color.TURQUOISE);
                                break;
                            case 7:
                                cercle.setFill(Color.ORANGE);
                                break;
                        }
                    }
                    //Bucle per donar color per color o posicio acertada
                    for (int i = 0; i < a; ++i) {
                        String id = "a" + String.valueOf(ncolumns * (nrows - 1 - h) + i);
                        Circle cercle = (Circle) gameBoard.lookup('#' + id);
                        cercle.setFill(Color.WHITE);
                    }
                    //Bucle per donar color per solament color acertat
                    for (int i = 0; i < b; ++i) {
                        String id = "a" + String.valueOf(ncolumns * (nrows - 1 - h) + i + a);
                        Circle cercle = (Circle) gameBoard.lookup('#' + id);
                        cercle.setFill(Color.BLACK);
                    }

                    if (m.endedGame()) {
                        firstTurn = true;
                        m.saveGameStats();
                        m.i++;
                        if (m.i % 2 == 0) {
                            m.saveSetStats();
                            m.setI(0);
                        }
                        //Actualitzacio scores de la interficie
                        player1Score.setText(String.valueOf(m.score.a));
                        player2Score.setText(String.valueOf(m.score.b));
                        m.humanPlaying = !m.humanPlaying;
                        alertaGame("jugador");
                        checkbutton.setText("SET SOLUTION");
                        cleanboard();
                    }
                }
            }
            else { //Si juga la IA
                while(!m.endedGame()){
                    int h = m.getGameHeight();
                    m.doPlay();
                    String s = m.boardcomb(h);
                    int a = m.boardmatchPos(h);
                    int b = m.boardmatchCol(h);

                    //Bucle per donar color a la fila del taulell jugada
                    for (int i = 0; i < ncolumns; ++i) {
                        String id = String.valueOf(ncolumns * (nrows-1-h) + i);
                        Circle cercle = (Circle) gameBoard.lookup('#'+id);
                        char number = s.charAt(i);
                        switch (Character.getNumericValue(number)){
                            case 0: cercle.setFill(Color.BLUE); break;
                            case 1: cercle.setFill(Color.RED); break;
                            case 2: cercle.setFill(Color.GREEN); break;
                            case 3: cercle.setFill(Color.YELLOW); break;
                            case 4: cercle.setFill(Color.BROWN); break;
                            case 5: cercle.setFill(Color.PINK); break;
                            case 6: cercle.setFill(Color.TURQUOISE); break;
                            case 7: cercle.setFill(Color.ORANGE); break;
                        }
                    }
                    //Bucle per donar color per color o posicio acertada
                    for (int i = 0; i < a; ++i) {
                        String id = "a" + String.valueOf(ncolumns * (nrows-1-h) + i);
                        Circle cercle = (Circle) gameBoard.lookup('#'+id);
                        cercle.setFill(Color.WHITE);
                    }
                    //Bucle per donar color per solament color acertat
                    for (int i = 0; i < b; ++i) {
                        String id = "a" + String.valueOf(ncolumns * (nrows-1-h) + i + a);
                        Circle cercle = (Circle) gameBoard.lookup('#'+id);
                        cercle.setFill(Color.BLACK);
                    }
                }
                firstTurn = true;
                m.saveGameStats();
                m.i++;
                if(m.i%2 == 0){m.saveSetStats();m.setI(0);}
                //Actualitzacio scores de la interficie
                player1Score.setText(String.valueOf(m.score.a));
                player2Score.setText(String.valueOf(m.score.b));
                m.humanPlaying = !m.humanPlaying;
                alertaGame("ia");
                checkbutton.setText("CHECK");
                if(m.score.a == m.gamesNeeded || m.score.b == m.gamesNeeded) endset();
                else cleanboard();
            }
        }
    }

    //Funcio que crea una alerta al final de la ronda
    private void alertaGame(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game results");
        if (s.equals("jugador")){ //Si juga el jugador
            if (m.isVictorious()) { //Si acerta la solucio
                int h = m.getGameHeight();
                alert.setContentText("You have guessed the solution in " + h + " guesses!"+"\n"+"Now its IA turn");
            }
            else alert.setContentText("You have not guessed the solution."+"\n"+"Now its IA turn");
        }
        else { //Si juga la IA
            if (m.isVictorious()) { //Si la IA acerta la solucio
                int h = m.getGameHeight();
                alert.setContentText("IA has guessed the solution in " + h + " guesses!"+"\n"+"Round has finished. Scores:"+"\n"+"You: " + m.score.a +"\n"+"IA: " + m.score.b);
            }
            else alert.setContentText("IA has not guessed the solution."+"\n"+"Round has finished. Scores:"+"\n"+"You: " + m.score.a +"\n"+"IA: " + m.score.b);
        }
        alert.showAndWait();
    }

    //Funcio que sexecuta quan acaba el set de partides
    private void endset() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        String titol;
        if (m.score.a == m.gamesNeeded) titol = "YOU WON THE SET!";
        else titol = "YOU LOST THE SET!";
        alert.setTitle(titol);
        String s = "Do you want to save results on ranking?";
        alert.setContentText(s);
        Optional<ButtonType> result = alert.showAndWait();
        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Save results");
            dialog.setHeaderText("Enter your name:");
            Optional<String> resultat = dialog.showAndWait();
            String name = " ";
            boolean finished = false;
            while (resultat.isPresent() && !finished) {
                name = resultat.get();
                if (name == null || name.isEmpty() || !name.matches("[A-Za-z]*")) {
                    dialog.setHeaderText("Invalid name, only letters and one word. Enter your name:");
                    resultat = dialog.showAndWait();
                }
                else {
                    m.save_score_to_txt(name);
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Save results");
                    alert2.setContentText("Results saved successfully!");
                    alert2.showAndWait();
                    finished = true;
                }
            }
        }
        resetboard();
        fPlay.setVisible(false);
        fPrincipal.setVisible(true);
        ImatgeMastermind.setVisible(true);
    }

    //Funcio per netejar el taulell
    private void cleanboard() {
        //Inicialitzacio cercles taulell
        for (int r = 0; r <= nrows; r++) {          //N de files
            for (int c = 0; c < ncolumns; c++) {       //N de columnes
                String id = String.valueOf(ncolumns * r + c);
                Circle cercle = (Circle) gameBoard.lookup('#'+id);
                if (r == nrows)cercle.setFill(Color.GREY); //Cercles per fer la guess
                else cercle.setFill(Color.SILVER);

            }
        }
        //Inicialitzacio cercles answers
        for (int r = 0; r < nrows; r++) {
            for (int c = 0; c < ncolumns; c++) {
                String id = "a" + String.valueOf(ncolumns * r + c);
                Circle cercle = (Circle) gameBoard.lookup('#'+id);
                cercle.setFill(Color.SILVER);
            }
        }
    }

    //Funcio per netejar els taulells i les puntuacions
    private void resetboard() {
        player1Score.setText("0");
        player2Score.setText("0");
        gameBoard.getChildren().clear();
        colorBoard.getChildren().clear();
    }

    //Quan es clica el boto OPTIONS de la pantalla Play
    public void options(ActionEvent actionEvent) {
        fOptions.setVisible(true);
        fPlay.setOpacity(.30);
        fPlay.setDisable(true);

    }

    //Quan es clica el boto GO BACK de la pantalla Play
    public void backGame(ActionEvent actionEvent) {
        fOptions.setVisible(false);
        fPlay.setOpacity(1);
        fPlay.setDisable(false);

    }

    //Quan es clica el boto SAVE GAME de la pantalla PLAY
    public void saveGame(ActionEvent actionEvent) {
        //GUARDAR DADES ACTUALS DEL GAME AL STRINGJSON
        String stringJSON = gson.toJson(m);
        m.saveSet(stringJSON);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Save SetGame");
        alert.setContentText("SetGame saved successfully!");
        alert.showAndWait();
    }

    //Quan es clica el boto EXIT de la pantalla Play
    public void exitGame(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        String s = "Are you sure you want to exit?";
        alert.setContentText(s);
        Optional<ButtonType> result = alert.showAndWait();
        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            System.exit(0);
        }
    }

    //Quan es clica el boto END GAME de la pantalla PLAY
    public void endGame(ActionEvent actionEvent) {
        fOptions.setVisible(false);
        fPlay.setOpacity(1);
        fPlay.setVisible(false);
        fPrincipal.setVisible(true);
        ImatgeMastermind.setVisible(true);
        resetboard();
    }

    //Quan es clica el boto FV vs Genetic del menu principal
    public void testAlgorithms(ActionEvent actionEvent) {
        fPrincipal.setVisible(false);
        ImatgeMastermind.setVisible(false);
        fTest.setVisible(true);
        difficultyTest.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> selected, String oldValue, String newValue) {
                if (oldValue != null) {
                    if(oldValue.equals("CUSTOM") ) {
                        nColorsTest.setVisible(false);
                        nRowsTest.setVisible(false);
                        nColumnsTest.setVisible(false);
                        textNrowsTest.setVisible(false);
                        textNcolumnsTest.setVisible(false);
                        textNcolorsTest.setVisible(false);
                    }
                }
                if (newValue != null) {
                    if(newValue.equals("CUSTOM") ) {
                        nColorsTest.setVisible(true);
                        nRowsTest.setVisible(true);
                        nColumnsTest.setVisible(true);
                        textNrowsTest.setVisible(true);
                        textNcolumnsTest.setVisible(true);
                        textNcolorsTest.setVisible(true);
                    }
                }
            }
        });
    }

    //Quan es clica el boto TEST AGAIN de la pantalla Test
    public void testAgain(ActionEvent actionEvent) {
        fGameTest.setVisible(false);
        fTest.setVisible(true);
    }

    //Quan es clica el boto MAIN MENU de la pantalla GameTest
    public void backPrincTest(ActionEvent actionEvent) {
        fGameTest.setVisible(false);
        fPrincipal.setVisible(true);
        ImatgeMastermind.setVisible(true);
    }

    //Quan es clica el boto GO BACK de la pantalla Test
    public void backTest(ActionEvent actionEvent) {
        fPrincipal.setVisible(true);
        ImatgeMastermind.setVisible(true);
        fTest.setVisible(false);
    }

    //Quan es clica el boto PLAY de la pantalla Test
    public void playTest(ActionEvent actionEvent) {
        fTest.setVisible(false);
        fGameTest.setVisible(true);
        int ngames = Integer.parseInt(nGamesTest.getSelectionModel().getSelectedItem().toString());
        String diff = difficultyTest.getSelectionModel().getSelectedItem();
        int rows, columns, colors;
        if (diff.equals("CUSTOM")) {
            rows = Integer.parseInt(nRows.getSelectionModel().getSelectedItem().toString());
            columns = Integer.parseInt(nColumns.getSelectionModel().getSelectedItem().toString());
            colors = Integer.parseInt(nColors.getSelectionModel().getSelectedItem().toString());
        }
        else {
            if (diff.equals("RETARDED")){
                rows = 11;
                columns = 4;
                colors = 6;
            }
            else if (diff.equals("AMATEUR")){
                rows = 10;
                columns = 5;
                colors = 7;
            }
            else {
                rows = 9;
                columns = 6;
                colors = 8;
            }
        }
        m = new Mastermind(rows, columns, colors, ngames);
        m.doTest(rows, columns, colors, ngames);
        geneticScore.setText(String.valueOf(m.getScoreGenetic()));
        fiveGuessScore.setText(String.valueOf(m.getScoreFG()));
    }

    EventHandler<MouseEvent> sourceOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    source = (Circle)t.getSource();
                }
            };

}