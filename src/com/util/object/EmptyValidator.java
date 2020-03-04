package com.util.object;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class EmptyValidator {

    public static boolean isNotEmpty(Object object){

        if (object instanceof String){
            String str = (String)object;
            if (str == null || "".equals(str.trim())){
                return false;
            }else{
                return true;
            }
        }else if(object instanceof List){
            List list = (List)object;
            if (list == null || list.size() == 0){
                return false;
            }else{
                return true;
            }
        }else if(object instanceof Map){
            Map map = (Map)object;
            if (map == null || map.size() == 0){
                return false;
            }else{
                return true;
            }
        }else if(object instanceof Set){
            Set set = (Set)object;
            if (set == null || set.size() == 0){
                return false;
            }else {
                return true;
            }
        }else{
            if (object != null ){
                return true;
            }
        }
        return false;
    }

}
