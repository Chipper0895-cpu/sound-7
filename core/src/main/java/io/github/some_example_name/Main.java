package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import javax.sound.sampled.LineUnavailableException;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    Sound sound;
    Midi midi;
    Thread Midithread;


    @Override
    public void create() {
        try {
            sound = new Sound(440);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        try {
            midi = new Midi();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        Midithread = new Thread(midi);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        if (Gdx.input.isKeyPressed(Input.Keys.F)){
            try {
                sound.samples();
            } catch (LineUnavailableException e) {
            }
        }else{
            sound.close();
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.P))&&!Midithread.isAlive()){
            System.out.println("Loop started, press [ENTER] to escape.");
            Midithread = new Thread(midi);
            Midithread.start();
        }
        if (Midithread.isAlive()&& Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            Midithread.interrupt();
        }


    }

    @Override
    public void dispose() {

    }
}
