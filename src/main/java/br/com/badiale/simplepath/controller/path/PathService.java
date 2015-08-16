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
    public Path shorthestPath(LogisticPoint from, LogisticPoint to) {
        Set<LogisticPoint> reachablePoints = from.getReachablePoints();

        if (!reachablePoints.contains(to)) {
            throw new IllegalStateException("no path found");
        }

        Map<LogisticPoint, Path> distances = Maps.newHashMap();
        Set<LogisticPoint> visitedPoints = Sets.newHashSet();

        initializeDistances(from, reachablePoints, distances);
        while (!visitedPoints.equals(reachablePoints)) {
            LogisticPoint currentVisiting = getClosestUnvisitedNode(reachablePoints, distances, visitedPoints);
            visitedPoints.add(currentVisiting);
            for (LogisticArch arch : currentVisiting.getSiblings()) {
                distances.put(arch.getTo(), minimalPathToPointFrom(arch, distances));
            }
        }

        return distances.get(to);
    }

    private static void initializeDistances(LogisticPoint from, Set<LogisticPoint> reachablePoints, Map<LogisticPoint, Path> distances) {
        for (LogisticPoint point : reachablePoints) {
            distances.put(point, Path.INFINITY);
        }
        distances.put(from, Path.ZERO);
    }

    private static LogisticPoint getClosestUnvisitedNode(Set<LogisticPoint> reachablePoints, Map<LogisticPoint, Path> distances, Set<LogisticPoint> visitedPoints) {
        LogisticPoint result = null;
        Path minDistance = Path.INFINITY;
        for (LogisticPoint arch : Sets.difference(reachablePoints, visitedPoints)) {
            Path path = distances.get(arch);
            if (path.compareTo(minDistance) < 0) {
                minDistance = path;
                result = arch;
            }
        }
        return result;
    }

    private static Path minimalPathToPointFrom(LogisticArch arch, Map<LogisticPoint, Path> distances) {
        LogisticPoint from = arch.getFrom();
        Path totalDistance = distances.get(arch.getTo());
        Path distanceUsingArch = distances.get(from).addArch(arch);
        if (totalDistance.compareTo(distanceUsingArch) < 0) {
            return totalDistance;
        }
        return distanceUsingArch;
    }
}
