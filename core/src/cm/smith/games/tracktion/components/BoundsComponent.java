package cm.smith.games.tracktion.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Polygon;

/**
 * Created by anthony on 2016-12-08.
 */

public class BoundsComponent implements Component {
    public Polygon bounds = new Polygon();
}
