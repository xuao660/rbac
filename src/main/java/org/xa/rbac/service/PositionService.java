package org.xa.rbac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.xa.rbac.mapper.PositionMapper;
import org.xa.rbac.model.Position;

import java.util.Date;
import java.util.List;

/**
 * (Position)表服务接口
 *
 * @author makejava
 * @since 2022-03-17 11:47:54
 */
@Service
public class PositionService {

    @Autowired
    public PositionMapper positionMapper;

    public List<Position> getAllPositions(){
        return positionMapper.getAllPositions();
    }
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public Position queryById(Integer id) {
        return this.positionMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param position 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    public Page<Position> queryByPage(Position position, PageRequest pageRequest) {
        long total = this.positionMapper.count(position);
        return new PageImpl<>(this.positionMapper.queryAllByLimit(position, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param position 实例对象
     * @return 实例对象
     */
    public Integer insert(Position position) {
        position.setCreateDate(new Date());
        position.setEnabled(true);
        return positionMapper.insert(position);
    }

    /**
     * 修改数据
     *
     * @param position 实例对象
     * @return 实例对象
     */
    public Integer update(Position position) {

        return positionMapper.update(position);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */

    public Integer deleteById(Integer id) {
        return this.positionMapper.deleteById(id) ;
    }


    //批量删除
    public Integer deletePositionByIds(Integer[] ids){

        return positionMapper.deletePositionByIds(ids);
    }
}
