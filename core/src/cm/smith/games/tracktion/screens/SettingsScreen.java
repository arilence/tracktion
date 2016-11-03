package cm.smith.games.tracktion.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.sun.prism.impl.BaseGraphics;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.Tweens;
import cm.smith.games.tracktion.ui.LabelButton;
import cm.smith.games.tracktion.ui.TextLabel;
import cm.smith.games.tracktion.ui.UISlider;

/**
 * Created by anthony on 2016-11-02.
 */

public class SettingsScreen extends BaseScreen {

    LabelButton backButton;

    TextLabel effectsLabel;
    TextLabel musicLabel;
    TextLabel sensLabel;

    UISlider effectsSlider;
    UISlider musicSlider;
    UISlider sensSlider;

    public SettingsScreen(MainGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        //uiStage.setDebugAll(true);

        backButton = LabelButton.makeButton(this.game, "< Back", 60, new LabelButton.Callback() {
            @Override
            public void onClick() {
                SettingsScreen.this.transitionOutScreen(new TitleScreen(SettingsScreen.this.game));
            }
        });
        backButton.setInvisible(true);

        effectsLabel = TextLabel.makeLabel(this.game, "Effects Level");
        effectsLabel.setInvisible(true);
        musicLabel = TextLabel.makeLabel(this.game, "Music Level");
        musicLabel.setInvisible(true);
        sensLabel = TextLabel.makeLabel(this.game, "Turn Sensitivity");
        sensLabel.setInvisible(true);

        effectsSlider = UISlider.makeSlider(this.game, 0, 100, 10, false);
        effectsSlider.setValue(50);
        effectsSlider.setInvisible(true);
        musicSlider = UISlider.makeSlider(this.game, 0, 100, 10, false);
        musicSlider.setValue(50);
        musicSlider.setInvisible(true);
        sensSlider = UISlider.makeSlider(this.game, 0, 100, 10, false);
        sensSlider.setValue(50);
        sensSlider.setInvisible(true);

        Table buttonTable = new Table();
        buttonTable.add(backButton).padLeft(20 * SCALE_X).padTop(20 * SCALE_Y);
        buttonTable.setFillParent(true);

        Table settingsTable = new Table();
        settingsTable.add(effectsLabel).padBottom(20 * SCALE_Y).center().left();
        settingsTable.add(effectsSlider).width(500 * SCALE_X).height(60 * SCALE_Y).center().right().padBottom(20 * SCALE_Y);
        settingsTable.row();

        settingsTable.add(musicLabel).padBottom(20 * SCALE_Y).center().left();
        settingsTable.add(musicSlider).width(500 * SCALE_X).height(60 * SCALE_Y).center().right().padBottom(20 * SCALE_Y);
        settingsTable.row();

        settingsTable.add(sensLabel);
        settingsTable.add(sensSlider).width(500 * SCALE_X).height(60 * SCALE_Y).center().right();

        Stack stack = new Stack();
        stack.setFillParent(true);

        stack.add(buttonTable.top().left());
        stack.add(settingsTable.center());
        uiStage.addActor(stack);

        transitionIntoScreen();
    }

    private void transitionIntoScreen() {
        // Initial intro tween animation
        Timeline.createSequence()
                .beginParallel()
                .push(Tween.from(backButton, Tweens.POSITION_X, 1f) .targetRelative(-500) .ease(TweenEquations.easeInOutCubic) .delay(0.5f))
                .push(Tween.to(backButton, Tweens.ALPHA, 1.5f) .target(1) .ease(TweenEquations.easeInBack) .delay(0.25f))

                .push(Tween.to(effectsLabel, Tweens.ALPHA, 1f) .target(1) .ease(TweenEquations.easeInBack))
                .push(Tween.to(effectsSlider, Tweens.ALPHA, 1f) .target(1) .ease(TweenEquations.easeInBack))

                .push(Tween.to(musicLabel, Tweens.ALPHA, 1f) .target(1) .ease(TweenEquations.easeInBack))
                .push(Tween.to(musicSlider, Tweens.ALPHA, 1f) .target(1) .ease(TweenEquations.easeInBack))

                .push(Tween.to(sensLabel, Tweens.ALPHA, 1f) .target(1) .ease(TweenEquations.easeInBack))
                .push(Tween.to(sensSlider, Tweens.ALPHA, 1f) .target(1) .ease(TweenEquations.easeInBack))

                .end()
                .start(this.tweenManager);
    }

    private void transitionOutScreen(final BaseScreen screen) {
        Timeline tl = Timeline.createSequence()
                .beginParallel()
                .push(Tween.to(backButton, Tweens.ALPHA, 0.5f) .target(0) .ease(TweenEquations.easeInCubic))

                .push(Tween.to(effectsLabel, Tweens.ALPHA, 0.5f) .target(0) .ease(TweenEquations.easeInCubic))
                .push(Tween.to(effectsSlider, Tweens.ALPHA, 0.5f) .target(0) .ease(TweenEquations.easeInCubic))

                .push(Tween.to(musicLabel, Tweens.ALPHA, 0.5f) .target(0) .ease(TweenEquations.easeInCubic))
                .push(Tween.to(musicSlider, Tweens.ALPHA, 0.5f) .target(0) .ease(TweenEquations.easeInCubic))

                .push(Tween.to(sensLabel, Tweens.ALPHA, 0.5f) .target(0) .ease(TweenEquations.easeInCubic))
                .push(Tween.to(sensSlider, Tweens.ALPHA, 0.5f) .target(0) .ease(TweenEquations.easeInCubic))

                .end();

        this.transitionOut(tl, screen);
    }

}
