package view;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Player;
import model.SmallinfoLabel;

import java.util.Random;

public class GameViewManager {

    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private Stage menuStage;

    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private int angle;
    private ImageView shipImage;
    private AnimationTimer gameTimer;
    private GridPane gridPane1;
    private GridPane gridPane2;
    private ImageView[] meteors = new ImageView[6];
    private ImageView star;
    private Player ship;
    private SmallinfoLabel pointLabel;
    private ImageView[] playerLives;
    private int playerLivesInt;
    private int points;
    private int difficulty;
    Random meteorPosition;

    private static final String starURL = "view/resources/star_gold.png";
    private static final double WIDTH=600;
    private static final double HEIGHT=600;
    private static double angleChanger = 6;
    private static double Xchanger = 4;
    private static double backgroundSpeed = 4;
    private static double meteorSpeed = 5;
    private static double meteorSpeedAngle = 3;
    private static final double paneLayoutDiff = 1000;

    private static final double METEOR_RADIUS = 20;
    private static final double STAR_RADIUS = 12;
    private static final double SHIP_RADIUS = 27;

    public  GameViewManager(){
        meteorPosition = new Random();
        initializeStage();
        createKeyListeners();
    }

    /**
     * create keyListeners
     */
    private void createKeyListeners(){
        gameScene.setOnKeyPressed(e->{
            if(e.getCode()== KeyCode.LEFT || e.getCode()== KeyCode.A){
                isLeftKeyPressed = true;
            }else if(e.getCode() == KeyCode.RIGHT || e.getCode()== KeyCode.D){
                isRightKeyPressed =true;
            }
        });

        gameScene.setOnKeyReleased(e->{
            if(e.getCode()== KeyCode.LEFT || e.getCode()== KeyCode.A){
                isLeftKeyPressed = false;
            }else if(e.getCode() == KeyCode.RIGHT || e.getCode()== KeyCode.D){
                isRightKeyPressed = false;
            }
        });
    }

    /**
     * initializes the stage
     */
    private void initializeStage(){
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, WIDTH, HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
        gameStage.setResizable(false);
    }

    /**
     * create the star
     */
    private void createStar(){
        star = new ImageView(starURL);
        setNewMeteorPosition(star);
        gamePane.getChildren().add(star);
    }

    /**
     * create the info bar on the right upper corner
     */
    private void createStatisticsUI(){
        pointLabel = new SmallinfoLabel("POINTS : 00");
        pointLabel.setLayoutY(30);
        pointLabel.setLayoutX(400);
        gamePane.getChildren().add(pointLabel);
        playerLives = new ImageView[playerLivesInt];
        for (int i =0;i< playerLives.length;i++){
            ImageView life = new ImageView(ship.getUrl_player_life());
            life.setLayoutY(90);
            life.setLayoutX(400+i*70);
            playerLives[i] = life;
            gamePane.getChildren().add(playerLives[i]);
        }
    }

    /**
     * creates meteors
     */
    private void createMeteors(){
        for(int i =0;i< meteors.length;i++){
            ImageView meteor = new ImageView(getMeteorImage());
            meteors[i] = meteor;
            setNewMeteorPosition(meteor);
            gamePane.getChildren().add(meteor);
        }
    }

    /**
     * randomly set position of the object
     * @param meteor the object to locate
     */
    private void setNewMeteorPosition(ImageView meteor){
        meteor.setLayoutX(meteorPosition.nextInt(360)+20);
        meteor.setLayoutY(-meteorPosition.nextInt(1000));
    }

    /**
     * randomly pick the image of the meteor
     * @return
     */
    private String getMeteorImage(){
        int number =(int) (Math.random()*4+1);
        return "view/resources/shipChooser/meteor"+number+".png";
    }

    /**
     * creates background
     */
    private void createBackground(){
        gridPane1 = new GridPane();
        gridPane2 = new GridPane();
        String backgroundImageString = getBackgroundImage();
        for(int i =0;i<12;i++){
            ImageView backgroundImage1 = new ImageView(backgroundImageString);
            ImageView backgroundImage2 = new ImageView(backgroundImageString);
            GridPane.setConstraints(backgroundImage1,i%3,i/3);
            GridPane.setConstraints(backgroundImage2,i%3,i/3);
            gridPane1.getChildren().add(backgroundImage1);
            gridPane2.getChildren().add(backgroundImage2);
        }
        gridPane2.setLayoutY(-paneLayoutDiff);
        gamePane.getChildren().addAll(gridPane1,gridPane2);
    }

    /**
     * randomly picks a background image
     * @return the image
     */
    private String getBackgroundImage(){
        int number =(int) (Math.random()*3+1);
        return "view/resources/game_bg"+number+".png";
    }

    /**
     * creates the game scene
     * @param earlierStage the menuStage
     * @param ship the picked ship
     * @param difficulty picked difficulty
     */
    public void createGameScene(Stage earlierStage, Player ship,int difficulty){
        this.menuStage = earlierStage;
        this.menuStage.hide();
        this.ship = ship;
        this.difficulty = difficulty;
        setDifficulty(difficulty);
        createBackground();
        createStar();
        createMeteors();
        createStatisticsUI();
        createShip(ship);
        createGameLoop();
        gameStage.show();
    }


