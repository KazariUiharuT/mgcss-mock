package com.acme.bnb.services;

import com.acme.bnb.exceptions.ConflictException;
import com.acme.bnb.model.Invoice;
import com.acme.bnb.model.Request;
import com.acme.bnb.model.datatype.CreditCard;
import com.acme.bnb.repositories.InvoiceRepository;
import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.transaction.Transactional;
import lombok.Data;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

@Data
@Service
@Transactional
public class InvoiceService {

    private final InvoiceRepository invoiceRepo;
    private final RequestService requestService;
    private final SysConfigService sysConfigService;
    private final ConstantsService constants;
    private final B0vEBlobService blobService;

    public Invoice getForRequest(Long requestId) {
        Invoice invoice = requestService.findById(requestId).getInvoice();
        if (invoice == null) {
            throw new ConflictException("This request does not have an invoice");
        }
        return invoice;
    }

    public Long create(Long requestId) {
        Request request = requestService.findById(requestId);
        Invoice invoice = new Invoice();
        invoice.setCreditCard(request.getTenantCreditCard());
        invoice.setAmmount(request.getTenantTotal());
        invoice.setRequest(request);
        invoice.setVat(sysConfigService.getConfig("bnbVat"));

        //TODO: Crear PDF con pdfbox
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            DecimalFormat decimal = new DecimalFormat("#.##");
            CreditCard creditCard = invoice.getRequest().getTenantCreditCard();
            creditCard.hide();
            
            List<String> lines = new ArrayList<>();
            lines.add("Property: "+invoice.getRequest().getProperty().getName());
            lines.add("Checkin: "+format.format(invoice.getRequest().getCheckIn()));
            lines.add("Checkout: "+format.format(invoice.getRequest().getCheckOut()));
            lines.add("Number of days: "+invoice.getRequest().getNDays());
            lines.add("Rate: "+decimal.format(invoice.getRequest().getRate())+" €/day");
            lines.add("Fee: "+decimal.format(invoice.getRequest().getTenantFee())+" €");
            lines.add("Total cost: "+decimal.format(invoice.getAmmount())+" €");
            lines.add("Creditcard: "+creditCard.getNumber());
            
            PDDocument doc = PDDocument.load(this.constants.getInvoiceTemplate().getInputStream());
            PDPage firstPage = doc.getPage(0);
            PDFont pdfFont = PDType1Font.HELVETICA;
            PDPageContentStream contentStream = new PDPageContentStream(doc, firstPage, PDPageContentStream.AppendMode.APPEND, true, true);
            contentStream.setFont(pdfFont, 16);
            
            for (int i = 0; i < lines.size(); i++) {
                contentStream.beginText();
                contentStream.newLineAtOffset(75, 650-(i*30));
                contentStream.showText(lines.get(i));
                contentStream.endText();
            }
            
            contentStream.close();
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            doc.save(baos);
            String pdfBase64 = "data:application/pdf;base64," +Base64.getEncoder().encodeToString(baos.toByteArray());
            invoice.setPdf(blobService.storeDoc(pdfBase64));
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
            invoice.setPdf(null);
        }

        invoiceRepo.save(invoice);
        return invoice.getId();
    }

}
