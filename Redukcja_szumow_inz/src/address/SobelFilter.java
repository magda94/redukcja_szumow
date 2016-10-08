package address;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class SobelFilter extends Filter{
	
	double scale;
	double delta;
	int depth;
	int dx;
	int dy;

	public SobelFilter(String path,int size,int dx, int dy, double scale,double delta) throws Exception{
		super(path,size);
		this.scale=scale;
		this.delta=delta;
		this.dx=dx;
		this.dy=dy;
	}
	
	public SobelFilter(Mat matrix,int size, int dx, int dy, double scale,double delta){
		super(matrix,size);
		this.scale=scale;
		this.delta=delta;
		this.dx=dx;
		this.dy=dy;
	}
	
	public void filtrImage(){
		if(imageMatrix.depth()==CvType.CV_8U)
			depth=-1/CvType.CV_16S;
		if(imageMatrix.depth()==(CvType.CV_16U/CvType.CV_16S))
			depth=-1/CvType.CV_32F;
		//dx,dy pochdna w kierunku x,y (na moje szukanie krawêdzi pionowych i poziomych)
		Imgproc.Sobel(imageMatrix, imageMatrix, depth, dx,dy, size, scale, delta);
	}
}
