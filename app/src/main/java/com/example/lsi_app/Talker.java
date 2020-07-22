package com.example.lsi_app;

import org.ros.concurrent.CancellableLoop;
import org.ros.message.Time;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

import ackermann_msgs.AckermannDriveStamped;
import std_msgs.Bool;
import std_msgs.Header;
import std_msgs.Int32;

public class Talker extends AbstractNodeMain {

    private AutomaticControllActivity automaticControllActivity;
    private JoystickActivity joystickActivity;
    private VisualizationActivity visualizationActivity;
    private ConnectedNode connectedNode;
    private Publisher<Header> stillAlivePublisher;
    private Publisher<Header> playPublisher;
    private Publisher<Header> pausePublisher;
    private Publisher<Int32> vehicleModePublisher;
    private Publisher<AckermannDriveStamped> throtitleAndBrakePublisher;
    private Publisher<AckermannDriveStamped> steeringPublisher;
    private Publisher<std_msgs.Bool> throtitleEnablePublisher;
    private Publisher<std_msgs.Bool> steeringEnablePublisher;
    private Publisher<std_msgs.Bool> brakeEnablePublisher;
    private Publisher<Header> verdeModePublisher;
    private Publisher<Header> hInfiniteModePublisher;
    private Publisher<Header> stanleyModePublisher;

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("AutoCarts");
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

        if(automaticControllActivity != null) {
            publisherForVehicleMode(1);
            setThrotitleEnablePublisher(false);
            setSteeringEnablePublisher(false);
            setBrakeEnablePublisher(false);
        }

