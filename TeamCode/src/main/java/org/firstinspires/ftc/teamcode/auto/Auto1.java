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

        //Autonomous code, no usage of vision. Starts with 1 (one) preloaded specimen directly at the center of the wall.


        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            autoClass.Init(hardwareMap);


            autoClass.Forward(1000, 0.5f);


            autoClass.Arm(0.85); //Arm goes up to combat the energy transfer from moving
            Thread.sleep(500); //Sleep to allow arm to move
            autoClass.CloseClaw();
            Thread.sleep(500);

            autoClass.extendSlide(1800); //Linear slide goes up high enough to place specimen

            Thread.sleep(2000); //Wait for linear slide

            autoClass.Forward(175, 0.1f); //Forward slightly to properly align specimen

            autoClass.CloseClaw(); // Adjust grip on the claw

            autoClass.extendSlide(100); //Move slide back down

            Integer i = 0;
            while (i < 1000) { //Overcomplicated code to force claw closed
                autoClass.CloseClaw();
                telemetry.addData(i.toString(), null);
                telemetry.update();
                Thread.sleep(10);

                i += 10;
            }
            Thread.sleep(750); //More time for linear slide
            autoClass.OpenClaw(); //Release specimen
            Thread.sleep(100); //Time for claw to open

            autoClass.extendSlide(11); //Set the linear slide to 0
            Thread.sleep(750); //Time for slide to go down
            autoClass.extendSlide(0); //Still having it going down just ignore this part

            autoClass.Backward(1000, 0.5f); //Move backwards (obviously)

            autoClass.Right(2000, 0.5f); //Move right to park

            //Wow much productivity
            Thread.sleep(1000);




            //autoClass.ForwardDist(0.25, 0.25);







            break;


        }
    }

    




}