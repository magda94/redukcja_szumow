package address;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class HighPassFilter extends Filter{
	
	double scale;
	double delta;

	public HighPassFilter(Mat matrix, int size){
		super(matrix, size);
	}
	
	public HighPassFilter(String path, int size) throws Exception{
		super(path, size);
	}
	
	public void filtrImage(){
		setParameters();
		Mat tempImage = new Mat(imageMatrix.rows(),imageMatrix.cols(), imageMatrix.type());
		Imgproc.Laplacian(imageMatrix, tempImage, CvType.CV_8U,size, scale, delta);
		Core.add(imageMatrix, tempImage, imageMatrix);
	}
	
	private void setParameters(){
		if(size == 3){
			scale = -0.5;
			delta = 0;
		}else if(size == 5){
			scale = -0.05;
			delta = 0;
		}else if(size == 7){
			scale = -0.01;
			delta = 0;
		}
	}
}