        if(joystickActivity != null) {
            publisherForVehicleMode(0);
            setThrotitleEnablePublisher(true);
            setSteeringEnablePublisher(true);
            setBrakeEnablePublisher(true);
        }
    }

    public void publisherForPlayButton() {
        playPublisher = connectedNode.newPublisher("play", Header._TYPE);
        std_msgs.Header playHeader = playPublisher.newMessage();
        playHeader.setStamp(new Time());
        playHeader.setFrameId("Phone");
        playPublisher.publish(playHeader);
    }

    public void publisherForPauseButton() {
        pausePublisher = connectedNode.newPublisher("pause", Header._TYPE);
        std_msgs.Header pauseHeader = pausePublisher.newMessage();
        pauseHeader.setStamp(new Time());
        pauseHeader.setFrameId("Phone");
        pausePublisher.publish(pauseHeader);
    }

    public void publisherForVerdeModeButton() {
        verdeModePublisher = connectedNode.newPublisher("verdeMode", Header._TYPE);
        std_msgs.Header pauseHeader = verdeModePublisher.newMessage();
        pauseHeader.setStamp(new Time());
        pauseHeader.setFrameId("Phone");
        verdeModePublisher.publish(pauseHeader);
    }

    public void publisherForHInfiniteModeButton() {
        hInfiniteModePublisher = connectedNode.newPublisher("hInfiniteMode", Header._TYPE);
        std_msgs.Header pauseHeader = hInfiniteModePublisher.newMessage();
        pauseHeader.setStamp(new Time());
        pauseHeader.setFrameId("Phone");
        hInfiniteModePublisher.publish(pauseHeader);
    }

    public void publisherForStanleyModeButton() {
        stanleyModePublisher = connectedNode.newPublisher("stanleyMode", Header._TYPE);
        std_msgs.Header pauseHeader = pausePublisher.newMessage();
        pauseHeader.setStamp(new Time());
        pauseHeader.setFrameId("Phone");
        stanleyModePublisher.publish(pauseHeader);
    }
    public void publisherForVehicleMode(int mode) {
        vehicleModePublisher = connectedNode.newPublisher("ada/vehicle_mode", Int32._TYPE);
        std_msgs.Int32 vehicleMode = vehicleModePublisher.newMessage();
        switch (mode) {
            case 0:
                vehicleMode.setData(0);
                break;
            case 1:
                vehicleMode.setData(1);
                break;
            case 2:
                vehicleMode.setData(2);
                break;
        }
        vehicleModePublisher.publish(vehicleMode);
    }

    public void setThrotitleEnablePublisher(boolean value) {
        throtitleEnablePublisher = connectedNode.newPublisher("can/throtitle_enable", Bool._TYPE);
        std_msgs.Bool throtitleEnable = throtitleEnablePublisher.newMessage();
        throtitleEnable.setData(value);
        throtitleEnablePublisher.publish(throtitleEnable);
    }

    public void setSteeringEnablePublisher(boolean value) {
        steeringEnablePublisher = connectedNode.newPublisher("can/steering_enable", Bool._TYPE);
        std_msgs.Bool throtitleEnable = steeringEnablePublisher.newMessage();
        throtitleEnable.setData(value);
        steeringEnablePublisher.publish(throtitleEnable);
    }

    public void setBrakeEnablePublisher(boolean value) {
        brakeEnablePublisher = connectedNode.newPublisher("can/brake_enable", Bool._TYPE);
        std_msgs.Bool throtitleEnable = brakeEnablePublisher.newMessage();
        throtitleEnable.setData(value);
        brakeEnablePublisher.publish(throtitleEnable);
    }

    public void publisherForSteering(final float angle) {
        steeringPublisher = connectedNode.newPublisher("ackermannSteeringPhone", AckermannDriveStamped._TYPE);
        ackermann_msgs.AckermannDriveStamped steeringAckerMannDriveStamped = steeringPublisher.newMessage();
        steeringAckerMannDriveStamped.getDrive().setSteeringAngleVelocity(0);
        steeringAckerMannDriveStamped.getDrive().setSteeringAngle(angle);
        steeringAckerMannDriveStamped.getDrive().setAcceleration(0);
        steeringAckerMannDriveStamped.getDrive().setJerk(0);
        steeringAckerMannDriveStamped.getHeader().setStamp(new Time());
        steeringAckerMannDriveStamped.getHeader().setFrameId("Phone");
        steeringPublisher.publish(steeringAckerMannDriveStamped);
    }

    public void publisherForBrakeAndThrotitle(final float strength) {
        throtitleAndBrakePublisher = connectedNode.newPublisher("ackermannThrotitleBrakePhone", AckermannDriveStamped._TYPE);
        ackermann_msgs.AckermannDriveStamped throtitleBrakeAckerMannDriveStamped = steeringPublisher.newMessage();
        throtitleBrakeAckerMannDriveStamped.getDrive().setSteeringAngleVelocity(0);
        throtitleBrakeAckerMannDriveStamped.getDrive().setAcceleration(0);
        throtitleBrakeAckerMannDriveStamped.getDrive().setJerk(0);
        throtitleBrakeAckerMannDriveStamped.getDrive().setSpeed(strength);
        throtitleBrakeAckerMannDriveStamped.getHeader().setStamp(new Time());
        throtitleBrakeAckerMannDriveStamped.getHeader().setFrameId("Phone");
        throtitleAndBrakePublisher.publish(throtitleBrakeAckerMannDriveStamped);
    }
    

    public void closeActivity() {
        if (stillAlivePublisher != null) {
            stillAlivePublisher.shutdown();
        } else if (playPublisher != null) {
            playPublisher.shutdown();
        } else if (pausePublisher != null) {
            pausePublisher.shutdown();
        } else if (vehicleModePublisher != null) {
            vehicleModePublisher.shutdown();
        } else if (throtitleAndBrakePublisher != null) {
            throtitleAndBrakePublisher.shutdown();
        } else if (steeringPublisher != null) {
            steeringPublisher.shutdown();
        } else if (throtitleEnablePublisher != null) {
            throtitleEnablePublisher.shutdown();
        } else if (steeringEnablePublisher != null) {
            steeringEnablePublisher.shutdown();
        } else if (brakeEnablePublisher != null) {
            brakeEnablePublisher.shutdown();
        }
        connectedNode.shutdown();
    }
}

