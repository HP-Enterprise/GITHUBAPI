package com.incar.gitApi.util;

import com.incar.gitApi.entity.WorkDetail;
import org.hibernate.mapping.Array;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/15.
 */
public class MapUtil {
    public  static Map<Object,Object[]> putToMap(List<WorkDetail> list){
        Map<Object,Object[]> map = new HashMap<>();
        Object[] str1= new Object[list.size()];
        Object[] str2= new Object[list.size()];
        Object[] str3= new Object[list.size()];
        for (int i=0;i<list.size();i++) {
            str1[i] = list.get(i).getId().toString();
            str2[i] = list.get(i).getExpectedTime();
            str3[i] = list.get(i).getActualTime();
        }
        map.put("category",str1);
        map.put("expect_data",str2);
        map.put("actual_data",str3);
        return map;
    }

}
