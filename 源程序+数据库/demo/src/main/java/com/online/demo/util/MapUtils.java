package com.online.demo.util;

import com.online.demo.entity.ParentModule;
import com.online.demo.entity.SonModules;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapUtils {
    /**
     * 获取map中的数据
     * @param data
     * @param key
     * @return
     */
    public static String getMapNullString(Map data,String key){
        return data == null ? "" : data.get(key) == null ? "" : data.get(key).toString();
    }

    /**
     * 合并模块信息
     * @param modules
     * @return
     */
    public static List<ParentModule> getModilesInfo(List<Map<String,Object>> modules, HttpServletRequest request){
        List<ParentModule> roleName=new ArrayList<>();
        for (Map<String,Object> temp : modules){
            ParentModule p=new ParentModule(MapUtils.getMapNullString(temp,"t_p_id"),MapUtils.getMapNullString(temp,"t_p_name"));
            if(roleName.contains(p)){
                for (int i=0;i<roleName.size();i++){
                    if(roleName.get(i).equals(p)){
                        roleName.get(i).getSon().add(new SonModules(MapUtils.getMapNullString(temp,"t_s_id"),MapUtils.getMapNullString(temp,"t_s_name"),request.getContextPath()+MapUtils.getMapNullString(temp,"t_s_url"),MapUtils.getMapNullString(temp,"t_parent_id")));
                        break;
                    }
                }
            }else{
                List<SonModules> list=new ArrayList<>();
                if(!MapUtils.getMapNullString(temp,"t_s_id").equals(""))
                    list.add(new SonModules(MapUtils.getMapNullString(temp,"t_s_id"),MapUtils.getMapNullString(temp,"t_s_name"),request.getContextPath()+MapUtils.getMapNullString(temp,"t_s_url"),MapUtils.getMapNullString(temp,"t_parent_id")));
                p.setSon(list);
                roleName.add(p);
            }
        }
        return roleName;
    }
}
