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
    public Path shorthestPath(LogisticPoint from, LogisticPoint to) {
        Set<LogisticPoint> reachablePoints = Sets.newHashSet(from.getReachablePoints());

        if (!reachablePoints.contains(to)) {
            throw new PathNotFoundException();
        }

        Map<LogisticPoint, Path> distances = Maps.newHashMap();

        initializeDistances(from, reachablePoints, distances);
        while (!reachablePoints.isEmpty()) {
            LogisticPoint currentVisiting = getClosestUnvisitedNode(reachablePoints, distances);
            reachablePoints.remove(currentVisiting);
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

    private static LogisticPoint getClosestUnvisitedNode(Set<LogisticPoint> reachablePoints, Map<LogisticPoint, Path> distances) {
        LogisticPoint result = null;
        Path minPath = Path.INFINITY;
        for (LogisticPoint point : reachablePoints) {
            Path path = distances.get(point);
            if (path.compareTo(minPath) < 0) {
                minPath = path;
                result = point;
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
