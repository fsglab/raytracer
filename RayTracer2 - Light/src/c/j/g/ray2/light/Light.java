package c.j.g.ray2.light;

import c.j.g.ray2.Color;
import c.j.g.ray2.HitInfo;

public interface Light {

    Color getColor(HitInfo hi);

}
