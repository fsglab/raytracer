package c.j.g.ray1;

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
