package c.j.g.ray2;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class Vec3 {
    public static final Vec3 O = new Vec3(0, 0, 0);
    private final double x, y, z;

    public static double lenSqu(Vec3 v) {
	return v.x * v.x + v.y * v.y + v.z * v.z;
    }

    public static double len(Vec3 v) {
	return Math.sqrt(lenSqu(v));
    }

    public static double angle(Vec3 a, Vec3 b) {
	return Math.acos(cosAngle(a, b));
    }
    
    public static double cosAngle(Vec3 a, Vec3 b){
	return dot(a, b) / (len(a) * len(b));
    }

    public static Vec3 sub(Vec3 a, Vec3 b) {
	Vec3Builder v = Vec3.builder();
	v.x = a.x - b.x;
	v.y = a.y - b.y;
	v.z = a.z - b.z;
	return v.build();
    }

    public static Vec3 mul(Vec3 v, double t) {
	return new Vec3(v.x * t, v.y * t, v.z * t);
    }

    public static Vec3 add(Vec3 a, Vec3 b) {
	Vec3Builder v = Vec3.builder();
	v.x = a.x + b.x;
	v.y = a.y + b.y;
	v.z = a.z + b.z;
	return v.build();
    }

    public static double dot(Vec3 a, Vec3 b) {
	return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public static final Vec3 nor(Vec3 v) {
	double len = lenSqu(v);
	if (len == 0)
	    return v;
	len = Math.sqrt(len);
	Vec3Builder n = Vec3.builder();
	n.x = v.x / len;
	n.y = v.y / len;
	n.z = v.z / len;
	return n.build();
    }
}
