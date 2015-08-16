package br.com.badiale.simplepath.controller.path;

import br.com.badiale.simplepath.model.LogisticArch;
import br.com.badiale.simplepath.model.LogisticPoint;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableList;

import javax.annotation.concurrent.Immutable;
import java.util.Collections;
import java.util.List;

/**
 * Caminho percorrido pelo algorítmo de Dijkstra.
 * <p>
 * Não mantém a origem no caminho, para poder ter as variáveis {@link #ZERO} e {@link #INFINITY}.
 */
@Immutable
public class Path implements Comparable<Path> {
    public static final Path ZERO = new Path(0l);

    public static final Path INFINITY = new Path(Long.MAX_VALUE) {
        @Override
        public Path addArch(LogisticArch arch) {
            // infinito + infinito = infinito
            return this;
        }
    };

    private final List<LogisticPoint> path;
    private final Long distance;

    private Path(long distance) {
        this(Collections.emptyList(), distance);
    }

    @VisibleForTesting
    Path(List<LogisticPoint> objects, long l) {
        path = objects;
        distance = l;
    }

    public List<LogisticPoint> getPath() {
        return path;
    }

    public Long getDistance() {
        return distance;
    }

    public Path addArch(LogisticArch arch) {
        return new Path(
                ImmutableList.<LogisticPoint>builder().addAll(getPath()).add(arch.getTo()).build(),
                distance + arch.getDistance());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Path)) return false;
        Path path1 = (Path) o;
        return Objects.equal(path, path1.path) &&
                Objects.equal(distance, path1.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(path, distance);
    }

    @Override
    public int compareTo(Path o) {
        return ComparisonChain.start()
                .compare(distance, o.distance)
                .compare(path.size(), o.path.size())
                .result();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("path", path)
                .add("distance", distance)
                .toString();
    }
}
