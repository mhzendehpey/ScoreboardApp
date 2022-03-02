package com.mxz.board.utils;

import java.util.Calendar;
import java.util.Locale;

public class SolarCalendar {

    public String strWeekDay = "";
    public String strMonth = "";

    int date;
    int month;
    int year;

    private void calcSolarCalendar() {

        int ld;

        Calendar calendar = Calendar.getInstance();
        int gregorianYear = calendar.get(Calendar.YEAR) + 1900;
        int gregorianMonth = calendar.get(Calendar.MONTH) + 1;
        int gregorianDate = calendar.get(Calendar.DAY_OF_MONTH);
        int WeekDay = calendar.get(Calendar.DAY_OF_WEEK);

        int[] buf1 = new int[12];
        int[] buf2 = new int[12];

        buf1[0] = 0;
        buf1[1] = 31;
        buf1[2] = 59;
        buf1[3] = 90;
        buf1[4] = 120;
        buf1[5] = 151;
        buf1[6] = 181;
        buf1[7] = 212;
        buf1[8] = 243;
        buf1[9] = 273;
        buf1[10] = 304;
        buf1[11] = 334;

        buf2[0] = 0;
        buf2[1] = 31;
        buf2[2] = 60;
        buf2[3] = 91;
        buf2[4] = 121;
        buf2[5] = 152;
        buf2[6] = 182;
        buf2[7] = 213;
        buf2[8] = 244;
        buf2[9] = 274;
        buf2[10] = 305;
        buf2[11] = 335;

        if ((gregorianYear % 4) != 0) {
            date = buf1[gregorianMonth - 1] + gregorianDate;

            if (date > 79) {
                date = date - 79;
                if (date <= 186) {
                    if (date % 31 == 0) {
                        month = date / 31;
                        date = 31;
                    } else {
                        month = (date / 31) + 1;
                        date = (date % 31);
                    }
                } else {
                    date = date - 186;

                    if (date % 30 == 0) {
                        month = (date / 30) + 6;
                        date = 30;
                    } else {
                        month = (date / 30) + 7;
                        date = (date % 30);
                    }
                }
                year = gregorianYear - 621;
            } else {
                if ((gregorianYear > 1996) && (gregorianYear % 4) == 1) {
                    ld = 11;
                } else {
                    ld = 10;
                }
                date = date + ld;

                if (date % 30 == 0) {
                    month = (date / 30) + 9;
                    date = 30;
                } else {
                    month = (date / 30) + 10;
                    date = (date % 30);
                }
                year = gregorianYear - 622;
            }
        } else {
            date = buf2[gregorianMonth - 1] + gregorianDate;

            if (gregorianYear >= 1996) {
                ld = 79;
            } else {
                ld = 80;
            }
            if (date > ld) {
                date = date - ld;

                if (date <= 186) {
                    if (date % 31 == 0) {
                        month = (date / 31);
                        date = 31;
                    } else {
                        month = (date / 31) + 1;
                        date = (date % 31);
                    }
                } else {
                    date = date - 186;

                    if (date % 30 == 0) {
                        month = (date / 30) + 6;
                        date = 30;
                    } else {
                        month = (date / 30) + 7;
                        date = (date % 30);
                    }
                }
                year = gregorianYear - 621;
            } else {
                date = date + 10;

                if (date % 30 == 0) {
                    month = (date / 30) + 9;
                    date = 30;
                } else {
                    month = (date / 30) + 10;
                    date = (date % 30);
                }
                year = gregorianYear - 622;
            }

        }

        switch (month) {
            case 1:
                strMonth = "فروردين";
                break;
            case 2:
                strMonth = "ارديبهشت";
                break;
            case 3:
                strMonth = "خرداد";
                break;
            case 4:
                strMonth = "تير";
                break;
            case 5:
                strMonth = "مرداد";
                break;
            case 6:
                strMonth = "شهريور";
                break;
            case 7:
                strMonth = "مهر";
                break;
            case 8:
                strMonth = "آبان";
                break;
            case 9:
                strMonth = "آذر";
                break;
            case 10:
                strMonth = "دي";
                break;
            case 11:
                strMonth = "بهمن";
                break;
            case 12:
                strMonth = "اسفند";
                break;
        }

        switch (WeekDay) {

            case 0:
                strWeekDay = "يکشنبه";
                break;
            case 1:
                strWeekDay = "دوشنبه";
                break;
            case 2:
                strWeekDay = "سه شنبه";
                break;
            case 3:
                strWeekDay = "چهارشنبه";
                break;
            case 4:
                strWeekDay = "پنج شنبه";
                break;
            case 5:
                strWeekDay = "جمعه";
                break;
            case 6:
                strWeekDay = "شنبه";
                break;
        }

    }

    public static String getCurrentSolarDate() {
        Locale loc = new Locale("en_US");
        SolarCalendar sc = new SolarCalendar();
        return sc.year + "/" + String.format(loc, "%02d",
                sc.month) + "/" + String.format(loc, "%02d", sc.date);
    }

}


