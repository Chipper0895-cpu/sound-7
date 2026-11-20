package io.github.some_example_name;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import javax.sound.midi.*;

import javax.sound.sampled.LineUnavailableException;
import java.util.Objects;

public class Midi implements Runnable {
    MidiDevice sel = null;
    boolean count = false;
    private int pressedKey;
    String type;
    Boolean keyPress;
    Sounds sound = new Sounds(440);
    Thread thread = new Thread(sound);

    public Midi() throws LineUnavailableException {
    }


    @Override
    public void run() {
        MidiDevice.Info[] devices = MidiSystem.getMidiDeviceInfo();
        MidiDevice.Info transmiter;
        MidiEvent midiE;
        try {
            for (MidiDevice.Info info : devices) {
                MidiDevice device = MidiSystem.getMidiDevice(info);
                boolean canR = device.getMaxTransmitters() != 0;
                device.open();

                boolean T = device.getMaxTransmitters() != 0;
                boolean R = device.getMaxReceivers() != 0;
                if (R && T) {
                    type = "IN/OUT";
                }
                if (R && !T) {
                    type = "OUT";
                }
                if (T && !R) {
                    type = "IN";
                }
                if (!T && !R) {
                    type = "unknown";
                }

                if (type.equals("IN") && ((Objects.equals(info.getDescription(), "External MIDI Port")) || (Objects.equals(info.getDescription(), "No details available")))) {
                    sel = device;
                }
                device.close();
            }
            if (sel != null) {
                Synthesizer synthesizer = MidiSystem.getSynthesizer();
                sel.open();
                synthesizer.open();
                Transmitter transmitter = sel.getTransmitter();
                if(!sound.alive){
                    thread.start();
                }
                transmitter.setReceiver(new Receiver() {
                    @Override
                    public void send(MidiMessage message, long timeStamp) {
                        ShortMessage shortMessage = (ShortMessage) message;
                        int command = shortMessage.getCommand();
                        int note = shortMessage.getData1();
                        int velocity = shortMessage.getData2();

                        if (command == ShortMessage.NOTE_ON && velocity > 0) {
                            pressedKey = note;
                            sound.setFq(27.5 * Math.pow(2, (note - 21) / 12.0));
                            sound.setAmp(velocity / 128f);
                        } else if (command == ShortMessage.NOTE_OFF || (command == ShortMessage.NOTE_ON && velocity == 0)) {
                            if (note == pressedKey) {
                                sound.setAmp(0f);
                            }
                        }
                    }

                    @Override
                    public void close() {
                    }
                });
                do {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }while (!Gdx.input.isKeyPressed(Input.Keys.ENTER));
                synthesizer.close();
                sel.close();
                //midiE = new MidiEvent();
                //MidiChannel channel = new Midi();
                //MidiMessage mg = midiE.getMessage()

            }
        } catch (MidiUnavailableException e) {
            System.err.println("MIDI Error: Device is currently busy or unavailable.");

        } catch (IllegalMonitorStateException e) {
            throw new RuntimeException(e);
        }

        if (sel == null) {
            System.out.println("no suitable device found");
        }

    }

}
