package address;

import java.util.ArrayList;
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
	private Mat complexImage;
	
	public FourierTransform(Mat matrix, int size){
		super(matrix, size);
		planes = new ArrayList<>();
		complexImage = new Mat(imageMatrix.rows(), imageMatrix.cols(), CvType.CV_32F);
	}
	
	public FourierTransform(String path, int size) throws Exception{
		super(path, size);
		planes = new ArrayList<>();
		complexImage = new Mat(imageMatrix.rows(), imageMatrix.cols(), CvType.CV_32F);
	}
	
	public void filtrImage(){
		Mat padded = new Mat();
		int addPixelRows = Core.getOptimalDFTSize(imageMatrix.rows());
		int addPixelCols = Core.getOptimalDFTSize(imageMatrix.cols());
		Imgproc.copyMakeBorder(imageMatrix, padded, 0, addPixelRows-imageMatrix.rows(), 0, addPixelCols-imageMatrix.cols(),
				Imgproc .BORDER_CONSTANT, Scalar.all(0));
		padded.convertTo(padded, CvType.CV_32F);
		complexImage.convertTo(complexImage, CvType.CV_32F);
		planes.add(padded);
		planes.add(Mat.zeros(padded.size(), CvType.CV_32F));
		
		Core.merge(planes, complexImage);
		
		System.out.println("32F: "+CvType.CV_32F);
		System.out.println("64F: "+CvType.CV_64F);
		System.out.println("32FC4: "+CvType.CV_32FC4);
		System.out.println("32FC3: "+CvType.CV_32FC3);
		System.out.println("32FC2: "+CvType.CV_32FC2);
		System.out.println("32FC1: "+CvType.CV_32FC1);
		
		Imgproc.cvtColor(complexImage, complexImage, Imgproc.COLOR_BGRA2GRAY);
		complexImage.convertTo(complexImage, CvType.CV_32FC2);
		System.out.println("TYP OBRAZU: "+complexImage.type());
		Core.dft(complexImage, complexImage);
		imageMatrix = createOptimalizedMagnitude(complexImage);
	}
	
	private Mat createOptimalizedMagnitude(Mat complexImage){
		List<Mat> newPlanes = new ArrayList<>();
		Mat mag = new Mat();
		Core.split(complexImage, newPlanes);
		for(int i=0;i<newPlanes.size();i++){
			System.out.println(newPlanes.get(i));
		}
		Core.magnitude(newPlanes.get(0), newPlanes.get(1), mag);
		Core.add(Mat.ones(mag.size(), CvType.CV_32F), mag, mag);
		Core.log(mag, mag);
		
		
		mag.convertTo(mag, CvType.CV_8UC1);
		Core.normalize(mag, mag, 0, 255, Core.NORM_MINMAX, CvType.CV_8UC1);
		return mag;
	}
	
}
