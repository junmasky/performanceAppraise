package com.util.date;

import java.math.BigDecimal;
import java.util.Date;

public class DateConvertModel {

    private BigDecimal year;
    private BigDecimal moth;
    private BigDecimal weekOfYear;
    private BigDecimal dayOfWeek;
    private Date weekFirstDay;
    private Date weekLastDay;
    private Date lastWeekFirstDay;
    private Date lastWeekLastDay;

    public BigDecimal getYear() {
        return year;
    }

    public void setYear(BigDecimal year) {
        this.year = year;
    }

    public BigDecimal getMoth() {
        return moth;
    }

    public void setMoth(BigDecimal moth) {
        this.moth = moth;
    }

    public BigDecimal getWeekOfYear() {
        return weekOfYear;
    }

    public void setWeekOfYear(BigDecimal weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    public BigDecimal getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(BigDecimal dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Date getWeekFirstDay() {
        return weekFirstDay;
    }

    public void setWeekFirstDay(Date weekFirstDay) {
        this.weekFirstDay = weekFirstDay;
    }

    public Date getWeekLastDay() {
        return weekLastDay;
    }

    public void setWeekLastDay(Date weekLastDay) {
        this.weekLastDay = weekLastDay;
    }

    public Date getLastWeekFirstDay() {
        return lastWeekFirstDay;
    }

    public void setLastWeekFirstDay(Date lastWeekFirstDay) {
        this.lastWeekFirstDay = lastWeekFirstDay;
    }

    public Date getLastWeekLastDay() {
        return lastWeekLastDay;
    }

    public void setLastWeekLastDay(Date lastWeekLastDay) {
        this.lastWeekLastDay = lastWeekLastDay;
    }
}
