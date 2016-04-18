public class Main {
	public static void main(String[] args) {
		
		Window window = new Window();
		Test test = new Test(window);
		Keyboard keyboard = new Keyboard(test);
		window.setKeyListener(keyboard);
		
		
		
		int size = test.getSize();
		int[][] newMapMatrix = test.getMapMatrix();
		
		for(int i = 0; i < size; i++){
			newMapMatrix[i][0] = 1;
			newMapMatrix[i][size-1]=1;
			for(int j = 1;j < size-1; j++){
				newMapMatrix[i][j] = 0;
				newMapMatrix[0][j] = 1;
				newMapMatrix[size-1][j] = 1;
			}
		}
		
		test.setMapMatrix(newMapMatrix);
		window.refreshMap(newMapMatrix, test.getHero().getHP());
		
	}
}
