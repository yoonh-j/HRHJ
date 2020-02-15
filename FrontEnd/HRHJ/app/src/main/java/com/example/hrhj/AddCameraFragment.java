package com.example.hrhj;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraDevice;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;


public class AddCameraFragment extends Fragment {

    private View view;
    private CameraPreview cameraPreview;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    public int CAMERA_FACING = Camera.CameraInfo.CAMERA_FACING_BACK;
    private OnFragmentInteractionListener mListener;

    public static AddCameraFragment newInstance() {
        return new AddCameraFragment();
    }
    public AddCameraFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_camera, container, false);
        surfaceView = view.findViewById(R.id.surfaceView);
        initCameraPreview(CAMERA_FACING);

        Button cameraButton = view.findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraPreview.takePicture();
            }
        });

        final ImageButton flashButton = view.findViewById(R.id.flashButton);
        flashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                    cameraPreview.flashlight();
                    flashButton.setImageResource(cameraPreview.isFlashOn? android.R.drawable.ic_menu_compass : android.R.drawable.ic_menu_mylocation);
                } else {
                    Toast.makeText(view.getContext(), "플래시를 지원하지 않는 기종입니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        if(CAMERA_FACING == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            flashButton.setVisibility(View.GONE);
        }

        ImageButton switchCamButton = view.findViewById(R.id.switchCamButton);
        switchCamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CAMERA_FACING == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    CAMERA_FACING = Camera.CameraInfo.CAMERA_FACING_FRONT;
                    //TODO: switchCamera();
                } else {
                    CAMERA_FACING = Camera.CameraInfo.CAMERA_FACING_BACK;
                }
            }
        });
        return view;
    }

    private void initCameraPreview(int cameraFacing) {
        cameraPreview = new CameraPreview(this.getContext(), this.getActivity(), cameraFacing, surfaceView);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
