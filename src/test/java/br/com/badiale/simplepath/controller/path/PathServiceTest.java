package br.com.badiale.simplepath.controller.path;

import br.com.badiale.simplepath.model.LogisticPoint;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class PathServiceTest {
    private PathService pathService;

    @Before
    public void instantiateService() {
        pathService = new PathService();
    }

    @Test
    public void shouldFindShortestPathBetweenTheSinglePoint() {
        LogisticPoint start = new LogisticPointMock("A");
        assertThat(pathService.shorthestPath(start, start), IsEqual.equalTo(0l));
    }

    @Test
    public void shouldFindShortestPathOfTwoPoints() {
        LogisticPoint start = new LogisticPointMock("A");
        LogisticPoint end = new LogisticPointMock("B");
        start.addSibling(end, 10l);
        assertThat(pathService.shorthestPath(start, end), IsEqual.equalTo(10l));
    }

    @Test
    public void shouldFindShortestPathPassingOnOtherPoints() {
        LogisticPoint start = new LogisticPointMock("A");
        LogisticPoint middle = new LogisticPointMock("B");
        LogisticPoint end = new LogisticPointMock("C");
        start.addSibling(end, 10l);
        start.addSibling(middle, 2l);
        middle.addSibling(end, 4l);
        assertThat(pathService.shorthestPath(start, end), IsEqual.equalTo(6l));
    }

    @Test
    public void shouldFindShortestPathEvenWhenLooping() {
        LogisticPoint start = new LogisticPointMock("A");
        LogisticPoint middle = new LogisticPointMock("B");
        LogisticPoint end = new LogisticPointMock("C");
        start.addSibling(end, 10l);
        start.addSibling(middle, 2l);
        middle.addSibling(end, 4l);
        middle.addSibling(start, 1l);
        assertThat(pathService.shorthestPath(start, end), IsEqual.equalTo(6l));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotFindShortestPathWhenNotConnected() {
        LogisticPoint start = new LogisticPointMock("A");
        LogisticPoint middle = new LogisticPointMock("B");
        LogisticPoint end = new LogisticPointMock("C");
        start.addSibling(middle, 1l);
        assertThat(pathService.shorthestPath(start, end), IsEqual.equalTo(6l));
    }

    @Test
    public void shouldFindPathInMoreComplexCases() {
        LogisticPoint point1 = new LogisticPointMock("1");
        LogisticPoint point2 = new LogisticPointMock("2");
        LogisticPoint point3 = new LogisticPointMock("3");
        LogisticPoint point4 = new LogisticPointMock("4");
        LogisticPoint point5 = new LogisticPointMock("5");
        point1.addSibling(point2, 1l);
        point1.addSibling(point4, 3l);
        point1.addSibling(point5, 10l);
        point2.addSibling(point3, 5l);
        point3.addSibling(point5, 1l);
        point4.addSibling(point3, 2l);
        point4.addSibling(point5, 6l);


        assertThat(pathService.shorthestPath(point1, point2), IsEqual.equalTo(1l));
        assertThat(pathService.shorthestPath(point1, point3), IsEqual.equalTo(5l));
        assertThat(pathService.shorthestPath(point1, point4), IsEqual.equalTo(3l));
        assertThat(pathService.shorthestPath(point1, point5), IsEqual.equalTo(6l));
    }

    @Test
    public void shouldFindPathOnExampleCase() {
        LogisticPoint pointA = new LogisticPointMock("A");
        LogisticPoint pointB = new LogisticPointMock("B");
        LogisticPoint pointC = new LogisticPointMock("C");
        LogisticPoint pointD = new LogisticPointMock("D");
        LogisticPoint pointE = new LogisticPointMock("E");
        pointA.addSibling(pointB, 10l);
        pointB.addSibling(pointD, 15l);
        pointA.addSibling(pointC, 20l);
        pointC.addSibling(pointD, 30l);
        pointB.addSibling(pointE, 50l);
        pointD.addSibling(pointE, 30l);


        assertThat(pathService.shorthestPath(pointA, pointD), IsEqual.equalTo(25l));
    }
}