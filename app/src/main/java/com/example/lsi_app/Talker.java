package com.example.lsi_app;

import org.ros.concurrent.CancellableLoop;
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
    private Publisher<AckermannDriveStamped> ackermannPublisher;
    private Publisher<std_msgs.Bool> throttleEnablePublisher;
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
                stillAliveHeader.setStamp(connectedNode.getCurrentTime());
                stillAliveHeader.setFrameId("Phone");
                stillAlivePublisher.publish(stillAliveHeader);
                Thread.sleep(200);
                counterStillalive++;
            }
        });

        if(automaticControllActivity != null) {
            publisherForVehicleMode(1);
            setThrottleEnablePublisher(false);
            setSteeringEnablePublisher(false);
            setBrakeEnablePublisher(false);
        }

        if(joystickActivity != null) {
            publisherForVehicleMode(0);
            setThrottleEnablePublisher(true);
            setSteeringEnablePublisher(true);
            setBrakeEnablePublisher(true);
            createPublisherForJoystick();
        }
    }

    public void createPublisherForJoystick() {
        ackermannPublisher = connectedNode.newPublisher("ackermannSteeringPhone", AckermannDriveStamped._TYPE);
    }

    public void publisherForPlayButton() {
        playPublisher = connectedNode.newPublisher("play", Header._TYPE);
        std_msgs.Header playHeader = playPublisher.newMessage();
        playHeader.setStamp(connectedNode.getCurrentTime());
        playHeader.setFrameId("Phone");
        playPublisher.publish(playHeader);
    }

    public void publisherForPauseButton() {
        pausePublisher = connectedNode.newPublisher("pause", Header._TYPE);
        std_msgs.Header pauseHeader = pausePublisher.newMessage();
        pauseHeader.setStamp(connectedNode.getCurrentTime());
        pauseHeader.setFrameId("Phone");
        pausePublisher.publish(pauseHeader);
    }

    public void publisherForVerdeModeButton() {
        verdeModePublisher = connectedNode.newPublisher("verdeMode", Header._TYPE);
        std_msgs.Header verdeHeader = verdeModePublisher.newMessage();
        verdeHeader.setStamp(connectedNode.getCurrentTime());
        verdeHeader.setFrameId("Phone");
        verdeModePublisher.publish(verdeHeader);
    }

    public void publisherForHInfiniteModeButton() {
        hInfiniteModePublisher = connectedNode.newPublisher("hInfiniteMode", Header._TYPE);
        std_msgs.Header hInfiniteHeader = hInfiniteModePublisher.newMessage();
        hInfiniteHeader.setStamp(connectedNode.getCurrentTime());
        hInfiniteHeader.setFrameId("Phone");
        hInfiniteModePublisher.publish(hInfiniteHeader);
    }

    public void publisherForStanleyModeButton() {
        stanleyModePublisher = connectedNode.newPublisher("stanleyMode", Header._TYPE);
        std_msgs.Header stanleyHeader = stanleyModePublisher.newMessage();
        stanleyHeader.setStamp(connectedNode.getCurrentTime());
        stanleyHeader.setFrameId("Phone");
        stanleyModePublisher.publish(stanleyHeader);
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

    public void setThrottleEnablePublisher(boolean value) {
        throttleEnablePublisher = connectedNode.newPublisher("can/throttle_enable", Bool._TYPE);
        std_msgs.Bool throttleEnable = throttleEnablePublisher.newMessage();
        throttleEnable.setData(value);
        throttleEnablePublisher.publish(throttleEnable);
    }

    public void setSteeringEnablePublisher(boolean value) {
        steeringEnablePublisher = connectedNode.newPublisher("can/steering_enable", Bool._TYPE);
        std_msgs.Bool steeringEnable = steeringEnablePublisher.newMessage();
        steeringEnable.setData(value);
        steeringEnablePublisher.publish(steeringEnable);
    }

    public void setBrakeEnablePublisher(boolean value) {
        brakeEnablePublisher = connectedNode.newPublisher("can/brake_enable", Bool._TYPE);
        std_msgs.Bool brakeEnable = brakeEnablePublisher.newMessage();
        brakeEnable.setData(value);
        brakeEnablePublisher.publish(brakeEnable);
    }

    public void publisherForSteering(final float angle) {
        ackermann_msgs.AckermannDriveStamped steeringAckerMannDriveStamped = ackermannPublisher.newMessage();
        steeringAckerMannDriveStamped.getDrive().setSteeringAngleVelocity(0);
        steeringAckerMannDriveStamped.getDrive().setSteeringAngle(angle);
        steeringAckerMannDriveStamped.getDrive().setAcceleration(0);
        steeringAckerMannDriveStamped.getDrive().setJerk(0);
        steeringAckerMannDriveStamped.getHeader().setStamp(connectedNode.getCurrentTime());
        steeringAckerMannDriveStamped.getHeader().setFrameId("Phone");
        ackermannPublisher.publish(steeringAckerMannDriveStamped);
    }

    public void publisherForBrakeAndThrottle(final float strength) {
        ackermann_msgs.AckermannDriveStamped throttleBrakeAckerMannDriveStamped = ackermannPublisher.newMessage();
        throttleBrakeAckerMannDriveStamped.getDrive().setSteeringAngleVelocity(0);
        throttleBrakeAckerMannDriveStamped.getDrive().setAcceleration(0);
        throttleBrakeAckerMannDriveStamped.getDrive().setJerk(0);
        throttleBrakeAckerMannDriveStamped.getDrive().setSpeed(strength);
        throttleBrakeAckerMannDriveStamped.getHeader().setStamp(connectedNode.getCurrentTime());
        throttleBrakeAckerMannDriveStamped.getHeader().setFrameId("Phone");
        ackermannPublisher.publish(throttleBrakeAckerMannDriveStamped);
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
        } else if (ackermannPublisher != null) {
            ackermannPublisher.shutdown();
        } else if (throttleEnablePublisher != null) {
            throttleEnablePublisher.shutdown();
        } else if (steeringEnablePublisher != null) {
            steeringEnablePublisher.shutdown();
        } else if (brakeEnablePublisher != null) {
            brakeEnablePublisher.shutdown();
        } else if (verdeModePublisher != null) {
            verdeModePublisher.shutdown();
        } else if (hInfiniteModePublisher != null) {
            hInfiniteModePublisher.shutdown();
        } else if (stanleyModePublisher != null) {
            stanleyModePublisher.shutdown();
        }
        connectedNode.shutdown();
    }
}

