package cm.smith.games.tracktion.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.ui.LabelButton;
import cm.smith.games.tracktion.ui.TextLabel;

/**
 * Created by anthony on 2016-09-16.
 */
public class TitleScreen extends BaseScreen {

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

        // Game Logo
        Label gameLogo = TextLabel.makeLabel("TRACKTION", 150);

        // Play game as a driver
        LabelButton driverBtn = LabelButton.makeButton("play as driver", new LabelButton.Callback() {
            @Override
            public void onClick() {
                TitleScreen.this.game.setScreen(new GameScreen(TitleScreen.this.game, MainGame.ROLE_DRIVER));
            }
        });

        // Play game as the track builder
        LabelButton builderBtn = LabelButton.makeButton("play as builder", new LabelButton.Callback() {
            @Override
            public void onClick() {
                TitleScreen.this.game.setScreen(new GameScreen(TitleScreen.this.game, MainGame.ROLE_BUILDER));
            }
        });
        builderBtn.right();

        // Setup UI Containers
        Table uiTable = new Table();
        uiTable.setFillParent(true);
        HorizontalGroup horiGroup = new HorizontalGroup();
        Table buttonTable = new Table();

        // Add elements to the containers
        buttonTable.padBottom(100f * BaseScreen.SCALE_Y)
                .add(driverBtn)
                .size(512 * SCALE_X, 110 * SCALE_Y)
                .width(MainGame.VIEW_WIDTH / 2)
                .align(Align.right);
        buttonTable.row();
        buttonTable.padTop(100f * BaseScreen.SCALE_Y)
                .add(builderBtn)
                .size(512 * SCALE_X, 110 * SCALE_Y)
                .width(MainGame.VIEW_WIDTH / 2)
                .align(Align.right);

        horiGroup.addActor(gameLogo);
        horiGroup.addActor(buttonTable);

        // Add the containers to the screen
        uiTable.add(horiGroup);
        this.uiStage.addActor(uiTable);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}
