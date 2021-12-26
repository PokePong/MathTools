package org.math.vector;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.math.Mathf;
import org.math.Matrix4;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
@ToString
public class Quaternion extends AVector4<Quaternion> implements Serializable {

    public static final Quaternion IDENTITY = new Quaternion(0f, 0f, 0f, 1f);
    public static final Quaternion ZERO = new Quaternion(0f, 0f, 0f, 0f);

    public Quaternion() {
        this(0, 0, 0, 1);
    }

    public Quaternion(float s) {
        super(s);
    }

    public Quaternion(float x, float y, float z, float w) {
        super(x, y, z, w);
    }

    public Quaternion(Quaternion q) {
        super(q.getX(), q.getY(), q.getZ(), q.getW());
    }

    private Quaternion(QuaternionTemp q) {
        super(q.x, q.y, q.z, q.w);
    }

    public boolean isIdentity() {
        return equalsApproximately(IDENTITY);
    }

    public Quaternion set(float x, float y, float z, float w) {
        setX(x);
        setY(y);
        setZ(z);
        setW(w);
        return this;
    }

    @Override
    public Quaternion mul(Quaternion q) {
        QuaternionTemp res = new QuaternionTemp();
        res.x = x * q.w + y * q.z - z * q.y + w * q.x;
        res.y = -x * q.z + y * q.w + z * q.x + w * q.y;
        res.z = x * q.y - y * q.x + z * q.w + w * q.z;
        res.w = -x * q.x - y * q.y - z * q.z + w * q.w;
        return new Quaternion(res);
    }

    @Override
    public Quaternion inverse() {
        float norm = norm();
        if (norm > 0.0f) {
            float invNorm = 1.0f / norm;
            return new Quaternion(-x * invNorm, -y * invNorm, -z * invNorm, w
                    * invNorm);
        }
        // return an invalid result to flag the error
        return null;
    }

    public Vector3 mul(Vector3 v) {
        float tempX = w * w * v.x + 2 * y * w * v.z - 2 * z * w * v.y + x * x * v.x
                + 2 * y * x * v.y + 2 * z * x * v.z - z * z * v.x - y * y * v.x;
        float tempY = 2 * x * y * v.x + y * y * v.y + 2 * z * y * v.z + 2 * w * z
                * v.x - z * z * v.y + w * w * v.y - 2 * x * w * v.z - x * x * v.y;
        float tempZ = 2 * x * z * v.x + 2 * y * z * v.y + z * z * v.z - 2 * w * y
                * v.x - y * y * v.z + 2 * w * x * v.y - x * x * v.z + w * w * v.z;
        return new Vector3(tempX, tempY, tempZ);
    }

    public float norm() {
        return dot(this);
    }

    @Override
    protected Quaternion build(@NonNull float[] values) {
        return new Quaternion(values[0], values[1], values[2], values[3]);
    }

    @Override
    protected Quaternion build(float value) {
        return new Quaternion(value);
    }

    /**
     * <code>toRotationMatrix</code> converts this quaternion to a rotational
     * matrix. The result is stored in result. 4th row and 4th column values are
     * untouched. Note: the result is created from a normalized version of this
     * quat.
     *
     * @return the rotation matrix representation of this quaternion.
     */
    public Matrix4 toRotationMatrix4() {
        float norm = norm();
        // we explicitly test norm against one here, saving a division
        // at the cost of a test and branch. Is it worth it?
        float s = (norm == 1f) ? 2f : (norm > 0f) ? 2f / norm : 0;

        // compute xs/ys/zs first to save 6 multiplications, since xs/ys/zs
        // will be used 2-4 times each.
        float xs = x * s;
        float ys = y * s;
        float zs = z * s;
        float xx = x * xs;
        float xy = x * ys;
        float xz = x * zs;
        float xw = w * xs;
        float yy = y * ys;
        float yz = y * zs;
        float yw = w * ys;
        float zz = z * zs;
        float zw = w * zs;

        // using s=2/norm (instead of 1/norm) saves 9 multiplications by 2 here
        return new Matrix4( //
                1 - (yy + zz), (xy - zw), (xz + yw), 0, //
                (xy + zw), 1 - (xx + zz), (yz - xw), 0, //
                (xz - yw), (yz + xw), 1 - (xx + yy), 0, //
                0, 0, 0, 1);
    }

