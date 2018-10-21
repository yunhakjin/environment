package com.springboot.environment.controller;

/**
 * Created by yww on 2018/10/13.
 */

import com.springboot.environment.bean.Group;
import com.springboot.environment.bean.Norm;
import com.springboot.environment.bean.Role;
import com.springboot.environment.service.GroupService;
import com.springboot.environment.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/group")
@Api("用户组类相关api")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @ApiOperation(value="返回所有用户组的信息",notes = "所有用户组的信息")
    @GetMapping(value = "/getall")
    public List< Object[]> getAll(){
        return groupService.getAll();
    }

    @ApiOperation(value="增加某一个用户组",notes = "需要定义用户组名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "group_name",value="用户组名称",dataType = "String")
    }
    )
    @RequestMapping(value = "/addone/{group_name}",method = RequestMethod.GET)
    @ResponseBody
    public void addOne(@PathVariable String group_name,@PathVariable String group_detail){
        Group group=new Group();
        group.setGroup_name(group_name);
        group.setGroup_detail(group_detail);
        groupService.addOne(group);
    }

    @ApiOperation(value="修改某一个用户组",notes = "需要参数：用户组id，用户组名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "group_id",value="用户组id",dataType = "String"),
            @ApiImplicitParam(name = "group_name",value="用户组名称",dataType = "String"),
            @ApiImplicitParam(name = "group_detail",value="用户组详情",dataType = "String")
    }
    )
    @RequestMapping(value = "/updateone/{group_id}/{group_name}/{group_detail}",method = RequestMethod.GET)
    @ResponseBody
    public void updateOne(@PathVariable String group_id,
                          @PathVariable String group_name,
                          @PathVariable String group_detail){
        groupService.updateOne(group_id,group_name,group_detail);
    }

    @ApiOperation(value="删除某一个用户组",notes = "根据用户组的id进行用户组删除，同时删除user_group")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "group_id",value="用户组id",dataType = "String")
    }
    )
    @RequestMapping(value="/deleteone/{group_id}",method = RequestMethod.GET)
    @ResponseBody
    public void deleteOne(@PathVariable String group_id){
        groupService.delOne(group_id);
    }


}
