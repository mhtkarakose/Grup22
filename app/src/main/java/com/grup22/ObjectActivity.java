package com.grup22;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.ml.vision.label.FirebaseVisionLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetector;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetectorOptions;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ObjectActivity extends AppCompatActivity implements Camera.PreviewCallback {

    private static final int CAMERA_REQUEST = 1888; // field

    private ImageView imageView;
    private TextView textView;
    private ProgressBar progressBar;

    private FrameLayout frameLayout;
    private Camera camera;
    private ShowCamera showCamera;

    public static int scan = 0;

    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object);

        imageView = findViewById(R.id.imageView);

        textView = findViewById(R.id.textView);

        progressBar = findViewById(R.id.progressBar);

        frameLayout = findViewById(R.id.frameLayout);

        button2 = findViewById(R.id.button2);

        //takePicture();

        camera = Camera.open();

        showCamera = new ShowCamera(this,camera);
        frameLayout.addView(showCamera);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                //runFaceDetector(bitmap);
                runImageLabelling(bitmap);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            //Bitmap picture = (Bitmap) data.getExtras().get("data");//this is your bitmap image and now you can do whatever you want with this
            //imageView.setImageBitmap(picture); //for example I put bmp in an ImageView
            //progressBar.setVisibility(View.VISIBLE);
            //runFaceDetector(picture);
        }
    }

    private void runImageLabelling(Bitmap bitmap) {
        FirebaseVisionLabelDetectorOptions options = new FirebaseVisionLabelDetectorOptions.Builder()
                        .setConfidenceThreshold(0.8f)
                        .build();

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        //FirebaseVisionLabelDetector detector = FirebaseVision.getInstance()
        //        .getVisionLabelDetector(options);

        FirebaseVisionLabelDetector detector = FirebaseVision.getInstance()
                .getVisionLabelDetector();

        detector.detectInImage(image)
                .addOnSuccessListener(
                        new OnSuccessListener<List<FirebaseVisionLabel>>() {
                            @Override
                            public void onSuccess(List<FirebaseVisionLabel> labels) {
                                // Task completed successfully
                                // ...

                                /*for (FirebaseVisionLabel label: labels) {
                                    String text = label.getLabel();
                                    String entityId = label.getEntityId();
                                    float confidence = label.getConfidence();


                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                                }*/

                                if(labels.size() > 1)
                                {
                                    for (int i = 0; i < 1; i++)
                                    {
                                        FirebaseVisionLabel label = labels.get(0);
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getApplicationContext(), label.getLabel(), Toast.LENGTH_LONG).show();
                                        Intent s = new Intent(ObjectActivity.this,QuizActivity.class);
                                        s.putExtra(QuizActivity.EXTRA_DATA_KEY,label.getLabel());
                                        startActivity(s);
                                    }
                                }
                                else
                                {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getApplicationContext(), "Bulunamadı", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                // ...
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        try {
            /*Camera.Parameters parameters = camera.getParameters();

            if(this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE)
            {
                parameters.set("orientation","portrait");
                camera.setDisplayOrientation(90);
                parameters.setRotation(90);
            }
            else
            {
                parameters.set("orientation","landscape");
                camera.setDisplayOrientation(0);
                parameters.setRotation(0);
            }

            int width = parameters.getPreviewSize().width;
            int height = parameters.getPreviewSize().height;

            YuvImage yuv = new YuvImage(data, parameters.getPreviewFormat(), width, height, null);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            yuv.compressToJpeg(new Rect(0, 0, width, height),  100, out);

            byte[] bytes = out.toByteArray();
            //final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);*/

            if (data.length > 0)
            {
                Camera.Parameters parameters = camera.getParameters();

                //parameters.setRotation(90);

                int width = parameters.getPreviewSize().width;
                int height = parameters.getPreviewSize().height;

                YuvImage yuv = new YuvImage(data, parameters.getPreviewFormat(), width, height, null);
                ByteArrayOutputStream out =
                        new ByteArrayOutputStream();
                yuv.compressToJpeg(new Rect(0, 0, width, height), 100, out);

                byte[] compressed = out.toByteArray();

                final Bitmap newBmp = BitmapFactory.decodeByteArray(compressed, 0, compressed.length);

                //Matrix mat = new Matrix();
                //mat.postRotate(PrefsController.instance.getPrefs().getCameraPrefs(cameraId).angle);
                //newBmp = Bitmap.createBitmap(newBmp, 0, 0, newBmp.getWidth(), newBmp.getHeight(), mat, true);
                /*ByteArrayOutputStream out2 = new ByteArrayOutputStream();
                if (quality) {
                    newBmp.compress(Bitmap.CompressFormat.PNG, 100, out2);
                } else {
                    newBmp.compress(Bitmap.CompressFormat.JPEG, 80, out2);
                }*/

                Matrix matrix = new Matrix();

                matrix.postRotate(90);

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(newBmp, width, height, true);

                final Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);

                ObjectActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        //imageView.setImageBitmap(newBmp);
                        //if(scan == 0) {
                        //    scan = 1;
                        //   progressBar.setVisibility(View.VISIBLE);
                        imageView.setImageBitmap(rotatedBitmap);
                        //   runFaceDetector(rotatedBitmap);
                        //}
                    }
                });
            }
        } catch (Exception e) {
            Toast toast = Toast
                    .makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
