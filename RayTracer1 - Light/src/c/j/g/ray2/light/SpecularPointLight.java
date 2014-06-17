package c.j.g.ray2.light;

import static c.j.g.ray2.Vec3.*;
import static c.j.g.ray2.Color.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Builder;
import c.j.g.ray2.Color;
import c.j.g.ray2.HitInfo;
import c.j.g.ray2.Vec3;

/*
 * Phong
 */

@Data
@Builder
@AllArgsConstructor
public class SpecularPointLight implements Light {

	private final Vec3 origin;
	private final Color color;
	private final double intensity;

	@Override
	public Color getColor(HitInfo hi) {

		double i = intensity;
		i *= 1 / lenSqu(sub(hi.getGHitPoint(), origin));
		if (i <= 0)
			return Color.BLACK;

		Vec3 lightDir = nor(sub(hi.getGHitPoint(), origin));
		Vec3 surfaceToLight = mul(lightDir, -1);
		double brightness = i * cosAngle(hi.getGNormal(), surfaceToLight);
		if (brightness < 0)
			return Color.BLACK;

		Vec3 r = reflect(surfaceToLight, hi.getGNormal());
		double dot = dot(r, mul(hi.getRay().getDirection(), -1));
		if (dot < 0)
			return Color.BLACK;
		Color light = mul(hi.getGeo().getSpecColor(), color);
		ColorBuilder b = Color.builder();
		b.r(Math.pow(light.getR() * dot, hi.getGeo().getE()));
		b.g(Math.pow(light.getG() * dot, hi.getGeo().getE()));
		b.b(Math.pow(light.getB() * dot, hi.getGeo().getE()));
		return mul(b.build().clamp(), brightness);
	}

}
