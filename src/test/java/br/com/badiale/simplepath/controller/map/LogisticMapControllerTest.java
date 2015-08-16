package br.com.badiale.simplepath.controller.map;

import br.com.badiale.simplepath.AbstractIntegrationTest;
import br.com.badiale.simplepath.model.LogisticArch;
import br.com.badiale.simplepath.model.LogisticMap;
import br.com.badiale.simplepath.model.LogisticPoint;
import br.com.badiale.simplepath.repository.LogisticMapRepository;
import br.com.badiale.simplepath.repository.LogisticPointRepository;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Set;

import static org.junit.Assert.assertThat;

public class LogisticMapControllerTest extends AbstractIntegrationTest {
    @Resource
    private LogisticMapRepository mapRepository;

    @Resource
    private LogisticPointRepository pointRepository;

    @Resource
    private LogisticMapController logisticMapController;

    @Test
    public void shouldSaveMap() {
        SerializedLogisticMap map = new SerializedLogisticMap("testMap", ImmutableSet.of(
                new SerializedLogisticArch("testA", "testB", 10l)
        ));
        logisticMapController.convertAndPersist(map);

        LogisticMap persistedMap = mapRepository.findByName(map.getName());
        assertThat(persistedMap.getId(), IsNull.notNullValue());

        Set<LogisticPoint> logisticPoints = persistedMap.getLogisticPoints();
        assertThat(logisticPoints.size(), IsEqual.equalTo(2));

        LogisticArch arch = Iterables.getOnlyElement(pointRepository.findByName("testA").getSiblings());
        assertThat(arch.getTo().getName(), IsEqual.equalTo("testB"));
        assertThat(arch.getDistance(), IsEqual.equalTo(10l));
    }

    @Test(expected = MapAlreadyExistsException.class)
    public void shouldRejectMapWhenAlreadyExists() {
        SerializedLogisticMap map = new SerializedLogisticMap("testMap", ImmutableSet.of());
        logisticMapController.convertAndPersist(map);
        logisticMapController.convertAndPersist(map);
    }

    @Test(expected = ArchAlreadyExistsInMapException.class)
    public void shouldRejectMapWhenArchAlreadyExists() {
        SerializedLogisticMap map = new SerializedLogisticMap("testMap", ImmutableSet.of(
                new SerializedLogisticArch("testA", "testB", 10l),
                new SerializedLogisticArch("testA", "testB", 20l)
        ));
        logisticMapController.convertAndPersist(map);
    }

    @Test(expected = ArchHasDistanceLessThanOrEqualToZeroException.class)
    public void shouldRejectMapWhenDistanceIsLessThanZero() {
        SerializedLogisticMap map = new SerializedLogisticMap("testMap", ImmutableSet.of(
                new SerializedLogisticArch("testA", "testB", -1l)
        ));
        logisticMapController.convertAndPersist(map);
    }

    @Test(expected = ArchWithSameFromAndToException.class)
    public void shouldRejectMapWhenArchHaveSameFromAndTo() {
        SerializedLogisticMap map = new SerializedLogisticMap("testMap", ImmutableSet.of(
                new SerializedLogisticArch("testA", "testA", 10l)
        ));
        logisticMapController.convertAndPersist(map);
    }
}