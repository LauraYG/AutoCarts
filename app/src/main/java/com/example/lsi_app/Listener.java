package com.example.lsi_app;

import android.widget.Toast;

import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

import std_msgs.String;

public class Listener extends AbstractNodeMain {

    private AutomaticControllActivity automaticControllActivity;
    private JoystickActivity joystickActivity;
    private VisualizationActivity visualizationActivity;
    private ConnectedNode connectedNode;
    private Subscriber<String> pruebaSubscriber2;
    private Subscriber<std_msgs.String> pruebaSubscriber;


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
        pruebaSubscriber2 = connectedNode.newSubscriber("chat", String._TYPE);
        pruebaSubscriber2.addMessageListener(new MessageListener<String>() {
            @Override
            public void onNewMessage(String string) {
                int x = 0;
                automaticControllActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast llega = Toast.makeText(automaticControllActivity.getBaseContext(), "llega",Toast.LENGTH_SHORT);
                        llega.show();
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

