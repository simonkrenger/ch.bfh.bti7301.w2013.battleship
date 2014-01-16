/**
 * This is free and unencumbered software released into the public domain.
 * 
 * Anyone is free to copy, modify, publish, use, compile, sell, or distribute
 * this software, either in source code form or as a compiled binary, for any
 * purpose, commercial or non-commercial, and by any means.
 * 
 * In jurisdictions that recognize copyright laws, the author or authors of this
 * software dedicate any and all copyright interest in the software to the
 * public domain. We make this dedication for the benefit of the public at large
 * and to the detriment of our heirs and successors. We intend this dedication
 * to be an overt act of relinquishment in perpetuity of all present and future
 * rights to this software under copyright law.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * For more information, please refer to [http://unlicense.org]
 */
package ch.bfh.bti7301.w2013.battleship.game.players.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import ch.bfh.bti7301.w2013.battleship.game.Coordinates;
import ch.bfh.bti7301.w2013.battleship.game.Game;

/**
 * @author simon
 * 
 */
public class StrategyStack {
	
	ArrayList<Coordinates> stack = new ArrayList<Coordinates>();
			
	private final static int UP_SIZE = 5;
	
	public StrategyStack(int initialSize) {
		for(int i=0; i <= initialSize; i++) {
			stack.add(getRandom());
		}
	}

	public Coordinates getNextStep() {
		Random rand = new Random();
		
		if(stack.isEmpty()) {
			for(int i=0; i <= UP_SIZE; i++) {
				stack.add(getRandom());
			}
		}
		return stack.remove(rand.nextInt(stack.size()));
	}
	
	public Coordinates getRandom() {
		int sizeOfBoard = Game.getInstance().getRule().getBoardSize();

		Random rand = new Random();
		return new Coordinates(rand.nextInt(sizeOfBoard)+1, rand
					.nextInt(sizeOfBoard)+1);
	}

}
