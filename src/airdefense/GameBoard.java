package airdefense;

import java.awt.image.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;

import javax.swing.*;

public class GameBoard extends JPanel{
	public static final int HEIGHT = 600;
	public static final int WIDTH = 960;
	
	public static BufferedImage bombImage;
	public static BufferedImage planeImage;
	public static BufferedImage debrisImage;
	public static BufferedImage fortImage;
	public static BufferedImage bulletImage;
	public static BufferedImage explosionImage;
	public static BufferedImage backgroundImage;
	public static BufferedImage titleImage;
	public static BufferedImage pauseImage;
	public static BufferedImage gameOverImage;
	public static BufferedImage missionCompleteImage;
	
	private static final int INITIAL = 0; //表示游戏未开始
	private static final int RUNNING = 1; //表示游戏正在运行
	private static final int PAUSE = 2;   //表示游戏暂停
	private static final int GAMEOVER = 3;//表示游戏结束
	private static final int MISSIONCOMPLETE = 4; //表示关卡完成
	
	private static int score = 0;
	private static final int interval = 10;
	public static int level = 1;
	private Timer timer;
	private int gamestate;
	private int fortTimeMark = 0;
	private int planeToGo = 10;
	
	private Fort fort = new Fort();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Bomb> bombs = new ArrayList<Bomb>();
	private ArrayList<Debris> debrises = new ArrayList<Debris>();
	private ArrayList<Explosion> explosions = new ArrayList<Explosion>();
	private static ArrayList<FlyingObject> bombDelist,debrisDelist,bulletDelist,planeDelist;
	private ArrayList<Explosion> explosionDelist;
	private ArrayList<Plane> planes = new ArrayList<Plane>();
	//private Plane plane = new Plane();
	
	static { //初始化图片资源  
        try {  
            backgroundImage = ImageIO.read(GameBoard.class  
                    .getResource("background.png"));  
            titleImage = ImageIO.read(GameBoard.class.getResource("title.png"));  
            planeImage = ImageIO  
                    .read(GameBoard.class.getResource("plane.png"));  
            bombImage = ImageIO.read(GameBoard.class.getResource("bomb.png"));  
            bulletImage = ImageIO.read(GameBoard.class.getResource("bullet.png"));  
            fortImage = ImageIO.read(GameBoard.class.getResource("fort.png"));  
            debrisImage = ImageIO.read(GameBoard.class.getResource("debris.png")); 
            explosionImage = ImageIO.read(GameBoard.class.getResource("explosion.png"));
            pauseImage = ImageIO.read(GameBoard.class.getResource("pause.png"));  
            gameOverImage = ImageIO  
                    .read(GameBoard.class.getResource("gameover.png"));  
            missionCompleteImage = ImageIO  
                    .read(GameBoard.class.getResource("missioncomplete.png"));
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
	
	  
 
    @Override  
    public void paint(Graphics g) {  
        g.drawImage(backgroundImage, 0, 0, null); // 画背景图  
        paintFort(g); // 画炮台 
        paintPlane(g); // 画飞机
        paintBullets(g); //画炮弹  
        paintBombs(g); //画炸弹  
        paintDebrises(g);//画碎片
        paintExplosions(g);//画爆炸
        paintScore(g); // 画分数  
        paintState(g); // 画游戏状态  
    }  
 
    public void paintFort(Graphics g) {  //画炮台
        g.drawImage(fort.getImage(), fort.x, fort.y, null);  
    }  
    
    public void paintPlane(Graphics g) {  // 画飞机
        //g.drawImage(plane.getImage(), plane.x, plane.y, null);  
    	for (Iterator<Plane> it = planes.iterator();it.hasNext();) {  
            Plane pl = it.next();  
            g.drawImage(pl.getImage(), pl.x, pl.y, null);  
        }
    }
  
    public void paintBullets(Graphics g) {  //画炮弹
        for (Iterator<Bullet> it = bullets.iterator();it.hasNext();) {  
            Bullet bu = it.next();  
            g.drawImage(bu.getImage(), bu.x, bu.y, null);  
        }  
    }  
  
    public void paintBombs(Graphics g) {  //画炸弹
    	for (Iterator<Bomb> it = bombs.iterator();it.hasNext();) {  
            Bomb bo = it.next();  
            g.drawImage(bo.getImage(), bo.x, bo.y, null);  
        }  
    }
    
    public void paintExplosions(Graphics g) {  //画爆炸效果
    	for (Iterator<Explosion> it = explosions.iterator();it.hasNext();) {  
            Explosion exp = it.next();  
            g.drawImage(exp.getImage(), exp.x, exp.y, null);  
        }  
    }
    
    public void paintDebrises(Graphics g) {  //画掉落的飞机碎片
    	for (Iterator<Debris> it = debrises.iterator();it.hasNext();) {  
            Debris de = it.next();  
            g.drawImage(de.getImage(), de.x, de.y, null);  
        }  
    }
 
    public void paintScore(Graphics g) {  
        int x = 10; // x坐标  
        int y = 25; // y坐标  
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 22); // 字体  
        g.setColor(new Color(0xFF0000));  
        g.setFont(font); // 设置字体  
        g.drawString("SCORE:" + score, x, y); // 画分数  
        y=y+20; // y坐标增20  
        g.drawString("LIVES:" + fort.lives, x, y); // 画生命数 
        y=y+20; // y坐标增20
        g.drawString("LEVEL:" + GameBoard.level, x, y); // 画当前等级
        y=y+20; // y坐标增20
        g.drawString("PLANETOGO:" + planeToGo, x, y); // 画剩余过关飞机数
    }  
 
    public void paintState(Graphics g) {  
        switch (gamestate) {  
        case INITIAL: // 启动状态  
            g.drawImage(titleImage, 0, 0, null);  
            break;  
        case PAUSE: // 暂停状态  
            g.drawImage(pauseImage, 0, 0, null);  
            break;  
        case GAMEOVER: // 游戏终止状态  
            g.drawImage(gameOverImage, 0, 0, null);  
            break;  
        case MISSIONCOMPLETE:  //关卡完成状态
        	g.drawImage(missionCompleteImage, 0, 0, null);  
            break;
        }  
    }
    
    public static void main(String[] args) {
		JFrame frame = new JFrame("AirDefenseGame");
		GameBoard game = new GameBoard();
		frame.add(game);
        frame.setSize(WIDTH, HEIGHT); 
        frame.setAlwaysOnTop(true);   
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true); 
        game.action(); 
	}
	
