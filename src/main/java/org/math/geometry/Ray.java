package org.math.geometry;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.math.vector.Vector3;

import java.io.Serializable;

@EqualsAndHashCode
@ToString
public class Ray implements Serializable {

    @Getter
    @Setter
    private Vector3 origin;

    @Getter
    @Setter
    private Vector3 direction;

    public Ray(Vector3 origin, Vector3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vector3 getPoint(float distance) {
        return direction.mul(distance).add(origin);
    }

}
