package cm.smith.games.tracktion.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import aurelienribon.tweenengine.TweenAccessor;
import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.Tweens;
import cm.smith.games.tracktion.screens.BaseScreen;

/**
 * Created by anthony on 2016-11-09.
 */

public class UIImageButton extends ImageButton implements TweenAccessor<ImageButton> {

    private UIImageButton(ImageButtonStyle style) {
        super(style);
    }

    /**
     * Method that generates a text button with a custom callback function
     * @param texture Image that is displayed on the button
     * @return
     */
    public static UIImageButton makeButton(MainGame game, TextureRegion texture) {
        ImageButton.ImageButtonStyle btnStyle = new ImageButton.ImageButtonStyle();
        //btnStyle.up = new TextureRegionDrawable(Assets.btnCircleUpState);
        //btnStyle.down = new TextureRegionDrawable(Assets.btnCircleDownState);
        btnStyle.imageUp = new TextureRegionDrawable(texture);
        btnStyle.pressedOffsetX = -1;
        btnStyle.unpressedOffsetX = -1;

        UIImageButton tempBtn = new UIImageButton(btnStyle);
        tempBtn.getImage().setScale(BaseScreen.SCALE_Y / 2);
        tempBtn.pack();
        tempBtn.getImage().setOrigin(Align.center);
        return tempBtn;
    }

    /**
     * Change the alpha of the LabelButton to either 100% or 0%
     * @param isInvisible whether or not the LabelButton is visible or not
     */
    public void setInvisible(boolean isInvisible) {
        Color tempColor = getColor();
        tempColor.a = (isInvisible) ? 0 : 1;
        setColor(tempColor);
    }

    @Override
    public int getValues(ImageButton target, int tweenType, float[] returnValues) {
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
    public void setValues(ImageButton target, int tweenType, float[] newValues) {
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
