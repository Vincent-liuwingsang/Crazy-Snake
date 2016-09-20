import java.awt.Color;
import java.awt.Graphics;

public class Token{
	private int x,y,count;
	private Snake snake;
	private static int score;
	private static boolean added;
	
	public Token(Snake s){
		x=(int)(Math.random()*395);
		y=(int)(Math.random()*395);
		snake = s;
		count =0;
		score=0;
		added=false;
	}
	
	public int getScore(){
		return score;
	}
	
	public boolean canAddToken(){
		if (!added && score!=0){
			added=true;
			return true;
		}
		return false;
	}
	
	public void setAdded(boolean b){
		added=b;
	}
	
	public void resetScrore(){
		score=0;
	}
	
	public void drawP(Graphics g){
		g.setColor(Color.pink);
		g.fillRect(x,y,6,6);
	}

	public void draw(Graphics g){
		g.setColor(Color.green);
		g.fillRect(x,y,6,6);
	}

	
	public void changePosition(){
		x=(int)(Math.random()*395);
		y=(int)(Math.random()*395);
	}
	
	public boolean snakeCollision(){
		int snakeX=snake.getX()+2;
		int snakeY=snake.getY()+2;
		if (snakeX>=x-1 && snakeX <=x+7 && snakeY>=y-1 && snakeY <=y+7){
			changePosition();
			score++;
			snake.setElongate(true);
			added=false;
			return true;
		}
		return false;
	}
	public void move(){
		boolean turn=true;
		if ((double)count >= Math.random()*200){
			turn= (turn?false:true);
			count=0;
		}
		if (turn){
			if (x<=0){
				if (Math.random()<0.5){
					turn=false;
				}
				else {
					x+=2;
				}
			}
			else if (x>=396){
				if (Math.random()<0.5){
					turn=false;
				}
				else {
					x-=2;
				}
			}
			else {
				if (Math.random()<0.5){
					x+=2;
				}
				else {
					x-=2;
				}
			}
			

		}
		else{
			if (y<=0){
				if (Math.random()<0.5){
					turn=true;
				}
				else {
					y+=1;
				}
			}
			else if (y>=396){
				if (Math.random()<0.5){
					turn=true;
				}
				else {
					y-=1;
				}
			}
			else {
				if (Math.random()<0.5){
					y+=1;
				}
				else {
					y-=1;
				}
			}
		}
		count++;
	}
}