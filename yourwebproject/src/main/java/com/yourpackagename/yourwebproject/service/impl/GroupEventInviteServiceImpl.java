/**
 * 
 */
package com.yourpackagename.yourwebproject.service.impl;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.VerticalAlignment;
import be.quodlibet.boxable.utils.ImageUtils;


import com.yourpackagename.framework.data.BaseJpaServiceImpl;
import com.yourpackagename.yourwebproject.common.Props;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPass;
import com.yourpackagename.yourwebproject.model.entity.GroupEvents;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.repository.GroupEventInviteRepository;
import com.yourpackagename.yourwebproject.service.GroupEventInviteService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class GroupEventInviteServiceImpl extends
		BaseJpaServiceImpl<GroupEventInvite, String> implements
		GroupEventInviteService {

	@Autowired
	private GroupEventInviteRepository groupEventInviteRepository;

	protected @Autowired Props props;
	
	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = groupEventInviteRepository;
		this.entityClass = GroupEventInvite.class;
		this.baseJpaRepository.setupEntityClass(GroupEventInvite.class);

	}

	public List<GroupEventInvite> findByGroupCodeAndCategoryCodeAndEventCode(
			String groupCode, String memberCategoryCode, String eventCode) {
		return groupEventInviteRepository
				.findByGroupCodeAndCategoryCodeAndEventCode(groupCode,
						memberCategoryCode, eventCode);
	}

	public List<GroupEventInvite> findByGroupCodeAndEventCode(String groupCode,
			String eventCode) {
		return groupEventInviteRepository.findByGroupCodeAndEventCode(
				groupCode, eventCode);
	}

	public List<GroupEventInvite> findByGroupMember(GroupMember groupMember) {
		return groupEventInviteRepository.findByGroupMember(groupMember);
	}

	public GroupEventInvite findByGroupMemberAndGroupEvent(
			GroupMember groupMember, GroupEvents groupEvent) {
		return groupEventInviteRepository.findByGroupMemberAndGroupEvent(
				groupMember, groupEvent);
	}

	public GroupEventInvite findByGroupEventInviteCode(
			String groupEventInviteCode) {
		return groupEventInviteRepository
				.findByGroupEventInviteCode(groupEventInviteCode);
	}

	public ByteArrayOutputStream generateTicketPDF(GroupEventInvite gei,
			List<GroupEventPass> groupEventPasses) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		if (gei == null || CollectionUtils.isEmpty(groupEventPasses))
			return output;
		// Creating Document

		File f = new File(System.getProperty("java.io.tmpdir", "/tmp"),
				"batch/files");
		// load the document

		String urlLogo =  props.applicationUrl+"/"+props.applicationProject+"/00622239-f2b3-48e7-a55a-d7c1f65d05ad/batch/files/MKC_Logo_Xtra_Small.jpg";
		URL logo = new URL(urlLogo);
		File logoFile = new File(f, "MKC_Logo_Xtra_Small.jpg");
		FileUtils.copyURLToFile(logo, logoFile);

		File passImageFile = null;
		File barcodeFile = null;

		PDDocument document = new PDDocument();
		int passIterationTracker = 0;
		int noOfPages = groupEventPasses.size() / 2;
		float noOfPagesActual = groupEventPasses.size() / 2f;

		if (noOfPagesActual > noOfPages * 1f) {
			noOfPages++;
		}
		// Creating Pages
		for (int a = 0; a < noOfPages; a++) {

			PDPage page = new PDPage();

			// Adding page to document
			document.addPage(page);

			// Adding FONT to document
			PDType1Font font = PDType1Font.HELVETICA;
			PDPageContentStream contentStream = new PDPageContentStream(
					document, page);

			float margin = 30;
			float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
			float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
			boolean drawContent = true;
			float yStart = yStartNewPage;
			float bottomMargin = 0;
			BaseTable table = new BaseTable(yStart, yStartNewPage,
					bottomMargin, tableWidth, margin, document, page, true,
					drawContent);

			Row<PDPage> headerRow = table.createRow(15f);
			Cell<PDPage> cell = headerRow
					.createCell(100,
							"Note : Please carry a print of your tickets to the venue!");
			cell.setFont(PDType1Font.HELVETICA_BOLD);
			cell.setFillColor(Color.BLACK);
			cell.setTextColor(Color.WHITE);
			cell.setFontSize(12);
			// This will be there on each page
			table.addHeaderRow(headerRow);

			// A Max of 2 tickets on each page
			for (int ab = 0; ab < 2
					&& passIterationTracker < groupEventPasses.size(); ab++) {

				GroupEventPass gep = groupEventPasses.get(passIterationTracker);
				if (gep.getGroupEventPassCategory() != null) {
					float second = 0.25f;
					float third = 1.75f;
					boolean showPassType = true;
					if(StringUtils.isBlank(gep.getGroupEventPassCategory().getPassCategoryName()))
					{
						third = second+third;
						showPassType = false;
					}
					if (StringUtils.isNotBlank(gep.getGroupEventPassCategory()
							.getPassImageURL())) {
						String passImage = gep.getGroupEventPassCategory()
								.getPassImageURL();
						URL url = new URL(passImage);
						String fileName = passImage.substring(passImage
								.lastIndexOf("/") + 1);
						passImageFile = new File(f, fileName);
						if (passImageFile != null && !passImageFile.exists())
							FileUtils.copyURLToFile(url, passImageFile);
					}

					if (StringUtils.isNotBlank(gep.getGroupEventPassCategory()
							.getPassBarocodeURL())) {
						String passBarcodeURL = gep.getGroupEventPassCategory()
								.getPassBarocodeURL();
						URL bc = new URL(passBarcodeURL + gep.getPassBarcode());
						String fileName = gep.getPassBarcode() + ".jpg";
						barcodeFile = new File(f, fileName);
						if (barcodeFile != null && !barcodeFile.exists())
							FileUtils.copyURLToFile(bc, barcodeFile);
					}

					Row<PDPage> row = table.createRow(5f);
					if (passImageFile != null) {
						cell = row.createImageCell((100 / 8f) * 6,
								ImageUtils.readImage(passImageFile),
								HorizontalAlignment.CENTER,
								VerticalAlignment.MIDDLE);
						cell.setTopPadding(5);
						cell.setBottomPadding(5);
					}
					
					if(showPassType){
					cell = row.createCell((100 / 8f) * second,
							gep.getGroupEventPassCategory().getPassCategoryName() ,HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
					cell.setTopPadding(1);
					cell.setBottomPadding(1);
					cell.setRightPadding(1);
					cell.setLeftPadding(5);
					cell.setTextRotated(true);
					cell.setFontSize(15);
					cell.setFillColor(Color.LIGHT_GRAY);
					cell.setFont(PDType1Font.HELVETICA_BOLD);}
					// buffer make sure the text is placed at the right height
					// for every ticket
					float buffer = 0;
					System.out.println("cell height:" + cell.getHeight());
					buffer = ab * cell.getHeight();
					BaseTable table2 = new BaseTable(yStart - 30 - buffer,
							yStartNewPage - 30 - buffer, bottomMargin, 140,
							458, document, page, false, drawContent);

					Row<PDPage> inner = table2.createRow(1f);
					Cell<PDPage> cellar = inner.createCell(100, "Name : "
							+ gep.getGroupEventInvite().getGroupMember()
									.getFirstName());
					cellar.setFont(PDType1Font.HELVETICA);
					cellar.setFontSize(10);
					cellar.setTopPadding(0);

					inner = table2.createRow(1f);
					cellar = inner.createCell(100, "Entry for : "+gep.getNoOfPeopleTagged());
					cellar.setFont(PDType1Font.HELVETICA);
					cellar.setFontSize(10);
					cellar.setTopPadding(0);
					// cellar.setFillColor(Color.LIGHT_GRAY);

					boolean displayPrice = false;
					if (gep.getGroupEventPassCategory() != null) {
						if (gep.getGroupEventPassCategory().isDisplayPrice()) {
							displayPrice = true;
						}
					}
					if (displayPrice) {
						inner = table2.createRow(1f);
						cellar = inner.createCell(100,
								"Pass Price : $" + gep.getPassPrice());
						cellar.setFont(PDType1Font.HELVETICA);
						cellar.setFontSize(10);
						cellar.setTopPadding(0);
					}
					if (StringUtils.isNotBlank(gep.getTableNumber())) {
						inner = table2.createRow(1f);
						cellar = inner.createCell(100,
								"Seating : " + gep.getTableNumber());
						cellar.setFont(PDType1Font.HELVETICA);
						cellar.setFontSize(10);
						cellar.setTopPadding(0);
					}
					Row<PDPage> inner2 = table2.createRow(1f);
					cellar = inner2.createCell(100, gep.getPassIdentifier());
					// cellar.setFillColor(Color.LIGHT_GRAY);
					cellar.setFont(PDType1Font.HELVETICA);
					cellar.setFontSize(12);

					table2.draw();

					if (barcodeFile != null)
						cell = row.createImageCell((100 / 8f)*third,
								ImageUtils.readImage(barcodeFile),
								HorizontalAlignment.CENTER,
								VerticalAlignment.BOTTOM);
				}
				passIterationTracker++;
				FileUtils.deleteQuietly(barcodeFile);
			}

			if (logoFile != null) {
				Row<PDPage> row = table.createRow(5f);
				cell = row.createImageCell(100, ImageUtils.readImage(logoFile),
						HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
				cell.setTopPadding(2);
				cell.setBottomPadding(2);
			}
			table.draw();
			// contentStream.beginText();
			// contentStream.setFont(font, 12);
			// Move to the start of the next line, offset from the start of the
			// current line by (150, 700).
			// contentStream.newLineAtOffset(500, 600);
			// Shows the given text at the location specified by the current
			// text matrix.
			// contentStream
			// .showText("This is a PDF document created by PDFBox library");
			// contentStream.endText();
			// PDImageXObject image =
			// PDImageXObject.createFromFileByExtension(des, document);
			// draw an image of width 300 and height 200. Here 100,480
			// represents x,y coordinates from where the image needs to be drawn
			// float scale = 1f; // alter this value to set the image size
			// contentStream.drawImage(image, 100, 400, image.getWidth()*scale,
			// image.getHeight()*scale);

			contentStream.close();

		}

		// Finally Let's save the PDF
		document.save(output);
		document.close();

		return output;
	}
}