	public void action() {  
        MouseAdapter l = new MouseAdapter() {  
            @Override  
            public void mouseMoved(MouseEvent e) {
                if (gamestate == RUNNING) { //移动炮台 
                    int x = e.getX();  
                    int y = e.getY();  
                    fort.moveTo(x, y);  
                }  
            }  

            @Override  
            public void mouseEntered(MouseEvent e) { 
                if (gamestate == PAUSE) { //继续游戏
                    gamestate = RUNNING;  
                }  
            }  

            @Override  
            public void mouseExited(MouseEvent e) { 
                if (gamestate == RUNNING) { // 游戏暂停  
                    gamestate = PAUSE;  
                }  
            }  

            @Override  
            public void mouseClicked(MouseEvent e) { 
                switch (gamestate) {  
                case INITIAL:  //准备开始游戏
                    gamestate = RUNNING;
                    planeEnterAction();
                    break;  
                case GAMEOVER: // 准备重新开始 
                    bombs = new ArrayList<Bomb>(); 
                    bullets = new ArrayList<Bullet>(); 
                    debrises = new ArrayList<Debris>();
                    planes = new ArrayList<Plane>();
                    fort = new Fort();
                    fortTimeMark = 0;
                    score = 0;
                    //existPlane = false;
                    gamestate = INITIAL;
                    level = 1;
                    planeToGo = 10;
                    break;
                case MISSIONCOMPLETE:
                	bombs = new ArrayList<Bomb>(); 
                    bullets = new ArrayList<Bullet>(); 
                    //debrises = new ArrayList<Debris>();
                    fort = new Fort();
                    fortTimeMark = 0;
                    level++;
                    gamestate = RUNNING;
                    planeToGo = 10;
                    if(planes.size() < level) planeEnterAction();
                	break;
                }  
            }  
        };  
        this.addMouseListener(l); 
        this.addMouseMotionListener(l);

        timer = new Timer(); // 主流程控制  
        timer.schedule(new TimerTask() {  
            @Override  
            public void run() {  
                if (gamestate == RUNNING) { 
                	//if(!existPlane) planeEnterAction();
                	if(planes.size() < level) planeEnterAction();//飞机进场
                	bombDelist = new ArrayList<FlyingObject>();
                	bulletDelist = new ArrayList<FlyingObject>();
                	debrisDelist = new ArrayList<FlyingObject>();
                	explosionDelist = new ArrayList<Explosion>();
                	planeDelist = new ArrayList<FlyingObject>();
                    dropBombAction(); //投掷炸弹    
                    fortFireAction(); // 炮台射击  
                    nextStepAction(); //所有物体走一步
                    hitPlaneCheck(); //检查炮弹是否击中飞机
                    bulletHitBombCheck();//检查炮弹是否击中炸弹
                    fort.lives += hitFortCheck(); // 检查炸弹是否击中炮台 
                    if(fort.lives <= 0) {//判断游戏结束
                    	gamestate = GAMEOVER;
                    }
                    else {
                    	outOfBoundsCheck(); 
                    	deleteObjects();// 删除越界飞行物及子弹    
                    }
                    //if(plane.outOfBounds()) planeEnterAction();
                }  
                repaint(); // 重绘，调用paint()方法  
            }  

        }, interval, interval);  
    } 
	
	private void planeEnterAction() { //飞机进场
		//this.plane = new Plane();
		if((planes.isEmpty()) || 
				(planes.get(planes.size()-1).timeMark > 100))//飞机进场间隔100
		planes.add(new Plane());
		//existPlane = true;
	}
	
