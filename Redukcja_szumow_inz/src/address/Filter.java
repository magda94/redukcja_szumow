package address;

import java.awt.image.BufferedImage;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public abstract class Filter {
	
	protected Mat imageMatrix=new Mat();
	
	protected int size;
	
	public Filter(String path,int size) throws Exception{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			imageMatrix=Highgui.imread(path);
			Imgproc.cvtColor(imageMatrix, imageMatrix, Imgproc.COLOR_RGBA2BGR);
		this.size=size;
	}
	
	public Filter(Mat matrix,int size){
		imageMatrix=matrix;
		this.size=size;
	}
	
	public Mat returnMatrix(){
		return imageMatrix;
	}
	
	public abstract void filtrImage();
	
	public Image returnFiltredImage(){
		BufferedImage bImage;
		byte[] data=new byte[imageMatrix.width()*imageMatrix.height()*(int)imageMatrix.elemSize()];
		imageMatrix.get(0,0,data);
		if(imageMatrix.channels()>1){
			bImage = new BufferedImage(imageMatrix.width(), imageMatrix.height(), BufferedImage.TYPE_3BYTE_BGR);
		}else{
			bImage = new BufferedImage(imageMatrix.width(), imageMatrix.height(), BufferedImage.TYPE_BYTE_GRAY);
		}
		bImage.getRaster().setDataElements(0,0,imageMatrix.cols(),imageMatrix.rows(),data);
		Image image = SwingFXUtils.toFXImage(bImage, null);
		return image;
	}
}
