package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Autonomous
public class Hardware{
    private DcMotor lift;
    private Servo hook;
    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor collector;

    HardwareMap hwMap;
    //@Override
    public void runOpMode() {
        hwMap = new HardwareMap();
        lift = hwMap.get(DcMotor.class, "lift");
        hook = hwMap.get(Servo.class, "hook");
        leftDrive = hwMap.get(DcMotor.class, "leftDrive");
        rightDrive = hwMap.get(DcMotor.class, "rightDrive");
        collector = hwMap.get(DcMotor.class, "collector");
    }



}