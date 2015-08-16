package br.com.badiale.simplepath.repository;

import br.com.badiale.simplepath.AbstractIntegrationTest;
import br.com.badiale.simplepath.model.LogisticArch;
import br.com.badiale.simplepath.model.LogisticPoint;
import com.google.common.collect.Iterables;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.model.MappingException;

import javax.annotation.Resource;
import javax.validation.ValidationException;

import static org.junit.Assert.assertThat;

public class LogisticPointRepositoryTest extends AbstractIntegrationTest {

    @Resource
    private LogisticPointRepository logisticPointRepository;

    @Test
    public void shouldSave() {
        assertThat(logisticPointRepository.save(new LogisticPoint("test")).getId(), IsNull.notNullValue());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldFailOnDuplicatedNames() {
        logisticPointRepository.save(new LogisticPoint("test"));
        logisticPointRepository.save(new LogisticPoint("test"));
    }

    @Test(expected = MappingException.class)
    public void shouldFailOnNullName() {
        logisticPointRepository.save(new LogisticPoint(null));
    }

    @Test(expected = ValidationException.class)
    public void shouldFailOnEmptyName() {
        logisticPointRepository.save(new LogisticPoint(""));
    }

    @Test(expected = NullPointerException.class)
    public void shouldFailWhenToIsNull() {
        new LogisticPoint("A").addSibling(null, 10l);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWhenSiblingIsThis() {
        LogisticPoint pointA = logisticPointRepository.save(new LogisticPoint("A"));
        pointA.addSibling(pointA, 10l);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWhenSiblingDistanceIsLessThanZero() {
        LogisticPoint pointA = logisticPointRepository.save(new LogisticPoint("A"));
        LogisticPoint pointB = logisticPointRepository.save(new LogisticPoint("B"));
        pointA.addSibling(pointB, -1l);
    }

    @Test
    public void shouldPersistSiblings() {
        LogisticPoint pointA = logisticPointRepository.save(new LogisticPoint("A"));
        LogisticPoint pointB = logisticPointRepository.save(new LogisticPoint("B"));
        LogisticPoint pointC = logisticPointRepository.save(new LogisticPoint("C"));
        LogisticPoint pointD = logisticPointRepository.save(new LogisticPoint("D"));
        LogisticArch archAB = pointA.addSibling(pointB, 10l);
        LogisticArch archBC = pointB.addSibling(pointC, 15l);
        LogisticArch archCD = pointC.addSibling(pointD, 20l);

        logisticPointRepository.save(pointA);

        LogisticPoint persistedA = logisticPointRepository.findByName("A");
        LogisticArch persistedArchAB = Iterables.getOnlyElement(persistedA.getSiblings());
        assertThat(persistedArchAB, IsEqual.equalTo(archAB));

        LogisticArch persistedArchBC = Iterables.getOnlyElement(persistedArchAB.getTo().getSiblings());
        assertThat(persistedArchBC, IsEqual.equalTo(archBC));

        LogisticArch persistedArchCD = Iterables.getOnlyElement(persistedArchBC.getTo().getSiblings());
        assertThat(persistedArchCD, IsEqual.equalTo(archCD));
    }
}