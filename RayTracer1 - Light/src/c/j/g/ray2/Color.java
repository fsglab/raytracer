package c.j.g.ray2;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class Color {
    private final double r, g, b;

    public static final Color BLACK = new Color(0, 0, 0);

    public static Color mul(Color a, Color b) {
	return new Color(a.r * b.r, a.g * b.g, a.b * b.b);
    }

    public static Color mul(Color a, double d) {
	return new Color(a.r * d, a.g * d, a.b * d);
    }

    public Color clamp() {
	ColorBuilder cb = Color.builder();
	cb.r = clamp(r, 0, 1);
	cb.g = clamp(g, 0, 1);
	cb.b = clamp(b, 0, 1);
	return cb.build();
    }

    private double clamp(double r, double min, double max) {
return Math.min(max, Math.max(min, r));
    }

    public static Color add(Color a, Color b) {
	ColorBuilder bb = Color.builder();
	bb.r = Math.min(a.r + b.r, 1);
	bb.g = Math.min(a.g + b.g, 1);
	bb.b = Math.min(a.b + b.b, 1);
	return bb.build();
    }

    public int getRGB() {
	return ((1 & 0xFF) << 24) | ((aI(r) & 0xFF) << 16)
		| ((aI(g) & 0xFF) << 8) | ((aI(b) & 0xFF) << 0);
    }

    private int aI(double c) {
	return (int) (c * 255 + 0.f);
    }
}
