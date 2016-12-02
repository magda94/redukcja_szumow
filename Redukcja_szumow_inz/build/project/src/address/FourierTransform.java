package address;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class FourierTransform extends Filter{
	
	private List<Mat> planes;
	private List<Mat> channels;
	private Mat complexImage;
	
	public FourierTransform(Mat matrix, int size){
		super(matrix, size);
		planes = new ArrayList<>();
		channels = new ArrayList<>();
		complexImage = new Mat(imageMatrix.rows(), imageMatrix.cols(), CvType.CV_32F);
	}
	
	public FourierTransform(String path, int size) throws Exception{
		super(path, size);
		planes = new ArrayList<>();
		channels = new ArrayList<>();
		complexImage = new Mat(imageMatrix.rows(), imageMatrix.cols(), CvType.CV_32F);
	}
	
	public void filtrImage(){
		Core.split(imageMatrix, channels);
		Mat redChannel = channels.get(0);
		redChannel.convertTo(redChannel, CvType.CV_32F);
		Mat greenChannel = channels.get(1);
		greenChannel.convertTo(greenChannel, CvType.CV_32F);
		Mat blueChannel = channels.get(2);
		blueChannel.convertTo(blueChannel, CvType.CV_32F);
		Mat RMatrix = transformImage(redChannel);
		
		imageMatrix = RMatrix;
		//imageMatrix = antiTransformImage(RMatrix);
	}
	
	private Mat transformImage(Mat image){
		Mat padded = new Mat();
		int addPixelRows = Core.getOptimalDFTSize(image.rows());
		int addPixelCols = Core.getOptimalDFTSize(image.cols());
		Imgproc.copyMakeBorder(image, padded, 0, addPixelRows-image.rows(), 0, addPixelCols-image.cols(),
				Imgproc .BORDER_CONSTANT, Scalar.all(0));
		padded.convertTo(padded, CvType.CV_32F);
		complexImage.convertTo(complexImage, CvType.CV_32F);
		planes.add(padded);
		planes.add(Mat.zeros(padded.size(), CvType.CV_32F));
		
		Core.merge(planes, complexImage);
		System.out.println("CV_32FC1 ="+CvType.CV_32FC1);
		System.out.println("COMPLEX TYPE: " + image.type());
		System.out.println("COMPLEX CHANNEL: " + image.channels());
		Core.dft(image, complexImage);
		Mat result = createOptimalizedMagnitude(complexImage);
		
		return result;
	}
	
	private Mat antiTransformImage(Mat image){
		image.convertTo(image, CvType.CV_32F);
		Core.idft(image, image);
		List<Mat> newPlanes = new ArrayList<>();
		Mat restoredImage = new Mat();
		Core.split(image, newPlanes);
		Core.normalize(newPlanes.get(0), restoredImage, 0, 255, Core.NORM_MINMAX);
		image.convertTo(restoredImage, CvType.CV_8U);
		return restoredImage;
	}
	
	private Mat createOptimalizedMagnitude(Mat complexImage){
		List<Mat> newPlanes = new ArrayList<>();
		Mat mag = new Mat();
		Core.split(complexImage, newPlanes);
		if(newPlanes.size() >1)
			Core.magnitude(newPlanes.get(0), newPlanes.get(1), mag);
		else
			mag = complexImage;
		Core.add(Mat.ones(mag.size(), CvType.CV_32F), mag, mag);
		Core.log(mag, mag);
		
		shiftDFT(mag);
		
		mag.convertTo(mag, CvType.CV_8UC1);
		Core.normalize(mag, mag, 0, 255, Core.NORM_MINMAX, CvType.CV_8UC1);
		return mag;
	}
	
	private void shiftDFT(Mat image){
		image = image.submat(new Rect(0, 0, image.cols() & -2, image.rows() & -2));
		int cx = image.cols() / 2;
		int cy = image.rows() / 2;
		
		Mat q0 = new Mat(image, new Rect(0, 0, cx, cy));
		Mat q1 = new Mat(image, new Rect(cx, 0, cx, cy));
		Mat q2 = new Mat(image, new Rect(0, cy, cx, cy));
		Mat q3 = new Mat(image, new Rect(cx, cy, cx, cy));
		
		Mat tmp = new Mat();
		/*q0.copyTo(tmp);
		q3.copyTo(q0);
		tmp.copyTo(q3);*/
		q0.copyTo(q3);
		Core.flip(q0.t(), q0, 1);
		Core.flip(q0.t(), q0, 1);
		
		
		//q1.copyTo(tmp);
		q2.copyTo(q1);
		Core.flip(q2.t(), q2, 1);
		Core.flip(q2.t(), q2, 1);
		//tmp.copyTo(q2);
	}
	
}
