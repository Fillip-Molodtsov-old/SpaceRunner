package model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;

public class InfoLabel extends Label {
    private static final String FONT_PATH = "src/model/resources/kenvector_future.ttf";
    private static final String BACKGROUND_PATH = "view/resources/buttonYellow.png";

    public InfoLabel(String text){
        setPrefWidth(380);
        setPrefHeight(40);
        setAlignment(Pos.CENTER);
        setText(text);
        setWrapText(true);
        setSelectedFont();
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(BACKGROUND_PATH,380,40,false,true),
                BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
        setBackground(new Background(backgroundImage));
    }

    /**
     * set the font
     */
    private void setSelectedFont(){
        try {
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),23));
        }catch (Exception e){
            setFont(new Font("Verdana",23));
        }
    }
}
