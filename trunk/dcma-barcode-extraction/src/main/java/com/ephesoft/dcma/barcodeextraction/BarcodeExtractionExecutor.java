/********************************************************************************* 
* Ephesoft is a Intelligent Document Capture and Mailroom Automation program 
* developed by Ephesoft, Inc. Copyright (C) 2010-2011 Ephesoft Inc. 
* 
* This program is free software; you can redistribute it and/or modify it under 
* the terms of the GNU Affero General Public License version 3 as published by the 
* Free Software Foundation with the addition of the following permission added 
* to Section 15 as permitted in Section 7(a): FOR ANY PART OF THE COVERED WORK 
* IN WHICH THE COPYRIGHT IS OWNED BY EPHESOFT, EPHESOFT DISCLAIMS THE WARRANTY 
* OF NON INFRINGEMENT OF THIRD PARTY RIGHTS. 
* 
* This program is distributed in the hope that it will be useful, but WITHOUT 
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
* FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more 
* details. 
* 
* You should have received a copy of the GNU Affero General Public License along with 
* this program; if not, see http://www.gnu.org/licenses or write to the Free 
* Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 
* 02110-1301 USA. 
* 
* You can contact Ephesoft, Inc. headquarters at 111 Academy Way, 
* Irvine, CA 92617, USA. or at email address info@ephesoft.com. 
* 
* The interactive user interfaces in modified source and object code versions 
* of this program must display Appropriate Legal Notices, as required under 
* Section 5 of the GNU Affero General Public License version 3. 
* 
* In accordance with Section 7(b) of the GNU Affero General Public License version 3, 
* these Appropriate Legal Notices must retain the display of the "Ephesoft" logo. 
* If the display of the logo is not reasonably feasible for 
* technical reasons, the Appropriate Legal Notices must display the words 
* "Powered by Ephesoft". 
********************************************************************************/ 

package com.ephesoft.dcma.barcodeextraction;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ephesoft.dcma.barcodeextraction.BarcodeExtractionReader.BarcodeReaderTypes;
import com.ephesoft.dcma.batch.schema.Document;
import com.ephesoft.dcma.batch.schema.Page;
import com.ephesoft.dcma.core.exception.DCMAApplicationException;
import com.ephesoft.dcma.core.threadpool.AbstractRunnable;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.GenericMultipleBarcodeReader;

public class BarcodeExtractionExecutor extends AbstractRunnable {

	private final String sourcePath;
	private final String fileName;
	private final String[] appReaderTypes;
	private BarcodeExtractionResult[] barCodeResults;
	private final Document document;
	private final Page page;

	private static final Logger LOGGER = LoggerFactory.getLogger(BarcodeExtractionExecutor.class);

	public BarcodeExtractionExecutor(String sourcePath, String fileName, String[] appReaderTypes, Page page, Document document) {
		super();
		this.sourcePath = sourcePath;
		this.fileName = fileName;
		this.appReaderTypes = appReaderTypes;
		this.page = page;
		this.document = document;
	}

	/**
	 * This method processes each image file supplied using Zxing library and finds the barcode results using different readers for
	 * Code 39, QR , Code 93, Code 128, ITF, PDF 417 , EAN 13 Codabar and Datamatrix formats.
	 * 
	 */
	@Override
	public void run() {
		LOGGER.info("Running barcode for the file " + fileName);
		BufferedImage sourceImage = null;
		Result[] codeResults = null;
		Vector<BarcodeFormat> barCodeFormatVector = new Vector<BarcodeFormat>();
		try {
			if (sourcePath != null && !sourcePath.isEmpty()) {
				sourceImage = ImageIO.read(new File(sourcePath));
			} else {
				LOGGER.error("No valid extensions are specified in resources");
				setDcmaApplicationException(new DCMAApplicationException("No valid extensions are specified in resources"));
				return;
			}
			if (sourceImage == null) {
				LOGGER.error("Source image is null for image : " + sourcePath);
				setDcmaApplicationException(new DCMAApplicationException("Source image is null for image : " + sourcePath));
				return;
			}
			if (appReaderTypes != null && appReaderTypes.length > 0) {
				for (String appReaderType : appReaderTypes) {

					if (appReaderType.equalsIgnoreCase(BarcodeReaderTypes.CODE39.name())) {
						barCodeFormatVector.add(BarcodeFormat.CODE_39);
					} else if (appReaderType.equalsIgnoreCase(BarcodeReaderTypes.QR.name())) {
						barCodeFormatVector.add(BarcodeFormat.QR_CODE);
					} else if (appReaderType.equalsIgnoreCase(BarcodeReaderTypes.DATAMATRIX.name())) {
						barCodeFormatVector.add(BarcodeFormat.DATA_MATRIX);
					} else if (appReaderType.equalsIgnoreCase(BarcodeReaderTypes.CODE128.name())) {
						barCodeFormatVector.add(BarcodeFormat.CODE_128);
					} else if (appReaderType.equalsIgnoreCase(BarcodeReaderTypes.CODE93.name())) {
						barCodeFormatVector.add(BarcodeFormat.CODE_93);
					} else if (appReaderType.equalsIgnoreCase(BarcodeReaderTypes.ITF.name())) {
						barCodeFormatVector.add(BarcodeFormat.ITF);
					} else if (appReaderType.equalsIgnoreCase(BarcodeReaderTypes.CODABAR.name())) {
						barCodeFormatVector.add(BarcodeFormat.CODABAR);
					} else if (appReaderType.equalsIgnoreCase(BarcodeReaderTypes.PDF417.name())) {
						barCodeFormatVector.add(BarcodeFormat.PDF417);
					} else if (appReaderType.equalsIgnoreCase(BarcodeReaderTypes.EAN13.name())) {
						barCodeFormatVector.add(BarcodeFormat.EAN_13);
					}
				}
				codeResults = getBarCodeResults(sourceImage, fileName, barCodeFormatVector);
				barCodeResults = setBarcodeResults(codeResults);

			}

			else {
				LOGGER.error("No proper Barcode type is specified in resources");
				setDcmaApplicationException(new DCMAApplicationException("No proper Barcode type is specified in resources"));
			}

		} catch (IOException e) {
			LOGGER.error("Could not read sourceImage " + sourcePath);
			setDcmaApplicationException(new DCMAApplicationException("Could not read sourceImage " + sourcePath));
		} finally {
			if (sourceImage != null) {
				sourceImage.flush();
				barCodeFormatVector = null;
			}
		}
	}

