package com.alkaidmc.alkaid.mongodb.interfaces;

import java.util.List;
import java.util.Map;

public interface WriteableActions {
    /**
     * 创建一个数据文档
     *
     * @param collection 数据集合名称
     * @param data       数据实体
     */
    void create(String collection, Object data);

    /**
     * 创建一打数据集合
     *
     * @param collection 数据集合名称
     * @param data       数据实体
     */
    void create(String collection, List<Object> data);

    /**
     * 更新一些数据文档
     *
     * @param collection 数据集合名称
     * @param index      数据索引 需要 new 一个目标对应的 Map kv 值 仅写入索引数据 将自动处理为 Bson
     * @param data       数据实体 需要更新进入数据库的数据实体
     */
    void update(String collection, Map<String, Object> index, Object data);

    /**
     * 删除一些数据文档
     *
     * @param collection 数据集合名称
     * @param index      数据索引 需要 new 一个目标对应的 Map kv 值 仅写入索引数据 将自动处理为 Bson
     */
    void delete(String collection, Map<String, Object> index);
}
