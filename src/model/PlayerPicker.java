package model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;



public class PlayerPicker extends VBox {
    private ImageView circleImage;
    private ImageView playerImage;

    private static final String CIRCLE_CHOOSEN = "view/resources/shipChooser/red_boxTick.png";
    private static final String CIRCLE_NOT_CHOOSEN = "view/resources/shipChooser/grey_boxTick.png";

    private Player player;

    private boolean isCirclechoosen;

    public PlayerPicker(Player player){
        this.player = player;
        circleImage = new ImageView(CIRCLE_NOT_CHOOSEN);
        playerImage = new ImageView(player.getUrl());
        isCirclechoosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().add(circleImage);
        this.getChildren().add(playerImage);
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isCirclechoosen() {
        return isCirclechoosen;
    }

    /**
     * set if the option is selected
     * @param isChosen isSelected
     */
    public void setIsChoosen(boolean isChosen){
        this.isCirclechoosen = isChosen;
        String imageToSet = this.isCirclechoosen ? CIRCLE_CHOOSEN : CIRCLE_NOT_CHOOSEN;
        circleImage.setImage(new Image(imageToSet));
    }

}
