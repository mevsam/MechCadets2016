package org.usfirst.frc.team4456.robot;

public class Constants
{
	// Defines XBox Controls
	
	/*
	 * The left thumbstick will drive the robot
	 * The right thumbstick will rotate the robot
	 */
	public static final int button_A = 1;
	public static final int button_B = 2;
	public static final int button_X = 3;
	public static final int button_Y = 4;
	public static final int button_leftBumper = 5;
	public static final int button_rightBumper = 6;
	public static final int button_Back = 7;
	public static final int button_Start = 8;
	public static final int button_leftStick = 9;
	public static final int button_rightStick = 10;
	public static final int axis_leftStick_X = 0;
	public static final int axis_leftStick_Y = 1;
	public static final int axis_leftTrigger = 2;
	public static final int axis_rightTrigger = 3;
	public static final int axis_rightStick_X = 4;
	public static final int axis_rightStick_Y = 5;
	public static final int axis_dPad_X = 6;

	// Lidar Stuff
	public static final int LIDAR_ADDR = 0x62;
	public static final int LIDAR_CONFIG_REGISTER = 0x00;
	public static final int LIDAR_DISTANCE_REGISTER = 0x8f;
	
	// Ultrasonic Stuff
	public static final double ULTRASONIC_FACTOR_VOLTS = 40.2969;
	
	// Hooks Stuff
	public static final double HOOK_LOADER_AUTO_CHECK_POSITION = -2700;
	public static final double[] HOOK_LOADER_POSITIONS = {-11688, -10355, -8224, -5884, -4500, 0}; //In reverse order, from HIGH to LOW
	public static final double MAX_HOOK_NUDGE = 90;
	
	// Ladder Stuff
	public static final double[] WINCH_LADDER_POSITIONS = {-12100, -11995, -10228, -8171, -5692, -501, 500};
	public static final double LADDER_NUDGE_FACTOR = 170;
	public static final int piston1Port1 = 0, piston1Port2 = 1, piston2Port1 = 2, piston2Port2 = 3;
	
	//Driver stuff
	public static final double RS_GYRO_FACTOR_1 = 1;
	public static double RS_GYRO_FACTOR_2 = 0.01;
	
	//vision
	public static final String filePathRoborio = "/home/lvuser/";
	
	//AUTO
	public static String auto1SimpleBackupName = "Auto1SimpleBackup";
	public static String auto2SimpleForwardsName = "Auto2SimpleForwards";
	public static String auto3PickToteBackupName = "Auto3PickToteBackup";
	public static String auto4PickToteBackupName = "Auto4PickToteRotateBackup";
	public static String auto5PickTrashBinLandfillName = "Auto5PickTrashBinLandfill";
	

}
