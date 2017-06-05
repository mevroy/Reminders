/**
 * 
 */
package com.yourpackagename.yourwebproject.service.impl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.VerticalAlignment;
import be.quodlibet.boxable.line.LineStyle;
import be.quodlibet.boxable.utils.ImageUtils;

import com.yourpackagename.commons.util.CommonUtils;
import com.yourpackagename.framework.data.BaseJpaServiceImpl;
import com.yourpackagename.yourwebproject.common.Props;
import com.yourpackagename.yourwebproject.model.entity.GroupDependents;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPass;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.repository.GroupMembersRepository;
import com.yourpackagename.yourwebproject.service.GroupMembersService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class GroupMembersServiceImpl extends BaseJpaServiceImpl<GroupMember, String> implements GroupMembersService {

	private @Autowired GroupMembersRepository groupMembersRespoistory;
	protected @Autowired Props props;
	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = groupMembersRespoistory;
		this.entityClass = GroupMember.class;
		this.baseJpaRepository.setupEntityClass(GroupMember.class);
		
	}

	public List<GroupMember> findByGroupCodeAndMemberCategoryCode(
			String groupCode, String membercategoryCode) {
		return groupMembersRespoistory.findByGroupCodeAndMemberCategoryCode(groupCode, membercategoryCode);
	}

	public List<GroupMember> findByGroupCode(String groupCode) {
		return groupMembersRespoistory.findByGroupCode(groupCode);
	}

	public List<GroupMember> executeGenericQuery(String hibernateQuery) {
		return groupMembersRespoistory.executeGenericQuery(hibernateQuery);
	}
	
	public List<List<GroupMember>> executeGenericQueryAsList(String hibernateQuery)
	{
		List<List<GroupMember>> list = new ArrayList<List<GroupMember>>();
		list.add(groupMembersRespoistory.executeGenericQuery(hibernateQuery));
		return list;
	}


	public List<GroupMember> findByGroupCodeAndMemberCategoryCodeNotExitingInGroupEventInvite(
			String groupCode, String membercategoryCode, String groupEventCode) {
		return groupMembersRespoistory.findByGroupCodeAndMemberCategoryCodeNotExitingInGroupEventInvite(groupCode, membercategoryCode, groupEventCode);
	}

	public List<GroupMember> findByGroupCodeNotExitingInGroupEventInvite(
			String groupCode, String groupEventCode) {
		return groupMembersRespoistory.findByGroupCodeNotExitingInGroupEventInvite(groupCode, groupEventCode);
	}

	public GroupMember findbyMembershipIdentifier(String membershipIdentifier) {

		return groupMembersRespoistory.findbyMembershipIdentifier(membershipIdentifier);
	}

	public List<GroupMember> findByAssociatedEmailForGroupMember(
			String emailAddress, String groupCode) {
		return groupMembersRespoistory.findByAssociatedEmailForGroupMember(emailAddress, groupCode);
	}

	public  ByteArrayOutputStream prefillPDF(GroupMember groupMember) throws IOException {
	    ByteArrayOutputStream output = new ByteArrayOutputStream(); 
	    if(groupMember == null)
	    	return output;
	    // Creating Document
        URL url = new URL(props.membershipFilePath);
        File f = new File(System.getProperty("java.io.tmpdir", "/tmp"), "batch/files");
		File des = new File(f,"Membership_Form_2016-17.pdf");
		if(!des.exists())
        FileUtils.copyURLToFile(url, des);
        // load the document
        PDDocument pdfDocument = PDDocument.load(des);
        PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();
        
        // as there might not be an AcroForm entry a null check is necessary
        if (acroForm != null)
        {
            // Retrieve an individual field and set its value.
            CommonUtils.setAcroFormField(acroForm, "First Name", groupMember.getFirstName());
            CommonUtils.setAcroFormField(acroForm, "Last Name", groupMember.getLastName());
            if(groupMember.getBirthday()!=null){
            Calendar bday = Calendar.getInstance();
            bday.setTime(groupMember.getBirthday());
            CommonUtils.setAcroFormField(acroForm, "Date", String.valueOf(bday.get(Calendar.DAY_OF_MONTH)));
            CommonUtils.setAcroFormField(acroForm, "Month", String.valueOf(bday.get(Calendar.MONTH)+1));
            CommonUtils.setAcroFormField(acroForm, "year", String.valueOf(bday.get(Calendar.YEAR)));
            }
            CommonUtils.setAcroFormField(acroForm, "untitled4", groupMember.getFirstName() + " "+groupMember.getLastName());
            CommonUtils.setAcroFormField(acroForm, "untitled3", groupMember.getMobilephone());
            CommonUtils.setAcroFormField(acroForm, "untitled5", groupMember.getPrimaryEmail());

            CommonUtils.setAcroFormField(acroForm, "Full Name", groupMember.getFirstName() + " "+groupMember.getLastName());
            CommonUtils.setAcroFormField(acroForm, "Mobile Phone", groupMember.getMobilephone());
            CommonUtils.setAcroFormField(acroForm, "Primary Email", groupMember.getPrimaryEmail());
            
            if(!CollectionUtils.isEmpty(groupMember.getGroupDependents())){
            	int i = 0;
            	for(GroupDependents gd: groupMember.getGroupDependents()){
            		switch (i) {
					case 0:
			            CommonUtils.setAcroFormField(acroForm, "Spouse First Name", gd.getFirstName());
			            CommonUtils.setAcroFormField(acroForm, "Spouse Last Name", gd.getLastName());
			            i++;
			            break;
					
					
					case 1:
			            CommonUtils.setAcroFormField(acroForm, "Child 1 First Name", gd.getFirstName());
			            CommonUtils.setAcroFormField(acroForm, "Child 1 Last Name", gd.getLastName());
			            i++;
					break;
					
					case 2:
			            CommonUtils.setAcroFormField(acroForm, "Child 2 First Name", gd.getFirstName());
			            CommonUtils.setAcroFormField(acroForm, "Child 2 Last Name", gd.getLastName());
			            i++;
					break;
					
					case 3:
			            CommonUtils.setAcroFormField(acroForm, "Child 3 First Name", gd.getFirstName());
			            CommonUtils.setAcroFormField(acroForm, "Child 3 Last Name", gd.getLastName());
	
					i++;
					break;
					
					default:
						break;
					
					}

            	}
            }

        }

        
        // Save and close the filled out form.
        pdfDocument.save(output);
        pdfDocument.close();
	    
	    // Creating Pages
/*	    for(int i=0; i<2; i++) {

	        page = new PDPage();

	        // Adding page to document
	        document.addPage(page); 

	        // Adding FONT to document
	        font = PDType1Font.HELVETICA;           

	        // Retrieve Image to be added to the PDF
	        inputFront = new FileInputStream(new File("D:/Media/imageFront.jpg"));  
	        inputBack = new FileInputStream(new File("D:/Media/imageBack.jpg"));

	        BufferedImage buffFront = ImageIO.read(inputFront);
	        BufferedImage resizedFront = Scalr.resize(buffFront, 460);

	        BufferedImage buffBack = ImageIO.read(inputBack);
	        BufferedImage resizedBack = Scalr.resize(buffBack, 460); 

	        front = new PDJpeg(document, resizedFront);
	        back = new PDJpeg(document, resizedBack);

	        // Next we start a new content stream which will "hold" the to be created content.
	        contentStream = new PDPageContentStream(document, page);                

	        // Let's define the content stream
	        contentStream.beginText();
	        contentStream.setFont(font, 8);
	        contentStream.moveTextPositionByAmount(10, 770);
	        contentStream.drawString("Amount: $1.00");
	        contentStream.endText();

	        contentStream.beginText();
	        contentStream.setFont(font, 8);
	        contentStream.moveTextPositionByAmount(200, 770);
	        contentStream.drawString("Sequence Number: 123456789");
	        contentStream.endText();

	        contentStream.beginText();
	        contentStream.setFont(font, 8);
	        contentStream.moveTextPositionByAmount(10, 760);
	        contentStream.drawString("Account: 123456789");
	        contentStream.endText();

	        contentStream.beginText();
	        contentStream.setFont(font, 8);
	        contentStream.moveTextPositionByAmount(200, 760);
	        contentStream.drawString("Captura Date: 04/25/2011");
	        contentStream.endText();

	        contentStream.beginText();
	        contentStream.setFont(font, 8);
	        contentStream.moveTextPositionByAmount(10, 750);
	        contentStream.drawString("Bank Number: 123456789");
	        contentStream.endText();

	        contentStream.beginText();
	        contentStream.setFont(font, 8);
	        contentStream.moveTextPositionByAmount(200, 750);
	        contentStream.drawString("Check Number: 123456789");
	        contentStream.endText();            

	        // Let's close the content stream       
	        contentStream.close();

	    }*/

	    // Finally Let's save the PDF
/*	    document.save(output);
	    document.close();*/

	    return output;
	}
	
	
	public  ByteArrayOutputStream prefillDefinedPDF(GroupMember groupMember, String httpPath) throws IOException {
	    ByteArrayOutputStream output = new ByteArrayOutputStream(); 
	    if(groupMember == null)
	    	return output;
	    String fileName = httpPath.substring(httpPath.lastIndexOf("/")+1);
	    // Creating Document
        URL url = new URL(
        		httpPath);
        File f = new File(System.getProperty("java.io.tmpdir", "/tmp"), "batch/files");
		File des = new File(f,fileName);
		if(!des.exists())
        FileUtils.copyURLToFile(url, des);
        // load the document
        PDDocument pdfDocument = PDDocument.load(des);
        PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();
        
        // as there might not be an AcroForm entry a null check is necessary
        if (acroForm != null)
        {
            // Retrieve an individual field and set its value.
            CommonUtils.setAcroFormField(acroForm, "First Name", groupMember.getFirstName());
            CommonUtils.setAcroFormField(acroForm, "Last Name", groupMember.getLastName());
            if(groupMember.getBirthday()!=null){
            Calendar bday = Calendar.getInstance();
            bday.setTime(groupMember.getBirthday());
            CommonUtils.setAcroFormField(acroForm, "Date", String.valueOf(bday.get(Calendar.DAY_OF_MONTH)));
            CommonUtils.setAcroFormField(acroForm, "Month", String.valueOf(bday.get(Calendar.MONTH)+1));
            CommonUtils.setAcroFormField(acroForm, "year", String.valueOf(bday.get(Calendar.YEAR)));
            }
            CommonUtils.setAcroFormField(acroForm, "untitled4", groupMember.getFirstName() + " "+groupMember.getLastName());
            CommonUtils.setAcroFormField(acroForm, "untitled3", groupMember.getMobilephone());
            CommonUtils.setAcroFormField(acroForm, "untitled5", groupMember.getPrimaryEmail());
            
            CommonUtils.setAcroFormField(acroForm, "Full Name", groupMember.getFirstName() + " "+groupMember.getLastName());
            CommonUtils.setAcroFormField(acroForm, "Mobile Phone", groupMember.getMobilephone());
            CommonUtils.setAcroFormField(acroForm, "Primary Email", groupMember.getPrimaryEmail());

            if(!CollectionUtils.isEmpty(groupMember.getGroupDependents())){
            	int i = 0;
            	for(GroupDependents gd: groupMember.getGroupDependents()){
            		switch (i) {
					case 0:
			            CommonUtils.setAcroFormField(acroForm, "Spouse First Name", gd.getFirstName());
			            CommonUtils.setAcroFormField(acroForm, "Spouse Last Name", gd.getLastName());
			            CommonUtils.setAcroFormField(acroForm, "Spouse Email", gd.getEmail());
			            i++;
			            break;
					
					
					case 1:
			            CommonUtils.setAcroFormField(acroForm, "Child 1 First Name", gd.getFirstName());
			            CommonUtils.setAcroFormField(acroForm, "Child 1 Last Name", gd.getLastName());
			            i++;
					break;
					
					case 2:
			            CommonUtils.setAcroFormField(acroForm, "Child 2 First Name", gd.getFirstName());
			            CommonUtils.setAcroFormField(acroForm, "Child 2 Last Name", gd.getLastName());
			            i++;
					break;
					
					case 3:
			            CommonUtils.setAcroFormField(acroForm, "Child 3 First Name", gd.getFirstName());
			            CommonUtils.setAcroFormField(acroForm, "Child 3 Last Name", gd.getLastName());
	
					i++;
					break;
					
					default:
						break;
					
					}

            	}
            }

        }
        // Save and close the filled out form.
        pdfDocument.save(output);
        pdfDocument.close();
	    return output;
	}
	
	
	
	
	public  ByteArrayOutputStream prefillPDFTest(GroupMember groupMember) throws IOException {
	    ByteArrayOutputStream output = new ByteArrayOutputStream(); 
	    if(groupMember == null)
	    	return output;
	    // Creating Document
        URL url = new URL(
				"http://reminders-mrdapp.rhcloud.com/app/00622239-f2b3-48e7-a55a-d7c1f65d05ad/batch/files/DD/AdultDDTicket.jpg");
        File f = new File(System.getProperty("java.io.tmpdir", "/tmp"), "batch/files");
		File des = new File(f,"AdultDDTicket.jpg");
		if(!des.exists())
        FileUtils.copyURLToFile(url, des);
		
		URL bc = new URL("https://chart.googleapis.com/chart?chs=116x116&cht=qr&chl=MON16-0001");
		File bcdes = new File(f,"MON16-0001.jpg");
		FileUtils.copyURLToFile(bc, bcdes);
		
		
		URL logo = new URL("http://reminders-mrdapp.rhcloud.com/app/00622239-f2b3-48e7-a55a-d7c1f65d05ad/batch/files/MKC_Logo_Xtra_Small.jpg");
		File logoF = new File(f,"MKC_Logo_Xtra_Small.jpg");
		FileUtils.copyURLToFile(logo, logoF);
        // load the document
        PDDocument document = new PDDocument();

        // Save and close the filled out form.
        //pdfDocument.save(output);
        //pdfDocument.close();
	    
	    // Creating Pages
	    for(int a=0; a<2; a++) {

	    	PDPage page = new PDPage();

	        // Adding page to document
	        document.addPage(page); 

	        // Adding FONT to document
	        PDType1Font font = PDType1Font.HELVETICA;           

	        PDPageContentStream contentStream = new PDPageContentStream(document, page);  
	        /*  float margin = 10;
			float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);

			//Initialize table
			float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
			boolean drawContent = true;
			float yStart = yStartNewPage;
			float bottomMargin = 70;
			
			
			BaseTable table = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, margin, document, page, true,
					drawContent);

	        
	        Row<PDPage> row = table.createRow(300f);
	        
	        Cell<PDPage> cell = row.createImageCell((100 / 2f), ImageUtils.readImage(des));
	         cell = row.createCell(100/9f, "Boxable Input");
*/	        
			
			
			
			
			
			
	        
	        
	        
			float margin = 30;

			List<String[]> facts = getFacts();

			//Initialize Document
			
			
			float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);

			//Initialize table
			float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
			System.out.println("yStartNewPage:"+yStartNewPage+"-- tableWidth:"+tableWidth);
			boolean drawContent = true;
			float yStart = yStartNewPage;
			//float bottomMargin = 70;
			float bottomMargin = 0;
			BaseTable table = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, margin, document, page, true,
					drawContent);

			//Create Header row
			Row<PDPage> headerRow = table.createRow(15f);
			Cell<PDPage> cell = headerRow.createCell(100, "Note : Please carry a print of your tickets to the venue!");
			cell.setFont(PDType1Font.HELVETICA_BOLD);
			cell.setFillColor(Color.BLACK);
			cell.setTextColor(Color.WHITE);
			cell.setFontSize(12);

			table.addHeaderRow(headerRow);

			//Create 2 column row
		//	Row<PDPage> row = table.createRow(15f);
		//	cell = row.createCell(30, "Name");
		//	cell.setFont(PDType1Font.HELVETICA);

			//cell = row.createCell(70, "Mevan D Souza");
			//cell.setFont(PDType1Font.HELVETICA_OBLIQUE);

			for (int ab = 0; ab<2;  ab++ ){
			//Create Fact header row
			/*Row<PDPage> factHeaderrow = table.createRow(15f);

			cell = factHeaderrow.createCell((100 / 4f) * 3, "Name : Mevan D Souza");
			cell.setFont(PDType1Font.HELVETICA);
			cell.setFontSize(12);
			cell.setFillColor(Color.LIGHT_GRAY);

			cell = factHeaderrow.createCell((100 / 4f), "MON16-0001");
			cell.setFillColor(Color.LIGHT_GRAY);
			cell.setFont(PDType1Font.HELVETICA_OBLIQUE);
			cell.setFontSize(14);*/

			//Add multiple rows with random facts about Belgium
			

			Row<PDPage> 	row = table.createRow(5f);
				cell = row.createImageCell((100 / 4f) * 3, ImageUtils.readImage(des), HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
				cell.setTopPadding(5);
				cell.setBottomPadding(5);
				//cell = row.createCell((100 / 4f) * 3, "Hello");

				float buffer = 0;
				
					System.out.println("cell height:"+cell.getHeight());
					buffer = ab * cell.getHeight();
				
				BaseTable table2 = new BaseTable(yStart-30-buffer, yStartNewPage-30-buffer, bottomMargin, 140, 453, document, page, false,
						drawContent);
				Row<PDPage> inner = table2.createRow(1f);

				Cell<PDPage> cellar = inner.createCell(100, "Name : Mevan ui D Souza");
				cellar.setFont(PDType1Font.HELVETICA);
				cellar.setFontSize(10);
				cellar.setTopPadding(0);
				
				inner = table2.createRow(1f);
				cellar = inner.createCell(100, "Entry for : 1");
				cellar.setFont(PDType1Font.HELVETICA);
				cellar.setFontSize(10);
				cellar.setTopPadding(0);
//				cellar.setFillColor(Color.LIGHT_GRAY);

				inner = table2.createRow(1f);
				cellar = inner.createCell(100, "Pass Price : $50.00");
				cellar.setFont(PDType1Font.HELVETICA);
				cellar.setFontSize(10);
				cellar.setTopPadding(0);
				
				Row<PDPage> inner2 = table2.createRow(1f);
				cellar = inner2.createCell(100, "MON16-0001");
				//cellar.setFillColor(Color.LIGHT_GRAY);
				cellar.setFont(PDType1Font.HELVETICA);
				cellar.setFontSize(12);
				
				table2.draw();
				//cell = row.createCell((100 / 4f), "Name: Mevan D SOUza");
				//cell.setFont(PDType1Font.HELVETICA);
				//cell.setFontSize(16);
				
/*				for (int i = 1; i < fact.length; i++) {
					if (fact[i].startsWith("image:")) {
						
						cell = row.createImageCell((100 / 2f), ImageUtils.readImage(des));
					} else {
						cell = row.createCell((100 / 9f), fact[i]);
						cell.setFont(PDType1Font.HELVETICA_OBLIQUE);
						cell.setFontSize(6);
						//Set colors
						if (fact[i].contains("beer"))
							cell.setFillColor(Color.yellow);
						if (fact[i].contains("champion"))
							cell.setTextColor(Color.GREEN);
					}
				}*/
			

			
				cell = row.createImageCell(100/4f, ImageUtils.readImage(bcdes), HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM);
			
			
			
			}
			
			Row<PDPage> 	row = table.createRow(5f);
			cell = row.createImageCell(100, ImageUtils.readImage(logoF), HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
			cell.setTopPadding(0);
			cell.setBottomPadding(0);
			
	        table.draw();
	        //   contentStream.beginText();
	         //   contentStream.setFont(font, 12);
	            // Move to the start of the next line, offset from the start of the
	            // current line by (150, 700).
	         //   contentStream.newLineAtOffset(500, 600);
	            // Shows the given text at the location specified by the current
	            // text matrix.
	         //   contentStream
	          //          .showText("This is a PDF document created by PDFBox library");
	           // contentStream.endText();
	      //  PDImageXObject image = PDImageXObject.createFromFileByExtension(des, document);
            // draw an image of width 300 and height 200. Here 100,480 represents x,y coordinates from where the image needs to be drawn
          //  float scale = 1f; // alter this value to set the image size
           // contentStream.drawImage(image, 100, 400, image.getWidth()*scale, image.getHeight()*scale);

    
	        contentStream.close();

	    }

	    // Finally Let's save the PDF
	    document.save(output);
	    document.close();

	    FileUtils.deleteQuietly(bcdes);
	    return output;
	}
	
	
	public ByteArrayOutputStream prefillPDFTest1(GroupMember groupMember) throws IOException {
		//Set margins
		 ByteArrayOutputStream output = new ByteArrayOutputStream(); 
		float margin = 10;

	    if(groupMember == null)
	    	return null;
	    // Creating Document
        URL url = new URL(
				"http://reminders-mrdapp.rhcloud.com/app/00622239-f2b3-48e7-a55a-d7c1f65d05ad/batch/files/MKC_Logo_Xtra_Small.jpg");
        File f = new File(System.getProperty("java.io.tmpdir", "/tmp"), "batch/files");
		File des = new File(f,"AdultDDTicket.jpg");
		if(!des.exists())
        FileUtils.copyURLToFile(url, des);
		
		URL bc = new URL("https://chart.googleapis.com/chart?chs=116x116&cht=qr&chl=MON16-0001");
		File bcdes = new File(f,"MON16-0001.jpg");
		FileUtils.copyURLToFile(bc, bcdes);
		
		
		URL logo = new URL("http://reminders-mrdapp.rhcloud.com/app/00622239-f2b3-48e7-a55a-d7c1f65d05ad/batch/files/MKC_Logo_Xtra_Small.jpg");
		File logoF = new File(f,"MKC_Logo_Xtra_Small.jpg");
		FileUtils.copyURLToFile(logo, logoF);
        // load the document
        PDDocument document = new PDDocument();
    	PDPage page = new PDPage();

        // Adding page to document
        document.addPage(page); 

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
		//Initialize table
		float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
		float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
		boolean drawContent = true;
		boolean drawLines = true;
		float yStart = yStartNewPage;
		float bottomMargin = 70;
		BaseTable table = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, margin, document, page, drawLines,
				drawContent);

		//Create Header row
		Row<PDPage> row = table.createRow(15f);
		Cell<PDPage> cell = row.createCell((100 / 3f), "Hello", HorizontalAlignment.get("center"),
				VerticalAlignment.get("top"));
		cell.setTextRotated(true);
		cell.setFont(PDType1Font.HELVETICA);
		cell.setFontSize(6);

		Cell<PDPage> cell2 = row.createCell((100 / 3f), "It's me", HorizontalAlignment.get("center"),
				VerticalAlignment.get("middle"));
		cell2.setTextRotated(true);
		cell2.setFont(PDType1Font.HELVETICA);
		cell2.setFontSize(6);

		Cell<PDPage> cell3 = row.createCell((100 / 3f), "I was wondering", HorizontalAlignment.get("center"),
				VerticalAlignment.get("bottom"));
		cell3.setTextRotated(true);
		cell3.setFont(PDType1Font.HELVETICA);
		cell3.setFontSize(6);

		Row<PDPage> row2 = table.createRow(15f);
		Cell<PDPage> cell4 = row2.createCell((100 / 3.0f), groupMember.getFirstName().toUpperCase(), HorizontalAlignment.get("center"),
				VerticalAlignment.get("top"));
		cell4.setFont(PDType1Font.HELVETICA);
		cell4.setFontSize(6);

		Cell<PDPage> cell5 = row2.createCell((100 / 3f), groupMember.getFirstName().toUpperCase(), HorizontalAlignment.get("center"),
				VerticalAlignment.get("middle"));
		cell5.setTextRotated(true);
		cell5.setFont(PDType1Font.HELVETICA);
		cell5.setFontSize(6);

		Cell<PDPage> cell6 = row2.createCell((100 / 3f),
				"I<br>m<br><br>i<br>n<br><br>C<br>a<br>l<br>i<br>f<br>o<br>r<br>n<br>i<br>a<br>".toUpperCase(),
				HorizontalAlignment.get("center"), VerticalAlignment.get("bottom"));
		cell6.setFont(PDType1Font.HELVETICA);
		cell6.setFontSize(6);
		table.draw();

		contentStream.close();
	    document.save(output);
	    document.close();
	    return output;
	}

	
	
	private static List<String[]> getFacts() {
		List<String[]> facts = new ArrayList<String[]>();
		facts.add(new String[] { "Oil Painting was invented by the Belgian van Eyck brothers", "art", "inventions",
				"science" });
		facts.add(new String[] { "The Belgian Adolphe Sax invented the Saxophone", "inventions", "music", "" });
		facts.add(new String[] { "11 sites in Belgium are on the UNESCO World Heritage List", "art", "history", "" });
		facts.add(new String[] { "Belgium was the second country in the world to legalize same-sex marriage",
				"politics", "image:150dpi.png", "" });
		facts.add(new String[] { "In the seventies, schools served light beer during lunch", "health", "school",
				"beer" });
		facts.add(new String[] { "Belgium has the sixth fastest domestic internet connection in the world", "science",
				"technology", "" });
		facts.add(new String[] { "Belgium hosts the World's Largest Sand Sculpture Festival", "art", "festivals",
				"world championship" });
		facts.add(
				new String[] { "Belgium has compulsary education between the ages of 6 and 18", "education", "", "" });
		facts.add(new String[] {
				"Belgium also has more comic makers per square kilometer than any other country in the world", "art",
				"social", "world championship" });
		facts.add(new String[] {
				"Belgium has one of the lowest proportion of McDonald's restaurants per inhabitant in the developed world",
				"food", "health", "" });
		facts.add(new String[] { "Belgium has approximately 178 beer breweries", "beer", "food", "" });
		facts.add(new String[] { "Gotye was born in Bruges, Belgium", "music", "celebrities", "" });
		facts.add(new String[] { "The Belgian Coast Tram is the longest tram line in the world", "technology",
				"world championship", "" });
		facts.add(new String[] { "Stefan Everts is the only motocross racer with 10 World Championship titles.",
				"celebrities", "sports", "world champions" });
		facts.add(new String[] { "Tintin was conceived by Belgian artist Hergé", "art", "celebrities", "inventions" });
		facts.add(new String[] { "Brussels Airport is the world's biggest selling point of chocolate", "food",
				"world champions", "" });
		facts.add(new String[] { "Tomorrowland is the biggest electronic dance music festival in the world",
				"festivals", "music", "world champion" });
		facts.add(new String[] { "French Fries are actually from Belgium", "food", "inventions", "image:300dpi.png" });
		facts.add(new String[] { "Herman Van Rompy is the first full-time president of the European Council",
				"politics", "", "" });
		facts.add(new String[] { "Belgians are the fourth most money saving people in the world", "economy", "social",
				"" });
		facts.add(new String[] {
				"The Belgian highway system is the only man-made structure visible from the moon at night",
				"technology", "world champions", "" });
		facts.add(new String[] { "Andreas Vesalius, the founder of modern human anatomy, is from Belgium",
				"celebrities", "education", "history" });
		facts.add(
				new String[] { "Napoleon was defeated in Waterloo, Belgium", "celebrities", "history", "politicians" });
		facts.add(new String[] {
				"The first natural color picture in National Geographic was of a flower garden in Gent, Belgium in 1914",
				"art", "history", "science" });
		facts.add(new String[] { "Rock Werchter is the Best Festival in the World", "festivals", "music",
				"world champions" });

		//Make the table a bit bigger
		facts.addAll(facts);
		facts.addAll(facts);
		facts.addAll(facts);

		return facts;
	}
	
	
	
	

}
	
