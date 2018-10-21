package com.springboot.environment.controller;

/**
 * Created by yww on 2018/10/13.
 */

import com.springboot.environment.bean.Norm;
import com.springboot.environment.bean.Permission;
import com.springboot.environment.bean.Role;
import com.springboot.environment.service.PermissionService;
import com.springboot.environment.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value="/permission")
@Api("权限类相关api")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value="返回所有权限的信息",notes = "所有权限的信息")

    @GetMapping(value = "/getall")
    public List< Object[]> getAll(){
        return permissionService.getAll();
    }

    @ApiOperation(value="增加某一个权限",notes = "需要定义权限名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "permission_name",value="权限名称",dataType = "String")
    }
    )
    @RequestMapping(value = "/addone/{permission_name}",method = RequestMethod.GET)
    @ResponseBody
    public void addOne(@PathVariable String permission_name){
        Permission permission=new Permission();
        permission.setPermission_name(permission_name);
        permissionService.addOne(permission);
    }

    @ApiOperation(value="修改某一个权限",notes = "需要定义权限id，权限名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "permission_id",value="权限id",dataType = "String"),
            @ApiImplicitParam(name = "permission_name",value="权限名称",dataType = "String")
    }
    )
    @RequestMapping(value = "/updateone/{permission_id}/{permission_name}",method = RequestMethod.GET)
    @ResponseBody
    public void updateOne(@PathVariable String permission_id,
                          @PathVariable String permission_name){
        permissionService.updateOne(permission_id,permission_name);
    }

    @ApiOperation(value="删除某一个权限",notes = "根据权限的id进行权限删除，同时删除user_role role_permission")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "permission_id",value="权限id",dataType = "String")
    }
    )
    @RequestMapping(value="/deleteone/{permission_id}",method = RequestMethod.GET)
    @ResponseBody
    public void deleteOne(@PathVariable String permission_id){
        permissionService.delOne(permission_id);
    }


}

