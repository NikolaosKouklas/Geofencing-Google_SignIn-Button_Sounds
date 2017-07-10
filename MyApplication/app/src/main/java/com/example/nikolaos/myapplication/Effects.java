package com.example.nikolaos.myapplication;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

// http://www.vogella.com/tutorials/AndroidMedia/article.html  --> sound Effects
// https://www.hrupin.com/2012/08/how-to-get-sound-effect-when-button-clicked-in-android-or-playing-with-soundpool
public class Effects {

    private static final String TAG = Effects.class.toString();
    private static final Effects INSTANCE = new Effects();
    boolean loaded = false;

    // Sound ID can't be 0 (zero)
    public static final int METAL_HIT = 1;
    public static final int THIP = 2;
    public static final int CANCEL_BIT = 3;
    public static final int CONFIRM_BIT = 4;
    public static final int DRAIN_MAGIC = 5;
    public static final int FART = 6;
    public static final int FLAME_MAGIC = 7;
    public static final int KIRBY_STYLE_LASER = 8;
    public static final int POWER_UP = 9;
    public static final int ROBOT_NOISE = 10;
    public static final int SELECT_BIT = 11;
    public static final int SMALL_EXPLOSION_BIT = 12;

    private Effects() {

    }

    public static Effects getInstance() {
        return INSTANCE;
    }

    private SoundPool soundPool;
    private HashMap<Integer, Integer> soundPoolMap;
    int priority = 1;
    int no_loop = 0;
    int num_of_loaded = 0;
    private float volume;
    float normal_playback_rate = 1f;

    private Context context;

    public void init(Context context) {

        this.context = context;

        // Load the sound
        soundPool = new SoundPool(12, AudioManager.STREAM_MUSIC, 100);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                num_of_loaded = num_of_loaded + 1;
                if (num_of_loaded == 12) {
                    loaded = true;
                }
            }
        });

        soundPoolMap = new HashMap<Integer, Integer>();
        soundPoolMap.put(METAL_HIT, soundPool.load(context, R.raw.metalhit, 1));
        soundPoolMap.put(THIP, soundPool.load(context, R.raw.thip, 1));
        soundPoolMap.put(CANCEL_BIT, soundPool.load(context, R.raw.cancelbit, 1));
        soundPoolMap.put(CONFIRM_BIT, soundPool.load(context, R.raw.confirmbit, 1));
        soundPoolMap.put(DRAIN_MAGIC, soundPool.load(context, R.raw.drainmagic, 1));
        soundPoolMap.put(FART, soundPool.load(context, R.raw.fart, 1));
        soundPoolMap.put(FLAME_MAGIC, soundPool.load(context, R.raw.flamemagic, 1));
        soundPoolMap.put(KIRBY_STYLE_LASER, soundPool.load(context, R.raw.kirbystylelaser, 1));
        soundPoolMap.put(POWER_UP, soundPool.load(context, R.raw.powerup, 1));
        soundPoolMap.put(ROBOT_NOISE, soundPool.load(context, R.raw.robotnoise, 1));
        soundPoolMap.put(SELECT_BIT, soundPool.load(context, R.raw.selectbit, 1));
        soundPoolMap.put(SMALL_EXPLOSION_BIT, soundPool.load(context, R.raw.smallexplosionbit, 1));

        // Getting the user sound settings
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume = actualVolume / maxVolume;
    }

    public void playSound(int soundId) {

        // Is the sound loaded already?
        if (loaded) {
            soundPool.play(soundPoolMap.get(soundId), volume, volume, priority, no_loop, normal_playback_rate);
        }
    }
}
