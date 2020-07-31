package com.example.lsi_app;

import org.ros.message.MessageListener;
import org.ros.message.Time;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

import sensor_msgs.CompressedImage;
import sensor_msgs.NavSatFix;
import std_msgs.Float64;
import std_msgs.Header;

public class Listener extends AbstractNodeMain {

    private AutomaticControllActivity automaticControllActivity;
    private JoystickActivity joystickActivity;
    private VisualizationActivity visualizationActivity;
    private ConnectedNode connectedNode;
    private Subscriber<Header> suscriberStillAlive;
    private Subscriber<Float64> speedSubscriber;
    private Subscriber<Float64> steeringSubscriber;
    private Subscriber<sensor_msgs.CompressedImage> imageSubscriber;
    private Subscriber<sensor_msgs.NavSatFix> navSatFixSubscriber;
    private Time time;

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("AutoCarts");
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

    @Override
    public void onStart(ConnectedNode connectedNode) {
        this.connectedNode = connectedNode;
        if(automaticControllActivity != null) {
            createListenersOfAutomaticControllActivity();
        }

        if(joystickActivity != null) {
            createListenersOfJoystickActivity();
        }

        if(visualizationActivity != null) {
            createListenersOfVisualizationActivity();
        }
    }

    private void createListenersOfAutomaticControllActivity() {
        suscriberStillAlive = connectedNode.newSubscriber("stillAlive", Header._TYPE);
        suscriberStillAlive.addMessageListener(new MessageListener<Header>() {
            @Override
            public void onNewMessage(final Header header) {
                automaticControllActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int finals = header.getStamp().secs;
                        int finalns = header.getStamp().nsecs;
                        if(time != null) {
                            int initials = time.secs;
                            int initialns = time.nsecs;
                            int diferenceInS = finals - initials;
                            int diferenceInNs = finalns - initialns;

                            if (diferenceInS <= 0.2 || diferenceInNs <= 200000000) {
                                automaticControllActivity.hideRedStillAlive();
                                automaticControllActivity.hideOrangeStillAlive();
                                automaticControllActivity.showGreenStillAlive();
                            } else if(diferenceInS >= 1 || diferenceInNs >= 1000000000) {
                                automaticControllActivity.hideGreenStillAlive();
                                automaticControllActivity.hideOrangeStillAlive();
                                automaticControllActivity.showRedStillAlive();
                            } else {
                                automaticControllActivity.hideGreenStillAlive();
                                automaticControllActivity.hideRedStillAlive();
                                automaticControllActivity.showOrangeStillAlive();
                            }
                        }
                        time = header.getStamp();
                    }
                });
            }
        });

        speedSubscriber = connectedNode.newSubscriber("/can/current_speed", Float64._TYPE);
        speedSubscriber.addMessageListener(new MessageListener<Float64>() {
            @Override
            public void onNewMessage(final Float64 float64) {
                automaticControllActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        automaticControllActivity.setGeneralSpeedText(float64.getData());
                    }
                });
            }
        });

        steeringSubscriber = connectedNode.newSubscriber("/can/current_steering", Float64._TYPE);
        steeringSubscriber.addMessageListener(new MessageListener<Float64>() {
            @Override
            public void onNewMessage(final Float64 float64) {
                automaticControllActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        automaticControllActivity.setGeneralSteeringText(float64.getData());
                    }
                });
            }
        });
    }

    private void createListenersOfJoystickActivity() {
        suscriberStillAlive = connectedNode.newSubscriber("stillAlive", Header._TYPE);
        suscriberStillAlive.addMessageListener(new MessageListener<Header>() {
            @Override
            public void onNewMessage(final Header header) {
                joystickActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int finals = header.getStamp().secs;
                        int finalns = header.getStamp().nsecs;
                        if(time != null) {
                            int initials = time.secs;
                            int initialns = time.nsecs;
                            int diferenceInS = finals - initials;
                            int diferenceInNs = finalns - initialns;

                            if (diferenceInS <= 0.2 || diferenceInNs <= 200000000) {
                                joystickActivity.hideRedStillAlive();
                                joystickActivity.hideOrangeStillAlive();
                                joystickActivity.showGreenStillAlive();
                            } else if(diferenceInS >= 1 || diferenceInNs >= 1000000000) {
                                joystickActivity.hideGreenStillAlive();
                                joystickActivity.hideOrangeStillAlive();
                                joystickActivity.showRedStillAlive();
                            } else {
                                joystickActivity.hideGreenStillAlive();
                                joystickActivity.hideRedStillAlive();
                                joystickActivity.showOrangeStillAlive();
                            }
                        }
                        time = header.getStamp();
                    }
                });
            }
        });

        speedSubscriber = connectedNode.newSubscriber("/can/current_speed", Float64._TYPE);
        speedSubscriber.addMessageListener(new MessageListener<Float64>() {
            @Override
            public void onNewMessage(final Float64 float64) {
                joystickActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        joystickActivity.setGeneralSpeedText(float64.getData());
                    }
                });
            }
        });

        steeringSubscriber = connectedNode.newSubscriber("/can/current_steering", Float64._TYPE);
        steeringSubscriber.addMessageListener(new MessageListener<Float64>() {
            @Override
            public void onNewMessage(final Float64 float64) {
                joystickActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        joystickActivity.setGeneralSteeringText(float64.getData());
                    }
                });
            }
        });
    }

    private void createListenersOfVisualizationActivity() {
        suscriberStillAlive = connectedNode.newSubscriber("stillAlive", Header._TYPE);
        suscriberStillAlive.addMessageListener(new MessageListener<Header>() {
            @Override
            public void onNewMessage(final Header header) {
                visualizationActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int finals = header.getStamp().secs;
                        int finalns = header.getStamp().nsecs;
                        if(time != null) {
                            int initials = time.secs;
                            int initialns = time.nsecs;
                            int diferenceInS = finals - initials;
                            int diferenceInNs = finalns - initialns;

                            if (diferenceInS <= 0.2 || diferenceInNs <= 200000000) {
                                visualizationActivity.hideRedStillAlive();
                                visualizationActivity.hideOrangeStillAlive();
                                visualizationActivity.showGreenStillAlive();
                            } else if(diferenceInS >= 1 || diferenceInNs >= 1000000000) {
                                visualizationActivity.hideGreenStillAlive();
                                visualizationActivity.hideOrangeStillAlive();
                                visualizationActivity.showRedStillAlive();
                            } else {
                                visualizationActivity.hideGreenStillAlive();
                                visualizationActivity.hideRedStillAlive();
                                visualizationActivity.showOrangeStillAlive();
                            }
                        }
                        time = header.getStamp();
                    }
                });
            }
        });
    }

    public void createListenerForImageFormat(String topic) {
        shutdownPositionSubscriber();
        imageSubscriber = connectedNode.newSubscriber(topic, CompressedImage._TYPE);
        imageSubscriber.addMessageListener(new MessageListener<CompressedImage>() {
            @Override
            public void onNewMessage(CompressedImage image) {
                visualizationActivity.showImageFromSubscriber(image);
            }
        });
    }

    public void createListenerForPositionFormat(String topic) {
        shutdownImageSubscriber();
        navSatFixSubscriber = connectedNode.newSubscriber(topic, NavSatFix._TYPE);
        navSatFixSubscriber.addMessageListener(new MessageListener<NavSatFix>() {
            @Override
            public void onNewMessage(NavSatFix navSatFix) {
                visualizationActivity.showPositionInMap(navSatFix);
            }
        });
    }

    public void shutdownImageSubscriber() {
        if (imageSubscriber != null) {
            imageSubscriber.shutdown();
        }
    }

    public void shutdownPositionSubscriber() {
        if (navSatFixSubscriber != null) {
            navSatFixSubscriber.shutdown();
        }
    }

    public void closeActivity() {
        if (suscriberStillAlive != null) {
            suscriberStillAlive.shutdown();
        } else if (speedSubscriber != null) {
            speedSubscriber.shutdown();
        } else if (steeringSubscriber != null) {
            steeringSubscriber.shutdown();
        } else if (imageSubscriber != null) {
            imageSubscriber.shutdown();
        } else if (navSatFixSubscriber != null) {
            navSatFixSubscriber.shutdown();
        }
        connectedNode.shutdown();
    }
}

