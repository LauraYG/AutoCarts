package com.example.lsi_app;

import android.widget.Toast;

import org.ros.message.MessageListener;
import org.ros.message.Time;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

import java.util.Date;

import std_msgs.Float32;
import std_msgs.Float64;
import std_msgs.Header;
import std_msgs.String;

public class Listener extends AbstractNodeMain {

    private AutomaticControllActivity automaticControllActivity;
    private JoystickActivity joystickActivity;
    private VisualizationActivity visualizationActivity;
    private ConnectedNode connectedNode;
    private Subscriber<Header> suscriberStillAlive;
    private Subscriber<Float64> speedSubscriber;
    private Subscriber<Float64> steeringSubscriber;
    private Time time;

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("rosjava/listener");
    }


    Listener(AutomaticControllActivity automaticControllActivity){
        this.automaticControllActivity = automaticControllActivity;
    }

    public Listener(JoystickActivity joystickActivity){
        this.joystickActivity = joystickActivity;
    }

    public Listener(VisualizationActivity visualizationActivity){
        this.visualizationActivity = visualizationActivity;
    }

    public Listener() {
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        this.connectedNode = connectedNode;
        suscriberStillAlive = connectedNode.newSubscriber("stillAlive", Header._TYPE);
        suscriberStillAlive.addMessageListener(new MessageListener<Header>() {
            @Override
            public void onNewMessage(final Header header) {
                automaticControllActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int finals = header.getStamp().secs;
                        int finalns = header.getStamp().nsecs;
                        if(time != null) {
                            int initials = time.secs;
                            int initialns = time.nsecs;
                            int diferenceInS = finals - initials;
                            int diferenceInNs = finalns - initialns;

                            if (diferenceInS >= 1 || diferenceInNs >= 1000000000) {
                                automaticControllActivity.hideGreenStillAlive();
                                automaticControllActivity.hideOrangeStillAlive();
                                automaticControllActivity.showRedStillAlive();
                            } else if (diferenceInS >= 0.2 || diferenceInNs >= 200000000) {
                                automaticControllActivity.hideGreenStillAlive();
                                automaticControllActivity.hideRedStillAlive();
                                automaticControllActivity.showOrangeStillAlive();
                            } else {
                                automaticControllActivity.hideRedStillAlive();
                                automaticControllActivity.hideOrangeStillAlive();
                                automaticControllActivity.showGreenStillAlive();
                            }
                        }
                        time = header.getStamp();
                    }
                });
            }
        });

        if(automaticControllActivity != null) {
            createListenersOfAutomaticControllActivity();
        }
    }

    private void createListenersOfAutomaticControllActivity() {
        speedSubscriber = connectedNode.newSubscriber("speed", Float64._TYPE);
        speedSubscriber.addMessageListener(new MessageListener<Float64>() {
            @Override
            public void onNewMessage(final Float64 float64) {
                automaticControllActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        automaticControllActivity.setGeneralSpeedText(float64.getData());
                    }
                });
            }
        });

        steeringSubscriber = connectedNode.newSubscriber("steering_angle", Float64._TYPE);
        steeringSubscriber.addMessageListener(new MessageListener<Float64>() {
            @Override
            public void onNewMessage(final Float64 float64) {
                automaticControllActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        automaticControllActivity.setGeneralSteeringText(float64.getData());
                    }
                });
            }
        });

        //TODO battery subscriber
        //TODO UPS subscriber
    }

    public void closeAutomaticControllActivity() {
        //Todo shutdowns from AutoMaticControllActivity
    }

    public void closeJoystickActivity() {
        //Todo shutdowns from JoystickActivity
    }

    public void closeVisualizationActivity() {
        //Todo shutdowns from VisualizationActivity
    }
}

