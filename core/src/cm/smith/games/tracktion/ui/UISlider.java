package cm.smith.games.tracktion.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import aurelienribon.tweenengine.TweenAccessor;
import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.Tweens;
import cm.smith.games.tracktion.screens.BaseScreen;

/**
 * Created by anthony on 2016-11-02.
 */

public class UISlider extends Slider implements TweenAccessor<Slider> {

    private UISlider(float min, float max, float stepSize, boolean vertical, SliderStyle style) {
        super(min, max, stepSize, vertical, style);
    }

    public static UISlider makeSlider(MainGame game, float min, float max, float stepSize, boolean vertical) {
        Texture knob = game.assetManager.get("uislider.png", Texture.class);

        TextureRegionDrawable progressBarImage = new TextureRegionDrawable(new TextureRegion(knob, 32, 32));
        TextureRegionDrawable progressBarKnobImage = new TextureRegionDrawable(new TextureRegion(knob, 32, 0, 32, 32));
        SliderStyle sliderStyle = new SliderStyle(progressBarImage, progressBarKnobImage);
        sliderStyle.knob.setMinHeight((sliderStyle.knob.getMinHeight() * BaseScreen.SCALE_Y) * 1.5f);
        sliderStyle.knob.setMinWidth((sliderStyle.knob.getMinHeight() / BaseScreen.SCALE_X) * 2);
        sliderStyle.background.setMinHeight(sliderStyle.background.getMinHeight() * BaseScreen.SCALE_Y);

        UISlider tempSlider = new UISlider(min, max, stepSize, vertical, sliderStyle);
        tempSlider.setValue(0.5f);

        return tempSlider;
    }

    @Override
    public int getValues(com.badlogic.gdx.scenes.scene2d.ui.Slider target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case Tweens.POSITION_X: returnValues[0] = target.getX(); return 1;
            case Tweens.POSITION_Y: returnValues[0] = target.getY(); return 1;
            case Tweens.POSITION_XY:
                returnValues[0] = target.getX();
                returnValues[1] = target.getY();
                return 2;
            case Tweens.ALPHA:
                returnValues[0] = target.getColor().a;
                return 1;
            default: assert false; return -1;
        }
    }

    @Override
    public void setValues(com.badlogic.gdx.scenes.scene2d.ui.Slider target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case Tweens.POSITION_X: target.setX(newValues[0]); break;
            case Tweens.POSITION_Y: target.setY(newValues[0]); break;
            case Tweens.POSITION_XY:
                target.setX(newValues[0]);
                target.setY(newValues[1]);
                break;
            case Tweens.ALPHA:
                Color oldColor = target.getColor();
                oldColor.a = newValues[0];
                target.setColor(oldColor);
                break;
            default: assert false; break;
        }
    }

    public void setInvisible(boolean isInvisible) {
        Color tempColor = getColor();
        tempColor.a = (isInvisible) ? 0 : 1;
        setColor(tempColor);
    }


    /**
     * The Callback interface.
     *
     * @author Anthony Smith
     */
    public interface Callback {
        /**
         * To do when button is clicked.
         */
        void onClick();
    }

}
