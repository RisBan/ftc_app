package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Autonomous
public class Hardware{
    private DcMotor lift;
    private Servo hook;
    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor collector;

    double Hook_Open = 0.0;
    double Hook_Closed = 1.0;

    HardwareMap hwMap = null;
    //@Override
    public void runOpMode() {
        //motors
        lift = hwMap.get(DcMotor.class, "lift");
        leftDrive = hwMap.get(DcMotor.class, "leftDrive");
        rightDrive = hwMap.get(DcMotor.class, "rightDrive");
        collector = hwMap.get(DcMotor.class, "collector");

        //servos
        hook = hwMap.get(Servo.class, "hook");

        //directions
        lift.setDirection(DcMotor.Direction.FORWARD);
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        collector.setDirection(DcMotor.Direction.FORWARD);
        hook.setDirection(Servo.Direction.FORWARD);

        //do we want to scale the range?
        hook.setPosition(Hook_Closed);
    }



}