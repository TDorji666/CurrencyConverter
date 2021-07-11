package com.gcit.sherigcaref1;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class Result extends AppCompatActivity {

    ImageView mEditText;
    Button mButton;
    EditText name;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    Uri PDFFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mEditText = (ImageView) findViewById(R.id.editText);
        mButton = (Button) findViewById(R.id.btn);
        name = findViewById(R.id.name);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("pdf");
        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent();
                                intent.setType("application/pdf");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select PDF Files"),101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processUpload(PDFFilePath);
            }
        });
    }

    private void processUpload(Uri teacherFilePath) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.show();
        if(teacherFilePath != null){
            StorageReference reference = storageReference.child("TeacherPDFResult/" +System.currentTimeMillis() + ".pdf");
            reference.putFile(teacherFilePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    putPDF teacherPDFHelperClass = new putPDF(name.getText().toString().trim(),uri.toString());
                                    databaseReference.child(databaseReference.push().getKey()).setValue(teacherPDFHelperClass);
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"File Uploaded Successfully",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            float percent = (100 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                            progressDialog.setMessage("File Uploading...." + (int)percent + "%");
                        }
                    });
        }
        else{
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),"No file selected",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == RESULT_OK){
            mButton.setEnabled(true);
            name.setText(data.getDataString()
                    .substring(data.getDataString().lastIndexOf("/") + 1));
            PDFFilePath = data.getData();
        }
    }

    public void RetrieveFile(View view) {
        Intent intent=new Intent(getApplicationContext(), RetrievePDF.class);
        startActivity(intent);
    }
}