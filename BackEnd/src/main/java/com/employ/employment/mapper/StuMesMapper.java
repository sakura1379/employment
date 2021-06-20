package com.employ.employment.mapper;

import com.employ.employment.entity.SoMap;
import com.employ.employment.entity.StuMes;
import com.employ.employment.entity.StuMesForReturn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
/**
 * mailInfo
 */

public interface StuMesMapper {
//    /**
//     * 增加公告信息
//     * @param s 实体对象
//     * @return 受影响行数
//     */
//    int addAnnouncement(StuMes s);

    /**
     * 增
     * @param s 实体对象
     * @return 受影响行数
     */
    int add(StuMes s);

    /**
     * 删特定信箱中特定信息
     * @param mailNum 要删除的信箱id
     * @return 受影响行数
     */
    int deleteOne(long mailNum);

    /**
     * 删所有信箱中特定信息
     * @param infoId 要删除的信息id
     * @return 受影响行数
     */
    int deleteAll(long infoId);

    /**
     * 改
     * @param pvsTb 临时表名称
     * @return 受影响行数
     */
    int createTempTable(String pvsTb);

    /**
     * 删
     * @param pvsTb 临时表名称
     * @return 受影响行数
     */
    int dropTempTable(String pvsTb);

    /**
     * 改
     * @param pvsTb 临时表名称
     * @return 受影响行数
     */
    int updateTempTable1(String pvsTb);

    /**
     * 改
     * @param pvsTb 临时表名称
     * @param infoId 信息编号
     * @param infoType 信息种类
     * @return 受影响行数
     */
    int updateTempTable2(@Param("pvsTb") String pvsTb, @Param("infoId") long infoId, @Param("infoType") long infoType);

    /**
     * 改
     * @param pvsTb 临时表名称
     * @param compId 当前登录企业ID
     * @return 受影响行数
     */
    int updateTempTable3(@Param("pvsTb") String pvsTb, @Param("compId") long compId);

    /**
     * 改
     * @param pvsTb 临时表名称
     * @return 受影响行数
     */
    int updateTable(String pvsTb);

    /**
     * 改
     * @param s 实体对象
     * @return 受影响行数
     */
    int update(StuMes s);

    /**
     * 查 - 根据id
     * @param infoId 要查询的信件id
     * @return 实体对象
     */
    StuMes getById(long infoId);

    /**
     * 查集合 - 根据条件（参数为空时代表忽略指定条件）
     * @param stuNum 要查询的学生id
     * @return 数据列表
     */
    List<StuMes> getList(long stuNum);

    /**
     * 查集合
     * @param stuNum
     * @return
     */
    List<StuMesForReturn> getStuList(long stuNum);
    
        /**
     * 查信息编号 -
     * @param mailNum 要查询的信箱id
     * @return infoId
     */
    long getInfoId(@Param("mailNum")long mailNum);
}
