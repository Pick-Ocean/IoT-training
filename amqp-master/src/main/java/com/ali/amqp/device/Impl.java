package com.ali.amqp.device;

import com.aliyun.iot20180120.models.DeleteDeviceRequest;
import com.aliyun.iot20180120.models.QueryDeviceRequest;
import com.aliyun.iot20180120.models.QueryDeviceResponse;
import com.aliyun.iot20180120.models.RegisterDeviceRequest;
import com.aliyun.tea.TeaModel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Impl {


    RegisterDevice registerDevice;

    DeleteDevice  deleteDevice;

    QueryDevice queryDevice;


    //添加设备
    public void input(String accessKeyId,String accessKeySecret,String DeviceName) throws Exception {
        com.aliyun.iot20180120.Client client = registerDevice.createClient(accessKeyId, accessKeySecret);
        RegisterDeviceRequest registerDeviceRequest = new RegisterDeviceRequest()
                .setProductKey("gr6ovkck4qG")
                .setIotInstanceId("iot-06z00d738kjjjqb")
                .setDeviceName(DeviceName);//这个是自定义设备名称不能小于4位字符

        client.registerDevice(registerDeviceRequest);
    }


    //删除设备
    public void delete(String accessKeyId,String accessKeySecret,String DeviceName) throws  Exception{
        com.aliyun.iot20180120.Client client = deleteDevice.createClient(accessKeyId, accessKeySecret);
        DeleteDeviceRequest deleteDeviceRequest = new DeleteDeviceRequest()
                .setIotInstanceId("iot-06z00d738kjjjqb")
                .setProductKey("gr6ovkck4qG")
                .setDeviceName(DeviceName);//指定删除的设备名称
        client.deleteDevice(deleteDeviceRequest);
    }


    //查询设备
    public List<String> query(String accessKeyId, String accessKeySecret) throws  Exception{
        ArrayList<String> list = new ArrayList<>();
        com.aliyun.iot20180120.Client client = queryDevice.createClient(accessKeyId, accessKeySecret);
        QueryDeviceRequest queryDeviceRequest = new QueryDeviceRequest()
                .setIotInstanceId("iot-06z00d738kjjjqb")
                .setProductKey("gr6ovkck4qG");
        QueryDeviceResponse resp = client.queryDevice(queryDeviceRequest);
        String data = com.aliyun.teautil.Common.toJSONString(TeaModel.buildMap(resp));
        JSONObject jsonObject = JSONObject.fromObject(data);
        String body = jsonObject.getString("body");
        JSONObject bodystr = JSONObject.fromObject(body);
        String data1 = bodystr.getString("Data");
        JSONObject data1str = JSONObject.fromObject(data1);
        JSONArray deviceInfo = data1str.getJSONArray("DeviceInfo");
        for (Object o : deviceInfo) {
            JSONObject jsonObject1 = JSONObject.fromObject(o);
            list.add(jsonObject1.getString("DeviceName"));
        }
        for (String s : list) {
            System.out.println(s);
        }
//        com.aliyun.teaconsole.Client.log(com.aliyun.teautil.Common.toJSONString());
        return list;
    }
}
