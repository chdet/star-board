import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{
	private Test test;
	public Keyboard(Test test){
		this.test = test;
	}

	@Override
	public void keyPressed(KeyEvent event) {
		int key = event.getKeyCode();
		
		switch (key){
			case KeyEvent.VK_RIGHT: 
				System.out.println("Right");
				test.moveHeroRight();
				break;
			case KeyEvent.VK_LEFT:
				System.out.println("Left");
				test.moveHeroLeft();
				break;
			case KeyEvent.VK_DOWN:
				System.out.println("Down");
				test.moveHeroDown();
				break;
			case KeyEvent.VK_UP:
				System.out.println("Up");
				test.moveHeroUp();
				break;
			case KeyEvent.VK_SPACE:
				System.out.println("Do something...");
				int[] newProjPos = {1,1};
				Projectile proj = new Projectile(test, newProjPos, test.getHero().getOrient());
				test.setProj(proj);
				Thread t1 = new Thread(proj);
				t1.start();
				break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}