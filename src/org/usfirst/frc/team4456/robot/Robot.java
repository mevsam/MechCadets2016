package org.usfirst.frc.team4456.robot;

import java.util.Date;
import java.util.TimerTask;

import org.usfirst.frc.team4456.robot.autonomous.*;

import com.kauailabs.navx_mxp.AHRS;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot
{
	//Change the current robot used HERE
	RobotType roboType = RobotType.MAIN_BOT;
	
	XBoxController xboxController;
	
	public Driver driver;
	
	boolean limitModeEnabled = true;
	public Hooks hooks;
	public Ladder ladder;
	
	DigitalInput limitSwitch;
	ADXL345_I2C accelerometer;
	UltrasonicSensor ultrasonic;
	Lidar lidar;
	Compressor compressor;
	
	public AHRS navx;
	
	float yawDeriv1 = 0;
	float yawPrev = 0;
	float yawCurr = 0;
	
	UI ui;
	//Vision vision;
	SerialPort serialUSB, serialPortMXP;
	
	public double speedFactor;
	boolean useGyro, useMechanum;
	
	Command autoCommand1, autoCommand2;
	SendableChooser autoChooser;
	
	boolean useAutoChooser = false;
	
	SendableChooser autoSelector;
	AutoSequence autoSequence;
	
    public void robotInit()
    {	
    	// Hooks and Ladder init
    	ladder = new Ladder(15, Constants.piston1Port1, Constants.piston1Port2, Constants.piston2Port1, Constants.piston2Port2);
    	hooks = new Hooks(13);
    	
    	// Mechanum and Gyro booleans for Driver
    	useMechanum = true;
    	useGyro = false;
    	
    	// Controller init
    	xboxController = new XBoxController(1);
    	
    	// Serial init
    	//serialUSB = new SerialPort(9600,SerialPort.Port.kUSB);

    	// Limit switch init
    	limitSwitch = new DigitalInput(9);
    	
    	// Vision init
    	//vision = new Vision();
    	
    	// Ultrasonic Sensor init
    	ultrasonic = new UltrasonicSensor(1);
    	
    	// Lidar init
    	lidar = new Lidar(this);
    	
    	//NAVX init
    	try
    	{
	    	serialPortMXP = new SerialPort(57600, SerialPort.Port.kMXP);
	    	byte updateRateHz = 50;
	    	navx = new AHRS(serialPortMXP, updateRateHz);
    	}
    	catch(Exception ex)
    	{
    		System.out.println("ERROR!: NAVX INIT" + "\n" + ex);
    	}
    	
    	// Driver init
    	speedFactor = .7;
    	driver = new Driver(roboType, navx.getYaw());
    	
    	compressor = new Compressor(0);
    	
    	//Autonomous Command Code init
    	autoChooser = new SendableChooser();
    	autoChooser.addDefault("Autonomous1", new AutonomousCommand1(this));
    	autoChooser.addObject("Autonomous2", new AutonomousCommand2(this));
    	
    	//auto
    	autoSelector = new SendableChooser();
    	autoSelector.addDefault(Constants.auto1SimpleBackupName, new Auto1SimpleBackup(this));
    	autoSelector.addObject(Constants.auto2SimpleForwardsName, new Auto2SimpleForwards(this));
    	autoSelector.addObject(Constants.auto3PickToteBackupName, new Auto3PickToteBackup(this));
    	autoSelector.addObject(Constants.auto4PickToteBackupName, new Auto4PickToteRotateFwd(this));
    	autoSelector.addObject(Constants.auto5PickTrashBinLandfillName, new Auto5PickTrashBinLandfill(this));
    	
    	
    	// UI init
    	ui = new UI(this);
    	//smartUi = new SmartUI(this);
    	
    	//print startup message
    	System.out.println("\n---Robot Init successful.---\n\n"
    					+ "RobotType:\t\t" + roboType.robotTypeName + "\n"
    					+ "Robot TeamNum:\t" + roboType.teamNum + "\n"
    					+ "DATE:\t\t\t" + (new Date()).toString() + "\n");
    }
    
    public void autonomousInit()
    {
    	compressor.start();
		
    	if(useAutoChooser)
    	{
    		//get auto selected sequence from the auto selector.
    		autoSequence = (AutoSequence) autoSelector.getSelected();
    		autoSequence.runInit();
    		
    		/*
	    	autoCommand1 = (Command) autoChooser.getSelected();
	    	autoCommand1.start();
	    	*/
    	}
    	else
    	{
    		//ROTATE 90º:
    		//rot value .4
    		//time .8 sec
    		
    		/*
    		//AUTO1
    		int hookPositionsLength = Constants.HOOK_LOADER_POSITIONS.length;
    		
    		Timer timer = new Timer();
    		
    		timer.start();
    		hooks.setIndex(hookPositionsLength - 2);
    		Timer.delay(.7);
    		
    		//go backwards for _ seconds
    		while(!timer.hasPeriodPassed(2.5))
    		{
    			driver.driveRawPolar(.4, 180, 0, this);
    		}
    		
    		Timer.delay(.2);
    		hooks.setIndex(hookPositionsLength - 1);
    		*/
    		
    		//AUTO - fwd into auto zone
    		/*
    		Timer timer = new Timer();
    		timer.start();
    		while(!timer.hasPeriodPassed(1.3))
    		{
    			driver.driveRawPolar(.4, 0, 0, this);
    		}
    		*/
    		
    		/*
    		//AUTO - pick up tote, turn right 90º, go fwd to auto zone
    		
    		int hookPositionsLength = Constants.HOOK_LOADER_POSITIONS.length;
    		
    		Timer timer = new Timer();
    		
    		hooks.setIndex(hookPositionsLength - 2);
    		Timer.delay(.7);
    		
    		//gyro based
    		//driver.rotateInDegrees(90, 3, this); //MUST BE CALIBRATED
    		
    		//time based
    		timer.start();
    		while(timer.hasPeriodPassed(1.4)) //MUST BE CALIBRATED
    			driver.driveRawPolar(0, 0, .5, this);
    		
    		//go backwards for
    		while(!timer.hasPeriodPassed(2.5))
    		{
    			driver.driveRawPolar(.4, 180, 0, this);
    		}
    		
    		Timer.delay(.2);
    		hooks.setIndex(hookPositionsLength - 1);
    		*/
    		
    		
    		
    		/*
    		//AUTO5 - rotate 90º counterclock, pick up trash bin, backup, turn 90 clockwise
    		int ladderPosLength = Constants.WINCH_LADDER_POSITIONS.length;
    		
    		Timer timer = new Timer();
    		
    		System.out.println("Running Auto");
    		
    		//open grabber
    		ladder.open();
    		
    		
    		driver.rotateInDegrees(90, 3, this);
    		
    		//bring ladder down
    		ladder.setIndex(0);
    		Timer.delay(2.5);
    		
    		//move robot-back
    		timer.start();
    		while(!timer.hasPeriodPassed(.1))
    			driver.driveRawPolar(.3, 180, 0, this);
    		driver.driveRawPolar(0, 0, 0, this);
    		timer.stop();
    		timer.reset();
    		
    		//grab trashcan & raise ladder
    		ladder.close();
    		Timer.delay(1.0);
    		ladder.setIndex(ladderPosLength - 1);
    		Timer.delay(1.0);
    		
    		//move robot-fwd
    		timer.start();
    		while(!timer.hasPeriodPassed(1.45))
    			driver.driveRawPolar(.3, 0, 0, this);
    		driver.driveRawPolar(0, 0, 0, this);
    		timer.stop();
    		timer.reset();
    		
    		Timer.delay(.5);
    		
    		//rotate
    		driver.rotateInDegrees(-90, 3, this);
    		*/
    		if(isAutonomous())
    		{
    			//auto7();
    			auto8();
    		}
    		
    		
    	}
    	
    }
    
    private void auto5()
    {
    	int ladderPosLength = Constants.WINCH_LADDER_POSITIONS.length;
		
		Timer timer = new Timer();
		
		System.out.println("Running Auto");
		
		//open grabber
		ladder.open();
		
		
		driver.rotateInDegrees(90, 3, this);
		
		//bring ladder down
		ladder.setIndex(0);
		Timer.delay(2.5);
		
		//move robot-back
		timer.start();
		while(!timer.hasPeriodPassed(.1))
			driver.driveRawPolar(.3, 180, 0, this);
		driver.driveRawPolar(0, 0, 0, this);
		timer.stop();
		timer.reset();
		
		//grab trashcan & raise ladder
		ladder.close();
		Timer.delay(1.0);
		ladder.setIndex(ladderPosLength - 1);
		Timer.delay(1.0);
		
		//move robot-fwd
		timer.start();
		while(!timer.hasPeriodPassed(1.45))
			driver.driveRawPolar(.3, 0, 0, this);
		driver.driveRawPolar(0, 0, 0, this);
		timer.stop();
		timer.reset();
		
		Timer.delay(.5);
		
		//rotate
		driver.rotateInDegrees(-90, 3, this);
    }
    
    private void auto6()
    {
    	int ladderPosLength = Constants.WINCH_LADDER_POSITIONS.length;
		
		Timer timer = new Timer();
		
		System.out.println("Running Auto");
		
		//open grabber
		ladder.open();
		
		//bring ladder down
		ladder.setIndex(0);
		Timer.delay(2.5);
		
		//move robot to trashcan -- rBack
		timer.start();
		while(!timer.hasPeriodPassed(.1))
			driver.driveRawPolar(.3, 180, 0, this);
		driver.driveRawPolar(0, 0, 0, this);
		timer.stop();
		timer.reset();
		
		//grab trashcan & raise ladder
		ladder.close();
		Timer.delay(1.0);
		ladder.setIndex(ladderPosLength - 1);
		Timer.delay(1.0);
		
		//move robot-fwd -- rFwd
		timer.start();
		while(!timer.hasPeriodPassed(1.45))
			driver.driveRawPolar(.3, 0, 0, this);
		driver.driveRawPolar(0, 0, 0, this);
		timer.stop();
		timer.reset();
		
		Timer.delay(.5);
		
		//rotate 90º
		driver.rotateInDegrees(90, 3, this);
		
		//bring down ladder and drop the trash can and bring the ladder back up.
		ladder.setIndex(0);
		Timer.delay(2.5);
		ladder.open();
		Timer.delay(.75);
		ladder.setIndex(4);
		
		//rotate -45º
		driver.rotateInDegrees(-45, 1.5, this);
		
		//Move towards trashcan
		timer.start();
		while(!timer.hasPeriodPassed(.5));
			driver.driveRawPolar(.3, 0, 0, this);
		driver.stop();
		timer.stop();
		timer.reset();
		
		//move ladder back down
		ladder.setIndex(0);
		Timer.delay(1.);
		
		//grab trash can
		ladder.close();
		Timer.delay(.25);
		
		//bring ladder up
		ladder.setIndex(4);
    }
    
    /**
     * auto for 2 cans.
     * */
    private void auto7()
    {
    	if(isAutonomous())
    	{
	    	Timer timer = new Timer();
	    	ladder.open();
	    	int ladderTopIndex = Constants.WINCH_LADDER_POSITIONS.length - 1;
	    	
	    	//rotate +58º
	    	driver.rotateInDegrees(66, 1.7, this);
	    	
	    	//drop ladder
	    	ladder.setIndex(0);
	    	Timer.delay(2.5);
	    	
	    	//pick up can
	    	ladder.close();
	    	Timer.delay(.1);
	    	
	    	//raise ladder
	    	ladder.setIndex(ladderTopIndex);
	    	Timer.delay(1.7);
	    	
	    	//rotate -58º
	    	driver.rotateInDegrees(-66, 1.7, this);
	    	
	    	//drop can -- dont drop the ladder
	    	ladder.open();
	    	Timer.delay(.5);
	    	
	    	//rotate +122º
	    	driver.rotateInDegrees(130, 3., this);
	    	
	    	//drop ladder
	    	ladder.setIndex(0);
	    	Timer.delay(2.5);
	    	
	    	//pick up can
	    	ladder.close();
	    	
	    	//raise ladder
	    	ladder.setIndex(ladderTopIndex);
	    	Timer.delay(2.);
    	}
    }

    
    private void auto8()
    {
    	int ladderTopIndex = Constants.WINCH_LADDER_POSITIONS.length - 1;
    	ladder.open();
    	
    	//bring down ladder
    	ladder.setIndex(0);
    	Timer.delay(2.5);
    	
    	//grab can
    	ladder.close();
    	Timer.delay(.5);
    	
    	//bring ladder up
    	ladder.setIndex(ladderTopIndex);
    	Timer.delay(2.9);
    	
    	//rotate -90
    	driver.rotateInDegrees(-90, 3.0, this);
    	
    	//drop can
    	ladder.open();
    	Timer.delay(.5);
    	
    	//rotate +127
    	driver.rotateInDegrees(127, 4.0, this);
    	
    	//bring down ladder
    	ladder.setIndex(0);
    }
    /**
     * @param clockwise if true, rot clockwise, if false, rotate counterclock
     * 
     * */
    private void rotate90(boolean clockwise)
    {
    	Timer timer = new Timer();
	    timer.start();
	    double rotMagnitude = clockwise ? 0.4 : -0.4;
		while(!timer.hasPeriodPassed(1.25))
			driver.driveRawPolar(0, 0, rotMagnitude, this);
		driver.stop();
		timer.stop();
		timer.reset();
    }
    
    /**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic()
	{
		if(isAutonomous())
		{
			
			if(useAutoChooser)
			{
				autoSequence.runPeriodic();
			}
			
			/*
			 * Scheduler.getInstance().run();
			 *	ui.update(this);
			 */
		}
	}

	//----------------
    //TELEOP
    //----------------
    public void teleopInit()
    {
    	compressor.start();
    }
    public void teleopPeriodic()
    {
    	ui.update();
    	
    	lidar.getDistance();
    	
    	/*
    	 * Switches between Mechanum and Tank based on what wheels we are using.
    	 * It also switches between Cartesian and Polar Mechanum Drives based on 
    	 * whether or not we are using a gyro.
    	 */
    	driver.drive(xboxController, (float)navx.getYaw(), this);
    	hooks.cycle(xboxController, this);
    	ladder.cycle(xboxController, this);
    	
    	//lidar.update(this);
    }
    
    public void disabledInit()
    {
    	super.disabledInit();
    	compressor.stop();
    	System.out.println("\n---DISABLED---\n\n"
				+ "RobotType:\t\t" + roboType.robotTypeName + "\n"
				+ "Robot TeamNum:\t" + roboType.teamNum + "\n"
				+ "DATE:\t\t\t" + (new Date()).toString() + "\n");
    }
    public void disabledPeriodic()
    {
    	super.disabledPeriodic();
    	ui.update();
    }
    
    public void testInit()
	{
		super.testInit();
	}

	/**
     * This function is called periodically during test mode
     */
    public void testPeriodic()
    {
    	super.testPeriodic();
    }
    
    private void updateYawDeriv()
    {
    		yawPrev = yawCurr;
    		yawCurr = navx.getYaw();
    		yawDeriv1 = yawCurr - yawPrev;
    }
    
    private class Time extends TimerTask
    {
		@Override
		public void run()
		{
			updateYawDeriv();
		}
    }
    
}
