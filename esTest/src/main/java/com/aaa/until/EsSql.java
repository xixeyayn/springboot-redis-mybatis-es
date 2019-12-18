package com.aaa.until;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/*  @  时间    :  2019/12/18 16:16:24
 *  @  类名    :  EsSql
 *  @  创建人  :  Xie
 *  @  描述    :
 *
 */
public class EsSql {
    //将实体类中的信息拆分为map
    public static Map<String,Object> objectMap(Object object) {
        Map<String, Object> result = new HashMap<String, Object>();
        Field[] fields = object.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                String name = new String(field.getName());
                result.put(name, field.get(object));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }
}
