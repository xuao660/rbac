package org.xa.rbac.mapper;

import org.xa.rbac.model.Position;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (Position)表数据库访问层
 *
 * @author makejava
 * @since 2022-03-17 11:47:45
 */
public interface PositionMapper {


    List<Position> getAllPositions();



    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Position queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param position 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<Position> queryAllByLimit(Position position, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param position 查询条件
     * @return 总行数
     */
    long count(Position position);

    /**
     * 新增数据
     *
     * @param position 实例对象
     * @return 影响行数
     */
    int insert(Position position);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Position> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Position> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Position> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Position> entities);

    /**
     * 修改数据
     *
     * @param position 实例对象
     * @return 影响行数
     */
    int  update(Position position);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);


    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键集合
     * @return 影响行数
     */
    //@Param("ids")的作用
    Integer deletePositionByIds(@Param("ids") Integer[] ids);
}

