package br.com.badiale.simplepath.controller.path;

import br.com.badiale.simplepath.model.LogisticPoint;
import com.google.common.collect.ImmutableList;
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
        assertThat(pathService.shorthestPath(start, start), IsEqual.equalTo(Path.ZERO));
    }

    @Test
    public void shouldFindShortestPathOfTwoPoints() {
        LogisticPoint start = new LogisticPointMock("A");
        LogisticPoint end = new LogisticPointMock("B");
        start.addSibling(end, 10l);
        assertThat(pathService.shorthestPath(start, end), IsEqual.equalTo(new Path(ImmutableList.of(end), 10l)));
    }

    @Test
    public void shouldFindShortestPathPassingOnOtherPoints() {
        LogisticPoint start = new LogisticPointMock("A");
        LogisticPoint middle = new LogisticPointMock("B");
        LogisticPoint end = new LogisticPointMock("C");
        start.addSibling(end, 10l);
        start.addSibling(middle, 2l);
        middle.addSibling(end, 4l);
        assertThat(pathService.shorthestPath(start, end), IsEqual.equalTo(new Path(ImmutableList.of(middle, end), 6l)));
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
        assertThat(pathService.shorthestPath(start, end), IsEqual.equalTo(new Path(ImmutableList.of(middle, end), 6l)));
    }

    @Test(expected = PathNotFoundException.class)
    public void shouldNotFindShortestPathWhenNotConnected() {
        LogisticPoint start = new LogisticPointMock("A");
        LogisticPoint end = new LogisticPointMock("C");
        pathService.shorthestPath(start, end);
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

        assertThat(pathService.shorthestPath(point1, point2), IsEqual.equalTo(new Path(ImmutableList.of(point2), 1l)));
        assertThat(pathService.shorthestPath(point1, point3), IsEqual.equalTo(new Path(ImmutableList.of(point4, point3), 5l)));
        assertThat(pathService.shorthestPath(point1, point4), IsEqual.equalTo(new Path(ImmutableList.of(point4), 3l)));
        assertThat(pathService.shorthestPath(point1, point5), IsEqual.equalTo(new Path(ImmutableList.of(point4, point3, point5), 6l)));
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

        assertThat(pathService.shorthestPath(pointA, pointD), IsEqual.equalTo(new Path(ImmutableList.of(pointB, pointD), 25l)));
    }

    @Test
    public void shouldFindPathOnBiggerExampleCase() {
        LogisticPoint start = new LogisticPointMock("S");
        LogisticPoint point2 = new LogisticPointMock("2");
        LogisticPoint point3 = new LogisticPointMock("3");
        LogisticPoint point4 = new LogisticPointMock("4");
        LogisticPoint point5 = new LogisticPointMock("5");
        LogisticPoint point6 = new LogisticPointMock("6");
        LogisticPoint point7 = new LogisticPointMock("7");
        LogisticPoint end = new LogisticPointMock("T");
        start.addSibling(point2, 9l);
        start.addSibling(point6, 14l);
        start.addSibling(point7, 15l);
        point2.addSibling(point3, 24l);
        point3.addSibling(point5, 2l);
        point3.addSibling(end, 19l);
        point4.addSibling(point3, 6l);
        point4.addSibling(end, 6l);
        point5.addSibling(point4, 11l);
        point5.addSibling(end, 16l);
        point6.addSibling(point3, 18l);
        point6.addSibling(point5, 30l);
        point6.addSibling(point7, 5l);
        point7.addSibling(point5, 20l);
        point7.addSibling(end, 44l);

        assertThat(pathService.shorthestPath(start, end), IsEqual.equalTo(new Path(ImmutableList.of(
                point6,
                point3,
                point5,
                end),
                50l)));
    }
}