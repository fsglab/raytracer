package c.j.g.ray1;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class HitInfo {

	private final Vec3 gHitPoint, gHitNormal;
	private final double distance;
	private final Sphere geo;
	
}
