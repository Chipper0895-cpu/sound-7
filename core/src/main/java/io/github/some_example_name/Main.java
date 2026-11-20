package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
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
    Thread Sthread1;
    Thread Sthread2;
    int frequency;
    int tempnum;

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
        Sthread1 = new Thread(sound);
    }

    @Override
    public void render() {
        if (Gdx.input.isKeyPressed(Input.Keys.F)&&!Sthread1.isAlive()){
            Sthread1.start();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            sound.play();
        }else{
            sound.stop();
            sound.drain();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.U)){
            sound.stop();
            sound.drain();
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
        if ((Gdx.input.isKeyJustPressed(Input.Keys.PAGE_UP))&&sound.getFq()<4200){
            tempnum = (int)Math.round(((12*Math.log(sound.getFq()/27.5))/Math.log(2)));
            sound.setFq(((27.5 * (Math.pow(2, ((tempnum+1) / 12.0))))));

        }
        if ((Gdx.input.isKeyJustPressed(Input.Keys.PAGE_DOWN)&&sound.getFq()>20)){
            tempnum = (int)Math.round(((12*Math.log(sound.getFq()/27.5))/Math.log(2)));
            sound.setFq(((27.5 * (Math.pow(2, ((tempnum-1) / 12.0))))));
        }
    }

    @Override
    public void dispose() {
    }
}
