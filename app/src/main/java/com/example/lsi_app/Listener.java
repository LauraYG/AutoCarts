package com.example.lsi_app;

import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

public class Listener extends AbstractNodeMain {

    private AutomaticControllActivity automaticControllActivity;
    private JoystickActivity joystickActivity;
    private VisualizationActivity visualizationActivity;
    private ConnectedNode connectedNode;
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

