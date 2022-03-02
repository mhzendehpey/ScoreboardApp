package com.mxz.board.entities;

public class TableIntervals {

    public enum TableIntervalEnum {
        LastNight,
//        LastTwoWeeks,
//        ThisMonth,
//        ThisSeason,
        AllTime
    }

    public static final String[] STRINGS = {"Last Night",
//            "Last Two Games",
//            "This Month",
//            "This Season",
            "All Time"};

    public static String ToString(TableIntervalEnum intervalEnum) {
        switch (intervalEnum) {
            case AllTime:
                return "All Time";
//            case ThisSeason:
//                return "This Season";
//            case ThisMonth:
//                return "This Month";
//            case LastTwoWeeks:
//                return "Last Two Games";
            case LastNight:
                return "Last Night";
            default:
                return "";
        }
    }

    public static TableIntervalEnum GetEnum(String name) {
        switch (name) {
            case "All Time":
                return TableIntervalEnum.AllTime;
//            case "This Season":
//                return TableIntervalEnum.ThisSeason;
//            case "This Month":
//                return TableIntervalEnum.ThisMonth;
//            case "Last Two Games":
//                return TableIntervalEnum.LastTwoWeeks;
            case "Last Night":
                return TableIntervalEnum.LastNight;
            default:
                return null;
        }
    }

}