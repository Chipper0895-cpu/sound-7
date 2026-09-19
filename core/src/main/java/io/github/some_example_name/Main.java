package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import jdk.jpackage.internal.Executor;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.LineUnavailableException;
import java.util.concurrent.Executors;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    Sound sound;
    Midi midi;
    Thread thread;

    @Override
    public void create() {
        try {
            sound = new Sound();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        try {
            midi = new Midi();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        thread = new Thread(midi);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        if (Gdx.input.isKeyPressed(Input.Keys.F)){
            try {
                sound.samples(440);
            } catch (LineUnavailableException e) {
            }
        }else{
            sound.close();
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.P))&&!thread.isAlive()){
            System.out.println("Loop started, press [ENTER] to escape.");
            thread = new Thread(midi);
            thread.start();
        }
        if (thread.isAlive()&& Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            thread.interrupt();
        }

    }

    @Override
    public void dispose() {

    }
}
