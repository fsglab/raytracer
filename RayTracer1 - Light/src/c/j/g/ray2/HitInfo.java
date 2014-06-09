package c.j.g.ray2;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class HitInfo {

	private final Vec3 gHitPoint, gNormal;
	private final double distance;
	private final Sphere geo;
	private final Ray ray;
	
}
