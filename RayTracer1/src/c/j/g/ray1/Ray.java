package c.j.g.ray1;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class Ray {
    private final Vec3 origin, direction;
}
