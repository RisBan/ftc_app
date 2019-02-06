package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

@Autonomous(name="Auto After Sample", group = "Autonomous")
public class AutoAfterSample extends LinearOpMode {
    public String pos1 = "position 1";
    public String pos2 = "position 2";
    public String pos3 = "position 3";

    Hardware2 robot = new Hardware2();
    @Override
    public void runOpMode() {
        robot.autosInit(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            //creating an array for the hue, saturation, and values information
            float hsvValues[] = {0F, 0F, 0F};

            // values is a reference to the hsvValues array.
            final float values[] = hsvValues;

            // to amplify/attentuate the raw RGB measured values.
            final double SCALE_FACTOR = 255;

            //String for color seen. None=0; Red=1; Blue=2; Black=3;
            int color = 0;

            //covert RGB values to HSV values
            Color.RGBToHSV((int) (robot.colorSensor.red() * SCALE_FACTOR),
                    (int) (robot.colorSensor.green() * SCALE_FACTOR),
                    (int) (robot.colorSensor.blue() * SCALE_FACTOR),
                    hsvValues);

            //setting number values for each color
            if (hsvValues[0]<=360 && hsvValues[0]>=275) {
                color = 1;
                telemetry.addData("Color:", "Red");
            }
            else if (hsvValues[0]>=175 && hsvValues[0]<=250) {
                color = 2;
                telemetry.addData("Color:", "Blue");
            }
            else if (hsvValues[0]<=125 && hsvValues[0]>=60) {
                color = 3;
                telemetry.addData("Color:", "Black");
            }
            else {
                color = 0;
                telemetry.addData("Color:", "No color detected.");
            }

            //line following
            if (color == 3) {
                robot.leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.leftDrive.setPower(0.5);
                robot.leftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
                telemetry.addLine("Position: Moving left side");
            }
            else if (color == 1 || color == 2){
                robot.rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.rightDrive.setPower(0.5);
                robot.rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
                telemetry.addLine("Position: moving right side");
            }
            else {
                robot.leftDrive.setPower(0.0);
                robot.rightDrive.setPower(0.0);
            }
            telemetry.update();
        }

    }
}
