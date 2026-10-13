package io.github.some_example_name;
import javax.sound.sampled.*;

public class Sounds implements Runnable{
    private int SampleR;
    private volatile double fq;
    private double angle1;
    private short[] samples;
    private short value;
    private double amp;
    private byte[] buffer;
    AudioFormat af;
    SourceDataLine sdl;
    volatile boolean audio;
    int index;
    volatile boolean alive;
    boolean open = false;


    public Sounds(double Fq) throws LineUnavailableException {
        SampleR = 44100;
        af = new AudioFormat(SampleR, 16, 1, true, false);
        sdl = AudioSystem.getSourceDataLine(af);
        amp=0.5;
        samples = new short[44100];
        buffer = new byte[882];
        fq=Fq;
        index=0;
        angle1=0;


    }

    @Override
    public void run() {
        alive = true;
        audio = true;
        try {
            open();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        while (Thread.currentThread().isAlive()) {
            if(!open&&audio){
                try {
                    open();
                } catch (LineUnavailableException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                while (audio) {
                    angle1 += 2 * Math.PI * fq / SampleR;
                    value = (short) (amp * (Math.sin(angle1)) * Short.MAX_VALUE);
                    //saw wave, dodgy at high frequencies
                    //angle1 = ((2*Math.PI*fq*i)/SampleR);
                    //value = (short)(((angle1+Math.PI)/2-Math.floor((angle1+Math.PI)/2+1/2))*Short.MAX_VALUE*amp);
                    buffer[index++] = (byte) (value & 0xFF);
                    buffer[index++] = (byte) ((value >> 8) & 0xFF);
                    if (index == buffer.length) {
                        sdl.write(buffer, 0, buffer.length);
                        index = 0;
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void close(){
        sdl.drain();
        sdl.stop();
        sdl.close();
        open = false;
    }
    public void open() throws LineUnavailableException {
        sdl.open(af);
        sdl.start();
        open = true;
    }

    public void setFq(double fq){
        this.fq = fq;
    }

    public double getAmp() {
        return amp;
    }

    public void setAmp(double amp) {
        this.amp = amp;
    }

    public void stop(){
        audio = false;
    }
    public void play(){
        audio = true;
    }

    public double getFq() {
        return fq;
    }
}
