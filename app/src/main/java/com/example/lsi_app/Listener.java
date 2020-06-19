package com.example.lsi_app;

import android.widget.Toast;

import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

import java.util.Date;

import std_msgs.Float32;
import std_msgs.String;

public class Listener extends AbstractNodeMain {

    private AutomaticControllActivity automaticControllActivity;
    private JoystickActivity joystickActivity;
    private VisualizationActivity visualizationActivity;
    private ConnectedNode connectedNode;
    private Subscriber<String> suscriberStillAlive;
    private Subscriber<Float32> speedSubscriber;
    private Subscriber<Float32> steeringSubscriber;
    private Date initialDate = new Date();

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
        suscriberStillAlive = connectedNode.newSubscriber("stillAlive", String._TYPE);
        suscriberStillAlive.addMessageListener(new MessageListener<String>() {
            @Override
            public void onNewMessage(String string) {
                automaticControllActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Date newDate = new Date();
                        long diffInMs = newDate.getTime() - initialDate.getTime();
                        if(diffInMs > 1000) {
                            automaticControllActivity.hideGreenStillAlive();
                            automaticControllActivity.hideOrangeStillAlive();
                            automaticControllActivity.showRedStillAlive();
                        } else if (diffInMs > 200 && diffInMs < 1000) {
                            automaticControllActivity.hideGreenStillAlive();
                            automaticControllActivity.hideRedStillAlive();
                            automaticControllActivity.showOrangeStillAlive();
                        } else {
                            automaticControllActivity.hideRedStillAlive();
                            automaticControllActivity.hideOrangeStillAlive();
                            automaticControllActivity.showGreenStillAlive();
                        }
                        initialDate = newDate;
                    }
                });
            }
        });

        if(automaticControllActivity != null) {
            createListenersOfAutomaticControllActivity();
        }
    }

    private void createListenersOfAutomaticControllActivity() {
        speedSubscriber = connectedNode.newSubscriber("speed", Float32._TYPE);
        speedSubscriber.addMessageListener(new MessageListener<Float32>() {
            @Override
            public void onNewMessage(final Float32 float32) {
                automaticControllActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        automaticControllActivity.setGeneralSpeedText(float32.getData());
                    }
                });
            }
        });

        steeringSubscriber = connectedNode.newSubscriber("steering_angle", Float32._TYPE);
        steeringSubscriber.addMessageListener(new MessageListener<Float32>() {
            @Override
            public void onNewMessage(final Float32 float32) {
                automaticControllActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        automaticControllActivity.setGeneralSteeringText(float32.getData());
                    }
                });
            }
        });
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

