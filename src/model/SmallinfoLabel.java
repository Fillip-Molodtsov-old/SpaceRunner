package model;

import javafx.geometry.Insets;
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

public class SmallinfoLabel extends Label {

    private static final String FONT_PATH = "src/model/resources/kenvector_future.ttf";
    private static final String BACKGROUND_PATH = "view/resources/shipChooser/buttonBlue.png";

    public SmallinfoLabel(String text){
        setPrefWidth(190);
        setPrefHeight(40);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10,10,10,10));
        setText(text);
        setWrapText(true);
        setSelectedFont();
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(BACKGROUND_PATH,190,40,false,true),
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
