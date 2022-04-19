package com.alkaidmc.alkaid.mongodb.interfaces;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface AsyncQueryActions {
    /**
     * 查询数据集合 符合的结果将进入 List 列表返回 需要提供泛型数据实体类类型
     *
     * @param index    数据索引 需要 new 一个目标对应的 Map kv 值 仅写入索引数据 将自动处理为 Bson
     * @param type     泛型数据实体类类型
     * @param consumer 回调函数
     * @param <T>      返回的数据实体类型
     */
    <T> void read(Map<String, Object> index, Class<T> type, Consumer<List<T>> consumer);

    /**
     * 搜索数据集合 符合的结果将进入 List 列表返回 需要提供泛型数据实体类类型 <br>
     * 本方法提供根据某一个字段名称的边界以及限制数量进行搜索的操作 <br>
     * <b>其中 top 与  bottom 值为泛型值类型 指定的字段需与存储数据的类型对应</b> <br>
     * 举例: <br>
     * number 存储数据类型为 int <br>
     * 那么 top 和 bottom 值为 int 类型 即无需双引号的拆箱类型
     *
     * @param data     字段名称
     * @param top      边界
     * @param bottom   边界
     * @param limit    限制值
     * @param type     泛型数据实体类类型
     * @param consumer 回调函数
     * @param <T>      返回的数据实体类型
     * @param <V>      边界类型
     */
    <T, V> void search(String data, V top, V bottom, int limit, Class<T> type, Consumer<List<T>> consumer);
}
