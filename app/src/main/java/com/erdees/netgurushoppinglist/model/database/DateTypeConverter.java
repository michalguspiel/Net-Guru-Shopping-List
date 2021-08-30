package com.erdees.netgurushoppinglist.model.database;


import java.util.Date;

public class DateTypeConverter {
    @androidx.room.TypeConverter
    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @androidx.room.TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }
}