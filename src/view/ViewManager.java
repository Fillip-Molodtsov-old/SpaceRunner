package view;

import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ViewManager {
    private static final double WIDTH = 1000;
    private static final double HEIGHT = 600;
    private static  final double BUTTONS_ALIGNMENT_X = 100;
    private static final double BUTTONS_ALIGNMENT_Y_START = 100;

    private AnchorPane mainPane;
    private PanelMenuSubScene playsubScene;
    private PanelMenuSubScene scoresSubScene;
    private PanelMenuSubScene helpsubScene;
    private PanelMenuSubScene lvlModeSubScene;
    private PanelMenuSubScene subSceneToHide;
    private Scene mainScene;
    private Stage mainStage;
    private List<MenuButton> buttons;

    private List<DifficultyPicker> diffList;
    private int choosenDifficulty;
    private List<PlayerPicker> shipList;
    private Player choosenPlayer;

    private static final String fileString = "src/model/score.txt";

    public ViewManager(){
        buttons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane,WIDTH,HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createButtons();
        createBackground();
        createLabel();
        createSubScenes();
    }

    /**
     * show/hide subScene
     * @param subScene - chosen subScene
     */
    private void showSubScene(PanelMenuSubScene subScene){
        if(subSceneToHide!=null){
            subSceneToHide.moveSubScene();
        }
        subScene.moveSubScene();
        subSceneToHide = subScene;
    }

    /**
     *  create subScenes
     */
    private void createSubScenes(){
        createPlaySubScene();

        createScoresSubScene();

        createHelpSubScene();

        createLvlModeSubScene();
    }

    /**
     * create help SubScene
     */
    private void createHelpSubScene(){
        helpsubScene = new PanelMenuSubScene();
        mainPane.getChildren().add(helpsubScene);
        InfoLabel message = new InfoLabel("help");
        message.setLayoutX(120);
        message.setLayoutY(20);
        String text = "Choose your ship and start the game \non the start tab.\n\n" +
                        "Control your ship with left and right \narrows or A/D keys.\n\n" +
                        "Avoid meteors and collect stars.\n\n" +
                        "Check your ranking on score tab.\n\n" +
                        "Change the difficulty on lvl \nmode tab.\n\n" +
                        "Press the exit button if you want \nto quit the game.";
        TextLabel textLabel = new TextLabel(text,17);
        textLabel.setLayoutX(40);
        textLabel.setLayoutY(70);
        helpsubScene.getPane().getChildren().addAll(message,textLabel);
    }

    /**
     * create scores SubScene
     */
    private void createScoresSubScene(){
        scoresSubScene = new PanelMenuSubScene();
        mainPane.getChildren().add(scoresSubScene);

        InfoLabel message = new InfoLabel("Scores");
        message.setLayoutX(120);
        message.setLayoutY(20);
        scoresSubScene.getPane().getChildren().add(message);
        try{
            File file = new File(fileString);
            if(!file.exists()) file.createNewFile();
            BufferedReader bfr = new BufferedReader(new FileReader(file));
            String line;
            int i =0;
            while((line=bfr.readLine())!=null){
                if(!line.trim().equals("")){
                    TextLabel score = new TextLabel(line,25);
                    score.setLayoutX(40);
                    score.setLayoutY(70+i*30);
                    scoresSubScene.getPane().getChildren().add(score);
                }
                i++;
            }
            bfr.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * create difficulty level SubScene
     */
    private void createLvlModeSubScene(){
        lvlModeSubScene = new PanelMenuSubScene();
        mainPane.getChildren().add(lvlModeSubScene);

        InfoLabel message = new InfoLabel("choose difficulty lvl");
        message.setLayoutX(120);
        message.setLayoutY(20);
        lvlModeSubScene.getPane().getChildren().add(message);
        lvlModeSubScene.getPane().getChildren().add(createDifficultyToPick());
    }


    /**
     * create checkboxes to pick difficulty level
     * @return checkboxes
     */
    private HBox createDifficultyToPick(){
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.setLayoutX(180);
        hBox.setLayoutY(100);
        diffList = new ArrayList<>();
        int[] difficulties = new int[]{1,2,3,4,5};
        for(int number:difficulties){
            DifficultyPicker difficultyPicker = new DifficultyPicker(number);
            if(number==3){
                difficultyPicker.setIsChoosen(true);
                choosenDifficulty = difficultyPicker.getNumber();
            }
            diffList.add(difficultyPicker);
            hBox.getChildren().add(difficultyPicker);
            difficultyPicker.setOnMouseClicked(e-> {
                for(DifficultyPicker diffPicker : diffList){
                    diffPicker.setIsChoosen(false);
                }
                difficultyPicker.setIsChoosen(true);
                choosenDifficulty = difficultyPicker.getNumber();
            });
        }
        return hBox;
    }

    /**
     * create play SubScene
     */
    private void createPlaySubScene(){
        playsubScene = new PanelMenuSubScene();
        mainPane.getChildren().add(playsubScene);

        InfoLabel message = new InfoLabel("Choose your ship");
        message.setLayoutX(120);
        message.setLayoutY(20);
        playsubScene.getPane().getChildren().add(message);
        playsubScene.getPane().getChildren().add(createShipsToPick());
        playsubScene.getPane().getChildren().add(createStartButton());
    }

    /**
     * create start of the game button
     * @return
     */
    private MenuButton createStartButton(){
        MenuButton startButton = new MenuButton("START","red");
        startButton.setLayoutX(365);
        startButton.setLayoutY(300);
        startButton.setOnAction(e->{
            if(choosenPlayer!=null){
                GameViewManager gameViewManager = new GameViewManager();
                gameViewManager.createGameScene(mainStage,choosenPlayer,choosenDifficulty);
            }
        });
        return startButton;
    }

    /**
     * create checkboxes to pick ships
     * @return checkboxes
     */
    private HBox createShipsToPick(){
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.setLayoutX(46);
        hBox.setLayoutY(100);
        shipList = new ArrayList<>();
        for(Player player : Player.values()){
            PlayerPicker playerPicker = new PlayerPicker(player);
            shipList.add(playerPicker);
            hBox.getChildren().add(playerPicker);
            playerPicker.setOnMouseClicked(e-> {
                for(PlayerPicker ship: shipList){
                    ship.setIsChoosen(false);
                }
                playerPicker.setIsChoosen(true);
                choosenPlayer = playerPicker.getPlayer();
            });
        }
        return hBox;
    }

    /**
     * create tabs' buttons
     */
    private void createButtons(){
        createPlayButton();
        createScoresButton();
        createHelpButton();
        createDifficultyButton();
        createExitButton();
    }

    /**
     * create play button
     */
    private void createPlayButton(){
        MenuButton button = new MenuButton("PLAY","green");
        addButton(button);
        button.setOnAction(e-> {
            showSubScene(playsubScene);
        });
    }

    /**
     * create scores button
     */
    private void createScoresButton(){
        MenuButton button = new MenuButton("SCORES","blue");
        addButton(button);
        button.setOnAction(e->{
            createScoresSubScene();
            showSubScene(scoresSubScene);
        });
    }

    /**
     * create help button
     */
    private void createHelpButton(){
        MenuButton button = new MenuButton("HELP","red");
        addButton(button);
        button.setOnAction(e-> {
            showSubScene(helpsubScene);
        });
    }

    /**
     * create lvl mode button
     */
    private void createDifficultyButton(){
        MenuButton button = new MenuButton("LVL MODE","yellow");
        addButton(button);
        button.setOnAction(e-> {
            showSubScene(lvlModeSubScene);
        });
    }

    /**
     * create exit button
     */
    private void createExitButton(){
        MenuButton button = new MenuButton("Exit","yellow");
        addButton(button);
        button.setOnAction(e-> {
            mainStage.close();
        });
    }

    /**
     * method to add buttons to mainPane
     * @param button
     */
    private void addButton(MenuButton button){
        button.setLayoutX(BUTTONS_ALIGNMENT_X);
        button.setLayoutY(BUTTONS_ALIGNMENT_Y_START+ buttons.size()*100);
        buttons.add(button);
        mainPane.getChildren().add(button);
    }

    /**
     * creates background
     */
    private void createBackground(){
        boolean bg = Math.random()>=0.5;
        int number = 1;
        if(!bg) number=2;
        String imageURL = "/view/resources/bg"+number+".jpg";
        Image backgroundImage = new Image(imageURL,1024,1024,false,true);
        BackgroundImage background =
                new BackgroundImage(backgroundImage,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        null);
        mainPane.setBackground(new Background(background));
    }

    /**
     * creates the logo of the game
     */
    private void createLabel(){
        ImageView label = new ImageView("/view/resources/label.png");
        label.setLayoutX(470);
        label.setLayoutY(20);
        label.setOnMouseEntered(e-> label.setEffect(new DropShadow()));
        label.setOnMouseExited(e -> label.setEffect(null));
        mainPane.getChildren().add(label);
    }

    public Stage getMainStage() {
        return mainStage;
    }
}
