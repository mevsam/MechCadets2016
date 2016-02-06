package org.usfirst.frc.team4456.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.ColorMode;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.RGBValue;
import com.ni.vision.NIVision.Range;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.vision.AxisCamera;

import edu.wpi.first.wpilibj.image.HSLImage;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Class for the Vision Processing.
 * @author samega15
 */
public class Vision
{
	/*
	 * NOTE: if you get an Invalid Image error, check to make sure that
	 * the image type for extracting planes is IMAGE_U8
	 */
	int session;
	private AxisCamera camera;
	
	Image frame;
	Image binaryFrame;
	Image channel1, channel2, channel3;
	int imaqError;
	
	double AREA_MINIMUM = 0.5;
	
	NIVision.RawData rawData;
	
	Range HUE = new Range(0,255);
	Range SAT = new Range(0,60);
	Range VAL = new Range(150,255);
	
	RGBValue rgbVal = new RGBValue(255, 255, 255, 1);
	
	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
    
    public Vision()
    {
    	// Init SmartDashValues
    	SmartDashboard.putNumber("Range1min", HUE.minValue);
    	SmartDashboard.putNumber("Range1max", HUE.maxValue);
    	
    	SmartDashboard.putNumber("Range2min", SAT.minValue);
    	SmartDashboard.putNumber("Range2max", SAT.maxValue);
    	
    	SmartDashboard.putNumber("Range3min", VAL.minValue);
    	SmartDashboard.putNumber("Range3max", VAL.maxValue);
    	
    	rawData = new NIVision.RawData();
    	
    	frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
    	binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
    	criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA,AREA_MINIMUM, 100.0, 0, 0);
    	
    	channel1 = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
    	channel2 = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
    	channel3 = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
    	
    	camera = new AxisCamera("10.50.0.30");
    	SmartDashboard.putNumber("CameraBrightness", camera.getBrightness());
    	
        camera = new AxisCamera("10.50.0.30");
    }
    
    public void cycle()
    {
    	
    }
    
    public void updateRanges()
    {
    	HUE.minValue = (int)SmartDashboard.getNumber("Range1min");
    	HUE.maxValue = (int)SmartDashboard.getNumber("Range1max");
    	
    	SAT.minValue = (int)SmartDashboard.getNumber("Range2min");
    	SAT.maxValue = (int)SmartDashboard.getNumber("Range2max");
    	
    	VAL.minValue = (int)SmartDashboard.getNumber("Range3min");
    	VAL.maxValue = (int)SmartDashboard.getNumber("Range3max");
    	
    	camera.writeBrightness((int)SmartDashboard.getNumber("CameraBrightness"));

    	camera.getImage(frame);

        Timer.delay(0.005);
    }
    
    public void writeThresholdImg()
    {
    	updateRanges();
    	
    	camera.getImage(frame);
    	NIVision.imaqWriteBMPFile(frame, Constants.filePathRoborio + "imgMain.bmp", 0, rgbVal);
    	
    	//threshold the image
    	NIVision.imaqColorThreshold(binaryFrame, frame, 255, ColorMode.HSV, HUE, SAT, VAL);
    	
    	int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
    	//System.out.println("Masked Particles: " + numParticles);
    	
    	CameraServer.getInstance().setImage(binaryFrame);
    	NIVision.imaqWriteBMPFile(binaryFrame, Constants.filePathRoborio + "binaryImg.bmp", 0, rgbVal);
    	
    	//extract planes
    	NIVision.imaqExtractColorPlanes(frame, ColorMode.RGB, channel1, channel2, channel3);
    	
    	NIVision.imaqWriteJPEGFile(channel1, Constants.filePathRoborio + "channel1.jpg", 99, (NIVision.RawData)rawData);
    	NIVision.imaqWriteJPEGFile(channel2, Constants.filePathRoborio + "channel2.jpg", 99, (NIVision.RawData)rawData);
    	NIVision.imaqWriteJPEGFile(channel3, Constants.filePathRoborio + "channel3.jpg", 99, (NIVision.RawData)rawData);

    	Timer.delay(0.005);
    }
}
