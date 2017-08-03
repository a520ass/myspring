package com.hf.joda;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.junit.Test;

/**
 * Created by krt on 2017/7/27.
 */
public class JodaTimeTest {

    @Test
    public void testJoda1(){
        DateTime start = DateTime.parse("2017-01");
        DateTime end = DateTime.parse("2017-04");
        Period p = new Period(start,end, PeriodType.yearMonthDay());//最后一个参数如果不写的话，下面的返回值将会是错误的。

        int year = p.getYears();
    }
}
