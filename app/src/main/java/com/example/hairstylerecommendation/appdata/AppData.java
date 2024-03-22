// AppData.java
package com.example.hairstylerecommendation.appdata;

import android.graphics.Bitmap;

import com.example.hairstylerecommendation.model.Haircut;
import com.example.hairstylerecommendation.model.HaircutTree;
import com.example.hairstylerecommendation.model.HistoryCut;
import com.example.hairstylerecommendation.model.PopularHaircut;
import com.example.hairstylerecommendation.model.UserProfiling;

import java.util.ArrayList;
import java.util.List;

public class AppData {
    public static int hairSizeId = -1;
    public static int hairTypeId = -1;
    public static int hairColorId = -1;
    public static int hairCutNameId = -1;

    public static String hairType;
    public static String hairColor;
    public static String hairSize;
    public static String hairCutName;
    public static Bitmap userPicture = null;
    public static String userFaceShape = "";
    public static List<Haircut> haircutList = new ArrayList<>();
    public static List<HistoryCut> historyCutList = new ArrayList<>();
    public static List<PopularHaircut> popularHaircutList = new ArrayList<>();
    public static UserProfiling userInfo = new UserProfiling();
    public static List<HaircutTree> cutCount = new ArrayList<>();
    public static List<HaircutTree> recommendHaircutList = new ArrayList<>();
    public static String userId;

    public static void resetData() {
        hairSizeId = 0;
        hairTypeId = 0;
        hairColorId = 0;
        hairCutNameId = 0;
        hairType = "";
        hairColor = "";
        hairSize = "";
        hairCutName = "";
        userFaceShape = "";
        userPicture = null;
        haircutList = new ArrayList<>();
        historyCutList = new ArrayList<>();
        popularHaircutList = new ArrayList<>();
        cutCount = new ArrayList<>();
        recommendHaircutList = new ArrayList<>();
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        AppData.userId = userId;
    }
}
