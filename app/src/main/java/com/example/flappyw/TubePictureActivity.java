package com.example.flappyw;


import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TubePictureActivity extends Activity {

    int height, width;
    Bitmap FullImage;
    Bitmap FullImageCopy;
    Bitmap FullImageTube;
    Rect rectCrop;
    Mat FullImageMatrix;
    TubeDrawingView RechteckView;

    ImageView photo;
    TextView WidthHeight;

    String CurrentPhotoPath;

    static final int REQUEST_IMAGE_CAPTURE =1034;

    public final String APP_TAG = "FlappyWo";
    public String photoFileName = "photo.jpg";
    File photoFile;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taketubepicture);
        Intent intent = getIntent();


        OpenCVLoader.initDebug();

        photo = (ImageView) findViewById(R.id.im);
        WidthHeight = (TextView) findViewById(R.id.text1);
        RechteckView = (TubeDrawingView) findViewById(R.id.rechteckView);

        Button clickButton = (Button) findViewById(R.id.Button);
        if (clickButton != null) {
            clickButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    scale();
                }
            });
        }


        Button clickButton2 = (Button) findViewById(R.id.Button2);
        if (clickButton2 != null) {
            clickButton2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        dispatchTakePictureIntent();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("2");
                    }

                }
            });
        }

        Button clickButton3 = (Button) findViewById(R.id.FinishButton);
        if (clickButton3 != null) {
            clickButton3.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    finishThis();
                }
            });
        }
    }

    public void scale(){

        if(FullImage== null ){
            return;
        }

        try {
            FullImageCopy = FullImage.copy(Bitmap.Config.ARGB_8888, true);
            FullImageMatrix = new Mat(height, width, CvType.CV_8UC1);
            Utils.bitmapToMat(FullImageCopy, FullImageMatrix);
            FullImageTube = Bitmap.createBitmap( 100 ,1066 , Bitmap.Config.ARGB_8888);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("0");
        }
        try {
            int Start = (int) RechteckView.returnXCoordinate();
            rectCrop = new Rect(Start, 0, 100, 1066);
            Mat image_roi = new Mat(FullImageMatrix, rectCrop);
            Utils.matToBitmap(image_roi,FullImageTube);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("1");
        }

        try {
            ImageView img2 = (ImageView) findViewById(R.id.im2);
            img2.setImageBitmap(FullImageTube);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("3");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                FullImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                FullImage = BitmapScaler.scaleToFitWidth(FullImage, 800);
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                photo.setImageBitmap(FullImage);
                height = FullImage.getHeight();
                width = FullImage.getWidth();
                WidthHeight.setText("Weite: " + width + "Höhe: " + height);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }


       /*        try {
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
                    bm = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

                    try {
                       // bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(CurrentPhotoPath));
                        bm = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Jooo");

                    }
                    photo.setImageBitmap(bm);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("3");
            } */
    }

    private void dispatchTakePictureIntent() {

        try {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                // File photoFile = null;
                // photoFile = createImageFile();
                photoFile = getPhotoFileUri(photoFileName);
                // Continue only if the File was successfully created
                try {
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(TubePictureActivity.this,
                                "com.example.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("88");

                }

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("4");
        }
    }

    private File createImageFile() throws IOException {

        try {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            // Save a file: path for use with ACTION_VIEW intents
            CurrentPhotoPath = "File" + image.getAbsolutePath();
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("5");
            return null;
        }
    }

    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    public String saveBitmap(Bitmap bitmap) {
        String fileName2 = "Tube";//no .png or .jpg needed
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput(fileName2, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            // remember close file output
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error 100");
            fileName2 = null;
        }
        return fileName2;
    }

    public void finishThis(){
        Intent intent = new Intent(TubePictureActivity.this,MainActivity.class);
        intent.putExtra("KEY", saveBitmap(FullImageTube));
        startActivity(intent);
        this.finish();
    }

}




