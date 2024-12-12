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

        //Servo arm = hardwareMap.get(Servo.class, "arm");
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
            autoClass.ForwardDist(2.25, 0.5); //Move forward

            autoClass.Arm(0.85); //Arm goes up to combat the energy transfer from moving
            Thread.sleep(500); //Sleep to allow arm to move

            autoClass.extendSlide(2700); //Linear slide goes up high enough to place specimen

            Thread.sleep(2000); //Wait for linear slide

            autoClass.ForwardDist(0.25, 0.1); //Forward slightly to properly align specimen

            autoClass.CloseClaw(); // Adjust grip on the claw

            autoClass.extendSlide(2000); //Move slide back down

            Integer i = 0;
            while (i < 1000) { //Overcomplicated code to force claw closed
                autoClass.CloseClaw();
                telemetry.addData(i.toString(), null);
                telemetry.update();
                Thread.sleep(10);

                i += 10;
            }
            Thread.sleep(400); //More time for linear slide
            autoClass.OpenClaw(); //Release specimen
            Thread.sleep(100); //Time for claw to open

            autoClass.extendSlide(11); //Set the linear slide to 0
            Thread.sleep(750); //Time for slide to go down
            autoClass.extendSlide(0); //Still having it going down just ignore this part

            autoClass.Backward(2.25, 0.5); //Move backwards (obviously)

            autoClass.Right(4, 0.5); //Move right to park

            //Wow much productivity




            //autoClass.ForwardDist(0.25, 0.25);







            break;


        }
    }

    




}