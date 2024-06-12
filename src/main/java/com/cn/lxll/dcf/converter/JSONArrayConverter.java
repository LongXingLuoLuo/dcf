package com.cn.lxll.dcf.converter;

import com.alibaba.fastjson2.JSONArray;
import org.neo4j.driver.Value;
import org.neo4j.driver.Values;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyConverter;

/**
 * Project dcf
 * Created on 2024/5/30 上午3:12
 *
 * @author Lxll
 */
public class JSONArrayConverter implements Neo4jPersistentPropertyConverter<JSONArray> {

    @Override
    public Value write(JSONArray source) {
        if (source != null) {
            return Values.value(source.toJSONString());
        } else {
            return Values.NULL;
        }
    }

    @Override
    public JSONArray read(Value source) {
        if (source.isNull()) {
            return new JSONArray();
        }
        String str = source.asString();
        return JSONArray.parseArray(str);
    }
}
