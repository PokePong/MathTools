package org.math.vector;

import lombok.*;
import org.math.Mathf;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
@ToString
public class Vector2 extends Vector<Vector2> implements Serializable {

    public static final Vector2 ZERO = new Vector2(0f, 0f);
    public static final Vector2 UNIT_X = new Vector2(1f, 0f);
    public static final Vector2 UNIT_Y = new Vector2(0f, 1f);
    public static final Vector2 UNIT = new Vector2(1f, 1f);

    @Getter
    @Setter
    public float x;

    @Getter
    @Setter
    public float y;

    public Vector2() {
        this(0, 0);
    }

    public Vector2(float s) {
        this(s, s);
    }

    public Vector2(Vector2 v) {
        this(v.getX(), v.getY());
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float determinant(Vector2 v) {
        return (x * v.y) - (y * v.x);
    }

    public Vector2 set(float x, float y) {
        setX(x);
        setY(y);
        return this;
    }

    /**
     * <code>getAngle</code> returns (in radians) the angle represented by this
     * Vector2 as expressed by a conversion from rectangular coordinates (
     * <code>x</code>,&nbsp;<code>y</code>) to polar coordinates
     * (r,&nbsp;<i>theta</i>).
     *
     * @return the angle in radians. [-pi, pi)
     */
    public float getAngle() {
        return Mathf.aTan2(y, x);
    }

    /**
     * <code>angleBetween</code> returns (in radians) the angle required to rotate
     * a ray represented by this vector to lie colinear to a ray described by the
     * given vector. It is assumed that both this vector and the given vector are
     * unit vectors (iow, normalized).
     *
     * @param v the "destination" unit vector
     * @return the angle in radians.
     */
    @Override
    public float angleBetween(Vector2 v) {
        return v.getAngle() - getAngle();
    }

    /**
     * <code>smallestAngleBetween</code> returns (in radians) the minimum angle
     * between two vectors. It is assumed that both this vector and the given
     * vector are unit vectors (iow, normalized).
     *
     * @param otherVector a unit vector to find the angle against
     * @return the angle in radians.
     */
    public float smallestAngleBetween(Vector2 otherVector) {
        float dotProduct = dot(otherVector);
        return Mathf.aCos(dotProduct);
    }

    @Override
    public float[] toArray() {
        return new float[]{x, y};
    }

    @Override
    public Vector2 build(@NonNull float[] values) {
        return new Vector2(values[0], values[1]);
    }

    @Override
    public Vector2 build(float value) {
        return new Vector2(value);
    }

    public static Vector2 reflect(Vector2 direction, Vector2 normal) {
        float factor = -2f * normal.dot(direction);
        return new Vector2(factor * normal.x + direction.x, factor * normal.y + direction.y);
    }

    public static Vector2 perpendicular(Vector2 direction) {
        return new Vector2(-direction.getY(), direction.getX());
    }

}
