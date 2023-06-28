package trajectory;

import java.util.ArrayList;
import java.util.List;

public class Point {
    private final Double X;
    private final Double Y;
    private final Double Z;

    private final Double Vx;
    private final Double Vy;
    private final Double Vz;

    public Point(Double X, Double Y, Double Z, Double Vx, Double Vy, Double Vz){
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.Vx = Vx;
        this.Vy = Vy;
        this.Vz = Vz;
    }

    public List<Double> getXYZ(){
        List<Double> XYZ = new ArrayList<>();
        XYZ.add(X);
        XYZ.add(Y);
        XYZ.add(Z);
        return XYZ;
    }

    public List<Double> getV(){
        List<Double> V = new ArrayList<>();
        V.add(Vx);
        V.add(Vy);
        V.add(Vz);
        return V;
    }

    public Double getX(){
        return X;
    }

    public Double getY(){
        return Y;
    }

    public Double getZ(){
        return Z;
    }

    public Double getVx(){
        return Vx;
    }

    public Double getVy(){
        return Vy;
    }

    public Double getVz(){
        return Vz;
    }

}
