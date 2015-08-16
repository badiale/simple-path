package br.com.badiale.simplepath.controller.path;

import br.com.badiale.simplepath.model.LogisticArch;
import br.com.badiale.simplepath.model.LogisticPoint;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class PathService {
    /**
     * Implementação de caminho mais curto, utilizando algoritmo de Dijkstra.
     *
     * @see <a href="https://en.wikipedia.org/wiki/Dijkstra's_algorithm">Dijkstra's algorithm</a>
     */
    // FIXME melhorar implementação. Foi feita uma tradução literal de pseudocode para java.
    // FIXME deveria retornar o caminho, além da distância.
    public Long shorthestPath(LogisticPoint origem, LogisticPoint to) {
        Set<LogisticPoint> V = origem.getReachablePoints();

        if (!V.contains(to)) {
            throw new IllegalStateException("no path found");
        }

        Long INFINITY = Long.MAX_VALUE;
        Map<LogisticPoint, Long> D = Maps.newHashMap();
        LogisticPoint w;
        Set<LogisticPoint> S = Sets.newHashSet(origem);

        for (LogisticPoint point : V) {
            D.put(point, INFINITY);
        }
        D.put(origem, 0l);
        for (LogisticArch arch : origem.getSiblings()) {
            D.put(arch.getTo(), arch.getDistance());
        }
        while (!S.equals(V)) {
            Long minDistance = INFINITY;
            LogisticPoint sibling = null;
            for (LogisticPoint arch : Sets.difference(V, S)) {
                Long a = D.get(arch);
                if (a < minDistance) {
                    minDistance = a;
                    sibling = arch;
                }
            }
            if (sibling == null) {
                continue;
            }
            w = sibling;
            S.add(w);
            for (LogisticArch arch : w.getSiblings()) {
                Long a = D.get(arch.getTo());
                Long b = D.get(w) + arch.getDistance();
                D.put(arch.getTo(), Math.min(a, b));
            }
        }
        Long result = D.get(to);
        if (result.equals(INFINITY)) {
            throw new IllegalStateException("no path found");
        }
        return result;
    }
}
