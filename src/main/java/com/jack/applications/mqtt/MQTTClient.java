package com.jack.applications.mqtt;

import org.eclipse.paho.client.mqttv3.*;

public class MQTTClient implements MqttCallback {

    private static String brokerURI = "tcp://localhost:1883";
    private static int qos = 2;

    private MqttClient client;

    public MQTTClient(String topic) {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);

        try {
            this.client = new MqttClient(brokerURI, "room_" + topic);
            this.client.setCallback(this);
            this.client.connect(options);

            this.client.subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }

        System.out.println("Connected to " + brokerURI);
    }

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("Connection to MQTT broker lost !");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println("-------------------------------------------------");
        System.out.println("| Topic:" + s);
        System.out.println("| Message: " + new String(mqttMessage.getPayload()));
        System.out.println("-------------------------------------------------");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println("Delivery completed !");
    }
}
