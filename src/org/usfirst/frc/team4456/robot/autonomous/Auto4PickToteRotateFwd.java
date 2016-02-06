package org.usfirst.frc.team4456.robot.autonomous;

import org.usfirst.frc.team4456.robot.Constants;
import org.usfirst.frc.team4456.robot.Robot;

import edu.wpi.first.wpilibj.Timer;

public class Auto4PickToteRotateFwd implements AutoSequence
{
	private Robot robot;
	private Timer timer;
	private float initialRotation;
	private float initialDisplacement;
	private float targetDisplacement = 25;

	public Auto4PickToteRotateFwd(Robot robot)
	{
		this.robot = robot;
		timer = new Timer();
	}
	
	@Override
	public void runInit()
	{
		initialRotation = robot.navx.getYaw();
		robot.hooks.setIndex(Constants.HOOK_LOADER_POSITIONS.length - 2);
		rotate();
		initialDisplacement = robot.navx.getDisplacementY();
	}

	@Override
	public void runPeriodic()
	{
		if(Math.abs(robot.navx.getDisplacementY() - initialDisplacement) < targetDisplacement)
			robot.driver.driveRawPolar(.4, 0, 0, robot);
	}
	
	private void rotate()
	{
		//rotate for 180 degrees
		while(Math.abs(robot.navx.getYaw() - initialRotation) < 180)
		{
			robot.driver.driveRawPolar(0, 0, .5, robot);
		}

		//TIME BASED --
		/*
		timer.start();
		while(!timer.hasPeriodPassed(1.0))
		{
			robot.driver.driveRawPolar(0, 0, .5);
		}
		timer.stop();
		timer.reset();
		*/
	}
}