    /**
     * Get the pole of the gimbal lock, if any.
     *
     * @return positive (+1) for north pole, negative (-1) for south pole, zero (0) when no gimbal lock
     */
    public int getGimbalPole() {
        final float t = y * x + z * w;
        return t > 0.499f ? 1 : (t < -0.499f ? -1 : 0);
    }

    public float getRollRad() {
        final int pole = getGimbalPole();
        return pole == 0 ? Mathf.aTan2(2f * (w * z + y * x), 1f - 2f * (x * x + z * z))
                : (float) pole * 2f * Mathf.aTan2(y, w);
    }

    public float getRoll() {
        return getRollRad() * Mathf.RAD_TO_DEG;
    }

    public float getPitchRad() {
        final int pole = getGimbalPole();
        return pole == 0 ? (float) Math.asin(Mathf.clamp(2f * (w * x - z * y), -1f, 1f)) : (float) pole * Mathf.PI * 0.5f;
    }

    public float getPitch() {
        return getPitchRad() * Mathf.RAD_TO_DEG;
    }

    public float getYawRad() {
        return getGimbalPole() == 0 ? Mathf.aTan2(2f * (y * w + x * z), 1f - 2f * (y * y + x * x)) : 0f;
    }

    public float getYaw() {
        return getYawRad() * Mathf.RAD_TO_DEG;
    }

    /**
     * <code>toAngles</code> returns this quaternion converted to Euler rotation
     * angles (yaw,roll,pitch).<br/>
     *
     * @param angles the float[] in which the angles should be stored, or null if you
     *               want a new float[] to be created
     * @return the float[] in which the angles are stored.
     * @see <a
     * href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToEuler/index.htm">http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToEuler/index.htm</a>
     */
    public float[] toAngles(float[] angles) {
        if (angles == null) {
            angles = new float[3];
        } else if (angles.length != 3) {
            throw new IllegalArgumentException(
                    "Angles array must have three elements");
        }

        float sqw = w * w;
        float sqx = x * x;
        float sqy = y * y;
        float sqz = z * z;
        float unit = sqx + sqy + sqz + sqw; // if normalized is one, otherwise
        // is correction factor
        float test = x * y + z * w;
        if (test > 0.499 * unit) { // singularity at north pole
            angles[1] = 2 * Mathf.aTan2(x, w);
            angles[2] = Mathf.HALF_PI;
            angles[0] = 0;
        } else if (test < -0.499 * unit) { // singularity at south pole
            angles[1] = -2 * Mathf.aTan2(x, w);
            angles[2] = -Mathf.HALF_PI;
            angles[0] = 0;
        } else {
            angles[1] = Mathf.aTan2(2 * y * w - 2 * x * z, sqx - sqy - sqz + sqw); // roll
            // or
            // heading
            angles[2] = Mathf.aSin(2 * test / unit); // pitch or attitude
            angles[0] = Mathf.aTan2(2 * x * w - 2 * y * z, -sqx + sqy - sqz + sqw); // yaw
            // or
            // bank
        }
        return angles;
    }

    /**
     * <code>getRotationColumn</code> returns one of three columns specified by
     * the parameter. This column is returned as a <code>Vector3f</code> object.
     * The value is retrieved as if this quaternion was first normalized.
     *
     * @param i the column to retrieve. Must be between 0 and 2.
     * @return the column specified by the index.
     */
    public Vector3 getRotationColumn(int i) {
        float norm = norm();
        if (norm != 1.0f) {
            norm = Mathf.invSqrt(norm);
        }

        float xx = x * x * norm;
        float xy = x * y * norm;
        float xz = x * z * norm;
        float xw = x * w * norm;
        float yy = y * y * norm;
        float yz = y * z * norm;
        float yw = y * w * norm;
        float zz = z * z * norm;
        float zw = z * w * norm;
        float rx, ry, rz;

        switch (i) {
            case 0:
                rx = 1 - 2 * (yy + zz);
                ry = 2 * (xy + zw);
                rz = 2 * (xz - yw);
                break;
            case 1:
                rx = 2 * (xy - zw);
                ry = 1 - 2 * (xx + zz);
                rz = 2 * (yz + xw);
                break;
            case 2:
                rx = 2 * (xz + yw);
                ry = 2 * (yz - xw);
                rz = 1 - 2 * (xx + yy);
                break;
            default:
                throw new IllegalArgumentException("Invalid column index. " + i);
        }

        return new Vector3(rx, ry, rz);
    }

