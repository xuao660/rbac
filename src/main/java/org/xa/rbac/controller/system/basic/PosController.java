package org.xa.rbac.controller.system.basic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xa.rbac.model.Position;
import org.xa.rbac.model.RespBean;
import org.xa.rbac.service.PositionService;

import java.util.List;

@RestController
@RequestMapping("/sys/basic/pos")
public class PosController {

    @Autowired
    PositionService positionService;


    @GetMapping("/")
    public List<Position> getAllPositions(){
        return positionService.getAllPositions();
    }

    @PostMapping("/")
    public RespBean addPosition(@RequestBody Position position){
        if(positionService.insert(position)==1){
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }
    @PutMapping("/")
    public RespBean update(@RequestBody Position position){
        System.out.println("updatePositions:"+position.getName()+" "+position.getId());

        if(positionService.update(position)==1){
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }
    @DeleteMapping("/{id}")
    public RespBean delete(@PathVariable Integer id){
        if (positionService.deleteById(id)==1) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }
    @DeleteMapping("/")
    public RespBean deletePositionByIds(Integer[] ids){
        System.out.println("ids:"+ids.length);
        if(positionService.deletePositionByIds(ids)==ids.length){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

}
