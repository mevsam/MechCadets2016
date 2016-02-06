package org.usfirst.frc.team4456.robot.SDashUI;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * SmartDashboard Number Readonly
 * designed for displaying a number on the SmartDashboard, but not for setting a value from the SmartDashboard.
 * @author samega15
 */
public class SDNumberR implements SDElement
{
	private String key;
	
	/**
	 * constructs a SmartDashboard Number
	 * remember to override update()
	 * @param key key
	 */
	public SDNumberR(String key)
	{
		this.key = key;
	}

	@Override
	public String getKey()
	{
		return key;
	}
	
	/**
	 * typical update is sendSDValue(double value)
	 */
	public void update()
	{
		
	}
	
	/**
	 * Send a a number to the SmartDashboard.
	 * This gets called when update is called. Override this method before using the object.
	 */
	public void sendSDValue(double value)
	{
		SmartDashboard.putNumber(key, value);
	}
}