package es.ieci.tecdoc.fwktd.test.db;

import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@TestExecutionListeners(DbUnitInitializerTestExecutionListener.class)
public abstract class AbstractDbUnitTransactionalJUnit4SpringContextTests
		extends AbstractTransactionalJUnit4SpringContextTests {

}
