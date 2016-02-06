package org.usfirst.frc.team4456.robot;

import org.usfirst.frc.team4456.robot.util.Util;
import org.usfirst.frc.team4456.robot.SDashUI.*;
import org.usfirst.frc.team4456.robot.autonomous.AutoSequence;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Class for the Smart Dashboard.
 * @author samega15
 */
public class UI
{
    private Robot robot;
    
    //which autonomous mode is enabled.
    //auto mode 1 (index 1) by default.
    //autonomousEnabled[0] not used.

	public UI(Robot robot)
    {
		System.out.println("UI Initializing...");
        
		this.robot = robot;
		
		//winchLimitModeInit
		SmartDashboard.putBoolean("Winches Limit Mode Enabled", robot.limitModeEnabled);
		
        // Button for whether or not we use a gyroscope
        SmartDashboard.putBoolean("Using Gyro", false);
        
        // Button for whether we use Mechanum or Tank
        SmartDashboard.putBoolean("Using Mechanum", true);
        
        this.autonomousUIInit();
        this.driverInit();
        
        System.out.println("UI Init Done. UI Running.");
    }
	
	public void update()
	{	
    	//Robot Enabled
    	SmartDashboard.putBoolean("Enabled", robot.isEnabled());
    	
    	//robot.pidController = (PIDController) SmartDashboard.getData("talonPID");
    	
    	//AUTONOMOUS
    	robot.useAutoChooser = SmartDashboard.getBoolean("Use Auto Chooser");
    	
    	// Toggles Gyro and Mechanum Buttons
    	robot.useGyro = SmartDashboard.getBoolean("Using Gyro");
    	robot.useMechanum = SmartDashboard.getBoolean("Using Mechanum");
    	
    	//SmartDashboard.putNumber("Get PIDController", robot.driver.talon2.get());
    	
    	//LIDAR
    	SmartDashboard.putNumber("Lidar Distance", robot.lidar.getDistance());
    	
    	this.navxUI();
    	this.winchesInformation();
    	this.driverValues();
    	
    }
    
    private void navxUI()
    {
    	try
    	{
	    	SmartDashboard.putBoolean(  "navx_Connected",        robot.navx.isConnected());
	        SmartDashboard.putBoolean(  "navx_IsCalibrating",    robot.navx.isCalibrating());
	        SmartDashboard.putNumber(   "navx_Yaw",              robot.navx.getYaw());
	        SmartDashboard.putNumber(   "navx_Pitch",            robot.navx.getPitch());
	        SmartDashboard.putNumber(   "navx_Roll",             robot.navx.getRoll());
	        SmartDashboard.putNumber(   "navx_CompassHeading",   robot.navx.getCompassHeading());
	        SmartDashboard.putNumber(   "navx_Update_Count",     robot.navx.getUpdateCount());
	        SmartDashboard.putNumber(   "navx_Byte_Count",       robot.navx.getByteCount());
	        
	        
	        SmartDashboard.putNumber(   "navx_Accel_X",          robot.navx.getWorldLinearAccelX());
	        SmartDashboard.putNumber(   "navx_Accel_Y",          robot.navx.getWorldLinearAccelY());
	        SmartDashboard.putBoolean(  "navx_IsMoving",         robot.navx.isMoving());
	        SmartDashboard.putNumber(   "navx_Temp_C",           robot.navx.getTempC());
	        
	        SmartDashboard.putNumber(   "Velocity_X",       	robot.navx.getVelocityX() );
	        SmartDashboard.putNumber(   "Velocity_Y",       	robot.navx.getVelocityY() );
	        SmartDashboard.putNumber(   "Displacement_X",       robot.navx.getDisplacementX() );
	        SmartDashboard.putNumber(   "Displacement_Y",       robot.navx.getDisplacementY() );
    	}
    	catch(Exception ex)
    	{
    		System.out.println("ERROR!: NAVX_MXP\n" + ex);
    	}
	}

	private void autonomousUIInit()
    {
    	SmartDashboard.putBoolean("Use Auto Chooser", robot.useAutoChooser);
        SmartDashboard.putData("Autonomous Command Chooser", robot.autoChooser);
        SmartDashboard.putData("Auto Sequence Selector", robot.autoSelector);
    }
    
	private void autonomousUpdate()
	{
		//robot.useAutoChooser = (AutoSequence) SmartDashboard.getData("Auto Sequence Selector");
	}
	
	private void winchesInformation()
    {
    	//positions
		SmartDashboard.putNumber("Ladder TrashCanArmEncoder", robot.ladder.getWinchPosition());
		SmartDashboard.putNumber("Hooks TotesEncoder", robot.hooks.getWinchPosition());
		robot.limitModeEnabled = SmartDashboard.getBoolean("Winches Limit Mode Enabled");
		
		//level indexes
		SmartDashboard.putNumber("Hooks Target Index", robot.hooks.getCurrentTargetIndex());
    }
	
	private void driverInit()
	{
		SmartDashboard.putBoolean("Auto Stabilize", robot.driver.getAutostabilize());
		SmartDashboard.putNumber("Auto Stabilize Factor 2", Constants.RS_GYRO_FACTOR_2);
	}
	
	private void driverValues()
	{
    	SmartDashboard.putNumber("Current Magnitude", robot.xboxController.getMagnitude());
    	SmartDashboard.putNumber("Cartesian X Value", robot.xboxController.getAxisLStickX());
    	SmartDashboard.putNumber("Cartesian Y Value", robot.xboxController.getAxisLStickY());
    	SmartDashboard.putNumber("Current Rotation", robot.xboxController.getAxisRStickX());
    	SmartDashboard.putNumber("Speed Factor", robot.speedFactor);
    	
    	if(robot.speedFactor == 1.)
    		SmartDashboard.putString("Speed", "Quick");
    	else
    		SmartDashboard.putString("Speed", "Slow");
    	
    	robot.driver.setAutostabilize(SmartDashboard.getBoolean("Auto Stabilize"));
    	
    	Constants.RS_GYRO_FACTOR_2 = SmartDashboard.getNumber("Auto Stabilize Factor 2");
	}

}
