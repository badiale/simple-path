package br.com.badiale.simplepath.controller.path;

import br.com.badiale.simplepath.AbstractIntegrationTest;
import br.com.badiale.simplepath.model.LogisticPoint;
import br.com.badiale.simplepath.repository.LogisticPointRepository;
import com.google.common.collect.Lists;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.assertThat;

public class PathControllerTest extends AbstractIntegrationTest {
    @Resource
    private LogisticPointRepository pointRepository;

    @Resource
    private PathController pathController;

    @Test(expected = AutonomyIsZeroOsLessException.class)
    public void shouldFailWhenAutonomyIsZeroOrLess() {
        pathController.getShortPath("A", "B", -10.0, 10.0);
    }

    @Test(expected = GasValueIsZeroOsLessException.class)
    public void shouldFailWhenGasValueIsZeroOrLess() {
        pathController.getShortPath("A", "B", 10.0, -10.0);
    }

    @Test(expected = PointNotFoundException.class)
    public void shouldFailWhenFromNotExists() {
        pointRepository.save(new LogisticPoint("B"));
        pathController.getShortPath("A", "B", 10.0, 10.0);
    }

    @Test(expected = PointNotFoundException.class)
    public void shouldFailWhenToNotExists() {
        pointRepository.save(new LogisticPoint("A"));
        pathController.getShortPath("A", "B", 10.0, 10.0);
    }

    @Test(expected = PathNotFoundException.class)
    public void shouldFailWhenPathNotExists() {
        pointRepository.save(new LogisticPoint("A"));
        pointRepository.save(new LogisticPoint("B"));
        pathController.getShortPath("A", "B", 10.0, 10.0);
    }

    @Test
    public void shouldFindPath() {
        LogisticPoint pointA = pointRepository.save(new LogisticPoint("A"));
        LogisticPoint pointB = pointRepository.save(new LogisticPoint("B"));
        pointA.addSibling(pointB, 10);
        assertThat(pathController.getShortPath("A", "B", 10.0, 10.0), IsEqual.equalTo(new PathControllerResponse(Lists.newArrayList("A", "B"), 10.0)));
    }

    @Test
    public void shouldFindPathOnExampleCase() {
        LogisticPoint pointA = pointRepository.save(new LogisticPoint("A"));
        LogisticPoint pointB = pointRepository.save(new LogisticPoint("B"));
        LogisticPoint pointC = pointRepository.save(new LogisticPoint("C"));
        LogisticPoint pointD = pointRepository.save(new LogisticPoint("D"));
        LogisticPoint pointE = pointRepository.save(new LogisticPoint("E"));
        pointA.addSibling(pointB, 10l);
        pointB.addSibling(pointD, 15l);
        pointA.addSibling(pointC, 20l);
        pointC.addSibling(pointD, 30l);
        pointB.addSibling(pointE, 50l);
        pointD.addSibling(pointE, 30l);

        assertThat(pathController.getShortPath("A", "D", 10.0, 2.5), IsEqual.equalTo(new PathControllerResponse(Lists.newArrayList("A", "B", "D"), 6.25)));
    }
}