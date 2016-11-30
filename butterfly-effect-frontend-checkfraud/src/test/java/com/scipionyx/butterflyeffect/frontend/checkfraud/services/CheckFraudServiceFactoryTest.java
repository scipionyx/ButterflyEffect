package com.scipionyx.butterflyeffect.frontend.checkfraud.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.scipionyx.butterflyeffect.checkfraud.model.CheckImage;

/**
 * 
 * @author Renato Mendes - rmendes@bottomline.com / renato.mendes.1123@gmail.com
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = {})
public class CheckFraudServiceFactoryTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CheckFraudServiceFactoryTest.class);

	@Autowired
	private CheckFraudServiceFactory checkFraudServiceFactory;

	/**
	 * 
	 */
	@Test
	public void ping() {
		ICheckImageService service = checkFraudServiceFactory.instance();
		String a = service.ping();
		Assert.assertEquals("Hello", a);
		LOGGER.info("Test Ping:" + a);
	}

	@Test
	public void analyze() {
		ICheckImageService service = checkFraudServiceFactory.instance();
		CheckImage a = service.analyze();
		Assert.assertNotNull(a);
		LOGGER.info("Test Analyze:" + a);
	}

}
