package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Class for the XboxController
 * @author samega15
 */
public class XBoxController
{
	/*
	 * CURRENT CONTROLS
	 * Left Stick : Translate
	 * Right Stick : Rotate
	 */
	private Joystick xboxController;
	
	public XBoxController(int port)
	{
		xboxController = new Joystick(port);
	}
	
	/*
	 * BUTTONS
	 */
	public boolean getA()
	{
		return xboxController.getRawButton(Constants.button_A);
	}
	public boolean getB()
	{
		return xboxController.getRawButton(Constants.button_B);
	}
	public boolean getX()
	{
		return xboxController.getRawButton(Constants.button_X);
	}
	public boolean getY()
	{
		return xboxController.getRawButton(Constants.button_Y);
	}
	public boolean getLBumper()
	{
		return xboxController.getRawButton(Constants.button_leftBumper);
	}
	public boolean getRBumper()
	{
		return xboxController.getRawButton(Constants.button_rightBumper);
	}
	public boolean getBack()
	{
		return xboxController.getRawButton(Constants.button_Back);
	}
	public boolean getStart()
	{
		return xboxController.getRawButton(Constants.button_Start);
	}
	public boolean getLSClick()
	{
		return xboxController.getRawButton(Constants.button_leftStick);
	}
	public boolean getRSClick()
	{
		return xboxController.getRawButton(Constants.button_rightStick);
	}
	public int getDPad()
	{
		return xboxController.getPOV();
	}
	
	//BUTTONS DPAD
	public boolean getDPadUp()
	{
		return xboxController.getPOV() == 0;
	}
	public boolean getDPadRight()
	{
		return xboxController.getPOV() == 90;
	}
	public boolean getDPadDown()
	{
		return xboxController.getPOV() == 180;
	}
	public boolean getDPadLeft()
	{
		return xboxController.getPOV() == 270;
	}
	public boolean getDPadUpRight()
	{
		return xboxController.getPOV() == 45;
	}
	public boolean getDPadDownRight()
	{
		return xboxController.getPOV() == 135;
	}
	public boolean getDPadDownLeft()
	{
		return xboxController.getPOV() == 225;
	}
	public boolean getDPadUpLeft()
	{
		return xboxController.getPOV() == 315;
	}
	
	//1. = full RT, -1. = full LT
	public double getAxisTriggers()
	{
		return xboxController.getRawAxis(Constants.axis_rightTrigger) - xboxController.getRawAxis(Constants.axis_leftTrigger);
	}
	public double getAxisLStickX()
	{
		return xboxController.getRawAxis(Constants.axis_leftStick_X);
	}
	public double getAxisLStickY()
	{
		return xboxController.getRawAxis(Constants.axis_leftStick_Y);
	}
	public double getAxisRStickX()
	{
		return xboxController.getRawAxis(Constants.axis_rightStick_X);
	}
	public double getAxisRStickY()
	{
		return xboxController.getRawAxis(Constants.axis_rightStick_Y);
	}
	
	//POLAR
	public double getMagnitude()
	{
		return xboxController.getMagnitude();
	}
	public double getDirectionDegrees()
	{
		return xboxController.getDirectionDegrees();
	}
	
}