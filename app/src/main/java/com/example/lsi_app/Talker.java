package com.example.lsi_app;

import org.ros.concurrent.CancellableLoop;
import org.ros.message.Time;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

import std_msgs.Header;

public class Talker extends AbstractNodeMain {

    private AutomaticControllActivity automaticControllActivity;
    private JoystickActivity joystickActivity;
    private VisualizationActivity visualizationActivity;
    private ConnectedNode connectedNode;
    private Publisher<Header> stillAlivePublisher;
    private Publisher<Header> playPublisher;
    private Publisher<Header> pausePublisher;
    private Publisher<Header> emergencyStopPublisher;


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
        this.connectedNode = connectedNode;
        stillAlivePublisher = connectedNode.newPublisher("stillAlivePhone", Header._TYPE);
        connectedNode.executeCancellableLoop(new CancellableLoop() {
            int counterStillalive = 0;
            @Override
            protected void loop() throws InterruptedException {
                std_msgs.Header stillAliveHeader = stillAlivePublisher.newMessage();
                stillAliveHeader.setSeq(counterStillalive);
                stillAliveHeader.setStamp(new Time());
                stillAliveHeader.setFrameId("Phone");
                stillAlivePublisher.publish(stillAliveHeader);
                Thread.sleep(200);
                counterStillalive++;
            }
        });
    }

    public void publisherForPlayButton() {
        playPublisher = connectedNode.newPublisher("play", Header._TYPE);
        connectedNode.executeCancellableLoop(new CancellableLoop() {
            @Override
            protected void loop() {
                std_msgs.Header stillAliveHeader = playPublisher.newMessage();
                stillAliveHeader.setStamp(new Time());
                stillAliveHeader.setFrameId("Phone");
                playPublisher.publish(stillAliveHeader);
                return;
            }
        });
    }

    public void publisherForPauseButton() {
        pausePublisher = connectedNode.newPublisher("pause", Header._TYPE);
        connectedNode.executeCancellableLoop(new CancellableLoop() {
            @Override
            protected void loop() {
                std_msgs.Header stillAliveHeader = pausePublisher.newMessage();
                stillAliveHeader.setStamp(new Time());
                stillAliveHeader.setFrameId("Phone");
                pausePublisher.publish(stillAliveHeader);
                return;
            }
        });
    }

    public void publisherForEmergencyButton() {
        emergencyStopPublisher = connectedNode.newPublisher("emergencyStop", Header._TYPE);
        connectedNode.executeCancellableLoop(new CancellableLoop() {
            @Override
            protected void loop() {
                std_msgs.Header stillAliveHeader = emergencyStopPublisher.newMessage();
                stillAliveHeader.setStamp(new Time());
                stillAliveHeader.setFrameId("Phone");
                emergencyStopPublisher.publish(stillAliveHeader);
                return;
            }
        });
    }

    public void publisherForSteering() {
    }

    public void publisherForBrakeAndThrotitle() {
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

