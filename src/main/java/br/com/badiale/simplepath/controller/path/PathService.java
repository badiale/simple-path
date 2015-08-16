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
    private static final Long INFINITY = Long.MAX_VALUE;

    /**
     * Implementação de caminho mais curto, utilizando algoritmo de Dijkstra.
     *
     * @see <a href="https://en.wikipedia.org/wiki/Dijkstra's_algorithm">Dijkstra's algorithm</a>
     */
    // FIXME deveria retornar o caminho, além da distância.
    public Long shorthestPath(LogisticPoint from, LogisticPoint to) {
        Set<LogisticPoint> reachablePoints = from.getReachablePoints();

        if (!reachablePoints.contains(to)) {
            throw new IllegalStateException("no path found");
        }

        Map<LogisticPoint, Long> distances = Maps.newHashMap();
        Set<LogisticPoint> visitedPoints = Sets.newHashSet();

        initializeDistances(from, reachablePoints, distances);
        while (!visitedPoints.equals(reachablePoints)) {
            LogisticPoint currentVisiting = getClosestUnvisitedNode(reachablePoints, distances, visitedPoints);
            visitedPoints.add(currentVisiting);
            for (LogisticArch arch : currentVisiting.getSiblings()) {
                distances.put(arch.getTo(), minimalDistanceToPointFrom(arch, distances));
            }
        }

        return distances.get(to);
    }

    private static void initializeDistances(LogisticPoint from, Set<LogisticPoint> reachablePoints, Map<LogisticPoint, Long> distances) {
        for (LogisticPoint point : reachablePoints) {
            distances.put(point, INFINITY);
        }
        distances.put(from, 0l);
    }

    private static LogisticPoint getClosestUnvisitedNode(Set<LogisticPoint> reachablePoints, Map<LogisticPoint, Long> distances, Set<LogisticPoint> visitedPoints) {
        LogisticPoint result = null;
        Long minDistance = INFINITY;
        for (LogisticPoint arch : Sets.difference(reachablePoints, visitedPoints)) {
            Long distance = distances.get(arch);
            if (distance < minDistance) {
                minDistance = distance;
                result = arch;
            }
        }
        return result;
    }

    private static long minimalDistanceToPointFrom(LogisticArch arch, Map<LogisticPoint, Long> distances) {
        LogisticPoint from = arch.getFrom();
        Long totalDistance = distances.get(arch.getTo());
        Long distanceUsingArch = distances.get(from) + arch.getDistance();
        return Math.min(totalDistance, distanceUsingArch);
    }
}
