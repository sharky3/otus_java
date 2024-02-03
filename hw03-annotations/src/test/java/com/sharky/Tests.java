package com.sharky;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("java:S2187")
public class Tests {
    private static final Logger logger = LoggerFactory.getLogger(Tests.class);

    @Before
    void setup(String testName) {
        logger.debug("Starting {} test...", testName);
    }

    @After
    void shutdown(String testName) {
        logger.debug("Finished {} test", testName);
    }

    @Before
    void anotherSetup() {
        logger.debug("Making some preparations...");
    }

    @Before
    void oneMoreSetup() {
        logger.debug("Loading...");
    }

    @After
    void anotherShutdown() {
        logger.debug("Bye, have a beautiful time!");
    }

    @Test
    void passingTest() {
        logger.info("THIS TEST SHALL PASS");
        assertEquals(5, 2 + 3);
    }

    @Test
    void exceptionThrowingTest() {
        logger.error("EXCEPTION THROWING TEST");
        var myArray = new int[16];
        var iob = myArray[20];
    }

    @Test
    void failingTest() {
        logger.error("FAILING TEST");
        assertEquals(5, 2 + 2);
    }

    @Test
    void anotherPassingTest() {
        logger.info("AND THIS TEST SHALL PASS");
        var isTrue = true;
        assertTrue(isTrue);
    }

    @Test
    private void unreachableTest() {
        logger.error("THIS TEST MUST NOT HAVE BEEN INVOKED");
        throw new UnsupportedOperationException();
    }
}
