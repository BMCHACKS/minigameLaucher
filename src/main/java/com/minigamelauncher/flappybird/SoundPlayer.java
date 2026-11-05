package com.minigamelauncher.flappybird;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class SoundPlayer {

    private byte[] audioData;
    private AudioFormat format;

    public SoundPlayer(String filename) {
        try {
            InputStream stream = SoundPlayer.class.getResourceAsStream(filename);
            if (stream == null) {
                System.out.println("Sound file not found: " + filename);
                return;
            }
            AudioInputStream audio = AudioSystem.getAudioInputStream(
                    new BufferedInputStream(stream));
            format = audio.getFormat();

            // Read all audio bytes into memory once
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] chunk = new byte[4096];
            int bytesRead;
            while ((bytesRead = audio.read(chunk)) != -1) {
                buffer.write(chunk, 0, bytesRead);
            }
            audioData = buffer.toByteArray();
        } catch (Exception e) {
            System.out.println("Could not load sound: " + filename + " - " + e.getMessage());
        }
    }

    public void play() {
        if (audioData == null)
            return;

        // Create a fresh Clip every time so sounds never conflict
        new Thread(() -> {
            try {
                AudioInputStream stream = new AudioInputStream(
                        new ByteArrayInputStream(audioData), format, audioData.length / format.getFrameSize());
                Clip clip = AudioSystem.getClip();
                clip.open(stream);
                clip.addLineListener(e -> {
                    if (e.getType() == LineEvent.Type.STOP) {
                        clip.close(); // free resources when done
                    }
                });
                clip.start();
            } catch (Exception e) {
                System.out.println("Could not play sound: " + e.getMessage());
            }
        }).start();
    }
}