    /**
     * change params of the difficulty
     * @param difficulty chosen difficulty
     */
    private void setDifficulty(int difficulty){
        playerLivesInt = 3;
        switch (difficulty){
            case 1:
                backgroundSpeed = 1;
                meteorSpeed = 2.5;
                meteorSpeedAngle = 1.5;
                break;
            case 2:
                backgroundSpeed = 2;
                meteorSpeed = 3.5;
                meteorSpeedAngle = 2;
                break;
            case 4:
                playerLivesInt = 2;
                Xchanger = 5;
                backgroundSpeed = 6;
                meteorSpeed = 7;
                meteorSpeedAngle = 5;
                break;
            case 5:
                playerLivesInt = 2;
                Xchanger = 8;
                backgroundSpeed = 10;
                meteorSpeed = 9;
                meteorSpeedAngle = 7;
                break;
        }
    }

    /**
     * creates and locate the shipImage
     * @param ship
     */
    private void createShip(Player ship){
        shipImage = new ImageView(new Image(ship.getUrl()));
        shipImage.setLayoutX(WIDTH/2.5);
        shipImage.setLayoutY(HEIGHT-90);
        gamePane.getChildren().add(shipImage);
    }

    /**
     * gameTimer for animation
     */
    private void createGameLoop(){
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveBackground();
                checkifElementsCollide();
                moveMeteorsAndStars();
                moveShip();
            }
        };
        gameTimer.start();
    }

    /**
     * collide method
     */
    private void checkifElementsCollide(){
        if(SHIP_RADIUS+STAR_RADIUS >
                calculateDistanceBetweenCoordinates(shipImage.getLayoutX()+49,
                                                    star.getLayoutX()+15,
                                                        shipImage.getLayoutY()+39,
                                                    star.getLayoutY()+15)){
            setNewMeteorPosition(star);
            addPoint();
        }
        for(int i =0;i<meteors.length;i++){
            if(SHIP_RADIUS+METEOR_RADIUS >
                    calculateDistanceBetweenCoordinates(shipImage.getLayoutX()+49,
                            meteors[i].getLayoutX()+20,
                            shipImage.getLayoutY()+39,
                            meteors[i].getLayoutY()+20)){
                removeLife();
                setNewMeteorPosition(meteors[i]);
            }
        }
    }

    /**
     * adding point to the score of the player
     */
    private void addPoint(){
        points++;
        String stringPointLabel="POINTS : ";
        if(points<10){
            stringPointLabel = stringPointLabel+"0";
        }
        stringPointLabel = stringPointLabel+points;
        pointLabel.setText(stringPointLabel);
    }

    /**
     * remove life of the player
     */
    private void removeLife(){
        gamePane.getChildren().remove(playerLives[playerLivesInt-1]);
        playerLivesInt--;
        if(playerLivesInt<=0){
            gameStage.close();
            gameTimer.stop();
            SaveScoreViewManager saveScoreViewManager = new SaveScoreViewManager();
            saveScoreViewManager.createScoreScene(menuStage,points,getBackgroundImage(),difficulty);
        }
    }

    /**
     * calculate distance between objects' centers
     * @param x of object1
     * @param x1 of object2
     * @param y of object1
     * @param y1 of object2
     * @return distance
     */
    private double calculateDistanceBetweenCoordinates(double x, double x1,double y, double y1){
        return Math.sqrt(Math.pow(x-x1,2)+Math.pow(y-y1,2));
    }

    /**
     * frame of meteor/star move
     */
    private void moveMeteorsAndStars(){
        star.setLayoutY(star.getLayoutY()+meteorSpeed);
        star.setRotate(star.getRotate()+meteorSpeedAngle);
        if(star.getLayoutY()>700){
            setNewMeteorPosition(star);
        }
        for (int i=0;i<meteors.length;i++){
            meteors[i].setLayoutY(meteors[i].getLayoutY()+meteorSpeed);
            meteors[i].setRotate(meteors[i].getRotate()+meteorSpeedAngle);
            if(meteors[i].getLayoutY()>700){
                setNewMeteorPosition(meteors[i]);
            }
        }
    }

    /**
     * frame of ship's moving
     */
    private void moveShip(){
        if(isRightKeyPressed && !isLeftKeyPressed){
            if(angle<30){
                angle +=angleChanger;
            }
            shipImage.setRotate(angle);
            if(shipImage.getLayoutX()<522){
                shipImage.setLayoutX(shipImage.getLayoutX()+Xchanger);
            }
        }else if(isLeftKeyPressed && !isRightKeyPressed){
            if(angle>-30){
                angle -=angleChanger;
            }
            shipImage.setRotate(angle);
            if(shipImage.getLayoutX()>-20){
                shipImage.setLayoutX(shipImage.getLayoutX()-Xchanger);
            }
        }else{
            if(angle<0){
                angle += angleChanger;
            }else if(angle >0){
                angle -= angleChanger;
            }
            shipImage.setRotate(angle);
        }
    }

    /**
     * frame of the animation of the background
     */
    private void moveBackground(){
        gridPane1.setLayoutY(gridPane1.getLayoutY()+backgroundSpeed);
        gridPane2.setLayoutY(gridPane2.getLayoutY()+backgroundSpeed);
        if(gridPane1.getLayoutY()>paneLayoutDiff){
            gridPane1.setLayoutY(-paneLayoutDiff);
        }
        if(gridPane2.getLayoutY()>paneLayoutDiff){
            gridPane2.setLayoutY(-paneLayoutDiff);
        }
    }
}
