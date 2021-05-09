package com.employ.employment.mapper;

import com.employ.employment.entity.SoMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 公用Mapper，封装一些常见的Mapper操作，避免某些及其简单的逻辑也要写一堆xml方法
 * 更新于2020-12-1 新增部分方法
 *
 */
@Mapper
@Component
public interface PublicMapper {


	// ------------------------ 一些工具方法 ------------------------

	/**
	 * 返回上一句SQL插入的自增主键值
	 * @return
	 */
	long getPrimarykey();


	// ------------------------ 新增SQL相关 ------------------------


	// ------------------------ 删除SQL相关 ------------------------

	/**
	 * 根据id删除一条记录
	 * @param tableName 表名字
	 * @param id id值
	 * @return
	 */
	int deleteById(
			@Param("tableName") String tableName,
			@Param("id") Object id
	);

	/**
	 * 根据指定列指定值删除一条记录
	 * @param tableName 表名
	 * @param whereName 条件列
	 * @param whereValue 条件列值
	 * @return
	 */
	int deleteBy(
			@Param("tableName") String tableName,
			@Param("whereName") String whereName,
			@Param("whereValue") Object whereValue
	);

	/**
	 * 根据id列表批量删除
	 * @param tableName 表名字
	 * @param ids id列表
	 * @return
	 */
	int deleteByIds(
			@Param("tableName") String tableName,
			@Param("ids") List<?> ids
	);

	/**
	 * 根据指定列指定值删除多条记录
	 * @param tableName 表名
	 * @param whereName 条件列名
	 * @param whereList 条件列值
	 * @return
	 */
	int deleteByWhereList(
			@Param("tableName") String tableName,
			@Param("whereName") String whereName,
			@Param("whereList") List<?> whereList
	);


	// ------------------------ 修改SQL相关 ------------------------

	/**
	 * 指定表的指定字段增加指定值，可以为负值
	 * @param tableName 表名字
	 * @param columnName 列值
	 * @param num 增加的值
	 * @param id id值
	 * @return
	 */
	int columnAdd(
			@Param("tableName") String tableName,
			@Param("columnName") String columnName,
			@Param("num") long num,
			@Param("id") Object id
	);

	/**
	 * 指定表的指定字段增加指定值，可以为负值
	 * @param tableName 表名字
	 * @param columnName 列名字
	 * @param num 增加的值
	 * @param ids id列表
	 * @return
	 */
	int columnAddByIds(
			@Param("tableName") String tableName,
			@Param("columnName") String columnName,
			@Param("num") long num,
			@Param("ids") List<?> ids
	);

	/**
	 * 指定表的指定字段更新为指定值,根据指定id
	 * @param tableName 表名子
	 * @param columnName 列名
	 * @param value 值
	 * @param id id值
	 * @return
	 */
	int updateColumnById(
			@Param("tableName") String tableName,
			@Param("columnName") String columnName,
			@Param("value") Object value,
			@Param("id") Object id
	);

	/**
	 * 指定表的指定字段更新为指定值,根据指定id列表
	 * @param tableName 表名子
	 * @param columnName 列名
	 * @param value 值
	 * @param ids id值
	 * @return
	 */
	int updateColumnByIds(
			@Param("tableName") String tableName,
			@Param("columnName") String columnName,
			@Param("value") Object value,
			@Param("ids") List<?> ids
	);

	/**
	 * 指定表的指定字段更新为指定值, 根据指定列的指定值
	 * @param tableName 表名
	 * @param columnName 列名
	 * @param columnValue 列值
	 * @param whereName 条件列名
	 * @param whereValue 条件列值
	 * @return
	 */
	int updateColumnBy(
			@Param("tableName") String tableName,
			@Param("columnName") String columnName,
			@Param("columnValue") Object columnValue,
			@Param("whereName") String whereName,
			@Param("whereValue") Object whereValue
	);

	/**
	 * 指定表的指定字段SoMap集合更新为指定值,根据指定id
	 * @param tableName 表名
	 * @param soMap 要修改的列
	 * @param id id值
	 * @return
	 */
	int updateBySoMapById(
			@Param("tableName") String tableName,
			@Param("soMap") SoMap soMap,
			@Param("id") Object id
	);

	/**
	 * 指定表的指定字段SoMap集合更新为指定值,指定列的指定值
	 * @param tableName 表名子
	 * @param soMap 要修改的列
	 * @param whereName 条件列值
	 * @param whereValue 条件列值
	 * @return
	 */
	int updateBySoMapBy(
			@Param("tableName") String tableName,
			@Param("soMap") SoMap soMap,
			@Param("whereName") String whereName,
			@Param("whereValue") Object whereValue
	);


	// ------------------------ 查询SQL相关 ------------------------

	/**
	 * 获取指定表的指定字段值,根据id值
	 * @param tableName 表名
	 * @param columnName 列名
	 * @param id id值
	 * @return
	 */
	String getColumnById(
			@Param("tableName") String tableName,
			@Param("columnName") String columnName,
			@Param("id") Object id
	);

	/**
	 * 获取指定表的指定字段值,并转化为long,根据id值
	 * @param tableName 表名
	 * @param columnName 列名
	 * @param id id值
	 * @return
	 */
	long getColumnByIdToLong(
			@Param("tableName") String tableName,
			@Param("columnName") String columnName,
			@Param("id") Object id
	);

	/**
	 * 获取指定表的指定字段值,根据指定条件(whereName=whereValue)
	 * @param tableName 表名
	 * @param columnName 列名
	 * @param whereName 条件列名
	 * @param whereValue 条件列值
	 * @return
	 */
	String getColumnByWhere(
			@Param("tableName") String tableName,
			@Param("columnName") String columnName,
			@Param("whereName") String whereName,
			@Param("whereValue") Object whereValue
	);

	/**
	 * 获取指定表的指定字段值列表,并转化为long, 根据指定条件(whereName=whereValue)
	 * @param tableName 表名
	 * @param columnName 列名
	 * @param whereName 条件列名
	 * @param whereValue 条件列值
	 * @return
	 */
	List<Long> getColumnListToLongByWhere(
			@Param("tableName") String tableName,
			@Param("columnName") String columnName,
			@Param("whereName") String whereName,
			@Param("whereValue") Object whereValue
	);

	/**
	 * 获取指定表的count数据,根据指定条件(whereName=whereValue)
	 * @param tableName 表名
	 * @param whereName 条件列名
	 * @param whereValue 条件列值
	 * @return
	 */
	long getCountBy(
			@Param("tableName") String tableName,
			@Param("whereName") String whereName,
			@Param("whereValue") Object whereValue
	);

	// ------------------------ 查询集合SQL相关 ------------------------

	/**
	 * 获取指定表的全部字段全部数据转化为Map
	 * @param tableName 表名子
	 * @return
	 */
	List<SoMap> getListMap(@Param("tableName") String tableName);

	/**
	 * 获取指定表的全部字段全部数据转化为Map, 根据指定条件(whereName=whereValue)
	 * @param tableName 表名字
	 * @param whereName 条件列名
	 * @param whereValue 条件列值
	 * @return
	 */
	List<SoMap> getListMapByWhere(
			@Param("tableName") String tableName,
			@Param("whereName") String whereName,
			@Param("whereValue") Object whereValue
	);

	/**
	 * 获取指定表的全部字段全部数据转化为Map, 根据指定条件(id=id)
	 * @param tableName 表名子
	 * @param id id值
	 * @return
	 */
	List<SoMap> getListMapById(
			@Param("tableName") String tableName,
			@Param("id") Object id
	);


}
