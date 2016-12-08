package cm.smith.games.tracktion.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.IntMap;

/**
 * Created by anthony on 2016-12-08.
 */

public class AnimationComponent implements Component {
    public IntMap<Animation> animations = new IntMap<Animation>();
}
