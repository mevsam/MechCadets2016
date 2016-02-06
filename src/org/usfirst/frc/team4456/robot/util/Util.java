package org.usfirst.frc.team4456.robot.util;

import org.usfirst.frc.team4456.robot.Constants;
import org.usfirst.frc.team4456.robot.Robot;

/**
 * various utility functions for the robot.
 * @author MechCadets
 *
 */
public class Util
{
	
	/**
	 * calculates minimum
	 * @param a first number
	 * @param b second number
	 * @return minimum number
	 */
	public static double min(double a, double b)
	{
		return a>b ? b : a;
	}
	
	/**
	 * calculates maximum
	 * @param a first number
	 * @param b second number
	 * @return maximum number
	 */
	public static double max(double a, double b)
	{
		return a>b ? a : b;
	}
	
	/**
	 * Meant for value with range 0 to 1
	 * Reduces sensitivity for lower values
	 * @param value value
	 * @return value^3
	 * This sets the sensitivity exponentially
	 */
	public static double lowerSensitivity(double value, Robot robot)
	{
		// The value should be from 0 to 1, so it makes an exponential curve
		// This method can be used by the various drive methods
		value = Math.pow(value, 3) * robot.speedFactor;
		if(value > 1)
		{
			value = 1;
		}
		if(value < -1)
		{
			value = -1;
		}
		return value;
	}
	
	// This will just set an angle in between 0 and 360 to make it easier for the user to understand
    public static double get360Angle(double value)
    {
    	while((value < 0 || value > 360))
    	{
    		if(value < 0)
    		{
    			value = value + 360;
    		}
    		if(value > 360)
    		{
    			value = value - 360;
    		}
    	}
    	return value;
    }

	public static double findAngleDiff(double angle1, double angle2)
	{
		double result = angle1 - angle2;
		
		if(result < -180)
			result += 360;
		if(result > 180)
			result -= 360;
		
		return result;
	}
}
