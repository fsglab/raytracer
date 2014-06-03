package c.j.g.ray1;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class Sphere {
    private final Color color;
    private final Vec3 origin;
    private final double radius;
}
