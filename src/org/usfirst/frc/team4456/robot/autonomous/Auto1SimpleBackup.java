package org.usfirst.frc.team4456.robot.autonomous;

import org.usfirst.frc.team4456.robot.Robot;

import edu.wpi.first.wpilibj.Timer;

/**
 * SimpleBackup
 * back up the robot until a certain displacement has been reached or time has passed.
 * @author serge-olivieramega
 *
 */
public class Auto1SimpleBackup implements AutoSequence
{
	private Robot robot;
	private float initialDisplacement;
	Timer timer;

	public Auto1SimpleBackup(Robot robot)
	{
		this.robot = robot;
		timer = new Timer();
	}

	@Override
	public void runInit()
	{
		initialDisplacement = robot.navx.getDisplacementY();
	}

	@Override
	public void runPeriodic()
	{
		//INERTIAL BASED -------
		
		//if displacement_Y is NOT over 15, move backwards
		if(!(Math.abs(robot.navx.getDisplacementY() - initialDisplacement) > 15))
			robot.driver.driveRawPolar(.4, 180, 0, robot);
		else
			robot.driver.driveRawPolar(0, 0, 0, robot);
		
		//TIME BASED -------
		/*
		if(!timer.hasPeriodPassed(2.5))
			robot.driver.driveRawPolar(.4, 180, 0);
		else
			robot.driver.driveRawPolar(0, 0, 0);
		*/
		
	}

}
