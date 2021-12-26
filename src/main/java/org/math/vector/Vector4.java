package org.math.vector;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
@ToString
public class Vector4 extends AVector4<Vector4> implements Serializable {

    public final static Vector4 ZERO = new Vector4(0, 0, 0, 0);
    public final static Vector4 NAN = new Vector4(Float.NaN, Float.NaN, Float.NaN, Float.NaN);
    public final static Vector4 UNIT_X = new Vector4(1, 0, 0, 0);
    public final static Vector4 UNIT_Y = new Vector4(0, 1, 0, 0);
    public final static Vector4 UNIT_Z = new Vector4(0, 0, 1, 0);
    public final static Vector4 UNIT_W = new Vector4(0, 0, 0, 1);
    public final static Vector4 UNIT_XYZW = new Vector4(1, 1, 1, 1);


    public final static Vector4 POSITIVE_INFINITY = new Vector4(
            Float.POSITIVE_INFINITY,
            Float.POSITIVE_INFINITY,
            Float.POSITIVE_INFINITY,
            Float.POSITIVE_INFINITY);
    public final static Vector4 NEGATIVE_INFINITY = new Vector4(
            Float.NEGATIVE_INFINITY,
            Float.NEGATIVE_INFINITY,
            Float.NEGATIVE_INFINITY,
            Float.NEGATIVE_INFINITY);

    public Vector4() {
        this(0);
    }

    public Vector4(float s) {
        super(s);
    }

    public Vector4(Vector2 v, float z, float w) {
        super(v.getX(), v.getY(), z, w);
    }

    public Vector4(Vector2 v, float z) {
        this(v, z, 1);
    }

    public Vector4(Vector2 v) {
        this(v, 0, 1);
    }

    public Vector4(Vector3 v, float w) {
        super(v.getX(), v.getY(), v.getZ(), w);
    }

    public Vector4(Vector3 v) {
        this(v, 1f);
    }

    public Vector4(float x, float y, float z, float w) {
        super(x, y, z, w);
    }

    @Override
    protected Vector4 build(float x, float y, float z, float w) {
        return new Vector4(x, y, z, w);
    }

    @Override
    protected Vector4 This() {
        return this;
    }
}
