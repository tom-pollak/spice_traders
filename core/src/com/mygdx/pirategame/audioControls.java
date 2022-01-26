package com.mygdx.pirategame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class audioControls {

    //Set keys that are stored in the file
    private static final String MUSIC_VOLUME = "volume";
    private static final String MUSIC_ENABLED = "music.enabled";
    private static final String SOUND_ENABLED = "sound.enabled";
    private static final String EFFECT_VOL = "sound";
    private static final String PAGE = "audio";

    //Allows interaction with the file
    protected Preferences optionsObtains() {
        return Gdx.app.getPreferences(PAGE);
    }

    //See if effects are enabled
    public boolean isEffectsEnabled() {
        return optionsObtains().getBoolean(SOUND_ENABLED, true);
    }

    //Set effects on true or false
    public void setEffectsEnabled(boolean EffectsOn) {
        optionsObtains().putBoolean(SOUND_ENABLED, EffectsOn);
        optionsObtains().flush();
    }

    //See if music is enabled
    public boolean isMusicEnabled() {
        return optionsObtains().getBoolean(MUSIC_ENABLED, true);
    }

    //Set music on true or false
    public void setMusicEnabled(boolean musicOn) {
        optionsObtains().putBoolean(MUSIC_ENABLED, musicOn);
        optionsObtains().flush();
    }

    //Get the volume of music
    public float getMusicVolume() {
        return optionsObtains().getFloat(MUSIC_VOLUME, 0.5f);
    }

    //Set the volume of music
    public void setMusicVolume(float volume) {
        optionsObtains().putFloat(MUSIC_VOLUME, volume);
        optionsObtains().flush();
    }

    //Get the volume of effects
    public float getEffectsVolume() {
        return optionsObtains().getFloat(EFFECT_VOL, 0.5f);
    }

    //Set the volume of effects
    public void setEffectsVolume(float volume) {
        optionsObtains().putFloat(EFFECT_VOL, volume);
        optionsObtains().flush();
    }
}
