package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Autonomous Command 1
 * >pick up tote
 * >move backward for a distance
 * @author serge-olivieramega
 *
 */
public class AutonomousCommand1 extends Command
{
	Robot robot;
	boolean isFinished = false;
	double initialDisplacement;
	
	public AutonomousCommand1(Robot robot)
	{
		this.robot = robot;
	}
	
	//method is called 1st time the command runs
	@Override
	protected void initialize()
	{
		System.out.println("Running AutoCommand1");
		initialDisplacement = robot.navx.getDisplacementY();
		robot.hooks.setIndex(Constants.HOOK_LOADER_POSITIONS.length - 2); // raise hooks to level 1
	}
	
	//periodically called until command finishes
	//TODO try this code again, I think it works. - samega15
	@Override
	protected void execute()
	{
		int arrayLength = Constants.WINCH_LADDER_POSITIONS.length;
		if(robot.hooks.getWinchPosition() > Constants.HOOK_LOADER_AUTO_CHECK_POSITION) // if hooks are above level 1 - 200 encoder units
			robot.driver.driveRawPolar(.4, 180, 0, robot);
		isFinished = (Math.abs(robot.navx.getDisplacementY() - initialDisplacement) > 15);
	}
	
	//returns true if the command is finished running
	@Override
	protected boolean isFinished()
	{
		return isFinished;
	}
	
	//called when command ends w/o interruption
	@Override
	protected void end()
	{
		robot.driver.driveRawPolar(0, 0, 0, robot);
		robot.hooks.setIndex(Constants.HOOK_LOADER_POSITIONS.length - 1); // set hooks back down to the lowest level
	}
	
	//called when command is interrupted
	@Override
	protected void interrupted()
	{
		robot.driver.driveRawPolar(0, 0, 0, robot);
	}

}
