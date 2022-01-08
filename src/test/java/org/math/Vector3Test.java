package org.math;

import org.junit.jupiter.api.Test;
import org.math.vector.Vector3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Vector3Test {

    @Test
    public void testToArray() {

    }

    @Test
    public void testBuildValues() {

    }

    @Test
    public void testBuildValue() {

    }

    @Test
    public void testAngleBetween() {

    }

    @Test
    public void testAddVector() {
        Vector3 a = new Vector3(0.0f, Float.NaN, 5.21f);
        Vector3 b = new Vector3(-1.0f, 0.0f, -5.21f);
        Vector3 res = a.add(b);

        assertNotNull(res);
        assertEquals(res.x, -1.0f);
        assertEquals(res.y, Float.NaN);
        assertEquals(res.z, 0.0f);
    }

    @Test
    public void testAddValue() {
        Vector3 a = new Vector3(0.0f, Float.NaN, 5.21f);
        Vector3 res = a.add(-5.21f);

        assertNotNull(res);
        assertEquals(res.x, -5.21f);
        assertEquals(res.y, Float.NaN);
        assertEquals(res.z, 0.0f);
    }

    @Test
    public void testSubVector() {

    }

    @Test
    public void testSubValue() {

    }

    @Test
    public void testMulValue() {

    }

    @Test
    public void testMulVector() {

    }

    @Test
    public void tesDivideValue() {

    }

    @Test
    public void testDivideVector() {

    }

    @Test
    public void testInverse() {

    }

    @Test
    public void testDot() {

    }

    @Test
    public void testNegate() {

    }

    @Test
    public void testNormalize() {

    }

    @Test
    public void testIsUnitVector() {

    }

    @Test
    public void testLengthSquared() {

    }

    @Test
    public void testLength() {

    }

    @Test
    public void testIsValid() {

    }

    @Test
    public void testDistanceSquared() {

    }

    @Test
    public void testDistance() {

    }

    @Test
    public void testLerp() {

    }

    @Test
    public void testEqualsApproximately() {

    }

    @Test
    public void testMinLocal() {

    }

    @Test
    public void testMaxLocal() {

    }

    @Test
    public void testProject() {

    }

    @Test
    public void testFillFloatBuffer() {

    }

    @Test
    public void testAddValues() {

    }

    @Test
    public void testSubValues() {

    }

    @Test
    public void testMulValues() {

    }

    @Test
    public void testDivideValues() {

    }

    @Test
    public void testCross() {

    }

    @Test
    public void testSet() {

    }

    @Test
    public void testProjectOnPlane() {

    }

    @Test
    public void testGenerateOrthonormalBasis() {

    }

    @Test
    public void testGenerateComplementBasis() {

    }


}
