package io.github.some_example_name.UI;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class PButton {
    private Button White_button;
    private Button Black_button;
    private Skin Sskin;
    private Skin Cskin;
    private double fq;

    public PButton(Skin Sskin,Skin Cskin){
        this.Sskin = Sskin;
        this.Cskin = Cskin;
    }

    public Button CreateBlackButton(){
        Black_button = new Button(Sskin);
        Black_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
            }
        });
        return Black_button ;
    }

    public Button CreateWhiteButton(){
        White_button = new Button(Cskin);
        White_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
            }
        });
        return White_button;
    }

    public boolean isWpressed(){
        return White_button.isPressed();
    }

    public boolean isBpressed(){
        return Black_button.isPressed();
    }

    public Button getWhite_button() {
        return White_button;
    }

    public void setWhite_button(Button white_button) {
        White_button = white_button;
    }

    public Button getBlack_button() {
        return Black_button;
    }

    public void setBlack_button(Button black_button) {
        Black_button = black_button;
    }
}
