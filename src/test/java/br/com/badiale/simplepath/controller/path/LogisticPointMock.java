package br.com.badiale.simplepath.controller.path;

import br.com.badiale.simplepath.model.LogisticPoint;
import com.google.common.base.Objects;

public class LogisticPointMock extends LogisticPoint {
    public LogisticPointMock(String name) {
        super(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogisticPointMock)) return false;
        LogisticPointMock that = (LogisticPointMock) o;
        return Objects.equal(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }
}
