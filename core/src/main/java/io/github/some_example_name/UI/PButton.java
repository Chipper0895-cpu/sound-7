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

    public PButton(Skin Sskin,Skin Cskin){
        this.Sskin = Sskin;
        this.Cskin = Cskin;
    }

    public Button CreateBlackButton(){
        Black_button = new Button(Sskin);
        return Black_button ;
    }

    public Button CreateWhiteButton(){
        White_button = new Button(Cskin);
        return White_button;
    }

}