    /**
     * Sets the values of this quaternion to the slerp from itself to q2 by
     * changeAmnt
     *
     * @param q2         Final interpolation value
     * @param changeAmnt The amount diffrence
     */
    public Quaternion slerp(Quaternion q2, float changeAmnt) {
        if (this.x == q2.x && this.y == q2.y && this.z == q2.z && this.w == q2.w) {
            return this;
        }

        float result = (this.x * q2.x) + (this.y * q2.y) + (this.z * q2.z)
                + (this.w * q2.w);
        if (result < 0.0f) {
            // Negate the second quaternion and the result of the dot product
            q2 = q2.negate();
            result = -result;
        }

        // Set the first and second scale for the interpolation
        float scale0 = 1 - changeAmnt;
        float scale1 = changeAmnt;

        // Check if the angle between the 2 quaternions was big enough to
        // warrant such calculations
        if ((1 - result) > 0.1f) {
            // Get the angle between the 2 quaternions, and then store the sin()
            // of that angle
            float theta = Mathf.aCos(result);
            float invSinTheta = 1f / Mathf.sin(theta);

            // Calculate the scale for q1 and q2, according to the angle and
            // it's sine value
            scale0 = Mathf.sin((1 - changeAmnt) * theta) * invSinTheta;
            scale1 = Mathf.sin((changeAmnt * theta)) * invSinTheta;
        }

        // Calculate the x, y, z and w values for the quaternion by using a
        // special
        // form of linear interpolation for quaternions.
        return new Quaternion(
                (scale0 * this.x) + (scale1 * q2.x),
                (scale0 * this.y) + (scale1 * q2.y),
                (scale0 * this.z) + (scale1 * q2.z),
                (scale0 * this.w) + (scale1 * q2.w));
    }

    /**
     * Sets the values of this quaternion to the nlerp from itself to q2 by blend.
     */
    public Quaternion nlerp(Quaternion q2, float blend) {
        float dot = dot(q2);
        float blendI = 1.0f - blend;
        QuaternionTemp q = new QuaternionTemp();
        if (dot < 0.0f) {
            q.x = blendI * x - blend * q2.x;
            q.y = blendI * y - blend * q2.y;
            q.z = blendI * z - blend * q2.z;
            q.w = blendI * w - blend * q2.w;
        } else {
            q.x = blendI * x + blend * q2.x;
            q.y = blendI * y + blend * q2.y;
            q.z = blendI * z + blend * q2.z;
            q.w = blendI * w + blend * q2.w;
        }
        return new Quaternion(q).normalize();
    }

    public float getAngleRad() {
        return (float) (2.0 * Math.acos((this.w > 1) ? (this.w / length()) : this.w));
    }

    /**
     * Get the angle in degrees of the rotation this quaternion represents. Use to get both the
     * axis and the angle of this rotation.
     *
     * @return the angle in degrees of the rotation
     */
    public float getAngle() {
        return getAngleRad() * Mathf.RAD_TO_DEG;
    }


