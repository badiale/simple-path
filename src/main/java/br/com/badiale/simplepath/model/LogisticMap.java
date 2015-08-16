package br.com.badiale.simplepath.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Set;

/**
 * Entidade que contém uma coleção de pontos em um mapa.
 */
@NodeEntity
public class LogisticMap {
    @GraphId
    private Long id;

    @Indexed(unique = true, failOnDuplicate = true)
    @NotNull
    @Size(min = 1)
    private String name;

    @RelatedTo
    @NotNull
    private final Set<LogisticPoint> logisticPoints = Sets.newHashSet();

    private LogisticMap() {
    }

    public LogisticMap(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * @return Uma visão imutável dos pontos contidos no mapa.
     */
    public Set<LogisticPoint> getLogisticPoints() {
        return Collections.unmodifiableSet(logisticPoints);
    }

    /**
     * Adiciona um ponto ao mapa.
     *
     * @param point Ponto à ser adicionado ao mapa.
     * @return LogisticPoint o ponto adicionado no mapa.
     * @throws NullPointerException caso o ponto fornecido seja nulo.
     */
    public LogisticPoint addPoint(LogisticPoint point) {
        logisticPoints.add(Preconditions.checkNotNull(point, "point is null"));
        return point;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogisticMap)) return false;
        LogisticMap that = (LogisticMap) o;
        return Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("logisticPoints", logisticPoints)
                .toString();
    }
}
