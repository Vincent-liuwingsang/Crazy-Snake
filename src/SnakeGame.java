import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class SnakeGame extends Applet implements Runnable, KeyListener{
	
	Graphics gfx;
	Image img;
	Thread thread;
	Snake snake;
	boolean gameover,difficult;
	List<Token> token;
	Token gem;
	int speed;
	
	public void init(){
		gameover=false;
		difficult=true;
		speed=2;
		this.resize(400, 400);
		img = createImage(400,400);
		gfx = img.getGraphics();
		this.addKeyListener(this);
		snake = new Snake();
		gem = new Token(snake);
		token = new ArrayList<Token>();
		token.add(new Token(snake));
		thread = new Thread(this);
		thread.start();
	}
	
	public void paint(Graphics g){
		gfx.setColor(Color.black);
		gfx.fillRect(0, 0, 400, 400);
		snake.draw(gfx);
		for (Token t:token){
			t.drawP(gfx);
		}
		gem.draw(gfx);
		if (gameover){
			gfx.setColor(Color.red);
			gfx.drawString("Game Over!", 160, 200);
		}
		gfx.setColor(Color.blue);
		gfx.drawString(Integer.toString(gem.getScore()), 380,15);
		g.drawImage(img,0,0,null);
	}
	
	public void update(Graphics g){
		paint(g);
	}
	
	public void repaint(Graphics g){
		paint(g);
	}
	
	public void run(){
		for (;;){
			if (gem.canAddToken()){
				token.add(new Token(snake));
			}

			if (difficult){
				for (Token t:token){
					t.move();
				}
			}
			if (!gameover){
				snake.move();
				this.checkGameOver();
				gem.snakeCollision();
			}
			this.repaint();
			try {
				Thread.sleep(40/speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	
	public void checkGameOver(){
		if (snake.getX()<0 || snake.getX()>396 || snake.getY()<0 || snake.getY() >396){
			gameover=true;
		}
		if (snake.snakeCollision()){
			gameover=true;
		}
		for (Token t:token){
			if(t.snakeCollision()){
				gameover=true;
			}
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (!snake.isMoving()){
			if (e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_DOWN || e.getKeyCode()==KeyEvent.VK_RIGHT){
				snake.setIsMoving(true);
			}
		}
		if (e.getKeyCode()==KeyEvent.VK_UP){
			if (snake.getYDir()!=1){
				snake.setXDir(0);
				snake.setYDir(-1);
			}
		}
		if (e.getKeyCode()==KeyEvent.VK_DOWN){
			if (snake.getYDir()!=-1){
				snake.setXDir(0);
				snake.setYDir(1);
			}			
		}
		if (e.getKeyCode()==KeyEvent.VK_LEFT){
			if (snake.getXDir()!=1){
				snake.setXDir(-1);
				snake.setYDir(0);
			}
		}
		if (e.getKeyCode()==KeyEvent.VK_RIGHT){
			if (snake.getXDir()!=-1){
				snake.setXDir(1);
				snake.setYDir(0);
			}
		}
		if (e.getKeyCode()==KeyEvent.VK_SPACE){
			restart();
		}
		if (e.getKeyCode()==KeyEvent.VK_I){
			if (difficult){
				difficult = false;
			}
			else {
				difficult = true;
			}
		}
		if (e.getKeyCode()==KeyEvent.VK_1){
			speed = 1;
		}
		if (e.getKeyCode()==KeyEvent.VK_2){
			speed = 2;
		}
		if (e.getKeyCode()==KeyEvent.VK_3){
			speed = 3;
		}
	}
	
	public void restart(){
		gameover=false;;
		snake = new Snake();
		token.clear();
		token.add(new Token(snake));
		gem = new Token(snake);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}