package cm.smith.games.tracktion.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import aurelienribon.tweenengine.TweenAccessor;
import cm.smith.games.tracktion.Colors;
import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.Tweens;
import cm.smith.games.tracktion.screens.BaseScreen;

/**
 * Created by anthony on 2016-10-27.
 */

public class LabelButton extends TextButton implements TweenAccessor<TextButton> {

    private LabelButton(String label, TextButtonStyle style) {
        super(label, style);
    }

    /**
     * Method that generates a text button with a custom callback function
     * @param label String to display on button
     * @param size Size of the font displayed on the button
     * @param color Color of the font displayed on the button
     * @param callback Method is called once the button is clicked
     * @return
     */
    public static LabelButton makeButton(MainGame game, String label, Integer size, Color color, final Callback callback) {
        BitmapFont font = game.assetManager.get("font" + size + ".ttf", BitmapFont.class);

        TextButtonStyle btnStyle = new TextButtonStyle();
        btnStyle.font = font;
        btnStyle.font.getData().setScale(BaseScreen.SCALE_X, BaseScreen.SCALE_Y);

        LabelButton tempBtn = new LabelButton(label, btnStyle);
        tempBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                callback.onClick();
            }
        });

        return tempBtn;
    }

    /**
     * Method that generates a text button with a custom callback function
     * @param label String to display on button
     * @param size Size of the font displayed on the button
     * @param callback Method that is called once the button is clicked
     * @return
     */
    public static LabelButton makeButton(MainGame game, String label, Integer size, final Callback callback) {
        return LabelButton.makeButton(game, label, size, Colors.LIGHT_TEXT, callback);
    }

    /**
     * Method that generates a text button with a custom callback function
     * @param label String to display on button
     * @param callback Method that is called once the button is clicked
     * @return
     */
    public static LabelButton makeButton(MainGame game, String label, final Callback callback) {
        return LabelButton.makeButton(game, label, 80, Colors.LIGHT_TEXT, callback);
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
    public int getValues(TextButton target, int tweenType, float[] returnValues) {
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
    public void setValues(TextButton target, int tweenType, float[] newValues) {
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
