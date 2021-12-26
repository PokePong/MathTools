package org.math.vector;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.math.Mathf;

import java.nio.FloatBuffer;

@EqualsAndHashCode
public abstract class Vector<T extends Vector<T>> {

    protected abstract float[] toArray();

    protected abstract T build(@NonNull float[] values);

    protected abstract T build(@NonNull float value);

    public abstract float angleBetween(T v);

    public final T add(@NonNull T v) {
        float[] a = toArray();
        float[] b = v.toArray();
        for (int i = 0; i < a.length; i++) {
            a[i] += b[i];
        }
        return build(a);
    }

    public final T add(float s) {
        float[] a = toArray();
        for (int i = 0; i < a.length; ++i) {
            a[i] += s;
        }
        return build(a);
    }

    public final T sub(float s) {
        return add(-s);
    }

    public final T sub(@NonNull T v) {
        return add(v.negate());
    }

    public final T mul(float scalar) {
        float[] a = toArray();
        for (int i = 0; i < a.length; ++i) {
            a[i] *= scalar;
        }
        return build(a);
    }

    /**
     * Not marked final as quaternions have a different
     * idea of the inverse
     */
    public T mul(@NonNull T v) {
        float[] a = toArray();
        float[] b = v.toArray();
        for (int i = 0; i < a.length; ++i) {
            a[i] *= b[i];
        }
        return build(a);
    }

    /**
     * Not marked final as quaternions have a different
     * idea of the inverse
     */
    public T inverse() {
        float[] a = toArray();
        for (int i = 0; i < a.length; ++i) {
            a[i] = 1f / a[i];
        }
        return build(a);
    }

    public final float dot(@NonNull T v) {
        float[] a = toArray();
        float[] b = v.toArray();
        float result = 0;
        for (int i = 0; i < a.length; ++i) {
            result += a[i] * b[i];
        }
        return result;
    }

    public final T scale(float scalar) {
        return mul(scalar);
    }

    public final T divide(float scalar) {
        return mul(1f / scalar);
    }

    public final T negate() {
        return mul(-1);
    }

    public final T normalize() {
        float lengthSquared = lengthSquared();
        if (lengthSquared == 0 && Mathf.approximately(lengthSquared, 1)) {
            return build(toArray());
        }
        return divide(length());
    }

    public final boolean isUnitVector() {
        return Mathf.approximately(lengthSquared(), 1);
    }

    public final float lengthSquared() {
        float[] a = toArray();
        float res = 0;
        for (float i : a) {
            res += i * i;
        }
        return res;
    }

    public final float length() {
        return Mathf.sqrt(lengthSquared());
    }

    public final boolean isValid() {
        for (float f : toArray()) {
            if (Float.isNaN(f) || Float.isInfinite(f)) {
                return false;
            }
        }
        return true;
    }

    public final float distanceSquared(@NonNull T v) {
        float[] a = toArray();
        float[] b = v.toArray();
        float result = 0;
        for (int i = 0; i < a.length; i++) {
            float f = a[i] - b[i];
            result += f * f;
        }
        return result;
    }

    public final float distance(@NonNull T v) {
        return Mathf.sqrt(distanceSquared(v));
    }

    public final T lerp(@NonNull T v, float changeAmnt) {
        float[] a = toArray();
        float[] b = v.toArray();
        for (int i = 0; i < a.length; ++i) {
            a[i] = Mathf.interpolateLinear(changeAmnt, a[i], b[i]);
        }
        return build(a);
    }

    public final boolean equalsApproximately(@NonNull T v) {
        float[] a = toArray();
        float[] b = v.toArray();
        for (int i = 0; i < a.length; ++i) {
            if (!Mathf.approximately(a[i], b[i])) {
                return false;
            }
        }
        return true;
    }

    public final T minLocal(@NonNull T v) {
        float[] a = toArray();
        float[] b = v.toArray();
        for (int i = 0; i < a.length; ++i) {
            a[i] = Math.min(a[i], b[i]);
        }
        return build(a);
    }

    public final T maxLocal(@NonNull T v) {
        float[] a = toArray();
        float[] b = v.toArray();
        for (int i = 0; i < a.length; ++i) {
            a[i] = Math.max(a[i], b[i]);
        }
        return build(a);
    }

    public final T project(@NonNull T other) {
        float n = this.dot(other); // A . B
        float d = other.lengthSquared(); // |B|^2
        return other.normalize().mul(n / d);
    }

    public final void fillFloatBuffer(@NonNull FloatBuffer buffer) {
        buffer.put(toArray());
    }

}