	/**
	 * This method scans the barcode information for images . If image is of different format this method will return null.
	 * 
	 * @param sourceImage
	 * @param fileName
	 * @param barCodeFormatVector
	 * @return Result[]
	 */
	public Result[] getBarCodeResults(final BufferedImage sourceImage, final String fileName,
			final Vector<BarcodeFormat> barCodeFormatVector) {
		Result[] codeResults = null;
		LuminanceSource source = new BufferedImageLuminanceSource(sourceImage);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();

		MultiFormatReader mfr = new MultiFormatReader();

		hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
		hints.put(DecodeHintType.POSSIBLE_FORMATS, barCodeFormatVector);

		GenericMultipleBarcodeReader genericReader = new GenericMultipleBarcodeReader(mfr);
		try {

			codeResults = genericReader.decodeMultiple(bitmap, hints);
		} catch (ReaderException re) {
			LOGGER.info("The image was not of types defined in the system" + re.getMessage());
		} catch (Exception re) {
			LOGGER.info("Barcode Reader could not read image file: " + fileName);
		} finally {
			hints = null;
		}
		return codeResults;
	}

	/**
	 * This method populates BarcodeResult array from Result array fetched by uzing Zxing libraries.
	 * 
	 * @param results
	 * @return BarcodeResult[]
	 */
	public BarcodeExtractionResult[] setBarcodeResults(Result[] results) {
		BarcodeExtractionResult[] barcodeResults = null;
		if (results != null && results.length > 0) {
			barcodeResults = new BarcodeExtractionResult[results.length];
			for (int i = 0; i < results.length; i++) {
				barcodeResults[i] = new BarcodeExtractionResult();
				barcodeResults[i].setX0Coordinate(results[i].getResultPoints()[0].getX());
				barcodeResults[i].setX1Coordinate(results[i].getResultPoints()[1].getX());
				barcodeResults[i].setY0Coordinate(results[i].getResultPoints()[0].getY());
				barcodeResults[i].setY1Coordinate(results[i].getResultPoints()[1].getY());
				barcodeResults[i].setTexts(results[i].getText());
				if (BarcodeFormat.CODE_39.getName().equalsIgnoreCase(results[i].getBarcodeFormat().getName())) {
					barcodeResults[i].setBarcodeType(BarcodeReaderTypes.CODE39);
				} else if (BarcodeFormat.QR_CODE.getName().equalsIgnoreCase(results[i].getBarcodeFormat().getName())) {
					barcodeResults[i].setBarcodeType(BarcodeReaderTypes.QR);
				} else if (BarcodeFormat.DATA_MATRIX.getName().equalsIgnoreCase(results[i].getBarcodeFormat().getName())) {
					barcodeResults[i].setBarcodeType(BarcodeReaderTypes.DATAMATRIX);
				} else if (BarcodeFormat.CODE_128.getName().equalsIgnoreCase(results[i].getBarcodeFormat().getName())) {
					barcodeResults[i].setBarcodeType(BarcodeReaderTypes.CODE128);
				} else if (BarcodeFormat.CODE_93.getName().equalsIgnoreCase(results[i].getBarcodeFormat().getName())) {
					barcodeResults[i].setBarcodeType(BarcodeReaderTypes.CODE93);
				} else if (BarcodeFormat.CODABAR.getName().equalsIgnoreCase(results[i].getBarcodeFormat().getName())) {
					barcodeResults[i].setBarcodeType(BarcodeReaderTypes.CODABAR);
				} else if (BarcodeFormat.ITF.getName().equalsIgnoreCase(results[i].getBarcodeFormat().getName())) {
					barcodeResults[i].setBarcodeType(BarcodeReaderTypes.ITF);
				} else if (BarcodeFormat.PDF417.getName().equalsIgnoreCase(results[i].getBarcodeFormat().getName())) {
					barcodeResults[i].setBarcodeType(BarcodeReaderTypes.PDF417);
				} else if (BarcodeFormat.EAN_13.getName().equalsIgnoreCase(results[i].getBarcodeFormat().getName())) {
					barcodeResults[i].setBarcodeType(BarcodeReaderTypes.EAN13);
				}
			}
		}
		return barcodeResults;
	}

	public String getFileName() {
		return fileName;
	}

	public BarcodeExtractionResult[] getBarCodeResults() {
		return barCodeResults;
	}

	public Document getDocument() {
		return document;
	}

	public Page getPage() {
		return page;
	}

}
