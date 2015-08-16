package br.com.badiale.simplepath.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Entidade que representa um ponto num mapa.
 * <p>
 * Um ponto pode estar relacionado à outros pontos.
 */
@NodeEntity
public class LogisticPoint {
    @GraphId
    private Long id;

    @Indexed(unique = true, failOnDuplicate = true)
    @NotNull
    @Size(min = 1)
    private String name;

    @RelatedToVia
    private final Set<LogisticArch> siblings = Sets.newHashSet();

    private LogisticPoint() {
    }

    public LogisticPoint(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<LogisticArch> getSiblings() {
        return ImmutableSet.copyOf(siblings);
    }

    /**
     * Adiciona um ponto como irmão deste.
     *
     * @param to       Ponto irmão à ser adicionado.
     * @param distance Distância entre os pontos.
     * @return Arco criado pela ligação entre os dois pontos.
     * @throws NullPointerException     caso {@code to} seja nulo.
     * @throws IllegalArgumentException caso este ponto seja igual à {@code to},
     *                                  ou caso a distância seja menor ou igual à zero.
     */
    public LogisticArch addSibling(LogisticPoint to, long distance) {
        Preconditions.checkNotNull(to, "to is null");
        Preconditions.checkArgument(!this.equals(to), "from and to are the same");
        Preconditions.checkArgument(distance > 0, "distance is less than 0");

        LogisticArch logisticArch = new LogisticArch(this, to, distance);
        siblings.add(logisticArch);
        return logisticArch;
    }

    /**
     * Determina se este ponto já possui um determinado ponto como irmão.
     *
     * @param sibling Ponto para ser verificado.
     * @return true se {@code sibling} for irmão deste ponto, ou false caso contrário.
     */
    public boolean hasSibling(LogisticPoint sibling) {
        for (LogisticArch arch : getSiblings()) {
            if (arch.getTo().equals(sibling)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método que retorna todos os pontos que podem ser alcançadas à partir deste ponto.
     * <p>
     * Este ponto também é incluido no conjunto.
     *
     * @return todos os pontos que podem ser alcançadas à partir deste ponto.
     */
    public Set<LogisticPoint> getReachablePoints() {
        Set<LogisticPoint> result = Sets.newHashSet(this);
        Set<LogisticPoint> toVisit = Sets.newHashSet();

        for (LogisticArch arch : getSiblings()) {
            toVisit.add(arch.getTo());
        }
        while (!toVisit.isEmpty()) {
            Set<LogisticPoint> newVisits = Sets.newHashSet();
            for (LogisticPoint to : toVisit) {
                result.add(to);
                for (LogisticArch arch : to.getSiblings()) {
                    newVisits.add(arch.getTo());
                }
            }
            toVisit.addAll(newVisits);
            toVisit.removeAll(result);
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogisticPoint)) return false;
        LogisticPoint that = (LogisticPoint) o;
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
                .toString();
    }
}
