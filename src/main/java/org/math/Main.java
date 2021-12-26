package org.math;

import org.math.vector.Vector2;

public class Main {

    public static void main(String[] args) {
        Vector2 a = new Vector2(5);
        Vector2 b = new Vector2(5);

        print(a);
        print(b);

        print(a);
        print(b);

    }

    private static void print(Object o) {
        System.out.println(o);
    }

}
