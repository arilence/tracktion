package cm.smith.games.tracktion.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import cm.smith.games.tracktion.MainGame;

/**
 * Created by anthony on 2016-09-16.
 */
public class TitleScreen extends BaseScreen {

    // Holds all the UI Elements
    Table uiTable;

    public TitleScreen(final MainGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        if (!game.playServices.isSignedIn()) {
            game.playServices.signIn();
            game.playServices.connectOnline();
        }

        uiTable = new Table();
        uiTable.setFillParent(true);
        this.uiStage.addActor(uiTable);

        // Play game as a driver
        TextButton playDriverBTN = new TextButton("Play as Driver", getTextSkin());
        playDriverBTN.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TitleScreen.this.game.setScreen(new GameScreen(TitleScreen.this.game, MainGame.ROLE_DRIVER));
            }
        });
        uiTable.padBottom(100f * BaseScreen.SCALE_Y).add(playDriverBTN).size(512 * SCALE_X, 110 * SCALE_Y);

        uiTable.row();

        // Play game as the track builder
        TextButton playBuilderBTN = new TextButton("Play as Builder", getTextSkin());
        playBuilderBTN.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TitleScreen.this.game.setScreen(new GameScreen(TitleScreen.this.game, MainGame.ROLE_BUILDER));
            }
        });
        uiTable.padTop(100f * BaseScreen.SCALE_Y).add(playBuilderBTN).size(512 * SCALE_X, 110 * SCALE_Y);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    public Skin getTextSkin() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        BitmapFont font18 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        Skin skin = new Skin();
        skin.add("default", font18);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = skin.getFont("default");
        textButtonStyle.font.getData().setScale(BaseScreen.SCALE_X, BaseScreen.SCALE_Y);
        skin.add("default", textButtonStyle);

        return skin;
    }
}
