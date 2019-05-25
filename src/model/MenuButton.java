package model;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.io.FileInputStream;

public class MenuButton extends Button {
    private static final String FONT_PATH = "src/model/resources/kenvector_future.ttf";
    private String color = "red";
    private  String BUTTON_FREE_STYLE ;

    private  String BUTTON_PRESSED_STYLE;


    public MenuButton(String text,String color){
        if(color.equals("blue")||color.equals("green")||color.equals("yellow")) this.color = color;
        String freeUrl = "/model/resources/"+color+"_button01.png";
        String pressedURL = "/model/resources/"+color+"_button02.png";
        BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url("+freeUrl+")";
        BUTTON_PRESSED_STYLE =
                "-fx-background-color: transparent; -fx-background-image: url("+pressedURL+")";
        setText(text);
        setButtonFont();
        setPrefWidth(190);
        setPrefHeight(49);
        setStyle(BUTTON_FREE_STYLE);
        initializeButtonListeners();
    }

    /**
     * set buttons font
     */
    private void  setButtonFont(){
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH),23));
        }catch(Exception e){
            setFont(Font.font("Verdana",23));
        }
    }

    /**
     * set button pressedStyle
     */
    private void setButtonPressedStyle(){
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(45);
        setLayoutY(getLayoutY()+4);
    }

    /**
     * set button released style
     */
    private void setButtonFreeStyle(){
        setStyle(BUTTON_FREE_STYLE);
        setPrefHeight(49);
        setLayoutY(getLayoutY()-4);
    }

    /**
     * attach listeners to button events
     */
    private void initializeButtonListeners(){
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    setButtonPressedStyle();
                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    setButtonFreeStyle();
                }
            }
        });
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(new DropShadow());
            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(null);
            }
        });
    }
}
