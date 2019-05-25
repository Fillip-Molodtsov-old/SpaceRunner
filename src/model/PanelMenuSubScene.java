package model;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;


public class PanelMenuSubScene extends SubScene {

    private static final String FONT_PATH = "model/resources/kenvector_future.ttf";
    private static final String BACKGROUND_PATH = "model/resources/grey_panel.png";
    private boolean isHidden = true;


    public PanelMenuSubScene() {
        super(new AnchorPane(), 600, 400);
        AnchorPane root2 = (AnchorPane) this.getRoot();
        BackgroundImage image =
                new
                BackgroundImage(new Image(BACKGROUND_PATH,600,400,false,true),
                        BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
        root2.setBackground(new Background(image));
        setLayoutY(150);
        setLayoutX(1060);
    }

    /**
     * animates the subScene
     */
    public void moveSubScene(){
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.3));
        translateTransition.setNode(this);
        if(isHidden){
            translateTransition.setToX(-700);
            isHidden=false;
        }else{
            translateTransition.setToX(0);
            isHidden=true;
        }
        translateTransition.play();
    }

    public AnchorPane getPane(){
        return (AnchorPane) this.getRoot();
    }
}
