package br.com.badiale.simplepath;

import br.com.badiale.simplepath.controller.ServicesTestSuite;
import br.com.badiale.simplepath.repository.RepositoryTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RepositoryTestSuite.class,
        ServicesTestSuite.class
})
public class SimplePathApplicationTests {
}
