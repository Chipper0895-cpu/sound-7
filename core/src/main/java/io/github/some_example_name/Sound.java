package io.github.some_example_name;
import javax.sound.sampled.*;

public class Sound {
    private int SampleR;
    private int Fq;
    private double angle1;
    private short[] samples;
    private short value;
    private double amp;


    public Sound() throws LineUnavailableException{
        SampleR = 44100;
        AudioFormat af = new AudioFormat(SampleR, 16, 1, true, false);
        SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
        amp=0.5;
        samples = new short[441];

    }


    public void samples(int fq) throws LineUnavailableException{
        Fq = fq;
        for(int i =0; i<441; i++){
            angle1 = ((2*Math.PI*fq*i)/SampleR);
            value = (short)(amp*(Math.sin(angle1))*Short.MAX_VALUE);
            samples[i]=value;
        }
    }
}
