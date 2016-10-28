package cm.smith.games.tracktion.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import cm.smith.games.tracktion.Colors;
import cm.smith.games.tracktion.screens.BaseScreen;

/**
 * Created by anthony on 2016-10-27.
 */

public class TextLabel extends Label {

    private TextLabel(String label, Label.LabelStyle style) {
        super(label, style);
    }

    /**
     * Method that generates a text button with a custom callback function
     * @param label String to display on button
     * @param size Size of the font displayed on the button
     * @param color Color of the font displayed on the button
     * @return
     */
    public static TextLabel makeLabel(String label, Integer size, Color color) {
        // Generate the font sprite from font file
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Thin.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = color;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose(); // don't forget to dispose to avoid memory leaks!


        Label.LabelStyle btnStyle = new Label.LabelStyle();
        //btnStyle.up = new TextureRegionDrawable(Assets.btnRectUpState);
        //btnStyle.down = new TextureRegionDrawable(Assets.btnRectDownState);
        btnStyle.font = font;
        btnStyle.font.getData().setScale(BaseScreen.SCALE_X, BaseScreen.SCALE_Y);

        return new TextLabel(label, btnStyle);
    }

    /**
     * Method that generates a text button with a custom callback function
     * @param label String to display on button
     * @param size Size of the font displayed on the button
     * @return
     */
    public static TextLabel makeLabel(String label, Integer size) {
        return TextLabel.makeLabel(label, size, Colors.LIGHT_TEXT);
    }

    /**
     * Method that generates a text button with a custom callback function
     * @param label String to display on button
     * @return
     */
    public static TextLabel makeLabel(String label) {
        return TextLabel.makeLabel(label, 80, Colors.LIGHT_TEXT);
    }

}
