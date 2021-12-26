package org.math.vector;

import lombok.*;
import org.math.Mathf;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
@ToString
public class Vector3 extends Vector<Vector3> implements Serializable {

    public final static Vector3 ZERO = new Vector3(0f, 0f, 0f);
    public final static Vector3 NAN = new Vector3(Float.NaN, Float.NaN, Float.NaN);
    public final static Vector3 UNIT_X = new Vector3(1f, 0f, 0f);
    public final static Vector3 UNIT_Y = new Vector3(0f, 1f, 0f);
    public final static Vector3 UNIT_Z = new Vector3(0f, 0f, 1f);
    public final static Vector3 UNIT = new Vector3(1f, 1f, 1f);

    public final static Vector3 RIGHT = new Vector3(1f, 0f, 0f);
    public final static Vector3 LEFT = new Vector3(-1f, 0f, 0f);
    public final static Vector3 UP = new Vector3(0f, 1f, 0f);
    public final static Vector3 DOWN = new Vector3(0f, -1f, 0f);
    public final static Vector3 FORWARD = new Vector3(0f, 0f, 1f);
    public final static Vector3 BACK = new Vector3(0f, 0f, -1f);

    public final static Vector3 POSITIVE_INFINITY = new Vector3(
            Float.POSITIVE_INFINITY,
            Float.POSITIVE_INFINITY,
            Float.POSITIVE_INFINITY);
    public final static Vector3 NEGATIVE_INFINITY = new Vector3(
            Float.NEGATIVE_INFINITY,
            Float.NEGATIVE_INFINITY,
            Float.NEGATIVE_INFINITY);

    @Getter
    @Setter
    public float x;

    @Getter
    @Setter
    public float y;

    @Getter
    @Setter
    public float z;

    public Vector3() {
        this(0);
    }

    public Vector3(float s) {
        this(s, s, s);
    }

    public Vector3(Vector3 v) {
        this(v.x, v.y, v.z);
    }

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 cross(Vector3 v) {
        float resX = ((y * v.z) - (z * v.y));
        float resY = ((z * v.x) - (x * v.z));
        float resZ = ((x * v.y) - (y * v.x));
        return new Vector3(resX, resY, resZ);
    }

    public Vector3 set(float x, float y, float z) {
        setX(x);
        setY(y);
        setZ(z);
        return this;
    }

    public Vector3 projectOnPlane(Vector3 normal) {
        float n = dot(normal);
        float d = normal.lengthSquared();
        return sub(normal.normalize().mul(n / d));
    }

    @Override
    protected float[] toArray() {
        return new float[]{x, y, z};
    }

    @Override
    protected Vector3 build(@NonNull float[] values) {
        return new Vector3(values[0], values[1], values[2]);
    }

    @Override
    protected Vector3 build(float value) {
        return new Vector3(value);
    }

    @Override
    public float angleBetween(Vector3 v) {
        float dotProduct = this.dot(v);
        return Mathf.aCos(dotProduct);
    }

    public static Vector3 reflect(Vector3 inDirection, Vector3 inNormal) {
        float factor = -2f * inNormal.dot(inDirection);
        return new Vector3(factor * inNormal.x + inDirection.x,
                factor * inNormal.y + inDirection.y,
                factor * inNormal.z + inDirection.z);
    }
}
