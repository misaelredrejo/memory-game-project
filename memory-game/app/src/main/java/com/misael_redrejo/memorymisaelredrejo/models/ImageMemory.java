package com.misael_redrejo.memorymisaelredrejo.models;

public class ImageMemory {
    private int image;
    private boolean isFlipped;
    private boolean isGuessed;

    public ImageMemory(int image) {
        this.image = image;
        this.isFlipped = false;
        this.isGuessed = false;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }

    public boolean isGuessed() {
        return isGuessed;
    }

    public void setGuessed(boolean guessed) {
        isGuessed = guessed;
    }
}
