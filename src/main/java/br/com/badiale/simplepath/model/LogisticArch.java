package br.com.badiale.simplepath.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.data.neo4j.annotation.*;

/**
 * Entidade que representa o relacionamento entre um ponto e outro.
 */
@RelationshipEntity(type = "ARCH")
public class LogisticArch {
    @GraphId
    private Long id;

    @StartNode
    private LogisticPoint from;

    @Fetch
    @EndNode
    private LogisticPoint to;

    private Long distance;

    public LogisticArch() {
    }

    public LogisticArch(LogisticPoint from, LogisticPoint to, Long distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public LogisticPoint getFrom() {
        return from;
    }

    public LogisticPoint getTo() {
        return to;
    }

    public Long getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogisticArch)) return false;
        LogisticArch that = (LogisticArch) o;
        return Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("from", from)
                .add("to", to)
                .add("distance", distance)
                .toString();
    }
}
