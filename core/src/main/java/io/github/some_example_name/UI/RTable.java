package io.github.some_example_name.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class RTable {
    private Table root;
    private Stage stage;
    private Skin Cskin;
    private Skin Sskin;
    private PButton pButton;
    private TButton tButton;
    private Button button;
    private TextButton textButton;

    public RTable() {
        Sskin = new Skin(Gdx.files.internal("skins/ShadeUI/uiskin.json"));
        Cskin = new Skin(Gdx.files.internal("skins/ChillUI/clean-crispy-ui.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        pButton = new PButton(Sskin, Cskin);
        tButton = new TButton(Sskin, Cskin);

        textButton = tButton.CreateBlackButton("MIDI");
        root.add(textButton).expand().pad(5).growX();
        root.row();
        button = pButton.CreateWhiteButton();
        root.add(button).expand().pad(5).growX();
    }

    public void Draw() {
        Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    public boolean IsButtonPressed() {
        if (pButton.getWhite_button() != null) {
            return pButton.isWpressed();
        } else if (pButton.getBlack_button() != null) {
            return pButton.isBpressed();
        }else
        return false;
    }

    public boolean IsWtoggled(){
        if (tButton.getWhite_button()!=null){
            return tButton.isWtoggled();
        }else
            return false;
    }
    public boolean IsBtoggled(){
        if (tButton.getBlack_button()!=null){
            return tButton.isBtoggled();
        }else
            return false;
    }
}
