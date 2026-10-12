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
    Sounds sound;
    Midi midi;
    Thread Mthread;
    Thread Sthread;

    @Override
    public void create() {
        try {
            sound = new Sounds(440);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        try {
            midi = new Midi();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        Mthread = new Thread(midi);
        Sthread = new Thread(sound);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        if (Gdx.input.isKeyPressed(Input.Keys.F)&&!Sthread.isAlive()){
            Sthread.start();
        } else if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            sound.play();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.U)){
            sound.stop();
            sound.close();
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.P))&&!Mthread.isAlive()){
            System.out.println("Loop started, press [ENTER] to escape.");
            Mthread = new Thread(midi);
            Mthread.start();
        }
        if (Mthread.isAlive()&& Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            Mthread.interrupt();
        }

    }

    @Override
    public void dispose() {
        Sthread.interrupt();
        Mthread.interrupt();
    }
}
