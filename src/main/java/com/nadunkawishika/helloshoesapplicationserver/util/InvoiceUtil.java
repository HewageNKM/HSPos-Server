package com.nadunkawishika.helloshoesapplicationserver.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.nadunkawishika.helloshoesapplicationserver.dto.InvoiceDTO;
import com.nadunkawishika.helloshoesapplicationserver.dto.SaleDetailDTO;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class InvoiceUtil {
    public byte[] getInvoice(InvoiceDTO dto) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        List<SaleDetailDTO> saleDetailsList = dto.getSaleDetailsList();
        double total = saleDetailsList.stream().mapToDouble(SaleDetailDTO::getTotal).sum();

        // Add header and company details
        document.add(new Paragraph("Invoice \n").setFontSize(20).setBold().setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Hello Shoes PVT LTD").setFontSize(14).setBold());
        document.add(new Paragraph("001/A,Colombo 7,").setBold());
        document.add(new Paragraph("0777700011 \n\n").setBold());
        if (dto.getCustomerId() != null) document.add(new Paragraph("Customer ID: " + dto.getCustomerId()));
        document.add(new Paragraph("Date/Time: " + LocalDateTime.now()));
        document.add(new Paragraph("Total Amount: Rs " + String.format("%.2f", total)));
        document.add(new Paragraph("Payment: " + dto.getPaymentDescription() + "\n\n"));

        // Create and populate the table
        Table table = new Table(5);
        table.addHeaderCell(new Cell().add(new Paragraph("Item ID").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Description").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Unit Price").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Quantity").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Total").setBold()));

        for (SaleDetailDTO item : saleDetailsList) {
            table.addCell(item.getItemId());
            table.addCell(item.getDescription());
            table.addCell(item.getPrice().toString());
            table.addCell(item.getQuantity().toString());
            table.addCell(item.getTotal().toString());
        }
        document.add(table);
        document.add(new Paragraph("\n"));

        // Add additional information
        document.add(new Paragraph("Cashier Name: " + dto.getCashierName()));

        if (dto.getCustomerId() != null) {
            document.add(new Paragraph("\n-------------Loyalty------------"));
            document.add(new Paragraph("Added Points: " + dto.getAddedPoints()));
            document.add(new Paragraph("Total Loyalty Points: " + dto.getTotalPoints() + "\n\n"));
        }

        document.add(new Paragraph("For Return:\nUnworn shoes can be returned within 3 days with the original receipt.\n\n").setBold());

        document.add(new Paragraph("Thank You!").setFontSize(16).setBold().setTextAlignment(TextAlignment.CENTER));

        document.close();

        return out.toByteArray();
    }
}
