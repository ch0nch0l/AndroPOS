package me.chonchol.andropos.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;
import me.chonchol.andropos.R;
import me.chonchol.andropos.adapter.StockListAdapter;
import me.chonchol.andropos.model.Stock;
import me.chonchol.andropos.rest.ApiClient;
import me.chonchol.andropos.rest.ApiService;
import me.chonchol.andropos.sharedpref.ClientSharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class StockActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 200;

    private RecyclerView stockListView;
    private StockListAdapter adapter;
    private List<Stock> stockList = new ArrayList<>();

    private ApiService apiService;

    private PdfPCell cell;
    private Image imgReportLogo;

    BaseColor headColor = WebColors.getRGBColor("#DEDEDE");
    BaseColor tableHeadColor = WebColors.getRGBColor("#F5ABAB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeView();

        FloatingActionButton btnGenerateStockReport = (FloatingActionButton) findViewById(R.id.btnGenerateStockReport);

        btnGenerateStockReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checkPermission()) {
                    try {
                        createPDF();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (checkPermission()) {
                        try {
                            requestPermissionAndContinue();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            createPDF();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    //Get All Stock Information
    private List<Stock> getStockList() {
        apiService = ApiClient.getClient(ClientSharedPreference.getClientUrl(getApplicationContext())).create(ApiService.class);
        Call<List<Stock>> getAllStockList = apiService.getAllStockList();
        getAllStockList.enqueue(new Callback<List<Stock>>() {
            @Override
            public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
                for (Stock stock : response.body()) {
                    stockList.add(stock);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<List<Stock>> call, Throwable t) {

            }
        });

        return stockList;
    }

    //View Initialization
    private void initializeView() {
        stockListView = findViewById(R.id.stockListView);

        adapter = new StockListAdapter(getApplicationContext(), getStockList());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        stockListView.setLayoutManager(layoutManager);
        stockListView.setItemAnimator(new DefaultItemAnimator());
        stockListView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        stockListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //Generate PDF Report for Stock
    public void createPDF() throws FileNotFoundException, DocumentException {

        //Create document file
        Document document = new Document();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a");
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmm");
            FileOutputStream outputStream = new FileOutputStream(
                    new File(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS) + "/AndroPOS/Reports"
                            , "STOCK_" + dateFormat.format(Calendar.getInstance().getTime()) + ".pdf"
                    ));

            PdfWriter writer = PdfWriter.getInstance(document, outputStream);

            //Open the document
            document.open();

            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor("Andro POS");
            document.addCreator("http://chonchol.me");

            //Create Header table
            PdfPTable header = new PdfPTable(3);
            header.setWidthPercentage(100);
            float[] fl = new float[]{20, 45, 35};
            header.setWidths(fl);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);

            //Set Logo in Header Cell
            Drawable logo = StockActivity.this.getResources().getDrawable(R.drawable.ic_point);
            Bitmap bitmap = ((BitmapDrawable) logo).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapLogo = stream.toByteArray();
            try {
                imgReportLogo = Image.getInstance(bitmapLogo);
                imgReportLogo.setAbsolutePosition(330f, 642f);

                cell.addElement(imgReportLogo);
                header.addCell(cell);

                cell = new PdfPCell();
                cell.setBorder(Rectangle.NO_BORDER);

                // Heading
                //BaseFont font = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);
                Font titleFont = new Font(Font.FontFamily.COURIER, 22.0f, Font.BOLD, BaseColor.BLACK);

                //Creating Chunk
                Chunk titleChunk = new Chunk("Andro POS", titleFont);
                //Paragraph
                Paragraph titleParagraph = new Paragraph(titleChunk);

                cell.addElement(titleParagraph);
                cell.addElement(new Paragraph("Stock Report"));
                cell.addElement(new Paragraph("Date: " + format.format(Calendar.getInstance().getTime())));
                header.addCell(cell);

                cell = new PdfPCell(new Paragraph(""));
                cell.setBorder(Rectangle.NO_BORDER);
                header.addCell(cell);

                PdfPTable pTable = new PdfPTable(1);
                pTable.setWidthPercentage(100);
                cell = new PdfPCell();
                cell.setColspan(1);
                cell.addElement(header);
                pTable.addCell(cell);

                PdfPTable table = new PdfPTable(5);
                //float[] columnWidth = new float[]{10, 30, 30, 20, 20, 30};
                float[] columnWidth = new float[]{10, 45, 30, 25, 25};
                table.setWidths(columnWidth);
                cell = new PdfPCell();
                cell.setBackgroundColor(headColor);
                cell.setColspan(6);
                cell.addElement(pTable);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(" "));
                cell.setColspan(6);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setColspan(6);
                cell.setBackgroundColor(tableHeadColor);
                cell = new PdfPCell(new Phrase("SL"));
                cell.setBackgroundColor(tableHeadColor);
                cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("PRODUCT NAME"));
                cell.setBackgroundColor(tableHeadColor);
                cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("CATEGORY"));
                cell.setBackgroundColor(tableHeadColor);
                cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("PRICE"));
                cell.setBackgroundColor(tableHeadColor);
                cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("IN STOCk"));
                cell.setBackgroundColor(tableHeadColor);
                cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase("Header 5"));
