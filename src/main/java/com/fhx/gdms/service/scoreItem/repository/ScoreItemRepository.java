package com.fhx.gdms.service.scoreItem.repository;

import login.com.scoreItem.model.ScoreItemModel;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface ScoreItemRepository {

    @InsertProvider(type = ScoreItemProvider.class, method = "save")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(ScoreItemModel model);

    @Select("SELECT * FROM tb_score_item WHERE id = #{id}")
    @Results(id = "scoreItemMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class),
            @Result(column = "item_name", property = "itemName", javaType = String.class),
            @Result(column = "score_rate", property = "scoreRate", javaType = Integer.class),
            @Result(column = "introduce", property = "introduce", javaType = String.class),
            @Result(column = "status", property = "status", javaType = Integer.class),
            @Result(column = "type", property = "type", javaType = Integer.class),

            @Result(column = "create_time", property = "createTime", javaType = Date.class),
            @Result(column = "update_time", property = "updateTime", javaType = Date.class),
    })
    ScoreItemModel findById(@Param("id") Integer id);

    @Select("SELECT * FROM tb_score_item WHERE  item_name = #{itemName}")
    @ResultMap(value = "scoreItemMap")
    ScoreItemModel findByItemName(@Param("itemName") String itemName);

    @Select("SELECT * FROM tb_score_item ORDER BY create_time, status DESC")
    @ResultMap(value = "scoreItemMap")
    List<ScoreItemModel> listAll();

    @Update("UPDATE tb_score_item SET status = #{status} WHERE id = #{id}")
    @ResultMap(value = "scoreItemMap")
    Integer updateStatus(ScoreItemModel model);

    @Select("SELECT * FROM tb_score_item WHERE type = #{type} AND status = 1 ORDER BY create_time DESC")
    @ResultMap(value = "scoreItemMap")
    List<ScoreItemModel> findAlive(@Param("type") Integer type);

    @UpdateProvider(type = ScoreItemProvider.class, method = "updateModel")
    @ResultMap(value = "scoreItemMap")
    void updateItem(ScoreItemModel model);

    @Select("SELECT type, sum(score_rate) as sum FROM tb_score_item WHERE status = 1 GROUP BY type")
    List<Map<String, Integer>> countRate();


    /********** 内部类 *********/
    class ScoreItemProvider {
        public String save(ScoreItemModel model) {
            SQL sql = new SQL();
            sql.INSERT_INTO("tb_score_item");
            sql.VALUES("item_name", "#{itemName}");
            sql.VALUES("score_rate", "#{scoreRate}");
            sql.VALUES("introduce", "#{introduce}");
            sql.VALUES("status", "0");
            sql.VALUES("type", "#{type}");

            sql.VALUES("update_time", "now()");
            sql.VALUES("create_time", "now()");
            return sql.toString();
        }

        public String updateModel(ScoreItemModel model) {
            SQL sql = new SQL();
            sql.UPDATE("tb_score_item");
            if (model.getItemName() != null && !"".equals(model.getItemName())) {
                sql.SET("item_name = #{itemName}");
            }
            if (model.getIntroduce() != null && !"".equals(model.getIntroduce())) {
                sql.SET("introduce = #{introduce}");
            }
            if (model.getScoreRate() != null && !"".equals(model.getScoreRate())) {
                sql.SET("score_rate = #{scoreRate}");
            }
            if (model.getType() != null && !"".equals(model.getType())) {
                sql.SET("type = #{type}");
            }

            sql.WHERE("id = #{id}");
            return sql.toString();
        }
    }
}