    /**
     * <code>fromAngles</code> builds a Quaternion from the Euler rotation angles
     * (x,y,z) aka (pitch, yaw, rall)). Note that we are applying in order: (y, z,
     * x) aka (yaw, roll, pitch) but we've ordered them in x, y, and z for
     * convenience.
     *
     * @param xAngle the Euler pitch of rotation (in radians). (aka Attitude, often rot
     *               around x)
     * @param yAngle the Euler yaw of rotation (in radians). (aka Heading, often rot
     *               around y)
     * @param zAngle the Euler roll of rotation (in radians). (aka Bank, often rot
     *               around z)
     * @see <a
     * href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/eulerToQuaternion/index.htm">http://www.euclideanspace.com/maths/geometry/rotations/conversions/eulerToQuaternion/index.htm</a>
     */
    public static Quaternion fromEulerAngles(float xAngle, float yAngle, float zAngle) {
        float angle;
        float sinY, sinZ, sinX, cosY, cosZ, cosX;
        angle = zAngle * 0.5f;
        sinZ = Mathf.sin(angle);
        cosZ = Mathf.cos(angle);
        angle = yAngle * 0.5f;
        sinY = Mathf.sin(angle);
        cosY = Mathf.cos(angle);
        angle = xAngle * 0.5f;
        sinX = Mathf.sin(angle);
        cosX = Mathf.cos(angle);

        // variables used to reduce multiplication calls.
        float cosYXcosZ = cosY * cosZ;
        float sinYXsinZ = sinY * sinZ;
        float cosYXsinZ = cosY * sinZ;
        float sinYXcosZ = sinY * cosZ;

        QuaternionTemp temp = new QuaternionTemp();
        temp.w = (cosYXcosZ * cosX - sinYXsinZ * sinX);
        temp.x = (cosYXcosZ * sinX + sinYXsinZ * cosX);
        temp.y = (sinYXcosZ * cosX + cosYXsinZ * sinX);
        temp.z = (cosYXsinZ * cosX - sinYXcosZ * sinX);

        return new Quaternion(temp).normalize();
    }

    /**
     * <code>fromAngles</code> builds a Quaternion from the Euler rotation angles
     * (x,y,z) aka (pitch, yaw, rall)). Note that we are applying in order: (y, z,
     * x) aka (yaw, roll, pitch) but we've ordered them in x, y, and z for
     * convenience.
     *
     * @param pitch the Euler pitch of rotation (in degree). (aka Attitude, often rot
     *              around x)
     * @param yaw   the Euler yaw of rotation (in degree). (aka Heading, often rot
     *              around y)
     * @param roll  the Euler roll of rotation (in degree). (aka Bank, often rot
     *              around z)
     * @see <a
     * href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/eulerToQuaternion/index.htm">http://www.euclideanspace.com/maths/geometry/rotations/conversions/eulerToQuaternion/index.htm</a>
     */
    public static Quaternion euler(float pitch, float yaw, float roll) {
        float pr = pitch * Mathf.DEG_TO_RAD;
        float yr = yaw * Mathf.DEG_TO_RAD;
        float rr = roll * Mathf.DEG_TO_RAD;
        return fromEulerAngles(pr, yr, rr);
    }

    /**
     * <code>fromAngleAxis</code> sets this quaternion to the values specified by
     * an angle and an axis of rotation. This method creates an object, so use
     * fromAngleNormalAxis if your axis is already normalized.
     *
     * @param angle the angle to rotate (in degree).
     * @param axis  the axis of rotation.
     * @return this quaternion
     */
    public static Quaternion eulerAxis(float angle, Vector3 axis) {
        Vector3 normAxis = axis.normalize();
        return fromAngleNormalAxis(angle * Mathf.DEG_TO_RAD, normAxis);
    }

    /**
     * <code>fromAngleNormalAxis</code> sets this quaternion to the values
     * specified by an angle and a normalized axis of rotation.
     *
     * @param angle the angle to rotate (in radians).
     * @param axis  the axis of rotation (already normalized).
     */
    public static Quaternion fromAngleNormalAxis(float angle, Vector3 axis) {
        if (axis.x == 0 && axis.y == 0 && axis.z == 0) {
            return IDENTITY;
        }
        float halfAngle = 0.5f * angle;
        float sin = Mathf.sin(halfAngle);
        return new Quaternion(sin * axis.x, sin * axis.y, sin * axis.z, Mathf.cos(halfAngle));
    }

    /**
     * <code>slerp</code> sets this quaternion's value as an interpolation between
     * two other quaternions.
     *
     * @param q1 the first quaternion.
     * @param q2 the second quaternion.
     * @param t  the amount to interpolate between the two quaternions.
     */
    public static Quaternion slerp(Quaternion q1, Quaternion q2, float t) {
        return q1.slerp(q2, t);
    }

    private static class QuaternionTemp {
        float x, y, z, w;
    }

}
