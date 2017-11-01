import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {//handles all of our game objects
	
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	private int health = 100;
	Game game;


	public void tick(){
		for(int i = 0; i < object.size(); i++){
			GameObject tempObject = object.get(i);
			tempObject.tick();
		}

	}
	
	public void render(Graphics g){
		for(int i = 0; i < object.size(); i++){
			GameObject tempObject = object.get(i);
			
			tempObject.render(g);
		}
	}
	
	public void addObject(GameObject object){
		this.object.add(object);
	}
	
	public void removeObject(GameObject object){
		this.object.remove(object);
	}
}
