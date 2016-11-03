package cm.smith.games.tracktion.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.Tweens;
import cm.smith.games.tracktion.ui.LabelButton;
import cm.smith.games.tracktion.ui.TextLabel;

/**
 * Created by anthony on 2016-10-26.
 */

public class HelpScreen extends BaseScreen {

    boolean driverTextVisible;
    boolean animationFinished;

    LabelButton backButton;
    LabelButton changeHelpButton;

    TextLabel driverText;
    TextLabel builderText;

    public HelpScreen(MainGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        driverTextVisible = true;
        animationFinished = true;

        backButton = LabelButton.makeButton(this.game, "< Back", 60, new LabelButton.Callback() {
            @Override
            public void onClick() {
                HelpScreen.this.transitionOutScreen(new TitleScreen(HelpScreen.this.game));
            }
        });
        backButton.setInvisible(true);

        changeHelpButton = LabelButton.makeButton(this.game, "Builder Help >", 60, new LabelButton.Callback() {
            @Override
            public void onClick() {
                HelpScreen.this.toggleHelp();
            }
        });
        changeHelpButton.setInvisible(true);

        driverText = TextLabel.makeLabel(this.game,
                "As Driver\n" +
                        "Your goals are simple:\n" +
                        "- Move as far away from the start position to gain points\n" +
                        "- Don't crash\n" +
                        "- Don't slow down below 10km/h for more than 2 seconds\n"
        );
        driverText.setInvisible(true);

        builderText = TextLabel.makeLabel(this.game,
                "As Builder\n" +
                        "Your goals are simple:\n" +
                        "- Build a path for the driver to move along\n"
        );
        builderText.setInvisible(true);

        Table buttonTable = new Table();
        buttonTable.add(backButton).padLeft(20 * SCALE_X).padTop(20 * SCALE_Y);
        buttonTable.setFillParent(true);

        Table driverTextTable = new Table();
        driverTextTable.add(driverText);
        driverTextTable.setFillParent(true);

        Table builderTextTable = new Table();
        builderTextTable.add(builderText);
        builderTextTable.setFillParent(true);

        Table controlTable = new Table();
        controlTable.add(changeHelpButton).padRight(20 * SCALE_X).padBottom(20 * SCALE_X);
        controlTable.setFillParent(true);

        Stack stack = new Stack();
        stack.setFillParent(true);

        stack.add(buttonTable.top().left());
        stack.add(driverTextTable.center());
        stack.add(builderTextTable.center());
        stack.add(controlTable.bottom().right());
        uiStage.addActor(stack);

        transitionIntoScreen();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    private void toggleHelp() {
        if (!animationFinished) {
            return;
        }
        if (driverTextVisible) {
            changeHelpButton.setText("< Driver Help");
            animationFinished = false;

            Timeline.createSequence()
                    .beginParallel()
                    .push(Tween.to(driverText, Tweens.POSITION_X, 1f) .targetRelative(-Gdx.graphics.getWidth()) .ease(TweenEquations.easeInOutCubic))
                    .push(Tween.to(builderText, Tweens.POSITION_X, 1f) .targetRelative(-Gdx.graphics.getWidth()) .ease(TweenEquations.easeInOutCubic))
                    .end()
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            animationFinished = true;
                        }
                    })
                    .start(this.tweenManager);

            driverTextVisible = false;
        } else {
            changeHelpButton.setText("Builder Help >");
            animationFinished = false;

            Timeline.createSequence()
                    .beginParallel()
                    .push(Tween.to(driverText, Tweens.POSITION_X, 1f) .targetRelative(Gdx.graphics.getWidth()) .ease(TweenEquations.easeInOutCubic))
                    .push(Tween.to(builderText, Tweens.POSITION_X, 1f) .targetRelative(Gdx.graphics.getWidth()) .ease(TweenEquations.easeInOutCubic))
                    .end()
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            animationFinished = true;
                        }
                    })
                    .start(this.tweenManager);

            driverTextVisible = true;
        }
    }

    private void transitionIntoScreen() {
        builderText.moveBy(Gdx.graphics.getWidth(), 0);
        //builderText.setX(builderText.getX() + Gdx.graphics.getWidth());

        // Initial intro tween animation
        Timeline.createSequence()
                .beginParallel()
                .push(Tween.from(backButton, Tweens.POSITION_X, 1f) .targetRelative(-500) .ease(TweenEquations.easeInOutCubic) .delay(0.5f))
                .push(Tween.to(backButton, Tweens.ALPHA, 1.5f) .target(1) .ease(TweenEquations.easeInBack) .delay(0.25f))

                .push(Tween.from(changeHelpButton, Tweens.POSITION_X, 1f) .targetRelative(500) .ease(TweenEquations.easeInOutCubic) .delay(0.5f))
                .push(Tween.to(changeHelpButton, Tweens.ALPHA, 1.5f) .target(1) .ease(TweenEquations.easeInBack) .delay(0.25f))

                .push(Tween.to(driverText, Tweens.ALPHA, 1f) .target(1) .ease(TweenEquations.easeInBack))
                .push(Tween.to(builderText, Tweens.ALPHA, 1f) .target(1) .ease(TweenEquations.easeInBack))
                .push(Tween.to(builderText, Tweens.POSITION_X, 0.1f) .targetRelative(Gdx.graphics.getWidth()) .ease(TweenEquations.easeInOutCubic))

                .end()
                .start(this.tweenManager);
    }

    private void transitionOutScreen(final BaseScreen screen) {
        Timeline tl = Timeline.createSequence()
                .beginParallel()
                .push(Tween.to(backButton, Tweens.ALPHA, 0.5f) .target(0) .ease(TweenEquations.easeInCubic))
                .push(Tween.to(changeHelpButton, Tweens.ALPHA, 0.5f) .target(0) .ease(TweenEquations.easeInCubic))
                .push(Tween.to(driverText, Tweens.ALPHA, 0.5f) .target(0) .ease(TweenEquations.easeInCubic))
                .push(Tween.to(builderText, Tweens.ALPHA, 0.5f) .target(0) .ease(TweenEquations.easeInCubic))
                .end();

        this.transitionOut(tl, screen);
    }
}
