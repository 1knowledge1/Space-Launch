package ru.easyspace.spacelaunch.spacepicture;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

@Entity(tableName = "space_picture_table")
public class SpacePictureJSON {
        @NonNull
        @PrimaryKey
        @ColumnInfo(name = "title")
        public String title;
        @ColumnInfo(name = "copyright")
        public String copyright;
        @ColumnInfo(name = "date")
        public String date;
        @ColumnInfo(name = "explanation")
        public String explanation;
        @ColumnInfo(name = "hdurl")
        public String hdurl;
        @ColumnInfo(name = "mediaType")
        public String mediaType;
        @ColumnInfo(name = "serviceVersion")
        public String serviceVersion;
        @ColumnInfo(name = "url")
        public String url;
}
