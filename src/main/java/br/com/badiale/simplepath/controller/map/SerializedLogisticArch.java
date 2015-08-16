package br.com.badiale.simplepath.controller.map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.annotation.concurrent.Immutable;

/**
 * Versão serializável de um arco.
 * <p>
 * Esta implementação é utilizada para traduzir o que é enviado para o serviço de mapas.
 *
 * @see LogisticMapController
 */
@Immutable
public class SerializedLogisticArch {
    private final String from;
    private final String to;
    private final Long distance;

    @JsonCreator
    public SerializedLogisticArch(
            @JsonProperty("from") String from,
            @JsonProperty("to") String to,
            @JsonProperty("distance") Long distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Long getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SerializedLogisticArch)) return false;
        SerializedLogisticArch that = (SerializedLogisticArch) o;
        return Objects.equal(from, that.from) &&
                Objects.equal(to, that.to) &&
                Objects.equal(distance, that.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(from, to, distance);
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
