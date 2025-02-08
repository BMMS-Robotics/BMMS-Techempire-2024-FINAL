package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class AutoSample extends LinearOpMode {
    BNO055IMU imu;
    @Override
    public void runOpMode() throws InterruptedException {

        AutoClass autoClass = new AutoClass();
        //775 is a 90 degree turn, i think


        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            autoClass.Init(hardwareMap); //Do not touch (Pretty please)

            //Nobody ever looks at this code. In fact, if you're reading this,
            //send or show me a screenshot and you will win
            autoClass.Forward(12, 0.5f);
            autoClass.TurnLeft(90, 0.5f);
            autoClass.Forward(4, 0.5f);
            autoClass.TurnLeft(50, 0.5f);
            autoClass.Right(10, 0.5f);
            autoClass.extendSlide(3800);

            Thread.sleep(500);

            autoClass.Forward(22, 0.25f);

            Thread.sleep(1500);

            autoClass.OpenClaw();

            Thread.sleep(1500);

            autoClass.Backward(10, 0.5f);
            autoClass.extendSlide(1);
            autoClass.TurnRight(135, 0.5f);

            Thread.sleep(1500);

            autoClass.Forward(4, 0.5f);
            autoClass.Right(2, 0.5f);
            autoClass.Arm(0.5);

            Thread.sleep(2000);

            autoClass.CloseClaw();

            Thread.sleep(1000);

            autoClass.Arm(0.85);
            autoClass.Backward(4, 0.5f);
            autoClass.Left(2, 0.5f);

            Thread.sleep(500);

            autoClass.TurnLeft(135, 0.5f);
            autoClass.extendSlide(3800);
            autoClass.Left(6, 0.5f);

            Thread.sleep(500);

            autoClass.Forward(10, 0.5f);
            autoClass.OpenClaw();





            Thread.sleep(2000); //Time for final commands to finish executing

            break;
        }
    }






}