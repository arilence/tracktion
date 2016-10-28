package cm.smith.games.tracktion.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Bounce;
import aurelienribon.tweenengine.equations.Elastic;
import aurelienribon.tweenengine.equations.Quad;
import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.Tweens;
import cm.smith.games.tracktion.ui.LabelButton;
import cm.smith.games.tracktion.ui.TextLabel;

/**
 * Created by anthony on 2016-09-16.
 */
public class TitleScreen extends BaseScreen {

    public TitleScreen(final MainGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        if (!game.playServices.isSignedIn()) {
            game.playServices.signIn();
            game.playServices.connectOnline();
        }

        this.tweenManager = new TweenManager();

        // Game Logo
        TextLabel gameLogo = TextLabel.makeLabel("TRACKTION", 150);
        gameLogo.setInvisible(true);

        // Play game as a driver
        LabelButton driverBtn = LabelButton.makeButton("play as driver", new LabelButton.Callback() {
            @Override
            public void onClick() {
                TitleScreen.this.game.setScreen(new GameScreen(TitleScreen.this.game, MainGame.ROLE_DRIVER));
            }
        });
        driverBtn.setInvisible(true);

        // Play game as the track builder
        LabelButton builderBtn = LabelButton.makeButton("play as builder", new LabelButton.Callback() {
            @Override
            public void onClick() {
                TitleScreen.this.game.setScreen(new GameScreen(TitleScreen.this.game, MainGame.ROLE_BUILDER));
            }
        });
        builderBtn.setInvisible(true);

        // Setup UI Containers
        Table uiTable = new Table();
        uiTable.setFillParent(true);
        HorizontalGroup horiGroup = new HorizontalGroup();
        Table buttonTable = new Table();

        // Add elements to the containers
        buttonTable.padBottom(100f * BaseScreen.SCALE_Y)
                .add(driverBtn)
                .size(512 * SCALE_X, 110 * SCALE_Y)
                .width(MainGame.VIEW_WIDTH / 2)
                .align(Align.right);
        buttonTable.row();
        buttonTable.padTop(100f * BaseScreen.SCALE_Y)
                .add(builderBtn)
                .size(512 * SCALE_X, 110 * SCALE_Y)
                .width(MainGame.VIEW_WIDTH / 2)
                .align(Align.right);

        horiGroup.addActor(gameLogo);
        horiGroup.addActor(buttonTable);

        // Add the containers to the screen
        uiTable.add(horiGroup);
        this.uiStage.addActor(uiTable);


        // Initial intro tween animation
        Timeline.createSequence()
                .beginParallel()
                    .push(Tween.from(gameLogo, Tweens.POSITION_X, 2f) .targetRelative(-500) .ease(TweenEquations.easeInOutCubic) .delay(1f))
                    .push(Tween.to(gameLogo, Tweens.ALPHA, 2.5f) .target(1) .ease(TweenEquations.easeInBack) .delay(0.75f))

                    .push(Tween.from(driverBtn, Tweens.POSITION_X, 2f) .targetRelative(500) .ease(TweenEquations.easeInOutCubic) .delay(2f))
                    .push(Tween.to(driverBtn, Tweens.ALPHA, 2.5f) .target(1) .ease(TweenEquations.easeInBack) .delay(1.5f))

                    .push(Tween.from(builderBtn, Tweens.POSITION_X, 2f) .targetRelative(500) .ease(TweenEquations.easeInOutCubic) .delay(2.5f))
                    .push(Tween.to(builderBtn, Tweens.ALPHA, 2.5f) .target(1) .ease(TweenEquations.easeInBack) .delay(2f))
                .end()
                .start(this.tweenManager);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}
