package org.math.vector;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.math.Mathf;

@EqualsAndHashCode(callSuper = false)
@ToString
public abstract class AVector4<T extends AVector4<T>> extends Vector<T> {

    @Getter
    @Setter
    public float x;

    @Getter
    @Setter
    public float y;

    @Getter
    @Setter
    public float z;

    @Getter
    @Setter
    public float w;

    public AVector4(float s) {
        this(s, s, s, s);
    }

    public AVector4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    @Override
    protected float[] toArray() {
        return new float[]{x, y, z, w};
    }

    @Override
    public float angleBetween(T v) {
        float dotProduct = dot(v);
        return Mathf.acos(dotProduct);
    }
}
