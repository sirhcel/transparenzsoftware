package com.hastobe.transparenzsoftware.verification.format.ocmf.v02;

import org.junit.Assert;
import org.junit.Test;

import java.time.OffsetDateTime;

public class ReadingTest {

    @Test
    public void testTimestampParsing() {
        Reading reading = new Reading();
        reading.setTM("2018-07-24T13:22:04,000+0200 S");
        OffsetDateTime timestamp = reading.getTimestamp();
        Assert.assertNotNull(timestamp);
        Assert.assertEquals(2018, timestamp.getYear());
        Assert.assertEquals(7, timestamp.getMonthValue());
        Assert.assertEquals(24, timestamp.getDayOfMonth());
        Assert.assertEquals(13, timestamp.getHour());
        Assert.assertEquals(22, timestamp.getMinute());
        Assert.assertEquals(4, timestamp.getSecond());
        Assert.assertEquals(0, timestamp.getNano());
        Assert.assertEquals(2 * 60 * 60, timestamp.getOffset().getTotalSeconds());
    }

    @Test
    public void testTimestampParsingWrongFormat() {
        Reading reading = new Reading();
        reading.setTM("201800-07-24T13:22:04,000+0200 S");
        OffsetDateTime timestamp = reading.getTimestamp();
        Assert.assertNull(timestamp);
    }

    @Test
    public void testTimestampParsingNull() {
        Reading reading = new Reading();
        OffsetDateTime timestamp = reading.getTimestamp();
        Assert.assertNull(timestamp);
    }

    @Test
    public void testTimestampSynchronicity() {
        Reading reading = new Reading();
        reading.setTM("2018-07-24T13:22:04,000+0200 S");
        Assert.assertEquals("S", reading.getTimeSynchronicity());
    }

    @Test
    public void testTimestampWrongFormatNull() {
        Reading reading = new Reading();
        reading.setTM("2018-07-24T13:22:04,000+0200S");
        Assert.assertNull(reading.getTimeSynchronicity());
    }

    @Test
    public void testTimestampSynchronicityNull() {
        Reading reading = new Reading();
        Assert.assertNull(reading.getTimeSynchronicity());
    }


    @Test
    public void testTimestampSynchronicityNullLabel() {
        Reading reading = new Reading();
        Assert.assertNull(reading.getLabelForTimeFlag());
    }

    @Test
    public void testTimestampSynchronicityULabel() {
        Reading reading = new Reading();
        reading.setTM("2018-07-24T13:22:04,000+0200 U");
        Assert.assertEquals("app.verify.ocmf.timesynchronicity.unknown", reading.getLabelForTimeFlag());
    }

    @Test
    public void testTimestampSynchronicityYLabel() {
        Reading reading = new Reading();
        reading.setTM("2018-07-24T13:22:04,000+0200 Y");
        Assert.assertEquals("app.verify.ocmf.timesynchronicity.unknown", reading.getLabelForTimeFlag());
    }

    @Test
    public void testTimestampSynchronicitySLabel() {
        Reading reading = new Reading();
        reading.setTM("2018-07-24T13:22:04,000+0200 S");
        Assert.assertEquals("app.verify.ocmf.timesynchronicity.synchronised", reading.getLabelForTimeFlag());
    }

    @Test
    public void testTimestampSynchronicityRLabel() {
        Reading reading = new Reading();
        reading.setTM("2018-07-24T13:22:04,000+0200 R");
        Assert.assertEquals("app.verify.ocmf.timesynchronicity.relative", reading.getLabelForTimeFlag());
    }

    @Test
    public void testTimestampSynchronicityILabel() {
        Reading reading = new Reading();
        reading.setTM("2018-07-24T13:22:04,000+0200 I");
        Assert.assertEquals("app.verify.ocmf.timesynchronicity.informative", reading.getLabelForTimeFlag());
    }

    @Test
    public void testIsTimeSynchron(){
        Reading reading = new Reading();
        reading.setTM("2018-07-24T13:22:04,000+0200 I");
        Assert.assertTrue(reading.isTimeInformativeOnly());
        reading.setTM("2018-07-24T13:22:04,000+0200 S");
        Assert.assertFalse(reading.isTimeInformativeOnly());
        reading.setTM("2018-07-24T13:22:04,000+0200 U");
        Assert.assertTrue(reading.isTimeInformativeOnly());

        reading.setTM("2018-07-24T13:22:04,000+0200 R");
        Assert.assertFalse(reading.isTimeInformativeOnly());
    }
}
