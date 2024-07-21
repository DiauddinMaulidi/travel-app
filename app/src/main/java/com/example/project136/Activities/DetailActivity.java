package com.example.project136.Activities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.project136.Domains.PopularDomain;
import com.example.project136.R;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    private TextView titleTxt, locationTxt, bedTxt, guideTxt, wifiTxt, descriptionTxt, scoreTxt, priceTxt;
    private PopularDomain item;
    private ImageView picImg, backBtn;
    private Button bookNowButton;
    Date dateTime;
    DateFormat dateFormat;

    int pageWidth = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();
        setVariable();

        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printPDF();
            }
        });
    }

    private void setVariable() {
        item = (PopularDomain) getIntent().getSerializableExtra("object");

        titleTxt.setText(item.getTitle());
        scoreTxt.setText("" + item.getScore());
        locationTxt.setText(item.getLocation());
        bedTxt.setText(item.getBed() + " Bed");
        descriptionTxt.setText(item.getDescription());
        priceTxt.setText("Rp. " + item.getPrice());

        if (item.isGuide()) {
            guideTxt.setText("Guide");
        } else {
            guideTxt.setText("No-Guide");
        }
        if (item.isWifi()) {
            wifiTxt.setText("Wifi");
        } else {
            wifiTxt.setText("No-Wifi");
        }
        int drawableResId = getResources().getIdentifier(item.getPic(), "drawable", getPackageName());

        Glide.with(this)
                .load(drawableResId)
                .into(picImg);

        backBtn.setOnClickListener(v -> finish());
    }

    private void initView() {
        titleTxt = findViewById(R.id.titleTxt);
        locationTxt = findViewById(R.id.locationTxt);
        bedTxt = findViewById(R.id.bedTxt);
        guideTxt = findViewById(R.id.guideTxt);
        wifiTxt = findViewById(R.id.wifiTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        scoreTxt = findViewById(R.id.scoreTxt);
        picImg = findViewById(R.id.picImg);
        scoreTxt = findViewById(R.id.scoreTxt);
        backBtn = findViewById(R.id.backBtn);
        priceTxt = findViewById(R.id.priceTxt);
        bookNowButton = findViewById(R.id.button);
    }

    private void printPDF() {
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        String jobName = getString(R.string.app_name) + " Document";

        printManager.print(jobName, new MyPrintDocumentAdapter(this), null);
    }

    private class MyPrintDocumentAdapter extends PrintDocumentAdapter {
        private Context context;
        private PdfDocument pdfDocument;
        private int totalPages = 1;

        public MyPrintDocumentAdapter(Context context) {
            this.context = context;
        }

        @Override
        public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes,
                             CancellationSignal cancellationSignal,
                             LayoutResultCallback callback, Bundle extras) {

            pdfDocument = new PdfDocument();

            if (cancellationSignal.isCanceled()) {
                callback.onLayoutCancelled();
                return;
            }

            if (totalPages > 0) {
                PrintDocumentInfo info = new PrintDocumentInfo
                        .Builder("print_output.pdf")
                        .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                        .setPageCount(totalPages)
                        .build();
                callback.onLayoutFinished(info, true);
            } else {
                callback.onLayoutFailed("Page count is zero.");
            }
        }

        @Override
        public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor destination,
                            CancellationSignal cancellationSignal,
                            WriteResultCallback callback) {

            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, 2010, 1).create();
            PdfDocument.Page page = pdfDocument.startPage(pageInfo);

            if (cancellationSignal.isCanceled()) {
                callback.onWriteCancelled();
                pdfDocument.close();
                pdfDocument = null;
                return;
            }

            Canvas canvas = page.getCanvas();
            Paint paint = new Paint();
            Paint titlePaint = new Paint();
            paint.setColor(Color.BLACK);

            // Title
            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            titlePaint.setColor(Color.BLACK);
            titlePaint.setTextSize(50);
            canvas.drawText("Pembayaran", pageWidth / 2, 100, titlePaint);

            // Customer details
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setColor(Color.BLACK);
            paint.setTextSize(35f);
            canvas.drawText("Nama Pemesan: AHMAD HUSEIN", 35, 240, paint);
            canvas.drawText("Nomor Tlp: 0878654321", 35, 280, paint);

            paint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText("No. Pesanan: 232425", pageWidth - 35, 240, paint);

            dateFormat = new SimpleDateFormat("dd/MM/yy");
            canvas.drawText("Tanggal: " + dateFormat.format(new Date()), pageWidth - 35, 280, paint);

            dateFormat = new SimpleDateFormat("HH:mm:ss");
            canvas.drawText("Pukul: " + dateFormat.format(new Date()), pageWidth - 35, 320, paint);

            // Table headers
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2);
            canvas.drawRect(35, 360, pageWidth - 35, 420, paint);

            paint.setTextAlign(Paint.Align.LEFT);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawText("No.", 50, 400, paint);
            canvas.drawText("Tujuan Wisata", 200, 400, paint);
            canvas.drawText("Harga", 700, 400, paint);
            canvas.drawText("Jumlah", 900, 400, paint);
            canvas.drawText("Total Keseluruhan", 1050, 400, paint);

            canvas.drawLine(180, 360, 180, 420, paint);
            canvas.drawLine(680, 360, 680, 420, paint);
            canvas.drawLine(880, 360, 880, 420, paint);
            canvas.drawLine(1030, 360, 1030, 420, paint);

            // Draw table rows
            paint.setTextSize(25f);
            canvas.drawText("1.", 50, 460, paint);
            canvas.drawText(item.getTitle(), 200, 460, paint);
            canvas.drawText(item.getPrice(), 700, 460, paint);
            canvas.drawText("1", 900, 460, paint);
            canvas.drawText(item.getPrice(), 1050, 460, paint);

            // Draw the totals
            paint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText("Sub Total : Rp" + item.getPrice(), 35, 600, paint);

            String harga = item.getPrice().replace(".", "");
            int newHarga = Integer.parseInt(harga);
            double hargaPersen = newHarga * 0.1;

            double totalHarga = newHarga - hargaPersen;

            NumberFormat formatter = NumberFormat.getInstance(new Locale("id", "ID"));
            String hargaFormatted = formatter.format(hargaPersen);
            String hargaTotalFormatted = formatter.format(totalHarga);

            canvas.drawText("PPN (10%) : Rp" + hargaFormatted, 35, 640, paint);
            paint.setColor(Color.RED);
            canvas.drawText("Total Pembayaran : Rp" + hargaTotalFormatted, 35, 680, paint);

            pdfDocument.finishPage(page);

            try {
                pdfDocument.writeTo(new FileOutputStream(destination.getFileDescriptor()));
            } catch (IOException e) {
                callback.onWriteFailed(e.toString());
                return;
            } finally {
                pdfDocument.close();
                pdfDocument = null;
            }
            callback.onWriteFinished(pageRanges);
        }
    }
}
