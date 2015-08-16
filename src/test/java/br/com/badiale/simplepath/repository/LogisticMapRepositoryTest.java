package br.com.badiale.simplepath.repository;

import br.com.badiale.simplepath.AbstractIntegrationTest;
import br.com.badiale.simplepath.model.LogisticMap;
import br.com.badiale.simplepath.model.LogisticPoint;
import com.google.common.collect.ImmutableSet;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.model.MappingException;

import javax.annotation.Resource;
import javax.validation.ValidationException;

import static org.junit.Assert.assertThat;

public class LogisticMapRepositoryTest extends AbstractIntegrationTest {

    @Resource
    private LogisticMapRepository logisticMapRepository;

    @Resource
    private LogisticPointRepository logisticPointRepository;

    @Test
    public void shouldSave() {
        assertThat(logisticMapRepository.save(new LogisticMap("test")).getId(), IsNull.notNullValue());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldFailOnDuplicatedNames() {
        logisticMapRepository.save(new LogisticMap("test"));
        logisticMapRepository.save(new LogisticMap("test"));
    }

    @Test(expected = MappingException.class)
    public void shouldFailOnNullName() {
        logisticMapRepository.save(new LogisticMap(null));
    }

    @Test(expected = ValidationException.class)
    public void shouldFailOnEmptyName() {
        logisticMapRepository.save(new LogisticMap(""));
    }

    @Test(expected = NullPointerException.class)
    public void shouldFailWhenPointIsNull() {
        new LogisticMap("Map").addPoint(null);
    }

    @Test
    public void shouldPersistPoints() {
        LogisticMap map = logisticMapRepository.save(new LogisticMap("Map"));
        LogisticPoint pointA = logisticPointRepository.save(new LogisticPoint("A"));
        LogisticPoint pointB = logisticPointRepository.save(new LogisticPoint("B"));
        map.addPoint(pointA);
        map.addPoint(pointB);

        logisticMapRepository.save(map);

        LogisticMap persistedMap = logisticMapRepository.findByName("Map");
        assertThat(persistedMap.getLogisticPoints(), IsEqual.equalTo(ImmutableSet.of(pointA, pointB)));
    }
}