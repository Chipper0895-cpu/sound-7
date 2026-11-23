package io.github.some_example_name.UI;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class TButton {
    private TextButton White_button;
    private TextButton Black_button;
    private Skin Sskin;
    private Skin Cskin;

    public TButton(Skin Sskin,Skin Cskin){
        this.Sskin = Sskin;
        this.Cskin = Cskin;
    }

    public TextButton CreateBlackButton(String string){
        Black_button = new TextButton(string,Sskin,"toggle");
        return Black_button;
    }

    public TextButton CreateWhiteButton(String string){
        White_button = new TextButton(string,Sskin,"toggle");
        return White_button;
    }

}
