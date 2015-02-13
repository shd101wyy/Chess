package chess;

/**
 * Created by wangyiyi on 2/12/15.
 */
public class Coordinate {
    private int x;
    private int y;
    public Coordinate(){
        this.x = -1;
        this.y = -1;
    }
    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return this.x;
    }
    public void setX(int x){
        this.x = x;
    }
    public int getY(){
        return this.y;
    }
    public void setY(int y){
        this.y = y;
    }
}
