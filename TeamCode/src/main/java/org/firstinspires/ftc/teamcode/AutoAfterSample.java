package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Auto After Sample", group = "Autonomous")
public class AutoAfterSample extends LinearOpMode {
    public String pos1 = "position 1";
    public String pos2 = "position 2";
    public String pos3 = "position 3";

    private ElapsedTime pressedTime = new ElapsedTime();
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime newTime = new ElapsedTime();

    //String for color seen. None=0; Red=1; Blue=2; Black=3;
    int color = 0;
    int backup = 0;
    int newRightTarget;
    int newLeftTarget;

    Hardware2 robot = new Hardware2();

    //creating an array for the hue, saturation, and values information
    float hsvValues[] = {0F, 0F, 0F};

    // values is a reference to the hsvValues array.
    final float values[] = hsvValues;

    // to amplify/attentuate the raw RGB measured values.
    final double SCALE_FACTOR = 255;

    @Override
    public void runOpMode() {
        robot.autosInit(hardwareMap);
        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {
            runtime.reset();
            //covert RGB values to HSV values
            Color.RGBToHSV((int) (robot.colorSensor.red() * SCALE_FACTOR),
                    (int) (robot.colorSensor.green() * SCALE_FACTOR),
                    (int) (robot.colorSensor.blue() * SCALE_FACTOR),
                    hsvValues);

            getColor();

            if (robot.touchSensor.isPressed() == false && backup == 0) {
                lineFollow();
            }
            else if (robot.touchSensor.isPressed() == true) {
                pressedTime.reset();
                backup = 1;
                while (opModeIsActive() && backup == 1) {

                    while ((robot.touchSensor.isPressed() == true) && (pressedTime.seconds() <= 3) && (opModeIsActive())) {

                    }
                    if (robot.touchSensor.isPressed() == false) {
                        backup = 0;
                    } else if (pressedTime.seconds() >= 3) {
                        telemetry.addData("Starting:", "finish auto");
                        telemetry.update();
                        finishAuto();
                    }
                }
            }
            }

            telemetry.update();
        }

    //line following
    void lineFollow() {
        if (color == 0) {
            telemetry.addLine();
            telemetry.addData("Moving:", "No, because no color detected");
            telemetry.update();
        } else if (color != 0) {
            while (color == 3 && opModeIsActive()) {
                robot.leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.leftDrive.setPower(0.5);
                robot.leftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
                getColor();
            }
            while ((color == 1 || color == 2) && opModeIsActive()) {
                robot.rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.rightDrive.setPower(0.5);
                robot.rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
                getColor();
            }
        }
    }
/*        if (color == 3) {
            robot.leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            while (color == 3 && opModeIsActive()) {
                robot.leftDrive.setPower(0.2);
                robot.leftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
            }
            telemetry.addLine("Position: Moving left side");
        } else if (color == 1 || color == 2) {
            robot.rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            while ((color == 1 || color == 2) && opModeIsActive()) {
                robot.rightDrive.setPower(0.2);
                robot.rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
            }
            telemetry.addLine("Position: moving right side");
        } else {
            robot.leftDrive.setPower(0.0);
            robot.rightDrive.setPower(0.0);
        }
    }*/

    void finishAuto() {
        rotate(90, .5, 5);
        robot.collectorArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        newTime.reset();
        while (opModeIsActive() && newTime.seconds() <= 2) {
            robot.collectorArm.setPower(.75);
        }
        newRightTarget = robot.rightDrive.getCurrentPosition() + (int) (robot.TO_CRATER * robot.COUNTS_PER_INCH);
        newLeftTarget = robot.leftDrive.getCurrentPosition() + (int) (robot.TO_CRATER * robot.COUNTS_PER_INCH);
        robot.rightDrive.setTargetPosition(newRightTarget);
        robot.leftDrive.setTargetPosition(newLeftTarget);
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightDrive.setPower(1);
        robot.leftDrive.setPower(1);

    }

    public void rotate(double degrees, double speed, double timeoutS) {
        //arc length/2pir = degrees
        //degrees/2pir = arc length
        double circumference = 2 * Math.PI * 6.625;
        double inches = degrees / circumference;

        int newLeftTarget;
        int newRightTarget;
        newLeftTarget = robot.leftDrive.getCurrentPosition() + (int) (inches * robot.COUNTS_PER_INCH);
        newRightTarget = robot.rightDrive.getCurrentPosition() + (int) (inches * robot.COUNTS_PER_INCH);
        if (degrees > 0) {

            robot.leftDrive.setTargetPosition(-newLeftTarget);
            robot.rightDrive.setTargetPosition(newRightTarget);
        } else if (degrees < 0) {
            robot.leftDrive.setTargetPosition(newLeftTarget);
            robot.rightDrive.setTargetPosition(-newRightTarget);
        }
        // Turn On RUN_TO_POSITION
        robot.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData("update:", "about to turn on motors");
        telemetry.update();
        robot.leftDrive.setPower(Math.abs(speed));
        robot.rightDrive.setPower(Math.abs(speed));

        while (opModeIsActive() &&
                (runtime.seconds() < timeoutS) &&
                (robot.leftDrive.isBusy() && robot.rightDrive.isBusy())) {

            // Display it for the driver.
            telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
            telemetry.addData("Path2", "Running at %7d :%7d",
                    robot.leftDrive.getCurrentPosition(),
                    robot.rightDrive.getCurrentPosition());
            telemetry.update();
        }

        // Stop all motion;
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

        // Turn off RUN_TO_POSITION
        robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }
    public void getColor() {

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
    }
}
