package org.math;

import org.math.vector.Vector2;
import org.math.vector.Vector3;

import java.util.Arrays;
import java.util.Random;

public class Mathf {

    public static final float ZERO_TOLERANCE = 0.0001f;
    public static final float ONE_THIRD = 1f / 3f;

    public static final float PI = (float) Math.PI;
    public static final float TWO_PI = 2.0f * PI;
    public static final float HALF_PI = 0.5f * PI;
    public static final float QUARTER_PI = 0.25f * PI;
    public static final float INV_PI = 1.0f / PI;
    public static final float INV_TWO_PI = 1.0f / TWO_PI;
    public static final float DEG_TO_RAD = PI / 180.0f;
    public static final float RAD_TO_DEG = 180.0f / PI;

    public static final Random rand = new Random(System.currentTimeMillis());

    public static boolean isWithinEpsilon(float a, float b, float epsilon) {
        return Math.abs(a - b) <= epsilon;
    }

    public static boolean approximately(float a, float b) {
        return isWithinEpsilon(a, b, ZERO_TOLERANCE);
    }

    public static boolean isPowerOfTwo(int number) {
        return (number > 0) && (number & (number - 1)) == 0;
    }

    public static int nearestPowerOfTwo(int number) {
        if (number <= 0) {
            return 2;
        }
        return (int) pow(2, ceil(log(number, 2)));
    }

    public static int nearestPowerOfTen(int number) {
        if (number <= 0)
            return 1;
        return (int) pow(10, ceil(log(number, 10)));
    }

    public static float interpolateLinear(float scale, float startValue, float endValue) {
        if (startValue == endValue) {
            return startValue;
        }
        if (scale <= 0f) {
            return startValue;
        }
        if (scale >= 1f) {
            return endValue;
        }
        float res = ((1f - scale) * startValue) + (scale * endValue);
        return approximately(res, endValue) ? endValue : res;
    }

    public static float extrapolateLinear(float scale, float startValue, float endValue) {
        return ((1f - scale) * startValue) + (scale * endValue);
    }

    public static float sin(float x) {
        return (float) Math.sin(x);
    }

    public static float cos(float x) {
        return (float) Math.cos(x);
    }

    public static float tan(float x) {
        return (float) Math.tan(x);
    }

    public static float asin(float x) {
        if (-1.0f < x) {
            if (x < 1.0f) {
                return (float) Math.asin(x);
            }
            return HALF_PI;
        }
        return -HALF_PI;
    }

    public static float acos(float x) {
        if (-1.0f < x) {
            if (x < 1.0f) {
                return (float) Math.acos(x);
            }
            return 0.0f;
        }
        return PI;
    }

    public static float atan(float x) {
        return (float) Math.atan(x);
    }

    public static float atan2(float y, float x) {
        return (float) Math.atan2(y, x);
    }

    public static float abs(float x) {
        return Math.abs(x);
    }

    public static float min(float a, float b) {
        return Math.min(a, b);
    }

    public static float min(float[] values) {
        int len = values.length;
        if (len == 0) {
            throw new IllegalArgumentException("Argument array is empty");
        }
        float[] temp = values.clone();
        Arrays.sort(temp);
        return temp[0];
    }

    public static float max(float a, float b) {
        return Math.max(a, b);
    }

    public static float max(float[] values) {
        int len = values.length;
        if (len == 0) {
            throw new IllegalArgumentException("Argument array is empty");
        }
        float[] temp = values.clone();
        Arrays.sort(temp);
        return temp[len - 1];
    }

    public static float ceil(float fValue) {
        return (float) Math.ceil(fValue);
    }

    public static float exp(float fValue) {
        return (float) Math.exp(fValue);
    }

    public static float floor(float fValue) {
        return (float) Math.floor(fValue);
    }

    public static float invSqrt(float fValue) {
        return (float) (1.0f / Math.sqrt(fValue));
    }

    public static float fastInvSqrt(float x) {
        float xhalf = 0.5f * x;
        int i = Float.floatToIntBits(x); // get bits for floating value
        i = 0x5f375a86 - (i >> 1); // gives initial guess y0
        x = Float.intBitsToFloat(i); // convert bits back to float
        x = x * (1.5f - xhalf * x * x); // Newton step, repeating increases accuracy
        return x;
    }

    public static float log(float fValue) {
        return (float) Math.log(fValue);
    }

    public static float log(float value, float base) {
        return (float) (Math.log(value) / Math.log(base));
    }

    public static float pow(float fBase, float fExponent) {
        return (float) Math.pow(fBase, fExponent);
    }

    public static float sqr(float fValue) {
        return fValue * fValue;
    }

    public static float sqrt(float fValue) {
        return (float) Math.sqrt(fValue);
    }

    public static int sign(int iValue) {
        return iValue >= 0 ? 1 : -1;
    }

    public static float sign(float fValue) {
        return fValue >= 0 ? 1f : -1f;
    }

    public static float clamp(float x, float min, float max) {
        return (x < min) ? min : Math.min(x, max);
    }

    public static float saturate(float x) {
        return clamp(x, 0f, 1f);
    }

    public static float lerp(float a, float b, float t) {
        return a + (b - a) * saturate(t);
    }

    public static float lerpAngle(float a, float b, float t) {
        float delta = repeat((b - a), 360);
        if (delta > 180)
            delta -= 360;
        return a + delta * saturate(t);
    }

    public static float moveTowards(float current, float target, float maxDelta) {
        if (abs(target - current) <= maxDelta)
            return target;
        return current + sign(target - current) * maxDelta;
    }

    public static float repeat(float t, float length) {
        return clamp(t - floor(t / length) * length, 0.0f, length);
    }

