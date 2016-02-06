package org.usfirst.frc.team4456.robot.SDashUI;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SDPID implements SDElement
{
	PIDController pidControl;
	PIDSource pidSource;
	PIDOutput pidOut;
	
	String key;
	
	public SDPID(String key, double p, double i, double d)
	{
		pidSource = new PIDSource()
		{
			@Override
			public double pidGet()
			{
				return 0;
			}
		};
		pidOut = new PIDOutput()
		{
			@Override
			public void pidWrite(double output)
			{
				
			}
		};
		pidControl = new PIDController(p, i, d, pidSource, pidOut);
		
		this.key = key;
		SmartDashboard.putData(key, pidControl);
	}

	@Override
	public String getKey()
	{
		return key;
	}
	
	public void update()
	{
		this.updatePIDValues();
		pidControl = (PIDController)SmartDashboard.getData(key);
	}
	
	/**
	 * pid controller name: pidControl
	 * remember to set P, I, D for pidControl
	 */
	public void updatePIDValues()
	{
		
	}
}