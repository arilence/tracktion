package cm.smith.games.tracktion.entities;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import cm.smith.games.tracktion.MainGame;

/**
 * Created by anthony on 2016-09-18.
 */
public class Vehicle extends Entity {

    public Texture texture;
    public TextureRegion textureRegion;
    private boolean accelerate;

    // State values
    public float positionX;         // Where the car is on the X axis
    public float positionY;         // Where the car is on the Y axis
    public float velocityX;         // The car's speed on the X axis
    public float velocityY;         // The car's speed on the Y axis
    public float heading;           // The rotation of the car, in radians
    public float angularVelocity;   // Speed in which the car is spinning, in radians

    // Configurable values
    public float drag;              // How fast the car slows down
    public float angularDrag;       // How fast the car stops spinning
    public float power;             // How fast the car can accelerate
    public float turnSpeed;         // How fast the car can turn

    public Vehicle(MainGame game) {
        texture = game.assetManager.get("vehicle.png", Texture.class);
        textureRegion = new TextureRegion(texture);
        accelerate = false;

        positionX = 100;
        positionY = 100;
        drag = 0.9f;
        angularDrag = 0.9f;
        power = 20;
        turnSpeed = 0.5f;
    }

    public void update(float delta) {
        positionX += velocityX;
        positionY += velocityY;
        velocityX *= drag;
        velocityY *= drag;
        heading += angularVelocity;
        angularVelocity *= angularDrag;

        if (accelerate) {
            velocityX += Math.sin(heading) * power * delta;
            velocityY += Math.cos(heading) * power * delta;
            accelerate = false;
        } else {

        }
    }

    public void render(SpriteBatch batch) {
        //batch.draw(texture, (float)carPositionX, (float)carPositionY);

        float originX = texture.getWidth() / 2;
        float originY = texture.getHeight() / 2;
        float width = texture.getWidth();
        float height = texture.getHeight();
        float scaleX = 1;
        float scaleY = 1;
        batch.draw(textureRegion.getTexture(), positionX, positionY, originX, originY, width, height, scaleX, scaleY, heading, textureRegion.getRegionX(), textureRegion.getRegionY(), textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), false, false);
    }

    public void rightInput(float delta) {
        if (angularVelocity < 10) {
            angularVelocity += turnSpeed;
        }
    }

    public void leftInput(float delta) {
        if (angularVelocity > -10) {
            angularVelocity -= turnSpeed;
        }
    }

    public void accelerator(float delta) {
        accelerate = true;
    }

}
