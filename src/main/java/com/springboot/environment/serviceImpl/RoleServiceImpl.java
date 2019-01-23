package com.springboot.environment.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.springboot.environment.bean.Role;
import com.springboot.environment.dao.RoleDao;
import com.springboot.environment.service.RoleService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by yww on 2018/9/11.
 */
@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List< Object[]> getAll() {
        return roleDao.getAll();
    }

    @Override
    public void addOne(Role role) {
        roleDao.save(role);
    }

    @Override
    public void delOne(String role_id) {
        roleDao.deleteRoleUser(Integer.parseInt(role_id));
        roleDao.deleteRolePermission(Integer.parseInt(role_id));
        roleDao.deleteById(Integer.parseInt(role_id));
    }

    @Override
    public void updateOne(String role_id, String role_name) {
        roleDao.updateOne(Integer.parseInt(role_id),role_name);
    }

    @Override
    public List<Role> getRoleByUserID(String user_id) {
        List<Role> roles = roleDao.getRoleByUserID(user_id);
        return roles;
    }

    @Override
    public Map addRole(Map params) {
        String type=(String)params.get("type");
        String name=(String)params.get("name");
        String describe=(String)params.get("describe");
        Map<String,String> resultMap=new LinkedHashMap<String,String>();
        if(type.equals("add")){
            //判断这个name是否存在
            if(roleDao.findByName(name)==null){
                int t=roleDao.addOne(name,describe);
                System.out.println(t);
                if(t==1){
                    resultMap.put("addRoleFlag","true");
                }else{
                    resultMap.put("addRoleFlag","false");
                }
            }else{
                System.out.println("此角色名已经存在");
                resultMap.put("addRoleFlag","false");
            }


        }else if(type.equals("edit")){
            String id=(String)params.get("id");
            int flag=roleDao.updateOne2(Integer.parseInt(id),name,describe);
            if(flag==1){
                resultMap.put("editRoleFlag","true");
            }else{
                resultMap.put("editRoleFlag","false");
            }
        }
        return resultMap;
    }

    @Override
    public Map rolePermissionDistribute(Map params) {
        Map<String,String> resultMap=new LinkedHashMap<String,String>();
        String role_id=(String)params.get("role_id");
        String role_permissions=(params.get("role_permissions")+"").replaceAll("[\\[\\]]", "");
        System.out.println(role_permissions);
        int flag=roleDao.updatePermission(role_id,role_permissions);
        if(flag==1){
            resultMap.put("authorizeFlag","true");
        }else{
            resultMap.put("authorizeFlag","false");
        }
        return resultMap;
    }

    @Override
    public Map deleteRole(Map params) {
        Map<String,String> resultMap=new LinkedHashMap<String,String>();
        List<String> role_idList=(List)params.get("deleteRoleList");
        int count = 0;
        for(int i =0;i<role_idList.size();i++){
            //先判断有没有这个role_id
            if(roleDao.getRoleByRoleID(Integer.parseInt(role_idList.get(i)))!=null){
                int flagRoleUser=0;
                //判断roleUser是否有这个role
                if(roleDao.isHasRoleInRoleUser(Integer.parseInt(role_idList.get(i)))!=0){
                    flagRoleUser = roleDao.deleteRoleUserByRoleID(Integer.parseInt(role_idList.get(i)));
                    if(flagRoleUser==1){
                        int flag=roleDao.deleteOne(Integer.parseInt(role_idList.get(i)));
                        if(flag==1){
                            count++;
                        }
                    }
                }else{
                    int flag=roleDao.deleteOne(Integer.parseInt(role_idList.get(i)));
                    if(flag==1){
                        count++;
                    }
                }
                if(count==role_idList.size()){
                    resultMap.put("deleteFlag","true");
                }else{
                    resultMap.put("deleteFlag","false");
                }
            }else{
                System.out.println("不存在此角色，无法删除");
                resultMap.put("deleteFlag","false");
            }


            }

        return resultMap;
    }

    @Override
    public Map queryRole() {
        Map<String,List> resultMap=new LinkedHashMap<String,List>();
        List<Role> roles= roleDao.getAllRoles();
        List<Map> list=new ArrayList<Map>();
        for(int i=0;i<roles.size();i++){
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",roles.get(i).getRole_id()+"");
            map.put("name",roles.get(i).getRole_name());
            map.put("describe",roles.get(i).getDescribe());
            list.add(map);
        }
        resultMap.put("roleList",list);
        return resultMap;
    }

    @Override
    public Map queryRoleByRoleID(Map params) {
        Map<String,Object> resultMap=new LinkedHashMap<String,Object>();
        String selectedRoleId = (String)params.get("selectedRoleId");
        if(selectedRoleId.equals("*")){
            List<Map> list=new ArrayList<Map>();
            List<Role> roles= roleDao.getAllRoles();
            if(roles!=null){
                for(int i=0;i<roles.size();i++){
                    Map<String,Object> map=new HashMap<String, Object>();
                    map.put("id",roles.get(i).getRole_id()+"");
                    map.put("name",roles.get(i).getRole_name());
                    map.put("describe",roles.get(i).getDescribe());
                    if(roles.get(i).getPermission_list()!=null){
                        String[] permissions=roles.get(i).getPermission_list().split(",");
                        List<String> permissionList=new ArrayList<String>();
                        for(int j=0;j<permissions.length;j++){
                            permissionList.add(permissions[j]);
                        }
                        map.put("permissions",permissionList);
                    }else{
                        map.put("permissions","");
                    }
                    list.add(map);
                }
                resultMap.put("roleList",list);
            }else{
                resultMap.put("queryFlag","false");
                System.out.println("不存在任何角色，无法查询");
            }

        }else{
            if(roleDao.getRoleByRoleID(Integer.parseInt(selectedRoleId))!=null){
                Role role=roleDao.getRoleByRoleID(Integer.parseInt(selectedRoleId));
                List<Map> list=new ArrayList<Map>();
                Map<String,Object> map=new HashMap<String, Object>();
                System.out.println("role"+role);
                map.put("id",role.getRole_id()+"");
                map.put("name",role.getRole_name());
                map.put("describe",role.getDescribe());
                List<String> permissionsList=new ArrayList<String>();
                System.out.println(role.getPermission_list());
                if(role.getPermission_list()!=null&&role.getPermission_list()!=""){
                    String[] permissions=role.getPermission_list().split(",");
                    System.out.println("permissions"+permissions);
                    for(int i =0;i<permissions.length;i++){
                        permissionsList.add(permissions[i]);
                    }
                }
                map.put("permissions",permissionsList);
                list.add(map);
                resultMap.put("roleList",list);
            }else{
                resultMap.put("queryFlag","false");
                System.out.println("此角色不存在，无法查询");
            }
        }

        return resultMap;
    }

    @Override
    public Map updateMenulistAndPermissionList(Map params) {
        String role_id=(String)params.get("role_id");
        System.out.println(role_id);
        String role_permissions=(params.get("role_permissions")+"").replaceAll("[\\[\\]]", "");
        System.out.println(role_permissions);
        String menu_list=params.get("menu_list")+"";
        ArrayList list=(ArrayList)params.get("menu_list");
        JSONArray array=new JSONArray(list);
        System.out.println(array.toJSONString());
        Map<String,Object> resultMap=new LinkedHashMap<String,Object>();
        int flag=roleDao.updatePermissionAndMenuList(role_id,role_permissions,array.toJSONString());
        if(flag==1){
            resultMap.put("authorizeFlag","true");
        }else{
            resultMap.put("authorizeFlag","false");
        }
        return resultMap;
    }
}
