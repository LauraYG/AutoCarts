package com.example.lsi_app;

import android.widget.Toast;

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
    private Publisher<std_msgs.String> publisherStillAlive;
    private Subscriber<String> pruebaSubscriber2;
    private Publisher<Bool> publisherSOS;
    private JoystickActivity joystickActivity;
    private VisualizationActivity visualizationActivity;
    private ConnectedNode connectedNode;

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("rosjava/listenertalker");
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
        /*publisherStillAlive = connectedNode.newPublisher("chatter", String._TYPE);

        connectedNode.executeCancellableLoop(new CancellableLoop() {
            @Override
            protected void loop() throws InterruptedException {
                String message = publisherStillAlive.newMessage();
                message.setData("Hola holita");
                publisherStillAlive.publish(message);
                Thread.sleep(200L);
            }
        });*/

        automaticControllActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pruebaSubscriber2 = connectedNode.newSubscriber("chatter", String._TYPE);
                pruebaSubscriber2.addMessageListener(new MessageListener<String>() {
                    @Override
                    public void onNewMessage(String string) {
                        int x = 0;
                        Toast llega = Toast.makeText(automaticControllActivity.getBaseContext(), "Llega llegaaa!",Toast.LENGTH_SHORT);
                        llega.show();
                    }
                });
            }
        });


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

