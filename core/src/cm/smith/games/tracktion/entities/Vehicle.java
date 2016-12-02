package cm.smith.games.tracktion.entities;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.compression.lzma.Base;

import java.util.ArrayList;
import java.util.List;

import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.screens.BaseScreen;

/**
 * Created by anthony on 2016-09-18.
 */
public class Vehicle extends Entity {

    public Texture texture;
    public TextureRegion textureRegion;
    public Sprite vehicleSprite;

    public Body body;
    float width, length, angle, maxSteerAngle, minSteerAngle, maxSpeed, power;
    float wheelAngle;
    private int steer, accelerate;
    public List<Wheel> wheels;
    private boolean isAccelerating;

    public static final int STEER_NONE=0;
    public static final int STEER_LEFT=1;
    public static final int STEER_RIGHT=2;
    public static final int STEER_HARD_LEFT=3;
    public static final int STEER_HARD_RIGHT=4;

    public static final int ACC_NONE=0;
    public static final int ACC_ACCELERATE=1;
    public static final int ACC_BRAKE=2;

    public Vehicle(MainGame game, World world, float width, float length, Vector2 position,
                   float angle, float power, float minSteerAngle, float maxSteerAngle, float maxSpeed) {
        texture = game.assetManager.get("vehicle.png", Texture.class);
        textureRegion = new TextureRegion(texture);
        vehicleSprite = new Sprite(textureRegion);

        this.isAccelerating = false;

        this.steer = STEER_NONE;
        this.accelerate = ACC_NONE;

        this.width = width;
        this.length = length;
        this.angle = angle;
        this.maxSteerAngle = maxSteerAngle;
        this.minSteerAngle = minSteerAngle;
        this.maxSpeed = maxSpeed;
        this.power = power;
        this.wheelAngle = 0;

        //init body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);
        bodyDef.angle = angle;
        this.body = world.createBody(bodyDef);

        //init shape
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.4f; //friction when rubbing against other shapes
        fixtureDef.restitution  = 0.2f; //amount of force feedback when hitting something. >0 makes the car bounce off, it's fun!
        PolygonShape carShape = new PolygonShape();
        carShape.setAsBox(this.width / 2, this.length / 2);
        fixtureDef.shape = carShape;
        this.body.createFixture(fixtureDef);

        //initialize wheels
        this.wheels = new ArrayList<Wheel>();
        this.wheels.add(new Wheel(world, this, -0.5f, -0.6f, 0.2f, 0.4f, true,  true)); //top left
        this.wheels.add(new Wheel(world, this, 0.5f, -0.6f, 0.2f, 0.4f, true,  true)); //top right
        this.wheels.add(new Wheel(world, this, -0.5f, 0.6f, 0.2f, 0.4f, false,  false)); //back left
        this.wheels.add(new Wheel(world, this, 0.5f, 0.6f, 0.2f, 0.4f, false,  false)); //back right
    }

