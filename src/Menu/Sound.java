package Menu;

public enum Sound {
    EffectSound("menu/effect_sound.jpg"),
    BackgroundMusic("menu/background_music.jpg");

    String urlSound;

    private Sound(String urlSound){
        this.urlSound = urlSound;
    }
    public String getUrlSound(){return this.urlSound;}
}
