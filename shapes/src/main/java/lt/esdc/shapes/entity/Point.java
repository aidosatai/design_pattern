package lt.esdc.shapes.entity;

/**
 * Represents a point in 2D space.
 * Includes callback functionality to notify parent objects when coordinates change.
 */
public class Point {
    private double x;
    private double y;
    
    /**
     * Functional interface for change notification
     */
    public interface ChangeListener {
        void onPointChanged(Point point);
    }
    
    private ChangeListener changeListener;
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getX() {
        return x;
    }
    
    public void setX(double x) {
        if (this.x != x) {
            this.x = x;
            notifyChangeListener();
        }
    }
    
    public double getY() {
        return y;
    }
    
    public void setY(double y) {
        if (this.y != y) {
            this.y = y;
            notifyChangeListener();
        }
    }
    
    /**
     * Set a listener to be notified when this point's coordinates change
     * 
     * @param listener the listener to notify of changes
     */
    public void setChangeListener(ChangeListener listener) {
        this.changeListener = listener;
    }
    
    /**
     * Notify the registered listener that this point has changed
     */
    private void notifyChangeListener() {
        if (changeListener != null) {
            changeListener.onPointChanged(this);
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Point point = (Point) o;
        
        if (Double.compare(point.x, x) != 0) return false;
        return Double.compare(point.y, y) == 0;
    }
    
    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
    
    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
