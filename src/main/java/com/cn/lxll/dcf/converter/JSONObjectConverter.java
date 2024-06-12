package com.cn.lxll.dcf.converter;

import com.alibaba.fastjson2.JSONObject;
import org.neo4j.driver.Value;
import org.neo4j.driver.Values;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyConverter;

/**
 * Project dcf
 * Created on 2024/5/30 上午3:12
 *
 * @author Lxll
 */
public class JSONObjectConverter implements Neo4jPersistentPropertyConverter<JSONObject> {

    @Override
    public Value write(JSONObject source) {
        if (source != null) {
            return Values.value(source.toJSONString());
        } else {
            return Values.NULL;
        }
    }

    @Override
    public JSONObject read(Value source) {
        if (source.isNull()) {
            return new JSONObject();
        }
        String str = source.asString();
        return JSONObject.parseObject(str);
    }
}
