package cm.smith.games.tracktion.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.Tweens;
import cm.smith.games.tracktion.ui.LabelButton;
import cm.smith.games.tracktion.ui.TextLabel;

/**
 * Created by anthony on 2016-10-26.
 */

public class HelpScreen extends BaseScreen {

    LabelButton backButton;

    public HelpScreen(MainGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        backButton = LabelButton.makeButton(this.game, "< Back", 60, new LabelButton.Callback() {
            @Override
            public void onClick() {
                HelpScreen.this.transitionOutScreen(new TitleScreen(HelpScreen.this.game));
            }
        });
        backButton.setInvisible(true);

        TextLabel line1 = TextLabel.makeLabel(this.game, "This is a game about survival.");
        TextLabel line2 = TextLabel.makeLabel(this.game, "Work as a team to race through the track as");
        TextLabel line3 = TextLabel.makeLabel(this.game, "fast as possible without crashing.");

        Table buttonTable = new Table();
        buttonTable.add(backButton).padLeft(20 * SCALE_X).padTop(20 * SCALE_Y);
        buttonTable.setFillParent(true);

        Stack stack = new Stack();
        stack.setFillParent(true);

        stack.add(buttonTable.top().left());
        uiStage.addActor(stack);

        transitionIntoScreen();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    public void transitionIntoScreen() {
        // Initial intro tween animation
        Timeline.createSequence()
                .beginParallel()
                .push(Tween.from(backButton, Tweens.POSITION_X, 1f) .targetRelative(-500) .ease(TweenEquations.easeInOutCubic) .delay(0.25f))
                .push(Tween.to(backButton, Tweens.ALPHA, 1.5f) .target(1) .ease(TweenEquations.easeInBack))
                .end()
                .start(this.tweenManager);
    }

    public void transitionOutScreen(final BaseScreen screen) {
        Timeline tl = Timeline.createSequence()
                .push(Tween.to(backButton, Tweens.ALPHA, 0.5f) .target(0) .ease(TweenEquations.easeInCubic));

        this.transitionOut(tl, screen);
    }
}
