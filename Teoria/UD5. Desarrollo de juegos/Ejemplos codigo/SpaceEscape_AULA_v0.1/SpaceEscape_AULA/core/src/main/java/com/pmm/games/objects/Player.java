package com.pmm.games.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private float x, y;
    private float width, height;
    private float speed;

    private Texture texture;

    public Player(Texture texture, float x, float y, float width, float height, float speed) {
        this.texture= texture;
        this.x= x;
        this.y= y;
        this.width= width;
        this.height= height;
        this.speed= speed;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    public void moveLeft() {
        x-= speed;
    }

    public void movRight() {
        x+= speed;
    }

    public void moveUp() {
        y+= speed;
    }

    public void moveDown() {
        y-= speed;
    }

    public void dispose(){
        texture.dispose();
    }

}
