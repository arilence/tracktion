package cm.smith.games.tracktion.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;

import cm.smith.games.tracktion.components.BoundsComponent;
import cm.smith.games.tracktion.components.StateComponent;
import cm.smith.games.tracktion.components.TrackComponent;
import cm.smith.games.tracktion.components.TransformComponent;
import cm.smith.games.tracktion.components.VehicleComponent;
import cm.smith.games.tracktion.entities.Vehicle;

/**
 * Created by anthony on 2016-12-08.
 */

public class CollisionSystem extends EntitySystem {
    private ComponentMapper<BoundsComponent> bm;
    private ComponentMapper<StateComponent> sm;
    private ComponentMapper<TransformComponent> tm;

    public static interface CollisionListener {
        void trackCollision(boolean isOutside);
    }

    private Engine engine;
    private CollisionListener listener;
    private ImmutableArray<Entity> vehicles;
    private ImmutableArray<Entity> tracks;

    public CollisionSystem(CollisionListener listener) {
        this.listener = listener;

        bm = ComponentMapper.getFor(BoundsComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = engine;

        vehicles = engine.getEntitiesFor(Family.all(VehicleComponent.class, BoundsComponent.class, TransformComponent.class, StateComponent.class).get());
        tracks = engine.getEntitiesFor(Family.all(TrackComponent.class, BoundsComponent.class, TransformComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < vehicles.size(); ++i) {
            Entity vehicle = vehicles.get(i);

            StateComponent vehicleState = sm.get(vehicle);

            if (vehicleState.get() == Vehicle.STATE_DEAD) {
                continue;
            }

            BoundsComponent vehicleBounds = bm.get(vehicle);
            boolean outsideTrack = true;

            for (int j = 0; j < tracks.size(); ++j) {
                Entity track = tracks.get(j);
                BoundsComponent trackBounds = bm.get(track);

                if (Intersector.overlapConvexPolygons(vehicleBounds.bounds, trackBounds.bounds)) {
                    outsideTrack = false;
                }
            }

            listener.trackCollision(outsideTrack);
        }
    }
}
