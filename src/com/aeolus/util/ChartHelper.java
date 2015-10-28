package com.aeolus.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.jfree.chart.axis.SegmentedTimeline;

import com.aeolus.constant.BarSize;
//the jfreechart API has a bug to combine segmented time lines.
public class ChartHelper {
	public static SegmentedTimeline getTimeline(BarSize barSize) {
		if(barSize.equals(BarSize.Day1)||barSize.equals(BarSize.Week1)||barSize.equals(BarSize.Month1)){
			return SegmentedTimeline.newMondayThroughFridayTimeline();
		}
		long timelineSize = SegmentedTimeline.FIFTEEN_MINUTE_SEGMENT_SIZE * 2;
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.set(Calendar.YEAR, 1990);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date open = cal.getTime();

        cal = Calendar.getInstance(Locale.US);
        cal.set(Calendar.YEAR, 1990);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 16);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date close = cal.getTime();

        SegmentedTimeline result = null;
        // Create a segmented time line (segment size : 15 minutes)
        long quarterHourCount = (close.getTime() - open.getTime())
            / timelineSize;
        long totalQuarterHourCount = SegmentedTimeline.DAY_SEGMENT_SIZE
            / timelineSize;
        result = new SegmentedTimeline(
        		timelineSize,
            (int) quarterHourCount,
            (int) (totalQuarterHourCount - quarterHourCount)
        );
        result.setAdjustForDaylightSaving(true);
        // Set start time
        result.setStartTime(timelineSize*27);
        // Saturday and Sundays are non business hours. This line is useless thanks to the fucking API
        result.setBaseTimeline(
            SegmentedTimeline.newMondayThroughFridayTimeline()
        );
        return result;
    }
}
