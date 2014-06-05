package c.j.g.ray1;

import c.j.g.ray1.HitInfo.HitInfoBuilder;
import lombok.Data;
import lombok.experimental.Builder;
import static c.j.g.ray1.Vec3.*;
import static java.lang.Math.*;

@Data
@Builder
public class Sphere {
	private final Color color;
	private final Vec3 origin;
	private final double radius;

	public HitInfo getNearestIntersection(Ray ray) {
		Vec3 d = ray.getDirection();
		Vec3 o = sub(ray.getOrigin(), origin);
		// coefficients
		double a = dot(d, d), b = 2 * dot(d, o), c = dot(o, o)
				- (radius * radius);
		// discriminant
		double dis = b * b - 4 * a * c;
		if (dis < 0)// No intersection
			return null;
		double t = (-b - sqrt(dis)) / (2 * a);// QE, we need just a - no +.
		
		Vec3 pos = ray.getAt(t);
		HitInfoBuilder hjb = HitInfo.builder();
		hjb.distance(t);
		hjb.gHitPoint(pos);
		hjb.geo(this);
		return hjb.build();
	}
}
