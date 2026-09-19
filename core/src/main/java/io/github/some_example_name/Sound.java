package io.github.some_example_name;
import javax.sound.sampled.*;

public class Sound{
    private int SampleR;
    private double Fq;
    private double angle1;
    private short[] samples;
    private short value;
    private double amp;
    private byte[] buffer;
    AudioFormat af;
    SourceDataLine sdl;

    public Sound() throws LineUnavailableException {
        SampleR = 44100;
        af = new AudioFormat(SampleR, 16, 1, true, false);
        sdl = AudioSystem.getSourceDataLine(af);
        amp=0.5;
        samples = new short[44100];
        buffer = new byte[2];

    }


    public void samples(double fq) throws LineUnavailableException {
        Fq = fq;

        for (int i = 0; i < 4410; i++) {
            angle1 = ((2 * Math.PI * fq * i) / SampleR);
            value = (short) (amp * (Math.sin(angle1)) * Short.MAX_VALUE);
            //saw wave, dodgy at high frequencies
            //angle1 = ((2*Math.PI*fq*i)/SampleR);
            //value = (short)(((angle1+Math.PI)/2-Math.floor((angle1+Math.PI)/2+1/2))*Short.MAX_VALUE*amp);
            samples[i] = value;
            sdl.open(af);
            sdl.start();
            buffer[0] = (byte) (samples[i] & 0xFF);
            buffer[1] = (byte) ((samples[i] >> 8) & 0xFF);
            sdl.write(buffer, 0, 2);
        }
    }

        public void close(){
            sdl.drain();
            sdl.stop();
            sdl.close();


    }

    public double getAmp() {
        return amp;
    }

    public void setAmp(double amp) {
        this.amp = amp;
    }
}
