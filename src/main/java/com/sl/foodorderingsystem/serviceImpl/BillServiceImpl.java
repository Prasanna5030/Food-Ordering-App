package com.sl.foodorderingsystem.serviceImpl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sl.foodorderingsystem.JWT.JwtAuthFilter;
import com.sl.foodorderingsystem.Repository.BillRespository;
import com.sl.foodorderingsystem.constants.AppConstants;
import com.sl.foodorderingsystem.entity.Bill;
import com.sl.foodorderingsystem.service.BIllService;
import com.sl.foodorderingsystem.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
public class BillServiceImpl implements BIllService {



    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private BillRespository billRespository;
    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        try{
            String fileName;
            if(validateRequestMap(requestMap)){
                if(requestMap.containsKey("isGenerate")&& !(Boolean)requestMap.get("isGenerate")){
                    fileName= (String) requestMap.get("uuid");
                }else{
                    fileName= AppUtils.getUUID();
                    requestMap.put("uuid",fileName);
                    insertBill(requestMap);
                }
                String data= "Name: "+ requestMap.get("name")+"\n"+"Contact Number: "+requestMap.get("contactNumber")+
                        "\n"+"Email: "+ requestMap.get("email")+"\n"+"Payment Method: "+ requestMap.get("paymentMethod");

                Document document= new Document();
                PdfWriter.getInstance(document, new FileOutputStream(AppConstants.STORE_LOCATION+"\\"+fileName+".pdf"));
                document.open();
                setRectangularPdf(document);

                Paragraph para= new Paragraph("Cafe Management System", getFont("Header"));

                para.setAlignment(Element.ALIGN_CENTER);
                document.add(para);

                Paragraph paragraph = new Paragraph(data+"\n \n",getFont("Data"));
                document.add(paragraph);

                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                addTableHeader(table);

                JSONArray jsonArray= AppUtils.getJsonArrayFromString((String)requestMap.get("productDetails"));
                for( int i=0; i< jsonArray.length();i++){
                    addRows(table, AppUtils.getMapFromJson(jsonArray.getString(i)));
                }
                document.add(table);

                Paragraph footer = new Paragraph("Total Amount :" +requestMap.get("totalAmount")+"\n"
                +"Thank you for visiting. Please visit again!! ", getFont("Data"));

                document.add(footer);
                document.close();
                return new ResponseEntity<>("{\"uuid\":\""+fileName+"\"}" , HttpStatus.OK);

            }
            return new ResponseEntity<>("Requred data not found", HttpStatus.NOT_FOUND);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>("something went wrong" , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Bill>> getBills() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<Bill> billList= new ArrayList<>();
        if( jwtAuthFilter.isAdmin(authentication)){
            billList= billRespository.getAllBills();
        }
        else{
            billList= billRespository.getBillByUserName(jwtAuthFilter.getCurrentUserDetails().getUsername());
        }
    return new ResponseEntity<>(billList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        log.info(" inside getpdf ()");
        try{
            byte[] byteArray= new byte[0];
            if(!requestMap.containsKey("uuid") && validateRequestMap(requestMap))
                return new ResponseEntity<>(byteArray , HttpStatus.BAD_REQUEST);
            String filePath= AppConstants.STORE_LOCATION+"\\"+(String) requestMap.get("uuid")+".pdf";
            if(AppUtils.isFileExists(filePath)){
                byteArray=getByteArray(filePath);

                return new ResponseEntity<>(byteArray , HttpStatus.OK);
            }
            else{
                requestMap.put("isGenerate",false);
                 generateReport(requestMap);
                 byteArray  = getByteArray(filePath);
                 return new ResponseEntity<>(byteArray , HttpStatus.OK);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteBill(Integer id) {
        try{
            Optional<Bill> bill= billRespository.findById(id);
            if(!bill.isEmpty()){
                billRespository.deleteById(id);
                return new ResponseEntity<>("Bill deleted",HttpStatus.OK);
            }else
                return new ResponseEntity<>("Bill not found",HttpStatus.NOT_FOUND);
        }catch(Exception e){
             e.printStackTrace();
        }
        return new ResponseEntity<>(AppConstants.SOMETHING_WENT_WRONG ,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private byte[] getByteArray(String filePath) throws IOException {
        File intialFile = new File(filePath);
        InputStream targetStream = new FileInputStream(intialFile);
        byte[] byteArray= IOUtils.toByteArray(targetStream);
        targetStream.close();
        return byteArray;

    }

    private void addRows(PdfPTable table, Map<String, Object> data) {
        table.addCell((String) data.get("name"));
        table.addCell((String) data.get("category"));
        table.addCell((String) data.get("quantity"));
        table.addCell(Double.toString((Double) data.get("price")));
        table.addCell(Double.toString((Double) data.get("total")));
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Name", "Category" ,"Quantity", "Price" , "Sub Total")
                .forEach(columnTitle->{
                    PdfPCell header= new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    header.setBackgroundColor(BaseColor.YELLOW);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });

    }

    private Font getFont(String type) {
        switch(type){
            case "Header":
                Font headerFont= FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE,18 ,BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            case "Data":
                Font dataFont= FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;

            default:
                return new Font();
        }
    }

    private void setRectangularPdf(Document document) throws DocumentException {
        log.info("inside setRectangularPdf");
        Rectangle rect= new Rectangle(577,825,18,15);
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);
        rect.setBorderColor(BaseColor.BLUE);
        rect.setBorderWidth(1);
        document.add(rect);


    }

    private void insertBill(Map<String, Object> requestMap) {
        try{
            Bill bill = new Bill();
            bill.setUuid((String) requestMap.get("uuid"));
            bill.setName((String) requestMap.get("name"));
            bill.setEmail((String) requestMap.get("email"));
            bill.setContactNumber((String) requestMap.get("contactNumber"));
            bill.setPaymentMethod((String) requestMap.get("paymentMethod"));
            bill.setTotal(Integer.parseInt((String)requestMap.get("totalAmount")));
            bill.setProductDetail((String) requestMap.get("productDetails"));
            bill.setCreatedBy(jwtAuthFilter.getCurrentUserDetails().getUsername());
            billRespository.save(bill);

        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    private boolean validateRequestMap(Map<String, Object> requestMap) {
        return requestMap.containsKey("name")&&
                requestMap.containsKey("contactNumber")&&
                requestMap.containsKey("email")&&
                requestMap.containsKey("paymentMethod")&&
                requestMap.containsKey("productDetails")&&
                requestMap.containsKey("totalAmount");
    }
}
