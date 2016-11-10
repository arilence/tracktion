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
 * Created by anthony on 2016-09-16.
 */
public class TitleScreen extends BaseScreen {

    UILabel gameLogo;
    LabelButton driverBtn;
    LabelButton builderBtn;
    LabelButton settingsBtn;
    LabelButton helpBtn;

    public TitleScreen(final MainGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

//        if (!game.playServices.isSignedIn()) {
//            game.playServices.signIn();
//            game.playServices.connectOnline();
//        }

        setupUiElements();
        configureUiContainers();
        transitionIntoScreen();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    private void setupUiElements() {
        // Game Logo
        gameLogo = UILabel.makeLabel(this.game, "TRACKTION", 75);
        gameLogo.setInvisible(true);

        driverBtn = LabelButton.makeButton(this.game, "play as driver", new LabelButton.Callback() {
            @Override
            public void onClick() {
                TitleScreen.this.transitionOutScreen(new TestGameScreen(TitleScreen.this.game, GameController.ROLE.DRIVER));
            }
        });
        driverBtn.setInvisible(true);

        builderBtn = LabelButton.makeButton(this.game, "play as builder", new LabelButton.Callback() {
            @Override
            public void onClick() {
                TitleScreen.this.transitionOutScreen(new TestGameScreen(TitleScreen.this.game, GameController.ROLE.BUILDER));
            }
        });
        builderBtn.setInvisible(true);

        settingsBtn = LabelButton.makeButton(this.game, "settings", 30, new LabelButton.Callback() {
            @Override
            public void onClick() {
                TitleScreen.this.transitionOutScreen(new SettingsScreen(TitleScreen.this.game));
            }
        });
        settingsBtn.setInvisible(true);

        helpBtn = LabelButton.makeButton(this.game, "help", 30, new LabelButton.Callback() {
            @Override
            public void onClick() {
                TitleScreen.this.transitionOutScreen(new HelpScreen(TitleScreen.this.game));
            }
        });
        helpBtn.setInvisible(true);
    }

    private void configureUiContainers() {
        //uiStage.setDebugAll(true);

        // Configure the two play buttons
        Table buttonTable = new Table();
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

        // Place game logo and two play buttons side by side
        Table horGroup = new Table();
        horGroup.add(gameLogo);
        horGroup.columnDefaults(2);
        horGroup.add(buttonTable);

        // Add the settings button to the bottom right of the screen
        Table bottomTable = new Table();
        bottomTable.setFillParent(true);

        bottomTable.add(helpBtn).padRight(40 * SCALE_X).padBottom(20 * SCALE_Y);
        bottomTable.add(settingsBtn).padRight(20 * SCALE_X).padBottom(20 * SCALE_Y);

        Stack stack = new Stack();
        stack.setFillParent(true);

        // Piece it all together into awesomeness
        stack.add(horGroup);
        stack.add(bottomTable.bottom().right());
        this.uiStage.addActor(stack);
    }

    private void transitionIntoScreen() {
        // Initial intro tween animation
        Timeline.createSequence()
                .beginParallel()
                .push(Tween.from(gameLogo, Tweens.POSITION_X, 2f) .targetRelative(-500) .ease(TweenEquations.easeInOutCubic) .delay(0.5f))
                .push(Tween.to(gameLogo, Tweens.ALPHA, 2.5f) .target(1) .ease(TweenEquations.easeInBack) .delay(0.25f))

                .push(Tween.from(driverBtn, Tweens.POSITION_X, 2f) .targetRelative(500) .ease(TweenEquations.easeInOutCubic) .delay(1.5f))
                .push(Tween.to(driverBtn, Tweens.ALPHA, 2.5f) .target(1) .ease(TweenEquations.easeInBack) .delay(1f))

                .push(Tween.from(builderBtn, Tweens.POSITION_X, 2f) .targetRelative(500) .ease(TweenEquations.easeInOutCubic) .delay(2f))
                .push(Tween.to(builderBtn, Tweens.ALPHA, 2.5f) .target(1) .ease(TweenEquations.easeInBack) .delay(1.5f))

                .push(Tween.to(helpBtn, Tweens.ALPHA, 2.5f) .target(1) .ease(TweenEquations.easeInBack) .delay(1.5f))

                .push(Tween.to(settingsBtn, Tweens.ALPHA, 2.5f) .target(1) .ease(TweenEquations.easeInBack) .delay(1.5f))
                .end()
                .start(this.tweenManager);
    }

    public void transitionOutScreen(final BaseScreen screen) {
        Timeline tl = Timeline.createSequence()
                .beginParallel()
                .push(Tween.to(gameLogo, Tweens.ALPHA, 0.5f) .target(0) .ease(TweenEquations.easeOutCubic))
                .push(Tween.to(driverBtn, Tweens.ALPHA, 0.5f) .target(0) .ease(TweenEquations.easeOutCubic))
                .push(Tween.to(builderBtn, Tweens.ALPHA, 0.5f) .target(0) .ease(TweenEquations.easeOutCubic))
                .push(Tween.to(helpBtn, Tweens.ALPHA, 0.5f) .target(0) .ease(TweenEquations.easeOutCubic))
                .push(Tween.to(settingsBtn, Tweens.ALPHA, 0.5f) .target(0) .ease(TweenEquations.easeOutCubic))
                .end();

        this.transitionOut(tl, screen);
    }
}
