package org.usfirst.frc.team4456.robot.autonomous;

import org.usfirst.frc.team4456.robot.Constants;
import org.usfirst.frc.team4456.robot.Robot;

import edu.wpi.first.wpilibj.Timer;

public class Auto3PickToteBackup implements AutoSequence
{

	private Robot robot;
	private Timer timer;
	private float initialDisplacement;
	private boolean stop = false;

	public Auto3PickToteBackup(Robot robot)
	{
		this.robot = robot;
		timer = new Timer();
	}

	@Override
	public void runInit()
	{
		initialDisplacement = robot.navx.getDisplacementY();
		robot.hooks.setIndex(Constants.HOOK_LOADER_POSITIONS.length - 2);
		
		/*
		//TIME BASED--
		timer.start();
		Timer.delay(.7);
		*/
	}

	@Override
	public void runPeriodic()
	{
		//NAVX BASED--
		if(!stop)
		{
			//if hooks encoder value is under -2700 encoder units
			//reminder:lower encoder value = higher hooks position
			if(robot.hooks.getWinchPosition() < Constants.HOOK_LOADER_AUTO_CHECK_POSITION)
				robot.driver.driveRawPolar(.4, 180, 0, robot);
			
			if(Math.abs(robot.navx.getDisplacementY() - initialDisplacement) > 15)
				this.stop();
		}
		
		/*
		//TIME BASED--
		if(!stop)
		{
			if(timer.hasPeriodPassed(2.5))
				stop();
			
			robot.driver.driveRawPolar(.4, 180, 0);
		}
		*/
	}

	private void stop()
	{
		stop = true;
	}

}
