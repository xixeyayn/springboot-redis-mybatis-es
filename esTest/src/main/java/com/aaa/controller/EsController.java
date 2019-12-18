package com.aaa.controller;

import com.aaa.until.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/*  @  时间    :  2019/12/18 17:11:14
 *  @  类名    :  EsControllwe
 *  @  创建人  :  Xie
 *  @  描述    :
 *
 */
@RestController
public class EsController {

@RequestMapping("/test_create")
    public Map<String,Object> create(){
        Map<String, Object> test_index1 = EsService.createIndex("test_index1");
        return test_index1;
    }
    @RequestMapping("/test_addDate")
    public Map<String,Object> addDate(){
        Map<String, Object> map = new HashMap<>();
        map.put("name","xie");
        map.put("bb","xie");
         map = EsService.addData(map,"test_index1","index1","1");
        return map;
    }
    @RequestMapping("/test_update")
    public Map<String,Object> update(){
        Map<String, Object> map = new HashMap<>();
        map.put("name","xie1");
        map.put("bb","xie1");
        map = EsService.updateData(map,"test_index1","index1","1");
        return map;
    }
    @RequestMapping("/test_delete")
    public Map<String,Object> delete(){
        Map<String, Object> map = new HashMap<>();
        map = EsService.deleteDataById("test_index1","index1","1");
        return map;
    }
    @RequestMapping("/test_select")
    public Map<String,Object> select(){
        Map<String, Object> map = new HashMap<>();
        map = EsService.seleDateByid("test_index1","index1","1",null);
        return map;
    }
}
