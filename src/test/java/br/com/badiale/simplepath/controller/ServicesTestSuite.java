package br.com.badiale.simplepath.controller;

import br.com.badiale.simplepath.controller.map.LogisticMapControllerTest;
import br.com.badiale.simplepath.controller.path.PathServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LogisticMapControllerTest.class,
        PathServiceTest.class
})
public class ServicesTestSuite {
}
