package org.math.geometry;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.math.vector.Vector2;

import java.io.Serializable;

@EqualsAndHashCode
@ToString
public class Ray2D implements Serializable {

    @Getter
    @Setter
    private Vector2 origin;

    @Getter
    @Setter
    private Vector2 direction;

    public Ray2D(Vector2 origin, Vector2 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vector2 getPoint(float distance) {
        return direction.mul(distance).add(origin);
    }
}
