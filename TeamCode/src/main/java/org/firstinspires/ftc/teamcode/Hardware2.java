package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Hardware2 {

        /* Public OpMode members. */
        public DcMotor leftDrive   = null;
        public DcMotor  rightDrive  = null;
        public DcMotor collectorArm = null;
        public DcMotor  lift        = null;
        public CRServo latch       = null;
        public Servo middleBox = null;
        public ColorSensor colorSensor;
        public TouchSensor touchSensor;

        public static final double COLLECTOR_UP_POWER    =  0.7 ;
        public static final double COLLECTOR_DOWN_POWER  = -0.35 ;

        public static final double COLLECTOR_IN_POWER    =  0.75 ;
        public static final double COLLECTOR_OUT_POWER   =  -0.75 ;

        public static final double LIFT_UP_POWER    =  0.9 ;
        public static final double LIFT_DOWN_POWER  = -0.9 ;

        public static final double LATCH_OPEN_POWER  =  0.6 ;
        public static final double LATCH_CLOSE_POWER = -0.6 ;

        public static final double BUCKET_UP_POWER    =  0.8 ;
        public static final double BUCKET_DOWN_POWER  =  -0.4 ;

        public static final double BUCKET_DOOR_CLOSED =  0.55 ;
        public static final double BUCKET_DOOR_GOLD   =  0.88 ;
        public static final double BUCKET_DOOR_OPEN   =  1.0 ;

        //auto constants
        static final double ENCODER_CPR_60 = 1680;
        static final double ENCODER_CPR_40 = 1120;

        //auto lift constants
        static final double PINION_DIAMETER_Inches = 20.8/25.4;
        //static final double LIFT_HEIGHT = 8.875;
        static final double LIFT_HEIGHT = 9.4;
        static final double pinion_CPI = ENCODER_CPR_60/(PINION_DIAMETER_Inches*Math.PI);

        //auto collector constants
        static final double COLLECTOR_ANGLE = -90;

        //auto drive constants
        static final double TO_CRATER = 66;
        static final double WHEEL_DIAMETER_INCHES = 4.0;
        static final double WHEEL_CPI = ENCODER_CPR_40/(WHEEL_DIAMETER_INCHES*Math.PI);
        static final double COUNTS_PER_MOTOR_REV = 1440;
        static final double DRIVE_GEAR_REDUCTION = 1.0;
        static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);

        /* local OpMode members. */
        HardwareMap shwMap           =  null;
        private ElapsedTime period  = new ElapsedTime();

        /* Constructor */
        public Hardware2(){

        }

        /* Initialize standard Hardware interfaces */
        public void init(HardwareMap ahwMap) {
            // Save reference to Hardware map
            shwMap = ahwMap;

            // Define and Initialize Motors
            leftDrive  = shwMap.get(DcMotor.class, "left_drive");
            rightDrive = shwMap.get(DcMotor.class, "right_drive");
            lift = shwMap.get(DcMotor.class, "lift");
            collectorArm = shwMap.get(DcMotor.class, "collector_arm");

            leftDrive.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
            rightDrive.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors

            lift.setDirection(DcMotor.Direction.REVERSE);
            lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            collectorArm.setDirection(DcMotorSimple.Direction.FORWARD);

            // Set all motors to zero power
            leftDrive.setPower(0);
            rightDrive.setPower(0);
            lift.setPower(0);
            collectorArm.setPower(0);

            // Set all motors to run without encoders.
            // May want to use RUN_USING_ENCODERS if encoders are installed.
            leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            collectorArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            // Define and initialize ALL installed servos.
            latch  = shwMap.get(CRServo.class, "latch");
            latch.setPower(0.0);
            middleBox = shwMap.get(Servo.class, "middle_box");
            middleBox.setPosition(0.0);

            //Define and initialize ALL installed sensors.
            colorSensor = shwMap.get(ColorSensor.class, "color_sensor");
            touchSensor = shwMap.get(TouchSensor.class, "touch_sensor");

        }
        public void autosInit(HardwareMap othersHwMap) {
            init(othersHwMap);

            lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftDrive.setDirection(DcMotor.Direction.REVERSE);
            rightDrive.setDirection(DcMotor.Direction.FORWARD);

            collectorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            collectorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
