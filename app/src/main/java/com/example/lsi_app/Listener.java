package com.example.lsi_app;

import android.widget.Toast;

import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import std_msgs.String;

public class Listener extends AbstractNodeMain {

    private AutomaticControllActivity automaticControllActivity;
    private JoystickActivity joystickActivity;
    private VisualizationActivity visualizationActivity;
    private ConnectedNode connectedNode;
    private Subscriber<String> pruebaSubscriber2;
    private Subscriber<std_msgs.String> pruebaSubscriber;
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
        pruebaSubscriber2 = connectedNode.newSubscriber("stillAlive", String._TYPE);
        pruebaSubscriber2.addMessageListener(new MessageListener<String>() {
            @Override
            public void onNewMessage(String string) {
                automaticControllActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast llega = Toast.makeText(automaticControllActivity.getBaseContext(), "llega",Toast.LENGTH_SHORT);
                        llega.show();
                    }
                });
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

