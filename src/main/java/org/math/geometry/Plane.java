package org.math.geometry;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.math.Mathf;
import org.math.vector.Vector3;

import java.io.Serializable;

@EqualsAndHashCode
@ToString
public class Plane implements Serializable {

    public enum Side {
        POSITIVE,
        NEGATIVE,
        NONE
    }

    @Getter
    @Setter
    private Vector3 normal;

    @Getter
    @Setter
    private float constant;

    public Plane() {
        this(new Vector3(), 0);
    }

    public Plane(Vector3 normal, Vector3 point) {
        set(normal, point);
    }

    public Plane(Vector3 a, Vector3 b, Vector3 c) {
        set(a, b, c);
    }

    public Plane(Vector3 normal, float constant) {
        this.normal = normal.normalize();
        this.constant = constant;
    }

    public void set(Vector3 normal, Vector3 pos) {
        this.normal = normal.normalize();
        this.constant = -normal.dot(pos);
    }

    public void set(Vector3 a, Vector3 b, Vector3 c) {
        Vector3 x = b.sub(a);
        Vector3 y = c.sub(a);
        normal = x.cross(y).normalize();
        constant = -normal.dot(a);
    }

    public void flip() {
        normal.mul(-1);
        constant *= -1f;
    }

    public void translate(Vector3 translation) {
        constant += normal.dot(translation);
    }

    public Vector3 getClosestPointOnPlane(Vector3 from) {
        float dist = normal.dot(from) + constant;
        return from.sub(normal.mul(dist));
    }

    public float getDistanceToPoint(Vector3 point) {
        return normal.dot(point) + constant;
    }

    public boolean isOnPlane(Vector3 point) {
        float dist = getDistanceToPoint(point);
        return Mathf.approximately(Mathf.abs(dist), 0f);
    }

    public Side whichSide(Vector3 point) {
        if (isOnPlane(point))
            return Side.NONE;
        return getDistanceToPoint(point) > 0 ? Side.POSITIVE : Side.NEGATIVE;
    }

    public boolean isFrontFacing(Vector3 direction) {
        return normal.dot(direction) <= 0;
    }

    public float rayCast(Ray ray) {
        float vdot = normal.dot(ray.getDirection());
        float ndot = -normal.dot(ray.getOrigin()) - constant;
        return Mathf.approximately(vdot, 0.0f) ? 0.0f : ndot / vdot;
    }

}
