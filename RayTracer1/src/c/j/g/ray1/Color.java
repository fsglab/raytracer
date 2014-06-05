package c.j.g.ray1;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class Color {
	private final double r, g, b;

	public int getRGB() {
	   return 
            ((1 & 0xFF) << 24) |
            ((aI(r) & 0xFF) << 16) |
            ((aI(g) & 0xFF) << 8)  |
            ((aI(b) & 0xFF) << 0);
	}
	private int aI(double c){
	    return (int)(c * 255 + 0.f);
	}
}
