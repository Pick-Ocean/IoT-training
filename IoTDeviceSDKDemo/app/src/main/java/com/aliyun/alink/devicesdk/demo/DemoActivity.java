package com.aliyun.alink.devicesdk.demo;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aliyun.alink.devicesdk.app.AppLog;
import com.aliyun.alink.devicesdk.app.DemoApplication;
import com.aliyun.alink.devicesdk.app.DeviceInfoData;
import com.aliyun.alink.devicesdk.manager.DASHelper;
import com.aliyun.alink.devicesdk.manager.IDemoCallback;
import com.aliyun.alink.devicesdk.manager.InitManager;
import com.aliyun.alink.linkkit.api.LinkKit;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttPublishRequest;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttSubscribeRequest;
import com.aliyun.alink.linksdk.cmp.core.base.AMessage;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.base.ConnectState;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSubscribeListener;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.log.IDGenerater;

import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;


/*
 * Copyright (c) 2014-2016 Alibaba Group. All rights reserved.
 * License-Identifier: Apache-2.0
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

public class DemoActivity extends BaseActivity implements View.OnClickListener, SensorListener {
    private static final String TAG = "DemoActivity";
    int id = 0;
    SensorManager sm = null;
    TextView View1 = null;
    TextView View2 = null;
    TextView View3 = null;
    TextView View4 = null;
    TextView View5 = null;
    TextView View6 = null;
    TextView View7 = null;
    TextView View8 = null;
    TextView View9 = null;
    TextView View10 = null;
    TextView View11 = null;
    TextView View12 = null;
    private TextView errorTV = null;
    private AtomicInteger testDeviceIndex = new AtomicInteger(0);
    boolean flag = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AppLog.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        errorTV = findViewById(R.id.id_error_info);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        View1 = (TextView) findViewById(R.id.edt1);
        View2 = (TextView) findViewById(R.id.edt2);
        View3 = (TextView) findViewById(R.id.edt3);
        View4 = (TextView) findViewById(R.id.edt4);
        View5 = (TextView) findViewById(R.id.edt5);
        View6 = (TextView) findViewById(R.id.edt6);
        View7 = (TextView) findViewById(R.id.edt7);
        View8 = (TextView) findViewById(R.id.edt8);
        View9 = (TextView) findViewById(R.id.edt9);
        View10 = (TextView) findViewById(R.id.edt10);
        View11 = (TextView) findViewById(R.id.edt11);
        setListener();



        LinkKit.getInstance().registerOnPushListener(notifyListener);
        Log.e("----------------------","启用！");
        flag = true;
    }
    /**
     * 下行监听器，云端 MQTT 下行数据都会通过这里回调
     */
    private static IConnectNotifyListener notifyListener = new IConnectNotifyListener() {
        /**
         * onNotify 会触发的前提是 shouldHandle 没有指定不处理这个topic
         * @param connectId 连接类型，这里判断是否长链 connectId == ConnectSDK.getInstance().getPersistentConnectId()
         * @param topic 下行的topic
         * @param aMessage 下行的数据内容
         */
        @Override
        public void onNotify(String connectId, String topic, AMessage aMessage) {
            String data = new String((byte[]) aMessage.data);
            // 服务端返回数据示例  data = {"method":"thing.service.test_service","id":"123374967","params":{"vv":60},"version":"1.0.0"}
            Log.d("*****************",data);
        }

        /**
         * @param connectId 连接类型，这里判断是否长链 connectId == ConnectSDK.getInstance().getPersistentConnectId()
         * @param topic 下行topic
         * @return 是否要处理这个topic，如果为true，则会回调到onNotify；如果为false，onNotify不会回调这个topic相关的数据。建议默认为true。
         */
        @Override
        public boolean shouldHandle(String connectId, String topic) {
            return true;
        }

        /**
         * @param connectId 连接类型，这里判断是否长链 connectId == ConnectSDK.getInstance().getPersistentConnectId()
         * @param connectState {@link ConnectState}
         *     CONNECTED, 连接成功
         *     DISCONNECTED, 已断链
         *     CONNECTING, 连接中
         *     CONNECTFAIL; 连接失败
         */
        @Override
        public void onConnectStateChange(String connectId, ConnectState connectState) {
            Log.d(TAG, "onConnectStateChange() called with: connectId = [" + connectId + "], connectState = [" + connectState + "]");
        }
    };
    void sendData(String str1,String str2,String str3,String str4){
            // 发布
            MqttPublishRequest request = new MqttPublishRequest();
    // 设置是否需要应答。
            request.isRPC = false;
    // 设置topic，设备通过该Topic向物联网平台发送消息。
            request.topic = "/sys/gr6ovkck4qG/phone/thing/event/property/post";
    // 设置 qos
            request.qos = 0;

    // data 设置需要发布的数据 json String，其中id字段需要保持自增。
    //示例 属性上报 {"id":"160865432","method":"thing.event.property.post","params":{"LightSwitch":1},"version":"1.0"}
            request.payloadObj = "{\"id\":\""+id+++"\",\"property\":\""+str4+"\",\"params\":{\""+str4+"\":\"X"+str1+"Y"+str2+"Z"+str3+"\"},\"version\":\"1.0\"}";

            LinkKit.getInstance().publish(request, new IConnectSendListener() {
                @Override
                public void onResponse(ARequest aRequest, AResponse aResponse) {
                    Log.e("aaaaa","发布成功！");
                    subMsg();
                }
                @Override
                public void onFailure(ARequest aRequest, AError aError) {
                    // 发布失败
                    Log.e("aaaaa","发布失败！");

                }
            });
    }
    //订阅消息
    void subMsg(){
        // 发布
        MqttPublishRequest request = new MqttPublishRequest();
// 设置是否需要应答。
        request.isRPC = false;
// 设置topic，设备通过该Topic向物联网平台发送消息。
        request.topic = "/sys/gr6ovkck4qG/phone/thing/event/property/post_reply";
// 设置 qos
        request.qos = 0;
// data 设置需要发布的数据 json String，其中id字段需要保持自增。
//示例 属性上报 {"id":"160865432","method":"thing.event.property.post","params":{"LightSwitch":1},"version":"1.0"}
//        request.payloadObj = data;
        LinkKit.getInstance().publish(request, new IConnectSendListener() {
            @Override
            public void onResponse(ARequest aRequest, AResponse aResponse) {
                Log.e("eeeeee","订阅成功");
                // 发布成功
            }
            @Override
            public void onFailure(ARequest aRequest, AError aError) {
                // 发布失败
                Log.e("eeeeee","订阅失败");
            }
        });
//        MqttSubscribeRequest subscribeRequest = new MqttSubscribeRequest();
//        subscribeRequest.isSubscribe = true;
//// ${topic} 替换成调用方的自定义 topic
//        subscribeRequest.topic = "/ext/rrpc/gr6xZpppJHj/guangzhao";
//        LinkKit.getInstance().subscribe(subscribeRequest, new IConnectSubscribeListener() {
//            @Override
//            public void onSuccess() {
//                // 订阅成功
//                Log.e("------------","订阅成功");
//            }
//
//            @Override
//            public void onFailure(AError aError) {
//                // 订阅失败
//                Log.e("------------","订阅失败");
//            }
//        });
//        Log.e("------------","submsg");
    }
    public void onSensorChanged(int sensor, float[] values) {
        synchronized (this) {
            String str = "X：" + values[0] + "，Y：" + values[1] + "，Z：" + values[2];
            switch (sensor){
                case Sensor.TYPE_ACCELEROMETER:
                    View1.setText("加速度：" + str);
                    sendData(values[0]+"",values[1]+"",values[2]+"","jsd");
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    View2.setText("磁场：" + str);
                    sendData(values[0]+"",values[1]+"",values[2]+"","cc");
                    break;
                case Sensor.TYPE_ORIENTATION:
                    View3.setText("定位：" + str);
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    View4.setText("陀螺仪：" + str);
                    break;
                case Sensor.TYPE_LIGHT:
                    View5.setText("光线：" + str);
                    break;
                case Sensor.TYPE_PRESSURE:
                    View6.setText("压力：" + str);
                    break;
                case Sensor.TYPE_TEMPERATURE:
                    View7.setText("温度：" + str);
                    break;
                case Sensor.TYPE_PROXIMITY:
                    View8.setText("距离：" + str);
                    sendData(values[0]+"",values[1]+"",values[2]+"","jl");
                    break;
                case Sensor.TYPE_GRAVITY:
                    View9.setText("重力：" + str);
                    break;
                case Sensor.TYPE_LINEAR_ACCELERATION:
                    View10.setText("线性加速度：" + str);
                    break;
                case Sensor.TYPE_ROTATION_VECTOR:
                    View11.setText("旋转矢量：" + str);
                    break;
                default:
                    View12.setText("NORMAL：" + str);
                    break;
            }
        }
    }
    public void onAccuracyChanged(int sensor, int accuracy) {
        Log.d(TAG,"onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
    }


    @Override
    protected void onStop() {
        sm.unregisterListener(this);
        super.onStop();
    }
    private void setListener() {
        try {
            LinearLayout demoLayout = findViewById(R.id.id_demo_layout);
            int size = demoLayout.getChildCount();
            for (int i = 0; i < size; i++) {
                if (i == size - 1) {
                    break;
                }
                View child = demoLayout.getChildAt(i);
                child.setOnClickListener(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
            AppLog.w(TAG, "setListener exception " + e);
        }
    }

    public void startOTATest(View view) {
        if (!checkReady()) {
            return;
        }

        Intent intent = new Intent(this, OTAActivity.class);
        startActivity(intent);
    }


    public void startBreezeOTATest(View view) {
        if (!checkReady()) {
            return;
        }

//        Intent intent = new Intent(this, BreezeOtaActivity.class);
//        startActivity(intent);
    }

    public void startLPTest(View view) {
        if (!checkReady()) {
            return;
        }
        if (LinkKit.getInstance().getDeviceThing() == null) {
            showToast("物模型功能未启用");
            return;
        }
        Intent intent = new Intent(this, TSLActivity.class);
        startActivity(intent);
    }

    public void startLabelTest(View view) {
        if (!checkReady()) {
            return;
        }
        Intent intent = new Intent(this, LabelActivity.class);
        startActivity(intent);
    }

    public void startCOTATest(View view) {
        if (!checkReady()) {
            return;
        }
        Intent intent = new Intent(this, COTAActivity.class);
        startActivity(intent);
    }

    public void startShadowTest(View view) {
        if (!checkReady()) {
            return;
        }
        Intent intent = new Intent(this, ShadowActivity.class);
        startActivity(intent);
    }

    public void startGatewayTest(View view) {
        if (!checkReady()) {
            return;
        }
        if (LinkKit.getInstance().getGateway() == null) {
            showToast("网关功能未启用");
            return;
        }
        Intent intent = new Intent(this, GatewayActivity.class);
        startActivity(intent);
    }

    private boolean checkReady() {
        if (DemoApplication.userDevInfoError) {
            showToast("设备三元组信息res/raw/deviceinfo格式错误");
            return false;
        }
        if (!DemoApplication.isInitDone) {
            showToast("初始化尚未成功，请稍后点击");
            return false;
        }
        errorTV.setVisibility(View.GONE);
        return true;
    }

    public void startH2FileTest(View view) {
        if (!checkReady()) {
            return;
        }
        Intent intent = new Intent(this, H2FileManagerActivity.class);
        startActivity(intent);
    }

    public void startLogPush(View view) {
        if (!checkReady()) {
            return;
        }
        Intent intent = new Intent(this, LogPushActivity.class);
        startActivity(intent);
    }

    public void startMqttTest(View view) {
        if (!checkReady()) {
            return;
        }
        Intent intent = new Intent(this, MqttActivity.class);
        startActivity(intent);
    }

    private void startResetTest(View v) {
        Intent intent = new Intent(this, ResetActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_start_LP:
                startLPTest(v);
                break;
            case R.id.id_start_label:
                startLabelTest(v);
                break;
            case R.id.id_start_cota:
                startCOTATest(v);
                break;
            case R.id.id_start_shadow:
                startShadowTest(v);
                break;
            case R.id.id_start_gateway:
                startGatewayTest(v);
                break;
            case R.id.id_start_ota:
                startOTATest(v);
                break;
            case R.id.id_start_breeze_ota:
                startBreezeOTATest(v);
                break;
            case R.id.id_start_h2_file:
                startH2FileTest(v);
                break;
            case R.id.id_test_init:
                connect();
                break;
            case R.id.id_test_deinit:
                deinit();
                break;
            case R.id.id_mqtt_test:
//                testJniLeakWithCoAP();
                startMqttTest(v);
                break;
            case R.id.id_test_reset:
                startResetTest(v);
                break;
            case R.id.id_log_push:
                startLogPush(v);
                break;
        }
    }

    private static ArrayList<DeviceInfoData> getTestDataList() {
        ArrayList<DeviceInfoData> infoDataArrayList = new ArrayList<DeviceInfoData>();

        DeviceInfoData test6 = new DeviceInfoData();
        test6.productKey = DemoApplication.productKey;
        test6.deviceName = DemoApplication.deviceName;
        test6.deviceSecret = DemoApplication.deviceSecret;
        infoDataArrayList.add(test6);
        return infoDataArrayList;
    }

    /**
     * 初始化
     * 耗时操作，建议放到异步线程
     */
    private void connect() {
        AppLog.d(TAG, "connect() called");
        // SDK初始化
        DeviceInfoData deviceInfoData = getTestDataList().get(testDeviceIndex.getAndIncrement() % getTestDataList().size());
        DemoApplication.productKey = deviceInfoData.productKey;
        DemoApplication.deviceName = deviceInfoData.deviceName;
        DemoApplication.deviceSecret = deviceInfoData.deviceSecret;
        new Thread(new Runnable() {
            @Override
            public void run() {
                InitManager.init(DemoActivity.this, DemoApplication.productKey, DemoApplication.deviceName,
                        DemoApplication.deviceSecret, DemoApplication.productSecret, new IDemoCallback() {

                            @Override
                            public void onError(AError aError) {
                                AppLog.d(TAG, "onError() called with: aError = [" + InitManager.getAErrorString(aError) + "]");
                                // 初始化失败，初始化失败之后需要用户负责重新初始化
                                // 如一开始网络不通导致初始化失败，后续网络恢复之后需要重新初始化

                                if (aError != null) {
//                                    AppLog.d(TAG, "初始化失败，错误信息：" + aError.getCode() + "-" + aError.getSubCode() + ", " + aError.getMsg());
                                    showToast("初始化失败，错误信息：" + aError.getCode() + "-" + aError.getSubCode() + ", " + aError.getMsg());
                                } else {
//                                    AppLog.d(TAG, "初始化失败");
                                    showToast("初始化失败");
                                }
                            }

                            @Override
                            public void onInitDone(Object data) {
                                AppLog.d(TAG, "onInitDone() called with: data = [" + data + "]");
                                DemoApplication.isInitDone = true;
                                showToast("初始化成功");
//                                AppLog.d(TAG, "初始化成功");
                            }
                        });
            }
        }).start();
    }

    /**
     * 耗时操作，建议放到异步线程
     * 反初始化同步接口
     */
    private void deinit() {
        AppLog.d(TAG, "deinit");
        DemoApplication.isInitDone = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 同步接口
                LinkKit.getInstance().deinit();
                DASHelper.getInstance().deinit();
                showToast("反初始化成功");
//                AppLog.d(TAG, "反初始化成功");
            }
        }).start();
    }

    private void publishTest() {
        try {
            AppLog.d(TAG, "publishTest called.");
            MqttPublishRequest request = new MqttPublishRequest();
            // 支持 0 和 1， 默认0
            request.qos = 1;
            request.isRPC = false;
            request.topic = "/" + DemoApplication.productKey + "/" + DemoApplication.deviceName + "/user/update";
            request.msgId = String.valueOf(IDGenerater.generateId());
            // TODO 用户根据实际情况填写 仅做参考
            request.payloadObj = "{\"id\":\"" + request.msgId + "\", \"version\":\"1.0\"" + ",\"params\":{\"state\":\"1\"} }";
            LinkKit.getInstance().publish(request, new IConnectSendListener() {
                @Override
                public void onResponse(ARequest aRequest, AResponse aResponse) {
                    AppLog.d(TAG, "onResponse() called with: aRequest = [" + aRequest + "], aResponse = [" + aResponse + "]");
                    showToast("发布成功");
                }

                @Override
                public void onFailure(ARequest aRequest, AError aError) {
                    AppLog.d(TAG, "onFailure() called with: aRequest = [" + aRequest + "], aError = [" + aError + "]");
                    showToast("发布失败 " + (aError != null ? aError.getCode() : "null"));
                }
            });
        } catch (Exception e) {
            showToast("发布异常 ");
        }
    }


    private ScheduledFuture future = null;

    @Override
    protected void onResume() {
        super.onResume();
//        testJniLeak();
//        future =future ThreadPool.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                publishTest();
//            }
//        }, 0, 15, TimeUnit.SECONDS);
//        super.onResume();
        sm.registerListener(this,
                Sensor.TYPE_ACCELEROMETER |
                        Sensor.TYPE_MAGNETIC_FIELD |
                        Sensor.TYPE_ORIENTATION |
                        Sensor.TYPE_GYROSCOPE |
                        Sensor.TYPE_LIGHT |
                        Sensor.TYPE_PRESSURE |
                        Sensor.TYPE_TEMPERATURE |
                        Sensor.TYPE_PROXIMITY |
                        Sensor.TYPE_GRAVITY |
                        Sensor.TYPE_LINEAR_ACCELERATION |
                        Sensor.TYPE_ROTATION_VECTOR,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (future != null) {
                future.cancel(true);
                future = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