    public void update (float delta){

        //1. KILL SIDEWAYS VELOCITY

        for(Wheel wheel:wheels){
            wheel.killSidewaysVelocity();
        }

        //2. SET WHEEL ANGLE

        //calculate the change in wheel's angle for this update
        float incr=(this.maxSteerAngle) * delta * 5;

        if(this.steer== STEER_LEFT){
            this.wheelAngle=Math.min(Math.max(this.wheelAngle, 0)+incr, this.minSteerAngle); //increment angle without going over max steer
        }
        else if(this.steer== STEER_RIGHT){
            this.wheelAngle=Math.max(Math.min(this.wheelAngle, 0)-incr, -this.minSteerAngle); //decrement angle without going over max steer
        }
        else if(this.steer== STEER_HARD_LEFT){
            this.wheelAngle=Math.max(Math.min(this.wheelAngle, 0)+incr, this.maxSteerAngle);
        }
        else if(this.steer== STEER_HARD_RIGHT){
            this.wheelAngle=Math.max(Math.min(this.wheelAngle, 0)-incr, -this.maxSteerAngle);
        }
        else {
            this.wheelAngle=0;
        }

        //update revolving wheels
        for(Wheel wheel:this.getRevolvingWheels()) {
            wheel.setAngle(this.wheelAngle);
        }

        //3. APPLY FORCE TO WHEELS
        Vector2 baseVector; //vector pointing in the direction force will be applied to a wheel ; relative to the wheel.

        //if accelerator is pressed down and speed limit has not been reached, go forwards
        if((this.accelerate== ACC_ACCELERATE) && (this.getSpeedKMH() < this.maxSpeed)){
            baseVector= new Vector2(0, -1);
        }
        else if(this.accelerate== ACC_BRAKE){
            //braking, but still moving forwards - increased force
            if(this.getLocalVelocity().y<0)
                baseVector= new Vector2(0f, 1.3f);
                //going in reverse - less force
            else
                baseVector=new Vector2(0f, 0.7f);
        }
        else if (this.accelerate== ACC_NONE ) {
            //slow down if not accelerating
            baseVector=new Vector2(0, 0);
            if (this.getSpeedKMH()<7)
                this.setSpeed(0);
            else if (this.getLocalVelocity().y<0)
                baseVector=new Vector2(0, 0.7f);
            else if (this.getLocalVelocity().y>0)
                baseVector=new Vector2(0, -0.7f);
        }
        else
            baseVector=new Vector2(0, 0);

        //multiply by engine power, which gives us a force vector relative to the wheel
        Vector2 forceVector= new Vector2(this.power*baseVector.x, this.power*baseVector.y);

        //apply force to each wheel
        for(Wheel wheel:this.getPoweredWheels()){
            Vector2 position= wheel.body.getWorldCenter();
            wheel.body.applyForce(wheel.body.getWorldVector(new Vector2(forceVector.x, forceVector.y)), position, true);
        }

        //if going very slow, stop - to prevent endless sliding

    }

    public void render(SpriteBatch batch) {
        vehicleSprite.setPosition(BaseScreen.PIXELS_PER_METER * body.getPosition().x - vehicleSprite.getRegionWidth() / 2,
                BaseScreen.PIXELS_PER_METER * body.getPosition().y - vehicleSprite.getRegionHeight() / 2 );
        vehicleSprite.setRotation((MathUtils.radiansToDegrees * body.getAngle()));

        vehicleSprite.draw(batch);
    }

    public List<Wheel> getPoweredWheels () {
        List<Wheel> poweredWheels = new ArrayList<Wheel>();
        for (Wheel wheel:this.wheels) {
            if (wheel.powered)
                poweredWheels.add(wheel);
        }
        return poweredWheels;
    }

    public Vector2 getLocalVelocity() {
	    /*
	    returns car's velocity vector relative to the car
	    */
        return this.body.getLocalVector(this.body.getLinearVelocityFromLocalPoint(new Vector2(0, 0)));
    }

    public List<Wheel> getRevolvingWheels () {
        List<Wheel> revolvingWheels = new ArrayList<Wheel>();
        for (Wheel wheel:this.wheels) {
            if (wheel.revolving)
                revolvingWheels.add(wheel);
        }
        return revolvingWheels;
    }

    public float getSpeedKMH(){
        Vector2 velocity=this.body.getLinearVelocity();
        float len = velocity.len();
        return (len/1000)*3600;
    }

    public void setSpeed (float speed){
	    /*
	    speed - speed in kilometers per hour
	    */
        Vector2 velocity=this.body.getLinearVelocity();
        velocity=velocity.nor();
        velocity=new Vector2(velocity.x*((speed*1000.0f)/3600.0f),
                velocity.y*((speed*1000.0f)/3600.0f));
        this.body.setLinearVelocity(velocity);
    }

    public void setSteer(int value){
        this.steer = value;
    }

    public void setAccelerate(int value){
        this.accelerate = value;
    }

}
