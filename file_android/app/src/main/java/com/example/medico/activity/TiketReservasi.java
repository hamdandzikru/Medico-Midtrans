package com.example.medico.activity;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.medico.R;
import com.example.medico.utils.MidtransUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.LocalDataHandler;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.models.ItemDetails;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.medico.FungsiFungsi.StringFunction.ConvertPriceToInt;

public class TiketReservasi extends AppCompatActivity implements TransactionFinishedCallback {
    Toolbar toolbar;

    String email_key = "";
    String email_key_new = "";
    int pageWidth = 1200;
    TextView TVNamaRS, TVMedicalCheckUp, TVHargaPelayanan, TVTanggalPelayanan, TVWaktuPelayanan;
    Button btn_cetak;
    private Bitmap myBitmap;
    private String IdTicket, penyedia_layanan, jns_layanan;
    private int harga_layanan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiket_reservasi);
        getUsernameLocal();
        initData();

        toolbar = (Toolbar) findViewById(R.id.ToolbarTiketReservasi);

        TextView toolbar_text = findViewById(R.id.toolbar_text);
        final TextView TVNamaRS = findViewById(R.id.TVNamaRS);
        final TextView TVMedicalCheckUp = findViewById(R.id.TVMedicalCheckUp);
        final TextView TVHargaPelayanan = findViewById(R.id.HargaPelayanan);
        final TextView TVTanggalPelayanan = findViewById(R.id.TanggalPelayanan);
        final TextView TVWaktuPelayanan = findViewById(R.id.WaktuPelayanan);
        Button btnPembayaran = findViewById(R.id.btnPembayaran);

        btnPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initMidtransSdk();
                MidtransSDK.getInstance().setTransactionRequest(initTransactionRequest());
                MidtransSDK.getInstance().startPaymentUiFlow(TiketReservasi.this);
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_text.setText("Tiket Reservasi");

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Tiket").child(email_key_new).child(IdTicket);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TVNamaRS.setText(dataSnapshot.child("penyedia_layanan").getValue().toString());
                TVMedicalCheckUp.setText(dataSnapshot.child("jns_layanan").getValue().toString());
                TVHargaPelayanan.setText(dataSnapshot.child("harga_layanan").getValue().toString());
                TVTanggalPelayanan.setText(dataSnapshot.child("tanggal").getValue().toString());
                TVWaktuPelayanan.setText(dataSnapshot.child("waktu").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        //Melakukan screenshot pada background
//        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.RL_tiket);
//        relativeLayout.post(new Runnable() {
//            public void run() {
//                relativeLayout.setDrawingCacheEnabled(true);
//                //take screenshot
//                myBitmap = captureScreen(relativeLayout);
//
////                Toast.makeText(getApplicationContext(), "Screenshot captured..!", Toast.LENGTH_LONG).show();
//
//                try {
//                    if (myBitmap != null) {
//                        //save image to SD card
//                        saveImage(myBitmap);
//                    }
////                    Toast.makeText(getApplicationContext(), "Screenshot saved..!", Toast.LENGTH_LONG).show();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//
//            }
//        });

    }

    private void initData() {
        IdTicket = getIntent().getStringExtra(getString(R.string.IdTicket));

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Tiket").child(email_key_new).child(IdTicket);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                penyedia_layanan = dataSnapshot.child("penyedia_layanan").getValue().toString();
                jns_layanan = dataSnapshot.child("jns_layanan").getValue().toString();
                harga_layanan = ConvertPriceToInt(dataSnapshot.child("harga_layanan").getValue().toString());
               /* TVTanggalPelayanan.setText(dataSnapshot.child("tanggal").getValue().toString());
                TVWaktuPelayanan.setText(dataSnapshot.child("waktu").getValue().toString());*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    private void initMidtransSdk() {
        String client_key = MidtransUtils.getMerchantClientKey();
        String base_url = MidtransUtils.MERCHANT_BASE_CHECKOUT_URL;

        SdkUIFlowBuilder.init()
                .setClientKey(client_key) // client_key is mandatory
                .setContext(this) // context is mandatory
                .setTransactionFinishedCallback(this) // set transaction finish callback (sdk callback)
                .setMerchantBaseUrl(base_url) //set merchant url
                .enableLog(true) // enable sdk log
                .buildSDK();
    }

    private TransactionRequest initTransactionRequest() {
        // Create new Transaction Request
        TransactionRequest transactionRequestNew = new
                TransactionRequest(IdTicket, harga_layanan);

        //set customer details
        //transactionRequestNew.setCustomerDetails(initCustomerDetails());


        // set item details
        ItemDetails itemDetails = new ItemDetails(penyedia_layanan, harga_layanan, 1, jns_layanan);

        // Add item details into item detail list.
        ArrayList<ItemDetails> itemDetailsArrayList = new ArrayList<>();
        itemDetailsArrayList.add(itemDetails);
        transactionRequestNew.setItemDetails(itemDetailsArrayList);


        /*LocalDataHandler.saveObject("user_details", null);*/


        return transactionRequestNew;
    }


    //Pembuatan file screenshot
    public Bitmap captureScreen(View v) {
        View rv = v.getRootView();
        Bitmap screenshot = null;
        try {

            if (v != null) {

                screenshot = Bitmap.createBitmap(rv.getMeasuredWidth(), rv.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(screenshot);
                v.draw(canvas);
            }

        } catch (Exception e) {
            // Log.d("ScreenShotActivity", "Failed to capture screenshot because:" + e.getMessage());
        }

        return screenshot;
    }

    //Penyimpanan Image SS
    public void saveImage(Bitmap bitmap) throws IOException {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 40, bytes);
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "tiket123.jpg");
        f.createNewFile();
        FileOutputStream fo = new FileOutputStream(f, false);
        fo.write(bytes.toByteArray());
        fo.close();
    }

    private void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences("id", MODE_PRIVATE);
        email_key_new = sharedPreferences.getString(email_key, "");
    }

    public void cetaktiket(View view) throws FileNotFoundException {
        imageToPDF();
    }

    public void createPDF() {

        TVNamaRS = findViewById(R.id.TVNamaRS);
        TVHargaPelayanan = findViewById(R.id.HargaPelayanan);
        TVMedicalCheckUp = findViewById(R.id.TVMedicalCheckUp);
        TVTanggalPelayanan = findViewById(R.id.TanggalPelayanan);
        TVWaktuPelayanan = findViewById(R.id.WaktuPelayanan);
        btn_cetak = findViewById(R.id.btn_cetak);


        PdfDocument pdf = new PdfDocument();
        Paint myPaint = new Paint();
        Paint tittlePaint = new Paint();

        PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
        PdfDocument.Page myPage1 = pdf.startPage(myPageInfo1);
        Canvas canvas = myPage1.getCanvas();

        tittlePaint.setTextAlign(Paint.Align.CENTER);
        tittlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        tittlePaint.setTextSize(70);
        canvas.drawText("E-TICKET :", pageWidth / 2, 270, tittlePaint);

        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(35f);
        myPaint.setColor(Color.BLACK);
        canvas.drawText("Customer : " + TVNamaRS.getText(), 20, 590, myPaint);

        pdf.finishPage(myPage1);

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Hello.pdf");

        try {
            if (!file.exists()) {
                // file does not exist, create it
                file.createNewFile();
                pdf.writeTo(new FileOutputStream(file));
                Toast.makeText(getApplicationContext(), "BERHASIL MENYIMPAN TIKET!", Toast.LENGTH_LONG).show();
            } else {
                pdf.writeTo(new FileOutputStream(file, false));
                Toast.makeText(getApplicationContext(), "BERHASIL MENYIMPAN TIKET!", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        pdf.close();
    }


    //Insert png to pdf
    public void imageToPDF() throws FileNotFoundException {
        String dirpath;
        try {
            Document document = new Document();
            dirpath = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
            PdfWriter.getInstance(document, new FileOutputStream(dirpath + "/Tikettt.pdf")); //  Change pdf's name.
            document.open();
            Image img = Image.getInstance(Environment.getExternalStorageDirectory() + File.separator + "tiket123.jpg");
            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / img.getWidth()) * 100;
            img.scalePercent(scaler);
            img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            document.add(img);
            document.close();
            Toast.makeText(this, "PDF Generated successfully!..", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

        }
    }


    @Override
    public void onTransactionFinished(TransactionResult result) {
        Log.d("finalx", "rsultd:" + result.getResponse());

        if (result.getResponse() != null) {
            Log.d("finalx", "result:" + result.getResponse().getStatusMessage());
            Log.d("finalx", "result>fraud:" + result.getResponse().getFraudStatus());
            switch (result.getStatus()) {
                case TransactionResult.STATUS_SUCCESS:
                    Toast.makeText(this, "Transaction Finished. ID: " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    break;
                case TransactionResult.STATUS_PENDING:
                    Toast.makeText(this, "Transaction Pending. ID: " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    break;
                case TransactionResult.STATUS_FAILED:
                    Toast.makeText(this, "Transaction Failed. ID: " + result.getResponse().getTransactionId() + ". Message: " + result.getResponse().getStatusMessage(), Toast.LENGTH_LONG).show();
                    break;
            }
            result.getResponse().getValidationMessages();
        } else if (result.isTransactionCanceled()) {
            Toast.makeText(this, "Transaction Canceled", Toast.LENGTH_LONG).show();
        } else {
            if (result.getStatus().equalsIgnoreCase(TransactionResult.STATUS_INVALID)) {
                Toast.makeText(this, "Transaction Invalid", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Transaction Finished with failure.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
