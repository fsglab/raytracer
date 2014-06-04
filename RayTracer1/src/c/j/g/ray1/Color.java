package c.j.g.ray1;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class Color {
	private final double r, g, b;

	public java.awt.Color getAsAWTColor() {
		return new java.awt.Color((float) r, (float) g, (float) b);
	}

	public int getRGB() {
		return getAsAWTColor().getRGB();
	}
}
