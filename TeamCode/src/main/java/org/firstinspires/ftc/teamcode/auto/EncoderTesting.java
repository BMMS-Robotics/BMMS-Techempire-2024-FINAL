package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class EncoderTesting extends LinearOpMode {
    BNO055IMU imu;
    @Override
    public void runOpMode() throws InterruptedException {

        AutoClass autoClass = new AutoClass();


        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            autoClass.Init(hardwareMap); //Do not touch (Pretty please)
            Thread.sleep(1500);
            autoClass.LeftUntilAprilTag();
            /*
            autoClass.Right(500, 0.5f);
            autoClass.TurnLeft(500, 0.5f);
            autoClass.TurnRight(500, 0.5f);
            */
            sleep(10000);


            break;
        }
    }






}