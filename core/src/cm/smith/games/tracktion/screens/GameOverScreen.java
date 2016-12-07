package cm.smith.games.tracktion.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.Tweens;
import cm.smith.games.tracktion.controllers.GameController;
import cm.smith.games.tracktion.ui.LabelButton;
import cm.smith.games.tracktion.ui.UILabel;

/**
 * Created by anthony on 2016-12-04.
 */

public class GameOverScreen extends BaseScreen {

    MainGame game;
    GameController controller;

    UILabel gameOverText;
    UILabel timeLastedText;
    LabelButton retryBtn;
    LabelButton menuBtn;

    public GameOverScreen(MainGame game, GameController controller) {
        super(game);
        this.game = game;
        this.controller = controller;

        setupUiElements();
        configureUiContainers();
        transitionIntoScreen();
    }

    private void setupUiElements() {
        gameOverText = UILabel.makeLabel(this.game, "Nice Try!", 75);
        gameOverText.setInvisible(true);

        timeLastedText = UILabel.makeLabel(this.game, Float.toString(this.controller.time), 75);
        timeLastedText.setInvisible(true);
        
        retryBtn = LabelButton.makeButton(this.game, "retry?", new LabelButton.Callback() {
            @Override
            public void onClick() {

            }
        });
        retryBtn.setInvisible(true);

        menuBtn = LabelButton.makeButton(this.game, "return to menu", new LabelButton.Callback() {
            @Override
            public void onClick() {
                GameOverScreen.this.transitionOutScreen(new TitleScreen(GameOverScreen.this.game));
            }
        });
        menuBtn.setInvisible(true);
    }

    private void configureUiContainers() {
        Table infoTable = new Table();
        infoTable.padBottom(100f * BaseScreen.SCALE_Y)
                .add(gameOverText)
                .size(512 * SCALE_X, 110 * SCALE_Y)
                .width(MainGame.VIEW_WIDTH / 2)
                .align(Align.right);
        infoTable.row();
        infoTable.padTop(100f * BaseScreen.SCALE_Y)
                .add(timeLastedText)
                .size(512 * SCALE_X, 110 * SCALE_Y)
                .width(MainGame.VIEW_WIDTH / 2)
                .align(Align.center);

        // Configure the two play buttons
        Table buttonTable = new Table();
        buttonTable.padBottom(100f * BaseScreen.SCALE_Y)
                .add(retryBtn)
                .size(512 * SCALE_X, 110 * SCALE_Y)
                .width(MainGame.VIEW_WIDTH / 2)
                .align(Align.right);
        buttonTable.row();
        buttonTable.padTop(100f * BaseScreen.SCALE_Y)
                .add(menuBtn)
                .size(512 * SCALE_X, 110 * SCALE_Y)
                .width(MainGame.VIEW_WIDTH / 2)
                .align(Align.right);

        // Place game logo and two play buttons side by side
        Table horGroup = new Table();
        horGroup.add(infoTable);
        horGroup.columnDefaults(2);
        horGroup.add(buttonTable);

        Stack stack = new Stack();
        stack.setFillParent(true);

        // Piece it all together into awesomeness
        stack.add(horGroup);
        this.uiStage.addActor(stack);
    }

    private void transitionIntoScreen() {
        // Initial intro tween animation
        Timeline.createSequence()
                .beginParallel()
                .push(Tween.from(gameOverText, Tweens.POSITION_X, 2f) .targetRelative(-500) .ease(TweenEquations.easeInOutCubic))
                .push(Tween.to(gameOverText, Tweens.ALPHA, 2.5f) .target(1) .ease(TweenEquations.easeInBack))

                .push(Tween.from(timeLastedText, Tweens.POSITION_X, 2f) .targetRelative(-500) .ease(TweenEquations.easeInOutCubic))
                .push(Tween.to(timeLastedText, Tweens.ALPHA, 2.5f) .target(1) .ease(TweenEquations.easeInBack))

                .push(Tween.from(retryBtn, Tweens.POSITION_X, 2f) .targetRelative(500) .ease(TweenEquations.easeInOutCubic) .delay(1f))
                .push(Tween.to(retryBtn, Tweens.ALPHA, 2.5f) .target(1) .ease(TweenEquations.easeInBack) .delay(0.5f))

                .push(Tween.from(menuBtn, Tweens.POSITION_X, 2f) .targetRelative(500) .ease(TweenEquations.easeInOutCubic) .delay(1.5f))
                .push(Tween.to(menuBtn, Tweens.ALPHA, 2.5f) .target(1) .ease(TweenEquations.easeInBack) .delay(1f))
                .end()
                .start(this.tweenManager);
    }

    public void transitionOutScreen(final BaseScreen screen) {
        Timeline tl = Timeline.createSequence()
                .beginParallel()
                .push(Tween.to(gameOverText, Tweens.ALPHA, 0.5f) .target(0) .ease(TweenEquations.easeOutCubic))
                .push(Tween.to(timeLastedText, Tweens.ALPHA, 0.5f) .target(0) .ease(TweenEquations.easeOutCubic))
                .push(Tween.to(retryBtn, Tweens.ALPHA, 0.5f) .target(0) .ease(TweenEquations.easeOutCubic))
                .push(Tween.to(menuBtn, Tweens.ALPHA, 0.5f) .target(0) .ease(TweenEquations.easeOutCubic))
                .end();

        this.transitionOut(tl, screen);
    }

}
