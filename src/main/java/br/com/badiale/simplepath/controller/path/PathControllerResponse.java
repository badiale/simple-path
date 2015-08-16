package br.com.badiale.simplepath.controller.path;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.List;

/**
 * Resposta dada pelo serviço de caminho mais curto.
 */
public class PathControllerResponse {
    private final List<String> path;
    private final Double value;

    public PathControllerResponse(List<String> path, Double value) {
        this.path = path;
        this.value = value;
    }

    public List<String> getPath() {
        return path;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PathControllerResponse)) return false;
        PathControllerResponse that = (PathControllerResponse) o;
        return Objects.equal(path, that.path) &&
                Objects.equal(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(path, value);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("path", path)
                .add("value", value)
                .toString();
    }
}
