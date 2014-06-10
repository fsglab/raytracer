package c.j.g.ray2.light;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Builder;
import c.j.g.ray2.Color;
import c.j.g.ray2.HitInfo;

@Data
@Builder
@AllArgsConstructor
public class AmbientLighting implements Light{

    private final Color color;
    
    @Override
    public Color getColor(HitInfo hi) {
	return Color.mul(color, hi.getGeo().getColor());
    }
    
}
