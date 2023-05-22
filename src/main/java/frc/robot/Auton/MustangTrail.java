package frc.robot.Auton;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class MustangTrail {
    // private final String[] NA = {"not", "alive"};
    // private StringArraySubscriber options = SmartDashboard.getStringArray("options", NA);
    private NetworkTableInstance robot1;
    private NetworkTableInstance robot2;
    private NetworkTableInstance robot3;
    private final NetworkTableInstance[] robots = {robot1, robot2, robot3};

    public MustangTrail(int[] teams) {
        for (int x = 0; x < 3; x++) {
            robots[x] = NetworkTableInstance.create();
            robots[x].startClient4("robot" + x);
            robots[x].setServerTeam(teams[x], NetworkTableInstance.kDefaultPort4);
        }

        // team1 = team1 / 100;
        // team2 = team2 / 100;
        // team3 = team3 / 100;
        // robot1 = NetworkTableInstance.getDefault().getTable("" + team1);
        // robot2 = NetworkTableInstance.getDefault().getTable("" + team2);
        // robot3 = NetworkTableInstance.getDefault().getTable("" + team3);

        // robot1 = NetworkTableInstance.create();
        // robot1.startClient4("robot1");
        // robot1.setServerTeam(team1);
        
        // robot2 = NetworkTableInstance.create();
        // robot2.startClient4("robot2");
        // robot2.setServerTeam(team2);

        // robot3 = NetworkTableInstance.create();
        // robot3.startClient4("robot3");
        // robot3.setServerTeam(team3);
    }

    public NetworkTable getTable(int teamNum) {
        return robots[teamNum + 1].getTable("SmartDashboard");
    }
}
