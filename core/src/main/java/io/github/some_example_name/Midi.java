package io.github.some_example_name;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import javax.sound.midi.*;

import javax.sound.midi.*;
import javax.sound.sampled.LineUnavailableException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class Midi implements Runnable {
    MidiDevice sel = null;
    boolean count = false;
    private int pressedKey;
    String type;
    Boolean keyPress;
    Sound sound = new Sound();

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
                    //this code is heavily influenced by the wide wonders of the interwebs
                    Transmitter transmitter = sel.getTransmitter();
                    transmitter.setReceiver(new Receiver() {
                        @Override
                        public void send(MidiMessage message, long timeStamp) {
                            ShortMessage shortMessage = (ShortMessage) message;
                            if (shortMessage.getCommand()==ShortMessage.NOTE_ON){
                                keyPress=true;
                            }
                            if (shortMessage.getCommand()!=128){
                                    try {
                                        sound.samples((27.5 * (Math.pow(2, ((shortMessage.getData1() - 21) / 12.0)))));
                                        shortMessage.getChannel();
                                    } catch (LineUnavailableException e) {
                                        throw new RuntimeException(e);
                                    }
                            }
                            sound.close();
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
