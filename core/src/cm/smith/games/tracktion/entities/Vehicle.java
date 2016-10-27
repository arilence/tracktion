package cm.smith.games.tracktion.entities;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

/**
 * Created by anthony on 2016-09-18.
 */
public class Vehicle extends Entity {

    public Vehicle() {
        add(new PositionComponent());
        add(new VelocityComponent());
    }

    public class PositionComponent implements Component {
        public float x = 0.0f;
        public float y = 0.0f;
    }

    public class VelocityComponent implements Component {
        public float x = 0.0f;
        public float y = 0.0f;
        public float direction = 0.0f;
    }
}
