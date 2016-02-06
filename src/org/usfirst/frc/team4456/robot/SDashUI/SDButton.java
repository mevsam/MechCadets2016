package org.usfirst.frc.team4456.robot.SDashUI;

import org.usfirst.frc.team4456.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SDButton implements SDElement
{
	private String key;
	private Robot mainRobot;
	
	/**
	 * Constructs a SmartDashboard button object.
	 * Must override performAction() in order for the update() function to work.
	 * @param key The SmartDashboard key for this button
	 * @param robot The main robot
	 */
	public SDButton(String key, Robot robot)
	{
		this.key = key;
		
		// This will point to the main robot.
		this.mainRobot = robot;
	}
	
	/**
	 * Gets this button's key
	 * @return This button's key
	 */
	@Override
	public String getKey()
	{
		return key;
	}
	
	/**
	 * Checks to see if button is pressed. If so, performs action.
	 * Remember to override performAction before using.
	 * This should be called in the UI update function
	 */
	public void update()
	{
		if (SmartDashboard.getBoolean(key))
    	{
    		this.performAction();
    		SmartDashboard.putBoolean("key", false);
    	}
	}
	
	/**
	 * Performs action when button is pressed.
	 * The action performed differs based on what the button has overwritten for it
	 */
	public void performAction()
	{
		
	}

}
