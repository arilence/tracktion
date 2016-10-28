package cm.smith.games.tracktion.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import cm.smith.games.tracktion.screens.BaseScreen;

/**
 * Created by anthony on 2016-10-27.
 */

public class LabelButton extends TextButton {

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
    public static LabelButton makeButton(String label, Integer size, Color color, final Callback callback) {
        // Generate the font sprite from font file
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Thin.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 80;
        parameter.color = Color.BLACK;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose(); // don't forget to dispose to avoid memory leaks!


        TextButtonStyle btnStyle = new TextButtonStyle();
        //btnStyle.up = new TextureRegionDrawable(Assets.btnRectUpState);
        //btnStyle.down = new TextureRegionDrawable(Assets.btnRectDownState);
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
    public static LabelButton makeButton(String label, Integer size, final Callback callback) {
        return LabelButton.makeButton(label, size, Color.BLACK, callback);
    }

    /**
     * Method that generates a text button with a custom callback function
     * @param label String to display on button
     * @param callback Method that is called once the button is clicked
     * @return
     */
    public static LabelButton makeButton(String label, final Callback callback) {
        return LabelButton.makeButton(label, 80, Color.BLACK, callback);
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
