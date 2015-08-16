package br.com.badiale.simplepath.controller.map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * Versão serializável de um mapa.
 * <p>
 * Esta implementação é utilizada para traduzir o que é enviado para o serviço de mapas.
 *
 * @see LogisticMapController
 */
public class SerializedLogisticMap {
    private final String name;
    private final Set<SerializedLogisticArch> arches;

    @JsonCreator
    public SerializedLogisticMap(
            @JsonProperty("name") String name,
            @JsonProperty("arches") Set<SerializedLogisticArch> arches
    ) {
        this.name = name;
        this.arches = ImmutableSet.copyOf(arches);
    }

    public String getName() {
        return name;
    }

    public Set<SerializedLogisticArch> getArches() {
        return arches;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SerializedLogisticMap)) return false;
        SerializedLogisticMap that = (SerializedLogisticMap) o;
        return Objects.equal(name, that.name) &&
                Objects.equal(arches, that.arches);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, arches);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("arches", arches)
                .toString();
    }
}
