package io.github.some_example_name;
import javax.sound.sampled.*;

public class Sounds implements Runnable{
    private int SampleR;
    private volatile double fq;
    private double angle1;
    private short value;
    private double sum;
    private volatile double targetAmp;
    private double currentAmp;
    private final double AmpStep;
    private byte[] buffer;
    private AudioFormat af;
    private SourceDataLine sdl;
    private volatile boolean audio;
    private int index;
    volatile boolean alive;
    private boolean open = false;
    private volatile String wave;




    public Sounds(double Fq) throws LineUnavailableException {
        SampleR = 44100;
        af = new AudioFormat(SampleR, 16, 1, true, false);
        sdl = AudioSystem.getSourceDataLine(af);
        targetAmp = 0.0f;
        currentAmp = 0.0f;
        AmpStep = 0.0045f;
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
            if (currentAmp < targetAmp) {
                currentAmp = Math.min(targetAmp, currentAmp + AmpStep);
            } else if (currentAmp > targetAmp) {
                currentAmp = Math.max(targetAmp, currentAmp - AmpStep);
            }
            wave="saw";
            switch (wave) {

                case ("saw"):
                    try {
                        while (audio) {
                            angle1 += 2 * Math.PI * fq / SampleR;
                            sum = 0;

                            for (int i = 1; i < 300; i++) {
                                sum += ((Math.pow(-1f, i)) / i) * Math.sin(i * angle1);

                            }
                            value = (short) (((2 * targetAmp) / Math.PI) * sum * Short.MAX_VALUE);
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
                case ("sine"):
                        try {
                            while (audio) {
                                angle1 += 2 * Math.PI * fq / SampleR;
                                value = (short) (targetAmp * Math.sin(angle1) * Short.MAX_VALUE);
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
                case ("square"):
                    try {
                        while (audio) {
                            angle1 += 2 * Math.PI * fq / SampleR;
                            value = (short) ((targetAmp * Math.signum(Math.sin(angle1)) * Short.MAX_VALUE));
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
                case ("triangle"):
                    try {
                        while (audio) {
                            angle1 +=  fq  / SampleR;
                            value = (short) (targetAmp * (Math.abs(2*(angle1-Math.floor(angle1+1/2f)))-1/2f) * Short.MAX_VALUE);
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
    }

    public void close(){
        sdl.drain();
        sdl.stop();
        sdl.close();
        open = false;
    }
    public void open() throws LineUnavailableException {
        sdl.open(af,2048);
        sdl.start();
        open = true;
    }

    public void setFq(double fq){
        this.fq = fq;
    }


    public double getTargetAmp() {
        return targetAmp;
    }

    public void drain(){sdl.drain();}

    public void setAmp(double amp) {
        this.targetAmp = amp;
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
