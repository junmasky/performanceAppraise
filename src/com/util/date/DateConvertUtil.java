package com.util.date;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateConvertUtil {


    public static DateConvertModel dateConvert(Date date){


        if (date != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.setTime(date);

            int yearInt = calendar.get(Calendar.YEAR);
            int monthInt =  calendar.get(Calendar.MONTH);
            int weekOfYearInt = calendar.get(Calendar.WEEK_OF_YEAR);
            monthInt++;

            String dayFirst = String.valueOf( yearInt) +"0101";
            if (dayOfWeek(dayFirst,"yyyyMMdd") != 2){

                weekOfYearInt--;
                if (weekOfYearInt == 0){
                    weekOfYearInt = 54;

                    yearInt--;
                    monthInt =12;
                }
            }

            int dayOfWeekInt  = calendar.get(Calendar.DAY_OF_WEEK);
            dayOfWeekInt--;
            if (dayOfWeekInt == 0){
                dayOfWeekInt = 7;
            }

//            System.out.println("===>" + yearInt);
//            System.out.println("===>" +monthInt);
//            System.out.println("===>" +weekOfYearInt);
//            System.out.println("===>" +dayOfWeekInt);
//            System.out.println("==============================");

            DateConvertModel  convertModel = new DateConvertModel();
            convertModel.setYear(new BigDecimal(yearInt));
            convertModel.setMoth(new BigDecimal(monthInt));
            convertModel.setWeekOfYear(new BigDecimal(weekOfYearInt));
            convertModel.setDayOfWeek(new BigDecimal(dayOfWeekInt));
            convertModel = DateConvertUtil.convertWeekDay(date,convertModel);
            convertModel = DateConvertUtil.convertLastWeekDay(date,convertModel);
            return convertModel;
        }else{
            return null;
        }

    }

    /**
     * 获取本周的周一和周日
     * @param date
     * @param convertModel
     * @return
     */
    private static DateConvertModel convertWeekDay(Date date,DateConvertModel convertModel){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //判断要计算的日期是否周日，如果是则减一天算周六的
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if(1== dayWeek){
            cal.add(Calendar.DAY_OF_MONTH,-1);
        }

        //设置周的开始为周一
        cal.setFirstDayOfWeek(Calendar.MONDAY);

        int day = cal.get(Calendar.DAY_OF_WEEK);

        cal.add(Calendar.DATE,cal.getFirstDayOfWeek() - day);
        convertModel.setWeekFirstDay(cal.getTime());


        cal.add(Calendar.DATE,6);
        convertModel.setWeekLastDay(cal.getTime());

        return convertModel;
    }

    /**
     * 获取上周的周一和周日
     * @param date
     * @param convertModel
     * @return
     */
    private static DateConvertModel convertLastWeekDay(Date date,DateConvertModel convertModel){
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date);
        int dayOfWeek = cal1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        cal1.add(Calendar.DATE,offset1 - 7);
        cal2.add(Calendar.DATE,offset2 - 7);
        convertModel.setLastWeekFirstDay(cal1.getTime());
        convertModel.setLastWeekLastDay(cal2.getTime());
        return convertModel;
    }

    private static int dayOfWeek(String dateString, String formatString){
        if (dateString != null && !"".equals(dateString) && formatString != null && !"".equals(formatString)){
            SimpleDateFormat format = new SimpleDateFormat(formatString);
            Date date = null;
            try {
                date = format.parse(dateString);
            }catch (ParseException e){
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.setTime(date);
            return calendar.get(Calendar.DAY_OF_WEEK);
        }else{
            return -1;
        }
    }

    public static DateConvertModel dateConvert(String dateString, String  formatString){

       if (dateString != null && !"".equals(dateString) && formatString != null && !"".equals(formatString)){
           SimpleDateFormat format = new SimpleDateFormat(formatString);
           Date date = null;
           try {
               date = format.parse(dateString);
           }catch (ParseException e){
               e.printStackTrace();
           }

           return dateConvert(date);
       }else{
           return null;
       }

    }

    public static String dateToString(Date date,String formatString){
        if (date != null && formatString !=null && !"".equals(formatString)){
            SimpleDateFormat sdf = new SimpleDateFormat(formatString);
//            System.out.println(sdf.format(date));
            return sdf.format(date);
        }else{
            return null;
        }
    }

    public static Date stringToDate(String dateString,String formatString) throws Exception{

        if (dateString != null && formatString != null){
            SimpleDateFormat sdf = new SimpleDateFormat(formatString);

            return sdf.parse(dateString);
        }else{
            return null;
        }
    }


}
