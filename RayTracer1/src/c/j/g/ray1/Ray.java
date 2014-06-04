package c.j.g.ray1;

import lombok.Data;
import lombok.experimental.Builder;
import static c.j.g.ray1.Vec3.*;

@Data
@Builder
public class Ray {
    private final Vec3 origin, direction;
    public Vec3 getAt(double t){
    	return add(origin, mul(direction, t));
    }
}
