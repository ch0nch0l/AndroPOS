package me.chonchol.andropos.report;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;
import me.chonchol.andropos.R;
import me.chonchol.andropos.layout.StockActivity;
import me.chonchol.andropos.model.Sale;
import me.chonchol.andropos.model.Stock;
import me.chonchol.andropos.model.report.ProfitReport;
import me.chonchol.andropos.model.report.SaleReport;

/**
 * Created by mehedi.chonchol on 28-Oct-18.
 */

public class ReportGenerator {

    private PdfPCell cell;
    private Image imgReportLogo;

    BaseColor headColor = WebColors.getRGBColor("#DEDEDE");
    BaseColor tableHeadColor = WebColors.getRGBColor("#F5ABAB");

    public void generateStockReport(Context context, List<Stock> stockList){
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
            Drawable logo = context.getResources().getDrawable(R.drawable.ic_point);
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

                Toasty.success(context, "Stock Report STOCK_" + dateFormat.format(Calendar.getInstance().getTime()) + ".pdf generated at DOWNLOADS folder", Toast.LENGTH_SHORT, true).show();
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

    public void generateSaleReport(Context context, List<SaleReport> saleReportList){
        Document document = new Document();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a");
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmm");
            FileOutputStream outputStream = new FileOutputStream(
                    new File(Environment.getExternalStorageDirectory() + "/AndroPOS/Reports/Sale"
                            , "SALE_" + dateFormat.format(Calendar.getInstance().getTime()) + ".pdf"
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
            Drawable logo = context.getResources().getDrawable(R.drawable.ic_point);
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
                cell.addElement(new Paragraph("Sale Report"));
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

                PdfPTable table = new PdfPTable(6);
                float[] columnWidth = new float[]{10, 30, 20, 30, 20, 30};
                //float[] columnWidth = new float[]{10, 45, 30, 25, 25};
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
                cell = new PdfPCell(new Phrase("DATE"));
                cell.setBackgroundColor(tableHeadColor);
                cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("CUSTOMER NAME"));
                cell.setBackgroundColor(tableHeadColor);
                cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("PHONE"));
                cell.setBackgroundColor(tableHeadColor);
                cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("PRODUCT NAME"));
                cell.setBackgroundColor(tableHeadColor);
                cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("QUANTITY"));
                cell.setBackgroundColor(tableHeadColor);
                cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("TOTAL"));
                cell.setBackgroundColor(tableHeadColor);
                cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                //table.setHeaderRows(3);
                cell = new PdfPCell();
                cell.setColspan(6);

                for (int i = 0; i<saleReportList.size(); i++){
                    table.addCell(saleReportList.get(i).getDate().toString());
                    table.addCell(saleReportList.get(i).getCustomerName());
                    table.addCell(saleReportList.get(i).getPhoneNo());
                    table.addCell(saleReportList.get(i).getProductName());
                    table.addCell(saleReportList.get(i).getQuantity().toString());
                    table.addCell(saleReportList.get(i).getTotalAmount().toString());
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

                Toasty.success(context, "Sale Report SALE_" + dateFormat.format(Calendar.getInstance().getTime()) + ".pdf generated at DOWNLOADS folder", Toast.LENGTH_SHORT, true).show();
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

    public void generateProfitReport(Context context, List<ProfitReport> profitReportList){
        Document document = new Document();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a");
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmm");
            FileOutputStream outputStream = new FileOutputStream(
                    new File(Environment.getExternalStorageDirectory() + "/AndroPOS/Reports/Profit"
                            , "PROFIT_" + dateFormat.format(Calendar.getInstance().getTime()) + ".pdf"
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
            Drawable logo = context.getResources().getDrawable(R.drawable.ic_point);
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
                cell.addElement(new Paragraph("Profit Report"));
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
                //float[] columnWidth = new float[]{10, 30, 20, 30, 20, 30};
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

                cell = new PdfPCell(new Phrase("DATE"));
                cell.setBackgroundColor(tableHeadColor);
                cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("TOTAL SALE"));
                cell.setBackgroundColor(tableHeadColor);
                cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("TOTAL COST"));
                cell.setBackgroundColor(tableHeadColor);
                cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("PROFIT"));
                cell.setBackgroundColor(tableHeadColor);
                cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(cell);

                //table.setHeaderRows(3);
                cell = new PdfPCell();
                cell.setColspan(6);

                for (int i = 0; i<profitReportList.size(); i++){
                    table.addCell(String.valueOf(i+1));
                    table.addCell(profitReportList.get(i).getDate().toString());
                    table.addCell(profitReportList.get(i).getTotalSaleAmount().toString());
                    table.addCell(profitReportList.get(i).getTotalCostAmount().toString());
                    table.addCell(String.valueOf(profitReportList.get(i).getTotalSaleAmount()
                            - profitReportList.get(i).getTotalCostAmount()));

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

                Toasty.success(context, "Profit Report PROFIT_" + dateFormat.format(Calendar.getInstance().getTime()) + ".pdf generated at DOWNLOADS folder", Toast.LENGTH_SHORT, true).show();
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
}
