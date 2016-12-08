package cm.smith.games.tracktion.ui;

import com.badlogic.gdx.Gdx;
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

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.Tweens;
import cm.smith.games.tracktion.controllers.GameController;
import cm.smith.games.tracktion.screens.BaseScreen;
import cm.smith.games.tracktion.screens.TitleScreen;

/**
 * Created by anthony on 2016-11-10.
 */

public class Hud extends Stack {

    private BaseScreen screen;
    private DecimalFormat secondsFormatter;
    private DecimalFormat milliFormatter;

    public boolean isLeftDown;
    public boolean isRightDown;
    public boolean isAccelerateDown;

    // Generic HUD
    private UILabel time;
    private LabelButton mainMenu;
    private UILabel mode;
    private Table modeTable;

    // Driver HUD
    public UIImageButton turnLeftButton;
    public UIImageButton turnRightButton;
    public UIImageButton accelerateButton;

    public Hud(BaseScreen screen, GameController.ROLE role) {
        secondsFormatter = new DecimalFormat("00");
        // TODO: setup milliformatter to show the milliseconds roll by

        isLeftDown = false;
        isRightDown = false;
        isAccelerateDown = false;

        this.screen = screen;
        modeTable = new Table();
        setFillParent(true);
        setupBaseHud();

        if (role == GameController.ROLE.DRIVER) {
            setupDriverHud();
        }
        else {
            setupBuilderHud();
        }
    }

    public void setupBaseHud() {
        time = UILabel.makeLabel(screen.game, "0:00", 75);
        Table timeTable = new Table();
        timeTable.setFillParent(true);
        timeTable.add(time).top();
        add(timeTable.top());

        mainMenu = LabelButton.makeButton(screen.game, "main menu", 40, new LabelButton.Callback() {
            @Override
            public void onClick() {
                screen.game.multiplayerServices.disconnect();
                screen.game.setScreen(new TitleScreen(screen.game));
            }
        });
        Table menuTable = new Table();
        menuTable.setFillParent(true);
        menuTable.add(mainMenu).top().left().padLeft(20 * BaseScreen.SCALE_X).padTop(15 * BaseScreen.SCALE_Y);
        add(menuTable.top().left());
    }

    public void setupDriverHud() {
        Texture texture = screen.game.assetManager.get("gamecontrols.png", Texture.class);
        TextureRegion leftTexture = new TextureRegion(texture, 0, 0, 128, 256);
        TextureRegion rightTexture = new TextureRegion(texture, 0, 0, 128, 256);
        TextureRegion accelerateTexture = new TextureRegion(texture, 0, 256, 256, 128);
        rightTexture.flip(true, false);

        turnLeftButton = UIImageButton.makeButton(screen.game, leftTexture);
        turnRightButton = UIImageButton.makeButton(screen.game, rightTexture);
        accelerateButton = UIImageButton.makeButton(screen.game, accelerateTexture);

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
                .padBottom(100 * BaseScreen.SCALE_Y));

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

    public String getTimer(float timeToShow) {
        int minutes = (int)(timeToShow / 60);
        int seconds = (int)(timeToShow % 60);
        return minutes + ":" + secondsFormatter.format(seconds);
    }

    public void update(float delta) {

    }

    public void playText(String text) {
        if (mode != null) {
            modeTable.removeActor(mode);
        }
        mode = UILabel.makeLabel(screen.game, text, 120);
        modeTable.add(mode);
        add(modeTable);

        Tween.to(mode, Tweens.ALPHA, 0.5f) .target(1) .ease(TweenEquations.easeInBack)
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        Tween.to(mode, Tweens.ALPHA, 1f) .target(0) .ease(TweenEquations.easeInBack)
                                .start(screen.tweenManager);
                    }
                })
                .start(screen.tweenManager);
    }

}