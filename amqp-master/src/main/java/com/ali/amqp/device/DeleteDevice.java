package com.ali.amqp.device;
import com.aliyun.iot20180120.models.*;
import com.aliyun.teaopenapi.models.*;
public class DeleteDevice {

    /**
     * 使用AK&SK初始化账号Client
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.iot20180120.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId("LTAI5t8Kp2F9oD38sUEM3zKn")
                // 您的AccessKey Secret
                .setAccessKeySecret("4ZaVqIpV6lWNKu73JLYI4r9QsEitVJ");
        // 访问的域名
        config.endpoint = "iot.cn-shanghai.aliyuncs.com";
        return new com.aliyun.iot20180120.Client(config);
    }
}