    public static float pingPong(float t, float length) {
        t = repeat(t, length * 2f);
        return length - abs(t - length);
    }

    public static float invLerp(float a, float b, float value) {
        return a != b ? saturate((value - a) / (b - a)) : 0f;
    }

    public static float copySign(float x, float y) {
        if (y >= 0 && x <= -0) {
            return -x;
        } else if (y < 0 && x >= 0) {
            return -x;
        } else {
            return x;
        }
    }

    public static float normalize(float val, float min, float max) {
        if (Float.isInfinite(val) || Float.isNaN(val)) {
            return 0f;
        }
        float range = max - min;
        while (val > max) {
            val -= range;
        }
        while (val < min) {
            val += range;
        }
        return val;
    }

    public static float nextRandomFloat() {
        return rand.nextFloat();
    }

    public static int nextRandomInt(int min, int max) {
        return (int) (nextRandomFloat() * (max - min + 1)) + min;
    }

    public static int nextRandomInt() {
        return rand.nextInt();
    }

    public static float smoothStep(float from, float to, float t) {
        t = saturate(t);
        t = -2.0F * t * t * t + 3.0F * t * t;
        return to * t + from * (1F - t);
    }

    public static float gamma(float value, float absmax, float gamma) {
        boolean negative = value < 0F;
        float absval = abs(value);
        if (absval > absmax)
            return negative ? -absval : absval;

        float result = pow(absval / absmax, gamma) * absmax;
        return negative ? -result : result;
    }

    /**
     * Given 3 points in a 2d plane, this function computes if the points going from A-B-C
     * are moving counter clock wise.
     *
     * @param p0 Point 0.
     * @param p1 Point 1.
     * @param p2 Point 2.
     * @return 1 If they are CCW, -1 if they are not CCW, 0 if p2 is between p0 and p1.
     */
    public static int counterClockwise(Vector2 p0, Vector2 p1, Vector2 p2) {
        float dx1, dx2, dy1, dy2;
        dx1 = p1.x - p0.x;
        dy1 = p1.y - p0.y;
        dx2 = p2.x - p0.x;
        dy2 = p2.y - p0.y;
        if (dx1 * dy2 > dy1 * dx2) {
            return 1;
        }
        if (dx1 * dy2 < dy1 * dx2) {
            return -1;
        }
        if ((dx1 * dx2 < 0) || (dy1 * dy2 < 0)) {
            return -1;
        }
        if ((dx1 * dx1 + dy1 * dy1) < (dx2 * dx2 + dy2 * dy2)) {
            return 1;
        }
        return 0;
    }

    /**
     * Test if a point is inside a triangle.  1 if the point is on the ccw side,
     * -1 if the point is on the cw side, and 0 if it is on neither.
     *
     * @param t0 First point of the triangle.
     * @param t1 Second point of the triangle.
     * @param t2 Third point of the triangle.
     * @param p  The point to test.
     * @return Value 1 or -1 if inside triangle, 0 otherwise.
     */
    public static int pointInsideTriangle(Vector2 t0, Vector2 t1, Vector2 t2, Vector2 p) {
        int val1 = counterClockwise(t0, t1, p);
        if (val1 == 0) {
            return 1;
        }
        int val2 = counterClockwise(t1, t2, p);
        if (val2 == 0) {
            return 1;
        }
        if (val2 != val1) {
            return 0;
        }
        int val3 = counterClockwise(t2, t0, p);
        if (val3 == 0) {
            return 1;
        }
        if (val3 != val1) {
            return 0;
        }
        return val3;
    }

    /**
     * A method that computes normal for a triangle defined by three vertices.
     *
     * @param v1 first vertex
     * @param v2 second vertex
     * @param v3 third vertex
     * @return a normal for the face
     */
    public static Vector3 computeNormal(Vector3 v1, Vector3 v2, Vector3 v3) {
        Vector3 a1 = v1.sub(v2);
        Vector3 a2 = v3.sub(v2);
        return a2.cross(a1).normalize();
    }

    /**
     * Converts a point from Spherical coordinates to Cartesian (using positive
     * Y as up) and stores the results in the store var.
     *
     * @param sphereCoords the input spherical coordinates: x=distance from
     *                     origin, y=longitude in radians, z=latitude in radians (not null,
     *                     unaffected)
     * @return the Cartesian coordinates (a new vector)
     */
    public static Vector3 sphericalToCartesian(Vector3 sphereCoords) {
        Vector3 res = new Vector3();
        res.y = sphereCoords.x * Mathf.sin(sphereCoords.z);
        float a = sphereCoords.x * Mathf.cos(sphereCoords.z);
        res.x = a * Mathf.cos(sphereCoords.y);
        res.z = a * Mathf.sin(sphereCoords.y);
        return res;
    }

    /**
     * Converts a point from Cartesian coordinates (using positive Y as up) to
     * Spherical and stores the results in the store var. (Radius, Azimuth,
     * Polar)
     *
     * @param cartCoords the input Cartesian coordinates (not null, unaffected)
     * @return the Cartesian coordinates: x=distance from origin, y=longitude in
     * radians, z=latitude in radians (a new vector)
     */
    public static Vector3 cartesianToSpherical(Vector3 cartCoords) {
        Vector3 res = new Vector3();
        float x = cartCoords.x;
        if (x == 0) {
            x = Mathf.ZERO_TOLERANCE;
        }
        res.x = Mathf.sqrt((x * x)
                + (cartCoords.y * cartCoords.y)
                + (cartCoords.z * cartCoords.z));
        res.y = Mathf.atan(cartCoords.z / x);
        if (x < 0) {
            res.y += Mathf.PI;
        }
        res.z = Mathf.asin(cartCoords.y / res.x);
        return res;
    }

}
