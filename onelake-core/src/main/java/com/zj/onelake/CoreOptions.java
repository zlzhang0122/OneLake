package com.zj.onelake;

import org.apache.flink.configuration.Configuration;

import java.io.Serializable;
import java.util.Map;

/**
 * Core options for OneLake.
 *
 * @Author geniuszhang
 * @Date 2024/12/1 21:19
 * @Description: TODO
 * @Version 1.0
 */
public class CoreOptions implements Serializable {

    private final Configuration options;

    public CoreOptions() {
        this(new Configuration());
    }

    public CoreOptions(Map<String, String> options) {
        this(Configuration.fromMap(options));
    }

    public CoreOptions(Configuration options) {
        this.options = options;
    }

    public static CoreOptions fromMap(Map<String, String> options) {
        return new CoreOptions(options);
    }
}