//                cell.setBackgroundColor(tableHeadColor);
//                table.addCell(cell);

                //table.setHeaderRows(3);
                cell = new PdfPCell();
                cell.setColspan(6);

//                for (int i = 1; i <= 10; i++) {
//                    table.addCell(String.valueOf(i));
//                    table.addCell("Header 1 row " + i);
//                    table.addCell("Header 2 row " + i);
//                    table.addCell("Header 3 row " + i);
//                    table.addCell("Header 4 row " + i);
//                    table.addCell("Header 5 row " + i);
//                }

                for (int i = 0; i<stockList.size(); i++){
                    table.addCell(String.valueOf(i+1));
                    table.addCell(stockList.get(i).getProduct().getProductName());
                    table.addCell(stockList.get(i).getProduct().getSubcategory().getCategory().getCatName());
                    table.addCell(stockList.get(i).getProduct().getPrice().toString());
                    table.addCell(stockList.get(i).getQuantity().toString());

                }

                PdfPTable ftable = new PdfPTable(6);
                ftable.setWidthPercentage(100);
                float[] columnWidthaa = new float[]{30, 10, 30, 10, 30, 10};
                ftable.setWidths(columnWidthaa);
                cell = new PdfPCell();
                cell.setColspan(6);
                cell.setBackgroundColor(tableHeadColor);
                cell = new PdfPCell(new Phrase("Total Number"));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setBackgroundColor(tableHeadColor);
                ftable.addCell(cell);

                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setBackgroundColor(tableHeadColor);
                ftable.addCell(cell);

                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setBackgroundColor(tableHeadColor);
                ftable.addCell(cell);

                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setBackgroundColor(tableHeadColor);
                ftable.addCell(cell);

                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setBackgroundColor(tableHeadColor);
                ftable.addCell(cell);

                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setBackgroundColor(tableHeadColor);
                ftable.addCell(cell);

                cell = new PdfPCell(new Paragraph("Footer"));
                cell.setColspan(6);
                ftable.addCell(cell);

                cell = new PdfPCell();
                cell.setColspan(6);
                cell.addElement(ftable);
                table.addCell(cell);

                document.add(table);

                Toasty.success(getApplicationContext(), "Stock Report STOCK_" + dateFormat.format(Calendar.getInstance().getTime()) + ".pdf generated at DOWNLOADS folder", Toast.LENGTH_SHORT, true).show();
            } catch (DocumentException de) {
                Log.e("PDFCreator", "DocumentException:" + de);
            } catch (IOException e) {
                Log.e("PDFCreator", "ioException:" + e);
            } finally {
                document.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Check READ/WRITE PERMISSION
    private boolean checkPermission() {

        return ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ;
    }

    //Request for Permission
    private void requestPermissionAndContinue() throws FileNotFoundException, DocumentException {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle(getString(R.string.permission));
                alertBuilder.setMessage(R.string.storage_permission);
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(StockActivity.this, new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.e("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(StockActivity.this, new String[]{WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        } else {
            createPDF();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (permissions.length > 0 && grantResults.length > 0) {

                boolean flag = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        flag = false;
                    }
                }
                if (flag) {
                    try {
                        createPDF();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    finish();
                }

            } else {
                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
