package com.zj.onelake.table;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author geniuszhang
 * @Date 2024/12/1 20:42
 * @Description: TODO
 * @Version 1.0
 */
public interface Table extends org.apache.iceberg.Table, Serializable {

    Map<String, String> options();

    Table copy(Map<String, String> dynamicOptions);
}
