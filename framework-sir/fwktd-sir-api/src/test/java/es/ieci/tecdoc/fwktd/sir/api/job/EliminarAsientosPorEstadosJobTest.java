package es.ieci.tecdoc.fwktd.sir.api.job;

import java.util.Date;

import org.junit.Test;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.impl.calendar.CronCalendar;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.JobDetailBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ 
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
	"/beans/fwktd-sir-test-beans-custom.xml" })
public class EliminarAsientosPorEstadosJobTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	@Autowired
	JobDetailBean eliminarAsientosPorEstadosJobDetail;

	@Test
	public void testEliminarAsientosPorEstadosJob() throws Exception {

		SpringBeanJobFactory jobFactory = new SpringBeanJobFactory();

		CronTriggerBean trigger = new CronTriggerBean();
		trigger.setName("trigger");
		trigger.setJobDetail(eliminarAsientosPorEstadosJobDetail);
		trigger.setCronExpression("0/5 * * * * ?");
		trigger.afterPropertiesSet();

		SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
		schedulerFactory.setJobFactory(jobFactory);
		schedulerFactory.setTriggers(new Trigger[] { trigger });
		schedulerFactory.afterPropertiesSet();

		TriggerFiredBundle bundle = new TriggerFiredBundle(
				eliminarAsientosPorEstadosJobDetail,
				trigger,
				new CronCalendar(trigger.getCronExpression()),
				false,
				new Date(),
				new Date(),
				trigger.getPreviousFireTime(),
				trigger.getNextFireTime());

		Job job = jobFactory.newJob(bundle);
		job.execute(new JobExecutionContext(schedulerFactory.getScheduler(), bundle, job));
	}
}
