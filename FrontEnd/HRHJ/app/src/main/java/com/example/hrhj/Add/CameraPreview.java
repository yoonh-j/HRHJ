package com.example.hrhj.Add;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CameraPreview implements SurfaceHolder.Callback {

    private Camera camera;
    private Camera.CameraInfo cameraInfo;
    private int cameraId;
    private Camera.Parameters param;
    private int displayOrientation;
    private SurfaceHolder surfaceHolder;
    public String picPath;
    public Bitmap bitmap;
    private byte[] currentData;
    private FragmentActivity fragmentActivity;
    private Context context;
    private boolean isPreview = false;
    public boolean isFlashOn = false;

    public CameraPreview(Context context, FragmentActivity fragmentActivity, int cameraId, SurfaceView surfaceView) {
        this.context = context;
        this.fragmentActivity = fragmentActivity;
        this.cameraId = cameraId;
        surfaceView.setVisibility(View.VISIBLE);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera = Camera.open(cameraId);
        } catch(Exception e) { }

        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        cameraInfo = info;
        displayOrientation = fragmentActivity.getWindowManager().getDefaultDisplay().getRotation();

        int orientation = setPreviewOrientation(cameraInfo, displayOrientation);
        camera.setDisplayOrientation(orientation);

        param = camera.getParameters();
//        if(param != null) {
//            List<Camera.Size> pictureSizeList = param.getSupportedPictureSizes();
//            for(Camera.Size size : pictureSizeList) {
//                Log.d("##PictureSize##", "width: "+size.width+", height: "+size.height);
//            }
//            List<Camera.Size> previewSizeList = param.getSupportedPreviewSizes();
//            for(Camera.Size size : previewSizeList) {
//                Log.d("##PreviewSize##", "width: "+size.width+", height: "+size.height);
//            }
//        }

        int width = fragmentActivity.getWindowManager().getDefaultDisplay().getWidth();
        int height = width / 3 * 4;

        List<Camera.Size> previewSizes = camera.getParameters().getSupportedPreviewSizes();
        Camera.Size previewSize = getPreviewSize(previewSizes, width, height);
        param.setPreviewSize(previewSize.width, previewSize.height);
        //Log.d("##getPreviewSize##", "width: "+previewSize.width+", height: "+previewSize.height);

        List<Camera.Size> pictureSizes = camera.getParameters().getSupportedPictureSizes();
        Camera.Size pictureSize = getPreviewSize(pictureSizes, width, height);
        param.setPictureSize(pictureSize.width, pictureSize.height);
        //Log.d("##getPictureSize##", "width: "+pictureSize.width+", height: "+pictureSize.height);

        holder.setFixedSize(width, height);

        List<String> focusModes = param.getSupportedFocusModes();
        if(focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            param.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }
        camera.setParameters(param);

        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
            isPreview = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (surfaceHolder.getSurface() == null) {
            return;
        }
        try {
            camera.stopPreview();
        } catch (Exception e) {}

        int orientation = setPreviewOrientation(cameraInfo, displayOrientation);
        camera.setDisplayOrientation(orientation);

        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch(Exception e) {}
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            if (isPreview) {
                camera.stopPreview();
                camera = null;
                isPreview = false;
            }
        }
    }

    public Camera.Size getPreviewSize(List<Camera.Size> sizes, int w, int h) {
        double ASPECT_TOLERANCE = 0.05;
        double ratio = (double) h / w;

        if(sizes == null) { return null; }
        Camera.Size optimalSize = null;
        double diff = Double.MAX_VALUE;
        int height = h;

        for(Camera.Size size : sizes) {
            double r = (double) size.width / size.height;

            if(Math.abs(r - ratio) > ASPECT_TOLERANCE) continue;
            if(Math.abs(size.height - height) < diff) {
                optimalSize = size;
                diff = Math.abs(size.height - height);
            }
        }

        if (optimalSize == null) {
            diff = Double.MAX_VALUE;

            for(Camera.Size size : sizes) {
                if(Math.abs(size.height - height) < diff) {
                    optimalSize = size;
                    diff = Math.abs(size.height - height);
                }
            }
        }
        return optimalSize;
    }

    public static int setPreviewOrientation(Camera.CameraInfo info, int rotation) {
        int degree = 0;

        switch(rotation) {
            case Surface.ROTATION_0:
                degree = 0;
                break;
            case Surface.ROTATION_90:
                degree = 90;
                break;
            case Surface.ROTATION_180:
                degree = 180;
                break;
            case Surface.ROTATION_270:
                degree = 360;
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degree) % 360;
            result = (360 - result) % 360;
        } else {
            result = (info.orientation - degree + 360) % 360;
        }
        return result;
    }

    public void takePicture() {
        camera.takePicture(shutterCallback, rawCallback, jpegCallback);
    }

    Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        public void onShutter() {}
    };
    Camera.PictureCallback rawCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

        }
    };
    Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            int w = camera.getParameters().getPictureSize().width;
            int h = camera.getParameters().getPictureSize().height;
            int orientation = setPreviewOrientation(cameraInfo, displayOrientation);

            // byte array to bitmap
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

            // image rotation
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);

            // bitmap to byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            currentData = stream.toByteArray();

            new SaveImageTask().execute(currentData);
        }
    };

    private class SaveImageTask extends AsyncTask<byte[], Void, Void> {
        @Override
        protected Void doInBackground(byte[]... bytes) {
            FileOutputStream outputStream = null;

            try {
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/하루한장");
                if (!path.exists()) {
                    path.mkdirs();
                }

                String fileName = String.format(Locale.KOREA, "%d.jpg", System.currentTimeMillis());
                File outputFile = new File(path, fileName);
                picPath = outputFile.toString();

                outputStream = new FileOutputStream(outputFile);
                outputStream.write(bytes[0]);
                outputStream.flush();
                outputStream.close();

                camera.startPreview();

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(Uri.fromFile(outputFile));
                context.sendBroadcast(mediaScanIntent);

                try {
                    camera.setPreviewDisplay(surfaceHolder);
                    camera.startPreview();
                } catch (Exception e) {
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public String getPicPath() {
        return picPath;
    }

    public void flashlight() {
        isFlashOn = !isFlashOn;

        if(isFlashOn) {
            param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(param);
            camera.startPreview();
        } else {
            param.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(param);
            camera.startPreview();
        }
    }
}
