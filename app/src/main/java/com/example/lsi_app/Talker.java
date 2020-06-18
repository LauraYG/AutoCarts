package com.example.lsi_app;

import android.widget.Toast;

import org.ros.concurrent.CancellableLoop;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;
import org.ros.node.topic.Subscriber;

import std_msgs.Bool;
import std_msgs.String;

public class Talker extends AbstractNodeMain {

    private AutomaticControllActivity automaticControllActivity;
    private Subscriber<String> publisherStillAlive;
    private Subscriber<String> pruebaSubscriber2;
    private Publisher<Bool> publisherSOS;
    private JoystickActivity joystickActivity;
    private VisualizationActivity visualizationActivity;
    private ConnectedNode connectedNode;

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("rosjava/talker");
    }


    Talker(AutomaticControllActivity automaticControllActivity){
        this.automaticControllActivity = automaticControllActivity;
    }

    public Talker(JoystickActivity joystickActivity){
        this.joystickActivity = joystickActivity;
    }

    public Talker(VisualizationActivity visualizationActivity){
        this.visualizationActivity = visualizationActivity;
    }

    public Talker() {
    }

    @Override
    public void onStart(final ConnectedNode connectedNode) {
        publisherStillAlive = connectedNode.newSubscriber("stillAlive", String._TYPE);
        publisherStillAlive.addMessageListener(new MessageListener<String>() {
            @Override
            public void onNewMessage(String string) {
                /*automaticControllActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast llega = Toast.makeText(automaticControllActivity.getBaseContext(), "llega",Toast.LENGTH_SHORT);
                        llega.show();
                    }
                });*/
            }
        });
        /*publisherStillAlive = connectedNode.newPublisher("chatter", "std_msgs/String");

        connectedNode.executeCancellableLoop(new CancellableLoop() {
            @Override
            protected void loop() throws InterruptedException {
                String message = publisherStillAlive.newMessage();
                message.setData("Hola holita");
                publisherStillAlive.publish(message);
                Thread.sleep(2000L);
            }
        });


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
        });*/


        //publisherSOS = connectedNode.newPublisher("car/SOS/", Bool._TYPE);
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

