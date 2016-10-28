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
import cm.smith.games.tracktion.ui.LabelButton;

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
        LabelButton driverBtn = LabelButton.makeButton("play as driver", new LabelButton.Callback() {
            @Override
            public void onClick() {
                TitleScreen.this.game.setScreen(new GameScreen(TitleScreen.this.game, MainGame.ROLE_DRIVER));
            }
        });
        uiTable.padBottom(100f * BaseScreen.SCALE_Y).add(driverBtn).size(512 * SCALE_X, 110 * SCALE_Y);

        uiTable.row();

        // Play game as the track builder
        LabelButton builderBtn = LabelButton.makeButton("play as builder", new LabelButton.Callback() {
            @Override
            public void onClick() {
                TitleScreen.this.game.setScreen(new GameScreen(TitleScreen.this.game, MainGame.ROLE_BUILDER));
            }
        });
        uiTable.padTop(100f * BaseScreen.SCALE_Y).add(builderBtn).size(512 * SCALE_X, 110 * SCALE_Y);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}
