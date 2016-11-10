package cm.smith.games.tracktion.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.controllers.GameController;
import cm.smith.games.tracktion.screens.BaseScreen;

/**
 * Created by anthony on 2016-11-10.
 */

public class Hud extends Stack {

    private MainGame game;

    public boolean isLeftDown;
    public boolean isRightDown;
    public boolean isAccelerateDown;

    public UIImageButton turnLeftButton;
    public UIImageButton turnRightButton;
    public UIImageButton accelerateButton;

    public Hud(MainGame game) {
        isLeftDown = false;
        isRightDown = false;
        isAccelerateDown = false;

        this.game = game;
    }

    public void setupDriverHud() {
        Texture texture = game.assetManager.get("gamecontrols.png", Texture.class);
        TextureRegion leftTexture = new TextureRegion(texture, 0, 0, 64, 128);
        TextureRegion rightTexture = new TextureRegion(texture, 0, 0, 64, 128);
        TextureRegion accelerateTexture = new TextureRegion(texture, 0, 128, 128, 64);
        rightTexture.flip(true, false);

        turnLeftButton = UIImageButton.makeButton(game, leftTexture);
        turnRightButton = UIImageButton.makeButton(game, rightTexture);
        accelerateButton = UIImageButton.makeButton(game, accelerateTexture);

        turnLeftButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isLeftDown = true;
                return super.touchDown(event, x, y, pointer, button);
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isLeftDown = false;
                super.touchUp(event, x, y, pointer, button);
            }
        });
        turnRightButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isRightDown = true;
                return super.touchDown(event, x, y, pointer, button);
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isRightDown = false;
                super.touchUp(event, x, y, pointer, button);
            }
        });
        accelerateButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isAccelerateDown = true;
                return super.touchDown(event, x, y, pointer, button);
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isAccelerateDown = false;
                super.touchUp(event, x, y, pointer, button);
            }
        });

        Table turningTable = new Table();
        turningTable.setFillParent(true);
        turningTable.add(turnLeftButton).padLeft(80 * BaseScreen.SCALE_X).padBottom(70 * BaseScreen.SCALE_Y);
        turningTable.add(turnRightButton).padLeft(100 * BaseScreen.SCALE_X).padBottom(70 * BaseScreen.SCALE_Y);

        Table gearbox = new Table();
        gearbox.setFillParent(true);
        gearbox.add(accelerateButton.padRight(80 * BaseScreen.SCALE_X).padBottom(85 * BaseScreen.SCALE_Y));

        setFillParent(true);
        add(turningTable.bottom().left());
        add(gearbox.bottom().right());
    }

    public void setupBuilderHud() {

    }

}