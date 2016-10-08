package address;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Size;

public class GaussianFilter extends Filter{
	
	private double sigmaX;
	private double sigmaY;
	
	public GaussianFilter(String path, int size ,double devX, double devY) throws Exception{
		super(path,size);
		sigmaX=devX;
		sigmaY=devY;
	}

	public GaussianFilter(Mat matrix, int size, double devX, double devY) {
		super(matrix, size);
		sigmaX=devX;
		sigmaY=devY;
	}

	@Override
	//sigmaX odchylenie standardowe X
	//sigmaY odchylenie standardowe Y
	public void filtrImage() {
		Imgproc.GaussianBlur(imageMatrix, imageMatrix,new Size(size,size), sigmaX, sigmaY);
	}

}
