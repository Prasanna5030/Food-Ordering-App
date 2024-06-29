package com.sl.foodorderingsystem.utils;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sl.foodorderingsystem.entity.Role;
import com.sl.foodorderingsystem.entity.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AppUtils {

    public static String getUUID() {
        Date date = new Date();
        long time = date.getTime();
        return "Bill-" + time;
    }

    public static JSONArray getJsonArrayFromString (String data) throws JSONException
    {
    JSONArray jsonArray = new JSONArray(data);
    return jsonArray;
    }

    public static Map<String , Object> getMapFromJson(String data){
        if(!Strings.isNullOrEmpty(data))
            return new Gson().fromJson(data, new TypeToken<Map<String,Object>>(){

            }.getType());
        return new HashMap<>();

    }


    public static  boolean isFileExists(String filePath){
        try{
            File file = new File(filePath);
            return (file!=null && file.exists()) ? Boolean.TRUE : Boolean.FALSE;
        }catch (Exception e){
            e.printStackTrace();
    }
return Boolean.FALSE;
        }
}
