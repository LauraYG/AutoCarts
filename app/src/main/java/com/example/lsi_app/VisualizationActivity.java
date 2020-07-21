package com.example.lsi_app;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jboss.netty.buffer.ChannelBuffer;
import org.ros.android.AppCompatRosActivity;
import org.ros.internal.node.client.MasterClient;
import org.ros.internal.node.response.Response;
import org.ros.master.client.TopicType;
import org.ros.namespace.GraphName;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sensor_msgs.CompressedImage;
import sensor_msgs.Image;
import sensor_msgs.NavSatFix;

public class VisualizationActivity extends AppCompatRosActivity implements OnMapReadyCallback {

    private Talker talker;
    private Listener listener;
    private ImageView greenStillAliveImageView;
    private ImageView orangeStillAliveImageView;
    private ImageView redStillAliveImageView;
    private ImageView emergencyImageView;
    private ImageView exitImageView;
    private ImageView showTopicListButton;
    private ImageView imageForSubscriber;
    private List<String> topicsForVisualizationList = new ArrayList<>();
    private MapView mapView;
    private NavSatFix actualNavSatFix;
    private Thread imageThread;
    private Thread mapThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualization_layout);

        findViews();
        setItemsDesign();
        onClickListenerForButtons();
        mapView.onCreate(savedInstanceState);
    }

    public VisualizationActivity () {
        super("Visualization Activity", "Visualization Activity");
    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {
        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(getRosHostname(), getMasterUri());
        listener = new Listener(this);
        nodeConfiguration.setNodeName("listener");
        nodeMainExecutor.execute(listener, nodeConfiguration);

        NodeConfiguration nodeConfiguration2 = NodeConfiguration.newPublic(getRosHostname(), getMasterUri());
        talker = new Talker(this);
        nodeConfiguration2.setNodeName("talker");
        nodeMainExecutor.execute(talker, nodeConfiguration2);
    }

    private void findViews() {
        greenStillAliveImageView = findViewById(R.id.stillAlive_green_imageview);
        orangeStillAliveImageView = findViewById(R.id.stillAlive_orange_imageview);
        redStillAliveImageView = findViewById(R.id.stillAlive_red_imageview);
        emergencyImageView = findViewById(R.id.emergency_button);
        exitImageView = findViewById(R.id.exit_button);
        showTopicListButton = findViewById(R.id.show_topic_list_button);
        mapView = findViewById(R.id.map);
        imageForSubscriber = findViewById(R.id.image_for_subscriber);
    }

    private void getTopics() {
        if(getMasterUri() != null) {
            MasterClient masterClient = new MasterClient(getMasterUri());
            Response<List<TopicType>> listTopic = masterClient.getTopicTypes(GraphName.of("AutoCarts"));
            topicsForVisualizationList.clear();
            if (!listTopic.getResult().isEmpty()) {
                for (int i = 0; i < listTopic.getResult().size(); i++) {
                    TopicType topicType = listTopic.getResult().get(i);
                    if (topicType.getMessageType().equals("sensor_msgs/NavSatFix") || topicType.getMessageType().equals("sensor_msgs/CompressedImage")) {
                        topicsForVisualizationList.add(topicType.getName());
                    }
                }
            }
        } else {
            returnForBeWithoutMaster();
        }
    }

    private void showTopicListEmpty() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.empty_topics_layout);
        dialog.setCancelable(true);
        dialog.show();
    }

    private void showTopicDialog() {
        getTopics();
        if(!topicsForVisualizationList.isEmpty()) {

            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.topics_layout);
            RecyclerView recyclerView = dialog.findViewById(R.id.topic_list_recycler_view);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(dialog.getContext());
            CustomChooseTopicItemAdapter adapter = new CustomChooseTopicItemAdapter(topicsForVisualizationList, dialog, this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

            dialog.setCancelable(true);
            dialog.show();
        } else {
            showTopicListEmpty();
        }
    }

    public void subscribeToTopic (String topic){
        if(getMasterUri() != null) {
            MasterClient masterClient = new MasterClient(getMasterUri());
            Response<List<TopicType>> listTopic = masterClient.getTopicTypes(GraphName.of("AutoCarts"));
            for (int i = 0; i < listTopic.getResult().size(); i++) {
                TopicType topicType = listTopic.getResult().get(i);
                if (topicType.getName().equals(topic)) {
                    if (topicType.getMessageType().equals("sensor_msgs/CompressedImage")) {
                        listener.createListenerForImageFormat(topic);
                    } else {
                        listener.createListenerForPositionFormat(topic);
                    }
                }
            }
        } else {
            returnForBeWithoutMaster();
        }
    }

    public void showImageFromSubscriber(CompressedImage image) {
        if(mapView != null) {
            if(mapThread != null) {
                mapThread.interrupt();
            }
        }
        ChannelBuffer channelBuffer = image.getData();
        byte[] data = channelBuffer.array();
        final Bitmap bmp = BitmapFactory.decodeByteArray(data, channelBuffer.arrayOffset(), channelBuffer.readableBytes());

        imageThread = new Thread() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mapView != null) {
                            mapView.setVisibility(View.GONE);
                            mapView.onStop();
                        }
                        imageForSubscriber.setVisibility(View.VISIBLE);
                        imageForSubscriber.setImageBitmap(bmp);
                    }
                });
            }
        };
        imageThread.start();
    }

    public void showPositionInMap(NavSatFix navSatFix) {
        actualNavSatFix = navSatFix;
        if(imageForSubscriber != null) {
            if(imageThread != null) {
                imageThread.interrupt();
            }
        }
        mapThread = new Thread() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(imageForSubscriber != null) {
                            imageForSubscriber.setVisibility(View.GONE);
                        }
                        mapView.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                if (googleMap != null) {
                                    LatLng latLng = new LatLng(actualNavSatFix.getLatitude(), actualNavSatFix.getLongitude());
                                    MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                                    googleMap.clear();
                                    googleMap.addMarker(markerOptions);
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f));
                                }
                            }
                        });
                        mapView.setVisibility(View.VISIBLE);
                    }
                });
            }
        };
        mapThread.start();
    }

    private void onClickListenerForButtons() {
        emergencyImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickEmergencyButton();
            }
        });

        exitImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickExitButton();
            }
        });

        showTopicListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTopicDialog();
            }
        });
    }

    private void setItemsDesign() {
        exitImageView.setColorFilter(Color.BLUE);
    }

    private void userClickEmergencyButton() {
        userSentsVehicleMode(2);
    }

    private void userClickExitButton() {
        if(talker != null && listener != null) {
            talker.closeActivity();
            listener.closeActivity();
        }
        Intent intent = new Intent (VisualizationActivity.this, DemosSelectorActivity.class);
        startActivity(intent);
        finish();
    }

    public void userSentsVehicleMode(int mode) {
        if(talker != null) {
            talker.publisherForVehicleMode(mode);
        }
    }

    public void hideGreenStillAlive() {
        greenStillAliveImageView.setVisibility(View.GONE);
    }

    public void hideOrangeStillAlive() {
        orangeStillAliveImageView.setVisibility(View.GONE);
    }

    public void hideRedStillAlive() {
        redStillAliveImageView.setVisibility(View.GONE);
    }

    public void showGreenStillAlive() {
        greenStillAliveImageView.setVisibility(View.VISIBLE);
    }

    public void showOrangeStillAlive() {
        orangeStillAliveImageView.setVisibility(View.VISIBLE);
    }

    public void showRedStillAlive() {
        redStillAliveImageView.setVisibility(View.VISIBLE);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(googleMap != null) {
            LatLng latLng = new LatLng(actualNavSatFix.getLatitude(), actualNavSatFix.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions().position(latLng);
            googleMap.addMarker(markerOptions);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f));
        }
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    public void returnForBeWithoutMaster() {
        finish();
        Intent intent = new Intent (VisualizationActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
