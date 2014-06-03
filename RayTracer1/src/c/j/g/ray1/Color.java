package c.j.g.ray1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
@AllArgsConstructor()
public class Color {
    private final double r, g, b;
}
