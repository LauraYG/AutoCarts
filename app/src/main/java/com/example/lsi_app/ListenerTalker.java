package com.example.lsi_app;

import android.widget.Toast;

import org.ros.concurrent.CancellableLoop;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;
import org.ros.node.topic.Subscriber;

import std_msgs.Bool;
import std_msgs.String;

public class ListenerTalker extends AbstractNodeMain implements NodeMain {

    private AutomaticControllActivity automaticControllActivity;
    private Publisher<Bool> publisherStillAlive;
    private Publisher<Bool> publisherSOS;
    private JoystickActivity joystickActivity;
    private VisualizationActivity visualizationActivity;
    private ConnectedNode connectedNode;
    private Subscriber<String> pruebaSubscriber;
    private Subscriber<String> pruebaSubscriber2;

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("rosjava/automaticControllNode");
    }


    ListenerTalker(AutomaticControllActivity automaticControllActivity){
        this.automaticControllActivity = automaticControllActivity;
    }

    public ListenerTalker(JoystickActivity joystickActivity){
        this.joystickActivity = joystickActivity;
    }

    public ListenerTalker(VisualizationActivity visualizationActivity){
        this.visualizationActivity = visualizationActivity;
    }

    public ListenerTalker() {
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        this.connectedNode = connectedNode;
        publisherStillAlive = connectedNode.newPublisher("/chatter", Bool._TYPE);

        connectedNode.executeCancellableLoop(new CancellableLoop() {
            @Override
            protected void loop() throws InterruptedException {
                Bool stillAlive = publisherStillAlive.newMessage();
                stillAlive.setData(true);
                publisherStillAlive.publish(stillAlive);
                Thread.sleep(200L);
            }
        });

        publisherSOS = connectedNode.newPublisher("car/SOS/", Bool._TYPE);
        pruebaSubscriber = connectedNode.newSubscriber("automaticNodeControll", String._TYPE);
        pruebaSubscriber.addMessageListener(new MessageListener<String>() {
            @Override
            public void onNewMessage(String string) {
                Toast llega = Toast.makeText(automaticControllActivity, "Llega llegaaa!",Toast.LENGTH_SHORT);
                llega.show();
            }
        });

        pruebaSubscriber2 = connectedNode.newSubscriber("/chatter", String._TYPE);
        pruebaSubscriber2.addMessageListener(new MessageListener<String>() {
            @Override
            public void onNewMessage(String string) {
                Toast llega = Toast.makeText(automaticControllActivity, "Llega llegaaa!",Toast.LENGTH_SHORT);
                llega.show();
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