	private void dropBombAction() {//飞机投掷炸弹
		/*if( (plane.timeMark%40) == 0) {
			bombs.add(plane.dropBomb());
		}*/
		for(Iterator<Plane> it = planes.iterator();it.hasNext();) {
			Plane pl = it.next();
			if( (pl.timeMark%80) == 0) {//间隔80投掷一枚炸弹
				bombs.add(pl.dropBomb());
			}
		}
		
	}
	
	private void nextStepAction() {//所有飞行物走一步
		for(Iterator<Bomb> it = bombs.iterator();it.hasNext();) {
			it.next().nextStep();
		}
		
		for(Iterator<Bullet> it = bullets.iterator();it.hasNext();) {
			it.next().nextStep();
		}
		
		for(Iterator<Debris> it = debrises.iterator();it.hasNext();) {
			it.next().nextStep();
		}
		
		for(Iterator<Explosion> it = explosions.iterator();it.hasNext();) {
			Explosion exp = it.next();
			if(exp.nextStep() > 80) explosionDelist.add(exp);
		}
		for(Iterator<Plane> it = planes.iterator();it.hasNext();) {
			it.next().nextStep();
		}
	}
	
	private void fortFireAction() {//炮台开火
		this.fortTimeMark++;
		if(this.fortTimeMark % 30 ==0) {//发射间隔为30
			bullets.add(fort.fireWeapon());
		}
	}
	
	private void hitPlaneCheck() {//检查飞机是否击中
		for(Iterator<Bullet> it1 = bullets.iterator();it1.hasNext();) {
			Bullet bu = it1.next();
			for(Iterator<Plane> it2 = planes.iterator();it2.hasNext();) {
				Plane pl = it2.next();
				if(pl.hitted(bu)) {
					score += 100;
					planeToGo--;	
					if(planeToGo <= 0) gamestate = MISSIONCOMPLETE;//关卡完成
					debrises.add(new Debris(bu.x, bu.y));
					explosions.add(new Explosion(pl.x, pl.y));
					bulletDelist.add(bu);
					planeDelist.add(pl);
					planeEnterAction();
					return;
				}
			}
		}
	}
	
	private int hitFortCheck() {//检查炮台是否被击中，并返回生命值变化
		int result = 0;
		for(Iterator<Debris> it = debrises.iterator();it.hasNext();) {
			Debris de = it.next();
			if(this.fort.hitted(de)) {
				result++;//捡到飞机碎片，生命值增加
				debrisDelist.add(de);
			}
		}
		
		for(Iterator<Bomb> it = bombs.iterator();it.hasNext();) {
			Bomb bo = it.next();
			if(this.fort.hitted(bo)) {
				result--;//被炸弹击中，生命值减少
				bombDelist.add(bo);
				explosions.add(new Explosion(bo.x, bo.y));
			}
		}
		return result;
	}
	
	private void bulletHitBombCheck() {//检查炸弹是否被炮弹击中
		for(Iterator<Bomb> it1 = bombs.iterator();it1.hasNext();) {
			Bomb bo = it1.next();
			boolean destroyed = false;
			for(Iterator<Bullet> it2 = bullets.iterator();it2.hasNext();) {
				Bullet bu = it2.next();
				if(bo.hitted(bu)) {
					destroyed = true;
					explosions.add(new Explosion(bo.x, bo.y));
					bulletDelist.add(bu);
					break;
				}
			}
			if(destroyed) {
				bombDelist.add(bo);
				continue;
			}
		}
	}
	
	private void outOfBoundsCheck() {//检查飞行物是否超出边界
		for(Iterator<Bomb> it = bombs.iterator();it.hasNext();) {
			Bomb bo = it.next();
			if(bo.outOfBounds()) {
				bombDelist.add(bo);
			}
		}
		
		for(Iterator<Bullet> it = bullets.iterator();it.hasNext();) {
			Bullet bu = it.next();
			if(bu.outOfBounds()) {
				bulletDelist.add(bu);
			}
		}

		for(Iterator<Debris> it = debrises.iterator();it.hasNext();) {
			Debris de = it.next();
			if(de.outOfBounds()) {
				debrisDelist.add(de);
			}
		}
		
		for(Iterator<Plane> it = planes.iterator();it.hasNext();) {
			Plane pl = it.next();
			if(pl.outOfBounds()) {
				planeDelist.add(pl);
			}
		}
	}
	
	private void deleteObjects() {//用于将废弃的飞行物移除
		for(Iterator<FlyingObject> it = bombDelist.iterator();it.hasNext();) {
			 bombs.remove(it.next());
		}
		for(Iterator<FlyingObject> it = bulletDelist.iterator();it.hasNext();) {
			 bullets.remove(it.next());
		}
		for(Iterator<FlyingObject> it = debrisDelist.iterator();it.hasNext();) {
			 debrises.remove(it.next());
		}
		for(Iterator<Explosion> it = explosionDelist.iterator();it.hasNext();) {
			 explosions.remove(it.next());
		}
		for(Iterator<FlyingObject> it = planeDelist.iterator();it.hasNext();) {
			 planes.remove(it.next());
		}
	}
}
