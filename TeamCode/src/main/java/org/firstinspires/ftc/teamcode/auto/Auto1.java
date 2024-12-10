package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class Auto1 extends LinearOpMode {
//    DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
//    DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
//    DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
//    DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
//    DcMotor extend = hardwareMap.dcMotor.get("linearSlide");

    //Robot moves roughly 3.8ft/sec (45in/sec) (114cm/sec) at 100% power according to my back-of-the-envelope math
    //Henceforth moves *ROUGHLy* 1 foot per 360ms
    //At 0.5% then 720ms
    //Still at .5 to move forward 1 tile then 1140ms


    BNO055IMU imu;
    @Override
    public void runOpMode() throws InterruptedException {

        // // Declare our motors
        // // Make sure your ID's match your configuration
        AutoClass autoClass = new AutoClass();

        Servo arm = hardwareMap.get(Servo.class, "arm");
        //Servo claw = hardwareMap.get(Servo.class, "claw");

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.


        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            autoClass.Init(hardwareMap);

            //Move robot sideways
            //720ms * 4 ft (2 tiles sideways) = 2280ms to end up 2 tiles to the left
//            frontLeftMotor.setPower(-0.5);
//            backLeftMotor.setPower(-0.5);
//            frontRightMotor.setPower(-0.5);
//            backRightMotor.setPower(-0.5);


            //335 per foot?

            //375 per foot??

            //IMPORTANT: Movement functions pass in Distance (In feet) and power of the motor! DO NOT SET THE POWER TO 0.
            //If you set the power to 0, the universe as we know it will end.
            //autoClass.Forward(375, 0.5);
            //autoClass.Forward(750, 0.5);
            autoClass.ForwardDist(2.25, 0.5);
            telemetry.addData("Forward", null);
            telemetry.update();
            autoClass.Arm(0.85);
            telemetry.addData("Arm Up", null);
            telemetry.update();
            Thread.sleep(500);
            autoClass.extendSlide(2700);
            telemetry.addData("Slide Up", null);
            telemetry.update();
            Thread.sleep(2000);
            autoClass.ForwardDist(0.25, 0.1);
            telemetry.addData("Forward", null);
            telemetry.update();
            autoClass.CloseClaw();
            telemetry.addData("Close Claw", null);
            telemetry.update();
            autoClass.extendSlide(2000);
            telemetry.addData("Lower Slide", null);
            telemetry.update();
            Integer i = 0;
            while (i < 1000) {
                autoClass.CloseClaw();
                telemetry.addData(i.toString(), null);
                telemetry.update();
                Thread.sleep(10);

                i += 10;
            }
            Thread.sleep(400);
            autoClass.OpenClaw();
            Thread.sleep(100);
            autoClass.extendSlide(11);
            Thread.sleep(750);
            autoClass.extendSlide(0);
            autoClass.Backward(2.25, 0.5);
            autoClass.Right(4, 0.5);




            //autoClass.ForwardDist(0.25, 0.25);







            break;


        }
    }

    




}