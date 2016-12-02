package cm.smith.games.tracktion.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.controllers.GameController;
import cm.smith.games.tracktion.screens.BaseScreen;

/**
 * Created by anthony on 2016-11-10.
 */

public class Hud extends Stack {

    private MainGame game;
    private DecimalFormat secondsFormatter;
    private DecimalFormat milliFormatter;

    public boolean isLeftDown;
    public boolean isRightDown;
    public boolean isAccelerateDown;

    // Generic HUD
    private UILabel time;

    // Driver HUD
    public UIImageButton turnLeftButton;
    public UIImageButton turnRightButton;
    public UIImageButton accelerateButton;

    public Hud(MainGame game) {
        secondsFormatter = new DecimalFormat("00");
        // TODO: setup milliformatter to show the milliseconds roll by

        isLeftDown = false;
        isRightDown = false;
        isAccelerateDown = false;

        this.game = game;
    }

    public void setupBaseHud() {
        time = UILabel.makeLabel(this.game, "0:00", 75);

        Table timeTable = new Table();
        timeTable.setFillParent(true);

        timeTable.add(time).top();
        add(timeTable);
    }

    public void setupDriverHud() {
        Texture texture = game.assetManager.get("gamecontrols.png", Texture.class);
        TextureRegion leftTexture = new TextureRegion(texture, 0, 0, 128, 256);
        TextureRegion rightTexture = new TextureRegion(texture, 0, 0, 128, 256);
        TextureRegion accelerateTexture = new TextureRegion(texture, 0, 256, 256, 128);
        rightTexture.flip(true, false);

        turnLeftButton = UIImageButton.makeButton(game, leftTexture);
        turnRightButton = UIImageButton.makeButton(game, rightTexture);
        accelerateButton = UIImageButton.makeButton(game, accelerateTexture);

        turnLeftButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                isLeftDown = true;
                super.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                isLeftDown = false;
                super.exit(event, x, y, pointer, toActor);
            }
        });
        turnRightButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                isRightDown = true;
                super.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                isRightDown = false;
                super.exit(event, x, y, pointer, toActor);
            }
        });
        accelerateButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                isAccelerateDown = true;
                super.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                isAccelerateDown = false;
                super.exit(event, x, y, pointer, toActor);
            }
        });

        Table turningTable = new Table();
        turningTable.setFillParent(true);
        turningTable.add(turnLeftButton).padLeft(90 * BaseScreen.SCALE_X).padBottom(70 * BaseScreen.SCALE_Y);
        turningTable.add(turnRightButton).padLeft(70 * BaseScreen.SCALE_X).padBottom(70 * BaseScreen.SCALE_Y);

        Table gearbox = new Table();
        gearbox.setFillParent(true);
        gearbox.add(accelerateButton
                .padRight(80 * BaseScreen.SCALE_X)
                .padBottom(90 * BaseScreen.SCALE_Y));

        setFillParent(true);
        add(gearbox.bottom().right());
        add(turningTable.bottom().left());
    }

    public void setupBuilderHud() {

    }

    public void updateTimer(float timeToShow) {
        int minutes = (int)(timeToShow / 60);
        int seconds = (int)(timeToShow % 60);
        time.setText(minutes + ":" + secondsFormatter.format(seconds));
    }

    public void update(float delta) {

    }

}