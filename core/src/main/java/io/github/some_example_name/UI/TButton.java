package io.github.some_example_name.UI;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class TButton {
    private TextButton White_button;
    private TextButton Black_button;
    private Boolean Wtoggle;
    private Boolean Btoggle;
    private Skin Sskin;
    private Skin Cskin;

    public TButton(Skin Sskin,Skin Cskin){
        this.Sskin = Sskin;
        this.Cskin = Cskin;
        Btoggle =false;
        Wtoggle=false;
    }

    public TextButton CreateBlackButton(String string){
        Black_button = new TextButton(string,Sskin,"toggle");
        Black_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (Black_button.isPressed()){
                    Btoggle =true;
                }else
                    Btoggle = false;
            }
        });
        return Black_button;
    }

    public TextButton CreateWhiteButton(String string){
        White_button = new TextButton(string,Sskin,"toggle");
        White_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (White_button.isPressed()){
                    Wtoggle = true;
                }else
                    Btoggle = false;
            }
        });
        return White_button;
    }

    public boolean isWtoggled(){
        return Wtoggle;
    }

    public boolean isBtoggled(){
        return Btoggle;
    }

    public TextButton getWhite_button() {
        return White_button;
    }

    public void setWhite_button(TextButton white_button) {
        White_button = white_button;
    }

    public TextButton getBlack_button() {
        return Black_button;
    }

    public void setBlack_button(TextButton black_button) {
        Black_button = black_button;
    }
}
