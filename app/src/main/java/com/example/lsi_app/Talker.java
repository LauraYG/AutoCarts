package com.example.lsi_app;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

import std_msgs.Bool;
import std_msgs.String;

public class Talker extends AbstractNodeMain {

    private AutomaticControllActivity automaticControllActivity;
    private Publisher<std_msgs.String> publisherStillAlive;
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
    public void onStart(ConnectedNode connectedNode) {
        publisherStillAlive = connectedNode.newPublisher("chatter", String._TYPE);

        connectedNode.executeCancellableLoop(new CancellableLoop() {
            @Override
            protected void loop() throws InterruptedException {
                String message = publisherStillAlive.newMessage();
                message.setData("Hola holita");
                publisherStillAlive.publish(message);
                Thread.sleep(200L);
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

