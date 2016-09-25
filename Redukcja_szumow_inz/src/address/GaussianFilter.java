package address;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Size;

public class GaussianFilter extends Filter{
	
	private double sigmaX;
	private double sigmaY;
	
	public GaussianFilter(String path,int size){
		super(path,size);
	}

	public GaussianFilter(Mat matrix, int size) {
		super(matrix, size);
	}

	@Override
	//sigmaX odchylenie standardowe X
	//sigmaY odchylenie standardowe Y
	public void filtrImage() {
		Imgproc.GaussianBlur(imageMatrix, imageMatrix,new Size(size,size), 1, 1);
	}

}
