package c.j.g.ray2.light;

import c.j.g.ray2.Color;
import c.j.g.ray2.HitInfo;
import c.j.g.ray2.Vec3;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Builder;
import static c.j.g.ray2.Vec3.*;
import static c.j.g.ray2.Color.*;

@Data
@Builder
@AllArgsConstructor
public class PointLight implements Light {

    private final Vec3 origin;
    private final Color color;
    private final double intensity;

    @Override
    public Color getColor(HitInfo hi) {
	Vec3 surfaceToLight = sub(origin, hi.getGHitPoint());
	double brightness = cosAngle(hi.getGNormal(), surfaceToLight);
	if (brightness < 0)
	    return Color.BLACK;
	return mul(color, intensity * brightness);
    }
}
